package gov.emater.aterweb.model.domain;

public enum SituacaoDap {
	/*
	 * Situação da Dap:
	 * 
	 * S - tem dap
	 * N - nao tem dap
	 * X - nao dapeavel
	 */

	S("Possui"), N("Não Possui"), NDP("Não Dapeável");

	private String descricao;

	private SituacaoDap(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}