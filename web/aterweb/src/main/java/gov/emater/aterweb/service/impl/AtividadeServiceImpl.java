package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.ArquivoDao;
import gov.emater.aterweb.dao.AssuntoAcaoDao;
import gov.emater.aterweb.dao.AtividadeArquivoDao;
import gov.emater.aterweb.dao.AtividadeAssuntoDao;
import gov.emater.aterweb.dao.AtividadeDao;
import gov.emater.aterweb.dao.AtividadePessoaDao;
import gov.emater.aterweb.dao.AtividadeRelacionamentoDao;
import gov.emater.aterweb.dao.AtividadeRestricaoDao;
import gov.emater.aterweb.dao.OcorrenciaDao;
import gov.emater.aterweb.model.Acao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.model.Assunto;
import gov.emater.aterweb.model.AssuntoAcao;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.AtividadeArquivo;
import gov.emater.aterweb.model.AtividadeAssunto;
import gov.emater.aterweb.model.AtividadePessoa;
import gov.emater.aterweb.model.AtividadeRelacionamento;
import gov.emater.aterweb.model.AtividadeRestricao;
import gov.emater.aterweb.model.Metodo;
import gov.emater.aterweb.model.Ocorrencia;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.model.domain.AtividadeFinalidade;
import gov.emater.aterweb.model.domain.AtividadePessoaFuncao;
import gov.emater.aterweb.mvc.dto.AtividadeCadFiltroDto;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.service.AtividadeService;
import gov.emater.aterweb.service.ConstantesService;
import gov.emater.aterweb.service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.IllegalClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtividadeServiceImpl extends CrudServiceImpl<Atividade, Integer, AtividadeDao> implements AtividadeService {

	@Autowired
	private ArquivoDao arquivoDao;

	@Autowired
	private AssuntoAcaoDao assuntoAcaoDao;

	@Autowired
	private AtividadeArquivoDao atividadeArquivoDao;

	@Autowired
	private AtividadeAssuntoDao atividadeAssuntoDao;

	@Autowired
	private AtividadeDao atividadeDao;

	@Autowired
	private AtividadePessoaDao atividadePessoaDao;

	@Autowired
	private AtividadeRelacionamentoDao atividadeRelacionamentoDao;

	@Autowired
	private AtividadeRestricaoDao atividadeRestricaoDao;

	@Autowired
	private ConstantesService constantesService;

	@Autowired
	private OcorrenciaDao ocorrenciaDao;

	@Autowired
	public AtividadeServiceImpl(AtividadeDao dao) {
		super(dao);
	}

	private Atividade fetch(Atividade atividade) {
		if (atividade != null) {
			atividade.setAtividadeArquivoList(fetchAtividadeArquivoList(atividade));
			atividade.setAtividadeAssuntoList(fetchAtividadeAssuntoList(atividade));
			atividade.setAtividadePessoaList(fetchAtividadePessoaList(atividade));
			atividade.setAtividadeRestricaoList(fetchAtividadeRestricaoList(atividade));
			atividade.setMetodo(new Metodo(atividade.getMetodo().getId(), atividade.getMetodo().getNome()));
			atividade.setOcorrenciaList(fetchOcorrenciaList(atividade));
			atividade.setPrincipalAtividadeList(fetchPrincipalAtividadeList(atividade));
			atividade.setSubatividadeList(fetchSubatividadeList(atividade));
			atividade.setUsuario(fetchUsuario(atividade.getUsuario()));
		}
		return atividade;
	}

	private Usuario fetchUsuario(Usuario usuario) {
		Usuario result = null;
		if (usuario != null) {
			result = new Usuario(usuario.getId(), usuario.getNomeUsuario(), fetchPessoa(usuario.getPessoa()));
		}
		return result;
	}

	private Pessoa fetchPessoa(Pessoa pessoa) {
		Pessoa result = null;
		if (pessoa != null) {
			try {
				result = pessoa.getClass().newInstance();
			} catch (Exception e) {
				throw new IllegalClassException("erro ao instanciar classe");
			}
			result.setId(pessoa.getId());
			result.setNome(pessoa.getNome());
			result.setApelidoSigla(pessoa.getApelidoSigla());
			result.setPessoaTipo(pessoa.getPessoaTipo());
		}
		return result;
	}

	private List<AtividadeArquivo> fetchAtividadeArquivoList(Atividade atividade) {
		List<AtividadeArquivo> result = null;
		if (!isEmpty(atividade.getAtividadeArquivoList())) {
			for (AtividadeArquivo reg : atividade.getAtividadeArquivoList()) {
				if (reg.getId() == null) {
					continue;
				}
				if (result == null) {
					result = new ArrayList<AtividadeArquivo>();
				}
				AtividadeArquivo atividadeArquivo = new AtividadeArquivo();
				atividadeArquivo.setId(reg.getId());
				atividadeArquivo.setAtividade(new Atividade(atividade.getId()));
				atividadeArquivo.setDataUpload(reg.getDataUpload());
				atividadeArquivo.setDescricao(reg.getDescricao());

				Arquivo arquivo = new Arquivo();
				arquivo.setId(reg.getArquivo().getId());
				arquivo.setNome(reg.getArquivo().getNome());
				arquivo.setTamanho(reg.getArquivo().getTamanho());
				arquivo.setTipo(reg.getArquivo().getTipo());
				atividadeArquivo.setArquivo(arquivo);

				result.add(atividadeArquivo);
			}
		}
		return result;
	}

	private List<AtividadeAssunto> fetchAtividadeAssuntoList(Atividade atividade) {
		List<AtividadeAssunto> result = null;
		if (!isEmpty(atividade.getAtividadeAssuntoList())) {
			for (AtividadeAssunto reg : atividade.getAtividadeAssuntoList()) {
				if (result == null) {
					result = new ArrayList<AtividadeAssunto>();
				}
				if (reg.getId() == null) {
					continue;
				}
				AtividadeAssunto atividadeAssunto = new AtividadeAssunto();
				atividadeAssunto.setId(reg.getId());
				atividadeAssunto.setAtividade(new Atividade(atividade.getId()));
				atividadeAssunto.setDescricao(reg.getDescricao());
				atividadeAssunto.setTransversal(reg.getTransversal());
				AssuntoAcao assuntoAcao = new AssuntoAcao();
				assuntoAcao.setId(reg.getAssuntoAcao().getId());
				assuntoAcao.setAcao(new Acao(reg.getAssuntoAcao().getAcao().getId(), reg.getAssuntoAcao().getAcao().getNome()));
				Assunto assuntoSalvo = reg.getAssuntoAcao().getAssunto();
				Assunto assunto = new Assunto(assuntoSalvo.getId(), assuntoSalvo.getNome());
				assuntoAcao.setAssunto(assunto);
				while ((assuntoSalvo = assuntoSalvo.getPai()) != null) {
					assunto.setPai(new Assunto(assuntoSalvo.getId(), assuntoSalvo.getNome()));
					assunto = assunto.getPai();
				}
				atividadeAssunto.setAssuntoAcao(assuntoAcao);
				result.add(atividadeAssunto);
			}
		}
		return result;
	}

	private List<AtividadePessoa> fetchAtividadePessoaList(Atividade atividade) {
		List<AtividadePessoa> result = null;
		if (!isEmpty(atividade.getAtividadePessoaList())) {
			for (AtividadePessoa reg : atividade.getAtividadePessoaList()) {
				if (result == null) {
					result = new ArrayList<AtividadePessoa>();
				}
				AtividadePessoa atividadePessoa = new AtividadePessoa();
				atividadePessoa.setId(reg.getId());
				atividadePessoa.setAtividade(new Atividade(atividade.getId()));
				atividadePessoa.setFuncao(reg.getFuncao());
				atividadePessoa.setPessoa(fetchPessoa(reg.getPessoa()));
				result.add(atividadePessoa);
			}
		}
		return result;
	}

	private List<AtividadeRestricao> fetchAtividadeRestricaoList(Atividade atividade) {
		List<AtividadeRestricao> result = null;
		return result;
	}

	private List<Ocorrencia> fetchOcorrenciaList(Atividade atividade) {
		List<Ocorrencia> result = null;
		if (!isEmpty(atividade.getOcorrenciaList())) {
			for (Ocorrencia reg : atividade.getOcorrenciaList()) {
				if (result == null) {
					result = new ArrayList<Ocorrencia>();
				}
				Ocorrencia ocorrencia = new Ocorrencia();
				ocorrencia.setId(reg.getId());
				ocorrencia.setAtividade(new Atividade(atividade.getId()));
				ocorrencia.setIncidente(reg.getIncidente());
				ocorrencia.setInicioSuspensao(reg.getInicioSuspensao());
				ocorrencia.setMotivoSuspensao(reg.getMotivoSuspensao());
				ocorrencia.setPercentualConclusao(reg.getPercentualConclusao());
				ocorrencia.setPrioridade(reg.getPrioridade());
				ocorrencia.setRegistro(reg.getRegistro());
				ocorrencia.setRelato(reg.getRelato());
				ocorrencia.setSituacao(reg.getSituacao());
				ocorrencia.setTerminoSuspensao(reg.getTerminoSuspensao());
				ocorrencia.setUsuario(fetchUsuario(reg.getUsuario()));
				result.add(ocorrencia);
			}
		}
		return result;
	}

	private Atividade fetchAtividadeRelacionamento(Atividade atividade) {
		Atividade result = null;
		if (!isNull(atividade)) {
			result = new Atividade();
			result.setId(atividade.getId());
			result.setCodigo(atividade.getCodigo());
			result.setInicio(atividade.getInicio());
			result.setSituacao(atividade.getSituacao());
			result.setMetodo(new Metodo(atividade.getMetodo().getId(), atividade.getMetodo().getNome()));
			result.setAtividadeAssuntoList(fetchAtividadeAssuntoList(atividade));
			result.setAtividadePessoaList(fetchAtividadePessoaList(atividade));
		}
		return result;
	}

	private List<AtividadeRelacionamento> fetchPrincipalAtividadeList(Atividade atividade) {
		List<AtividadeRelacionamento> result = null;
		if (!isEmpty(atividade.getPrincipalAtividadeList())) {
			for (AtividadeRelacionamento reg : atividade.getPrincipalAtividadeList()) {
				if (result == null) {
					result = new ArrayList<AtividadeRelacionamento>();
				}
				AtividadeRelacionamento atividadeRelacionamento = new AtividadeRelacionamento();
				atividadeRelacionamento.setId(reg.getId());
				atividadeRelacionamento.setAtividade(new Atividade(atividade.getId()));
				atividadeRelacionamento.setPrincipalAtividade(fetchAtividadeRelacionamento(reg.getPrincipalAtividade()));
				result.add(atividadeRelacionamento);
			}
		}
		return result;
	}

	private List<AtividadeRelacionamento> fetchSubatividadeList(Atividade atividade) {
		List<AtividadeRelacionamento> result = null;
		if (!isEmpty(atividade.getSubatividadeList())) {
			for (AtividadeRelacionamento reg : atividade.getSubatividadeList()) {
				if (result == null) {
					result = new ArrayList<AtividadeRelacionamento>();
				}
				AtividadeRelacionamento atividadeRelacionamento = new AtividadeRelacionamento();
				atividadeRelacionamento.setId(reg.getId());
				atividadeRelacionamento.setAtividade(fetch(reg.getAtividade()));
				atividadeRelacionamento.setPrincipalAtividade(new Atividade(atividade.getId()));
				result.add(atividadeRelacionamento);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> filtrarByDto(Dto dto) {
		AtividadeCadFiltroDto filtro = (AtividadeCadFiltroDto) dto;
		List<Map<String, Object>> result = getDao().restoreByDto(filtro);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> getAssuntoAcaoTransversalList(Integer assuntoAcaoId) {
		return getDao().getAssuntoAcaoTransversalList(assuntoAcaoId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> getAtividadeAssuntoAcaoList(Integer assuntoId) {
		return getDao().getAtividadeAssuntoAcaoList(assuntoId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> getAtividadeAssuntoList(AtividadeFinalidade finalidade) {
		return getDao().getAtividadeAssuntoList(finalidade);
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> getAssuntoAcaoFilhos(Integer assuntoId) {
		return getDao().getAssuntoAcaoFilhos(assuntoId);
	}

	private boolean isValido(Atividade entidade) {
		boolean result = true;
		if (isNull(entidade) || isNull(entidade.getAgendamentoTipo()) || isEmpty(entidade.getCodigo()) || isNull(entidade.getDiaTodo()) || isNull(entidade.getPublicoEstimado()) || isNull(entidade.getFinalidade()) || isNull(entidade.getFormato()) || isNull(entidade.getMetodo()) || isNull(entidade.getNatureza())
				|| isNull(entidade.getPrioridade()) || isNull(entidade.getRegistro()) || isNull(entidade.getSituacao()) || isNull(entidade.getUsuario()) || isEmpty(entidade.getAtividadeAssuntoList()) || isEmpty(entidade.getAtividadePessoaList())) {
			result = false;
		}
		if (result) {
			int demandante = 0;
			int executor = 0;
			for (AtividadePessoa ap : entidade.getAtividadePessoaList()) {
				if (AtividadePessoaFuncao.D.equals(ap.getFuncao())) {
					demandante++;
				} else if (AtividadePessoaFuncao.ER.equals(ap.getFuncao())) {
					executor++;
				}
			}
			if (demandante == 0 || executor == 0) {
				result = false;
			}
			if (entidade.getPublicoEstimado() < demandante) {
				entidade.setPublicoEstimado(demandante);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Atividade restore(Integer id) {
		Atividade result = fetch(super.restore(id));
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	@Transactional
	public Atividade save(@Valid Atividade entidade) {

		if (!isValido(entidade)) {
			throw new ServiceException("Informações basicas sobre a atividade não foram informadas");
		}

		// verificar se é um registro existente
		Atividade entidadeExistente = null;
		if (entidade.getId() != null && entidade.getId() > 0) {
			// recuperar o registro do banco de dados
			entidadeExistente = getDao().restore(entidade.getId());
			if (entidadeExistente == null) {
				throw new ServiceException("Este registro já foi excluído do banco de dados");
			}
			if (entidadeExistente.getAtividadeArquivoList() != null) {
				entidadeExistente.getAtividadeArquivoList().size();
			}
			if (entidadeExistente.getAtividadeAssuntoList() != null) {
				entidadeExistente.getAtividadeAssuntoList().size();
			}
			if (entidadeExistente.getAtividadePessoaList() != null) {
				entidadeExistente.getAtividadePessoaList().size();
			}
			if (entidadeExistente.getAtividadeRestricaoList() != null) {
				entidadeExistente.getAtividadeRestricaoList().size();
			}
			if (entidadeExistente.getOcorrenciaList() != null) {
				entidadeExistente.getOcorrenciaList().size();
			}
			if (entidadeExistente.getPrincipalAtividadeList() != null) {
				entidadeExistente.getPrincipalAtividadeList().size();
			}
			if (entidadeExistente.getSubatividadeList() != null) {
				entidadeExistente.getSubatividadeList().size();
			}
			getDao().deatach(entidadeExistente);
		}

		// salvar o registro principal
		getDao().save(entidade);

		Map<String, List> modificacoes;

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getAtividadeArquivoList(), entidadeExistente == null ? null : entidadeExistente.getAtividadeArquivoList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadeArquivoDao.delete((AtividadeArquivo) obj);
		}

		// preparar os registros para gravação
		if (entidade.getAtividadeArquivoList() != null) {
			for (AtividadeArquivo registro : entidade.getAtividadeArquivoList()) {
				if (isNull(registro.getArquivo()) || isNull(registro.getArquivo().getId())) {
					throw new ServiceException("Informações básicas sobre o arquivo não foram informadas");
				}
				registro.setAtividade(entidade);
				atividadeArquivoDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getAtividadeAssuntoList(), entidadeExistente == null ? null : entidadeExistente.getAtividadeAssuntoList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadeAssuntoDao.delete((AtividadeAssunto) obj);
		}

		// preparar os registros para gravação
		if (entidade.getAtividadeAssuntoList() != null) {
			for (AtividadeAssunto registro : entidade.getAtividadeAssuntoList()) {
				if (isNull(registro.getAssuntoAcao()) || isNull(registro.getAssuntoAcao().getId())) {
					throw new ServiceException("Informações básicas sobre o assunto/ação não foram informadas");
				}
				registro.setAtividade(entidade);
				atividadeAssuntoDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getAtividadePessoaList(), entidadeExistente == null ? null : entidadeExistente.getAtividadePessoaList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadePessoaDao.delete((AtividadePessoa) obj);
		}

		// preparar os registros para gravação
		if (entidade.getAtividadePessoaList() != null) {
			for (AtividadePessoa registro : entidade.getAtividadePessoaList()) {
				if (isNull(registro.getPessoa()) || isNull(registro.getPessoa().getId())) {
					throw new ServiceException("Informações básicas sobre o demandante/executor não foram informadas");
				}
				registro.setAtividade(entidade);
				atividadePessoaDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getAtividadeRestricaoList(), entidadeExistente == null ? null : entidadeExistente.getAtividadeRestricaoList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadeRestricaoDao.delete((AtividadeRestricao) obj);
		}

		// preparar os registros para gravação
		if (entidade.getAtividadeRestricaoList() != null) {
			for (AtividadeRestricao registro : entidade.getAtividadeRestricaoList()) {
				if (isNull(registro.getReferenciadaAtividade()) || isNull(registro.getReferenciadaAtividade().getId()) || isNull(registro.getRestricaoTempo())) {
					throw new ServiceException("Informações básicas sobre o restrição não foram informadas");
				}
				registro.setAtividade(entidade);
				atividadeRestricaoDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getOcorrenciaList(), entidadeExistente == null ? null : entidadeExistente.getOcorrenciaList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			ocorrenciaDao.delete((Ocorrencia) obj);
		}

		// preparar os registros para gravação
		if (entidade.getOcorrenciaList() != null) {
			for (Ocorrencia registro : entidade.getOcorrenciaList()) {
				if (isEmpty(registro.getRelato())) {
					throw new ServiceException("Informações básicas sobre a ocorrência não foram informadas");
				}
				registro.setAtividade(entidade);
				ocorrenciaDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getPrincipalAtividadeList(), entidadeExistente == null ? null : entidadeExistente.getPrincipalAtividadeList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadeRelacionamentoDao.delete((AtividadeRelacionamento) obj);
		}

		// preparar os registros para gravação
		if (entidade.getPrincipalAtividadeList() != null) {
			for (AtividadeRelacionamento registro : entidade.getPrincipalAtividadeList()) {
				if (isNull(registro.getPrincipalAtividade()) || isNull(registro.getPrincipalAtividade().getId())) {
					throw new ServiceException("Informações básicas sobre a atividade principal não foram informadas");
				}
				registro.setAtividade(entidade);
				atividadeRelacionamentoDao.save(registro);
			}
		}

		// identificar as modificações ocorridas
		modificacoes = getModificacoes(entidade.getSubatividadeList(), entidadeExistente == null ? null : entidadeExistente.getSubatividadeList());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			atividadeRelacionamentoDao.delete((AtividadeRelacionamento) obj);
		}

		// preparar os registros para gravação
		if (entidade.getSubatividadeList() != null) {
			for (AtividadeRelacionamento registro : entidade.getSubatividadeList()) {
				if (isNull(registro.getAtividade())) {
					throw new ServiceException("Informações básicas sobre a subatividade não foram informadas");
				}
				registro.setPrincipalAtividade(entidade);
				registro.setAtividade(save(registro.getAtividade()));
				atividadeRelacionamentoDao.save(registro);
			}
		}

		return restore(entidade.getId());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Integer> getAssuntoIdDescendencia(Integer assuntoId) {
		return getDao().getAssuntoIdDescendencia(assuntoId);
	}

}