package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaRelacionamentoCidadeBaciaHidrograficaVi;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Pessoas do Relacionamento
 * 
 * @author frazao
 * 
 */
public interface PessoaRelacionamentoCidadeBaciaHidrograficaViDao extends _CrudDao<PessoaRelacionamentoCidadeBaciaHidrograficaVi, Integer> {

	List<Map<String,Object>> restorePorCidade(PessoaGrupo pessoaGrupo);

}