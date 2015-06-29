package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.FormularioDirecionamentoDao;
import gov.emater.aterweb.dao.PerguntaDao;
import gov.emater.aterweb.dao.RespostaDao;
import gov.emater.aterweb.dao.RespostaVersaoDao;
import gov.emater.aterweb.model.Formulario;
import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.OpcaoResposta;
import gov.emater.aterweb.model.OpcaoValor;
import gov.emater.aterweb.model.Pergunta;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.Resposta;
import gov.emater.aterweb.model.RespostaVersao;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.service.EnqueteService;
import gov.emater.aterweb.service.ServiceException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnqueteServiceImpl extends CrudServiceImpl<FormularioDirecionamento, Integer, FormularioDirecionamentoDao> implements EnqueteService {

	@Autowired
	private PerguntaDao perguntaDao;

	@Autowired
	private RespostaDao respostaDao;

	@Autowired
	private RespostaVersaoDao respostaVersaoDao;

	@Autowired
	public EnqueteServiceImpl(FormularioDirecionamentoDao dao) {
		super(dao);
	}

	@Transactional(readOnly = true)
	@Override
	public FormularioDirecionamento formularioResponder(Integer formularioDirecionamentoId, Usuario usuario) {
		FormularioDirecionamento formularioDirecionamento = restore(formularioDirecionamentoId);
		// criticar informacoes
		if (formularioDirecionamento == null) {
			throw new ServiceException("Formulário não encontrado");
		}
		if (Confirmacao.N.equals(formularioDirecionamento.getFormulario().getPermitirRespostaAnonima())) {
			if (usuario == null) {
				throw new ServiceException("Usuário não identificado");
			}
			if (!formularioDirecionamento.getUsuario().getId().equals(usuario.getId())) {
				throw new ServiceException("Usuï¿½rio nï¿½o autorizado a responder este formulï¿½rio");
			}
		}
		// TODO critica se pode responder mais de uma vez
		Calendar hoje = Calendar.getInstance(), inicio, termino;
		boolean disponivel = false;

		// verificar a data da campanha
		inicio = formularioDirecionamento.getFormulario().getInicio();
		termino = formularioDirecionamento.getFormulario().getTermino();
		if (hoje.after(inicio) && hoje.before(termino)) {
			disponivel = true;
		}
		if (!disponivel) {
			// verificar data de algum formulario especifico
			inicio = formularioDirecionamento.getInicio();
			termino = formularioDirecionamento.getTermino();
			if (hoje.after(inicio) && hoje.before(termino)) {
				disponivel = true;
			}
		}
		if (!disponivel) {
			throw new ServiceException("O formulï¿½rio esta indisponï¿½vel no momento");
		}

		// preparar formulario para resposta
		formularioDirecionamento.setUsuario(simplificaUsuario(formularioDirecionamento.getUsuario()));
		formularioDirecionamento.setRespostaVersaoList(null);
		formularioDirecionamento.getFormulario().setFormularioDirecionamentoList(null);

		RespostaVersao respostaVersao = new RespostaVersao();
		respostaVersao.setFormularioDirecionamento(new FormularioDirecionamento(formularioDirecionamento.getId()));
		respostaVersao.setInicio(hoje);
		respostaVersao.setUuid(UUID.randomUUID().toString());

		for (Pergunta pergunta : formularioDirecionamento.getFormulario().getPerguntaList()) {

			Formulario frm = new Formulario();
			frm.setId(formularioDirecionamento.getFormulario().getId());
			pergunta.setFormulario(frm);

			OpcaoResposta opcaoResposta = new OpcaoResposta();
			opcaoResposta.setId(pergunta.getOpcaoResposta().getId());
			opcaoResposta.setNome(pergunta.getOpcaoResposta().getNome());
			opcaoResposta.setOpcaoRespostaTipo(pergunta.getOpcaoResposta().getOpcaoRespostaTipo());
			if (pergunta.getOpcaoResposta().getOpcaoValorList() != null) {
				List<OpcaoValor> opcaoValorList = new ArrayList<OpcaoValor>();
				for (OpcaoValor opcaoValor : pergunta.getOpcaoResposta().getOpcaoValorList()) {
					OpcaoValor o = new OpcaoValor();
					o.setId(opcaoValor.getId());
					o.setCodigo(opcaoValor.getCodigo());
					o.setDescricao(opcaoValor.getDescricao());
					o.setOrdem(opcaoValor.getOrdem());
					opcaoValorList.add(o);
				}
				Collections.sort(opcaoValorList, new Comparator<OpcaoValor>() {
					@Override
					public int compare(OpcaoValor o1, OpcaoValor o2) {
						if (o1.getOrdem() != null && o2.getOrdem() != null) {
							return o1.getOrdem() - o2.getOrdem();
						}
						return 0;
					}
				});
				opcaoResposta.setOpcaoValorList(opcaoValorList);
			}
			pergunta.setOpcaoResposta(opcaoResposta);
			Resposta resposta = pergunta.getResposta();
			if (resposta == null) {
				resposta = new Resposta();
			}
			Pergunta perg = new Pergunta();
			perg.setId(pergunta.getId());
			resposta.setPergunta(perg);
			resposta.setRespostaVersao(respostaVersao);
			pergunta.setResposta(resposta);
		}
		Collections.sort(formularioDirecionamento.getFormulario().getPerguntaList(), new Comparator<Pergunta>() {
			@Override
			public int compare(Pergunta p1, Pergunta p2) {
				if (p1.getOrdem() != null && p2.getOrdem() != null) {
					return p1.getOrdem() - p2.getOrdem();
				}
				return 0;
			}
		});
		return formularioDirecionamento;
	}

	@Transactional(readOnly = true)
	@Override
	public FormularioDirecionamento formularioRespostaVer(Integer respostaVersaoId, Usuario usuario) {
		FormularioDirecionamento result = null;
		RespostaVersao respostaVersao = null;
		try {
			respostaVersao = respostaVersaoDao.restore(respostaVersaoId);
			if (respostaVersao == null) {
				throw new ServiceException("Dados nï¿½o encontrados");
			}
		} catch (EntityNotFoundException e) {
			throw new ServiceException("Dados nï¿½o encontrados");
		}
		result = formularioResponder(respostaVersao.getFormularioDirecionamento().getId(), usuario);
		if (result.getUsuario() != null && (!usuario.getId().equals(result.getUsuario().getId()))) {
			throw new ServiceException("Acesso a informaï¿½ï¿½o nï¿½o autorizada");
		}
		RespostaVersao novaRespostaVersao = new RespostaVersao();
		novaRespostaVersao.setId(respostaVersao.getId());
		novaRespostaVersao.setInicio(respostaVersao.getInicio());
		novaRespostaVersao.setTermino(respostaVersao.getTermino());
		novaRespostaVersao.setUuid(respostaVersao.getUuid());
		for (Pergunta pergunta : result.getFormulario().getPerguntaList()) {
			for (Resposta resposta : respostaVersao.getRespostaList()) {
				if (pergunta.getId().equals(resposta.getPergunta().getId())) {
					pergunta.setResposta(new Resposta(resposta.getId(), resposta.getValor(), novaRespostaVersao));
				}
			}
		}
		return result;
	}

	@Transactional
	@Override
	public void formularioRespostaRemover(Integer respostaVersaoId, Usuario usuario) {
		RespostaVersao respostaVersao = respostaVersaoDao.restore(respostaVersaoId);
		if (respostaVersao.getFormularioDirecionamento().getUsuario() != null && (!usuario.getId().equals(respostaVersao.getFormularioDirecionamento().getUsuario().getId()))) {
			throw new ServiceException("Operaï¿½ï¿½o nï¿½o autorizada");
		}
		if (respostaVersao != null) {
			for (Resposta resposta : respostaVersao.getRespostaList()) {
				respostaDao.delete(resposta);
			}
			// FIXME nÃ£o esta funcionando com este comando
			// respostaDao.flushAndClear();
		}
		respostaVersaoDao.delete(respostaVersao);
	}

	@Transactional
	@Override
	public FormularioDirecionamento formularioRespostaSalvar(FormularioDirecionamento formularioDirecionamento, Usuario usuario) {
		if (formularioDirecionamento == null) {
			throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. formularioDirecionamento is null");
		}
		FormularioDirecionamento result = getDao().restore(formularioDirecionamento.getId());
		if (result == null) {
			throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. formularioDirecionamento.getId() is null");
		}
		Formulario formulario = formularioDirecionamento.getFormulario();
		if (formulario == null) {
			throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. formularioDirecionamento.getFormulario() is null");
		}
		List<Pergunta> perguntaList = formulario.getPerguntaList();
		if (perguntaList == null || perguntaList.size() < 1) {
			throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. formularioDirecionamento.getFormulario().getPerguntaList() is null");
		}
		if (Confirmacao.N.equals(result.getFormulario().getPermitirReenviarResposta())) {
			if (result.getRespostaVersaoList().size() > 0) {
				throw new ServiceException("Este formulï¿½rio sï¿½ permite o envio de uma ï¿½nica resposta. Caso deseje reenviar, exclua a resposta anterior");
			}
		}
		// inicio da gravaï¿½ï¿½o
		RespostaVersao respostaVersao = null;
		for (Pergunta pergunta : perguntaList) {
			if (pergunta.getId() == null) {
				throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. pergunta.getId() == null");
			}
			Resposta resposta = pergunta.getResposta();
			if (resposta == null) {
				throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. resposta == null");
			}
			if (respostaVersao == null) {
				respostaVersao = resposta.getRespostaVersao();
				if (respostaVersao == null) {
					throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. respostaVersao == null");
				}
				respostaVersao.setId(null);
				respostaVersao.setTermino(Calendar.getInstance());
				respostaVersao.setFormularioDirecionamento(result);
				if (respostaVersao.getInicio() == null) {
					throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. respostaVersao.getInicio() == null");
				}
				if (respostaVersao.getInicio().after(respostaVersao.getTermino())) {
					throw new ServiceException("Estrutura do formulï¿½rio inconsistente. Informe ao Administrador do Sistema. respostaVersao.getInicio() after respostaVersao.getTermino()");
				}
				respostaVersao.setRespostaList(new ArrayList<Resposta>());
			}
			resposta.setRespostaVersao(respostaVersao);
			resposta.setId(null);
			resposta.setPergunta(perguntaDao.restore(pergunta.getId()));
			if (Confirmacao.S.equals(resposta.getPergunta().getObrigatorio()) && (resposta.getValor() == null || resposta.getValor().trim().length() == 0)) {
				throw new ServiceException(String.format("A resposta a pergunta %s deve ser preenchida", pergunta.getPergunta()));
			}
			respostaVersao.getRespostaList().add(resposta);
		}
		respostaVersaoDao.save(respostaVersao);
		result = getDao().restore(result.getId());
		RespostaVersao exemplo = new RespostaVersao();
		exemplo.setFormularioDirecionamento(result);
		result.setRespostaVersaoList(respostaVersaoDao.restore(exemplo));
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public List<FormularioDirecionamento> formulariosAtivos(Usuario usuario) {
		// DEVOLVER LISTA DE FORMULARIOS
		// SE JA FOI RESPONDIDO INFORMAR O HORARIO DA RESPOSTA
		List<FormularioDirecionamento> result = new ArrayList<FormularioDirecionamento>();
		List<Object[]> formularioDirecionamentoList = getDao().formulariosAtivos(usuario);
		if (formularioDirecionamentoList != null && formularioDirecionamentoList.size() > 0) {
			for (Object[] registro : formularioDirecionamentoList) {
				// preparar a instancia do formulario
				Formulario formulario = new Formulario();
				formulario.setId((Integer) registro[0]);
				formulario.setCodigo((String) registro[1]);
				formulario.setNome((String) registro[2]);
				if (registro[3] != null) {
					Calendar data = Calendar.getInstance();
					data.setTime((java.sql.Timestamp) registro[3]);
					formulario.setInicio(data);
				}
				if (registro[4] != null) {
					Calendar data = Calendar.getInstance();
					data.setTime((java.sql.Timestamp) registro[4]);
					formulario.setTermino(data);
				}
				formulario.setPermitirRespostaAnonima(Confirmacao.valueOf((String) registro[5]));
				formulario.setPermitirReenviarResposta(Confirmacao.valueOf((String) registro[6]));
				formulario.setPermitirExcluirResposta(Confirmacao.valueOf((String) registro[7]));
				formulario.setRespostaDirecionada(Confirmacao.valueOf((String) registro[8]));
				// preparar a instancia do formulario direcionamento
				FormularioDirecionamento formularioDirecionamento = new FormularioDirecionamento();
				formularioDirecionamento.setId((Integer) registro[9]);
				formularioDirecionamento.setFormulario(formulario);
				formularioDirecionamento.setUsuario(simplificaUsuario(usuario));
				formularioDirecionamento.setComplemento((String) registro[10]);
				if (registro[11] != null) {
					formularioDirecionamento.setOrdem(((BigInteger) registro[11]).intValue());
				}
				// preparar as instancias de respostas
				if (formularioDirecionamento.getId() != null) {
					RespostaVersao respostaVersaoExemplo = new RespostaVersao();
					respostaVersaoExemplo.setFormularioDirecionamento(formularioDirecionamento);
					List<RespostaVersao> respostaVersaoList = respostaVersaoDao.restore(respostaVersaoExemplo);
					if (respostaVersaoList != null) {
						for (RespostaVersao respostaVersao : respostaVersaoList) {
							respostaVersao.setRespostaList(null);
							respostaVersao.setFormularioDirecionamento(null);
						}
					}
					formularioDirecionamento.setRespostaVersaoList(respostaVersaoList);
				}

				result.add(formularioDirecionamento);
			}
		}
		return result;
	}

	private Usuario simplificaUsuario(Usuario usuario) {
		if (usuario == null) {
			return null;
		}
		Usuario result = new Usuario();
		result.setId(usuario.getId());
		result.setNomeUsuario(usuario.getNomeUsuario());
		Pessoa pes = null;
		try {
			pes = usuario.getPessoa().getClass().newInstance();
			pes.setId(usuario.getPessoa().getId());
			pes.setNome(usuario.getPessoa().getNome());
			pes.setApelidoSigla(usuario.getPessoa().getApelidoSigla());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		result.setPessoa(pes);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> getAvaliacao() {

		String[] cabecalho = { "formulario_codigo", "tipo_avaliacao", "avaliador_empresa", "avaliador_matricula", "avaliador_nome", "avaliado", "avaliado_matricula", "avaliado_nome", "p01", "p02", "p03", "p04", "p05", "p06", "p07", "p08", "p09", "p10", "p11", "p12", "que_bom",
				"poderia_melhorar", "que_tel" };

		List<Object[]> avaliacao = getDao().getAvaliacao();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Map<String, Object> reg = new HashMap<String, Object>();
		int cont = 0;
		Object[] anterior = new Object[6];
		for (Object[] registro : avaliacao) {

			// verificar se o registro é novo
			for (int i = 0; i < anterior.length; i++) {
				if ((registro[i] == null && anterior[i] != null) || (registro[i] != null && !registro[i].equals(anterior[i]))) {
					if (reg != null) {
						result.add(reg);
					}
					reg = new HashMap<String, Object>();
					cont = 2;
					// atualizar o registro anterior
					for (int j = 0; j < anterior.length; j++) {
						anterior[j] = registro[j];
						reg.put(cabecalho[j], registro[j]);
					}
					String temp = (String) reg.get("avaliado");
					String matricula = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));
					String nome = temp.substring(temp.indexOf(") ") + 2, temp.length());

					reg.put("avaliado_matricula", matricula);
					reg.put("avaliado_nome", nome);
					break;
				}
			}

			reg.put(cabecalho[anterior.length + cont++], registro[7]);
		}
		// captar o ultimo registro
		if (reg != null) {
			result.add(reg);
		}

		return result;
	}

}