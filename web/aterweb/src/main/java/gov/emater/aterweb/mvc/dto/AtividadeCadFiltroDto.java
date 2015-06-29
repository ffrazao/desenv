package gov.emater.aterweb.mvc.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AtividadeCadFiltroDto implements FiltroDto {

	private static final long serialVersionUID = 1L;

	private List<Object> atividadeAssuntoAcaoList;

	private String codigo;

	private List<Map<String, Set<Integer>>> grupoSocialList;

	private String inicio;

	private List<Integer> metodoIdList;

	private Integer numeroPagina;

	private List<String> pessoaList;

	private List<Map<String, Set<Integer>>> pessoaListJava;

	private List<String> situacaoIdList;

	private String termino;

	public List<Object> getAtividadeAssuntoAcaoList() {
		return atividadeAssuntoAcaoList;
	}

	public String getCodigo() {
		return codigo;
	}

	public List<Map<String, Set<Integer>>> getGrupoSocialList() {
		return grupoSocialList;
	}

	public String getInicio() {
		return inicio;
	}

	public List<Integer> getMetodoIdList() {
		return metodoIdList;
	}

	@Override
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public List<String> getPessoaList() {
		return pessoaList;
	}

	public List<Map<String, Set<Integer>>> getPessoaListJava() {
		return pessoaListJava;
	}

	public List<String> getSituacaoIdList() {
		return situacaoIdList;
	}

	public String getTermino() {
		return termino;
	}

	public void setAtividadeAssuntoAcaoList(List<Object> atividadeAssuntoAcaoList) {
		this.atividadeAssuntoAcaoList = atividadeAssuntoAcaoList;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setGrupoSocialList(List<Map<String, Set<Integer>>> grupoSocialList) {
		this.grupoSocialList = grupoSocialList;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public void setMetodoIdList(List<Integer> metodoIdList) {
		this.metodoIdList = metodoIdList;
	}

	@Override
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public void setPessoaList(List<String> pessoaList) {
		this.pessoaList = pessoaList;
	}

	public void setPessoaListJava(List<Map<String, Set<Integer>>> pessoaListJava) {
		this.pessoaListJava = pessoaListJava;
	}

	public void setSituacaoIdList(List<String> situacaoIdList) {
		this.situacaoIdList = situacaoIdList;
	}

	public void setTermino(String termino) {
		this.termino = termino;
	}

}