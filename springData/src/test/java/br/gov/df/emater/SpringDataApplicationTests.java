package br.gov.df.emater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.df.emater.dao.teste.TesteDao;
import br.gov.df.emater.entidade.teste.Teste;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataApplication.class)
public class SpringDataApplicationTests {
	
	@Autowired
	private TesteDao testeDao;

	@Test
	public void contextLoads() {
		Page<Teste> pagina = testeDao.findAll(new PageRequest(3, 4));
		System.out.println(pagina.getContent());
	}

}
