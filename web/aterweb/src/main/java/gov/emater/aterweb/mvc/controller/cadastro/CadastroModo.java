package gov.emater.aterweb.mvc.controller.cadastro;

public enum CadastroModo {
	
	N("Normal"), P("Popup");
	
	private String descricao;

	private CadastroModo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
	
}