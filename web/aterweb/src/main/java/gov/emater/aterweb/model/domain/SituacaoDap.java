package gov.emater.aterweb.model.domain;

public enum SituacaoDap {
	/*
	 * Situa��o da Dap:
	 * 
	 * S - tem dap
	 * N - nao tem dap
	 * X - nao dapeavel
	 */

	S("Possui"), N("N�o Possui"), NDP("N�o Dape�vel");

	private String descricao;

	private SituacaoDap(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}