package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.model.PerspectivaProducaoAgricola;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PrevisaoProducao;
import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.domain.PerspectivaProducao;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.IndiceProducaoCadFiltroDto;
import gov.emater.aterweb.service.IndiceProducaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
 * Gerencia requisições para Índices de Produção.
 */
@Controller
@RequestMapping(value = "/" + IndiceProducaoController.PAGINA)
public class IndiceProducaoController extends _BaseController implements CadastroController<PrevisaoProducao, IndiceProducaoCadFiltroDto> {

	private static final Logger logger = Logger.getLogger(IndiceProducaoController.class);

	public static final String PAGINA = "indice-producao-cad";

	public static final String TITULO = "Gestão de Índices de Produção";

	@Autowired
	public IndiceProducaoController(IndiceProducaoService service) {
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
	@RequestMapping(value = "/" + ACAO_EXCLUIR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse excluir(@RequestParam Integer id) {
		return super.excluir(id);
	}

	@Override
	@RequestMapping(value = "/" + ACAO_FILTRAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse filtrar(@RequestBody IndiceProducaoCadFiltroDto filtro, BindingResult bindingResult) {
		return super.filtrar(filtro, bindingResult);
	}

	@Override
	protected String getPagina() {
		return PAGINA;
	}

	@RequestMapping(value = "/produtoServicoPorPerspectiva", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getProdutoServicoPorPerspectiva(@RequestParam PerspectivaProducao perspectiva, @RequestParam(required = false) Integer pai) {
		JsonResponse result = null;
		try {
			ProdutoServico produtoServico = null;
			if (pai != null) {
				produtoServico = new ProdutoServico(pai);
			}
			List<?> lista = ((IndiceProducaoService) getService()).getProdutoServicoPorPerspectiva(perspectiva, produtoServico);
			if (lista != null && lista.size() > 0) {
				result = new JsonResponse(true, "Sucesso", lista);
			} else {
				result = new JsonResponse(true, "Nenhum registro encontrado");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/propriedadeRuralPorPessoaGrupo", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPropriedadeRuralPorPessoaGrupo(@RequestParam Integer pessoaGrupoId) {
		JsonResponse result = null;
		try {
			PessoaGrupo pessoaGrupo = new PessoaGrupo(pessoaGrupoId);

			List<?> lista = ((IndiceProducaoService) getService()).getPropriedadeRuralPorPessoaGrupo(pessoaGrupo);
			if (lista != null && lista.size() > 0) {
				result = new JsonResponse(true, "Sucesso", lista);
			} else {
				result = new JsonResponse(true, "Nenhum registro encontrado");
			}
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
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
		variaveis.put(VAR_FILTRO, new IndiceProducaoCadFiltroDto());
		variaveis.put(VAR_LISTA, new ArrayList<Object>());
		variaveis.put(VAR_REGISTRO, new Object());
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
	protected PrevisaoProducao restaurarInterno(Integer id) {
		return (PrevisaoProducao) (id == null ? new PerspectivaProducaoAgricola() : getService().restore(id));
	}

	@Override
	@RequestMapping(value = "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestBody PrevisaoProducao entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

}