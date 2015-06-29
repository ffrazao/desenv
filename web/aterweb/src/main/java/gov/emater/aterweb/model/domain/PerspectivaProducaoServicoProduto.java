package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoServicoProduto {

	EMBUTIDO("Embutidos"), HORTALICA("Hortaliças Minimamente Processadas"), PANIFICADO("Panificados"), POLPA("Polpas e Sucos");

	private String descricao;

	private PerspectivaProducaoServicoProduto(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
