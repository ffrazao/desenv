package gov.emater.aterweb.model.domain;

public enum AtividadeRestricaoTempo {

	ER("Quando esta terminar a referida ser� iniciada"),
	RE("Quando a referida terminar esta ser� iniciada"),
	II("Iniciam juntas"),
	TT("Terminam juntas"),
	IT("Iniciam e terminam juntas");

	private String descricao;

	private AtividadeRestricaoTempo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}