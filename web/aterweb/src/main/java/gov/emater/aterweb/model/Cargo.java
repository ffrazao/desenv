package gov.emater.aterweb.model;

import gov.emater.aterweb.dao._ChavePrimaria;
import gov.emater.aterweb.model.domain.TipoCargo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@Table(name = "cargo", schema = EntidadeBase.FUNCIONAL_SCHEMA)
public class Cargo extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "codigo_cbo")
	private String codigoCbo;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoCargo tipo;

	public Cargo() {
	}

	public String getCodigoCbo() {
		return codigoCbo;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public TipoCargo getTipo() {
		return tipo;
	}

	public void setCodigoCbo(String codigoCbo) {
		this.codigoCbo = codigoCbo;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(TipoCargo tipo) {
		this.tipo = tipo;
	}

}