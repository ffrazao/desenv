package gov.emater.aterweb.model.domain;

public enum EstadoCivil {

	C("Casado"), D("Desquitado"), P("Separado"), S("Solteiro"), U("Uni�o Est�vel"), V("Vi�vo");

	private String descricao;

	private EstadoCivil(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
