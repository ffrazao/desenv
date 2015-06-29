package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.comum.UtilitarioData;
import gov.emater.aterweb.dao.PessoaDao;
import gov.emater.aterweb.dao.PessoaGrupoDao;
import gov.emater.aterweb.dao.PessoaGrupoTipoDao;
import gov.emater.aterweb.dao.PessoaRelacionamentoDao;
import gov.emater.aterweb.dao.RelacionamentoConfiguracaoDao;
import gov.emater.aterweb.dao.RelacionamentoDao;
import gov.emater.aterweb.dao.RelacionamentoFuncaoDao;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.PessoaRelacionamento;
import gov.emater.aterweb.model.Relacionamento;
import gov.emater.aterweb.model.RelacionamentoConfiguracao;
import gov.emater.aterweb.model.RelacionamentoFuncao;
import gov.emater.aterweb.model.RelacionamentoTipo;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.model.domain.PessoaGrupoNivelGestao;
import gov.emater.aterweb.model.domain.RelacionamentoFuncaoParticipacao;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.mvc.dto.PessoaGrupoCadFiltroDto;
import gov.emater.aterweb.service.ConstantesService;
import gov.emater.aterweb.service.PessoaGrupoService;
import gov.emater.aterweb.service.PessoaService;
import gov.emater.aterweb.service.ServiceException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaGrupoServiceImpl extends CrudServiceImpl<PessoaGrupo, Integer, PessoaGrupoDao> implements PessoaGrupoService {

	@Autowired
	private ConstantesService constantesService;

	@Autowired
	private PessoaDao pessoaDao;

	@Autowired
	private PessoaGrupoTipoDao pessoaGrupoTipoDao;

	@Autowired
	private PessoaRelacionamentoDao pessoaRelacionamentoDao;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private RelacionamentoConfiguracaoDao relacionamentoConfiguracaoDao;

	@Autowired
	private RelacionamentoDao relacionamentoDao;

	@Autowired
	private RelacionamentoFuncaoDao relacionamentoFuncaoDao;

	@Autowired
	public PessoaGrupoServiceImpl(PessoaGrupoDao dao) {
		super(dao);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		List<Integer> arvoreDescendencia = getArvoreDescendencia(id, "id", "pai");
		for (Integer i : arvoreDescendencia) {
			// excluir associacoes ao grupo social
			List<PessoaRelacionamento> pessoaRelacionamentoList = pessoaRelacionamentoDao.restore(new PessoaRelacionamento(new PessoaGrupo(i)));
			if (pessoaRelacionamentoList != null) {
				for (PessoaRelacionamento pr : pessoaRelacionamentoList) {
					relacionamentoDao.delete(pr.getRelacionamento());
				}
			}
			super.deleteById(i);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> detalhar(Integer id) {
		List<?> result = null;
		return result;
	}

	private List<PessoaGrupo> fetch(List<?> lista, PessoaGrupoCadFiltroDto filtro) {
		List<PessoaGrupo> result = null;
		if (lista != null) {
			for (Object r : lista) {
				Object[] regPg = (Object[]) r;
				PessoaGrupo grupo = new PessoaGrupo();
				grupo.setNome((String) regPg[0]);
				grupo.setApelidoSigla((String) regPg[1]);
				grupo.setId((Integer) regPg[2]);
				grupo.setPessoaGrupoTipo(new PessoaGrupoTipo((Integer) regPg[3]));
				grupo.setPai(new PessoaGrupo((Integer) regPg[4]));
				grupo.setNivelGestao(PessoaGrupoNivelGestao.valueOf(((Character) regPg[5]).toString()));
				if (filtro != null && filtro.getIdsFiltrados() != null) {
					grupo.setFilhos(fetch(getDao().restoreByDto(filtro, grupo.getId()), filtro));
				}
				if (result == null) {
					result = new ArrayList<PessoaGrupo>();
				}
				result.add(grupo);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private PessoaGrupo fetch(PessoaGrupo pessoa) {
		if (pessoa != null) {
			((PessoaGrupo) pessoa).setFilhos(null);
			if (((PessoaGrupo) pessoa).getPai() != null) {
				PessoaGrupo ps = ((PessoaGrupo) pessoa).getPai();
				((PessoaGrupo) pessoa).setPai(new PessoaGrupo(ps.getId(), ps.getNome(), ps.getApelidoSigla()));
			}
			pessoaService.setFotoPerfil(pessoa);

			pessoa.setArquivoList(null);
			pessoa.setPessoaMeioContatos(null);
			pessoa.setPublicoAlvo(null);
			pessoa.setUsuario(null);
			pessoa.setPessoaRelacionamentos(null);
			pessoa.setPessoaGrupoTipo(new PessoaGrupoTipo(pessoa.getPessoaGrupoTipo().getId()));
			List<PessoaRelacionamento> pRel = null;

			List<Map<String, Object>> temp = (List<Map<String, Object>>) pessoaRelacionamentoDao.restorePessoaPorRelacionamentoTipo(pessoa, constantesService.getRelacionamentoTipo(RelacionamentoTipo.Codigo.GESTAO_GRUPO_SOCIAL));
			if (temp != null) {
				for (Map<String, Object> ent : temp) {
					if (!((Integer) ent.get("pessoa_id")).equals(pessoa.getId())) {
						PessoaRelacionamento item = new PessoaRelacionamento();
						item.setId((Integer) ent.get("id"));
						Pessoa p = pessoaDao.restore((Integer) ent.get("pessoa_id"));
						Pessoa p2 = null;
						try {
							p2 = p.getClass().getConstructor(Integer.class, String.class, String.class).newInstance(p.getId(), p.getNome(), p.getApelidoSigla());
						} catch (Exception e) {
							e.printStackTrace();
						}
						item.setPessoa(p2);
						item.setRelacionamento(new Relacionamento((Integer) ent.get("relacionamento_id"), new RelacionamentoTipo((Integer) ent.get("relacionamento_tipo_id")), UtilitarioData.getInstance().sqlTimestampToCalendar((Timestamp) ent.get("inicio")), UtilitarioData
								.getInstance().sqlTimestampToCalendar((Timestamp) ent.get("termino"))));
						item.setRelacionamentoFuncao(new RelacionamentoFuncao((Integer) ent.get("relacionamento_funcao_id")));
						item.setNome((String) ent.get("nome"));
						item.setCpfCnpj((String) ent.get("cpf_cnpj"));
						if (ent.get("pode_modificar") != null) {
							item.setPodeModificar(Confirmacao.valueOf(ent.get("pode_modificar").toString()));
						}
						if (ent.get("proprietario") != null) {
							item.setProprietario(Confirmacao.valueOf(ent.get("proprietario").toString()));
						}
						if (pRel == null) {
							pRel = new ArrayList<PessoaRelacionamento>();
						}
						pRel.add(item);
					}
				}
			}
			pessoa.setPessoaRelacionamentos(pRel);
		}
		return pessoa;
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> filtrarByDto(Dto dto) {
		PessoaGrupoCadFiltroDto filtro = (PessoaGrupoCadFiltroDto) dto;
		filtro.setIdsFiltrados(null);
		List<?> result = fetch(getDao().restoreByDto(filtro, null), filtro);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaGrupo restore(Integer id) {
		PessoaGrupo result = fetch(super.restore(id));
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> restoreFilhos(Integer id) {
		List<?> result = fetch(getDao().restoreFilhos(id), null);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PessoaGrupoTipo> restorePessoaGrupoTipo(PessoaGrupoTipo pessoaGrupoTipo) {
		return pessoaGrupoTipoDao.restore();
	}

	@Override
	@Transactional
	public PessoaGrupo save(PessoaGrupo pessoaGrupo) {
		if (pessoaGrupo.getPessoaRelacionamentos() == null || pessoaGrupo.getPessoaRelacionamentos().size() == 0) {
			throw new ServiceException("Não pode haver grupo sem gestores");
		}
		pessoaGrupo.setPessoaGrupoTipo(constantesService.getPersonalizadoPessoaGrupoTipo());
		pessoaGrupo.setPublicoAlvoConfirmacao(Confirmacao.N);

		// recuperar a instância do grupo social pai
		if (pessoaGrupo.getPai() == null || pessoaGrupo.getPai().getId() == null) {
			pessoaGrupo.setPai(null);
		} else {
			PessoaGrupo pai = getDao().restore(pessoaGrupo.getPai().getId());
			if (pai == null) {
				throw new ServiceException("Grupo Social principal inválido");
			}
			pessoaGrupo.setPai(pai);
		}

		// salvar o registro do Grupo Social
		PessoaGrupo pessoaGrupoAnterior = pessoaGrupo.getId() == null ? null : fetch(getDao().restore(pessoaGrupo.getId()));
		getDao().deatach(pessoaGrupoAnterior);
		super.save(pessoaGrupo);

		// atualizar pessoas relacionadas
		RelacionamentoTipo relacionamentoTipo = constantesService.getRelacionamentoTipo(RelacionamentoTipo.Codigo.GESTAO_GRUPO_SOCIAL);
		boolean temProprietario = false;
		for (PessoaRelacionamento relacionador : pessoaGrupo.getPessoaRelacionamentos()) {

			// verificar se o grupo social tem proprietario
			if (Confirmacao.S.equals(relacionador.getProprietario())) {
				if (temProprietario) {
					throw new ServiceException("Mais de um proprietario definido para o grupo");
				}
				temProprietario = true;
				relacionador.setPodeModificar(Confirmacao.S);
			}

			// verificar se é um novo relacionamento
			Pessoa pessoaRelacionador = relacionador.getPessoa();
			if (pessoaRelacionador == null || pessoaRelacionador.getId() == null) {
				throw new ServiceException("Gestor não informado corretamente (faltou o identificador)");
			}
			pessoaRelacionador = pessoaDao.restore(pessoaRelacionador.getId());

			Relacionamento relacionamento = relacionador.getRelacionamento();

			if (relacionamento == null) {
				// novos relacionamentos não enviam este objeto, então deve ser
				// criado aqui um novo objeto nestes casos
				relacionamento = new Relacionamento();
			}

			// verificar as datas
			if (relacionamento.getInicio() != null && relacionamento.getTermino() != null) {
				if (relacionamento.getInicio().after(relacionamento.getTermino())) {
					throw new ServiceException("Intervalo de gestão inválido");
				}
			}

			boolean pessoaJaRelacionada = false;
			if (pessoaGrupoAnterior != null && pessoaGrupoAnterior.getPessoaRelacionamentos() != null) {
				for (PessoaRelacionamento pr : pessoaGrupoAnterior.getPessoaRelacionamentos()) {
					if (pr.getRelacionamento() != null && !pr.getRelacionamento().getId().equals(relacionamento.getId()) && pr.getPessoa().getId().equals(pessoaRelacionador.getId())) {
						pessoaJaRelacionada = true;
						break;
					}
				}
			}
			if (!pessoaJaRelacionada) {
				int count = 0;
				for (PessoaRelacionamento pr : pessoaGrupo.getPessoaRelacionamentos()) {
					if (pessoaRelacionador.getId().equals(pr.getPessoa().getId())) {
						if (++count > 1) {
							pessoaJaRelacionada = true;
							break;
						}
					}
				}
			}
			if (pessoaJaRelacionada) {
				throw new ServiceException("Pessoa relacionada mais de uma vez ao grupo social");
			}

			// verificar se é um novo relacionamento
			if (relacionamento.getId() == null) {
				// verificar se a pessoa já foi relacionada
				relacionamento = relacionamentoDao.save(new Relacionamento(relacionamentoTipo, relacionamento.getInicio(), relacionamento.getTermino()));

				// preparar as instancias para salvar
				PessoaRelacionamento relacionado = new PessoaRelacionamento(pessoaDao.restore(pessoaGrupo.getId()), relacionamento);
				relacionado.setProprietario(Confirmacao.N);
				relacionado.setPodeModificar(Confirmacao.N);
				for (RelacionamentoConfiguracao configuracao : relacionamentoConfiguracaoDao.restore(new RelacionamentoConfiguracao(relacionamentoTipo))) {
					if (configuracao.getRelacionadorFuncao().getParticipacao() == RelacionamentoFuncaoParticipacao.A) {
						relacionador.setRelacionamentoFuncao(configuracao.getRelacionadorFuncao());
						relacionado.setRelacionamentoFuncao(configuracao.getRelacionadoFuncao());
						break;
					}
				}
				relacionado.setRelacionamento(relacionamento);
				pessoaRelacionamentoDao.save(relacionado);
			} else {
				relacionamento = relacionamentoDao.save(relacionamento);
			}
			if (relacionador.getRelacionamentoFuncao() == null || relacionador.getRelacionamentoFuncao().getId() == null) {
				throw new ServiceException("Configuração inválida");
			}
			relacionador.setRelacionamento(relacionamento);
			relacionador = pessoaRelacionamentoDao.save(relacionador);
		}

		if (!temProprietario) {
			throw new ServiceException("Não foi definido um proprietario do grupo");
		}

		// excluir os relacionamentos que não existem mais
		if (pessoaGrupoAnterior != null && pessoaGrupoAnterior.getPessoaRelacionamentos() != null) {
			for (PessoaRelacionamento anterior : pessoaGrupoAnterior.getPessoaRelacionamentos()) {
				if (anterior.getRelacionamento() != null) {
					boolean encontrou = false;
					for (PessoaRelacionamento novo : pessoaGrupo.getPessoaRelacionamentos()) {
						if (novo.getRelacionamento().getId().equals(anterior.getRelacionamento().getId())) {
							encontrou = true;
							break;
						}
					}
					if (!encontrou) {
						relacionamentoDao.delete(anterior.getRelacionamento());
					}
				}
			}
		}

		return restore(pessoaGrupo.getId());
	}

}