package br.gov.df.emater.dao._metodo;

import java.io.Serializable;
import java.util.List;

public interface Filtro<E, D extends Serializable> {

	List<E> filtrar(D dto);

}
