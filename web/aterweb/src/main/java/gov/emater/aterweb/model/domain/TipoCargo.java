package gov.emater.aterweb.model.domain;

public enum TipoCargo {

	F("Família"), O("Ocupação"), S("Sinônimo");

	private String descricao;

	private TipoCargo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}