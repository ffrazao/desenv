package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Producao;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de Produ��o
 * 
 * @author frazao
 * 
 */
public interface ProducaoDao extends _CrudDao<Producao, Integer> {

	List<Map<String, Object>> restoreByPrevisaoProducaoId(Integer previsaoProducaoId);

}