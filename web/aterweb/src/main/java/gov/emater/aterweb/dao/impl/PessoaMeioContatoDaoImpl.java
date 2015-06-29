package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaMeioContatoDao;
import gov.emater.aterweb.model.PessoaMeioContato;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaMeioContatoDaoImpl extends _CrudDaoImpl<PessoaMeioContato, Integer> implements PessoaMeioContatoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProprietarioList(Integer meioContatoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    pes.id AS id,").append("\n");
		sql.append("    pes.nome AS nome,").append("\n");
		sql.append("    exp.regime,").append("\n");
		sql.append("    exp.area").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    pessoa.pessoa_meio_contato pmeio").append("\n");
		sql.append("JOIN").append("\n");
		sql.append("    pessoa.pessoa pes ON pes.id = pmeio.pessoa_id").append("\n");
		sql.append("JOIN").append("\n");
		sql.append("    ater.exploracao exp ON exp.pessoa_meio_contato_id = pmeio.id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pmeio.meio_contato_id = ?").append("\n");
		sql.append("ORDER BY").append("\n");
		sql.append("    pes.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter(0, meioContatoId);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);

		return query.list();
	}

}