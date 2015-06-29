package gov.emater.aterweb.mvc.controller;

import gov.emater.aterweb.comum.UtilitarioString;
import gov.emater.aterweb.model.Acao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.model.Assunto;
import gov.emater.aterweb.model.AssuntoAcao;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.AtividadeArquivo;
import gov.emater.aterweb.model.AtividadeAssunto;
import gov.emater.aterweb.model.AtividadeRelacionamento;
import gov.emater.aterweb.model.EntidadeBase;
import gov.emater.aterweb.model.Metodo;
import gov.emater.aterweb.model.Ocorrencia;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.model.domain.AgendamentoTipo;
import gov.emater.aterweb.model.domain.AtividadeFinalidade;
import gov.emater.aterweb.model.domain.AtividadeFormato;
import gov.emater.aterweb.model.domain.AtividadeNatureza;
import gov.emater.aterweb.model.domain.AtividadePrioridade;
import gov.emater.aterweb.model.domain.AtividadeSituacao;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.mvc.config.JsonResponse;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroModo;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroOpcao;
import gov.emater.aterweb.mvc.controller.cadastro.CadastroTipoSelecao;
import gov.emater.aterweb.mvc.dto.AtividadeCadFiltroDto;
import gov.emater.aterweb.service.AtividadeService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/" + AtividadeController.PAGINA)
public class AtividadeController extends _BaseController implements CadastroController<Atividade, AtividadeCadFiltroDto> {

	public static final String PAGINA = "atividade-cad";

	public static final String TITULO = "Gestão de Atividades";

	@Autowired
	private AtividadeService service;

	@Autowired
	public AtividadeController(AtividadeService service) {
		super(service);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView abrir(@RequestParam(value = "modo", required = false, defaultValue = "N") CadastroModo modo, @RequestParam(value = "opcao", required = false) CadastroOpcao opcao[], @RequestParam(value = "urlPadrao", required = false) String urlPadrao,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "id", required = false) Integer id[], @RequestParam(value = "registro", required = false) String registro,
			@RequestParam(value = "tipoSelecao", required = false) CadastroTipoSelecao tipoSelecao[]) {
		return super.abrir(modo, opcao, urlPadrao, filtro, id, registro, tipoSelecao);
	}

	@RequestMapping(value = "/subatividade", method = RequestMethod.GET)
	public String abrirSubatividade(@RequestParam(value = "id", required = false) Integer id) {
		return "subatividade";
	}

	private String extraiNumeroProtocolo(String numero) {
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		for (char c : numero.toCharArray()) {
			int pos = str.indexOf(c);
			if (pos >= 0) {
				sb.append(UtilitarioString.zeroEsquerda(pos, 2));
			}
		}
		return sb.toString();
	}

