package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Producao;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Produção
 * 
 * @author frazao
 * 
 */
public interface ProducaoDao extends _CrudDao<Producao, Integer> {

	List<Map<String, Object>> restoreByPrevisaoProducaoId(Integer previsaoProducaoId);

}