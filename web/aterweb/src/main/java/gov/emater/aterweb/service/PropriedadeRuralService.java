package gov.emater.aterweb.service;

import gov.emater.aterweb.model.PessoaGrupo;
import gov.emater.aterweb.model.PropriedadeRural;

import java.util.List;
import java.util.Map;

public interface PropriedadeRuralService extends CrudService<PropriedadeRural, Integer> {

	Map<String, List<Map<String, Object>>> restoreComunicadeBaciaHidrografica(PessoaGrupo pessoaGrupo);

}