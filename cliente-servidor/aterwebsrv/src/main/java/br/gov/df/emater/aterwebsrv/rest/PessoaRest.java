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
	
	public PessoaRest() {
		System.out.println("novo PessoaRest");
	}

	@Autowired
	private FacadeBo facadeBo;

	@RequestMapping("/filtro-novo")
	public Resposta filtroNovo() {
		Context ctx = new ContextBase();
		//facadeBo.executar("pessoa-filtro-novo", ctx);
		facadeBo.executar("atividade", "atividadeChd");
		return new Resposta(ctx.get("resultado"));
	}

}