package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.EmpregoViDao;
import gov.emater.aterweb.dao.LotacaoViDao;
import gov.emater.aterweb.model.EmpregoVi;
import gov.emater.aterweb.model.LotacaoVi;
import gov.emater.aterweb.service.FuncionalService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionalServiceImpl extends CrudServiceImpl<EmpregoVi, Integer, EmpregoViDao> implements FuncionalService {

	@Autowired
	private LotacaoViDao lotacaoViDao;

	@Autowired
	public FuncionalServiceImpl(EmpregoViDao dao) {
		super(dao);
	}

	@Override
	public List<EmpregoVi> restoreEmpregadorAtivoPorEmpregado(Integer empregadoId) {
		if (empregadoId == null) {
			return null;
		}
		EmpregoVi exemplo = new EmpregoVi();
		exemplo.setEmpregadoId(empregadoId);
		return getDao().restore(exemplo);
	}

	@Override
	public List<LotacaoVi> restoreLotacaoAtivaPorEmpregado(Integer empregadoId) {
		if (empregadoId == null) {
			return null;
		}
		LotacaoVi exemplo = new LotacaoVi();
		exemplo.setEmpregadoId(empregadoId);
		return lotacaoViDao.restore(exemplo);
	}

}