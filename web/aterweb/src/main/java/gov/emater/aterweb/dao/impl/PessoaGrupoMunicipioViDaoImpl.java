package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaGrupoMunicipioViDao;
import gov.emater.aterweb.model.PessoaGrupoMunicipioVi;

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
public class PessoaGrupoMunicipioViDaoImpl extends _CrudDaoImpl<PessoaGrupoMunicipioVi, Integer> implements PessoaGrupoMunicipioViDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreMap(Integer pai) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("nome"), "nome");
		projections.add(Projections.property("sigla"), "sigla");
		projections.add(Projections.property("padrao"), "padrao");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("nome"));
		criteria.add(Restrictions.eq("pessoaGrupoEstadoVi.id", pai));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (List<Map<String, Object>>) findByCriteria(criteria);
	}

}