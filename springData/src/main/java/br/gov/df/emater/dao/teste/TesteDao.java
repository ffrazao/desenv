package br.gov.df.emater.dao.teste;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.df.emater.dao._metodo.Filtro;
import br.gov.df.emater.dto.filtro.TesteFiltroDto;
import br.gov.df.emater.entidade.teste.Teste;

public interface TesteDao extends JpaRepository<Teste, Long>, Filtro<Teste, TesteFiltroDto> {

}