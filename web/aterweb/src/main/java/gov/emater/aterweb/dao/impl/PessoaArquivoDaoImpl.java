package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PessoaArquivoDao;
import gov.emater.aterweb.model.PessoaArquivo;
import gov.emater.aterweb.model.domain.Confirmacao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaArquivoDaoImpl extends _CrudDaoImpl<PessoaArquivo, Integer> implements PessoaArquivoDao {

	@Override
	public String getFotoPerfil(Integer id) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("arquivo.md5"), "md5");
		projections.add(Projections.property("arquivo.extensao"), "extensao");
		criteria.setProjection(projections);
		criteria.add(Restrictions.eq("perfil", Confirmacao.S));
		criteria.add(Restrictions.eq("pessoa.id", id));
		criteria.createAlias("arquivo", "arquivo");
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> result = criteria.list();
		if (!isEmpty(result)) {
			String md5 = (String) result.get(0).get("md5");
			String extensao = (String) result.get(0).get("extensao");
			return String.format("resources/upload/%s%s", md5, extensao);
		}
		return "resources/img/pessoa_padrao.png";
	}

}