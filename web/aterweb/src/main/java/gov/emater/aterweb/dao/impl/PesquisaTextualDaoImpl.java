package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.PesquisaTextualDao;
import gov.emater.aterweb.mvc.controller.PesquisaTextualController;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PesquisaTextualDaoImpl implements PesquisaTextualDao {

	private static final Logger logger = Logger.getLogger(PesquisaTextualController.class);

	@Autowired
	private SessionFactory session;

	public PesquisaTextualDaoImpl() {
	}

	/**
	 * Mantém o objeto de sessão do Módulo de Persistência (Hibernate)
	 * 
	 * @return a sessão do Módulo de Persistência (Hibernate)
	 */
	public SessionFactory getSession() {
		if (session.isClosed()) {
			session.openSession();
		}
		return session;
	}

	@Override
	public List<?> pesquisar(Class<?>[] classe, String[] chavePesq, String queryString) throws ParseException {
		List<?> result = null;

		FullTextSession fullTextSession = Search.getFullTextSession(getSession().getCurrentSession());
		MultiFieldQueryParser parser = null;
		Query query = null;
		FullTextQuery fullTextQuery = null;

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Pesq chave [%s] string[%s]", chavePesq, queryString));
		}
		try {
			parser = new MultiFieldQueryParser(Version.LUCENE_36, chavePesq, new BrazilianAnalyzer(Version.LUCENE_36));
			query = parser.parse(queryString);
			fullTextQuery = fullTextSession.createFullTextQuery(query, classe);
			System.out.println(fullTextQuery.getQueryString());
			result = fullTextQuery.list();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
		}
		return result;
	}

	@Override
	public final boolean reindexar(Class<?>... types) {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getSession().getCurrentSession());
			fullTextSession.createIndexer(types).startAndWait();
			if (logger.isDebugEnabled()) {
				logger.debug("Reindexado...");
			}
			return true;
		} catch (InterruptedException e1) {
			if (logger.isDebugEnabled()) {
				logger.debug(e1);
			}
			return false;
		}
	}
}
// @Override
// public List<?> pesquisar(Class<?> classe, String chavePesq, String
// queryString) throws ParseException {
// List<?> result = null;
//
// FullTextSession fullTextSession =
// Search.getFullTextSession(getSession().getCurrentSession());
// QueryParser queryParser = null;
// Query query = null;
// FullTextQuery fullTextQuery = null;
//
// if (logger.isDebugEnabled()) {
// logger.debug(String.format("Pesq classe [%s] - chave [%s]", classe.getName(),
// chavePesq));
// }
// try {
// queryParser = new QueryParser(Version.LUCENE_36, chavePesq, new
// BrazilianAnalyzer(Version.LUCENE_36));
// query = queryParser.parse(queryString);
// fullTextQuery = fullTextSession.createFullTextQuery(query, classe);
// result = fullTextQuery.list();
// } catch (Exception e) {
// if (logger.isDebugEnabled()) {
// logger.debug(e);
// }
// }
// return result;
// }
