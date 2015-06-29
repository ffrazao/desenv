package gov.emater.aterweb.model.domain;

public enum OpcaoRespostaTipo {

	M("Objetiva - Multi Valor"), S("Subjetiva"), U("Objetiva - �nico Valor"), I("Informa��o"), A("Assinatura"), Q("Quebra de P�gina");

	private String descricao;

	private OpcaoRespostaTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}