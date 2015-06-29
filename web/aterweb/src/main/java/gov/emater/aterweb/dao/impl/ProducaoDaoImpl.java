package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.ProducaoDao;
import gov.emater.aterweb.model.Producao;

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
public class ProducaoDaoImpl extends _CrudDaoImpl<Producao, Integer> implements ProducaoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreByPrevisaoProducaoId(Integer previsaoProducaoId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("volume"), "volume");
		projections.add(Projections.property("propriedadeRural.id"), "propriedadeRuralId");
		projections.add(Projections.property("meioContatoEndereco.nomePropriedadeRuralOuEstabelecimento"), "propriedadeRuralNome");
		projections.add(Projections.property("propriedadeRural.areaTotal"), "propriedadeRuralAreaTotal");
		criteria.setProjection(projections);
		criteria.createCriteria("propriedadeRural", "propriedadeRural");
		criteria.createCriteria("propriedadeRural.meioContatoEndereco", "meioContatoEndereco");
		criteria.addOrder(Order.asc("propriedadeRuralNome"));
		criteria.addOrder(Order.asc("propriedadeRuralAreaTotal"));
		criteria.add(Restrictions.eq("previsaoProducao.id", previsaoProducaoId));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return criteria.list();
	}

}