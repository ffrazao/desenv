package gov.emater.aterweb.dao.impl;

import static gov.emater.aterweb.comum.UtilitarioString.collectionToString;
import gov.emater.aterweb.dao.PessoaGrupoDao;
import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.domain.PessoaGrupoNivelGestao;
import gov.emater.aterweb.mvc.dto.PessoaGrupoCadFiltroDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaGrupoDaoImpl extends _CrudDaoImpl<PessoaGrupo, Integer> implements PessoaGrupoDao {

	private static final String SQL_MATRIZ;

	static {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT   pes.nome, pes.apelido_sigla, gs.*").append("\n");
		sql.append("FROM     pessoa.pessoa_grupo gs").append("\n");
		sql.append("JOIN     pessoa.pessoa pes ON pes.id = gs.id").append("\n");
		sql.append("JOIN     pessoa.pessoa_grupo_tipo gst ON gst.id = gs.pessoa_grupo_tipo_id").append("\n");
		sql.append("WHERE    1 = 1").append("\n");
		sql.append("AND      gst.codigo = '").append(PessoaGrupoTipo.Codigo.PERSONALIZADO.name()).append("'\n");
		SQL_MATRIZ = sql.toString();
	}

	@SuppressWarnings("unchecked")
	protected List<Integer> getIdAncestrais(Integer id) {
		if (id == null) {
			return null;
		}
		List<Integer> result = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT gs.pessoa_grupo_id").append("\n");
		sql.append("FROM   pessoa.pessoa_grupo gs").append("\n");
		sql.append("WHERE  gs.id = :id").append("\n");
		SQLQuery paiQry = getSession().getCurrentSession().createSQLQuery(sql.toString());
		do {
			paiQry.setParameter("id", id);
			List<Integer> pais = (List<Integer>) paiQry.list();
			if (pais == null || pais.size() == 0) {
				id = null;
			} else {
				if (result == null) {
					result = new ArrayList<Integer>();
				}
				result.add(id);
				id = pais.get(0);
			}
		} while (id != null);
		return result;
	}

	protected Integer getIdRaiz(Integer id) {
		List<Integer> ancestrais = getIdAncestrais(id);
		if (ancestrais == null || ancestrais.size() == 0) {
			return null;
		}
		return ancestrais.get(ancestrais.size() - 1);
	}

	@SuppressWarnings("unchecked")
	protected Set<Integer> getIdRaizPeloPessoaGrupoNome(String nome) {
		Set<Integer> result = new HashSet<Integer>();
		StringBuilder sql;
		sql = new StringBuilder();
		sql.append("SELECT gs.id").append("\n");
		sql.append("FROM   pessoa.pessoa_grupo gs").append("\n");
		sql.append("JOIN   pessoa.pessoa_grupo_tipo gst ON gst.id = gs.pessoa_grupo_tipo_id").append("\n");
		sql.append("JOIN   pessoa.pessoa pes ON pes.id = gs.id").append("\n");
		sql.append("WHERE  1 = 1").append("\n");
		// sql.append("AND    gst.codigo = '").append(PessoaGrupoTipo.Codigo.PERSONALIZADO.name()).append("'\n");
		sql.append("AND    (pes.nome like :nome OR pes.apelido_sigla like :nome)").append("\n");
		SQLQuery qry = getSession().getCurrentSession().createSQLQuery(sql.toString());
		qry.setParameter("nome", "%".concat(nome.replaceAll("\\s", "%")).concat("%"));
		List<Integer> inicialIds = qry.list();

		if (inicialIds != null) {
			for (Integer id : inicialIds) {
				List<Integer> ancestrais = getIdAncestrais(id);
				if (ancestrais != null) {
					result.addAll(ancestrais);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> restoreByDto(PessoaGrupoCadFiltroDto filtro, Integer pai) {
		StringBuilder sql = new StringBuilder(SQL_MATRIZ);
		if (pai == null) {
			sql.append("AND      gs.pessoa_grupo_id IS NULL").append("\n");
		} else {
			sql.append("AND      gs.pessoa_grupo_id = :pai").append("\n");
		}
		if (filtro.getNome() != null && filtro.getNome().trim().length() > 0) {
			if (filtro.getIdsFiltrados() == null) {
				filtro.setIdsFiltrados(collectionToString(getIdRaizPeloPessoaGrupoNome(filtro.getNome())));
			}
			sql.append(String.format("AND gs.id in (%s)", filtro.getIdsFiltrados())).append("\n");
		}
		if (filtro.getSituacaoGrupo() != null) {
			sql.append(String.format("AND pes.situacao = '%s'", filtro.getSituacaoGrupo().name())).append("\n");
		}
		List<String> nivelGestao = new ArrayList<String>();
		if (filtro.isGestorGrupoTecnicoCheck()) {
			nivelGestao.add(PessoaGrupoNivelGestao.T.name());
		}
		if (filtro.isGestorGrupoUnidadeOrganizacionalCheck()) {
			nivelGestao.add(PessoaGrupoNivelGestao.U.name());
		}
		if (filtro.isGestorGrupoInstituicaoCheck()) {
			nivelGestao.add(PessoaGrupoNivelGestao.I.name());
		}
		if (nivelGestao.size() > 0) {
			sql.append(String.format("AND gs.nivel_gestao in (%s)", collectionToString(nivelGestao, true))).append("\n");
		}
		sql.append("ORDER BY gst.codigo, pes.nome, pes.apelido_sigla").append("\n");
		SQLQuery qry = getSession().getCurrentSession().createSQLQuery(sql.toString());
		if (pai != null) {
			qry.setParameter("pai", pai);
		}
		return qry.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> restoreFilhos(Integer pai) {
		StringBuilder sql = new StringBuilder(SQL_MATRIZ);
		sql.append("AND      gs.pessoa_grupo_id = :pai").append("\n");
		sql.append("ORDER BY gst.codigo, pes.nome, pes.apelido_sigla").append("\n");
		SQLQuery qry = getSession().getCurrentSession().createSQLQuery(sql.toString());
		if (pai != null) {
			qry.setParameter("pai", pai);
		}
		return qry.list();
	}

}