package gov.emater.aterweb.dao;

import java.util.List;

import org.apache.lucene.queryParser.ParseException;

public interface PesquisaTextualDao {

	List<?> pesquisar(Class<?>[] classe, String[] chavePesq, String queryString) throws ParseException;

	boolean reindexar(Class<?>... types);

}
