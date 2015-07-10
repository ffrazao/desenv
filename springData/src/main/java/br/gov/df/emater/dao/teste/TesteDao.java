package br.gov.df.emater.dao.teste;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import br.gov.df.emater.dao._metodo.Filtro;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

public interface TesteDao extends JpaRepository<Teste, Long>, QueryDslPredicateExecutor<Teste>, Filtro<Teste, TesteFiltroDto> {

}