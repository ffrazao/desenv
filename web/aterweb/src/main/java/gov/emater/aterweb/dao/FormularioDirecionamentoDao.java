package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.Usuario;

import java.util.List;

/**
 * Define os m�todos exclusivos de Formul�rio com Direcionamento de Enquete
 * 
 * @author frazao
 * 
 */
public interface FormularioDirecionamentoDao extends _CrudDao<FormularioDirecionamento, Integer> {

	List<Object[]> formulariosAtivos(Usuario usuario);

	List<Object[]> getAvaliacao();

}