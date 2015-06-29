package gov.emater.aterweb.model;

import gov.emater.aterweb.model.domain.PerspectivaProducaoAnimalExploracao;
import gov.emater.aterweb.model.domain.PerspectivaProducaoSistema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The persistent class for the perspectiva_producao_agricola database table.
 * 
 */
@Entity
@Table(name = "perspectiva_animal", schema = EntidadeBase.IPA_SCHEMA)
@PrimaryKeyJoinColumn(name = "id")
public class PerspectivaProducaoAnimal extends PrevisaoProducao {

	private static final long serialVersionUID = 1L;

	@Column(name = "exploracao")
	@Enumerated(EnumType.STRING)
	private PerspectivaProducaoAnimalExploracao exploracao;

	@Column(name = "sistema")
	@Enumerated(EnumType.STRING)
	private PerspectivaProducaoSistema sistemaAnimal;

	public PerspectivaProducaoAnimalExploracao getExploracao() {
		return exploracao;
	}

	public PerspectivaProducaoSistema getSistemaAnimal() {
		return sistemaAnimal;
	}

	public void setExploracao(PerspectivaProducaoAnimalExploracao exploracao) {
		this.exploracao = exploracao;
	}

	public void setSistemaAnimal(PerspectivaProducaoSistema sistemaAnimal) {
		this.sistemaAnimal = sistemaAnimal;
	}

}