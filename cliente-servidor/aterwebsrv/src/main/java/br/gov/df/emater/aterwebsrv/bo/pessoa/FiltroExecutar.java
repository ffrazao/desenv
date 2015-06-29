package br.gov.df.emater.aterwebsrv.bo.pessoa;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaTipo;
import br.gov.df.emater.aterwebsrv.modelo.dto.PessoaCadFiltroDto;

public class FiltroExecutar implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		PessoaCadFiltroDto filtro = new PessoaCadFiltroDto();
		filtro.setTipoPessoa(PessoaTipo.PF);
		context.put("resultado", filtro);
		return false;
	}

}
