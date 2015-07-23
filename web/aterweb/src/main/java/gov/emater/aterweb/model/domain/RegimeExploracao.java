package gov.emater.aterweb.model.domain;

public enum RegimeExploracao {

	PR("Próprio", 1), PA("Parceria", 2);

	private String descricao;

	private Integer ordem;

	private RegimeExploracao(String descricao, Integer ordem) {
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