package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.model.EntidadeBase;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.PropriedadeRuralCadFiltroDto;
import gov.emater.aterweb.service.PropriedadeRuralService;

import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Gerencia requisições para Propriedades.
 */
@Controller
@RequestMapping(value = "/" + PropriedadeRuralController.PAGINA)
public class PropriedadeRuralController extends _BaseController implements CadastroController<PropriedadeRural, PropriedadeRuralCadFiltroDto> {

	private static final Logger logger = Logger.getLogger(PropriedadeRuralController.class);

	public static final String PAGINA = "propriedade-rural-cad";

	public static final String TITULO = "Cadastro de Propriedades";

	@Autowired
	public PropriedadeRuralController(PropriedadeRuralService service) {
		super(service);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView abrir(@RequestParam(value = "modo", required = false, defaultValue = "N") CadastroModo modo, @RequestParam(value = "opcao", required = false) CadastroOpcao opcao[], @RequestParam(value = "urlPadrao", required = false) String urlPadrao,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "id", required = false) Integer id[], @RequestParam(value = "registro", required = false) String registro,
			@RequestParam(value = "tipoSelecao", required = false) CadastroTipoSelecao tipoSelecao[]) {
		return super.abrir(modo, opcao, urlPadrao, filtro, (java.lang.Integer[]) id, registro, tipoSelecao);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_EXCLUIR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse excluir(@RequestParam Integer id) {
		return super.excluir(id);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_FILTRAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse filtrar(PropriedadeRuralCadFiltroDto filtro, BindingResult bindingResult) {
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
	@RequestMapping(value = "/" + ACAO_PREPARAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse preparar() {
		return super.preparar();
	}

	@Override
	protected void prepararInterno(Map<String, Object> variaveis) {
		variaveis.put(VAR_FILTRO, new PropriedadeRuralCadFiltroDto());
		variaveis.put(VAR_LISTA, new ArrayList<Object>());
		variaveis.put(VAR_REGISTRO, new PropriedadeRural());
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("[%s] preparando [%s]", getPagina(), variaveis));
		}
	}

	@Override
	@RequestMapping(value = "/" + ACAO_RESTAURAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse restaurar(@RequestParam Integer id) {
		return super.restaurar(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EntidadeBase restaurarInterno(Integer id) {
		return (EntidadeBase) (id == null ? new PropriedadeRural() : getService().restore(id));
	}

	@Override
	@RequestMapping(value = "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestBody PropriedadeRural entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

	@RequestMapping(value = "/" + ACAO_RESTAURAR + "ComunidadeBaciaHidrografica", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse restoreComunicadeBaciaHidrografica(@RequestParam Integer id) {

		JsonResponse result = null;
		try {
			result = new JsonResponse(true, "Sucesso", ((PropriedadeRuralService) getService()).restoreComunicadeBaciaHidrografica(new PessoaGrupo(id)));
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}