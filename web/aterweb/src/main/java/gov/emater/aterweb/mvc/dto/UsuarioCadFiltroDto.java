package gov.emater.aterweb.mvc.dto;

import gov.emater.aterweb.model.domain.UsuarioStatusConta;

public class UsuarioCadFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private String nome;

	private String nomeUsuario;

	private Integer numeroPagina;

	private UsuarioStatusConta usuarioStatusConta;

	public String getNome() {
		return nome;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public UsuarioStatusConta getUsuarioStatusConta() {
		return usuarioStatusConta;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public void setUsuarioStatusConta(UsuarioStatusConta usuarioStatusConta) {
		this.usuarioStatusConta = usuarioStatusConta;
	}

}