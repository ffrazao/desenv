package gov.emater.aterweb.mvc.controller;

import static gov.emater.aterweb.mvc.controller._BaseController.NORMAL_SUFIX;
import static gov.emater.aterweb.mvc.controller._BaseController.POPUP_SUFIX;
import static gov.emater.aterweb.mvc.controller._BaseController.converterParaJson;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Gerencia requisições para Email.
 */
@Controller
@RequestMapping(value = "/" + EmailController.PAGINA)
public class EmailController {

	private static final Logger logger = Logger.getLogger(EmailController.class);

	public static final String PAGINA = "email";

	public static final String TITULO = "Caixa de Email";

	public EmailController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView abrir(@RequestParam(value = "modo", required = false, defaultValue = "N") CadastroModo modo, @RequestParam(value = "opcao", required = false) CadastroOpcao opcao[], @RequestParam(value = "urlPadrao", required = false) String urlPadrao,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "id", required = false) Integer id[], @RequestParam(value = "registro", required = false) String registro,
			@RequestParam(value = "tipoSelecao", required = false) CadastroTipoSelecao tipoSelecao[]) {
		logger.info("Abrindo a caixa de email");
		return abrirInterno(modo, opcao, urlPadrao, filtro, id, registro, tipoSelecao);
	}
	
	protected ModelAndView abrirInterno(CadastroModo modo, CadastroOpcao opcao[], String urlPadrao, Object filtro, Integer id[], Object registro, CadastroTipoSelecao tipoSelecao[]) {
		ModelAndView result = new ModelAndView();
		switch (modo) {
		case N:
			result.setViewName(getPagina() + "|" + getTitulo());
			break;
		case P:
			result.setViewName(getPagina() + "-pop|" + getTitulo());
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

	protected String getPagina() {
		return PAGINA;
	}

	protected String getTitulo() {
		return TITULO;
	}

}