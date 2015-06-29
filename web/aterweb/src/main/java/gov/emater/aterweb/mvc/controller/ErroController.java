package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.model.Usuario;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErroController {

	@RequestMapping("/erro")
	public String trataErro(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model) {
		Usuario usuario = null;
		if (principal != null) {
			usuario = (Usuario) ((Authentication) principal).getPrincipal();
		}
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Desconhecido";
		}
		String mensagemErro = "Erro desconhecido!";
		StringBuilder stackTraceList = new StringBuilder();
		if (throwable != null) {
			mensagemErro = throwable.getCause().getMessage();
			int linha = 0;
			String complemento = "";
			for (StackTraceElement elemento : throwable.getStackTrace()) {
				stackTraceList.append(String.format("%s%s<br/>", complemento, elemento.toString()));
				complemento = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if (linha++ > 1000) {
					stackTraceList.append("...");
					break;
				}
			}
		}
		String statusMessage = "";
		if (statusCode != null) {
			statusMessage = HttpStatus.valueOf(statusCode).getReasonPhrase();
			if (statusCode.equals(404)) {
				mensagemErro = "página ou recurso não encontrado!";
			}
		}
		
		if (usuario != null) {
		    model.addAttribute("usuario", String.format("usuario: [%s]<br/> ", usuario.getNomeUsuario()));
		}

		model.addAttribute("horario", String.format("%s", SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())));		
		model.addAttribute("pagina", String.format("%s", requestUri));
		model.addAttribute("codigo", String.format("%s", statusCode));
		model.addAttribute("erro", String.format("%s", statusMessage));
		model.addAttribute("mensagem", String.format("%s", mensagemErro));
		model.addAttribute("stackTraceList", stackTraceList.toString());

		return "exibe-erro|Erro ao executar o aplicativo";
	}
}