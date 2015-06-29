package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaRelacionamento;
import gov.emater.aterweb.model.RelacionamentoTipo;

import java.util.List;

/**
 * Define os métodos exclusivos ao DAO de Pessoas do Relacionamento
 * 
 * @author frazao
 * 
 */
public interface PessoaRelacionamentoDao extends _CrudDao<PessoaRelacionamento, Integer> {

	List<?> restorePessoaPorRelacionamentoTipo(Pessoa pessoa, RelacionamentoTipo relacionamentoTipo);

}