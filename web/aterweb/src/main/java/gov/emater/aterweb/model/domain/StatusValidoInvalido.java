package gov.emater.aterweb.model.domain;

public enum StatusValidoInvalido {

	I("Inválido"), V("Válido");

	private String descricao;

	private StatusValidoInvalido(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}