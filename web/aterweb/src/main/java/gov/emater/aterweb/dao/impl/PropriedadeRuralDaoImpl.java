package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PropriedadeRuralDao;
import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.mvc.dto.PropriedadeRuralCadFiltroDto;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

@Repository
public class PropriedadeRuralDaoImpl extends _CrudDaoImpl<PropriedadeRural, Integer> implements PropriedadeRuralDao {

	@Override
	public List<?> restoreByDto(PropriedadeRuralCadFiltroDto filtro) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT").append("\n");
		sql.append("    prop.id,").append("\n");
		sql.append("    ender.nome_prop_ou_estab AS nomePropriedade,").append("\n");
		sql.append("    prop.outorga,").append("\n");
		sql.append("    prop.situacao_fundiaria AS situacaoFundiaria,").append("\n");
		sql.append("    concat(ender.logradouro, ender.complemento, ender.numero) AS descricao,").append("\n");
		sql.append("    ender.cep,").append("\n");
		sql.append("    cid.nome AS cidade,").append("\n");
		sql.append("    mun.nome AS municipio,").append("\n");
		sql.append("    est.sigla AS estado,").append("\n");
		sql.append("    sisprod.nome AS sistemaProducao").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    ater.propriedade_rural prop").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    ater.sistema_producao sisprod ON sisprod.id = prop.sistema_producao_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.meio_contato_endereco ender ON ender.id = prop.meio_contato_endereco_id").append("\n");
		sql.append("        AND ender.propriedade_rural_confirmacao = 'S'").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_cidade_vi cid ON cid.id = ender.cidade_pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_municipio_vi mun ON mun.id = cid.pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_grupo_estado_vi est ON est.id = mun.pessoa_grupo_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa_meio_contato pmeio ON pmeio.meio_contato_id = ender.id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    pessoa.pessoa pes ON pes.id = pmeio.pessoa_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    ater.exploracao exp ON exp.pessoa_meio_contato_id = pmeio.id").append("\n");
//		sql.append("        LEFT JOIN").append("\n");
//		sql.append("    ater.propriedade_rural_pessoa_grupo prl ON prl.propriedade_rural_id = prop.id").append("\n");
//		sql.append("        LEFT JOIN").append("\n");
//		sql.append("    pessoa.pessoa_grupo comun ON comun.id = prl.pessoa_grupo_id").append("\n");
//		sql.append("        LEFT JOIN").append("\n");
//		sql.append("    pessoa.pessoa_grupo_tipo loctipo ON loctipo.id = comun.pessoa_grupo_tipo_id").append("\n");
//		sql.append("        AND loctipo.codigo = 'COMUNIDADE'").append("\n");
		sql.append("WHERE 1 = 1").append("\n");
		if (filtro.getNome() != null && filtro.getNome().trim().length() > 0) {
			sql.append("AND prop.nome like :nome").append("\n");
		}
		if (filtro.getComunidade() != null) {
			sql.append("AND comun.id = :comun").append("\n");
		}
		if (filtro.getSituacaoFundiaria() != null) {
			sql.append("AND prop.situacao_fundiaria = :situacao_fundiaria").append("\n");
		}
		if (filtro.getSistemaPredominante() != null) {
			sql.append("AND sisprod.id = :sisprod").append("\n");
		}
		if (filtro.getOutorga() != null) {
			sql.append("AND prop.outorga = :outorga").append("\n");
		}
		sql.append("ORDER BY").append("\n");
		sql.append("    nomePropriedade").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (filtro.getNome() != null && filtro.getNome().trim().length() > 0) {
			query.setParameter("nome", "%" + filtro.getNome() + "%");
		}
		if (filtro.getComunidade() != null) {
			query.setParameter("comun", filtro.getComunidade());
		}
		if (filtro.getSituacaoFundiaria() != null) {
			query.setParameter("situacao_fundiaria", filtro.getSituacaoFundiaria().name());
		}
		if (filtro.getSistemaPredominante() != null) {
			query.setParameter("sisprod", filtro.getSistemaPredominante());
		}
		if (filtro.getOutorga() != null) {
			query.setParameter("outorga", filtro.getOutorga().name());
		}

		return query.list();
	}

	@Override
	public Object teste(String sql) {
		return getSession().getCurrentSession().createQuery(sql.toString()).list();
	}

}