package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.domain.PerspectivaProducao;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de ProdutoServico
 * 
 * @author frazao
 * 
 */
public interface ProdutoServicoDao extends _CrudDao<ProdutoServico, Integer> {

	List<Map<String, Object>> getProdutoServicoPorPerspectiva(PerspectivaProducao perspectivaProducao, ProdutoServico pai);

}