// TODO Se o endereco já for uma propriedade rural então tomar cuidado para não estragar a informaçao
//      nao pode mudar de propriedade rural para náo propriedade rural aqui nesta pagina

package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.ArquivoDao;
import gov.emater.aterweb.dao.ExploracaoDao;
import gov.emater.aterweb.dao.MeioContatoEmailDao;
import gov.emater.aterweb.dao.MeioContatoEnderecoDao;
import gov.emater.aterweb.dao.MeioContatoTelefonicoDao;
import gov.emater.aterweb.dao.PessoaArquivoDao;
import gov.emater.aterweb.dao.PessoaDao;
import gov.emater.aterweb.dao.PessoaGrupoDao;
import gov.emater.aterweb.dao.PessoaMeioContatoDao;
import gov.emater.aterweb.dao.PessoaRelacionamentoDao;
import gov.emater.aterweb.dao.PublicoAlvoDao;
import gov.emater.aterweb.dao.RelacionamentoConfiguracaoDao;
import gov.emater.aterweb.dao.RelacionamentoDao;
import gov.emater.aterweb.dao.RelacionamentoFuncaoDao;
import gov.emater.aterweb.dao.RelacionamentoTipoDao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.model.Exploracao;
import gov.emater.aterweb.model.MeioContatoEmail;
import gov.emater.aterweb.model.MeioContatoEndereco;
import gov.emater.aterweb.model.MeioContatoTelefonico;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaArquivo;
import gov.emater.aterweb.model.PessoaFisica;
import gov.emater.aterweb.model.PessoaGrupoBaciaHidrograficaVi;
import gov.emater.aterweb.model.PessoaGrupoCidadeVi;
import gov.emater.aterweb.model.PessoaGrupoComunidadeVi;
import gov.emater.aterweb.model.PessoaGrupoEstadoVi;
import gov.emater.aterweb.model.PessoaGrupoMunicipioVi;
import gov.emater.aterweb.model.PessoaGrupoPaisVi;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.PessoaGrupoTipo.Codigo;
import gov.emater.aterweb.model.PessoaJuridica;
import gov.emater.aterweb.model.PessoaMeioContato;
import gov.emater.aterweb.model.PessoaRelacionamento;
import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.model.Relacionamento;
import gov.emater.aterweb.model.RelacionamentoConfiguracao;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.model.domain.MeioContatoTipo;
import gov.emater.aterweb.model.domain.PessoaTipo;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.mvc.dto.PessoaCadFiltroDto;
import gov.emater.aterweb.service.ConstantesService;
import gov.emater.aterweb.service.PessoaService;
import gov.emater.aterweb.service.ServiceException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaServiceImpl extends CrudServiceImpl<Pessoa, Integer, PessoaDao> implements PessoaService {

	@Autowired
	private ConstantesService constantesService;

	@Autowired
	private ArquivoDao arquivoDao;

	@Autowired
	private ExploracaoDao exploracaoDao;

	@Autowired
	private MeioContatoEmailDao meioContatoEmailDao;

	@Autowired
	private MeioContatoEnderecoDao meioContatoEnderecoDao;

	@Autowired
	private MeioContatoTelefonicoDao meioContatoTelefonicoDao;

	@Autowired
	private PessoaArquivoDao pessoaArquivoDao;

	@Autowired
	private PessoaGrupoDao pessoaGrupoDao;

	@Autowired
	private PessoaMeioContatoDao pessoaMeioContatoDao;

	@Autowired
	private PessoaRelacionamentoDao pessoaRelacionamentoDao;

	@Autowired
	private PublicoAlvoDao publicoAlvoDao;

	@Autowired
	private RelacionamentoConfiguracaoDao relacionamentoConfiguracaoDao;

	@Autowired
	private RelacionamentoDao relacionamentoDao;

	@Autowired
	private RelacionamentoFuncaoDao relacionamentoFuncaoDao;

	@Autowired
	private RelacionamentoTipoDao relacionamentoTipoDao;

	@Autowired
	public PessoaServiceImpl(PessoaDao dao) {
		super(dao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> detalhar(Integer id) {
		// variáveis de apoio
		List<Map<String, Object>> result = null;

		// captar público alvo
		Pessoa pessoa = getDao().restore(id);

		if (pessoa != null) {
			result = new ArrayList<Map<String, Object>>();
			Map<String, Object> linha = new HashMap<String, Object>();

			linha.put("publicoAlvoConfirmacao", pessoa.getPublicoAlvoConfirmacao().name());

			// verificar se a pessoa possui meios de contato
			if (pessoa.getPessoaMeioContatos() != null) {
				// percorrer todos os meios de contato
				for (PessoaMeioContato pessoaMeioContato : pessoa.getPessoaMeioContatos()) {
					// listar somente os meios de contato principais
					if (pessoaMeioContato.getOrdem().equals(1)) {
						linha.put(pessoaMeioContato.getMeioContato().getMeioContatoTipo().name(), pessoaMeioContato.getMeioContato());
						result.add(linha);
					}
				}
			}
		}

		// TODO fazer consulta para devolver o endereço da principal foto da
		// pessoa

		return result;
	}

	private void fetch(Pessoa pessoa) {
		if (pessoa != null) {
			if (pessoa.getPessoaMeioContatos() != null) {
				for (PessoaMeioContato pmc : pessoa.getPessoaMeioContatos()) {
					if (pmc.getMeioContato() != null && pmc.getMeioContato().getMeioContatoTipo() != null) {
						switch (pmc.getMeioContato().getMeioContatoTipo()) {
						case EMA:
							((MeioContatoEmail) pmc.getMeioContato()).getId();
							break;
						case END:
							PropriedadeRural pr = ((MeioContatoEndereco) pmc.getMeioContato()).getPropriedadeRural();
							// FIXME
							// if (pr != null &&
							// pr.getPropriedadeRuralPessoaGrupoList() != null)
							// {
							// pr.getPropriedadeRuralPessoaGrupoList().size();
							// }
							PessoaGrupoCidadeVi cidade = ((MeioContatoEndereco) pmc.getMeioContato()).getPessoaGrupoCidadeVi();
							PessoaGrupoMunicipioVi municipio = null;
							PessoaGrupoEstadoVi estado = null;
							PessoaGrupoPaisVi pais = null;
							if (cidade != null) {
								municipio = cidade.getPessoaGrupoMunicipioVi();
							}
							if (municipio != null) {
								estado = municipio.getPessoaGrupoEstadoVi();
							}
							if (estado != null) {
								pais = estado.getPessoaGrupoPaisVi();
								pais.getId();
							}
							break;
						case TEL:
							((MeioContatoTelefonico) pmc.getMeioContato()).getId();
							break;
						default:
							break;
						}
					}
				}
			}
			if (pessoa.getPessoaRelacionamentos() != null) {
				List<PessoaRelacionamento> pessoaRelacionamentoList = new ArrayList<PessoaRelacionamento>();
				for (PessoaRelacionamento pessoaRelacionamento : pessoa.getPessoaRelacionamentos()) {
					List<PessoaRelacionamento> r = pessoaRelacionamentoDao.restore(new PessoaRelacionamento(pessoaRelacionamento.getRelacionamento()));
					for (PessoaRelacionamento pr : r) {
						if (pr.getPessoa() != null && !pr.getPessoa().getId().equals(pessoa.getId())) {
							pessoaRelacionamentoList.add(pr);
						}
					}
				}
				pessoa.setPessoaRelacionamentos(pessoaRelacionamentoList);
			}
			setFotoPerfil(pessoa);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> filtrarByDto(Dto dto) {

		PessoaCadFiltroDto filtro = (PessoaCadFiltroDto) dto;

		if (filtro.getNome() != null) {
			filtro.setNome(filtro.getNome().trim().replaceAll("\\s+", " ").replaceAll("\\s;", ";").replaceAll(";\\s", ";").replaceAll("\\s", "%"));
		}

		List<Map<String, Object>> result = getDao().restoreByDto(filtro);

		if (result != null) {
			for (Map<String, Object> linha : result) {
				Integer id = (Integer) linha.get("id");
				PessoaTipo pessoaTipo = (PessoaTipo) linha.get("pessoaTipo");

				Pessoa pessoa = null;
				if (pessoaTipo.equals(PessoaTipo.PF)) {
					pessoa = new PessoaFisica();
					linha.put("@class", "gov.emater.aterweb.model.PessoaFisica");
				} else if (pessoaTipo.equals(PessoaTipo.PJ)) {
					pessoa = new PessoaJuridica();
					linha.put("@class", "gov.emater.aterweb.model.PessoaJuridica");
				}
				pessoa.setId(id);

				// recuperar a foto do perfil da pessoa
				linha.put("fotoPerfil", pessoaArquivoDao.getFotoPerfil(id));
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Pessoa restore(Integer id) {
		Pessoa result = super.restore(id);

		// restaurar dados subjacentes
		fetch(result);

		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	@Transactional
	public Pessoa save(@Valid Pessoa entidade) {
		if (entidade == null || isEmpty(entidade.getNome()) || entidade.getPessoaTipo() == null) {
			throw new ServiceException("Informações basicas da pessoa não foram informadas");
		}
		if (PessoaTipo.PF.equals(entidade.getPessoaTipo()) && ((PessoaFisica) entidade).getSexo() == null) {
			throw new ServiceException("Informações basicas da pessoa física não foram informadas");
		}

		// verificar se é um registro existente
		Pessoa entidadeSalva = null;
		if (entidade.getId() != null && entidade.getId() > 0) {
			// recuperar o registro do banco de dados
			entidadeSalva = getDao().restore(entidade.getId());
			if (entidadeSalva == null) {
				throw new ServiceException("Este registro já foi excluído do banco de dados");
			}
			// restaurar os dados que não serão modificados por este codigo
			if (entidadeSalva.getUsuario() != null) {
				entidade.setUsuario(entidadeSalva.getUsuario());
				entidade.getUsuario().setPessoa(entidade);
			}
		}

		// identificar as modificações ocorridas nos meios de contato
		Map<String, List> modificacoes = getModificacoes(entidade.getPessoaMeioContatos(), entidadeSalva == null ? null : entidadeSalva.getPessoaMeioContatos());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			pessoaMeioContatoDao.delete((PessoaMeioContato) obj);
		}

		// preparar o registro para gravação
		if (entidade.getPessoaMeioContatos() != null) {
			int ordem[] = new int[MeioContatoTipo.values().length];
			List<Integer> ids = new ArrayList<Integer>();
			for (PessoaMeioContato pmc : entidade.getPessoaMeioContatos()) {
				// definir a pessoa do endereço
				pmc.setPessoa(entidade);

				// criticar os dados
				if (pmc.getMeioContato() == null || pmc.getMeioContato().getMeioContatoTipo() == null) {
					throw new ServiceException("Dados de Meio de Contato inválidos");
				}
				switch (pmc.getMeioContato().getMeioContatoTipo()) {
				case EMA:
					pmc.setMeioContato(saveEmail((MeioContatoEmail) pmc.getMeioContato()));
					break;
				case END:
					pmc.setMeioContato(saveEndereco((MeioContatoEndereco) pmc.getMeioContato()));
					break;
				case TEL:
					pmc.setMeioContato(saveTelefonico((MeioContatoTelefonico) pmc.getMeioContato()));
					break;
				default:
					throw new ServiceException("Dados de Meio de Contato inválidos, tipo nao informado");
				}
				for (Integer id : ids) {
					if (pmc.getMeioContato().getId().equals(id)) {
						throw new ServiceException("Meio de Contato cadastrado mais de uma vez");
					}
				}
				ids.add(pmc.getMeioContato().getId());
				// definir a ordem do meio de contato
				pmc.setOrdem(++ordem[pmc.getMeioContato().getMeioContatoTipo().ordinal()]);
			}
		}

		List<PessoaArquivo> pessoaArquivoList = entidade.getArquivoList();

		// salvar o registro principal
		entidade = super.save(entidade);

		if (Confirmacao.S.equals(entidade.getPublicoAlvoConfirmacao())) {
			if (entidade.getPublicoAlvo() == null) {
				throw new ServiceException("Informações de Publico Alvo nao fornecida");
			}
			// entidade.getPublicoAlvo(). setId(entidade.getId());
			entidade.getPublicoAlvo().setPessoa(entidade);
			publicoAlvoDao.save(entidade.getPublicoAlvo());
		}

		// salvar todos os meios de contato
		if (entidade.getPessoaMeioContatos() != null) {
			for (PessoaMeioContato pmc : entidade.getPessoaMeioContatos()) {
				// atribuir o registro à pessoa
				pmc.setPessoa(entidade);
				if (pmc.getId() != null) {
					List<Exploracao> exploracaoList = exploracaoDao.restore(new Exploracao(pmc));
					if (exploracaoList != null && exploracaoList.size() == 1)
						pmc.setExploracao(exploracaoList.get(0));
				}
				pessoaMeioContatoDao.save(pmc);
			}
		}

		// salvar os arquivos da pessoa
		entidade.setArquivoList(pessoaArquivoList);
		if (entidade.getArquivoList() != null) {
			for (PessoaArquivo pa : entidade.getArquivoList()) {
				pa.setPessoa(entidade);
				pa.setArquivo(arquivoDao.restore(pa.getArquivo().getId()));
				pessoaArquivoDao.save(pa);
			}
		}

		// salvar os relacionamentos da pessoa
		modificacoes = getModificacoes(entidade.getPessoaRelacionamentos(), entidadeSalva == null ? null : entidadeSalva.getPessoaRelacionamentos());

		// descartar os registros que foram excluidos
		for (Object obj : modificacoes.get(LISTA_REMOVIDO)) {
			PessoaRelacionamento pessoaRelacionamento = (PessoaRelacionamento) obj;
			if (pessoaRelacionamento.getRelacionamento() != null) {
				List<PessoaRelacionamento> pessoaRelacionamentoList = pessoaRelacionamentoDao.restore(new PessoaRelacionamento(pessoaRelacionamento.getRelacionamento()));
				for (PessoaRelacionamento deleta : pessoaRelacionamentoList) {
					pessoaRelacionamentoDao.delete(deleta);
				}
			}
		}

		// TODO salvar os dados dos relacionamentos
		for (Object obj : modificacoes.get(LISTA_INSERIDO)) {
			// preparar as pessoas do relacionamento
			PessoaRelacionamento pessoaRelacionador = new PessoaRelacionamento();
			PessoaRelacionamento pessoaRelacionado = (PessoaRelacionamento) obj;

			// restaurar dados essenciais
			pessoaRelacionado.getRelacionamento().setRelacionamentoTipo(relacionamentoTipoDao.restore(pessoaRelacionado.getRelacionamento().getRelacionamentoTipo().getId()));
			pessoaRelacionado.setRelacionamentoFuncao(relacionamentoFuncaoDao.restore(pessoaRelacionado.getRelacionamentoFuncao().getId()));

			// construir a unidade do relacionamento
			Relacionamento relacionamento = relacionamentoDao.create(new Relacionamento(pessoaRelacionado.getRelacionamento().getRelacionamentoTipo()));

			// captar a funcao no relacionamento oposta
			RelacionamentoConfiguracao relacionamentoConfiguracao = relacionamentoConfiguracaoDao.restore(new RelacionamentoConfiguracao(pessoaRelacionado.getRelacionamento().getRelacionamentoTipo(), pessoaRelacionado.getRelacionamentoFuncao())).get(0);

			// construir dados do relacionador
			pessoaRelacionador.setRelacionamentoFuncao(relacionamentoConfiguracao.getRelacionadorFuncao());
			pessoaRelacionador.setRelacionamento(relacionamento);
			pessoaRelacionador.setPessoa(entidade);
			pessoaRelacionador.setNome(entidade.getNome());
			if (entidade instanceof PessoaFisica) {
				pessoaRelacionador.setCpfCnpj(((PessoaFisica) entidade).getCpf());
			} else if (entidade instanceof PessoaJuridica) {
				pessoaRelacionador.setCpfCnpj(((PessoaJuridica) entidade).getCnpj());
			} else {
				pessoaRelacionador.setCpfCnpj(null);
			}

			// construir dados do relacionado
			pessoaRelacionado.setRelacionamento(relacionamento);
			if (pessoaRelacionado.getPessoa() != null && pessoaRelacionado.getPessoa().getId() != null) {
				Pessoa relacionado = getDao().restore(pessoaRelacionado.getPessoa().getId());
				pessoaRelacionado.setPessoa(relacionado);
				pessoaRelacionado.setNome(relacionado.getNome());
				// atualizar campos Fake
				if (relacionado instanceof PessoaFisica) {
					pessoaRelacionado.setCpfCnpj(((PessoaFisica) relacionado).getCpf());
				} else if (relacionado instanceof PessoaJuridica) {
					pessoaRelacionado.setCpfCnpj(((PessoaJuridica) relacionado).getCnpj());
				} else {
					pessoaRelacionado.setCpfCnpj(null);
				}
			} else if (pessoaRelacionado.getNome() == null || pessoaRelacionado.getNome().trim().length() == 0) {
				throw new ServiceException("Nome do relacionado nao pode ser nulo");
			}

			pessoaRelacionamentoDao.create(pessoaRelacionador);
			pessoaRelacionamentoDao.create(pessoaRelacionado);
		}

		return restore(entidade.getId());
	}

	@Transactional
	private MeioContatoEmail saveEmail(@Valid MeioContatoEmail entidade) {
		// criticar os dados informados
		if (entidade == null) {
			throw new ServiceException("Dados invalidos");
		}
		if (entidade.getEmail() == null || entidade.getEmail().trim().length() == 0) {
			throw new ServiceException("Email não informado");
		}
		entidade.setMeioContatoTipo(MeioContatoTipo.EMA);
		List<MeioContatoEmail> entidadeSalva = meioContatoEmailDao.restore(new MeioContatoEmail(entidade.getEmail()));
		if (entidadeSalva != null && entidadeSalva.size() == 1) {
			entidade = entidadeSalva.get(0);
		}
		return meioContatoEmailDao.save(entidade);
	}

	@Override
	@Transactional
	public MeioContatoEndereco saveEndereco(@Valid MeioContatoEndereco entidade) {

		// INICIO critica dos dados
		if (entidade == null) {
			throw new ServiceException("Dados invalidos");
		}
		if (entidade.getLogradouro() == null || entidade.getLogradouro().trim().length() == 0) {
			throw new ServiceException("Endereço não informado");
		}
		if (entidade.getPessoaGrupoCidadeVi() == null || entidade.getPessoaGrupoCidadeVi().getId() == null) {
			throw new ServiceException("A Cidade do Endereço não pode ser nula");
		}

		// verificar se ja existe o endereco
		MeioContatoEndereco entidadeSalva = null;
		if (entidade.getId() != null && entidade.getId() > 0) {
			entidadeSalva = meioContatoEnderecoDao.restore(entidade.getId());
			if (entidadeSalva == null) {
				throw new ServiceException("Este registro já foi excluído do banco de dados");
			}
		}

		List<MeioContatoEndereco> pesquisaEndereco = null;

		// verificar se há outro endereço com a mesma descrição e cidade
		pesquisaEndereco = meioContatoEnderecoDao.restore(new MeioContatoEndereco(entidade.getLogradouro(), entidade.getPessoaGrupoCidadeVi()));
		if (pesquisaEndereco != null) {
			if ((pesquisaEndereco.size() == 1 && !pesquisaEndereco.get(0).getId().equals(entidade.getId())) || pesquisaEndereco.size() > 1) {
				throw new ServiceException(String.format("Endereço e cidade [%s, %d] já cadastrado! Informe ao administrador do sistema", entidade.getLogradouro(), entidade.getPessoaGrupoCidadeVi().getId()));
			}
		}
		// verificar se há outro endereço com as mesmas coordenados latitude e
		// longitude
		if (entidade.getLatitude() != null && entidade.getLongitude() != null) {
			pesquisaEndereco = meioContatoEnderecoDao.restore(new MeioContatoEndereco(entidade.getLatitude(), entidade.getLongitude()));
			if (pesquisaEndereco != null) {
				if ((pesquisaEndereco.size() == 1 && !pesquisaEndereco.get(0).getId().equals(entidade.getId())) || pesquisaEndereco.size() > 1) {
					throw new ServiceException(String.format("Longitude e latitude [%.12f, %.12f] já cadastrado! Informe ao administrador do sistema", entidade.getLongitude(), entidade.getLatitude()));
				}
			}
		}
		// FIM critica dos dados

		// INICIO preparar a informação
		entidade.setMeioContatoTipo(MeioContatoTipo.END);
		// FIXME a linha abaixo deve ser descomentada e revista
		// entidade.setPessoaGrupoCidadeVi((PessoaGrupoCidadeVi)
		// pessoaGrupoDao.restore(entidade.getPessoaGrupoCidadeVi().getId()));
		if (entidade.getPropriedadeRuralConfirmacao() == null) {
			entidade.setPropriedadeRuralConfirmacao(Confirmacao.N);
		}

		// preparar pessoa meio contato
		if (entidade.getPessoaMeioContatoList() != null) {
			for (PessoaMeioContato pmc : entidade.getPessoaMeioContatoList()) {
				pmc.setMeioContato(entidade);
				if (pmc.getExploracao() != null) {
					pmc.getExploracao().setPessoaMeioContato(pmc);
				}
			}
		}

		// salvar o endereço
		return meioContatoEnderecoDao.save(entidade);
	}

	@Transactional
	private MeioContatoTelefonico saveTelefonico(@Valid MeioContatoTelefonico entidade) {
		// criticar os dados informados
		if (entidade == null) {
			throw new ServiceException("Dados invalidos");
		}
		if (entidade.getNumero() == null || entidade.getNumero().trim().length() == 0) {
			throw new ServiceException("Número do telefone não informado");
		}
		entidade.setMeioContatoTipo(MeioContatoTipo.TEL);
		List<MeioContatoTelefonico> entidadeSalva = meioContatoTelefonicoDao.restore(new MeioContatoTelefonico(entidade.getNumero()));
		if (entidadeSalva != null && entidadeSalva.size() == 1) {
			entidade = entidadeSalva.get(0);
		}
		return meioContatoTelefonicoDao.save(entidade);
	}

	public void setFotoPerfil(Pessoa pessoa) {
		Arquivo fotoPerfil = null;
		if (!isNull(pessoa) && !isEmpty(pessoa.getArquivoList())) {
			for (PessoaArquivo pa : pessoa.getArquivoList()) {
				if (Confirmacao.S.equals(pa.getPerfil())) {
					fotoPerfil = pa.getArquivo();
					break;
				}
				if (fotoPerfil == null && pa.getArquivo().getTipo() != null && pa.getArquivo().getTipo().startsWith("image")) {
					fotoPerfil = pa.getArquivo();
				}
			}
		}
		if (isNull(pessoa) || isNull(fotoPerfil)) {
			pessoa.setFotoPerfil("resources/img/pessoa_padrao.png");
		} else {
			pessoa.setFotoPerfil(String.format("resources/upload/%s%s", fotoPerfil.getMd5(), fotoPerfil.getExtensao()));
		}
	}

	private PessoaGrupoTipo pessoaGrupoTipoEstado;

	private PessoaGrupoTipo getPessoaGrupoTipoEstado() {
		if (pessoaGrupoTipoEstado == null) {
			pessoaGrupoTipoEstado = constantesService.getPessoaGrupoTipo(Codigo.ESTADO);
		}
		return pessoaGrupoTipoEstado;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> completaDadosBuscaCep(Map<String, Object> dados) throws IOException {
		Integer temp = ((PessoaDao) getDao()).getEstado((String) dados.get("uf"), getPessoaGrupoTipoEstado());
		dados.put("uf", temp);
		temp = ((PessoaDao) getDao()).getMunicipio(temp, (String) dados.get("localidade"));
		dados.put("localidade", temp);
		return dados;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> procurarEnderecoPorPessoa(String nome, String documento, Boolean somentePropriedadeRural) {
		if (nome != null) {
			nome = nome.trim().replaceAll("\\s+", " ").replaceAll("\\s;", ";").replaceAll(";\\s", ";").replaceAll("\\s", "%");
		}
		if (documento != null) {
			documento = documento.trim().replaceAll("\\s+", " ").replaceAll("\\s;", ";").replaceAll(";\\s", ";");
		}
		List<Map<String, Object>> result = ((PessoaDao) getDao()).procurarEnderecoPorPessoa(nome, documento);
		if (result != null) {
			for (Map<String, Object> pessoa : result) {
				List<Map<String, Object>> enderecos = meioContatoEnderecoDao.restorePorPessoaId((Integer) pessoa.get("id"), somentePropriedadeRural);
				List<MeioContatoEndereco> enderecoList = null;
				if (enderecos != null && enderecos.size() > 0) {
					enderecoList = new ArrayList<MeioContatoEndereco>();
					for (Map<String, Object> campo : enderecos) {
						MeioContatoEndereco mce = new MeioContatoEndereco();
						mce.setBairro((String) campo.get("bairro"));
						mce.setCep((String) campo.get("cep"));
						mce.setCodigoIbge((String) campo.get("codigo_ibge"));
						mce.setComplemento((String) campo.get("complemento"));
						mce.setId((Integer) campo.get("id"));
						Float temp = (Float) campo.get("latitude");
						mce.setLatitude(temp == null ? null : new BigDecimal (temp));
						temp = (Float) campo.get("longitude");
						mce.setLogradouro((String) campo.get("logradouro"));
						mce.setLongitude(temp == null ? null : new BigDecimal (temp));
						mce.setNomePropriedadeRuralOuEstabelecimento((String) campo.get("nome_prop_ou_estab"));
						mce.setNumero((String) campo.get("numero"));
						mce.setPropriedadeRuralConfirmacao(Confirmacao.valueOf(campo.get("propriedade_rural_confirmacao").toString()));
						mce.setRoteiroAcessoOuEnderecoInternacional((String) campo.get("roteiro_aces_ou_end_inter"));

						String paisEstrangeiro = ((String) campo.get("pais_estrangeiro_nome"));
						if (paisEstrangeiro != null) {
							mce.setPessoaGrupoPaisVi(new PessoaGrupoPaisVi((Integer) campo.get("pais_estrangeiro_id"), paisEstrangeiro));
						} else {
							PessoaGrupoCidadeVi pessoaGrupo = new PessoaGrupoCidadeVi((Integer) campo.get("cidade_pessoa_grupo_id"));
							pessoaGrupo.setNome((String) campo.get("cidade_nome"));
							PessoaGrupoMunicipioVi municipio = new PessoaGrupoMunicipioVi((Integer) campo.get("municipio_id"), (String) campo.get("municipio_nome"));
							pessoaGrupo.setPessoaGrupoMunicipioVi(municipio);
							PessoaGrupoEstadoVi estado = new PessoaGrupoEstadoVi((Integer) campo.get("estado_id"));
							estado.setSigla((String) campo.get("estado_sigla"));
							municipio.setPessoaGrupoEstadoVi(estado);
							PessoaGrupoPaisVi pais = new PessoaGrupoPaisVi((Integer) campo.get("pais_id"), (String) campo.get("pais_nome"));
							estado.setPessoaGrupoPaisVi(pais);
							mce.setPessoaGrupoCidadeVi(pessoaGrupo);
						}
						mce.setPropriedadeRural(new PropriedadeRural((Integer) campo.get("propriedade_rural_id"), mce.getId(), new PessoaGrupoComunidadeVi((Integer) campo.get("comun_id"), (String) campo.get("comun_nome")), new PessoaGrupoBaciaHidrograficaVi((Integer) campo
								.get("bacia_id"), (String) campo.get("bacia_nome"))));

						enderecoList.add(mce);
					}
				}
				pessoa.put("enderecos", enderecoList);
			}
		}
		return result;
	}
}