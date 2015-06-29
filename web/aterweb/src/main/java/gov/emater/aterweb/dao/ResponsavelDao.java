package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Responsavel;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de Responsavel pela Produ��o
 * 
 * @author frazao
 * 
 */
public interface ResponsavelDao extends _CrudDao<Responsavel, Integer> {

	List<Map<String, Object>> restoreByProducaoId(Integer producaoId);

}