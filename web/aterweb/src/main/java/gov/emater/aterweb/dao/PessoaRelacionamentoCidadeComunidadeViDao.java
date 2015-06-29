package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PessoaRelacionamentoCidadeComunidadeVi;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Pessoas do Relacionamento
 * 
 * @author frazao
 * 
 */
public interface PessoaRelacionamentoCidadeComunidadeViDao extends _CrudDao<PessoaRelacionamentoCidadeComunidadeVi, Integer> {

	List<Map<String, Object>> restorePorCidade(PessoaGrupo pessoaGrupo);

}