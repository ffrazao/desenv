package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaMeioContato;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Pessoa Meio Contato
 * 
 * @author frazao
 * 
 */
public interface PessoaMeioContatoDao extends _CrudDao<PessoaMeioContato, Integer> {

	List<Map<String, Object>> getProprietarioList(Integer id);

}