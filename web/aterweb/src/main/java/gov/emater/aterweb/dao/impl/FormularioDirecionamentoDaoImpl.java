package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.FormularioDirecionamentoDao;
import gov.emater.aterweb.model.FormularioDirecionamento;
import gov.emater.aterweb.model.Usuario;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository
public class FormularioDirecionamentoDaoImpl extends _CrudDaoImpl<FormularioDirecionamento, Integer> implements FormularioDirecionamentoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> formulariosAtivos(Usuario usuario) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id").append("\n");
		sql.append("     , a.codigo").append("\n");
		sql.append("     , a.nome").append("\n");
		sql.append("     , a.inicio").append("\n");
		sql.append("     , a.termino").append("\n");
		sql.append("     , a.permitir_resposta_anonima").append("\n");
		sql.append("     , a.permitir_reenviar_resposta").append("\n");
		sql.append("     , a.permitir_excluir_resposta").append("\n");
		sql.append("     , a.resposta_direcionada").append("\n");
		sql.append("     , b.id as formulario_direcionamento_id").append("\n");
		sql.append("     , b.complemento").append("\n");
		sql.append("     , b.ordem").append("\n");
		sql.append("from enquete.formulario a").append("\n");
		sql.append("join enquete.formulario_direcionamento b").append("\n");
		sql.append("on   b.formulario_id = a.id").append("\n");
		sql.append("where  a.resposta_direcionada = 'S'").append("\n");
		sql.append("and    b.usuario_id = :usuario_id").append("\n");
		sql.append("and    (   (a.inicio <= now()").append("\n");
		sql.append("            and a.termino > now())").append("\n");

		sql.append("        or ((b.inicio is not null and b.inicio <= now())").append("\n");
		sql.append("            and (b.termino is not null and b.termino > now())))").append("\n");

		sql.append("union all").append("\n");
		sql.append("select a.id").append("\n");
		sql.append("     , a.codigo").append("\n");
		sql.append("     , a.nome").append("\n");
		sql.append("     , a.inicio").append("\n");
		sql.append("     , a.termino").append("\n");
		sql.append("     , a.permitir_resposta_anonima").append("\n");
		sql.append("     , a.permitir_reenviar_resposta").append("\n");
		sql.append("     , a.permitir_excluir_resposta").append("\n");
		sql.append("     , a.resposta_direcionada").append("\n");
		sql.append("     , null as formulario_direcionamento_id").append("\n");
		sql.append("     , null as complemento").append("\n");
		sql.append("     , 999999 as ordem").append("\n");
		sql.append("from enquete.formulario a").append("\n");
		sql.append("where a.resposta_direcionada = 'N'").append("\n");
		sql.append("and    (a.inicio <= now()").append("\n");
		sql.append("        and a.termino > now())").append("\n");
		sql.append("order by ordem, nome").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("usuario_id", usuario == null ? null : usuario.getId());
		return (List<Object[]>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvaliacao() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT").append("\n");
		sql.append("    f.codigo AS formulario_codigo,").append("\n");
		sql.append("    fd.lixo AS tipo_avaliacao,").append("\n");
		sql.append("    emp.empregador_sigla AS avaliador_empresa,").append("\n");
		sql.append("    emp.matricula AS avaliador_matricula,").append("\n");
		sql.append("    pes.nome AS avaliador_nome,").append("\n");
		sql.append("    fd.complemento AS avaliado,").append("\n");
		sql.append("    p.pergunta,").append("\n");
		sql.append("    IF(ov.id IS NULL, r.valor, ov.codigo) AS resposta").append("\n");
		sql.append("FROM").append("\n");
		sql.append("    enquete.formulario f").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    enquete.formulario_direcionamento fd ON fd.formulario_id = f.id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    sistema.usuario u ON u.id = fd.usuario_id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    pessoa.pessoa pes ON pes.id = u.pessoa_id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    funcional.emprego_vi emp ON emp.empregado_id = pes.id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    enquete.resposta_versao rv ON rv.formulario_direcionamento_id = fd.id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    enquete.resposta r ON r.resposta_versao_id = rv.id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    enquete.pergunta p ON p.id = r.pergunta_id").append("\n");
		sql.append("        JOIN").append("\n");
		sql.append("    enquete.opcao_resposta op ON op.id = p.opcao_resposta_id").append("\n");
		sql.append("        LEFT JOIN").append("\n");
		sql.append("    enquete.opcao_valor ov ON ov.opcao_resposta_id = op.id").append("\n");
		sql.append("        AND ov.id = r.valor").append("\n");
		sql.append("WHERE").append("\n");
		sql.append("    op.id IN (1 , 2)").append("\n");
		sql.append("ORDER BY fd.complemento , emp.empregador_sigla , emp.matricula , f.codigo , p.ordem").append("\n");
		SQLQuery query = getSession().getCurrentSession().createSQLQuery(sql.toString());
		//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Object[]>) query.list();
	}

}