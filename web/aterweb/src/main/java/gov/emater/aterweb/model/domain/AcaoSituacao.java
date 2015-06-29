package gov.emater.aterweb.model.domain;

public enum AcaoSituacao {

	P("Planejada mas nao iniciada"), E("Em execução"), C("Cancelada"), S("Suspensa ou aguardando");

	private String descricao;

	private AcaoSituacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}