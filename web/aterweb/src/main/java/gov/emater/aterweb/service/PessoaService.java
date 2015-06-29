package gov.emater.aterweb.service;

import gov.emater.aterweb.model.MeioContatoEndereco;
import gov.emater.aterweb.model.Pessoa;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PessoaService extends CrudService<Pessoa, Integer> {

	public MeioContatoEndereco saveEndereco(MeioContatoEndereco entidade);

	public void setFotoPerfil(Pessoa pessoa);

	public Map<String, Object> completaDadosBuscaCep(Map<String, Object> dados) throws IOException;

	public List<Map<String, Object>> procurarEnderecoPorPessoa(String nome, String documento, Boolean somentePropriedadeRural);

}