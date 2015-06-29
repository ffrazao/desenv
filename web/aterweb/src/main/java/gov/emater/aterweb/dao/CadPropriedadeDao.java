package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.CadPropriedade;
import gov.emater.aterweb.mvc.dto.CadastroGeralCadFiltroDto;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de Cadastro �nico da tabela
 * cad_propriedade
 * 
 * @author frazao
 * 
 */
public interface CadPropriedadeDao extends _CrudDao<CadPropriedade, Integer> {

	List<Map<String, Object>> restoreByDto(CadastroGeralCadFiltroDto filtro, Integer registroInicial, Integer registrosPorPagina);

}
