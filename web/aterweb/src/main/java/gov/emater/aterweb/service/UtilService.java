package gov.emater.aterweb.service;

import java.util.List;
import java.util.Map;

public interface UtilService extends Service {

	List<?> executaQuery(String sql, List<?> params);

	/**
	 * Método genérico para retorno de entidades de domínio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave primária da entidade
	 * @param valorChavePrimaria
	 *            valor da chave primária da entidade
	 * @param order
	 *            string para definir a ordem dos dados
	 * @return a relação das entidades
	 */
	List<?> getDominio(String ent, String npk, Integer vpk, String order, String[] fetchs) throws Exception;

	/**
	 * Método genérico para retorno de entidades de domínio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave primária da entidade
	 * @param valorChavePrimaria
	 *            valor da chave primária da entidade
	 * @param order
	 *            string para definir a ordem dos dados
	 * @return a relação das entidades
	 */
	List<?> getDominio(String ent, String npk, String vpk, String order, String[] fetchs) throws Exception;

	/**
	 * Método genérico para retorno de Enumerações do sistema (enum)
	 * 
	 * @param enumeracao
	 *            nome da Enumeração a ser chamada
	 */
	List<Map<String, Object>> getEnumeracao(String enumeracao) throws Exception;

}
