package gov.emater.aterweb.mvc.dto;

public class ModuloCadFiltroDto implements FiltroDto {

	private Integer numeroPagina;

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	private static final long serialVersionUID = 1L;

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
