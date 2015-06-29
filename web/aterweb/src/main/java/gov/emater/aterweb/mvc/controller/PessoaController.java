package gov.emater.aterweb.mvc.controller;

import static gov.emater.aterweb.comum.UtilitarioString.formataCep;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaFisica;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.PessoaCadFiltroDto;
import gov.emater.aterweb.service.PessoaService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Manipula requisições para o cadastro de pessoas
 * 
 */
@Controller
@RequestMapping(value = "/" + PessoaController.PAGINA)
public class PessoaController extends _BaseController implements CadastroController<Pessoa, PessoaCadFiltroDto> {

	private static final Logger logger = Logger.getLogger(PessoaController.class);

	public static final String PAGINA = "pessoa-cad";

	public static final String TITULO = "Cadastro de Pessoas";

	@Autowired
	public PessoaController(PessoaService service) {
		super(service);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView abrir(@RequestParam(value = "modo", required = false, defaultValue = "N") CadastroModo modo, @RequestParam(value = "opcao", required = false) CadastroOpcao opcao[], @RequestParam(value = "urlPadrao", required = false) String urlPadrao,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "id", required = false) Integer id[], @RequestParam(value = "registro", required = false) String registro,
			@RequestParam(value = "tipoSelecao", required = false) CadastroTipoSelecao tipoSelecao[]) {
		return super.abrir(modo, opcao, urlPadrao, filtro, (java.lang.Integer[]) id, registro, tipoSelecao);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_DETALHAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse detalhar(@RequestParam Integer id) {
		return super.detalhar(id);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_EXCLUIR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse excluir(@RequestParam Integer id) {
		return super.excluir(id);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_FILTRAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse filtrar(PessoaCadFiltroDto filtro, BindingResult bindingResult) {
		return super.filtrar(filtro, bindingResult);
	}

	@Override
	protected String getPagina() {
		return PAGINA;
	}

	@Override
	protected String getTitulo() {
		return TITULO;
	}

	@Override
	@RequestMapping(value = "/" + ACAO_PREPARAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse preparar() {
		return super.preparar();
	}

	@Override
	protected void prepararInterno(Map<String, Object> variaveis) {
		variaveis.put(VAR_FILTRO, new PessoaCadFiltroDto());
		variaveis.put(VAR_LISTA, new ArrayList<Object>());
		variaveis.put(VAR_REGISTRO, new PessoaFisica());
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("[%s] preparando [%s]", getPagina(), variaveis));
		}
	}

	@Override
	@RequestMapping(value = "/" + ACAO_RESTAURAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse restaurar(@RequestParam Integer id) {
		return super.restaurar(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Pessoa restaurarInterno(Integer id) {
		return (Pessoa) (id == null ? new PessoaFisica() : getService().restore(id));
	}

	@Override
	@RequestMapping(value = "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestBody Pessoa entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

	@RequestMapping(value = "/buscarCep", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse buscarCep(@RequestParam String cep) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Buscando o CEP [%s]", cep));
		}
		JsonResponse result = null;
		try {
			Map<String,Object> dados = servicoViaCep(cep);
			if (dados.get("erro") == null) {
				dados = ((PessoaService) getService()).completaDadosBuscaCep(dados);
				result = new JsonResponse(true, "Sucesso", dados);
			} else {
				result = new JsonResponse(false, "Erro ao buscar o CEP", "CEP não cadastrado!");
			}
		} catch (UnknownHostException e) {
			result = new JsonResponse(false, String.format("Servidor %s inacessivel!", e.getMessage()), e);
			e.printStackTrace();
		} catch (Exception e) {
			result = new JsonResponse(false, String.format("Erro desconhecido [%s]", e.getMessage()), e);
			e.printStackTrace();
		}
		return result;
	}
	
	public Map<String, Object> servicoViaCep(String cep) throws IOException {
		URL url = new URL("http://viacep.com.br/ws/" + formataCep(cep) + "/json/");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		return criarMapa(IOUtils.toString(in, encoding));
	}

	@RequestMapping(value = "/procurarEnderecoPorPessoa", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse buscarCep(@RequestParam String nome, @RequestParam String documento, @RequestParam Boolean somentePropriedadeRural) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("procurarEnderecoPorPessoa [%s][%s][%s]", nome, documento, somentePropriedadeRural));
		}
		JsonResponse result = null;
		try {
			List<Map<String,Object>> entidade = ((PessoaService) getService()).procurarEnderecoPorPessoa(nome, documento, somentePropriedadeRural);
			if (entidade != null && entidade.size() > 0) {
				result = new JsonResponse(true, "Sucesso", entidade);
			} else {
				result = new JsonResponse(false, "Nenhum registro encontrado");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
}