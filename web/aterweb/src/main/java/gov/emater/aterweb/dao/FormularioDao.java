package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Formulario;

import java.util.List;

/**
 * Define os métodos exclusivos de Formulário de Enquete
 * 
 * @author frazao
 * 
 */
public interface FormularioDao extends _CrudDao<Formulario, Integer> {

	List<Formulario> formulariosAtivos();

}