package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.ExploracaoDao;
import gov.emater.aterweb.model.Exploracao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ExploracaoDaoImpl extends _CrudDaoImpl<Exploracao, Integer> implements ExploracaoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProdutoresPorMeioContatoId(Integer meioContatoId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());		
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("pessoa.id"), "pessoaId");
		projections.add(Projections.property("pessoa.nome"), "pessoaNome");
		projections.add(Projections.property("regime"), "regime");
		projections.add(Projections.property("area"), "area");
		criteria.setProjection(projections);
		criteria.createAlias("pessoaMeioContato", "pessoaMeioContato");
		criteria.createAlias("pessoaMeioContato.pessoa", "pessoa");
		criteria.add(Restrictions.eq("pessoaMeioContato.meioContato.id", meioContatoId));
		criteria.addOrder(Order.asc("pessoa.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		return criteria.list();
	}

}