package br.gov.df.emater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.df.emater.bo.TesteBo;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataApplication.class)
public class SpringDataApplicationTests {
	
	@Autowired
	private TesteBo testeBo;

	@Test
	public void contextLoads() {
		Page<Teste> r = null;
		TesteFiltroDto d = new TesteFiltroDto();

		d.setPaginaTamanho(5);
		
		r = testeBo.encontrar(d);
		System.out.format("res[%s] tot[%d]\n", r.getContent(), d.getTotalRegistro());
		
		d.setNome("%5%");
		r = testeBo.encontrar(d);
		System.out.format("res[%s] tot[%d]\n", r.getContent(), d.getTotalRegistro());

		d.setIdIni(53l);
		r = testeBo.encontrar(d);
		System.out.format("res[%s] tot[%d]\n", r.getContent(), d.getTotalRegistro());
		
		d.setIdFim(55l);
		r = testeBo.encontrar(d);
		System.out.format("res[%s] tot[%d]\n", r.getContent(), d.getTotalRegistro());
		
		d.setIdIni(null);
		d.setIdFim(null);
		d.setNome(null);

		d.setPaginaNumero(3);
		r = testeBo.encontrar(d);
		System.out.format("res[%s] tot[%d]\n", r.getContent(), d.getTotalRegistro());
	}

}
