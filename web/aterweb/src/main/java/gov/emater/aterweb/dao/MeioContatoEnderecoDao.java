package gov.emater.aterweb.dao;

import java.util.List;
import java.util.Map;

import gov.emater.aterweb.model.MeioContatoEndereco;

/**
 * Define os métodos exclusivos ao DAO de Meio Contato Endereco
 * 
 * @author frazao
 * 
 */
public interface MeioContatoEnderecoDao extends _CrudDao<MeioContatoEndereco, Integer> {

	List<Map<String, Object>> restoreExploracaoPorMeioContato(Integer meioContatoEnderecoId);

	List<Map<String, Object>> restorePorPessoaId(Integer pessoaId, Boolean somentePropriedadeRural);

}