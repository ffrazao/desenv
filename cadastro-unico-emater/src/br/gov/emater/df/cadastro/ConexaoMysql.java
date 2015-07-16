package br.gov.emater.df.cadastro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMysql {

	private static ConexaoMysql instance;

	static {
		try {
			Class.forName(Constantes.MYSQL_DRIVER);
		} catch (java.lang.ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static ConexaoMysql getInstance() {
		if (instance == null) {
			instance = new ConexaoMysql();
		}
		return instance;
	}

	private ConexaoMysql() {
	}

	public Connection createConnection() {
		try {
			Connection result = DriverManager.getConnection(Constantes.MYSQL_STRING_CONEXAO, Constantes.MYSQL_USUARIO, Constantes.MYSQL_SENHA);
			result.setAutoCommit(false);
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
