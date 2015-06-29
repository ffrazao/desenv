package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PrevisaoProducao;
import gov.emater.aterweb.mvc.dto.IndiceProducaoCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de PerspectivaProducao
 * 
 * @author frazao
 * 
 */
public interface PrevisaoProducaoDao extends _CrudDao<PrevisaoProducao, Integer> {

	List<Map<String, Object>> restoreByDto(IndiceProducaoCadFiltroDto filtro);

}
