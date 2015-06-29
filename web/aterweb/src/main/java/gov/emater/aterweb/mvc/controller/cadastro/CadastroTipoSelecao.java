package gov.emater.aterweb.mvc.controller.cadastro;

public enum CadastroTipoSelecao {

	S("Simples"), M("Multipla");

	private String descricao;

	private CadastroTipoSelecao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
