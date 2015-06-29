package gov.emater.aterweb.model.domain;

public enum CamOrgao {

	A("Aeron�utica"), D("Minist�rio da Defesa"), E("Ex�rcito"), M("Marinha");

	private String descricao;

	private CamOrgao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}