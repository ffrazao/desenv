package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.service.ArquivoService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RequestMapping(value = "/" + ArquivoController.PAGINA)
@Controller
public class ArquivoController {

	public static final String PAGINA = "arquivo";

	public static final String TITULO = "Gestão de Arquivos";

	@Autowired
	private ArquivoService service;

	@RequestMapping(value = "/descer", method = RequestMethod.GET)
	public void descer(@RequestParam String arq, HttpServletRequest request, HttpServletResponse response) {
		service.descer(request, response, arq);
	}

	@RequestMapping(value = "/subir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subir(MultipartHttpServletRequest request) {
		return service.subir(request);
	}
}