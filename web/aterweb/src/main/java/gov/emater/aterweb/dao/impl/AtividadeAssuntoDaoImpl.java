package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.AtividadeAssuntoDao;
import gov.emater.aterweb.model.AssuntoAcao;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.AtividadeAssunto;
import gov.emater.aterweb.model.domain.Confirmacao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository
public class AtividadeAssuntoDaoImpl extends _CrudDaoImpl<AtividadeAssunto, Integer> implements AtividadeAssuntoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AtividadeAssunto> restorePorAtividade(Integer id) {
		List<AtividadeAssunto> result = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    ativ_ass.id,").append("\n");
		sql.append("    ativ_ass.assunto_acao_id,").append("\n");
		sql.append("    ativ_ass.descricao,").append("\n");
		sql.append("    ativ_ass.transversal").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    atividade.atividade_assunto ativ_ass").append("\n");
		sql.append("WHERE ativ_ass.atividade_id = :id").append("\n");
		sql.append("ORDER BY ativ_ass.assunto_acao_id").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("id", id);
		List<Object[]> temp = query.list();
		if (!isEmpty(temp)) {
			result = new ArrayList<AtividadeAssunto>();
			for (Object[] campo : temp) {
				AtividadeAssunto atividadeArquivo = new AtividadeAssunto();
				atividadeArquivo.setId((Integer) campo[0]);
				atividadeArquivo.setAtividade(new Atividade(id));
				atividadeArquivo.setAssuntoAcao(new AssuntoAcao((Integer) campo[1]));
				atividadeArquivo.setDescricao((String) campo[2]);
				atividadeArquivo.setTransversal(Confirmacao.valueOf((String) campo[3]));
				result.add(atividadeArquivo);
			}
		}

		return result;
	}

}