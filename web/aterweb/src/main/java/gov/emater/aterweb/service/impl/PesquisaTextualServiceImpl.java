package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.PesquisaTextualDao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.Benfeitoria;
import gov.emater.aterweb.model.MeioContatoEmail;
import gov.emater.aterweb.model.MeioContatoEndereco;
import gov.emater.aterweb.model.MeioContatoTelefonico;
import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaFisica;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaJuridica;
import gov.emater.aterweb.model.PropriedadeRural;
import gov.emater.aterweb.model.PublicoAlvo;
import gov.emater.aterweb.model.Setor;
import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.service.PesquisaTextualService;
import gov.emater.aterweb.service.PessoaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisaTextualServiceImpl implements PesquisaTextualService {

	class PesquisaTextualResultado {

		private String descricao;

		private String endereco;

		private String foto;

		private String nome;

		public PesquisaTextualResultado() {
		}

		public PesquisaTextualResultado(String nome, String descricao, String foto, String endereco) {
			super();
			this.nome = nome;
			this.descricao = descricao;
			this.foto = foto;
			this.endereco = endereco;
		}

		public String getDescricao() {
			return descricao;
		}

		public String getEndereco() {
			return endereco;
		}

		public String getFoto() {
			return foto;
		}

		public String getNome() {
			return nome;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		@Override
		public String toString() {
			return "PesquisaTextualResultado [descricao=" + descricao + ", endereco=" + endereco + ", foto=" + foto + ", nome=" + nome + "]";
		}
	}

	public static final String CHAVE_ACAO = "Ação";

	public static final String CHAVE_ARQUIVO = "Arquivo";

	public static final String CHAVE_PESSOA = "Pessoa";

	public static final String CHAVE_PROPRIEDADE = "Propriedade";

	private static final Logger logger = Logger.getLogger(PesquisaTextualServiceImpl.class);

	private final Map<String, Map<String, List<?>>> chavesPesquisa;

	@Autowired
	private PesquisaTextualDao dao;
	
	@Autowired
	private PessoaService pessoaService;
	
	public PesquisaTextualServiceImpl() {
		chavesPesquisa = new HashMap<String, Map<String, List<?>>>();
		Map<String, List<?>> config = null;
		List<Class<?>> classes = null;
		List<String> chaves = null;

		// acao
		classes = new ArrayList<Class<?>>();
		chaves = new ArrayList<String>();
		config = new HashMap<String, List<?>>();
		classes.add(Atividade.class);
		chaves.add("dataInicio");
		chaves.add("dataTermino");
		chaves.add("nome");
		config.put("classe", classes);
		config.put("chave", chaves);
		chavesPesquisa.put(CHAVE_ACAO, config);

		// arquivo
		classes = new ArrayList<Class<?>>();
		chaves = new ArrayList<String>();
		config = new HashMap<String, List<?>>();
		classes.add(Arquivo.class);
		chaves.add("descricao");
		chaves.add("md5");
		chaves.add("nome");
		config.put("classe", classes);
		config.put("chave", chaves);
		chavesPesquisa.put(CHAVE_ARQUIVO, config);

		// pessoa
		classes = new ArrayList<Class<?>>();
		chaves = new ArrayList<String>();
		config = new HashMap<String, List<?>>();

		classes.add(MeioContatoEmail.class);
		chaves.add("email");
		classes.add(MeioContatoEndereco.class);
		chaves.add("cep");
		chaves.add("descricao");
		classes.add(MeioContatoTelefonico.class);
		chaves.add("numero");

		// pessoa
		classes.add(Pessoa.class);
		chaves.add("apelidoSigla");
		chaves.add("nome");
		chaves.add("observacoes");

		// pessoa
		classes.add(PessoaFisica.class);
		chaves.add("camNumero");
		chaves.add("camSerie");
		chaves.add("certidaoCasamentoCartorio");
		chaves.add("certidaoCasamentoFolha");
		chaves.add("certidaoCasamentoLivro");
		chaves.add("cnhNumero");
		chaves.add("cpf");
		chaves.add("ctpsNumero");
		chaves.add("ctpsSerie");
		chaves.add("dapRegistro");
		chaves.add("nisNumero");
		chaves.add("rgNumero");
		chaves.add("tituloNumero");
		chaves.add("tituloSecao");
		chaves.add("tituloZona");

		// pessoa
		classes.add(PessoaJuridica.class);
		chaves.add("cnpj");
		chaves.add("inscricaoEstadual");

		// pessoa
		classes.add(PublicoAlvo.class);
		chaves.add("carteiraProdutorNumero");
		chaves.add("dapNumero");

		// pessoa
		classes.add(Setor.class);
		chaves.add("nome");

		// pessoa
		classes.add(Usuario.class);
		chaves.add("nomeUsuario");
		config.put("classe", classes);
		config.put("chave", chaves);
		chavesPesquisa.put(CHAVE_PESSOA, config);

		// propriedade
		classes = new ArrayList<Class<?>>();
		chaves = new ArrayList<String>();
		config = new HashMap<String, List<?>>();

		classes.add(Benfeitoria.class);
		chaves.add("caracteristica");
		chaves.add("especificacao");

		// propriedade
		classes.add(PessoaGrupo.class);
		chaves.add("nome");
		chaves.add("sigla");

		// propriedade
		classes.add(PropriedadeRural.class);
		chaves.add("nome");
		chaves.add("numeroRegistro");
		chaves.add("observacoes");
		chaves.add("outorga");
		chaves.add("outorgaNumero");
		chaves.add("roteiroAcesso");
		config.put("classe", classes);
		config.put("chave", chaves);
		chavesPesquisa.put(CHAVE_PROPRIEDADE, config);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> pesquisar(String queryString) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<String, Map<String, List<?>>> chave : chavesPesquisa.entrySet()) {
			List<?> lista = null, temp = null;
			int i;
			try {
				temp = (List<?>) chave.getValue().get("classe");
				Class<?>[] classe = new Class[temp.size()];
				i = 0;
				for (Object c : temp) {
					classe[i++] = (Class<?>) c;
				}
				temp = (List<String>) chave.getValue().get("chave");
				String[] chaves = new String[temp.size()];
				i = 0;
				for (Object c : temp) {
					chaves[i++] = (String) c;
				}

				lista = dao.pesquisar(classe, chaves, queryString);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("[%s] = [%d]", chave.getKey(), lista == null ? 0 : lista.size()));
			}
			if (lista != null && lista.size() > 0) {
				List<PesquisaTextualResultado> resultados = new ArrayList<PesquisaTextualResultado>();
				switch (chave.getKey()) {
				case CHAVE_ACAO:
					break;
				case CHAVE_ARQUIVO:
					break;
				case CHAVE_PESSOA: {
					Pessoa entidade = null;
					for (Object o : lista) {
						if (o instanceof Pessoa) {
							entidade = (Pessoa) o;
							pessoaService.setFotoPerfil(entidade);
						}
						if (entidade != null) {
							resultados.add(new PesquisaTextualResultado(entidade.getNome(), entidade.getPessoaTipo().toString(), entidade.getFotoPerfil(), "pessoa-cad/?modo=P&id=" + entidade.getId() + "#/formulario"));
						}
					}
					break;
				}
				case CHAVE_PROPRIEDADE: {
					PropriedadeRural entidade = null;
					for (Object o : lista) {
						if (o instanceof PropriedadeRural) {
							entidade = (PropriedadeRural) o;
						} else if (o instanceof Benfeitoria) {
							entidade = ((Benfeitoria) o).getPropriedadeRural();
						}
						if (entidade != null && entidade.getMeioContatoEndereco() != null) {
							resultados.add(new PesquisaTextualResultado(entidade.getMeioContatoEndereco().getNomePropriedadeRuralOuEstabelecimento(), entidade.getMeioContatoEndereco().getLogradouro(), "resources/img/propriedade_padrao.png", "propriedade-rural-cad/?modo=P&id=" + entidade.getId()));
						}
					}
					break;
				}
				default:
					throw new RuntimeException("Chave de pesquisa nao definida");
				}
				lista = resultados;
			}
			result.put(chave.getKey(), lista);
		}
		return result;
	}

	@Override
	@Transactional
	public boolean reindexar() {
		return dao.reindexar();
	}
}