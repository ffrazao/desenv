package br.gov.df.emater.dto.filtro;

public class TesteFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private Long idFim;

	private Long idIni;

	private String nome;

	private int paginaNumero = PAGINA_NUMERO_PADRAO;

	private int paginaTamanho = PAGINA_TAMANHO_PADRAO;

	private Long totalRegistro = 0L;

	public Long getIdFim() {
		return idFim;
	}

	public Long getIdIni() {
		return idIni;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public int getPaginaNumero() {
		return this.paginaNumero;
	}

	@Override
	public int getPaginaTamanho() {
		return this.paginaTamanho;
	}

	public Long getTotalRegistro() {
		return totalRegistro;
	}

	public void setIdFim(Long idFim) {
		this.idFim = idFim;
	}

	public void setIdIni(Long idIni) {
		this.idIni = idIni;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public void setPaginaNumero(int paginaNumero) {
		this.paginaNumero = paginaNumero < 1 ? 1 : paginaNumero;
	}

	@Override
	public void setPaginaTamanho(int paginaTamanho) {
		this.paginaTamanho = paginaTamanho < 1 ? 1 : paginaTamanho;
	}

	public void setTotalRegistro(Long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

}
