package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.ArquivoDao;
import gov.emater.aterweb.dao.BenfeitoriaDao;
import gov.emater.aterweb.dao.MeioContatoEnderecoDao;
import gov.emater.aterweb.dao.PessoaGrupoDao;
import gov.emater.aterweb.dao.PessoaMeioContatoDao;
import gov.emater.aterweb.dao.PessoaRelacionamentoCidadeBaciaHidrograficaViDao;
import gov.emater.aterweb.dao.PessoaRelacionamentoCidadeComunidadeViDao;
import gov.emater.aterweb.dao.PropriedadeRuralArquivoDao;
import gov.emater.aterweb.dao.PropriedadeRuralDao;
import gov.emater.aterweb.model.Benfeitoria;
import gov.emater.aterweb.model.MeioContatoEndereco;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaGrupoCidadeVi;
import gov.emater.aterweb.model.PessoaGrupoEstadoVi;
import gov.emater.aterweb.model.PessoaGrupoMunicipioVi;
import gov.emater.aterweb.model.PessoaGrupoPaisVi;
import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.model.PropriedadeRuralArquivo;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.mvc.dto.PropriedadeRuralCadFiltroDto;
import gov.emater.aterweb.service.ConstantesService;
import gov.emater.aterweb.service.PessoaService;
import gov.emater.aterweb.service.PropriedadeRuralService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropriedadeRuralServiceImpl extends CrudServiceImpl<PropriedadeRural, Integer, PropriedadeRuralDao> implements PropriedadeRuralService {

	@Autowired
	private ArquivoDao arquivoDao;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaGrupoDao pessoaGrupoDao;

	@Autowired
	private MeioContatoEnderecoDao meioContatoEnderecoDao;

	@Autowired
	private PessoaMeioContatoDao pessoaMeioContatoDao;

	@Autowired
	private PropriedadeRuralArquivoDao propriedadeRuralArquivoDao;

	@Autowired
	public PropriedadeRuralServiceImpl(PropriedadeRuralDao dao) {
		super(dao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> detalhar(Integer id) {
		// variáveis de apoio
		List<Map<String, Object>> result = null;

		// captar público alvo
		PropriedadeRural entidade = getDao().restore(id);

		if (entidade != null) {
			result = new ArrayList<Map<String, Object>>();
			// Map<String, Object> linha = new HashMap<String, Object>();

			// linha.put("publicoAlvoConfirmacao", entidade
			// .getPublicoAlvoConfirmacao().name());
			//
			// // verificar se a pessoa possui meios de contato
			// if (entidade.getPessoaMeioContatos() != null) {
			// // percorrer todos os meios de contato
			// for (PessoaMeioContato pessoaMeioContato : entidade
			// .getPessoaMeioContatos()) {
			// // listar somente os meios de contato principais
			// if (pessoaMeioContato.getOrdem().equals(1)) {
			// linha.put(pessoaMeioContato.getMeioContato()
			// .getMeioContatoTipo().name(),
			// pessoaMeioContato.getMeioContato());
			// result.add(linha);
			// }
			// }
			// }
		}

		// TODO fazer consulta para devolver o endereço da principal foto da
		// pessoa

		return result;
	}

	private void fetch(PropriedadeRural entidade) {
		if (entidade != null) {
			entidade.getMeioContatoEndereco().getId();

			// FIXME
//			if (entidade.getPropriedadeRuralPessoaGrupoList() != null) {				
//				entidade.getPropriedadeRuralPessoaGrupoList().size();
//			}
			if (entidade.getArquivoList() != null) {
				entidade.getArquivoList().size();
			}
			// restaurar as pessoas do endereco
			if (entidade.getMeioContatoEndereco() != null) {
				if (entidade.getMeioContatoEndereco().getPessoaMeioContatoList() != null) {
					entidade.getMeioContatoEndereco().getPessoaMeioContatoList().size();
				}
				PessoaGrupoCidadeVi cidade = entidade.getMeioContatoEndereco().getPessoaGrupoCidadeVi();
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
			}
		}
	}

	private void fetchFiltrarByDto(Map<String, Object> entidade) {
		if (entidade != null) {
			Integer id = (Integer) entidade.get("id");
			entidade.put("proprietarioList", pessoaMeioContatoDao.getProprietarioList(id));
			// FIXME
			//entidade.put("comunidadeList", propriedadeRuralPessoaGrupoDao.getComunidadeList(id));
			// recuperar a foto do perfil da propriedade
			PropriedadeRuralArquivo arquivoPerfil = new PropriedadeRuralArquivo();
			arquivoPerfil.setPropriedadeRural(new PropriedadeRural(id));
			List<PropriedadeRuralArquivo> arquivos = propriedadeRuralArquivoDao.restore(arquivoPerfil);
			arquivoPerfil = null;
			if (arquivos != null) {
				for (PropriedadeRuralArquivo pra : arquivos) {
					if (arquivoPerfil == null && pra.getArquivo().getTipo().startsWith("image")) {
						arquivoPerfil = pra;
					}
					if (Confirmacao.S.equals(pra.getPerfil())) {
						arquivoPerfil = pra;
						break;
					}
				}
			}

			PropriedadeRural pr = getDao().restore(id);
			if (pr.getMeioContatoEndereco() != null) {
				entidade.put("latitude", pr.getMeioContatoEndereco().getLatitude());
				entidade.put("longitude", pr.getMeioContatoEndereco().getLongitude());
			}
			if (arquivoPerfil != null) {
				arquivoPerfil.setPropriedadeRural(null);
				entidade.put("arquivoList", arquivoPerfil);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<?> filtrarByDto(Dto dto) {
		PropriedadeRuralCadFiltroDto filtro = (PropriedadeRuralCadFiltroDto) dto;

		if (filtro.getNome() != null) {
			filtro.setNome(filtro.getNome().replaceAll("\\s", "%"));
		}

		List<Map<String, Object>> result = (List<Map<String, Object>>) getDao().restoreByDto(filtro);
		if (result != null) {
			for (Map<String, Object> linha : result) {
				fetchFiltrarByDto(linha);
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PropriedadeRural restore(Integer id) {
		PropriedadeRural result = super.restore(id);

		// restaurar dados subjacentes
		fetch(result);

		return result;
	}

	@Override
	@Transactional
	public PropriedadeRural save(@Valid PropriedadeRural entidade) {

		// salvar meio de contato endereço
		MeioContatoEndereco meioContatoEndereco = entidade.getMeioContatoEndereco();
		meioContatoEndereco.setPropriedadeRuralConfirmacao(Confirmacao.S);
		meioContatoEndereco = pessoaService.saveEndereco(meioContatoEndereco);
		entidade.setMeioContatoEndereco(meioContatoEndereco);

		// preparar benfeitorias
		if (entidade.getBenfeitoriaList() != null) {
			for (Benfeitoria registro : entidade.getBenfeitoriaList()) {
				registro.setPropriedadeRural(entidade);
			}
		}
		// preparar Bacia Hidrografica e Comunidade
		// FIXME
		/*entidade.setPropriedadeRuralPessoaGrupoList(null);
		if (entidade.getPropriedadeRuralPessoaGrupoList() != null) {
			for (PropriedadeRuralPessoaGrupo registro : entidade.getPropriedadeRuralPessoaGrupoList()) {
				registro.setPropriedadeRural(entidade);
			}
		}*/
		// preparar arquivos
		if (entidade.getArquivoList() != null) {
			for (PropriedadeRuralArquivo registro : entidade.getArquivoList()) {
				registro.setPropriedadeRural(entidade);
			}
		}

		return getDao().save(entidade);
	}

	@Autowired
	private BenfeitoriaDao benfeitoriaDao;

	@Override
	@Transactional
	public void deleteById(Integer id) {
		PropriedadeRural pr = getDao().restore(id);

		// remover arquivo
		PropriedadeRuralArquivo pra = new PropriedadeRuralArquivo();
		pra.setPropriedadeRural(pr);
		List<PropriedadeRuralArquivo> pras = propriedadeRuralArquivoDao.restore(pra);
		for (PropriedadeRuralArquivo ent : pras) {
			propriedadeRuralArquivoDao.delete(ent.getId());
		}
		// remover propriedade rural pessoa grupo
		// FIXME
		/*
		PropriedadeRuralPessoaGrupo prl = new PropriedadeRuralPessoaGrupo();
		prl.setPropriedadeRural(pr);
		List<PropriedadeRuralPessoaGrupo> prls = propriedadeRuralPessoaGrupoDao.restore(prl);
		for (PropriedadeRuralPessoaGrupo ent : prls) {
			propriedadeRuralPessoaGrupoDao.delete(ent.getId());
		}*/
		// remover benfeitorias
		Benfeitoria ben = new Benfeitoria();
		ben.setPropriedadeRural(pr);
		List<Benfeitoria> bens = benfeitoriaDao.restore(ben);
		for (Benfeitoria ent : bens) {
			benfeitoriaDao.delete(ent.getId());
		}

		// definir que o endereço nao é uma propriedade rural
		MeioContatoEndereco mce = pr.getMeioContatoEndereco();
		mce.setPropriedadeRuralConfirmacao(Confirmacao.N);
		mce.setPropriedadeRural(null);
		meioContatoEnderecoDao.save(mce);

		super.deleteById(id);
		throw new RuntimeException("rollback");
	}

	@Autowired
	private PessoaRelacionamentoCidadeBaciaHidrograficaViDao pessoaRelacionamentoCidadeBaciaHidrograficaViDao;

	@Autowired
	private PessoaRelacionamentoCidadeComunidadeViDao pessoaRelacionamentoCidadeComunidadeViDao;

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<Map<String, Object>>> restoreComunicadeBaciaHidrografica(PessoaGrupo pessoaGrupo) {
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();

		result.put("BACIA_HIDROGRAFICA", pessoaRelacionamentoCidadeBaciaHidrograficaViDao.restorePorCidade(pessoaGrupo));
		result.put("COMUNIDADE", pessoaRelacionamentoCidadeComunidadeViDao.restorePorCidade(pessoaGrupo));

		return result;
	}

	@Autowired
	private ConstantesService constantesService;

}