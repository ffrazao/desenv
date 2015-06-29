package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Modulo;
import gov.emater.aterweb.mvc.dto.ModuloCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de Modulo
 * 
 * @author frazao
 * 
 */
public interface ModuloDao extends _CrudDao<Modulo, Integer> {
	
	List<Map<String, Object>> restoreByDto(ModuloCadFiltroDto filtro, Integer registroInicial, Integer registrosPorPagina);

}