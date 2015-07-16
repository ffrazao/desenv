package br.gov.emater.df.cadastro.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFunc {

	private static StringFunc instance;

	public static StringFunc getInstance() {
		if (instance == null) {
			instance = new StringFunc();
		}
		return instance;
	}

	// private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");

	// private SimpleDateFormat sdfDataHora = new
	// SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat sdfDataHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DecimalFormat decimalFormat;

	private StringFunc() {
		// Create a DecimalFormat that fits your requirements
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
	}

	public String criticaCNPJ(String temp) {
		temp = strZero(temp, 14);
		if (CNP.isCnpjValido(temp)) {
			return formatCNPJ(temp);
		}
		return null;
	}

	public String criticaCPF(String temp) {
		temp = strZero(temp, 11);
		if (CNP.isCpfValido(temp)) {
			return formatCPF(temp);
		}
		return null;
	}

	public String formatCNPJ(String temp) {
		Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
		Matcher matcher = pattern.matcher(temp);
		if (matcher.matches())
			temp = matcher.replaceAll("$1.$2.$3/$4-$5");
		return temp;
	}

	public String formatCPF(String cpf) {
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
		Matcher matcher = pattern.matcher(cpf);
		if (matcher.matches())
			cpf = matcher.replaceAll("$1.$2.$3-$4");
		return cpf;
	}

	public String limpaNumero(String numero) {
		if (numero == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numero.length(); i++) {
			if (Character.isDigit(numero.charAt(i))) {
				sb.append(numero.charAt(i));
			}
		}
		return sb.toString();
	}

	public java.sql.Date strToData(String data) throws ParseException {
		if (data == null || data.trim().length() == 0)
			return null;
		return new java.sql.Date(sdfData.parse(data).getTime());
	}

	public java.sql.Timestamp strToDataHora(String data) throws ParseException {
		if (data == null || data.trim().length() == 0)
			return null;
		return new java.sql.Timestamp(sdfDataHora.parse(data).getTime());
	}

	public String strZero(String numero, int size) {
		StringBuilder zeros = new StringBuilder();
		for (int i = 0; i < size; i++) {
			zeros.append("0");
		}
		zeros.append(numero);
		numero = zeros.toString();
		numero = numero.substring(numero.length() - size, numero.length());
		return numero;
	}

	public BigDecimal stroToBigDecimal(String numero) throws ParseException {
		// parse the string
		if (numero == null || numero.trim().length() == 0) {
			return null;
		}
		BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(numero);
		return bigDecimal;
	}
}
