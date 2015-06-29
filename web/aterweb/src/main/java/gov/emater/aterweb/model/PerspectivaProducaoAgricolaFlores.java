package gov.emater.aterweb.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The persistent class for the perspectiva_producao_agricola database table.
 * 
 */
@Entity
@Table(name = "perspectiva_flores", schema = EntidadeBase.IPA_SCHEMA)
@PrimaryKeyJoinColumn(name = "id")
public class PerspectivaProducaoAgricolaFlores extends PerspectivaProducaoAgricola {

	private static final long serialVersionUID = 1L;

}