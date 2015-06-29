package gov.emater.aterweb.model.domain;

public enum DiaMes {

	_01("Primeiro"), _02("Segundo"), _03("Terceiro"), _04("Quarto"), _05("Quinto"), _06("Sexto"), _07("Sétimo"), _08("Oitavo"), _09("Nono"), _10("Décimo"), _11("Décimo Primeiro"), _12("Décimo Segundo"), _13("Décimo Terceiro"), _14("Décimo Quarto"), _15("Décimo Quinto"), _16(
			"Décimo Sexto"), _17("Décimo Sétimo"), _18("Décimo Oitavo"), _19("Décimo Nono"), _20("Vigésimo"), _21("Vigésimo Primeiro"), _22("Vigésimo Segundo"), _23("Vigésimo Terceiro"), _24("Vigésimo Quarto"), _25("Vigésimo Quinto"), _26("Vigésimo Sexto"), _27("Vigésimo Sétimo"), _28(
			"Vigésimo Oitavo"), _29("Vigésimo Nono"), _30("Trigésimo"), _31("Trigésimo Primeiro");

	private String descricao;

	private DiaMes(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}