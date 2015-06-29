package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.mvc.dto.PessoaCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Pessoa
 * 
 * @author frazao
 * 
 */
public interface PessoaDao extends _CrudDao<Pessoa, Integer> {

	List<Map<String, Object>> restoreByDto(PessoaCadFiltroDto filtro);

	Integer getEstado(String estado, PessoaGrupoTipo pessoaGrupoTipo);

	Integer getMunicipio(Integer temp, String string);

	List<Map<String, Object>> procurarEnderecoPorPessoa(String nome, String documento);

}
