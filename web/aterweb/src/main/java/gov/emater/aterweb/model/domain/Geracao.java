package gov.emater.aterweb.model.domain;

public enum Geracao {

	C("Crian�a"), J("Jovem"), A("Adulto"), I("Idoso");

	private String descricao;

	private Geracao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}