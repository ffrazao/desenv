package gov.emater.aterweb.model;

import gov.emater.aterweb.dao._ChavePrimaria;
import gov.emater.aterweb.mvc.config.JsonDeserializerMilisegundos;
import gov.emater.aterweb.mvc.config.JsonSerializerMilisegundos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "atividade_arquivo", schema = EntidadeBase.ATIVIDADE_SCHEMA)
public class AtividadeArquivo extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "arquivo_id")
	private Arquivo arquivo;

	@ManyToOne
	@JoinColumn(name = "atividade_id")
	private Atividade atividade;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = JsonSerializerMilisegundos.class)
	@JsonDeserialize(using = JsonDeserializerMilisegundos.class)
	@Column(name = "data_upload")
	private Calendar dataUpload;

	@Column(name = "descricao")
	private String descricao;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public AtividadeArquivo() {
		super();
	}

	public AtividadeArquivo(Integer id) {
		super(id);
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public Calendar getDataUpload() {
		return dataUpload;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public void setDataUpload(Calendar dataUpload) {
		this.dataUpload = dataUpload;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}