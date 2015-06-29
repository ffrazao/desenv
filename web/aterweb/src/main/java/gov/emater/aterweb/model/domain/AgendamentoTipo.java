package gov.emater.aterweb.model.domain;

public enum AgendamentoTipo {

	_0("Não repetir, é um evento único"), 
	_1("Diáriamente"), 
	_2("Todos os dias úteis (Segunda à Sexta-Feira)"), 
	_3("Semanalmente (a cada {0})"), // usar: DiaSemana.value(?)
	_4("A cada 2 semanas ({0})"), // usar: DiaSemana.value(?)
	_5("Mensalmente (cada último(a) {0})"), // usar: DiaSemana.value(?)
	_6("Mensalmente (todo dia {0})"), // usar: DiaMes.value(?)
	_7("Anualmente (todo dia {0} de {1})"); // usar: DiaMes.value(?) e Mes.value(?)

	private String descricao;

	private AgendamentoTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}