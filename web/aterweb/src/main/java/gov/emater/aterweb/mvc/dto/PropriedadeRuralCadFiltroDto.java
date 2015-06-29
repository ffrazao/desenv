package gov.emater.aterweb.mvc.dto;

import gov.emater.aterweb.model.PessoaGrupoComunidadeVi;
import gov.emater.aterweb.model.SistemaProducao;
import gov.emater.aterweb.model.domain.Confirmacao;
import gov.emater.aterweb.model.domain.SituacaoFundiaria;

public class PropriedadeRuralCadFiltroDto implements FiltroDto {

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

	private PessoaGrupoComunidadeVi comunidade;

	private String nome;

	private Confirmacao outorga;

	private SistemaProducao sistemaPredominante;

	private SituacaoFundiaria situacaoFundiaria;

	public PropriedadeRuralCadFiltroDto() {

	}

	public PessoaGrupoComunidadeVi getComunidade() {
		return comunidade;
	}

	public String getNome() {
		return nome;
	}

	public Confirmacao getOutorga() {
		return outorga;
	}

	public SistemaProducao getSistemaPredominante() {
		return sistemaPredominante;
	}

	public SituacaoFundiaria getSituacaoFundiaria() {
		return situacaoFundiaria;
	}

	public void setComunidade(PessoaGrupoComunidadeVi comunidade) {
		this.comunidade = comunidade;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setOutorga(Confirmacao outorga) {
		this.outorga = outorga;
	}

	public void setSistemaPredominante(SistemaProducao sistemaPredominante) {
		this.sistemaPredominante = sistemaPredominante;
	}

	public void setSituacaoFundiaria(SituacaoFundiaria situacaoFundiaria) {
		this.situacaoFundiaria = situacaoFundiaria;
	}

}
