package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.FormularioDao;
import gov.emater.aterweb.model.Formulario;
import gov.emater.aterweb.model.domain.Confirmacao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FormularioDaoImpl extends _CrudDaoImpl<Formulario, Integer> implements FormularioDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Formulario> formulariosAtivos() {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		Calendar hoje = Calendar.getInstance();
		criteria.add(Restrictions.disjunction().add(Restrictions.isNull("inicio")).add(Restrictions.le("inicio", hoje)));
		criteria.add(Restrictions.disjunction().add(Restrictions.isNull("termino")).add(Restrictions.ge("termino", hoje)));
		criteria.add(Restrictions.eq("respostaDirecionada", Confirmacao.N));
		criteria.addOrder(Order.asc("permitirRespostaAnonima"));
		criteria.addOrder(Order.desc("respostaDirecionada"));
		criteria.addOrder(Order.asc("nome"));
		return (List<Formulario>) criteria.list();
	}

}