package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.comum.UtilitarioData;
import gov.emater.aterweb.dao.AtividadeDao;
import gov.emater.aterweb.model.Acao;
import gov.emater.aterweb.model.Assunto;
import gov.emater.aterweb.model.AssuntoAcao;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.domain.AtividadeFinalidade;
import gov.emater.aterweb.model.domain.AtividadeSituacao;
import gov.emater.aterweb.mvc.dto.AtividadeCadFiltroDto;
import gov.emater.aterweb.mvc.dto.AtividadeFuncaoDto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class AtividadeDaoImpl extends _CrudDaoImpl<Atividade, Integer> implements AtividadeDao {

	private Map<String, Object> createAssunto(Integer id, String nome, AtividadeFinalidade finalidade, Integer paiId, boolean preencheFilhos) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("nome", nome);
		result.put("finalidade", finalidade);
		result.put("pai", preenchePai(paiId));
		if (preencheFilhos) {
			result.put("filhos", preencheFilhos(result));
			result.put("acoes", getAtividadeAssuntoAcaoList(id));
		}
		return result;
	}

	private Assunto createAssuntoClass(Integer id, String nome, AtividadeFinalidade finalidade, Integer paiId, boolean preencheFilhos) {
		Assunto result = new Assunto(id, nome);
		result.setFinalidade(finalidade);
		result.setPai(preenchePaiClass(paiId));
		if (preencheFilhos) {
			result.setFilhos(preencheFilhosClass(result));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object filtraAtividadeAssuntoAcao(List<Object> atividadeAssuntoAcaoList) {
		// captar parametros informados
		List<String> atividadeFinalidadeList = (List<String>) atividadeAssuntoAcaoList.get(0);
		List<Integer> assuntoList = (List<Integer>) atividadeAssuntoAcaoList.get(1);
		List<Integer> assuntoAcaoList = (List<Integer>) atividadeAssuntoAcaoList.get(2);
		StringBuilder sqlCondicao = new StringBuilder();
		if (!isEmpty(atividadeFinalidadeList)) {
			insereCriterio("Finalidade", sqlCondicao, "_assunto.finalidade IN (:assuntoFinalidade)", true);
		}
		if (!isEmpty(assuntoList)) {
			insereCriterio("Assunto", sqlCondicao, "_assunto.id IN (:assuntoId)", true);
		}
		if (!isEmpty(assuntoAcaoList)) {
			insereCriterio("Assunto Acao", sqlCondicao, "_assunto_acao.id IN (:assuntoAcaoId)", true);
		}
		// preparar a query
		StringBuilder result = new StringBuilder();
		result.append("SELECT _atividade_assunto.atividade_id").append("\n");
		result.append("FROM   atividade.atividade_assunto _atividade_assunto").append("\n");
		result.append("JOIN   atividade.assunto_acao _assunto_acao ON _assunto_acao.id = _atividade_assunto.assunto_acao_id").append("\n");
		if (!isEmpty(atividadeFinalidadeList) || !isEmpty(assuntoList)) {
			result.append("JOIN   atividade.assunto _assunto ON _assunto.id = _assunto_acao.assunto_id").append("\n");
		}
		result.append("WHERE  _atividade_assunto.atividade_id = ativ.id").append("\n");
		result.append("AND    (").append("\n");
		result.append(sqlCondicao).append("\n");
		result.append("       )").append("\n");
		return result.toString();
	}

	private Object filtraPessoa(List<Map<String, Set<Integer>>> pessoaListJava, Map<String, Set<Integer>> valores) {
		StringBuilder result = new StringBuilder();

		int parametroCont = 0;
		// construcao dos blocos
		for (int blocoCont = 0; blocoCont < pessoaListJava.size(); blocoCont++) {

			Map<String, Set<Integer>> blocoMap = pessoaListJava.get(blocoCont);

			// construcao das condicoes
			StringBuilder sqlCondicao = new StringBuilder();
			for (AtividadeFuncaoDto atividadeFuncaoDto : AtividadeFuncaoDto.values()) {
				Set<Integer> pessoaList = blocoMap.get(atividadeFuncaoDto.name());
				if (isEmpty(pessoaList)) {
					continue;
				}
				String parametroNome = String.format("pessoaList%d", parametroCont++);
				valores.put(parametroNome, pessoaList);
				StringBuilder sqlTemp;
				switch (atividadeFuncaoDto) {
				case N: // Não se envolveu
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id NOT IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("                FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("                WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("                AND      a.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Não se envolveu", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case Q: // Qualquer
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("            WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      a.pessoa_id IN (:").append(parametroNome).append(")").append("\n");
					sqlTemp.append("            UNION").append("\n");
					sqlTemp.append("            SELECT   b.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.ocorrencia b").append("\n");
					sqlTemp.append("            JOIN     sistema.usuario b0").append("\n");
					sqlTemp.append("            ON       b0.id = b.usuario_id").append("\n");
					sqlTemp.append("            WHERE    b.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      b0.pessoa_id IN (:").append(parametroNome).append(")").append("\n");
					sqlTemp.append("            UNION").append("\n");
					sqlTemp.append("            SELECT   c.id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade c").append("\n");
					sqlTemp.append("            JOIN     sistema.usuario usu").append("\n");
					sqlTemp.append("            ON       usu.id = c.usuario_id").append("\n");
					sqlTemp.append("            WHERE    c.id = ativ.id").append("\n");
					sqlTemp.append("            AND      usu.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Qualquer", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case R: // Abriu
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.usuario_id IN (SELECT usu.id").append("\n");
					sqlTemp.append("                    FROM   sistema.usuario usu").append("\n");
					sqlTemp.append("                    WHERE  usu.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Abriu", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case D: // Demandou
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("            WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      a.funcao = 'D'").append("\n");
					sqlTemp.append("            AND      a.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Demandou", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case E: // Executou
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("            WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      a.funcao IN ('ER','E')").append("\n");
					sqlTemp.append("            AND      a.pessoa_id IN (:").append(parametroNome).append(")").append("\n");
					sqlTemp.append("            UNION").append("\n");
					sqlTemp.append("            SELECT   b.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.ocorrencia b").append("\n");
					sqlTemp.append("            JOIN     sistema.usuario b0").append("\n");
					sqlTemp.append("            ON       b0.id = b.usuario_id").append("\n");
					sqlTemp.append("            WHERE    b.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      b0.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Executou", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case ER: // Foi responsavel
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("            WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      a.funcao = 'ER'").append("\n");
					sqlTemp.append("            AND      a.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Foi responsavel", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				case A: // Apoiou
					sqlTemp = new StringBuilder();
					sqlTemp.append("ativ.id IN (SELECT   a.atividade_id").append("\n");
					sqlTemp.append("            FROM     atividade.atividade_pessoa a").append("\n");
					sqlTemp.append("            WHERE    a.atividade_id = ativ.id").append("\n");
					sqlTemp.append("            AND      a.funcao = 'E'").append("\n");
					sqlTemp.append("            AND      a.pessoa_id IN (:").append(parametroNome).append("))").append("\n");
					insereCriterio(String.format("Condicao Apoiou", blocoCont), sqlCondicao, sqlTemp.toString(), false);
					break;
				}
			}
			insereCriterio(String.format("BLOCO %d", blocoCont), result, sqlCondicao.toString(), true);
		}

		return result.toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getAssuntoAcaoFilhos(Integer assuntoId) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pai_id = :assuntoId").append("\n");
		sql.append("ORDER BY nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("assuntoId", assuntoId);
		List<Object[]> list = query.list();
		List<Map<String, Object>> result = null;

		for (Object[] linha : list) {
			if (result == null) {
				result = new ArrayList<Map<String, Object>>();
			}
			Map<String, Object> reg = new HashMap<String, Object>();
			reg.put("id", linha[0]);
			reg.put("nome", linha[1]);
			reg.put("finalidade", linha[2]);
			result.add(reg);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getAssuntoAcaoList(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    ata.id AS atividadeAssuntoId,").append("\n");
		sql.append("    assunto.id AS assuntoId,").append("\n");
		sql.append("    assunto.nome AS assuntoNome,").append("\n");
		sql.append("    acao.id AS acaoId,").append("\n");
		sql.append("    acao.nome AS acaoNome").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.atividade_assunto ata").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    atividade.assunto_acao assac ON assac.id = ata.assunto_acao_id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    atividade.assunto assunto ON assunto.id = assac.assunto_id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    atividade.acao acao ON acao.id = assac.acao_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    ata.atividade_id = :id").append("\n");
		sql.append("ORDER BY acao.nome , assunto.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return query.list();
	}

	@Override
	public List<Integer> getAssuntoIdDescendencia(Integer assuntoId) {
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> assuntoList = getAssuntoIdDescendenciaInterno(assuntoId);
		if (assuntoList != null) {
			for (Integer r : assuntoList) {
				result.add(r);
				result.addAll(getAssuntoIdDescendencia(r));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getAssuntoIdDescendenciaInterno(Integer assuntoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id").append("\n");
		sql.append("FROM   atividade.assunto").append("\n");
		sql.append("WHERE  pai_id = :assuntoId").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("assuntoId", assuntoId);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getAssuntoAcaoTransversalList(Integer assuntoAcaoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    aa.id,").append("\n");
		sql.append("    assunto.id AS assunto_id,").append("\n");
		sql.append("    assunto.nome AS assunto_nome,").append("\n");
		sql.append("    assunto.finalidade AS assunto_finalidade,").append("\n");
		sql.append("    assunto.pai_id AS assunto_pai_id,").append("\n");
		sql.append("    acao.id AS acao_id,").append("\n");
		sql.append("    acao.nome AS acao_nome").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto_acao_transversal atr").append("\n");
		sql.append("        INNER JOIN").append("\n");
		sql.append("    atividade.assunto_acao aa ON aa.id = atr.assunto_acao_transversal_id").append("\n");
		sql.append("        INNER JOIN").append("\n");
		sql.append("    atividade.acao acao ON acao.id = aa.acao_id").append("\n");
		sql.append("        INNER JOIN").append("\n");
		sql.append("    atividade.assunto assunto ON assunto.id = aa.assunto_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    atr.assunto_acao_id = :assuntoAcaoId").append("\n");
		sql.append("ORDER BY assunto.nome, acao.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("assuntoAcaoId", assuntoAcaoId);
		List<Object[]> list = query.list();
		List<AssuntoAcao> result = new ArrayList<AssuntoAcao>();
		for (Object[] reg : list) {
			AssuntoAcao r = new AssuntoAcao((Integer) reg[0]);
			r.setAssunto(createAssuntoClass((Integer) reg[1], (String) reg[2], AtividadeFinalidade.valueOf(((Character) reg[3]).toString()), (Integer) reg[4], false));
			r.setAcao(new Acao((Integer) reg[5], (String) reg[6]));
			result.add(r);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getAtividadeAssuntoAcaoList(Integer assuntoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    aa.id,").append("\n");
		sql.append("    assunto.id AS assunto_id,").append("\n");
		sql.append("    assunto.nome AS assunto_nome,").append("\n");
		sql.append("    assunto.finalidade AS assunto_finalidade,").append("\n");
		sql.append("    assunto.pai_id AS assunto_pai_id,").append("\n");
		sql.append("    acao.id AS acao_id,").append("\n");
		sql.append("    acao.nome AS acao_nome").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto_acao aa").append("\n");
		sql.append("        INNER JOIN").append("\n");
		sql.append("    atividade.assunto assunto ON assunto.id = aa.assunto_id").append("\n");
		sql.append("        INNER JOIN").append("\n");
		sql.append("    atividade.acao acao ON acao.id = aa.acao_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    aa.assunto_id = :assuntoId").append("\n");
		sql.append("ORDER BY acao.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("assuntoId", assuntoId);
		List<Object[]> list = query.list();
		List<AssuntoAcao> result = null;
		for (Object[] reg : list) {
			if (result == null) {
				result = new ArrayList<AssuntoAcao>();
			}
			AssuntoAcao r = new AssuntoAcao((Integer) reg[0]);
			r.setAssunto(createAssuntoClass((Integer) reg[1], (String) reg[2], AtividadeFinalidade.valueOf(((Character) reg[3]).toString()), (Integer) reg[4], false));
			r.setAcao(new Acao((Integer) reg[5], (String) reg[6]));
			result.add(r);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getAtividadeAssuntoList(AtividadeFinalidade finalidade) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade, pai_id").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pai_id IS NULL AND finalidade = :finalidade").append("\n");
		sql.append("ORDER BY nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("finalidade", finalidade.name());
		List<Object[]> list = query.list();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Object[] reg : list) {
			result.add(createAssunto((Integer) reg[0], (String) reg[1], AtividadeFinalidade.valueOf(((Character) reg[2]).toString()), (Integer) reg[3], false));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getDemandanteList(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    pes.id, pes.nome").append("\n");
		sql.append("    ,CASE pes.pessoa_tipo").append("\n");
		sql.append("        WHEN 'PF' THEN 'gov.emater.aterweb.model.PessoaFisica'").append("\n");
		sql.append("        WHEN 'PJ' THEN 'gov.emater.aterweb.model.PessoaJuridica'").append("\n");
		sql.append("        WHEN 'GS' THEN 'gov.emater.aterweb.model.PessoaGrupo'").append("\n");
		sql.append("        ELSE ''").append("\n");
		sql.append("    END AS '@class'").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.atividade_pessoa ap").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    pessoa.pessoa pes ON pes.id = ap.pessoa_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    ap.funcao = 'D' AND ap.atividade_id = :id").append("\n");
		sql.append("ORDER BY pes.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getExecutorList(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    pes.id, pes.nome").append("\n");
		sql.append("    ,CASE pes.pessoa_tipo").append("\n");
		sql.append("        WHEN 'PF' THEN 'gov.emater.aterweb.model.PessoaFisica'").append("\n");
		sql.append("        WHEN 'PJ' THEN 'gov.emater.aterweb.model.PessoaJuridica'").append("\n");
		sql.append("        WHEN 'GS' THEN 'gov.emater.aterweb.model.PessoaGrupo'").append("\n");
		sql.append("        ELSE ''").append("\n");
		sql.append("    END AS '@class'").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.atividade_pessoa ap").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    pessoa.pessoa pes ON pes.id = ap.pessoa_id").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    ap.funcao != 'D' AND ap.atividade_id = :id").append("\n");
		sql.append("ORDER BY pes.nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return query.list();
	}

	private void insereCriterio(String comentario, StringBuilder sql, String criterio, boolean juncao) {
		String temp = "    ";
		if (sql.length() > 0) {
			if (juncao) {
				temp = "OR  ";
			} else {
				temp = "AND ";
			}
		}
		sql.append("-- ").append(comentario).append("\n");
		sql.append(temp).append("(").append(criterio).append(")").append("\n");
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> preencheFilhos(Map<String, Object> assunto) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade, pai_id").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pai_id  = :paiId").append("\n");
		sql.append("ORDER BY nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("paiId", assunto.get("id"));
		List<Object[]> list = query.list();
		List<Map<String, Object>> result = null;
		for (Object[] reg : list) {
			if (result == null) {
				result = new ArrayList<Map<String, Object>>();
			}
			result.add(createAssunto((Integer) reg[0], (String) reg[1], AtividadeFinalidade.valueOf(((Character) reg[2]).toString()), (Integer) reg[3], true));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Assunto> preencheFilhosClass(Assunto assunto) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade, pai_id").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    pai_id  = :paiId").append("\n");
		sql.append("ORDER BY nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("paiId", assunto.getId());
		List<Object[]> list = query.list();
		List<Assunto> result = null;
		for (Object[] reg : list) {
			if (result == null) {
				result = new ArrayList<Assunto>();
			}
			result.add(createAssuntoClass((Integer) reg[0], (String) reg[1], AtividadeFinalidade.valueOf(((Character) reg[2]).toString()), (Integer) reg[3], true));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> preenchePai(Integer paiId) {
		if (paiId == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade, pai_id").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    id  = :paiId").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("paiId", paiId);
		List<Object[]> list = query.list();

		Map<String, Object> result = createAssunto((Integer) list.get(0)[0], (String) list.get(0)[1], AtividadeFinalidade.valueOf(((Character) list.get(0)[2]).toString()), (Integer) list.get(0)[3], false);

		if (result.get("pai") != null) {
			result.put("pai", preenchePai((Integer) result.get("pai")));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Assunto preenchePaiClass(Integer paiId) {
		if (paiId == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    id, nome, finalidade, pai_id").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.assunto").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    id  = :paiId").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("paiId", paiId);
		List<Object[]> list = query.list();

		Assunto result = createAssuntoClass((Integer) list.get(0)[0], (String) list.get(0)[1], AtividadeFinalidade.valueOf(((Character) list.get(0)[2]).toString()), (Integer) list.get(0)[3], false);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> restoreByDto(AtividadeCadFiltroDto filtro) {
		try {
			Map<String, Set<Integer>> pessoaListJavaValores = new HashMap<String, Set<Integer>>();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT").append("\n");
			sql.append("    ativ.id AS id,").append("\n");
			sql.append("    ativ.codigo AS codigo,").append("\n");
			sql.append("    ativ.inicio AS inicio,").append("\n");
			sql.append("    ativ.previsao_conclusao AS previsao_conclusao,").append("\n");
			sql.append("    ativ.conclusao AS conclusao,").append("\n");
			sql.append("    met.nome AS metodo,").append("\n");
			sql.append("    CAST(atu.situacao AS CHAR(50)) AS situacao,").append("\n");
			sql.append("    CAST(atu.percentual_conclusao AS CHAR(50)) AS percentual_conclusao,").append("\n");
			sql.append("    atu.registro AS registro,").append("\n");
			sql.append("    ativ.publico_estimado AS publicoEstimado,").append("\n");
			sql.append("    0 AS publicoReal").append("\n");
			sql.append("FROM").append("\n");
			sql.append("    atividade.atividade ativ").append("\n");
			sql.append("        JOIN").append("\n");
			sql.append("    atividade.metodo met ON met.id = ativ.metodo_id").append("\n");
			sql.append("        JOIN").append("\n");
			sql.append("    atividade.atividade_atual_vi atu ON atu.id = ativ.id").append("\n");
			sql.append("WHERE").append("\n");
			sql.append("    ativ.formato = 'E'").append("\n");
			if (!isEmpty(filtro.getCodigo())) {
				sql.append("AND ativ.codigo = :codigo").append("\n");
			} else {
				if (!isEmpty(filtro.getInicio())) {
					sql.append("AND (ativ.inicio >= :inicio OR ativ.inicio IS NULL)").append("\n");
				}
				if (!isEmpty(filtro.getTermino())) {
					sql.append("AND (ativ.inicio <= :termino OR ativ.inicio IS NULL)").append("\n");
				}
				if (!isEmpty(filtro.getMetodoIdList())) {
					sql.append("AND ativ.metodo_id IN (:metodo)").append("\n");
				}
				if (!isEmpty(filtro.getSituacaoIdList())) {
					sql.append("AND atu.situacao IN (:situacao)").append("\n");
				}
				if (!isEmpty(filtro.getPessoaListJava())) {
					sql.append(String.format("AND (%s)", filtraPessoa(filtro.getPessoaListJava(), pessoaListJavaValores))).append("\n");
				}
				if (!isEmpty(filtro.getAtividadeAssuntoAcaoList())) {
					sql.append(String.format("AND ativ.id in (%s)", filtraAtividadeAssuntoAcao(filtro.getAtividadeAssuntoAcaoList()))).append("\n");
				}
			}
			sql.append("ORDER BY ativ.inicio").append("\n");
			SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

			if (!isEmpty(filtro.getCodigo())) {
				query.setParameter("codigo", String.format("%s.%s-%s", filtro.getCodigo().substring(0, 4), filtro.getCodigo().substring(4, 8), filtro.getCodigo().substring(8, 9)));
			} else {
				if (!isEmpty(filtro.getInicio())) {
					query.setParameter("inicio", UtilitarioData.getInstance().formataMilisegundos(filtro.getInicio()));
				}
				if (!isEmpty(filtro.getTermino())) {
					query.setParameter("termino", UtilitarioData.getInstance().formataMilisegundos(filtro.getTermino()));
				}
				if (!isEmpty(filtro.getMetodoIdList())) {
					query.setParameterList("metodo", filtro.getMetodoIdList());
				}
				if (!isEmpty(filtro.getSituacaoIdList())) {
					query.setParameterList("situacao", filtro.getSituacaoIdList());
				}
				if (!isEmpty(filtro.getPessoaListJava())) {
					// configurar os parametros
					for (Map.Entry<String, Set<Integer>> valor : pessoaListJavaValores.entrySet()) {
						query.setParameterList(valor.getKey(), valor.getValue());
					}
				}
				if (!isEmpty(filtro.getAtividadeAssuntoAcaoList())) {
					List<String> atividadeFinalidadeList = (List<String>) filtro.getAtividadeAssuntoAcaoList().get(0);
					List<Integer> assuntoList = (List<Integer>) filtro.getAtividadeAssuntoAcaoList().get(1);
					List<Integer> assuntoAcaoList = (List<Integer>) filtro.getAtividadeAssuntoAcaoList().get(2);
					if (!isEmpty(atividadeFinalidadeList)) {
						query.setParameterList("assuntoFinalidade", atividadeFinalidadeList);
					}
					if (!isEmpty(assuntoList)) {
						query.setParameterList("assuntoId", assuntoList);
					}
					if (!isEmpty(assuntoAcaoList)) {
						query.setParameterList("assuntoAcaoId", assuntoAcaoList);
					}
				}
			}
			List<Map<String, Object>> result = query.list();
			if (!isEmpty(result)) {
				for (Map<String, Object> item : result) {
					Integer id = (Integer) item.get("id");
					if (item.get("inicio") != null) {
						Calendar data = Calendar.getInstance();
						data.setTimeInMillis(((Timestamp) item.get("inicio")).getTime());
						item.put("inicio", UtilitarioData.getInstance().formataData(data));
					}
					if (item.get("previsao_conclusao") != null) {
						Calendar data = Calendar.getInstance();
						data.setTimeInMillis(((Timestamp) item.get("previsao_conclusao")).getTime());
						item.put("previsaoConclusao", UtilitarioData.getInstance().formataData(data));
					}
					if (item.get("conclusao") != null) {
						Calendar data = Calendar.getInstance();
						data.setTimeInMillis(((Timestamp) item.get("conclusao")).getTime());
						item.put("conclusao", UtilitarioData.getInstance().formataData(data));
					}
					item.put("situacao", item.get("situacao"));
					item.put("situacaoDescricao", AtividadeSituacao.valueOf((String) item.get("situacao")).getDescricao());
					item.put("percentualConclusao", ((String) item.get("percentual_conclusao")).toString());
					Calendar data = Calendar.getInstance();
					data.setTimeInMillis(((Timestamp) item.get("registro")).getTime());
					item.put("registro", UtilitarioData.getInstance().formataData(data));
					item.put("demandanteList", getDemandanteList(id));
					item.put("executorList", getExecutorList(id));
					item.put("assuntoAcaoList", getAssuntoAcaoList(id));
					item.put("publicoReal", ((List<?>) item.get("demandanteList")).size());
				}
			}
			return result;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}