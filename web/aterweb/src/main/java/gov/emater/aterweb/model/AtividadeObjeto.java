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
@Table(name = "atividade_objeto", schema = EntidadeBase.ATIVIDADE_SCHEMA)
public class AtividadeObjeto extends EntidadeBase implements _ChavePrimaria<Integer> {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "atividade_id")
	private Atividade atividade;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	@ManyToOne
	@JoinColumn(name = "producao_id")
	private Producao producao;

	@ManyToOne
	@JoinColumn(name = "propriedade_rural_id")
	private PropriedadeRural propriedadeRural;

	public Atividade getAtividade() {
		return atividade;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Producao getProducao() {
		return producao;
	}

	public PropriedadeRural getPropriedadeRural() {
		return propriedadeRural;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setProducao(Producao producao) {
		this.producao = producao;
	}
	
	public void setPropriedadeRural(PropriedadeRural propriedadeRural) {
		this.propriedadeRural = propriedadeRural;
	}

}