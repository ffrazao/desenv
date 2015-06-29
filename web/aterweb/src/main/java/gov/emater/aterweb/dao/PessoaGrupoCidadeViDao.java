package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupoCidadeVi;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de PessoaGrupoCidadeVi
 * 
 * @author frazao
 * 
 */
public interface PessoaGrupoCidadeViDao extends _CrudDao<PessoaGrupoCidadeVi, Integer> {

	List<Map<String, Object>> restoreMap(Integer pai);

}