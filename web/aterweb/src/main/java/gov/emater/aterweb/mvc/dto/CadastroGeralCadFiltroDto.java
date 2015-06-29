package gov.emater.aterweb.mvc.dto;

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

public class CadastroGeralCadFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private Integer numeroPagina;

	private String produtorCpf;

	private String produtorEscolaridade;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar produtorNascimentoFin;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar produtorNascimentoIni;

	private String produtorNome;

	private String produtorNumeroDap;

	private String produtorNumeroInscricaoSefDf;

	private String produtorSexo;

	private String produtorStatus;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal propriedadeAreaPropriedadeFin;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal propriedadeAreaPropriedadeIni;

	private String propriedadeArrendatario;

	private String propriedadeCidade;

	private String propriedadeCnpj;

	private String propriedadeNome;

	private String propriedadeSituacaoFundiaria;

	private String propriedadeStatus;

	private String propriedadeTemApp;

	private String propriedadeTemPlanoUtilizacao;

	private String propriedadeTemReservaLegal;

	public CadastroGeralCadFiltroDto() {

	}

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public String getProdutorCpf() {
		return produtorCpf;
	}

	public String getProdutorEscolaridade() {
		return produtorEscolaridade;
	}

	public Calendar getProdutorNascimentoFin() {
		return produtorNascimentoFin;
	}

	public Calendar getProdutorNascimentoIni() {
		return produtorNascimentoIni;
	}

	public String getProdutorNome() {
		return produtorNome;
	}

	public String getProdutorNumeroDap() {
		return produtorNumeroDap;
	}

	public String getProdutorNumeroInscricaoSefDf() {
		return produtorNumeroInscricaoSefDf;
	}

	public String getProdutorSexo() {
		return produtorSexo;
	}

	public String getProdutorStatus() {
		return produtorStatus;
	}

	public BigDecimal getPropriedadeAreaPropriedadeFin() {
		return propriedadeAreaPropriedadeFin;
	}

	public BigDecimal getPropriedadeAreaPropriedadeIni() {
		return propriedadeAreaPropriedadeIni;
	}

	public String getPropriedadeArrendatario() {
		return propriedadeArrendatario;
	}

	public String getPropriedadeCidade() {
		return propriedadeCidade;
	}

	public String getPropriedadeCnpj() {
		return propriedadeCnpj;
	}

	public String getPropriedadeNome() {
		return propriedadeNome;
	}

	public String getPropriedadeSituacaoFundiaria() {
		return propriedadeSituacaoFundiaria;
	}

	public String getPropriedadeStatus() {
		return propriedadeStatus;
	}

	public String getPropriedadeTemApp() {
		return propriedadeTemApp;
	}

	public String getPropriedadeTemPlanoUtilizacao() {
		return propriedadeTemPlanoUtilizacao;
	}

	public String getPropriedadeTemReservaLegal() {
		return propriedadeTemReservaLegal;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public void setProdutorCpf(String produtorCpf) {
		this.produtorCpf = produtorCpf;
	}

	public void setProdutorEscolaridade(String produtorEscolaridade) {
		this.produtorEscolaridade = produtorEscolaridade;
	}

	public void setProdutorNascimentoFin(Calendar produtorNascimentoFin) {
		this.produtorNascimentoFin = produtorNascimentoFin;
	}

	public void setProdutorNascimentoIni(Calendar produtorNascimentoIni) {
		this.produtorNascimentoIni = produtorNascimentoIni;
	}

	public void setProdutorNome(String produtorNome) {
		this.produtorNome = produtorNome;
	}

	public void setProdutorNumeroDap(String produtorNumeroDap) {
		this.produtorNumeroDap = produtorNumeroDap;
	}

	public void setProdutorNumeroInscricaoSefDf(String produtorNumeroInscricaoSefDf) {
		this.produtorNumeroInscricaoSefDf = produtorNumeroInscricaoSefDf;
	}

	public void setProdutorSexo(String produtorSexo) {
		this.produtorSexo = produtorSexo;
	}

	public void setProdutorStatus(String produtorStatus) {
		this.produtorStatus = produtorStatus;
	}

	public void setPropriedadeAreaPropriedadeFin(BigDecimal propriedadeAreaPropriedadeFin) {
		this.propriedadeAreaPropriedadeFin = propriedadeAreaPropriedadeFin;
	}

	public void setPropriedadeAreaPropriedadeIni(BigDecimal propriedadeAreaPropriedadeIni) {
		this.propriedadeAreaPropriedadeIni = propriedadeAreaPropriedadeIni;
	}

	public void setPropriedadeArrendatario(String propriedadeArrendatario) {
		this.propriedadeArrendatario = propriedadeArrendatario;
	}

	public void setPropriedadeCidade(String propriedadeCidade) {
		this.propriedadeCidade = propriedadeCidade;
	}

	public void setPropriedadeCnpj(String propriedadeCnpj) {
		this.propriedadeCnpj = propriedadeCnpj;
	}

	public void setPropriedadeNome(String propriedadeNome) {
		this.propriedadeNome = propriedadeNome;
	}

	public void setPropriedadeSituacaoFundiaria(String propriedadeSituacaoFundiaria) {
		this.propriedadeSituacaoFundiaria = propriedadeSituacaoFundiaria;
	}

	public void setPropriedadeStatus(String propriedadeStatus) {
		this.propriedadeStatus = propriedadeStatus;
	}

	public void setPropriedadeTemApp(String propriedadeTemApp) {
		this.propriedadeTemApp = propriedadeTemApp;
	}

	public void setPropriedadeTemPlanoUtilizacao(String propriedadeTemPlanoUtilizacao) {
		this.propriedadeTemPlanoUtilizacao = propriedadeTemPlanoUtilizacao;
	}

	public void setPropriedadeTemReservaLegal(String propriedadeTemReservaLegal) {
		this.propriedadeTemReservaLegal = propriedadeTemReservaLegal;
	}

}