package gov.emater.aterweb.model;

import gov.emater.aterweb.dao._ChavePrimaria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assunto_acao_transversal", schema = EntidadeBase.ATIVIDADE_SCHEMA)
public class AssuntoAcaoTransversal extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "assunto_acao_id")
	private AssuntoAcao assuntoAcao;

	@ManyToOne
	@JoinColumn(name = "assunto_acao_transversal_id")
	private AssuntoAcaoTransversal assuntoAcaoTransversal;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public AssuntoAcao getAssuntoAcao() {
		return assuntoAcao;
	}

	public AssuntoAcaoTransversal getAssuntoAcaoTransversal() {
		return assuntoAcaoTransversal;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setAssuntoAcao(AssuntoAcao assuntoAcao) {
		this.assuntoAcao = assuntoAcao;
	}

	public void setAssuntoAcaoTransversal(AssuntoAcaoTransversal assuntoAcaoTransversal) {
		this.assuntoAcaoTransversal = assuntoAcaoTransversal;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
