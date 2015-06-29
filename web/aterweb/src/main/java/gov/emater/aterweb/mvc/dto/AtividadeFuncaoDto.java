package gov.emater.aterweb.mvc.dto;

public enum AtividadeFuncaoDto {

	A("Apoiou", 7), D("Demandou", 4), E("Executou", 5), ER("Foi responsável", 6), N("Não se envolveu", 1), Q("Qualquer", 2), R("Abriu", 3);

	private String descricao;

	private Integer ordem;

	private AtividadeFuncaoDto(String descricao, Integer ordem) {
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
