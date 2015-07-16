package br.gov.emater.df.cadastro.util;

public class CNP {

	private static final int[] cnpjPeso = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int[] cpfPeso = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int TAMANHO_DIGITO_VERIFICADOR = 2;

	private static int calcularDigito(String numero, int[] peso) {
		char digito, digitoInicial = '\0';
		boolean numerosRepetidos = true;
		int soma = 0;
		for (int indice = numero.length() - 1; indice >= 0; indice--) {
			digito = numero.charAt(indice);
			if (digitoInicial == '\0') {
				digitoInicial = digito;
			} else if (numerosRepetidos && digito != digitoInicial) {
				numerosRepetidos = false;
			}
			soma += Character.getNumericValue(digito) * peso[peso.length - numero.length() + indice];
		}
		// se houver mais de um digito e todos forem repetidos
		if (numero.length() > 1 && numerosRepetidos) {
			// invalidar o calculo
			throw new IllegalArgumentException("os números são repetidos");
		}
		// calcular o dv
		soma = 11 - soma % 11;
		// ajustar resultado
		return soma > 9 ? 0 : soma;
	}

	public static boolean isCnpjValido(String numero) {
		return validarDigitos(numero, 12, cnpjPeso);
	}

	public static boolean isCpfValido(String numero) {
		return validarDigitos(numero, 9, cpfPeso);
	}

	public static void main(String[] args) {
		System.out.printf("CPF Valido:%s \n", CNP.isCpfValido("01590889924"));
		System.out.printf("CPF Valido:%s \n", CNP.isCpfValido("1111111A111"));

		System.out.printf("CPF Valido:%s \n", CNP.isCpfValido("01115375502"));
		System.out.printf("CNPJ Valido:%s \n", CNP.isCnpjValido("13642634756318"));
	}

	private static boolean validarDigitos(String numero, int tamanho, int peso[]) {
		if ((numero == null) || (numero.length() != tamanho + TAMANHO_DIGITO_VERIFICADOR)) {
			return false;
		}
		try {
			Integer digito1 = calcularDigito(numero.substring(0, tamanho), peso);
			Integer digito2 = calcularDigito(numero.substring(0, tamanho) + digito1, peso);
			return numero.equals(String.format("%s%s%s", numero.substring(0, tamanho), digito1.toString(), digito2.toString()));
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}