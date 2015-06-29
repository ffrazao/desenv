package gov.emater.aterweb.model.domain;

public enum SituacaoLotacao {
	/*
	 * Situação da lotação:
	 * 
	 * T - Transitória. Saindo de um indo para outra E - Efetiva. Tornando uma
	 * Transitória em efetiva. P - Provisória. Atividades provisórias em outras
	 * unidades da instituição.
	 */

	E("Efetiva"), P("Provisória"), T("Transitória");

	private String descricao;

	private SituacaoLotacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}