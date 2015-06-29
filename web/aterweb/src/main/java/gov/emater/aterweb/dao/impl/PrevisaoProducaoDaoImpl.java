package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PrevisaoProducaoDao;
import gov.emater.aterweb.model.PrevisaoProducao;
import gov.emater.aterweb.model.Producao;
import gov.emater.aterweb.model.Responsavel;
import gov.emater.aterweb.model.domain.PerspectivaProducao;
import gov.emater.aterweb.mvc.dto.IndiceProducaoCadFiltroDto;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class PrevisaoProducaoDaoImpl extends _CrudDaoImpl<PrevisaoProducao, Integer> implements PrevisaoProducaoDao {

	private DetachedCriteria preparaCriteriaProducao(DetachedCriteria detachedCriteria) {
		if (detachedCriteria == null) {
			detachedCriteria = DetachedCriteria.forClass(Producao.class);
			detachedCriteria.createCriteria("propriedadeRural", "propriedadeRural");
			detachedCriteria.setProjection(Projections.property("previsaoProducao"));
		}
		return detachedCriteria;
	}

	private DetachedCriteria preparaCriteriaResponsavel(DetachedCriteria detachedCriteria) {
		if (detachedCriteria == null) {
			detachedCriteria = DetachedCriteria.forClass(Responsavel.class);
			detachedCriteria.createCriteria("exploracao", "exploracao");
			detachedCriteria.createCriteria("exploracao.pessoaMeioContato", "pessoaMeioContato");
			detachedCriteria.createCriteria("pessoaMeioContato.pessoa", "pessoa");
			detachedCriteria.setProjection(Projections.property("producao"));
		}
		return detachedCriteria;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> restoreByDto(IndiceProducaoCadFiltroDto filtro) {
		// filtrar previsões de produção
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		// configurar a pesquisa
		criteria.createCriteria("produtoServico", "produtoServico");
		criteria.createCriteria("produtoServico.unidadeMedida", "unidadeMedida");
		criteria.createCriteria("pessoaGrupo", "pessoaGrupo");
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("produtoServico.nome"), "produtoServico");
		projections.add(Projections.property("pessoaGrupo.nome"), "pessoaGrupoNome");
		projections.add(Projections.property("volume"), "volume");
		projections.add(Projections.property("inicio"), "inicio");
		projections.add(Projections.property("termino"), "termino");
		projections.add(Projections.property("totalPropriedades"), "totalPropriedades");
		projections.add(Projections.property("produtoServico.perspectiva"), "perspectiva");
		projections.add(Projections.property("unidadeMedida.nome"), "unidadeMedida");
		projections.add(Projections.property("protecaoEpocaForma"), "protecaoEpocaForma");
		projections.add(Projections.property("sistemaAgricola"), "sistemaAgricola");
		projections.add(Projections.property("tipo"), "tipo");
		projections.add(Projections.property("usoDagua"), "usoDagua");
		projections.add(Projections.property("exploracao"), "exploracao");
		projections.add(Projections.property("sistemaAnimal"), "sistemaAnimal");
		projections.add(Projections.property("condicao"), "condicao");
		projections.add(Projections.property("produto"), "produto");
		projections.add(Projections.property("projeto"), "projeto");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("perspectiva"));
		criteria.addOrder(Order.asc("pessoaGrupoNome"));
		criteria.addOrder(Order.asc("inicio"));
		criteria.addOrder(Order.asc("produtoServico"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		// filtrar pela previsão de produção
		if (filtrar(filtro.getProducaoVolumeIni())) {
			criteria.add(Restrictions.ge("volume", filtro.getProducaoVolumeIni()));
		}
		if (filtrar(filtro.getProducaoVolumeFin())) {
			criteria.add(Restrictions.le("volume", filtro.getProducaoVolumeFin()));
		}
		if (filtrar(filtro.getProducaoData())) {
			criteria.add(Restrictions.le("inicio", filtro.getProducaoData()));
			criteria.add(Restrictions.ge("termino", filtro.getProducaoData()));
		}
		if (filtrar(filtro.getPessoaGrupoComunidade())) {
			criteria.add(Restrictions.eq("pessoaGrupo", filtro.getPessoaGrupoComunidade()));
		}
		// filtrar pelo produto/serviço
		if (filtrar(filtro.getProducaoPerspectiva())) {
			PerspectivaProducao p = filtro.getProducaoPerspectiva();
			if (p.equals(PerspectivaProducao.AGRICOLA) || p.equals(PerspectivaProducao.FLORES)) {
				if (p.equals(PerspectivaProducao.AGRICOLA)) {
					criteria.add(Restrictions.disjunction(Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.AGRICOLA),
							Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.FLORES)));
				} else {
					criteria.add(Restrictions.eq("produtoServico.perspectiva", p));
				}
				if (filtrar(filtro.getProducaoPerspectivaFloresProtecaoEpocaForma())) {
					criteria.add(Restrictions.eq("protecaoEpocaForma", filtro.getProducaoPerspectivaFloresProtecaoEpocaForma()));
				}
				if (filtrar(filtro.getProducaoPerspectivaFloresSistema())) {
					criteria.add(Restrictions.eq("sistemaAgricola", filtro.getProducaoPerspectivaFloresSistema()));
				}
				if (filtrar(filtro.getProducaoPerspectivaFloresTipo())) {
					criteria.add(Restrictions.eq("tipo", filtro.getProducaoPerspectivaFloresTipo()));
				}
				if (filtrar(filtro.getProducaoPerspectivaFloresUsoDagua())) {
					criteria.add(Restrictions.eq("usoDagua", filtro.getProducaoPerspectivaFloresUsoDagua()));
				}
			} else if (p.equals(PerspectivaProducao.ANIMAL) || p.equals(PerspectivaProducao.CORTE) || p.equals(PerspectivaProducao.LEITE) || p.equals(PerspectivaProducao.POSTURA)) {
				if (p.equals(PerspectivaProducao.ANIMAL)) {
					criteria.add(Restrictions.disjunction(Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.ANIMAL),
							Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.CORTE), Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.LEITE),
							Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.POSTURA)));
				} else {
					criteria.add(Restrictions.eq("produtoServico.perspectiva", p));
				}
				if (filtrar(filtro.getProducaoPerspectivaAnimalExploracao())) {
					criteria.add(Restrictions.eq("exploracao", filtro.getProducaoPerspectivaAnimalExploracao()));
				}
				if (filtrar(filtro.getProducaoPerspectivaAnimalSistema())) {
					criteria.add(Restrictions.eq("sistemaAnimal", filtro.getProducaoPerspectivaAnimalSistema()));
				}
			} else if (p.equals(PerspectivaProducao.SERVICO) || p.equals(PerspectivaProducao.AGROINDUSTRIA) || p.equals(PerspectivaProducao.TURISMO)) {
				if (p.equals(PerspectivaProducao.SERVICO)) {
					criteria.add(Restrictions.disjunction(Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.SERVICO),
							Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.AGROINDUSTRIA), Restrictions.eq("produtoServico.perspectiva", PerspectivaProducao.TURISMO)));
				} else {
					criteria.add(Restrictions.eq("produtoServico.perspectiva", p));
				}
				if (filtrar(filtro.getProducaoPerspectivaServicoCondicao())) {
					criteria.add(Restrictions.eq("condicao", filtro.getProducaoPerspectivaServicoCondicao()));
				}
				if (filtrar(filtro.getProducaoPerspectivaServicoProduto())) {
					criteria.add(Restrictions.eq("produto", filtro.getProducaoPerspectivaServicoProduto()));
				}
				if (filtrar(filtro.getProducaoPerspectivaServicoProjeto())) {
					criteria.add(Restrictions.eq("projeto", filtro.getProducaoPerspectivaServicoProjeto()));
				}
			}
		}
		if (filtrar(filtro.getProducaoProdutoServico())) {
			criteria.add(Restrictions.eq("this.produtoServico", filtro.getProducaoProdutoServico()));
		}
		// filtrar pela produção
		DetachedCriteria criteriaProducao = null;
		if (filtrar(filtro.getPropriedadeNome())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.add(Restrictions.like("propriedadeRural.nome", filtro.getPropriedadeNome().replaceAll("\\s", "%"), MatchMode.ANYWHERE));
		}
		if (filtrar(filtro.getPropriedadeAreaPropriedadeIni())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.add(Restrictions.ge("volume", filtro.getPropriedadeAreaPropriedadeIni()));
		}
		if (filtrar(filtro.getPropriedadeAreaPropriedadeFin())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.add(Restrictions.le("volume", filtro.getPropriedadeAreaPropriedadeFin()));
		}
		if (filtrar(filtro.getPessoaGrupoCidade())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.createCriteria("propriedadeRural.meioContatoEndereco", "meioContatoEndereco");
			criteriaProducao.createCriteria("meioContatoEndereco.pessoaGrupoCidadeVi", "pessoaGrupoCidade");
			criteriaProducao.add(Restrictions.eq("pessoaGrupoCidade.id", filtro.getPessoaGrupoCidade().getId()));
		} else if (filtrar(filtro.getPessoaGrupoMunicipio())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.createCriteria("propriedadeRural.meioContatoEndereco", "meioContatoEndereco");
			criteriaProducao.createCriteria("meioContatoEndereco.pessoaGrupo", "pessoaGrupoCidade");
			criteriaProducao.createCriteria("pessoaGrupoCidade.pessoaGrupo", "pessoaGrupoMunicipio");
			criteriaProducao.add(Restrictions.eq("pessoaGrupoMunicipio.id", filtro.getPessoaGrupoMunicipio().getId()));
		} else if (filtrar(filtro.getPessoaGrupoEstado())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.createCriteria("propriedadeRural.meioContatoEndereco", "meioContatoEndereco");
			criteriaProducao.createCriteria("meioContatoEndereco.pessoaGrupo", "pessoaGrupoCidade");
			criteriaProducao.createCriteria("pessoaGrupoCidade.pessoaGrupo", "pessoaGrupoMunicipio");
			criteriaProducao.createCriteria("pessoaGrupoMunicipio.pessoaGrupo", "pessoaGrupoEstado");
			criteriaProducao.add(Restrictions.eq("pessoaGrupoEstado.id", filtro.getPessoaGrupoEstado().getId()));
		} else if (filtrar(filtro.getPessoaGrupoPais())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaProducao.createCriteria("propriedadeRural.meioContatoEndereco", "meioContatoEndereco");
			criteriaProducao.createCriteria("meioContatoEndereco.pessoaGrupo", "pessoaGrupoCidade");
			criteriaProducao.createCriteria("pessoaGrupoCidade.pessoaGrupo", "pessoaGrupoMunicipio");
			criteriaProducao.createCriteria("pessoaGrupoMunicipio.pessoaGrupo", "pessoaGrupoEstado");
			criteriaProducao.createCriteria("pessoaGrupoEstado.pessoaGrupo", "pessoaGrupoPais");
			criteriaProducao.add(Restrictions.eq("pessoaGrupoPais.id", filtro.getPessoaGrupoPais().getId()));
		}
		if (filtrar(filtro.getPessoaGrupoBaciaHidrografica())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			// FIXME
			criteriaProducao.createCriteria("propriedadeRural.propriedadeRuralPessoaGrupoList", "propriedadeRuralPessoaGrupoList");
			criteriaProducao.createCriteria("propriedadeRuralPessoaGrupoList.pessoaGrupo", "pessoaGrupoBaciaHidrografica");
			criteriaProducao.add(Restrictions.eq("pessoaGrupoBaciaHidrografica.id", filtro.getPessoaGrupoBaciaHidrografica().getId()));
		}
		// filtrar pelos responsaveis
		DetachedCriteria criteriaResponsavel = null;
		if (filtrar(filtro.getProdutorCpfCnpj())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaResponsavel = preparaCriteriaResponsavel(criteriaResponsavel);
			criteriaResponsavel.add(Restrictions.disjunction(Restrictions.eq("pessoa.cpf", filtro.getProdutorCpfCnpj()), Restrictions.eq("pessoa.cnpj", filtro.getProdutorCpfCnpj())));
		}
		if (filtrar(filtro.getProdutorNome())) {
			criteriaProducao = preparaCriteriaProducao(criteriaProducao);
			criteriaResponsavel = preparaCriteriaResponsavel(criteriaResponsavel);
			criteriaResponsavel.add(Restrictions.disjunction(Restrictions.like("pessoa.nome", filtro.getProdutorNome().replaceAll("\\s", "%"), MatchMode.ANYWHERE),
					Restrictions.like("pessoa.apelidoSigla", filtro.getProdutorNome().replaceAll("\\s", "%"), MatchMode.ANYWHERE)));
		}
		if (criteriaResponsavel != null) {
			criteriaProducao.add(Subqueries.propertyIn("id", criteriaResponsavel));
		}
		if (criteriaProducao != null) {
			criteria.add(Subqueries.propertyIn("id", criteriaProducao));
		}
		filtro.getNumeroPagina();

		return criteria.list();
	}

}