package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.Usuario;

import java.util.List;

/**
 * Define os métodos exclusivos de Formulário com Direcionamento de Enquete
 * 
 * @author frazao
 * 
 */
public interface FormularioDirecionamentoDao extends _CrudDao<FormularioDirecionamento, Integer> {

	List<Object[]> formulariosAtivos(Usuario usuario);

	List<Object[]> getAvaliacao();

}