package gov.emater.aterweb.model.domain;

public enum DiaSemana {

	_1("Domingo"), _2("Segunda-Feira"), _3("Terça-Feira"), _4("Quarta-Feira"), _5("Quinta-Feira"), _6("Sexta-Feira"), _7("Sábado");

	private String descricao;

	private DiaSemana(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}