package br.gov.df.emater.aterwebsrv.rest;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.emater.aterwebsrv.bo.FacadeBo;

@RestController
@RequestMapping("/pessoa")
public class PessoaRest {

	@Autowired
	private FacadeBo servicoFacade;

	@RequestMapping("/filtro-novo")
	public Resposta filtroNovo() {
		Context ctx = new ContextBase();
		servicoFacade.executar("pessoa-filtro-novo", ctx);
		return new Resposta(ctx.get("resultado"));
	}

}