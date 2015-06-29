package gov.emater.aterweb.mvc.dto;

import gov.emater.aterweb.model.PessoaGrupoBaciaHidrograficaVi;
import gov.emater.aterweb.model.PessoaGrupoCidadeVi;
import gov.emater.aterweb.model.PessoaGrupoComunidadeVi;
import gov.emater.aterweb.model.PessoaGrupoEstadoVi;
import gov.emater.aterweb.model.PessoaGrupoMunicipioVi;
import gov.emater.aterweb.model.PessoaGrupoPaisVi;
import gov.emater.aterweb.model.ProdutoServico;
import gov.emater.aterweb.model.domain.PerspectivaProducao;
import gov.emater.aterweb.model.domain.PerspectivaProducaoAnimalExploracao;
import gov.emater.aterweb.model.domain.PerspectivaProducaoFloresProtecaoEpocaForma;
import gov.emater.aterweb.model.domain.PerspectivaProducaoFloresTipo;
import gov.emater.aterweb.model.domain.PerspectivaProducaoFloresUsoDagua;
import gov.emater.aterweb.model.domain.PerspectivaProducaoServicoCondicao;
import gov.emater.aterweb.model.domain.PerspectivaProducaoServicoProduto;
import gov.emater.aterweb.model.domain.PerspectivaProducaoServicoProjeto;
import gov.emater.aterweb.model.domain.PerspectivaProducaoSistema;
import gov.emater.aterweb.mvc.config.JsonDeserializerData;
import gov.emater.aterweb.mvc.config.JsonFormatarBigDecimal;
import gov.emater.aterweb.mvc.config.JsonSerializerData;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class IndiceProducaoCadFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private PessoaGrupoBaciaHidrograficaVi pessoaGrupoBaciaHidrografica;

	private PessoaGrupoCidadeVi pessoaGrupoCidade;

	private PessoaGrupoComunidadeVi pessoaGrupoComunidade;

	private PessoaGrupoEstadoVi pessoaGrupoEstado;

	private PessoaGrupoMunicipioVi pessoaGrupoMunicipio;

	private PessoaGrupoPaisVi pessoaGrupoPais;

	private Integer numeroPagina;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar producaoData;

	private PerspectivaProducao producaoPerspectiva;

	private PerspectivaProducaoAnimalExploracao producaoPerspectivaAnimalExploracao;

	private PerspectivaProducaoSistema producaoPerspectivaAnimalSistema;

	private PerspectivaProducaoFloresProtecaoEpocaForma producaoPerspectivaFloresProtecaoEpocaForma;

	private PerspectivaProducaoSistema producaoPerspectivaFloresSistema;

	private PerspectivaProducaoFloresTipo producaoPerspectivaFloresTipo;

	private PerspectivaProducaoFloresUsoDagua producaoPerspectivaFloresUsoDagua;

	private PerspectivaProducaoServicoCondicao producaoPerspectivaServicoCondicao;

	private PerspectivaProducaoServicoProduto producaoPerspectivaServicoProduto;

	private PerspectivaProducaoServicoProjeto producaoPerspectivaServicoProjeto;

	private ProdutoServico producaoProdutoServico;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal producaoVolumeFin;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal producaoVolumeIni;

	private String produtorCpfCnpj;

	private String produtorNome;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal propriedadeAreaPropriedadeFin;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal propriedadeAreaPropriedadeIni;

	private String propriedadeNome;

	public IndiceProducaoCadFiltroDto() {

	}

	public PessoaGrupoBaciaHidrograficaVi getPessoaGrupoBaciaHidrografica() {
		return pessoaGrupoBaciaHidrografica;
	}

	public PessoaGrupoCidadeVi getPessoaGrupoCidade() {
		return pessoaGrupoCidade;
	}

	public PessoaGrupoComunidadeVi getPessoaGrupoComunidade() {
		return pessoaGrupoComunidade;
	}

	public PessoaGrupoEstadoVi getPessoaGrupoEstado() {
		return pessoaGrupoEstado;
	}

	public PessoaGrupoMunicipioVi getPessoaGrupoMunicipio() {
		return pessoaGrupoMunicipio;
	}

	public PessoaGrupoPaisVi getPessoaGrupoPais() {
		return pessoaGrupoPais;
	}

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public Calendar getProducaoData() {
		return producaoData;
	}

	public PerspectivaProducao getProducaoPerspectiva() {
		return producaoPerspectiva;
	}

	public PerspectivaProducaoAnimalExploracao getProducaoPerspectivaAnimalExploracao() {
		return producaoPerspectivaAnimalExploracao;
	}

	public PerspectivaProducaoSistema getProducaoPerspectivaAnimalSistema() {
		return producaoPerspectivaAnimalSistema;
	}

	public PerspectivaProducaoFloresProtecaoEpocaForma getProducaoPerspectivaFloresProtecaoEpocaForma() {
		return producaoPerspectivaFloresProtecaoEpocaForma;
	}

	public PerspectivaProducaoSistema getProducaoPerspectivaFloresSistema() {
		return producaoPerspectivaFloresSistema;
	}

	public PerspectivaProducaoFloresTipo getProducaoPerspectivaFloresTipo() {
		return producaoPerspectivaFloresTipo;
	}

	public PerspectivaProducaoFloresUsoDagua getProducaoPerspectivaFloresUsoDagua() {
		return producaoPerspectivaFloresUsoDagua;
	}

	public PerspectivaProducaoServicoCondicao getProducaoPerspectivaServicoCondicao() {
		return producaoPerspectivaServicoCondicao;
	}

	public PerspectivaProducaoServicoProduto getProducaoPerspectivaServicoProduto() {
		return producaoPerspectivaServicoProduto;
	}

	public PerspectivaProducaoServicoProjeto getProducaoPerspectivaServicoProjeto() {
		return producaoPerspectivaServicoProjeto;
	}

	public ProdutoServico getProducaoProdutoServico() {
		return producaoProdutoServico;
	}

	public BigDecimal getProducaoVolumeFin() {
		return producaoVolumeFin;
	}

	public BigDecimal getProducaoVolumeIni() {
		return producaoVolumeIni;
	}

	public String getProdutorCpfCnpj() {
		return produtorCpfCnpj;
	}

	public String getProdutorNome() {
		return produtorNome;
	}

	public BigDecimal getPropriedadeAreaPropriedadeFin() {
		return propriedadeAreaPropriedadeFin;
	}

	public BigDecimal getPropriedadeAreaPropriedadeIni() {
		return propriedadeAreaPropriedadeIni;
	}

	public String getPropriedadeNome() {
		return propriedadeNome;
	}

	public void setPessoaGrupoBaciaHidrografica(PessoaGrupoBaciaHidrograficaVi pessoaGrupoBaciaHidrografica) {
		this.pessoaGrupoBaciaHidrografica = pessoaGrupoBaciaHidrografica;
	}

	public void setPessoaGrupoCidade(PessoaGrupoCidadeVi pessoaGrupoCidade) {
		this.pessoaGrupoCidade = pessoaGrupoCidade;
	}

	public void setPessoaGrupoComunidade(PessoaGrupoComunidadeVi pessoaGrupoComunidade) {
		this.pessoaGrupoComunidade = pessoaGrupoComunidade;
	}

	public void setPessoaGrupoEstado(PessoaGrupoEstadoVi pessoaGrupoEstado) {
		this.pessoaGrupoEstado = pessoaGrupoEstado;
	}

	public void setPessoaGrupoMunicipio(PessoaGrupoMunicipioVi pessoaGrupoMunicipio) {
		this.pessoaGrupoMunicipio = pessoaGrupoMunicipio;
	}

	public void setPessoaGrupoPais(PessoaGrupoPaisVi pessoaGrupoPais) {
		this.pessoaGrupoPais = pessoaGrupoPais;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public void setProducaoData(Calendar producaoData) {
		this.producaoData = producaoData;
	}

	public void setProducaoPerspectiva(PerspectivaProducao producaoPerspectiva) {
		this.producaoPerspectiva = producaoPerspectiva;
	}

	public void setProducaoPerspectivaAnimalExploracao(PerspectivaProducaoAnimalExploracao producaoPerspectivaAnimalExploracao) {
		this.producaoPerspectivaAnimalExploracao = producaoPerspectivaAnimalExploracao;
	}

	public void setProducaoPerspectivaAnimalSistema(PerspectivaProducaoSistema producaoPerspectivaAnimalSistema) {
		this.producaoPerspectivaAnimalSistema = producaoPerspectivaAnimalSistema;
	}

	public void setProducaoPerspectivaFloresProtecaoEpocaForma(PerspectivaProducaoFloresProtecaoEpocaForma producaoPerspectivaFloresProtecaoEpocaForma) {
		this.producaoPerspectivaFloresProtecaoEpocaForma = producaoPerspectivaFloresProtecaoEpocaForma;
	}

	public void setProducaoPerspectivaFloresSistema(PerspectivaProducaoSistema producaoPerspectivaFloresSistema) {
		this.producaoPerspectivaFloresSistema = producaoPerspectivaFloresSistema;
	}

	public void setProducaoPerspectivaFloresTipo(PerspectivaProducaoFloresTipo producaoPerspectivaFloresTipo) {
		this.producaoPerspectivaFloresTipo = producaoPerspectivaFloresTipo;
	}

	public void setProducaoPerspectivaFloresUsoDagua(PerspectivaProducaoFloresUsoDagua producaoPerspectivaFloresUsoDagua) {
		this.producaoPerspectivaFloresUsoDagua = producaoPerspectivaFloresUsoDagua;
	}

	public void setProducaoPerspectivaServicoCondicao(PerspectivaProducaoServicoCondicao producaoPerspectivaServicoCondicao) {
		this.producaoPerspectivaServicoCondicao = producaoPerspectivaServicoCondicao;
	}

	public void setProducaoPerspectivaServicoProduto(PerspectivaProducaoServicoProduto producaoPerspectivaServicoProduto) {
		this.producaoPerspectivaServicoProduto = producaoPerspectivaServicoProduto;
	}

	public void setProducaoPerspectivaServicoProjeto(PerspectivaProducaoServicoProjeto producaoPerspectivaServicoProjeto) {
		this.producaoPerspectivaServicoProjeto = producaoPerspectivaServicoProjeto;
	}

	public void setProducaoProdutoServico(ProdutoServico producaoProdutoServico) {
		this.producaoProdutoServico = producaoProdutoServico;
	}

	public void setProducaoVolumeFin(BigDecimal producaoVolumeFin) {
		this.producaoVolumeFin = producaoVolumeFin;
	}

	public void setProducaoVolumeIni(BigDecimal producaoVolumeIni) {
		this.producaoVolumeIni = producaoVolumeIni;
	}

	public void setProdutorCpfCnpj(String produtorCpfCnpj) {
		this.produtorCpfCnpj = produtorCpfCnpj;
	}

	public void setProdutorNome(String produtorNome) {
		this.produtorNome = produtorNome;
	}

	public void setPropriedadeAreaPropriedadeFin(BigDecimal propriedadeAreaPropriedadeFin) {
		this.propriedadeAreaPropriedadeFin = propriedadeAreaPropriedadeFin;
	}

	public void setPropriedadeAreaPropriedadeIni(BigDecimal propriedadeAreaPropriedadeIni) {
		this.propriedadeAreaPropriedadeIni = propriedadeAreaPropriedadeIni;
	}

	public void setPropriedadeNome(String propriedadeNome) {
		this.propriedadeNome = propriedadeNome;
	}

}