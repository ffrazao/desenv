package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoAnimalExploracao {

	EXTENSIVO("Extensivo"), INTENSIVO("Intensivo"), SEMI_INTENSIVO("Semi-Intensivo");

	private String descricao;

	private PerspectivaProducaoAnimalExploracao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}