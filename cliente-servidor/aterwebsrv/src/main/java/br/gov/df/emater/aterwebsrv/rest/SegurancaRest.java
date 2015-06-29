package br.gov.df.emater.aterwebsrv.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.emater.aterwebsrv.bo.FacadeBo;

@RestController
public class SegurancaRest {
	
	@Autowired
	private FacadeBo servicoFacade;

	@RequestMapping("/login")
	public Resposta login() {
		servicoFacade.executar("testeChd");
		return new Resposta();
	}
	
}