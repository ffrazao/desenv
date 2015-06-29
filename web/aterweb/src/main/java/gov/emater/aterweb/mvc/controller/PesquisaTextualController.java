package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.service.PesquisaTextualService;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the Pesquisas Textuais.
 */
@Controller
@RequestMapping(value = "/" + PesquisaTextualController.PAGINA)
public class PesquisaTextualController {

	private static final Logger logger = Logger.getLogger(PesquisaTextualController.class);

	public static final String PAGINA = "pesquisa-textual";

	public static final String TITULO = "Pesquisa Textual";

	@Autowired
	private PesquisaTextualService service;

	@RequestMapping(value = "/pesquisar", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pesquisar(@RequestParam String query) {
		logger.debug("Pesquisando - " + query);
		Map<String, Object> result = null;

		result = service.pesquisar(query);

		return result;
	}

	@RequestMapping(value = "/reindexar", method = RequestMethod.GET)
	@ResponseBody
	public boolean reindexar() {
		logger.debug("Reindexando a base de pesquisas...");

		boolean result = service.reindexar();

		return result;
	}
}