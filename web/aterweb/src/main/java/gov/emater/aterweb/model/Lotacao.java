package gov.emater.aterweb.model;

import gov.emater.aterweb.model.domain.GestaoTipo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "lotacao", schema = EntidadeBase.FUNCIONAL_SCHEMA)
@PrimaryKeyJoinColumn(name = "id")
public class Lotacao extends Relacionamento {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "emprego_id")
	private Emprego emprego;

	@Column(name = "gestor")
	@Enumerated(EnumType.STRING)
	private GestaoTipo gestor;

	public Lotacao() {
	}

	public Emprego getEmprego() {
		return emprego;
	}

	public GestaoTipo getGestor() {
		return gestor;
	}

	public void setEmprego(Emprego emprego) {
		this.emprego = emprego;
	}

	public void setGestor(GestaoTipo gestor) {
		this.gestor = gestor;
	}

}