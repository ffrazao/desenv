package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoSistema {

	CONVENCIONAL("Convencional"), ORGANICO_CERT("Org�nico Certificado");

	private String descricao;

	private PerspectivaProducaoSistema(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}