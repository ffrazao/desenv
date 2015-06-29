package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupoPaisVi;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de PessoaGrupoPaisVi
 * 
 * @author frazao
 * 
 */
public interface PessoaGrupoPaisViDao extends _CrudDao<PessoaGrupoPaisVi, Integer> {

	List<Map<String, Object>> restoreMap();

}