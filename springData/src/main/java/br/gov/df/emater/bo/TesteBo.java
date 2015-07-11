package br.gov.df.emater.bo;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.df.emater.dao.TestePredicate;
import br.gov.df.emater.dao.teste.TesteDao;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

import com.mysema.query.types.expr.BooleanExpression;

@Service
public class TesteBo {

	@Resource
	private TesteDao testeDao;

	@Transactional(readOnly = true)
	public Page<Teste> encontrar(TesteFiltroDto d) {
		BooleanExpression bE = null;
		Pageable p = new PageRequest(d.getPaginaNumero(), d.getPaginaTamanho());

		if (d.getIdIni() != null && d.getIdFim() != null) {
			BooleanExpression x = TestePredicate.idEntre(d.getIdIni(), d.getIdFim());
			bE = bE == null ? x : bE.and(x);
		}
		if (!(d.getNome() == null || "".equals(d.getNome()))) {
			BooleanExpression x = TestePredicate.porNome(d.getNome());
			bE = bE == null ? x : bE.and(x);
		}

		Page<Teste> result = bE == null ? testeDao.findAll(p) : testeDao.findAll(bE, p);
		d.setTotalRegistro(result.getTotalElements());

		return result;
	}
}
