package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoServicoCondicao {

	FORMAL("Formal"), INFORMAL("Informal");

	private String descricao;

	private PerspectivaProducaoServicoCondicao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}