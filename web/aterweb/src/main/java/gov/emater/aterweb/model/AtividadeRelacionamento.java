package gov.emater.aterweb.model;

import gov.emater.aterweb.dao._ChavePrimaria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = "atividade_relacionamento", schema = EntidadeBase.ATIVIDADE_SCHEMA)
public class AtividadeRelacionamento extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "atividade_id")
	@Valid
	private Atividade atividade;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "principal_atividade_id")
	@Valid
	private Atividade principalAtividade;

	public Atividade getAtividade() {
		return atividade;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public Atividade getPrincipalAtividade() {
		return principalAtividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setPrincipalAtividade(Atividade principalAtividade) {
		this.principalAtividade = principalAtividade;
	}

}