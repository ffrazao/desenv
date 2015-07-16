package br.gov.emater.df.cadastro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFirebird {

	private static ConexaoFirebird instance;

	static {
		try {
			Class.forName(Constantes.FIREBIRD_DRIVER);
		} catch (java.lang.ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static ConexaoFirebird getInstance() {
		if (instance == null) {
			instance = new ConexaoFirebird();
		}
		return instance;
	}

	private ConexaoFirebird() {
	}

	public Connection createConnection(String base) {
		try {
			Connection result = DriverManager.getConnection(String.format(Constantes.FIREBIRD_STRING_CONEXAO, base), Constantes.FIREBIRD_USUARIO, Constantes.FIREBIRD_SENHA);
			result.setAutoCommit(false);
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
