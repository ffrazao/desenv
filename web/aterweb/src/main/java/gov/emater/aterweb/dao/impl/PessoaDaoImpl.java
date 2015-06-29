package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaDao;
import gov.emater.aterweb.dao.PessoaFisicaDao;
import gov.emater.aterweb.dao.PessoaJuridicaDao;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaFisica;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.PessoaJuridica;
import gov.emater.aterweb.model.domain.PessoaTipo;
import gov.emater.aterweb.mvc.dto.PessoaCadFiltroDto;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaDaoImpl extends _CrudDaoImpl<Pessoa, Integer> implements PessoaDao {

	@Autowired
	private PessoaFisicaDao pessoaFisicaDao;

	@Autowired
	private PessoaJuridicaDao pessoaJuridicaDao;

	@Override
	public Pessoa create(Pessoa pessoa) {
		if (pessoa.getPessoaTipo() == null) {
			throw new NullPointerException("Tipo de Pessoa não informado");
		}

		if (pessoa.getPessoaTipo().equals(PessoaTipo.PF)) {
			pessoa = pessoaFisicaDao.create((PessoaFisica) pessoa);
		} else if (pessoa.getPessoaTipo().equals(PessoaTipo.PJ)) {
			pessoa = pessoaJuridicaDao.create((PessoaJuridica) pessoa);
		} else {
			throw new IllegalStateException("Tipo de pessoa desconhecido!");
		}
		return pessoa;
	}

	@Override
	public void delete(Pessoa pessoa) {
		if (pessoa.getPessoaTipo().equals(PessoaTipo.PF)) {
			pessoaFisicaDao.delete((PessoaFisica) pessoa);
		} else if (pessoa.getPessoaTipo().equals(PessoaTipo.PJ)) {
			pessoaJuridicaDao.delete((PessoaJuridica) pessoa);
		} else {
			throw new NullPointerException("Tipo de Pessoa não informado");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> restoreByDto(PessoaCadFiltroDto filtro) {

		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("nome"), "nome");
		projections.add(Projections.property("pessoaTipo"), "pessoaTipo");
		projections.add(Projections.property("cpf"), "cpf");
		projections.add(Projections.property("cnpj"), "cnpj");
		criteria.setProjection(projections);
		criteria.add(Restrictions.ne("pessoaTipo", PessoaTipo.GS));
		// filtrar pelos parâmetros
		if (filtro.getNome() != null && filtro.getNome().trim().length() > 0) {
			Junction nomeCriteria = null;
			for (String nome: filtro.getNome().split(";")) {
				if (nomeCriteria == null) {
					nomeCriteria = Restrictions.disjunction().add(Restrictions.like("nome", nome, MatchMode.ANYWHERE)).add(Restrictions.like("apelidoSigla", nome, MatchMode.ANYWHERE));
				} else {
					nomeCriteria.add(Restrictions.disjunction().add(Restrictions.like("nome", nome, MatchMode.ANYWHERE)).add(Restrictions.like("apelidoSigla", nome, MatchMode.ANYWHERE)));
				}
			}
			criteria.add(nomeCriteria);
		}
		if (filtro.getTipoPessoa() != null) {
			criteria.add(Restrictions.eq("pessoaTipo", filtro.getTipoPessoa()));
		}
		if (filtro.getCpfCnpj() != null && filtro.getCpfCnpj().trim().length() > 0) {
			criteria.add(Restrictions.disjunction().add(Restrictions.eq("cpf", filtro.getCpfCnpj())).add(Restrictions.eq("cnpj", filtro.getCpfCnpj())));
		}
		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		return criteria.list();
	}

	@Override
	public Pessoa update(Pessoa pessoa) {
		if (pessoa.getPessoaTipo().equals(PessoaTipo.PF)) {
			pessoaFisicaDao.update((PessoaFisica) pessoa);
		} else if (pessoa.getPessoaTipo().equals(PessoaTipo.PJ)) {
			pessoaJuridicaDao.update((PessoaJuridica) pessoa);
		} else {
			throw new NullPointerException("Tipo de Pessoa não informado");
		}

		return pessoa;
	}

	@Override
	public Integer getEstado(String estado, PessoaGrupoTipo pessoaGrupoTipo) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		criteria.setProjection(projections);
		criteria.add(Restrictions.eq("apelidoSigla", estado));
		criteria.add(Restrictions.eq("pessoaGrupoTipo", pessoaGrupoTipo));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public Integer getMunicipio(Integer uf, String localidade) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		criteria.setProjection(projections);
		criteria.add(Restrictions.eq("pai.id", uf));
		criteria.add(Restrictions.like("nome", localidade));
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> procurarEnderecoPorPessoa(String nome, String documento) {
		if (nome == null || documento == null || (nome.trim().length() == 0 && documento.trim().length() == 0)) {
			return null;
		}
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"), "id");
		projections.add(Projections.property("nome"), "nome");
		projections.add(Projections.property("pessoaTipo"), "pessoaTipo");
		projections.add(Projections.property("cpf"), "cpf");
		projections.add(Projections.property("cnpj"), "cnpj");
		criteria.setProjection(projections);
		if (nome != null && nome.trim().length() > 0) {
			Junction nomeCriteria = null;
			for (String nm: nome.split(";")) {
				nm = nm.trim();
				if (nm.length() == 0) {
					continue;
				}
				if (nomeCriteria == null) {
					nomeCriteria = Restrictions.disjunction().add(Restrictions.like("nome", nm, MatchMode.ANYWHERE)).add(Restrictions.like("apelidoSigla", nm, MatchMode.ANYWHERE));
				} else {
					nomeCriteria.add(Restrictions.disjunction().add(Restrictions.like("nome", nm, MatchMode.ANYWHERE)).add(Restrictions.like("apelidoSigla", nm, MatchMode.ANYWHERE)));
				}
			}
			criteria.add(nomeCriteria);
		}
		if (documento != null && documento.trim().length() > 0) {
			Junction documentoCriteria = null;
			for (String dc: documento.split(";")) {
				dc = dc.trim();
				if (dc.length() == 0) {
					continue;
				}
				if (documentoCriteria == null) {
					documentoCriteria = Restrictions.disjunction().add(Restrictions.eq("cpf", dc)).add(Restrictions.eq("cnpj", dc));
				} else {
					documentoCriteria.add(Restrictions.disjunction().add(Restrictions.eq("cpf", dc)).add(Restrictions.eq("cnpj", dc)));
				}
			}
			criteria.add(documentoCriteria);
		}
		criteria.add(Restrictions.ne("pessoaTipo", PessoaTipo.GS));
		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return criteria.list();
	}
}