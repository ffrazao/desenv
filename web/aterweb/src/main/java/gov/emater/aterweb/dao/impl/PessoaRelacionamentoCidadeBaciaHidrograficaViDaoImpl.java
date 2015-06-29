package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaRelacionamentoCidadeBaciaHidrograficaViDao;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaRelacionamentoCidadeBaciaHidrograficaVi;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRelacionamentoCidadeBaciaHidrograficaViDaoImpl extends _CrudDaoImpl<PessoaRelacionamentoCidadeBaciaHidrograficaVi, Integer> implements PessoaRelacionamentoCidadeBaciaHidrograficaViDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restorePorCidade(PessoaGrupo pessoaGrupo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select   a.*").append("\n");
		sql.append("from     pessoa.pessoa_relacionamento_cidade_bacia_hidrografica_vi a").append("\n");
		sql.append("where    a.cid_id = :cidId").append("\n");
		sql.append("order by a.x_nome").append("\n");
		SQLQuery result = getSession().getCurrentSession().createSQLQuery(sql.toString());
		result.setParameter("cidId", pessoaGrupo.getId());
		result.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return result.list();
	}

}