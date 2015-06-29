package gov.emater.aterweb.model.domain;

public enum PerspectivaProducaoServicoProjeto {

	AGROINDUSTRIA("Agro-Indústria"), ARTESANATO("Artesanato"), PROC_ARTESANAL("Processamento Artesanal");

	private String descricao;

	private PerspectivaProducaoServicoProjeto(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