	@Override
	@RequestMapping(value = "/" + ACAO_FILTRAR, method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse filtrar(AtividadeCadFiltroDto filtro, BindingResult bindingResult) {
		captarAssuntoAcaoList(filtro);
		captarPessoaListJava(filtro);
		return super.filtrar(filtro, bindingResult);
	}

	@SuppressWarnings("unchecked")
	private void captarAssuntoAcaoList(AtividadeCadFiltroDto filtro) {
		// preparar elementos
		List<Object> result = null;
		for (Object linha : filtro.getAtividadeAssuntoAcaoList()) {
			if (result == null) {
				result = new ArrayList<Object>();
			}
			result.add(criarMapa((String) linha));
		}
		// contabilizar os elementos
		List<String> finalidadeList = new ArrayList<String>(); 
		List<Integer> assuntoList = new ArrayList<Integer>();
		List<Integer> assuntoAcaoList = new ArrayList<Integer>();
		for (Object linha : result) {
			Map<String, Object> reg = (Map<String, Object>) linha;
			Boolean marcado = (Boolean) reg.get("marcado"); 
			if (marcado != null && marcado) {
				finalidadeList.add((String) reg.get("finalidade"));
			} else {
				contaAssuntoAcao(reg, assuntoList, assuntoAcaoList);
			}
		}
		result = null;
		// totalizar elementos
		if (finalidadeList.size() > 0 || assuntoList.size() > 0 || assuntoAcaoList.size() > 0) {
			result = new ArrayList<Object>();
			result.add(finalidadeList);
			result.add(assuntoList);
			result.add(assuntoAcaoList);
		}
		filtro.setAtividadeAssuntoAcaoList(result);
	}

	@SuppressWarnings("unchecked")
	private void contaAssuntoAcao(Map<String, Object> reg, List<Integer> assuntoList, List<Integer> assuntoAcaoList) {
		List<Map<String, Object>> filhos = (List<Map<String, Object>>) reg.get("filhos");
		if (filhos != null) {
			for (Map<String, Object> assuntoAcao: filhos) {
				Boolean marcado = (Boolean) assuntoAcao.get("marcado"); 
				if (marcado != null && marcado) {
					assuntoList.add((Integer) assuntoAcao.get("id"));
					// inserir também os assuntos descendentes
					assuntoList.addAll(((AtividadeService) getService()).getAssuntoIdDescendencia((Integer) assuntoAcao.get("id")));
				} else {
					contaAssuntoAcao(assuntoAcao, assuntoList, assuntoAcaoList);
				}
			}
		}
		List<Map<String, Object>> acoes = (List<Map<String, Object>>) reg.get("acoes");
		if (acoes != null) {
			for (Map<String, Object> acao: acoes) {
				Boolean marcado = (Boolean) acao.get("marcado"); 
				if (marcado != null && marcado) {
					assuntoAcaoList.add((Integer) acao.get("id"));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void captarPessoaListJava(AtividadeCadFiltroDto filtro) {
		if (filtro.getPessoaList() != null) {
			Map<String, Set<Integer>> grupoFuncao = null;
			List<Map<String, Set<Integer>>> pessoaListJava = null;
			for (String pessoaStr : filtro.getPessoaList()) {
				Map<String, Object> filtroPessoa = (Map<String, Object>) criarClasse(pessoaStr);

				String funcao = ((String) filtroPessoa.get("funcao")).toUpperCase();
				Integer pessoaId = (Integer) ((Map<String, Object>) filtroPessoa.get("pessoa")).get("id");
				String juncao = ((String) filtroPessoa.get("juncao")).toUpperCase();

				if (grupoFuncao == null) {
					grupoFuncao = new HashMap<String, Set<Integer>>();
				}
				Set<Integer> funcaoList = (HashSet<Integer>) grupoFuncao.get(funcao);
				if (funcaoList == null) {
					funcaoList = new HashSet<Integer>();
					grupoFuncao.put(funcao, funcaoList);
				}
				funcaoList.add(pessoaId);
				if (pessoaListJava == null) {
					pessoaListJava = new ArrayList<Map<String, Set<Integer>>>();
				}
				if ("O".equals(juncao)) {
					pessoaListJava.add(grupoFuncao);
					grupoFuncao = null;
				}
			}
			if (grupoFuncao != null) {
				pessoaListJava.add(grupoFuncao);
			}
			filtro.setPessoaListJava(pessoaListJava);
		}
	}

	private String gerarCodigoAtividade() {
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < 8; i++) {
			int j = r.nextInt(str.length());
			sb.append(str.charAt(j));
		}
		sb.append(modulo10(extraiNumeroProtocolo(sb.toString())));
		sb.insert(4, ".");
		sb.insert(9, "-");
		return sb.toString();
	}

	@RequestMapping(value = "getAssuntoAcaoTransversalList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAssuntoAcaoTransversalList(Integer assuntoAcaoId) {
		JsonResponse result = null;
		try {
			List<?> lista = ((AtividadeService) getService()).getAssuntoAcaoTransversalList(assuntoAcaoId);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "getAtividadeAssuntoAcaoList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAtividadeAssuntoAcaoList(Integer assuntoId) {
		JsonResponse result = null;
		try {
			List<?> lista = ((AtividadeService) getService()).getAtividadeAssuntoAcaoList(assuntoId);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "getAtividadeAssuntoCompletaList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAtividadeAssuntoCompletaList(AtividadeFinalidade finalidade) {
		JsonResponse result = null;
		try {
			List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
			Map<String, Object> item;

			// adicionar itens operacionais
			item = new HashMap<String, Object>();
			item.put("nome", AtividadeFinalidade.O.toString());
			item.put("finalidade", AtividadeFinalidade.O);
			item.put("filhos", ((AtividadeService) getService()).getAtividadeAssuntoList(AtividadeFinalidade.O));
			lista.add(item);

			// adicionar itens administrativos
			item = new HashMap<String, Object>();
			item.put("nome", AtividadeFinalidade.A.toString());
			item.put("finalidade", AtividadeFinalidade.A);
			item.put("filhos", ((AtividadeService) getService()).getAtividadeAssuntoList(AtividadeFinalidade.A));
			lista.add(item);

			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "getAtividadeAssuntoList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAtividadeAssuntoList(AtividadeFinalidade finalidade) {
		JsonResponse result = null;
		try {
			List<?> lista = ((AtividadeService) getService()).getAtividadeAssuntoList(finalidade);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "getAssuntoAcaoFilhos", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAssuntoAcaoFilhos(Integer assuntoId) {
		JsonResponse result = null;
		try {
			List<?> lista = ((AtividadeService) getService()).getAssuntoAcaoFilhos(assuntoId);
			result = new JsonResponse(true, "Sucesso", lista);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
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
	@RequestMapping(value = "/" + ACAO_INCLUIR)
	@ResponseBody
	public JsonResponse incluir(Principal principal) {
		return super.incluir(principal);
	}

	@Override
	protected EntidadeBase incluirInterno(Usuario usuario) {

		Atividade result = new Atividade();

		Arquivo arquivo = new Arquivo(14);
		arquivo.setNome("teste.doc");
		arquivo.setTipo("jpeg");
		arquivo.setTamanho(432);

		AtividadeArquivo atividadeArquivo = new AtividadeArquivo(321);
		atividadeArquivo.setDescricao("significado deste arquivo para a atividade");
		atividadeArquivo.setDataUpload(Calendar.getInstance());
		atividadeArquivo.setAtividade(result);
		atividadeArquivo.setArquivo(arquivo);

		List<AtividadeArquivo> atividadeArquivoList = new ArrayList<AtividadeArquivo>();
		atividadeArquivoList.add(atividadeArquivo);

		// result.setAtividadeArquivoList(atividadeArquivoList);

		result.setRegistro(Calendar.getInstance());
		result.setCodigo(gerarCodigoAtividade());
		if (usuario != null) {
			result.setUsuario(usuario.simplificar());
		}
		result.setPrioridade(AtividadePrioridade.N);
		result.setSituacao(AtividadeSituacao.N);
		result.setFormato(AtividadeFormato.E);
		result.setPercentualConclusao(0);
		result.setPublicoEstimado(1);

		Calendar hoje = Calendar.getInstance();
		hoje.clear();
		hoje.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
		result.setInicio(hoje);
		result.setPrevisaoConclusao(hoje);
		result.setDiaTodo(Confirmacao.S);
		// result.setFinalidade(AtividadeFinalidade.O);
		result.setNatureza(AtividadeNatureza.D);
		// result.setMetodo(new Metodo(1));
		result.setAgendamentoTipo(AgendamentoTipo._0);

		// preparar atividade assunto list
		List<AtividadeAssunto> atividadeAssuntoList = new ArrayList<AtividadeAssunto>();
		AtividadeAssunto atividadeAssunto = new AtividadeAssunto();
		atividadeAssunto.setAtividade(new Atividade());
		atividadeAssunto.setTransversal(Confirmacao.N);
		AssuntoAcao assuntoAcao = new AssuntoAcao(1338);
		Assunto assuntoPai = new Assunto(1);
		assuntoPai.setNome("Renda");
		Assunto assunto = new Assunto(2);
		assunto.setNome("Agroecologia");
		assunto.setPai(assuntoPai);
		assunto.setFinalidade(AtividadeFinalidade.O);
		assuntoAcao.setAssunto(assunto);
		Acao acao = new Acao(48);
		acao.setNome("Crédito Rural");
		assuntoAcao.setAcao(acao);
		atividadeAssunto.setAssuntoAcao(assuntoAcao);
		atividadeAssunto.setDescricao("Teste de inclusao de acao em atividade");
		atividadeAssuntoList.add(atividadeAssunto);

		// result.setAtividadeAssuntoList(atividadeAssuntoList);

		List<AtividadeRelacionamento> principalAtividadeList = new ArrayList<AtividadeRelacionamento>();
		AtividadeRelacionamento principalAtividadeRelacionamento = new AtividadeRelacionamento();
		Atividade principalAtividade = new Atividade(7);
		principalAtividade.setFinalidade(AtividadeFinalidade.O);
		principalAtividade.setDiaTodo(Confirmacao.S);
		principalAtividade.setAgendamentoTipo(AgendamentoTipo._0);
		principalAtividade.setCodigo(gerarCodigoAtividade());
		principalAtividade.setPrioridade(AtividadePrioridade.N);
		principalAtividade.setSituacao(AtividadeSituacao.N);
		principalAtividade.setFormato(AtividadeFormato.E);
		principalAtividade.setRegistro(Calendar.getInstance());
		principalAtividade.setNatureza(AtividadeNatureza.D);
		principalAtividade.setMetodo(new Metodo(1));
		if (usuario != null) {
			principalAtividade.setUsuario(new Usuario(514));
		}

		principalAtividadeRelacionamento.setAtividade(result);
		principalAtividadeRelacionamento.setPrincipalAtividade(principalAtividade);
		principalAtividadeList.add(principalAtividadeRelacionamento);
		// result.setPrincipalAtividadeList(principalAtividadeList);

		List<AtividadeRelacionamento> subatividadeList = new ArrayList<AtividadeRelacionamento>();
		AtividadeRelacionamento subatividadeRelacionamento = new AtividadeRelacionamento();
		Atividade subatividade = new Atividade();
		subatividade.setAgendamentoTipo(AgendamentoTipo._0);
		subatividade.setCodigo(gerarCodigoAtividade());
		subatividade.setDiaTodo(Confirmacao.S);
		subatividade.setFinalidade(AtividadeFinalidade.O);
		subatividade.setFormato(AtividadeFormato.E);
		subatividade.setMetodo(new Metodo(1));
		subatividade.setNatureza(AtividadeNatureza.D);
		subatividade.setPrioridade(AtividadePrioridade.N);
		subatividade.setRegistro(Calendar.getInstance());
		subatividade.setSituacao(AtividadeSituacao.N);
		if (usuario != null) {
			subatividade.setUsuario(new Usuario(514));
		}

		atividadeAssuntoList = new ArrayList<AtividadeAssunto>();
		atividadeAssunto = new AtividadeAssunto(10);
		atividadeAssunto.setAtividade(new Atividade(20));
		atividadeAssunto.setTransversal(Confirmacao.N);
		assuntoAcao = new AssuntoAcao(1339);

		assuntoPai = new Assunto(1);
		assuntoPai.setNome("Sub Renda");

		assunto = new Assunto(50);
		assunto.setNome("Sub Agroecologia");
		assunto.setPai(assuntoPai);
		assunto.setFinalidade(AtividadeFinalidade.O);
		assuntoAcao.setAssunto(assunto);
		acao = new Acao(60);
		acao.setNome("Sub Crédito Rural");
		assuntoAcao.setAcao(acao);
		atividadeAssunto.setAssuntoAcao(assuntoAcao);
		atividadeAssunto.setDescricao("Teste de inclusao de acao em subatividade");
		atividadeAssuntoList.add(atividadeAssunto);

		subatividade.setAtividadeAssuntoList(atividadeAssuntoList);

		subatividadeRelacionamento.setAtividade(subatividade);
		subatividadeRelacionamento.setPrincipalAtividade(result);
		subatividadeList.add(subatividadeRelacionamento);
		// result.setSubatividadeList(subatividadeList);

		return result;
	}

	private int modulo10(String numero) {
		// variáveis de instancia
		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[numero.length() + 1];
		int multiplicador = 2;
		String aux;
		String aux2;
		String aux3;

		for (int i = numero.length(); i > 0; i--) {
			// Multiplica da direita pra esquerda, alternando os algarismos 2 e
			// 1
			if (multiplicador % 2 == 0) {
				// pega cada numero isoladamente
				numeros[i] = String.valueOf(Integer.valueOf(numero.substring(i - 1, i)) * 2);
				multiplicador = 1;
			} else {
				numeros[i] = String.valueOf(Integer.valueOf(numero.substring(i - 1, i)) * 1);
				multiplicador = 2;
			}
		}

		// Realiza a soma dos campos de acordo com a regra
		for (int i = (numeros.length - 1); i > 0; i--) {
			aux = String.valueOf(Integer.valueOf(numeros[i]));

			if (aux.length() > 1) {
				aux2 = aux.substring(0, aux.length() - 1);
				aux3 = aux.substring(aux.length() - 1, aux.length());
				numeros[i] = String.valueOf(Integer.valueOf(aux2) + Integer.valueOf(aux3));
			} else {
				numeros[i] = aux;
			}
		}

		// Realiza a soma de todos os elementos do array e calcula o digito
		// verificador
		// na base 10 de acordo com a regra.
		for (int i = numeros.length; i > 0; i--) {
			if (numeros[i - 1] != null) {
				soma += Integer.valueOf(numeros[i - 1]);
			}
		}
		resto = soma % 10;
		dv = 10 - resto;
		if (dv == 10) {
			dv = 0;
		}

		// retorna o digito verificador
		return dv;
	}

	@RequestMapping(value = "/ocorrenciaIncluir")
	@ResponseBody
	public JsonResponse ocorrenciaIncluir(@RequestParam(required = false) Integer atividadeId, @RequestParam AtividadeSituacao situacao, @RequestParam Integer percentualConclusao, @RequestParam AtividadePrioridade prioridade, Principal principal) {
		JsonResponse result = null;
		try {
			Ocorrencia entidade = new Ocorrencia();
			entidade.setRegistro(Calendar.getInstance());
			entidade.setInicioSuspensao(Calendar.getInstance());
			entidade.setAtividade(new Atividade(atividadeId));
			if (situacao.equals(AtividadeSituacao.N) || percentualConclusao == 0) {
				situacao = AtividadeSituacao.E;
				percentualConclusao = 1;
			}
			entidade.setSituacao(situacao);
			entidade.setPercentualConclusao(percentualConclusao);
			entidade.setPrioridade(prioridade);
			entidade.setIncidente(Confirmacao.N);
			Usuario usuarioLogado = getUsuario(principal);
			if (usuarioLogado != null) {
				entidade.setUsuario(usuarioLogado.simplificar());
			}
			result = new JsonResponse(true, "Sucesso", entidade);
		} catch (Exception e) {
			result = new JsonResponse(false, e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void prepararInterno(Map<String, Object> variaveis) {
		// TODO Auto-generated method stub
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
		return (Atividade) (id == null ? new Atividade() : getService().restore(id));
	}

	@Override
	@RequestMapping(value = "/" + ACAO_SALVAR, method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse salvar(@Valid @RequestBody Atividade entity, BindingResult bindingResult) {
		return super.salvar(entity, bindingResult);
	}

}