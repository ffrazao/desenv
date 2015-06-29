package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaGrupoPaisViDao;
import gov.emater.aterweb.model.PessoaGrupoPaisVi;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaGrupoPaisViDaoImpl extends _CrudDaoImpl<PessoaGrupoPaisVi, Integer> implements PessoaGrupoPaisViDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreMap() {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("nome"), "nome");
		projections.add(Projections.property("sigla"), "sigla");
		projections.add(Projections.property("padrao"), "padrao");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (List<Map<String, Object>>) findByCriteria(criteria);
	}

}