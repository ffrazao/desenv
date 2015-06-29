package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.ModuloDao;
import gov.emater.aterweb.dao.PerfilDao;
import gov.emater.aterweb.model.Modulo;
import gov.emater.aterweb.model.ModuloPerfil;
import gov.emater.aterweb.model.Perfil;
import gov.emater.aterweb.mvc.dto.Dto;
import gov.emater.aterweb.mvc.dto.ModuloCadFiltroDto;
import gov.emater.aterweb.service.ModuloService;
import gov.emater.aterweb.service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModuloServiceImpl extends CrudServiceImpl<Modulo, Integer, ModuloDao> implements ModuloService {

	@Autowired
	private PerfilDao perfilDao;

	@Autowired
	public ModuloServiceImpl(ModuloDao dao) {
		super(dao);
	}

	private void fetch(Modulo entidade) {
		if (entidade != null) {
			if (entidade.getModuloPerfilList() != null) {
				List<ModuloPerfil> moduloPerfilList = new ArrayList<ModuloPerfil>();
				for (ModuloPerfil e : entidade.getModuloPerfilList()) {
					
					Perfil perfil = new Perfil();
					perfil.setId(e.getPerfil().getId());
					perfil.setNome(e.getPerfil().getNome());
					
					Modulo modulo = new Modulo();
					modulo.setId(e.getModulo().getId());
					modulo.setNome(e.getModulo().getNome());
					
					ModuloPerfil moduloPerfil = new ModuloPerfil();
					moduloPerfil.setId(e.getId());
					moduloPerfil.setPerfil(perfil);
					moduloPerfil.setModulo(modulo);
					
					moduloPerfilList.add(moduloPerfil);
				}
				entidade.setModuloPerfilList(moduloPerfilList);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> filtrarByDto(Dto dto) {
		ModuloCadFiltroDto filtro = (ModuloCadFiltroDto) dto;
		Integer p[] = calculaNumeroPagina(filtro.getNumeroPagina());
		List<Map<String, Object>> result = getDao().restoreByDto(filtro, p[0], p[1]);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Modulo restore(Integer id) {
		Modulo result = super.restore(id);

		// restaurar dados subjacentes
		fetch(result);

		return result;
	}

	@Override
	@Transactional
	public Modulo save(Modulo entidade) {

		// preparar a gravaï¿½ï¿½o dos perfis
		if (entidade.getModuloPerfilList() != null && entidade.getModuloPerfilList().size() > 0) {
			// marcar para limpar
			List<ModuloPerfil> removidos = new ArrayList<ModuloPerfil>();
			for (ModuloPerfil moduloPerfil : entidade.getModuloPerfilList()) {
				if (moduloPerfil.getPerfil() == null || moduloPerfil.getPerfil().getId() == null) {
					removidos.add(moduloPerfil);
				} else {
					moduloPerfil.setModulo(entidade);
					moduloPerfil.setPerfil(perfilDao.restore(moduloPerfil.getPerfil().getId()));
				}
			}
			entidade.getModuloPerfilList().removeAll(removidos);
		}

		if (entidade.getModuloPerfilList() == null || entidade.getModuloPerfilList().size() == 0) {
			throw new ServiceException("Selecione no mínimo 1 perfil!");
		}

		entidade = super.save(entidade);

		return entidade;
	}

}
