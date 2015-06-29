package gov.emater.aterweb.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The persistent class for the perspectiva_turismo database table.
 * 
 */
@Entity
@Table(name = "perspectiva_turismo", schema = EntidadeBase.IPA_SCHEMA)
@PrimaryKeyJoinColumn(name = "id")
public class PerspectivaProducaoServicoTurismo extends PerspectivaProducaoServico {

	private static final long serialVersionUID = 1L;

}