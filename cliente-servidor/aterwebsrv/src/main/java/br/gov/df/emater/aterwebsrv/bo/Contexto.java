package br.gov.df.emater.aterwebsrv.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.chain.impl.ContextBase;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Contexto extends ContextBase {

	public static final String ERRO = "erro";

	public static final String REQUISICAO = "requisicao";

	public static final String RESPOSTA = "resposta";

	private static final long serialVersionUID = 1L;

	public Contexto() {
		this(null);
	}

	public Contexto(Map map) {
		super(map == null ? new HashMap<String, Object>() : map);
		/*super.put(REQUISICAO, null);
		super.put(RESPOSTA, null);
		super.put(ERRO, null);*/
	}

	public Object getErro() {
		return super.get(ERRO);
	}

	public Object getRequisicao() {
		return super.get(REQUISICAO);
	}

	public Object getResposta() {
		return super.get(RESPOSTA);
	}

	public void setErro(Object erro) {
		super.putIfAbsent(ERRO, erro);
	}

	public void setRequisicao(Object requisicao) {
		super.putIfAbsent(REQUISICAO, requisicao);
	}

	public void setResposta(Object resposta) {
		super.putIfAbsent(RESPOSTA, resposta);
	}
}
