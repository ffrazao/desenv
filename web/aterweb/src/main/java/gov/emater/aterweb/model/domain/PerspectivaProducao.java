package gov.emater.aterweb.model.domain;

public enum PerspectivaProducao {

	AGRICOLA("Agrícola"), AGROINDUSTRIA("Agro-indústria"), ANIMAL("Animal"), CORTE("Corte"), FLORES("Flores"), LEITE("Leite"), POSTURA("Postura"), SERVICO("Serviço"), TURISMO("Turismo");

	private String descricao;

	private PerspectivaProducao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
