package gov.emater.aterweb.service;

import java.util.Map;

public interface PesquisaTextualService extends Service {

	Map<String, Object> pesquisar(String queryString);

	boolean reindexar();

}
