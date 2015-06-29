package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.CadPessoaPropriedadeDao;
import gov.emater.aterweb.model.CadPessoaPropriedade;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class CadPessoaPropriedadeDaoImpl extends _CrudDaoImpl<CadPessoaPropriedade, Integer> implements CadPessoaPropriedadeDao {

	public CadPessoaPropriedadeDaoImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restorePessoaNomeByPropriedadeId(Integer propriedadeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select pessoa.id").append("\n");
		sql.append("     , pessoa.nome").append("\n");
		sql.append("     , pessoa.status").append("\n");
		sql.append("from cad_geral.cad_pessoa_propriedade pp").append("\n");
		sql.append("join cad_geral.cad_pessoa pessoa").append("\n");
		sql.append("on pp.cad_pessoa_id = pessoa.id").append("\n");
		sql.append("join cad_geral.cad_propriedade propriedade").append("\n");
		sql.append("on pp.cad_propriedade_id = propriedade.id").append("\n");
		sql.append("where propriedade.id = :propriedadeId").append("\n");
		sql.append("order by pessoa.nome").append("\n");
		SQLQuery restorePessoaNomeByPropriedadeIdQuery;
		restorePessoaNomeByPropriedadeIdQuery = getSession().getCurrentSession().createSQLQuery(sql.toString());
		restorePessoaNomeByPropriedadeIdQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		restorePessoaNomeByPropriedadeIdQuery.setParameter("propriedadeId", propriedadeId);
		return restorePessoaNomeByPropriedadeIdQuery.list();
	}

}