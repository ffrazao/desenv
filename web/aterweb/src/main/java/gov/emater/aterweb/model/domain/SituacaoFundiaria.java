package gov.emater.aterweb.model.domain;

public enum SituacaoFundiaria {

	C("Concessão de Uso"), E("Escritura Definitiva"), P("Posse");

	private String descricao;

	private SituacaoFundiaria(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}