package gov.emater.aterweb.model.domain;

public enum AtividadePessoaFuncao {
	D("Demandante"), E("Executor"), ER("Executor Responsável");
	private String descricao;

	private AtividadePessoaFuncao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
