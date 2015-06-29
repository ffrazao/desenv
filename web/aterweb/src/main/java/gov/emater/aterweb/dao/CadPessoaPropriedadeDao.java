package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.CadPessoaPropriedade;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de Cadastro Único da tabela
 * cad_pessoa_propriedade
 * 
 * @author frazao
 * 
 */
public interface CadPessoaPropriedadeDao extends _CrudDao<CadPessoaPropriedade, Integer> {

	List<Map<String, Object>> restorePessoaNomeByPropriedadeId(Integer propriedadeId);

}
