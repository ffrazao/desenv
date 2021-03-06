package gov.emater.aterweb.mvc.controller;

import java.security.Principal;

import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface padr�o para controladores do tipo cadastro (inclus�o, altera��o,
 * exclus�o e listagem)
 * 
 * @author frazao
 * 
 * @param <E>
 *            Entidade padr�o do cadastro
 * @param <P>
 *            DTO (Data Transformation Object) utilizado para o filtro do
 *            cadastro
 */
public interface CadastroController<E, P> {

	String ACAO_DETALHAR = "detalhar";

	String ACAO_EXCLUIR = "excluir";

	String ACAO_FILTRAR = "filtrar";

	String ACAO_INCLUIR = "incluir";

	String ACAO_PREPARAR = "preparar";

	String ACAO_RESTAURAR = "restaurar";

	String ACAO_SALVAR = "salvar";

	String VAR_FILTRO = "filtro";

	String VAR_LISTA = "lista";

	String VAR_REGISTRO = "registro";

	/**
	 * Abrir a tela principal do cadastro
	 * 
	 * @return o endere�o do cadastro
	 */
	ModelAndView abrir(CadastroModo modo, CadastroOpcao opcao[], String urlPadrao, String _filtro, Integer id[], String _registro, CadastroTipoSelecao tipoSelecao[]);

	/**
	 * Efetua a exclus�o de registros do controller
	 * 
	 * @param id
	 *            Chave Prim�ria do registro a ser exclu�do
	 * @param bindingResult
	 *            Mensagens de erro ocorridas durante a exclus�o do registro
	 */
	JsonResponse excluir(Integer id);

	/**
	 * Efetua o filtro de registros a serem exibidos na listagem dos dados
	 * 
	 * @param params
	 *            O dto utilizado como filtro de registros
	 * @param bindingResult
	 *            Mensagens de erro ocorrida durante a filtragem
	 * @return Lista de dados que ser� transformado em json
	 */
	JsonResponse filtrar(P params, BindingResult bindingResult);

	/**
	 * Criar uma nova entidade a ser inserida no sistema.
	 * 
	 * @param principal
	 *            informa��o de quem esta solicitando a inser��o
	 */
	JsonResponse incluir(Principal principal);

	/**
	 * Prepara a exibi��o do cadastro
	 * 
	 * @return para o endere�o do cadastro
	 */
	JsonResponse preparar();

	/**
	 * Restaura o valor de uma entidade. Utilizado para a exibi��o do formul�rio
	 * de inclus�o e altera��o de dados
	 * 
	 * @param id
	 *            Chave Prim�ria da entidade a ser restaurada
	 * @return A entidade restaurada que ser� transforada em json
	 */
	JsonResponse restaurar(Integer id);

	/**
	 * Executa a inclus�o (caso a chave prim�ria da entidade seja nula) ou a
	 * altera��o (caso a chave prim�ria da entidade tenha sido informada).
	 * 
	 * @param e
	 *            entidade a ser salva
	 * @param bindingResult
	 *            mensagens de erro
	 */
	JsonResponse salvar(E e, BindingResult bindingResult);
	
}