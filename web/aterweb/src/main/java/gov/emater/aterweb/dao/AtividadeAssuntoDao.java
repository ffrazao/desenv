package gov.emater.aterweb.dao;

import java.util.List;

import gov.emater.aterweb.model.AtividadeAssunto;

/**
 * Define os m�todos exclusivos ao DAO de AtividadeAssunto
 * 
 * @author frazao
 * 
 */
public interface AtividadeAssuntoDao extends _CrudDao<AtividadeAssunto, Integer> {

	List<AtividadeAssunto> restorePorAtividade(Integer id);

}