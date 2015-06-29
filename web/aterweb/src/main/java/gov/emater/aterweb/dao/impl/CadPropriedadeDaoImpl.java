package gov.emater.aterweb.dao.impl;

import gov.emater.aterweb.dao.CadPropriedadeDao;
import gov.emater.aterweb.model.CadPropriedade;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.model.domain.Escolaridade;
import gov.emater.aterweb.model.domain.Sexo;
import gov.emater.aterweb.model.domain.StatusValidoInvalido;
import gov.emater.aterweb.mvc.dto.CadastroGeralCadFiltroDto;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class CadPropriedadeDaoImpl extends _CrudDaoImpl<CadPropriedade, Integer> implements CadPropriedadeDao {

	public CadPropriedadeDaoImpl() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> restoreByDto(CadastroGeralCadFiltroDto filtro, Integer registroInicial, Integer registrosPorPagina) {
		Criteria restoreByDtoCriteria = getSession().getCurrentSession().createCriteria(getTipo(), "this");
		restoreByDtoCriteria.createCriteria("cadPessoaList", "pessoa");
		
		// configurar a pesquisa
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("this.id"), "id");
		projections.add(Projections.groupProperty("this.nome"), "nome");
		projections.add(Projections.groupProperty("this.status"), "status");
		restoreByDtoCriteria.setProjection(projections);
		restoreByDtoCriteria.addOrder(Order.asc("this.nome"));
		restoreByDtoCriteria.addOrder(Order.asc("pessoa.nome"));
		restoreByDtoCriteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		// filtro de propriedades
		if (!isEmpty(filtro.getPropriedadeArrendatario())) {
			restoreByDtoCriteria.add(Restrictions.eq("arrendatario", Confirmacao.valueOf(filtro.getPropriedadeArrendatario())));
		}
		if (!isEmpty(filtro.getPropriedadeCidade())) {
			restoreByDtoCriteria.add(Restrictions.like("cidade", filtro.getPropriedadeCidade().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		if (!isEmpty(filtro.getPropriedadeCnpj())) {
			restoreByDtoCriteria.add(Restrictions.eq("cnpj", filtro.getPropriedadeCnpj()));
		}
		if (!isEmpty(filtro.getPropriedadeNome())) {
			restoreByDtoCriteria.add(Restrictions.like("nome", filtro.getPropriedadeNome().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		if (!isEmpty(filtro.getPropriedadeSituacaoFundiaria())) {
			restoreByDtoCriteria.add(Restrictions.eq("situacaoFundiaria", filtro.getPropriedadeSituacaoFundiaria()));
		}
		if (!isEmpty(filtro.getPropriedadeStatus())) {
			restoreByDtoCriteria.add(Restrictions.eq("status", StatusValidoInvalido.valueOf(filtro.getPropriedadeStatus())));
		}
		if (!isEmpty(filtro.getPropriedadeTemApp())) {
			restoreByDtoCriteria.add(Restrictions.eq("temApp", Confirmacao.valueOf(filtro.getPropriedadeTemApp())));
		}
		if (!isEmpty(filtro.getPropriedadeTemPlanoUtilizacao())) {
			restoreByDtoCriteria.add(Restrictions.eq("temPlanoUtilizacao", Confirmacao.valueOf(filtro.getPropriedadeTemPlanoUtilizacao())));
		}
		if (!isEmpty(filtro.getPropriedadeTemReservaLegal())) {
			restoreByDtoCriteria.add(Restrictions.eq("temReservaLegal", Confirmacao.valueOf(filtro.getPropriedadeTemReservaLegal())));
		}
		if (filtro.getPropriedadeAreaPropriedadeFin() != null) {
			restoreByDtoCriteria.add(Restrictions.le("areaPropriedade", filtro.getPropriedadeAreaPropriedadeFin()));			
		}
		if (filtro.getPropriedadeAreaPropriedadeIni() != null) {
			restoreByDtoCriteria.add(Restrictions.ge("areaPropriedade", filtro.getPropriedadeAreaPropriedadeIni()));			
		}
		
		// filtro de produtores
		if (!isEmpty(filtro.getProdutorCpf())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.cpf", filtro.getProdutorCpf()));
		}
		if (!isEmpty(filtro.getProdutorEscolaridade())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.escolaridade", Escolaridade.valueOf(filtro.getProdutorEscolaridade())));
		}
		if (filtro.getProdutorNascimentoFin() != null) {
			restoreByDtoCriteria.add(Restrictions.le("pessoa.nascimento", filtro.getProdutorNascimentoFin()));
		}
		if (filtro.getProdutorNascimentoIni() != null) {
			restoreByDtoCriteria.add(Restrictions.ge("pessoa.nascimento", filtro.getProdutorNascimentoIni()));
		}
		if (!isEmpty(filtro.getProdutorNome())) {
			restoreByDtoCriteria.add(Restrictions.like("pessoa.nome", filtro.getProdutorNome().replace(" ", "%"), MatchMode.ANYWHERE));
		}
		if (!isEmpty(filtro.getProdutorNumeroDap())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.numeroDap", filtro.getProdutorNumeroDap()));
		}
		if (!isEmpty(filtro.getProdutorNumeroInscricaoSefDf())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.numeroInscricaoSefDf", filtro.getProdutorNumeroInscricaoSefDf()));
		}
		if (!isEmpty(filtro.getProdutorSexo())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.sexo", Sexo.valueOf(filtro.getProdutorSexo())));
		}
		if (!isEmpty(filtro.getProdutorStatus())) {
			restoreByDtoCriteria.add(Restrictions.eq("pessoa.status", StatusValidoInvalido.valueOf(filtro.getProdutorStatus())));
		}

		return (List<Map<String, Object>>) findByCriteria(restoreByDtoCriteria, registroInicial, registrosPorPagina);
	}
	
}