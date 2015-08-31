package br.gov.df.emater.aterwebsrv.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.emater.aterwebsrv.bo.FacadeBo;

@RestController
public class SegurancaRest {
	
	@Autowired
	private FacadeBo facadeBo;

	@RequestMapping("/login")
	public Resposta login() {
		facadeBo.executar("testeChd");
		return new Resposta();
	}
	
}