package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.ModuloDao;
import gov.emater.aterweb.model.Modulo;
import gov.emater.aterweb.model.ModuloPerfil;
import gov.emater.aterweb.mvc.dto.ModuloCadFiltroDto;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ModuloDaoImpl extends _CrudDaoImpl<Modulo, Integer> implements
		ModuloDao {

	public ModuloDaoImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreByDto(ModuloCadFiltroDto filtro,
			Integer registroInicial, Integer registrosPorPagina) {
		
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("this.id"), "id");
		projections.add(Projections.property("this.nome"), "nome");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("this.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		if (!isEmpty(filtro.getNome())) {
			criteria.add(Restrictions.like("nome", filtro.getNome().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		
		List<Map<String, Object>> result = (List<Map<String, Object>>) findByCriteria(criteria, registroInicial, registrosPorPagina);
		
		if (result != null) {
			for (Map<String, Object> reg: result) {
				reg.put("moduloPerfilList", getModuloPerfilList((Integer) reg.get("id")));
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getModuloPerfilList(Integer moduloId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(ModuloPerfil.class, "this");
		criteria.createCriteria("perfil", "perfil");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("perfil.id"), "id");
		projections.add(Projections.property("perfil.nome"), "nome");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("perfil.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		criteria.add(Restrictions.eq("modulo.id", moduloId));
		
		List<Map<String, Object>> result = criteria.list();
		return result;
	}

}