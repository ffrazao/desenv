package br.gov.df.emater.aterwebsrv.bo;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.config.ConfigParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("facadeBo")
public class FacadeBo implements Catalog {
	
	private CatalogFactory catalogo;

	private static final String LOCAL_INICIAL_CATALAGOS = "/br/gov/df/emater/aterwebsrv/bo";

	private static final String NOME_ARQUIVO_CATALOGO = "catalogo.xml";

	public static void main(String[] args) throws Exception {
		new FacadeBo().executar("atividade", "atividadeChd");
	}

	protected ConfigParser parser = new ConfigParser();

	public FacadeBo() throws Exception {
		System.out.println("novo FacadeBo");
		// carregar os catalogos de commandos
		carregarCatalogo(LOCAL_INICIAL_CATALAGOS);
		this.catalogo = CatalogFactory.getInstance(); 
	}

	@Override
	public void addCommand(String nomeComando, Command comando) {
		this.addCommand(null, nomeComando, comando);
	}

	public void addCommand(String catalogo, String nomeComando, Command comando) {
		this.getCatalogo(catalogo).addCommand(nomeComando, comando);
	}

	private void carregarCatalogo(String localCatalagos) throws Exception {
		File diretorio = new File(this.getClass().getResource(localCatalagos).getPath());
		for (File item : diretorio.listFiles()) {
			String diretorioOuCatalogo = localCatalagos.concat("/").concat(item.getName());
			if (item.isDirectory()) {
				// chamada recursiva
				carregarCatalogo(diretorioOuCatalogo);
			} else if (item.getName().equals(NOME_ARQUIVO_CATALOGO)) {
				// carregar o catalogo
				parser.parse(this.getClass().getResource(diretorioOuCatalogo));
			}
		}
	}

	public Object executar(String nomeComando) {
		return this.executar(nomeComando, null);
	}

	public Object executar(String nomeComando, Object parametros) {
		return this.executar(null, nomeComando, parametros);
	}

	public Object executar(String catalogo, String nomeComando) {
		return this.executar(catalogo, nomeComando, null);
	}
	
	public Object executar(String catalogo, String nomeComando, Object requisicao) {
		Contexto contexto = new Contexto();
		contexto.setRequisicao(requisicao);
		Command acao = getCatalogo(catalogo).getCommand(nomeComando);
		try {
			acao.execute(contexto);
		} catch (Exception e) {
			contexto.setErro(e);
		}
		return contexto;
	}

	public Catalog getCatalogo() {
		return getCatalogo(null);
	}

	public Catalog getCatalogo(String catalogo) {
		return catalogo == null ? this.catalogo.getCatalog() : this.catalogo.getCatalog(catalogo);
	}

	public Command getCommand(String command) {
		return getCommand(null, command);
	}

	public Command getCommand(String catalogo, String name) {
		return catalogo == null ? getCatalogo().getCommand(name) : getCatalogo(catalogo).getCommand(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getNames() {
		return this.getNames(null);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getNames(String catalogo) {
		return getCatalogo(catalogo).getNames();
	}

}