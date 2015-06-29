package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.ProdutoServicoDao;
import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.domain.PerspectivaProducao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoServicoDaoImpl extends _CrudDaoImpl<ProdutoServico, Integer> implements ProdutoServicoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProdutoServicoPorPerspectiva(PerspectivaProducao perspectivaProducao, ProdutoServico pai) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("nome"), "nome");
		projections.add(Projections.property("perspectiva"), "perspectiva");
		projections.add(Projections.property("unidadeMedida.nome"), "unidadeMedida");
		criteria.setProjection(projections);
		criteria.createAlias("unidadeMedida", "unidadeMedida");
		criteria.createAlias("pai", "pai", JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("nome"));
		if (perspectivaProducao != null) {
			criteria.add(Restrictions.eq("perspectiva", perspectivaProducao));
		}
		if (pai != null) {
			criteria.add(Restrictions.eq("pai", pai));
		} else {
			criteria.add(Restrictions.isNull("pai"));			
		}
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		return criteria.list();
	}

}