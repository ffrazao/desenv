package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.mvc.dto.PropriedadeRuralCadFiltroDto;

import java.util.List;

/**
 * Define os métodos exclusivos ao DAO de Pessoa Física
 * 
 * @author frazao
 * 
 */
public interface PropriedadeRuralDao extends _CrudDao<PropriedadeRural, Integer> {

	List<?> restoreByDto(PropriedadeRuralCadFiltroDto filtro);

	Object teste(String sql);

}