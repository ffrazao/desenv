package gov.emater.aterweb.dao;

import java.util.List;
import java.util.Map;

import gov.emater.aterweb.model.Exploracao;

/**
 * Define os métodos exclusivos ao DAO da tabela exploracao
 * 
 * @author frazao
 * 
 */
public interface ExploracaoDao extends _CrudDao<Exploracao, Integer> {

	List<Map<String, Object>> getProdutoresPorMeioContatoId(Integer meioContatoId);

}