package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Formulario;

import java.util.List;

/**
 * Define os m�todos exclusivos de Formul�rio de Enquete
 * 
 * @author frazao
 * 
 */
public interface FormularioDao extends _CrudDao<Formulario, Integer> {

	List<Formulario> formulariosAtivos();

}