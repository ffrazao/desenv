package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.mvc.dto.UsuarioCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de Usuario
 * 
 * @author frazao
 * 
 */
public interface UsuarioDao extends _CrudDao<Usuario, Integer> {

	List<Map<String, Object>> restoreByDto(UsuarioCadFiltroDto filtro, Integer registroInicial, Integer registrosPorPagina);

}