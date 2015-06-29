package gov.emater.aterweb.dao;

import java.util.List;

import gov.emater.aterweb.model.AtividadeArquivo;

/**
 * Define os m�todos exclusivos ao DAO de AtividadeArquivo
 * 
 * @author frazao
 * 
 */
public interface AtividadeArquivoDao extends _CrudDao<AtividadeArquivo, Integer> {

	List<AtividadeArquivo> restorePorAtividadeId(Integer id);

}