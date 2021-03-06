package br.gov.df.emater.aterwebsrv.modelo.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo.dominio.RelacionamentoFuncaoParticipacao;

/**
 * The persistent class for the relacionamento_funcao database table.
 * 
 */
@Entity
@Table(name = "relacionamento_funcao", schema = EntidadeBase.PESSOA_SCHEMA)
public class RelacionamentoFuncao extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nome_se_feminino")
	private String nomeSeFeminino;

	@Column(name = "nome_se_masculino")
	private String nomeSeMasculino;

	@Enumerated(EnumType.STRING)
	private RelacionamentoFuncaoParticipacao participacao;

	public RelacionamentoFuncao() {
	}

	public RelacionamentoFuncao(Integer id) {
		super(id);
	}

	public RelacionamentoFuncao(Integer id, String nomeSeFeminino, String nomeSeMasculino) {
		this(id);
		setNomeSeFeminino(nomeSeFeminino);
		setNomeSeMasculino(nomeSeMasculino);
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public String getNomeSeFeminino() {
		return this.nomeSeFeminino;
	}

	public String getNomeSeMasculino() {
		return this.nomeSeMasculino;
	}

	public RelacionamentoFuncaoParticipacao getParticipacao() {
		return participacao;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNomeSeFeminino(String nomeSeFeminino) {
		this.nomeSeFeminino = nomeSeFeminino;
	}

	public void setNomeSeMasculino(String nomeSeMasculino) {
		this.nomeSeMasculino = nomeSeMasculino;
	}

	public void setParticipacao(RelacionamentoFuncaoParticipacao participacao) {
		this.participacao = participacao;
	}

}