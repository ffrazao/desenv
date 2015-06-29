package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.AtividadeArquivoDao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.AtividadeArquivo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository
public class AtividadeArquivoDaoImpl extends _CrudDaoImpl<AtividadeArquivo, Integer> implements AtividadeArquivoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AtividadeArquivo> restorePorAtividadeId(Integer id) {
		List<AtividadeArquivo> result = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    ativ_arq.id,").append("\n");
		sql.append("    ativ_arq.data_upload,").append("\n");
		sql.append("    ativ_arq.descricao,").append("\n");
		sql.append("    arq.id AS arquivoId,").append("\n");
		sql.append("    arq.nome AS arquivoNome,").append("\n");
		sql.append("    arq.tamanho AS arquivoTamanho,").append("\n");
		sql.append("    arq.tipo AS arquivoTipo").append("\n");
		sql.append("FROM atividade.atividade_arquivo ativ_arq").append("\n");
		sql.append("JOIN pessoa.arquivo arq ").append("\n");
		sql.append("ON   arq.id = ativ_arq.arquivo_id").append("\n");
		sql.append("WHERE ativ_arq.atividade_id = :id").append("\n");
		sql.append("ORDER BY arq.nome, ativ_arq.data_upload").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("id", id);
		List<Object[]> temp = query.list();
		if (!isEmpty(temp)) {
			result = new ArrayList<AtividadeArquivo>();
			for (Object[] campo : temp) {
				AtividadeArquivo atividadeArquivo = new AtividadeArquivo();
				atividadeArquivo.setId((Integer) campo[0]);
				atividadeArquivo.setAtividade(new Atividade(id));

				Calendar data = Calendar.getInstance();
				data.setTimeInMillis(((Timestamp) campo[1]).getTime());
				atividadeArquivo.setDataUpload(data);
				atividadeArquivo.setDescricao((String) campo[2]);

				Arquivo arquivo = new Arquivo();
				arquivo.setId((Integer) campo[3]);
				arquivo.setNome((String) campo[4]);
				arquivo.setTamanho((Integer) campo[5]);
				arquivo.setTipo((String) campo[6]);

				atividadeArquivo.setArquivo(arquivo);

				result.add(atividadeArquivo);
			}
		}

		return result;
	}

}