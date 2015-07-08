package br.gov.df.emater.dao.teste.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import br.gov.df.emater.dao._metodo.Filtro;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

public class TesteDaoImpl implements Filtro<Teste, TesteFiltroDto> {

	@PersistenceContext
	private EntityManager entityManager;

	private JpaEntityInformation<Teste, ?> entityInformation;

	@PostConstruct
	public void postConstruct() {
		this.entityInformation = JpaEntityInformationSupport.getMetadata(Teste.class, entityManager);
	}

	@Override
	public List<Teste> filtrar(TesteFiltroDto dto) {
		System.out.println("Ok - " + this.entityInformation.getEntityName());
		return null;
	}

}
