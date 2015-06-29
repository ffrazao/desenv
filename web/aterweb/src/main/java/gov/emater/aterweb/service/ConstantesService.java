package gov.emater.aterweb.service;

import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.RelacionamentoTipo;

import javax.servlet.http.HttpServletRequest;

public interface ConstantesService extends Service {

	Integer REGISTROS_POR_PAGINA = 50;

	PessoaGrupoTipo getBaciaHidrograficaPessoaGrupoTipo();

	String getBaseUrl(HttpServletRequest request);

	PessoaGrupoTipo getComunidadePessoaGrupoTipo();

	PessoaGrupoTipo getPersonalizadoPessoaGrupoTipo();

	String getPessoaGrupoJson();

	PessoaGrupoTipo getPessoaGrupoTipo(gov.emater.aterweb.model.PessoaGrupoTipo.Codigo codigo);

	RelacionamentoTipo getRelacionamentoTipo(gov.emater.aterweb.model.RelacionamentoTipo.Codigo codigo);

	String getServletLocalDirectoryPath(HttpServletRequest request);

	String getServletResourcesLocalDirectoryPath(HttpServletRequest request);

}
