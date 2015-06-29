package br.gov.df.emater.aterwebsrv.bo;

import java.io.File;
import java.net.URL;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("servicoFacade")
public class FacadeBo {

	private static final String DEFAULT_XML = "/br/gov/df/emater/aterwebsrv/bo/";
	protected Catalog catalog;
	protected ConfigParser parser = new ConfigParser();

	public FacadeBo() throws Exception {
		URL local = this.getClass().getResource(DEFAULT_XML);
		File diretorio = new File(local.getPath());
		for (File dir : diretorio.listFiles()) {
			if (dir.isDirectory()) {
				for (File xml: dir.listFiles()) {
					if (xml.getName().endsWith(".xml")) {
						load(String.format("%s%s/%s/", DEFAULT_XML, dir.getName(), xml.getName()));						
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new FacadeBo();
	}

	public Object executar(String nomeAcao) {
		return this.executar(nomeAcao, null);
	}

	@SuppressWarnings("unchecked")
	public Object executar(String nomeAcao, Object parametros) {
		Context context = new ContextBase();
		Command acao = catalog.getCommand(nomeAcao);
		try {
			acao.execute(context);
		} catch (Exception e) {
			context.put("Erro", e);
		}
		return context;
	}

	private void load(String path) throws Exception {
		parser.parse(this.getClass().getResource(path));
		catalog = CatalogFactory.getInstance().getCatalog();
	}

}