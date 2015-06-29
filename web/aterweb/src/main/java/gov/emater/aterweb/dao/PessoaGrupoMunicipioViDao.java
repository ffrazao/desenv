package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupoMunicipioVi;

import java.util.List;
import java.util.Map;

/**
 * Define os métodos exclusivos ao DAO de PessoaGrupoMunicipioVi
 * 
 * @author frazao
 * 
 */
public interface PessoaGrupoMunicipioViDao extends _CrudDao<PessoaGrupoMunicipioVi, Integer> {

	List<Map<String, Object>> restoreMap(Integer pai);

}