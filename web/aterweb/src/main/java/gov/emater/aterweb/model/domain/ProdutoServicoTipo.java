package gov.emater.aterweb.model.domain;

public enum ProdutoServicoTipo {

	P("Produto"), S("Serviço");

	private String descricao;

	private ProdutoServicoTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}