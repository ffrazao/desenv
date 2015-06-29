package gov.emater.aterweb.dao;

import gov.emater.aterweb.model.PessoaGrupoEstadoVi;

import java.util.List;
import java.util.Map;

/**
 * Define os m�todos exclusivos ao DAO de PessoaGrupoEstadoVi
 * 
 * @author frazao
 * 
 */
public interface PessoaGrupoEstadoViDao extends _CrudDao<PessoaGrupoEstadoVi, Integer> {

	List<Map<String, Object>> restoreMap(Integer pai);

}