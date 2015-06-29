package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.UsuarioDao;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.model.UsuarioModulo;
import gov.emater.aterweb.model.UsuarioPerfil;
import gov.emater.aterweb.model.domain.UsuarioStatusConta;
import gov.emater.aterweb.mvc.dto.UsuarioCadFiltroDto;

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
public class UsuarioDaoImpl extends _CrudDaoImpl<Usuario, Integer> implements
		UsuarioDao {
	
	public UsuarioDaoImpl() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreByDto(UsuarioCadFiltroDto filtro, Integer registroInicial, Integer registrosPorPagina) {
		
		Criteria criteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		criteria.createCriteria("pessoa", "pessoa");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("this.id"), "id");
		projections.add(Projections.property("pessoa.nome"), "nome");
		projections.add(Projections.property("this.nomeUsuario"), "nomeUsuario");
		projections.add(Projections.property("this.usuarioStatusConta"), "usuarioStatusConta");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("pessoa.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		if (!isEmpty(filtro.getNome())) {
			criteria.add(Restrictions.like("pessoa.nome", filtro.getNome().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		if (!isEmpty(filtro.getNomeUsuario())) {
			criteria.add(Restrictions.like("this.nomeUsuario", filtro.getNomeUsuario().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		if (filtro.getUsuarioStatusConta() != null) {
			criteria.add(Restrictions.eq("this.usuarioStatusConta", filtro.getUsuarioStatusConta()));
		}
		
		List<Map<String, Object>> result = (List<Map<String, Object>>) findByCriteria(criteria, registroInicial, registrosPorPagina);
		
		if (result != null) {
			for (Map<String, Object> reg: result) {
				Integer usuarioId = (Integer) reg.get("id");
				reg.put("usuarioStatusConta", ((UsuarioStatusConta) reg.get("usuarioStatusConta")).toString());
				reg.put("usuarioModuloList", getUsuarioModuloList(usuarioId));
				reg.put("usuarioPerfilList", getUsuarioPerfilList(usuarioId));
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getUsuarioModuloList(Integer usuarioId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(UsuarioModulo.class, "this");
		criteria.createCriteria("modulo", "modulo");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("modulo.id"), "id");
		projections.add(Projections.groupProperty("modulo.nome"), "nome");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("modulo.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		criteria.add(Restrictions.eq("usuario.id", usuarioId));
		
		List<Map<String, Object>> result = criteria.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getUsuarioPerfilList(Integer usuarioId) {
		Criteria criteria = getSession().getCurrentSession().createCriteria(UsuarioPerfil.class, "this");
		criteria.createCriteria("perfil", "perfil");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("perfil.id"), "id");
		projections.add(Projections.groupProperty("perfil.nome"), "nome");
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("perfil.nome"));
		criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		criteria.add(Restrictions.eq("usuario.id", usuarioId));
		
		List<Map<String, Object>> result = criteria.list();
		return result;
	}

}