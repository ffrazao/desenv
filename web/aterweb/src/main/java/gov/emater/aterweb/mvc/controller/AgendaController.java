package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.EntidadeBase;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.AgendaCadFiltroDto;
import gov.emater.aterweb.service.AtividadeService;

import java.util.ArrayList;
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
 * Gerencia requisições para a agenda.
 */
@Controller
@RequestMapping(value = "/" + AgendaController.PAGINA)
public class AgendaController extends _BaseController implements CadastroController<Atividade, AgendaCadFiltroDto> {

	private static final Logger logger = Logger.getLogger(AgendaController.class);

	public static final String PAGINA = "agenda-cad";

	public static final String TITULO = "Agenda de Atividades";

	@Autowired
	public AgendaController(AtividadeService service) {
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
	@RequestMapping(value = "/" + ACAO_FILTRAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse filtrar(AgendaCadFiltroDto filtro, BindingResult bindingResult) {
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
		variaveis.put(VAR_FILTRO, new AgendaCadFiltroDto());
		variaveis.put(VAR_LISTA, new ArrayList<Object>());
		variaveis.put(VAR_REGISTRO, new Object());
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("preparando apresentação [%s]", variaveis));
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
	protected EntidadeBase restaurarInterno(Integer id) {
		EntidadeBase entidade = (EntidadeBase) (id == null ? new EntidadeBase() : getService().restore(id));
		return entidade;
	}

	@Override
	@RequestMapping(value = "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestBody Atividade entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

}