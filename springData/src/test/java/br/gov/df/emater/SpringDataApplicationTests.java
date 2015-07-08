package br.gov.df.emater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.df.emater.dao.teste.TesteDao;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataApplication.class)
public class SpringDataApplicationTests {
	
	@Autowired
	private TesteDao testeDao;

	@Test
	public void contextLoads() {
		Teste teste = null;
		teste = testeDao.findOne(401L);
		testeDao.filtrar(new TesteFiltroDto());
		System.out.println(teste);
	}

}
