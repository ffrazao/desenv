package br.gov.emater.df.cadastro;

public class Constantes {

	public static final String BASE_DESTINO = "EMATER";

	public static final String[] BASES_ORIGEM = { "ASBRA", "ASCRI", "ASFOR", "ASPBE", "ASPIP", "ASSOB", "ELALG", "ELBRA", "ELBSB", "ELCEI", "ELGAM", "ELJAR", "ELPAD", "ELPAR", "ELPIP", "ELPLA", "ELRIP", "ELRSS", "ELSOB", "ELTAB", "ELTAQ", "ELVAR" };

	public static final String FIREBIRD_DRIVER = "org.firebirdsql.jdbc.FBDriver";

	public static final String FIREBIRD_SENHA = "masterkey";

	public static final String FIREBIRD_STRING_CONEXAO = "jdbc:firebirdsql://localhost/C:/SATERBD/%s/DBSATER.fdb?encoding=ISO8859_1";
//	public static final String FIREBIRD_STRING_CONEXAO = "jdbc:firebirdsql://localhost/C:/SATERBD/%s/DBSATER.fdb?encoding=DOS437";
//	public static final String FIREBIRD_STRING_CONEXAO = "jdbc:firebirdsql://localhost/C:/SATERBD/%s/DBSATER.fdb?encoding=UTF8";

	public static final String FIREBIRD_USUARIO = "sysdba";

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public static final String MYSQL_SENHA = "emater";

	public static final String MYSQL_STRING_CONEXAO = "jdbc:mysql://localhost:3306/unificada?autoReconnect=true";

	public static final String MYSQL_USUARIO = "emater";

	public static final String SQL_DELETE = "DELETE FROM %s";

	public static final String SQL_SELECT = "SELECT * FROM %s";

	public static final String[] TABELA_ESPECIAL = { "PAT_ACAO", "PAT_METODO", "PAT_TEMA", "PAT_META" };

}
