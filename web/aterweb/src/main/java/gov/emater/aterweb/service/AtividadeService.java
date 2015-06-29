package gov.emater.aterweb.service;

import java.util.List;

import gov.emater.aterweb.model.Atividade;
import gov.emater.aterweb.model.domain.AtividadeFinalidade;

public interface AtividadeService extends CrudService<Atividade, Integer> {
	
	List<?> getAtividadeAssuntoList(AtividadeFinalidade finalidade);

	List<?> getAtividadeAssuntoAcaoList(Integer assuntoId);

	List<?> getAssuntoAcaoTransversalList(Integer assuntoAcaoId);

	List<?> getAssuntoAcaoFilhos(Integer assuntoId);

	List<Integer> getAssuntoIdDescendencia(Integer assuntoId);

}
