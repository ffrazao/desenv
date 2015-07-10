package br.gov.df.emater.bo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.gov.df.emater.dao.teste.TesteDao;
import br.gov.df.emater.entidade.teste.Teste;

@Service
public class TesteBo {

	@Resource
	private TesteDao testeDao;
	
	public List<Teste> encontrar() {
		return testeDao.findAll();
	}
}
