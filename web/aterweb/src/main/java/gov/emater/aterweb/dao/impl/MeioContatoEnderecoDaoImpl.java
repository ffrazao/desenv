package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.MeioContatoEnderecoDao;
import gov.emater.aterweb.model.MeioContatoEndereco;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MeioContatoEnderecoDaoImpl extends _CrudDaoImpl<MeioContatoEndereco, Integer> implements MeioContatoEnderecoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreExploracaoPorMeioContato(Integer meioContatoEnderecoId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo());
		criteria.createCriteria("pessoaMeioContatoList", "pessoaMeioContatoList");
		criteria.createCriteria("pessoaMeioContatoList.exploracao", "exploracao");
		criteria.createCriteria("pessoaMeioContatoList.pessoa", "pessoa");
		criteria.add(Restrictions.eq("id", meioContatoEnderecoId));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("exploracao.id"), "exploracaoId");
		projectionList.add(Projections.property("pessoa.nome"), "nome");
		projectionList.add(Projections.property("pessoa.pessoaTipo"), "pessoaTipo");
		projectionList.add(Projections.property("exploracao.area"), "exploracaoArea");
		projectionList.add(Projections.property("exploracao.regime"), "exploracaoRegime");
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.asc("nome"));
		criteria.addOrder(Order.asc("exploracaoArea"));
		criteria.addOrder(Order.asc("exploracaoRegime"));
		criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restorePorPessoaId(Integer pessoaId, Boolean somentePropriedadeRural) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    mce.id,").append("\n");
		sql.append("    mce.propriedade_rural_confirmacao,").append("\n");
		sql.append("    mce.cep,").append("\n");
		sql.append("    mce.codigo_ibge,").append("\n");
		sql.append("    mce.bairro,").append("\n");
		sql.append("    mce.nome_prop_ou_estab,").append("\n");
		sql.append("    mce.logradouro,").append("\n");
		sql.append("    mce.complemento,").append("\n");
		sql.append("    mce.numero,").append("\n");
		sql.append("    mce.roteiro_aces_ou_end_inter,").append("\n");
		sql.append("    mce.latitude,").append("\n");
		sql.append("    mce.longitude,").append("\n");		
		sql.append("    mce.cidade_pessoa_grupo_id,").append("\n");
		sql.append("    cidade.nome AS cidade_nome,").append("\n");
		sql.append("    municipio.id AS municipio_id,").append("\n");
		sql.append("    municipio.nome AS municipio_nome,").append("\n");
		sql.append("    estado.id AS estado_id,").append("\n");
		sql.append("    estado.sigla AS estado_sigla,").append("\n");
		sql.append("    pais.id AS pais_id,").append("\n");
		sql.append("    pais.nome AS pais_nome,").append("\n");
		sql.append("    mce.pais_pessoa_grupo_id AS pais_estrangeiro_id,").append("\n");
		sql.append("    pais_estrangeiro.nome AS pais_estrangeiro_nome,").append("\n");
		sql.append("    pr.id AS propriedade_rural_id,").append("\n");
		sql.append("    comun.id AS comun_id,").append("\n");
		sql.append("    comun.nome AS comun_nome,").append("\n");
		sql.append("    bacia.id AS bacia_id,").append("\n");
		sql.append("    bacia.nome AS bacia_nome").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    pessoa.meio_contato_endereco mce").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    pessoa.pessoa_meio_contato pmc ON pmc.meio_contato_id = mce.id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_cidade_vi cidade ON cidade.id = mce.cidade_pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_municipio_vi municipio ON municipio.id = cidade.pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_estado_vi estado ON estado.id = municipio.pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_pais_vi pais ON pais.id = estado.pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_pais_vi pais_estrangeiro ON pais.id = mce.pais_pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    ater.propriedade_rural pr ON pr.meio_contato_endereco_id = mce.id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_comunidade_vi comun ON comun.id = pr.comunidade_pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_bacia_hidrografica_vi bacia ON bacia.id = pr.bacia_hidrografica_pessoa_grupo_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pmc.pessoa_id = :pessoaId").append("\n");
		if (somentePropriedadeRural) {
			sql.append("AND mce.propriedade_rural_confirmacao = 'S'").append("\n");
		}
		sql.append("ORDER BY").append("\n");
		sql.append("    mce.logradouro,").append("\n");
		sql.append("    mce.complemento,").append("\n");
		sql.append("    mce.numero").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());		
		query.setParameter("pessoaId", pessoaId);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

}