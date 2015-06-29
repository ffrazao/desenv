package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.dao._ChavePrimaria;
import gov.emater.aterweb.model.EntidadeBase;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.service.CrudService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Modelo de auxï¿½lio aos demais controllers
 * 
 */
public abstract class _BaseController {

	private static final Logger logger = Logger.getLogger(_BaseController.class);

	protected static final String NORMAL_SUFIX = "/";

	protected static final String POPUP_SUFIX = "-pop/";

	@SuppressWarnings("rawtypes")
	private CrudService service;

	@SuppressWarnings("rawtypes")
	public _BaseController(CrudService service) {
		this.service = service;
	}

	/**
	 * Funcao de inicializacao de paginas do tipo cadastro
	 * 
	 * @param modo
	 *            modo de exibicao, pode ser N("Normal") ou P("Popup").
	 * @param opcao
	 *            opcoes da tela disponiveis, FI("Filtro"), LI("Listagem") ou
	 *            FO("Formulario")
	 * @param filtro
	 *            objeto de inicializacao do filtro
	 * @param id
	 *            array de ids de inicializacao
	 * @param registro
	 *            objeto de inicalizacao do registro
	 * @param tipoSelecao
	 *            tipo de selecao disponivel, S("Simples") ou M("Multipla")
	 * @return Tela a ser aberta
	 */
	public ModelAndView abrir(CadastroModo modo, CadastroOpcao opcao[], String urlPadrao, String _filtro, Integer id[], String _registro, CadastroTipoSelecao tipoSelecao[]) {
		if (logger.isDebugEnabled()) {
			logger.debug("Abrindo - " + getTitulo());
		}

		// convertendo e inicializando variaveis
		Object filtro = criarClasse(_filtro);
		Object registro = criarClasse(_registro);
		return abrirInterno(modo, opcao, urlPadrao, filtro, id, registro, tipoSelecao);
	}

	protected ModelAndView abrirInterno(CadastroModo modo, CadastroOpcao opcao[], String urlPadrao, Object filtro, Integer id[], Object registro, CadastroTipoSelecao tipoSelecao[]) {
		ModelAndView result = new ModelAndView();
		switch (modo) {
		case N:
			result.setViewName(getPagina() + NORMAL_SUFIX + getTitulo());
			break;
		case P:
			result.setViewName(getPagina() + POPUP_SUFIX + getTitulo());
			break;
		default:
			throw new RuntimeException("Modo de abertura inválida");
		}
		if (opcao == null) {
			opcao = CadastroOpcao.values();
		}
		if (tipoSelecao == null) {
			tipoSelecao = CadastroTipoSelecao.values();
		}
		result.addObject("modo", converterParaJson(modo));
		result.addObject("opcao", converterParaJson(opcao));
		result.addObject("urlPadrao", converterParaJson(urlPadrao == null ? "/" : urlPadrao));
		result.addObject("filtro", converterParaJson(filtro));
		result.addObject("id", converterParaJson(id));
		result.addObject("registro", converterParaJson(registro));
		result.addObject("tipoSelecao", converterParaJson(tipoSelecao));
		return result;
	}

	public static String converterParaJson(Object o) {
		if (o == null) {
			return "null";
		}
		String result = null;
		try {
			result = createObjectMapper().writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ObjectMapper createObjectMapper() {
		ObjectMapper result = new ObjectMapper();
		result.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// remover todos os itens nulos ou vazios das serializacoes json
		result.setSerializationInclusion(Include.NON_NULL);
		result.setSerializationInclusion(Include.NON_EMPTY);

		return result;
	}

	protected Object criarClasse(String classe) {
		Object result = null;
		if (classe != null) {
			ObjectMapper om = createObjectMapper();
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> elementos = om.readValue(classe, Map.class);
				Class<?> tipo = Class.forName((String) elementos.get("@class"));
				String conteudo = classe.substring(classe.indexOf("=") + 1);
				result = om.readValue(conteudo, tipo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> criarMapa(String classe) {
		Map<String, Object> result = null;
		if (classe != null) {
			ObjectMapper om = createObjectMapper();
			try {
				result = om.readValue(classe, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public JsonResponse detalhar(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] detalhando o id [%d]", getPagina(), id));
		}
		JsonResponse result = null;
		try {
			List<?> detalhe = getService().detalhar(id);
			result = new JsonResponse(true, "Sucesso", detalhe);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public JsonResponse excluir(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] excluindo o id [%d]", getPagina(), id));
		}
		JsonResponse result = null;
		try {
			getService().deleteById(id);
			result = new JsonResponse(true, "Sucesso");
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	public JsonResponse filtrar(Dto filtro, BindingResult bindingResult) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] filtrando o dto [%s]", getPagina(), filtro));
		}
		JsonResponse result = null;
		try {
			List<?> lista = getService().filtrarByDto(filtro);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	protected abstract String getPagina();

	@SuppressWarnings("rawtypes")
	protected CrudService getService() {
		return this.service;
	}

	protected abstract String getTitulo();

	protected Usuario getUsuario(Principal principal) {
		Usuario usuario = null;
		if (principal != null) {
			usuario = (Usuario) ((Authentication) principal).getPrincipal();
		}
		return usuario;
	}

	public JsonResponse preparar() {
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando - " + getTitulo());
		}

		JsonResponse result = null;

		Map<String, Object> variaveis = new HashMap<String, Object>();
		prepararInterno(variaveis);

		result = new JsonResponse(true, "Sucesso", variaveis);

		return result;
	}

	// variaveis.put(VAR_FILTRO, new PessoaCadFiltroDto());
	// variaveis.put(VAR_LISTA, new ArrayList<Object>());
	// variaveis.put(VAR_REGISTRO, new Object());
	protected abstract void prepararInterno(Map<String, Object> variaveis);

	public JsonResponse restaurar(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] restaurando o id [%d]", getPagina(), id));
		}
		JsonResponse result = null;
		try {
			EntidadeBase entidade = restaurarInterno(id);
			if (entidade != null) {
				result = new JsonResponse(true, "Sucesso", entidade);
			} else {
				result = new JsonResponse(false, "Nenhum registro encontrado");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	public JsonResponse incluir(Principal principal) {
		if (logger.isDebugEnabled()) {
			logger.debug("Incluindo novo registro");
		}
		JsonResponse result = null;
		try {
			EntidadeBase entidade = incluirInterno(getUsuario(principal));
			result = new JsonResponse(true, "Sucesso", entidade);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	protected EntidadeBase incluirInterno(Usuario usuario) {
		return new EntidadeBase();
	}

	// Pessoa pessoa = id == null ? new PessoaFisica() :
	// getService().restore(id);
	protected abstract EntidadeBase restaurarInterno(Integer id);

	@SuppressWarnings("unchecked")
	public JsonResponse salvar(EntidadeBase entity, BindingResult bindingResult) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] salvando a entidade [%s]", getPagina(), entity));
		}
		JsonResponse result = null;
		try {
			if (bindingResult.hasErrors()) {
				result = new JsonResponse(false, "Erros", bindingResult.getAllErrors());
			} else {
				result = new JsonResponse(true, "Sucesso", getService().save((_ChavePrimaria<Integer>) entity));
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}