package gov.emater.aterweb.model;

import gov.emater.aterweb.dao._ChavePrimaria;
import gov.emater.aterweb.mvc.config.JsonFormatarBigDecimal;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The persistent class for the exploracao database table.
 * 
 */
@Entity
@Table(name = "exploracao", schema = EntidadeBase.ATER_SCHEMA)
public class Exploracao extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal area;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "pessoa_meio_contato_id")
	private PessoaMeioContato pessoaMeioContato;

	private String regime;

	public Exploracao() {
	}

	public Exploracao(Integer id) {
		super(id);
	}

	public Exploracao(PessoaMeioContato pessoaMeioContato) {
		setPessoaMeioContato(pessoaMeioContato);
	}

	public BigDecimal getArea() {
		return area;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public PessoaMeioContato getPessoaMeioContato() {
		return pessoaMeioContato;
	}

	public String getRegime() {
		return regime;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setPessoaMeioContato(PessoaMeioContato pessoaMeioContato) {
		this.pessoaMeioContato = pessoaMeioContato;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}
}