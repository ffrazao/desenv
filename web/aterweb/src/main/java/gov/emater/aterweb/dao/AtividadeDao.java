package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.domain.AtividadeFinalidade;
import gov.emater.aterweb.mvc.dto.AtividadeCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Ação
 * 
 * @author frazao
 * 
 */
public interface AtividadeDao extends _CrudDao<Atividade, Integer> {

	List<?> getAssuntoAcaoFilhos(Integer assuntoId);

	List<?> getAssuntoAcaoTransversalList(Integer assuntoAcaoId);

	List<?> getAtividadeAssuntoAcaoList(Integer assuntoId);

	List<?> getAtividadeAssuntoList(AtividadeFinalidade finalidade);

	List<Map<String, Object>> restoreByDto(AtividadeCadFiltroDto filtro);

	List<Integer> getAssuntoIdDescendencia(Integer assuntoId);

}