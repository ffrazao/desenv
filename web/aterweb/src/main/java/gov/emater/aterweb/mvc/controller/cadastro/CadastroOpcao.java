package gov.emater.aterweb.mvc.controller.cadastro;

public enum CadastroOpcao {
	
	FI("Filtro"), LI("Listagem"), FO("Formulario");
	
	private String descricao;

	private CadastroOpcao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
	
}
