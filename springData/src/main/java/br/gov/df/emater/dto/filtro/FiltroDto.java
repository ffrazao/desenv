package br.gov.df.emater.dto.filtro;

import br.gov.df.emater.dto.Dto;

public interface FiltroDto extends Dto {

	int PAGINA_NUMERO_PADRAO = 1;

	int PAGINA_TAMANHO_PADRAO = 100;

	int getPaginaNumero();

	int getPaginaTamanho();

	void setPaginaNumero(int paginaNumero);

	void setPaginaTamanho(int paginaTamanho);

}