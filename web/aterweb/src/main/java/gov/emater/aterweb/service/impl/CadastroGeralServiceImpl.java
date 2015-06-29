package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.CadOrigemDao;
import gov.emater.aterweb.dao.CadPessoaDao;
import gov.emater.aterweb.dao.CadPessoaErroDao;
import gov.emater.aterweb.dao.CadPessoaPropriedadeDao;
import gov.emater.aterweb.dao.CadPropriedadeDao;
import gov.emater.aterweb.model.CadPropriedade;
import gov.emater.aterweb.mvc.dto.CadastroGeralCadFiltroDto;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.service.CadastroGeralService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGeralServiceImpl extends CrudServiceImpl<CadPropriedade, Integer, CadPropriedadeDao> implements CadastroGeralService {

	@Autowired
	private CadOrigemDao cadOrigemDao;
	@Autowired
	private CadPessoaDao cadPessoaDao;
	@Autowired
	private CadPessoaErroDao cadPessoaErroDao;
	@Autowired
	private CadPessoaPropriedadeDao cadPessoaPropriedadeDao;

	@Autowired
	public CadastroGeralServiceImpl(CadPropriedadeDao dao) {
		super(dao);
	}

	@SuppressWarnings("unchecked")
	private void fetch(List<?> result) {
		if (result != null) {
			for (Map<String, Object> linha : (List<Map<String, Object>>) result) {
				List<Map<String, Object>> produtores = cadPessoaPropriedadeDao.restorePessoaNomeByPropriedadeId((Integer) linha.get("id"));
				linha.put("produtores", produtores);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<?> filtrarByDto(Dto dto) {
		CadastroGeralCadFiltroDto filtro = (CadastroGeralCadFiltroDto) dto;
		Integer p[] = calculaNumeroPagina(filtro.getNumeroPagina());
		List<?> result = getDao().restoreByDto(filtro, p[0], p[1]);
		fetch(result);
		return result;
	}

}