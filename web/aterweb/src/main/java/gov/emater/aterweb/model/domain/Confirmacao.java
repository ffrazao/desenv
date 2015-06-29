package gov.emater.aterweb.model.domain;

public enum Confirmacao {

	N("N�o"), S("Sim");

	private String descricao;

	private Confirmacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}