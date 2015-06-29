package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.service.ConstantesService;
import gov.emater.aterweb.service.UtilService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Classe utilitaria para os Controladores do sistema
 * 
 * @author frazao
 * 
 */
@Controller
public class UtilController {

	private static final Logger logger = Logger.getLogger(UtilController.class);

	@Autowired
	private ConstantesService constantesService;

	@Autowired
	private UtilService utilService;

	/**
	 * Método para execução de queries livres no banco de dados
	 * 
	 * @param sql
	 *            a ser executada
	 * @param params
	 *            parametros da query
	 * @return resultado da query em formato JSon
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse executaQuery(@RequestParam String sql, @RequestParam(value = "p", required = false) List<String> params) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Executando query genérica [%s]", sql));
		}

		JsonResponse result = null;
		try {

			List<?> lista = utilService.executaQuery(sql, params);
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Foram recuperados [%d] registros do sql [%s]", lista.size(), sql));
			}
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Método genérico para retorno de entidades de domínio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave primária da entidade
	 * @param valorPk
	 *            valor da chave primária da entidade
	 * @return a relação das entidades em formato JSon
	 */
	@RequestMapping(value = "/dominio", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getDominio(@RequestParam String ent, @RequestParam(required = false) String npk, @RequestParam(required = false) String vpk, @RequestParam(required = false) String ord,
			String[] fetchs) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Recuperando domínio para Entidade[%s], NomeChavePrimaria[%s], ValorChavePrimária[%s]", ent, npk, vpk));
		}

		JsonResponse result = null;
		try {
			List<?> lista = null;
			try {
				lista = utilService.getDominio(ent, npk, Integer.parseInt(vpk), ord, fetchs);
			} catch (NumberFormatException e) {
				lista = utilService.getDominio(ent, npk, vpk, ord, fetchs);
			}
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Foram recuperados [%d] registros de domínio da Entidade[%s]", lista.size(), ent));
			}
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Método genérico para retorno de entidades de domínio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave primária da entidade
	 * @param valorPk
	 *            valor da chave primária da entidade
	 * @return a relação das entidades em formato JSon
	 */
	@RequestMapping(value = "/enumeracao", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getEnumeracao(@RequestParam String nome) {
		
		JsonResponse result = null;
		try {
			List<?> lista = null;
			lista = utilService.getEnumeracao(nome);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Método genérico para retorno de entidades de PessoaGrupo do tipo Pais,
	 * Estado, Municipio ou Cidade. Util para componentes do tipo treeview.
	 * 
	 * @return a relação das localidades em formato JSon
	 */
	@RequestMapping(value = "/pessoaGrupoJson", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPessoaGrupoJson() {
		JsonResponse result = null;
		try {
			String pessoaGrupo = constantesService.getPessoaGrupoJson();
			if (pessoaGrupo == null || pessoaGrupo.trim().length() == 0) {
				result = new JsonResponse(false, "Lista Vazia");
			} else {
				result = new JsonResponse(true, "Sucesso", pessoaGrupo);
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}