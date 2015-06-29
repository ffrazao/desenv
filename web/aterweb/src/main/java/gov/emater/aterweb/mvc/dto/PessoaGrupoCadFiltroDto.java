package gov.emater.aterweb.mvc.dto;

import gov.emater.aterweb.model.Pessoa;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.domain.PessoaSituacao;

public class PessoaGrupoCadFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private Pessoa gestorGrupoInstituicao;

	private boolean gestorGrupoInstituicaoCheck;

	private Pessoa gestorGrupoTecnico;

	private boolean gestorGrupoTecnicoCheck;

	private boolean gestorGrupoTodosCheck;

	private Pessoa gestorGrupoUnidadeOrganizacional;

	private boolean gestorGrupoUnidadeOrganizacionalCheck;

	private String nome;

	private Integer numeroPagina;

	private PessoaGrupoTipo pessoaGrupoTipo;

	private boolean selecionarSomenteGestores = false;

	private PessoaSituacao situacaoGrupo;
	
	private String idsFiltrados;

	public String getIdsFiltrados() {
		return idsFiltrados;
	}

	public void setIdsFiltrados(String idsFiltrados) {
		this.idsFiltrados = idsFiltrados;
	}

	public PessoaGrupoCadFiltroDto() {

	}

	public PessoaGrupoCadFiltroDto(String nome) {
		setNome(nome);
	}

	public Pessoa getGestorGrupoInstituicao() {
		return gestorGrupoInstituicao;
	}

	public Pessoa getGestorGrupoTecnico() {
		return gestorGrupoTecnico;
	}

	public Pessoa getGestorGrupoUnidadeOrganizacional() {
		return gestorGrupoUnidadeOrganizacional;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public PessoaGrupoTipo getPessoaGrupoTipo() {
		return pessoaGrupoTipo;
	}

	public PessoaSituacao getSituacaoGrupo() {
		return situacaoGrupo;
	}

	public boolean isGestorGrupoInstituicaoCheck() {
		return gestorGrupoInstituicaoCheck;
	}

	public boolean isGestorGrupoTecnicoCheck() {
		return gestorGrupoTecnicoCheck;
	}

	public boolean isGestorGrupoTodosCheck() {
		return gestorGrupoTodosCheck;
	}

	public boolean isGestorGrupoUnidadeOrganizacionalCheck() {
		return gestorGrupoUnidadeOrganizacionalCheck;
	}

	public boolean isSelecionarSomenteGestores() {
		return selecionarSomenteGestores;
	}

	public void setGestorGrupoInstituicao(Pessoa gestorGrupoInstituicao) {
		this.gestorGrupoInstituicao = gestorGrupoInstituicao;
	}

	public void setGestorGrupoInstituicaoCheck(boolean gestorGrupoInstituicaoCheck) {
		this.gestorGrupoInstituicaoCheck = gestorGrupoInstituicaoCheck;
	}

	public void setGestorGrupoTecnico(Pessoa gestorGrupoTecnico) {
		this.gestorGrupoTecnico = gestorGrupoTecnico;
	}

	public void setGestorGrupoTecnicoCheck(boolean gestorGrupoTecnicoCheck) {
		this.gestorGrupoTecnicoCheck = gestorGrupoTecnicoCheck;
	}

	public void setGestorGrupoTodosCheck(boolean gestorGrupoTodosCheck) {
		this.gestorGrupoTodosCheck = gestorGrupoTodosCheck;
	}

	public void setGestorGrupoUnidadeOrganizacional(Pessoa gestorGrupoUnidadeOrganizacional) {
		this.gestorGrupoUnidadeOrganizacional = gestorGrupoUnidadeOrganizacional;
	}

	public void setGestorGrupoUnidadeOrganizacionalCheck(boolean gestorGrupoUnidadeOrganizacionalCheck) {
		this.gestorGrupoUnidadeOrganizacionalCheck = gestorGrupoUnidadeOrganizacionalCheck;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public void setPessoaGrupoTipo(PessoaGrupoTipo pessoaGrupoTipo) {
		this.pessoaGrupoTipo = pessoaGrupoTipo;
	}

	public void setSelecionarSomenteGestores(boolean selecionarSomenteGestores) {
		this.selecionarSomenteGestores = selecionarSomenteGestores;
	}

	public void setSituacaoGrupo(PessoaSituacao situacaoGrupo) {
		this.situacaoGrupo = situacaoGrupo;
	}

}