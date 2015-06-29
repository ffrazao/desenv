package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaRelacionamentoDao;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaRelacionamento;
import gov.emater.aterweb.model.RelacionamentoTipo;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRelacionamentoDaoImpl extends _CrudDaoImpl<PessoaRelacionamento, Integer> implements PessoaRelacionamentoDao {

	@Override
	public List<?> restorePessoaPorRelacionamentoTipo(Pessoa pessoa, RelacionamentoTipo relacionamentoTipo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select   pr . *, pes.nome as pessoa_nome, pes.apelido_sigla, rel.relacionamento_tipo_id, rel.inicio, rel.termino").append("\n");
		sql.append("from     pessoa.pessoa_relacionamento prr").append("\n");
		sql.append("join     pessoa.relacionamento rel ON rel.id = prr.relacionamento_id").append("\n");
		sql.append("join     pessoa.pessoa_relacionamento pr ON pr.relacionamento_id = prr.relacionamento_id").append("\n");
		sql.append("join     pessoa.pessoa pes ON pes.id = pr.pessoa_id").append("\n");
		sql.append("where    prr.pessoa_id = :pessoaGrupo").append("\n");
		sql.append("and      rel.relacionamento_tipo_id = :relacionamentoTipo").append("\n");
		sql.append("and      pr.pessoa_id != :pessoaGrupo").append("\n");
		sql.append("order by pes.nome").append("\n");
		SQLQuery result = getSession().getCurrentSession().createSQLQuery(sql.toString());
		result.setParameter("pessoaGrupo", pessoa.getId());
		result.setParameter("relacionamentoTipo", relacionamentoTipo.getId());
		return result.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}