package gov.emater.aterweb.service;

import gov.emater.aterweb.model.EmpregoVi;
import gov.emater.aterweb.model.LotacaoVi;

import java.util.List;

public interface FuncionalService extends CrudService<EmpregoVi, Integer> {

	List<EmpregoVi> restoreEmpregadorAtivoPorEmpregado(Integer id);
	
	List<LotacaoVi> restoreLotacaoAtivaPorEmpregado(Integer id);
	
}
