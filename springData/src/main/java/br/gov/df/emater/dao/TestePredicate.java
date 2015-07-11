package br.gov.df.emater.dao;

import br.gov.df.emater.entidade.teste.QTeste;

import com.mysema.query.types.expr.BooleanExpression;

public class TestePredicate {
	private static QTeste qTeste = QTeste.teste;
	
	public static BooleanExpression idEntre(Long ini, Long fim) {
		return qTeste.id.between(ini, fim);
	}

	public static BooleanExpression porNome(String nome) {
		return qTeste.nome.like(nome);
	}

}
