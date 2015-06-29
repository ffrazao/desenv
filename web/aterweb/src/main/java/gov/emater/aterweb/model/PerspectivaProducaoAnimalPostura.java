package gov.emater.aterweb.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The persistent class for the perspectiva_postura database table.
 * 
 */
@Entity
@Table(name = "perspectiva_postura", schema = EntidadeBase.IPA_SCHEMA)
@PrimaryKeyJoinColumn(name = "id")
public class PerspectivaProducaoAnimalPostura extends PerspectivaProducaoAnimal {

	private static final long serialVersionUID = 1L;

}