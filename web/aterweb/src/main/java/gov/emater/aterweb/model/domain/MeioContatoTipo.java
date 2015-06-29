package gov.emater.aterweb.model.domain;

public enum MeioContatoTipo {

	EMA("Email"), END("Endereço"), TEL("Telefônico");

	private String descricao;

	private MeioContatoTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
