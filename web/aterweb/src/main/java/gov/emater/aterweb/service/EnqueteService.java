package gov.emater.aterweb.service;

import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.Usuario;

import java.util.List;
import java.util.Map;

public interface EnqueteService extends CrudService<FormularioDirecionamento, Integer> {

	FormularioDirecionamento formularioResponder(Integer formularioDirecionamentoId, Usuario usuario);

	void formularioRespostaRemover(Integer respostaVersaoId, Usuario usuario);

	FormularioDirecionamento formularioRespostaVer(Integer respostaVersaoId, Usuario usuario);

	FormularioDirecionamento formularioRespostaSalvar(FormularioDirecionamento formularioDirecionamento, Usuario usuario);

	List<FormularioDirecionamento> formulariosAtivos(Usuario usuario);

	List<Map<String, Object>> getAvaliacao();

}