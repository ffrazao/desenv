package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.ResponsavelDao;
import gov.emater.aterweb.model.Responsavel;

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
public class ResponsavelDaoImpl extends _CrudDaoImpl<Responsavel, Integer> implements ResponsavelDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreByProducaoId(Integer producaoId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("volume"), "volume");
		projections.add(Projections.property("pessoa.id"), "pessoaId");
		projections.add(Projections.property("pessoa.nome"), "pessoaNome");
		projections.add(Projections.property("pessoa.cpf"), "pessoaCpf");
		projections.add(Projections.property("pessoa.cnpj"), "pessoaCnpj");
		projections.add(Projections.property("exploracao.area"), "exploracaoArea");
		projections.add(Projections.property("exploracao.regime"), "exploracaoRegime");
		criteria.setProjection(projections);
		criteria.createCriteria("exploracao", "exploracao");
		criteria.createCriteria("exploracao.pessoaMeioContato", "pessoaMeioContato");
		criteria.createCriteria("pessoaMeioContato.pessoa", "pessoa");
		criteria.addOrder(Order.asc("pessoaNome"));
		criteria.addOrder(Order.asc("volume"));
		criteria.add(Restrictions.eq("producao.id", producaoId));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return criteria.list();
	}

}