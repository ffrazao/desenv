package gov.emater.aterweb.model.domain;

public enum AgendamentoTipoRepeticao {

	C("Contagem de Vezes"), 
	D("Data Término do Agendamento");

	private String descricao;

	private AgendamentoTipoRepeticao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}