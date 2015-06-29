package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoFloresTipo {

	FLORES("Flores"), FRUTIFERAS("Frutíferas"), GRANDES_CULTURAS("Grandes Culturas"), HORTALICAS("Hortaliças"), ORNAMENTAIS("Ornamentais"), SILVICULTURA("Silvicultura");

	private String descricao;

	private PerspectivaProducaoFloresTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}