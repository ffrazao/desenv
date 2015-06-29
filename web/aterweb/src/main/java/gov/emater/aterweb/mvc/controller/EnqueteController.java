package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.model.EntidadeBase;
import gov.emater.aterweb.model.Formulario;
import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.EnqueteCadFiltroDto;
import gov.emater.aterweb.service.EnqueteService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Gerencia requisições para Enquetes.
 */
@Controller
public class EnqueteController extends _BaseController implements CadastroController<Formulario, EnqueteCadFiltroDto> {

	private static final Logger logger = Logger.getLogger(EnqueteController.class);

	public static final String PAGINA = "enquete-cad";

	public static final String TITULO = "Enquetes Sociais";

	@Autowired
	public EnqueteController(EnqueteService service) {
		super(service);
	}

	@Override
	@RequestMapping(value = PAGINA, method = RequestMethod.GET)
	public ModelAndView abrir(@RequestParam(value = "modo", required = false, defaultValue = "N") CadastroModo modo, @RequestParam(value = "opcao", required = false) CadastroOpcao opcao[], @RequestParam(value = "urlPadrao", required = false) String urlPadrao,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "id", required = false) Integer id[], @RequestParam(value = "registro", required = false) String registro,
			@RequestParam(value = "tipoSelecao", required = false) CadastroTipoSelecao tipoSelecao[]) {
		return super.abrir(modo, opcao, urlPadrao, filtro, (java.lang.Integer[]) id, registro, tipoSelecao);
	}

	@Override
	@RequestMapping(value = "/" + EnqueteController.PAGINA + "/" + ACAO_EXCLUIR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse excluir(@RequestParam Integer id) {
		return super.excluir(id);
	}

	@Override
	@RequestMapping(value = "/" + EnqueteController.PAGINA + "/" + ACAO_FILTRAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse filtrar(EnqueteCadFiltroDto filtro, BindingResult bindingResult) {
		return super.filtrar(filtro, bindingResult);
	}

	@Override
	protected String getPagina() {
		return PAGINA;
	}

	@Override
	protected String getTitulo() {
		return TITULO;
	}

	@Override
	@RequestMapping(value = "/" + EnqueteController.PAGINA + "/" + ACAO_PREPARAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse preparar() {
		return super.preparar();
	}

	@Override
	public void prepararInterno(Map<String, Object> variaveis) {
		variaveis.put(VAR_FILTRO, new EnqueteCadFiltroDto());
		variaveis.put(VAR_LISTA, new ArrayList<Object>());
		variaveis.put(VAR_REGISTRO, new Object());
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("[%s] preparando [%s]", getPagina(), variaveis));
		}
	}

	@RequestMapping(value = "/enquete-formulario")
	@ResponseBody
	public JsonResponse getEnqueteFormulario(@RequestParam(required = false) Integer formularioId, @RequestParam(required = false) Integer respostaVersaoId, @RequestParam(required = true) boolean anonimo, Principal principal) {
		Usuario usuario = anonimo ? null : getUsuario(principal);
		if (formularioId == null && respostaVersaoId == null) {
			return getFormularios(usuario);
		} else {
			return getFormulario(formularioId, respostaVersaoId, usuario);
		}
	}

	public JsonResponse getFormularios(Usuario usuario) {
		JsonResponse result = null;
		try {
			List<FormularioDirecionamento> formularioDirecionamentoList = ((EnqueteService) getService()).formulariosAtivos(usuario);
			if (formularioDirecionamentoList != null && formularioDirecionamentoList.size() > 0) {
				result = new JsonResponse(true, "Sucesso", formularioDirecionamentoList);
			} else {
				result = new JsonResponse(false, "Nenhum registro encontrado!");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
		}
		return result;
	}

	public JsonResponse getFormulario(Integer formularioId, Integer respostaVersaoId, Usuario usuario) {
		JsonResponse result = null;
		try {
			FormularioDirecionamento formularioDirecionamento = null;
			if (respostaVersaoId != null) {
				formularioDirecionamento = ((EnqueteService) getService()).formularioRespostaVer(respostaVersaoId, usuario);
			} else {
				formularioDirecionamento = ((EnqueteService) getService()).formularioResponder(formularioId, usuario);
			}
			if (formularioDirecionamento != null) {
				result = new JsonResponse(true, "Sucesso", formularioDirecionamento);
			} else {
				result = new JsonResponse(false, "Nenhum registro encontrado!");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
		}
		return result;
	}

	@RequestMapping(value = "/enquete-avaliacao-dados")
	@ResponseBody
	public List<Map<String, Object>> getAvaliacao() {
		return ((EnqueteService) getService()).getAvaliacao();
	}

	@RequestMapping(value = "/enquete-avaliacao")
	public String abrirAvaliacao() {
		return "enquete-avaliacao|Avaliacoes";
	}

	@RequestMapping(value = "/enquete-anonima")
	public String formularioAnonimo(@RequestParam(required = false) Integer formularioId, @RequestParam(required = false) Integer respostaVersaoId) {
		return "enquete-resp|Responder Anï¿½nimo";
	}

	@RequestMapping(value = "/enquete-identificada")
	public String formularioIdentificado(@RequestParam(required = false) Integer formularioId, @RequestParam(required = false) Integer respostaVersaoId) {
		return "enquete-resp|Responder Identificado";
	}

	@RequestMapping(value = "/enquete-remover")
	@ResponseBody
	public void formularioRespostaRemover(@RequestParam Integer respostaVersaoId, Principal principal) {
		Usuario usuario = null;
		if (principal != null) {
			usuario = (Usuario) ((Authentication) principal).getPrincipal();
		}
		((EnqueteService) getService()).formularioRespostaRemover(respostaVersaoId, usuario);
	}

	@RequestMapping(value = "/enquete-salvar", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse formularioRespostaSalvar(@RequestBody FormularioDirecionamento registro, Principal principal) {
		Usuario usuario = null;
		if (principal != null) {
			usuario = (Usuario) ((Authentication) principal).getPrincipal();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] salvando respostas da enquete [%s]", getPagina(), registro.getFormulario().getNome()));
		}
		JsonResponse result = null;
		try {
			registro = ((EnqueteService) getService()).formularioRespostaSalvar(registro, usuario);
			result = new JsonResponse(true, "Sucesso", registro);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/" + EnqueteController.PAGINA + "/" + ACAO_RESTAURAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse restaurar(@RequestParam Integer id) {
		return super.restaurar(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected EntidadeBase restaurarInterno(Integer id) {
		return (EntidadeBase) (id == null ? new EntidadeBase() : getService().restore(id));
	}

	@Override
	@RequestMapping(value = "/" + EnqueteController.PAGINA + "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestParam Formulario entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

}