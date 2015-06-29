package gov.emater.aterweb.service;

import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PrevisaoProducao;
import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.domain.PerspectivaProducao;

import java.util.List;
import java.util.Map;

public interface IndiceProducaoService extends CrudService<PrevisaoProducao, Integer> {

	List<Map<String, Object>> getProdutoServicoPorPerspectiva(PerspectivaProducao perspectivaProducao, ProdutoServico pai);

	List<Map<String, Object>> getPropriedadeRuralPorPessoaGrupo(PessoaGrupo pessoaGrupo);

}
