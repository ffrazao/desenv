package gov.emater.aterweb.model.domain;

public enum OpcaoRespostaTipo {

	M("Objetiva - Multi Valor"), S("Subjetiva"), U("Objetiva - Único Valor"), I("Informação"), A("Assinatura"), Q("Quebra de Página");

	private String descricao;

	private OpcaoRespostaTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}