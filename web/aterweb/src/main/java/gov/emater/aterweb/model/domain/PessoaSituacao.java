package gov.emater.aterweb.model.domain;

public enum PessoaSituacao {

	A("Ativo", 1), F("Inativo por Falecimento", 3), O("Inativo por Outro Motivo", 4), U("Inativo por falta de Uso", 2);

	private String descricao;

	private Integer ordem;

	private PessoaSituacao(String descricao, Integer ordem) {
		this.descricao = descricao;
		this.ordem = ordem;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}