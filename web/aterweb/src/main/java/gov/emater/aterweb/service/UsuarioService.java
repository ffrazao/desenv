package gov.emater.aterweb.service;

import gov.emater.aterweb.model.Usuario;
import gov.emater.aterweb.mvc.dto.MudarSenhaAtualDto;

import org.springframework.security.core.Authentication;

public interface UsuarioService extends CrudService<Usuario, Integer> {

	Authentication autenticaUsuario(Authentication autenticacao);

	void mudarSenhaAtual(MudarSenhaAtualDto mudarSenhaAtualDto);

	Usuario restoreByLogin(String nomeUsuario);

}
