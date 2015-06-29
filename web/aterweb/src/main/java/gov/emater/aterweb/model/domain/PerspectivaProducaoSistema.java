package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoSistema {

	CONVENCIONAL("Convencional"), ORGANICO_CERT("Orgânico Certificado");

	private String descricao;

	private PerspectivaProducaoSistema(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}