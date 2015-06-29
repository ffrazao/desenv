package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.ExploracaoDao;
import gov.emater.aterweb.dao.MeioContatoEnderecoDao;
import gov.emater.aterweb.dao.PrevisaoProducaoDao;
import gov.emater.aterweb.dao.ProducaoDao;
import gov.emater.aterweb.dao.ProdutoServicoDao;
import gov.emater.aterweb.dao.PropriedadeRuralDao;
import gov.emater.aterweb.dao.ResponsavelDao;
import gov.emater.aterweb.model.Exploracao;
import gov.emater.aterweb.model.PessoaFisica;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaJuridica;
import gov.emater.aterweb.model.PrevisaoProducao;
import gov.emater.aterweb.model.Producao;
import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.Responsavel;
import gov.emater.aterweb.model.domain.PerspectivaProducao;
import gov.emater.aterweb.model.domain.PessoaTipo;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.mvc.dto.IndiceProducaoCadFiltroDto;
import gov.emater.aterweb.service.IndiceProducaoService;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndiceProducaoServiceImpl extends CrudServiceImpl<PrevisaoProducao, Integer, PrevisaoProducaoDao> implements IndiceProducaoService {

	@Autowired
	private MeioContatoEnderecoDao meioContatoEnderecoDao;

	@Autowired
	private ProducaoDao producaoDao;

	@Autowired
	private ProdutoServicoDao produtoServicoDao;

	@Autowired
	private ResponsavelDao responsavelDao;

	@Autowired
	public IndiceProducaoServiceImpl(PrevisaoProducaoDao dao) {
		super(dao);
	}

	private void fetch(PrevisaoProducao previsaoProducao) {
		if (previsaoProducao != null) {
			if (previsaoProducao.getProducaoList() != null) {
				for (Producao producao : previsaoProducao.getProducaoList()) {
					if (producao.getResponsavelList() != null) {
						producao.getResponsavelList().size();
					}
				}
			}
		}
	}

	private void fetchList(List<Map<String, Object>> result) {
		if (result != null) {
			for (Map<String, Object> previsao : result) {
				List<Map<String, Object>> producaoList = producaoDao.restoreByPrevisaoProducaoId((Integer) previsao.get("id"));
				if (producaoList != null) {
					for (Map<String, Object> producao : producaoList) {
						List<Map<String, Object>> responsavelList = responsavelDao.restoreByProducaoId((Integer) producao.get("id"));
						if (responsavelList != null) {
							producao.put("responsavelList", responsavelList);
						}
					}
					previsao.put("producaoList", producaoList);
				}
			}
		}
	}

	private void fetchProdutoServico(List<Map<String, Object>> result) {
		if (result != null) {
			for (Map<String, Object> registro : result) {
				PerspectivaProducao perspectivaProducao = (PerspectivaProducao) registro.get("perspectiva");
				ProdutoServico pai = new ProdutoServico((Integer) registro.get("id"));
				List<Map<String, Object>> filhos = produtoServicoDao.getProdutoServicoPorPerspectiva(perspectivaProducao, pai);
				if (filhos != null && filhos.size() > 0) {
					fetchProdutoServico(filhos);
					registro.put("filhos", filhos);
				}
			}
		}
	}

	private void fetchPropriedadeRuralPorPessoaGrupo(List<Map<String, Object>> list) {
		if (list != null) {
			for (Map<String, Object> registro : list) {
				fetchResponsavelList(registro);
			}
		}
	}

	private void fetchResponsavelList(Map<String, Object> registro) {
		if (registro != null) {
			Integer meioContatoEnderecoId = (Integer) registro.get("meioContatoEnderecoId");
			List<Map<String, Object>> exploracaoList = meioContatoEnderecoDao.restoreExploracaoPorMeioContato(meioContatoEnderecoId);
			if (exploracaoList != null) {
				for (Map<String, Object> exploracao : exploracaoList) {
					PessoaTipo pessoaTipo = (PessoaTipo) exploracao.get("pessoaTipo");
					switch (pessoaTipo) {
					case PF:
						exploracao.put("classe", PessoaFisica.class.getName());
						break;
					case PJ:
						exploracao.put("classe", PessoaJuridica.class.getName());
						break;
					default:
						break;
					}
				}
				registro.put("exploracaoList", exploracaoList);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> filtrarByDto(Dto dto) {
		IndiceProducaoCadFiltroDto filtro = (IndiceProducaoCadFiltroDto) dto;

		List<Map<String, Object>> result = getDao().restoreByDto(filtro);

		fetchList(result);

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProdutoServicoPorPerspectiva(PerspectivaProducao perspectivaProducao, ProdutoServico pai) {
		List<Map<String, Object>> result = produtoServicoDao.getProdutoServicoPorPerspectiva(perspectivaProducao, pai);
		fetchProdutoServico(result);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getPropriedadeRuralPorPessoaGrupo(PessoaGrupo pessoaGrupo) {
		List<Map<String, Object>> result = null; // FIXME propriedadeRuralPessoaGrupoDao.restorePropriedadeRuralPorPessoaGrupo(pessoaGrupo);
		fetchPropriedadeRuralPorPessoaGrupo(result);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PrevisaoProducao restore(Integer id) {
		PrevisaoProducao result = super.restore(id);

		// restaurar dados subjacentes
		fetch(result);

		return result;
	}
	
	@Autowired
	private ExploracaoDao exploracaoDao;
	
	@Autowired
	private PropriedadeRuralDao propriedadeRuralDao;

	@Override
	@Transactional
	public PrevisaoProducao save(@Valid PrevisaoProducao entidade) {
		if (!isNull(entidade) && !isNull(entidade.getProducaoList())) {
			for (Producao producao : entidade.getProducaoList()) {
				producao.setPrevisaoProducao(entidade);
				producao.setPropriedadeRural(propriedadeRuralDao.restore(producao.getPropriedadeRural().getId()));
				if (!isNull(producao.getResponsavelList())) {
					for (Responsavel responsavel : producao.getResponsavelList()) {
						responsavel.setProducao(producao);
						responsavel.setExploracao(exploracaoDao.restore(responsavel.getExploracao().getId()));
						// cada responsavel deve ter somente uma producao
						if (responsavel.getId() != null) {
							Responsavel pesq = new Responsavel();
							pesq.setProducao(producao);
							pesq.setExploracao(new Exploracao(responsavel.getExploracao().getId()));
							List<Responsavel> result = responsavelDao.restore(pesq);
							if (result != null && result.size() == 1) {
								responsavel.setId(result.get(0).getId());
							}
						}
					}
				}
				// cada propriedade deve ter somente uma previsao de producao
				if (producao.getId() != null) {
					Producao pesq = new Producao();
					pesq.setPrevisaoProducao(entidade);
					pesq.setPropriedadeRural(producao.getPropriedadeRural());
					List<Producao> result = producaoDao.restore(pesq);
					if (result != null && result.size() == 1) {
						producao.setId(result.get(0).getId());
					}
				}
			}
		}
		PrevisaoProducao result = super.save(entidade);
		
		for (Producao producao: result.getProducaoList()) {
			producaoDao.save(producao);
			for (Responsavel responsavel: producao.getResponsavelList()) {
				responsavelDao.save(responsavel);
			}
		}
		
		return result;
	}
}