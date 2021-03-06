<div class="row">
	<div class="form-group col-md-6">
		<label class="control-label">Cadastro Especial</label>
		<div class="form-control">
			<label class="checkbox-inline" for="beneficiario">
				<input type="checkbox" id="beneficiario" name="beneficiario" ng-model="registro.publicoAlvoConfirmacao" ng-true-value="S" ng-false-value="N">
				Benefici�rio
			</label> 
			<label class="checkbox-inline" for="colaborador">
				<input type="checkbox" id="colaborador" name="colaborador" ng-model="registro.colaboradorConfirmacao" ng-true-value="S" ng-false-value="N">
				Colaborador
			</label>
		</div>
	</div>
	<div class="form-group col-md-6">
		<label class="control-label">G�nero</label>
		<div class="form-control">
			<label class="radio-inline" ng-repeat="item in apoio.sexoList" for="genero-{{item.codigo}}">
				<input type="radio" id="genero-{{item.codigo}}" name="genero" value="{{item.codigo}}" data-ng-model="registro.sexo">
				{{item.descricao}}
			</label> 
		</div>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-12">
		<label class="control-label">Nascimento</label>
		<div class="row">
			<div class="form-group col-md-2">
				<label class="control-label">Data</label>
				<input class="form-control" type="text" data-ng-model="registro.nascimento" ui-date-mask /> 
			</div>
			<div class="form-group col-md-1">
				<label class="control-label">Idade</label>
				<input class="form-control" type="text" readonly="true" data-ng-model="registro.idade" /> 
			</div>
			<div class="form-group col-md-3">
				<label class="control-label">Pa�s</label>
				<select class="form-control" id="pais" name="pais" data-ng-model="registro.nascimentoPais" data-ng-options="item as item.nome for item in apoio.pessoaGrupoPaisViList" ng-change="registro.nascimentoEstado = null">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="form-group col-md-3" ng-show="registro.nascimentoPais.sigla === 'BR'">
				<label class="control-label">Estado</label>
				<select class="form-control" id="estado" name="estado" data-ng-model="registro.nascimentoEstado" data-ng-options="item as item.nome for item in apoio.pessoaGrupoEstadoViList" ng-change="registro.nascimentoMunicipio = null">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="form-group col-md-3" ng-show="registro.nascimentoPais.sigla === 'BR'">
				<label class="control-label">Munic�pio</label>
				<select class="form-control" id="municipio" name="municipio" data-ng-model="registro.nascimentoMunicipio" data-ng-options="item as item.nome for item in apoio.pessoaGrupoMunicipioViList">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="form-group col-md-3" ng-show="registro.nascimentoPais.sigla && registro.nascimentoPais.sigla !== 'BR'">
				<label class="radio-inline" for="genero-1">
					<input type="checkbox" name="genero" id="genero-1" value="F">
					Naturalizado?
				</label>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-12">
		<label class="control-label">Endere�o</label>
        <ng-include src="'tiles/pessoa-cad/sub-endereco.jsp'" ng-controller="SubEnderecoCtrl"/>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-6">
		<label class="control-label">Telefone</label>
        <ng-include src="'tiles/pessoa-cad/sub-telefone.jsp'" ng-controller="SubTelefoneCtrl"/>
	</div>
	<div class="form-group col-md-6">
		<label class="control-label">E-mail</label>
        <ng-include src="'tiles/pessoa-cad/sub-email.jsp'" ng-controller="SubEmailCtrl"/>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-12">
		<label class="control-label">Relacionamentos</label>
        <ng-include src="'tiles/pessoa-cad/sub-relacionamento.jsp'" ng-controller="SubRelacionamentoCtrl"/>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-4">
		<label class="control-label">Escolaridade</label>
		<select class="form-control" id="escolaridade" name="escolaridade" data-ng-model="registro.escolaridade" data-ng-options="item.codigo as item.descricao for item in apoio.escolaridadeList">
			<option value="">*** N�o Informado ***</option>
		</select>
	</div>
	<div class="form-group col-md-4">
		<label class="control-label">Profiss�o</label>
		<select class="form-control" id="profissao" name="profissao" data-ng-model="registro.profissao" data-ng-options="item.id as item.nome for item in apoio.profissaoList">
			<option value="">*** N�o Informado ***</option>
		</select>
	</div>
	<div class="form-group col-md-4">
		<label class="control-label">Estado Civil</label>
		<select class="form-control" id="estadoCivil" name="estadoCivil" data-ng-model="registro.estadoCivil" data-ng-options="item.codigo as item.descricao for item in apoio.estadoCivilList">
			<option value="">*** N�o Informado ***</option>
		</select>
	</div>
</div>
<div class="row">
	<fieldset class="form-group col-md-5">
		<label class="control-label">RG - Registro Geral</label>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label">N�mero</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-4">
				<label class="control-label">�rg. Emis.</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-4">
				<label class="control-label">Data</label>
				<input class="form-control" type="text" data-ng-model="registro.rg.emissao"  ui-date-mask />
			</div>
		</div>
	</fieldset>
	<div class="form-group col-md-5">
		<label class="control-label">T�tulo de Eleitor</label>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label">N�mero</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-4">
				<label class="control-label">Se��o</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-4">
				<label class="control-label">Zona</label>
				<input class="form-control" type="text"/>
			</div>
		</div>
	</div>
	<div class="form-group col-md-2">
		<label class="control-label">NIS - N�mero de Identifica��o Social</label>
		<input class="form-control" type="text"/>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-6">
		<label class="control-label">Certid�o de Casamento</label>
		<div class="row">
			<div class="col-md-3">
				<label class="control-label">Cart�rio</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">Folha</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">Livro</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">Regime</label>
				<select class="form-control" id="certidaoCasamentoRegime" name="certidaoCasamentoRegime" data-ng-model="registro.certidaoCasamentoRegime" data-ng-options="item.codigo as item.descricao for item in apoio.regimeCasamentoList">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group col-md-6" ng-show="colaboradorVisivel">
		<label class="control-label">CNH - Carteira Nacional de Habilita��o</label>
		<div class="row">
			<div class="col-md-3">
				<label class="control-label">Categoria</label>
				<select class="form-control" id="cnhCategoria" name="cnhCategoria" data-ng-model="registro.cnhCategoria" data-ng-options="item.codigo as item.descricao for item in apoio.cnhCategoriaList">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="col-md-3">
				<label class="control-label">N�mero</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">1� Habil.</label>
				<input class="form-control" type="text" ng-model="registro.cnhPrimeiraHabilitacao" ui-date-mask />
			</div>
			<div class="col-md-3">
				<label class="control-label">Validade</label>
				<input class="form-control" type="text" ng-model="registro.cnhValidade" ui-date-mask />
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="form-group col-md-6" ng-show="colaboradorVisivel">
		<label class="control-label">CAM - Certificado de Alistamento Militar</label>
		<div class="row">
			<div class="col-md-3">
				<label class="control-label">N�mero</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">�rg�o</label>
				<select class="form-control" id="camOrgao" name="camOrgao" data-ng-model="registro.camOrgao" data-ng-options="item.codigo as item.descricao for item in apoio.camOrgaoList">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="col-md-3">
				<label class="control-label">S�rie</label>
				<input class="form-control" type="text"/>
			</div>
			<div class="col-md-3">
				<label class="control-label">Unid. Militar</label>
				<input class="form-control" type="text"/>
			</div>
		</div>
	</div>
</div>

<!-- 

<div class="row">
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-6" data-ng-class="{ 'has-error' : formularioPessoa.nome.$invalid && (!formularioPessoa.nome.$pristine || submitted)}">
				<label for="nome" class="control-label">Nome</label>
				<input type="text" class="form-control" id="nome" data-ng-model="registro.nome" data-ng-minlength=3 data-ng-maxlength=250 name="nome" placeholder="Nome" required>
			</div>
			<div class="col-md-2">
				<label for="apelido" class="control-label">Apelido</label>
				<input type="text" class="form-control" id="apelidoSigla" data-ng-model="registro.apelidoSigla" name="apelidoSigla" placeholder="Apelido" />
			</div>
			<div class="col-md-2">
				<label class="control-label">Natureza </label>
				<div class="form-control">{{registro.pessoaTipo === 'PF' ? 'Pessoa F�sica' : registro.pessoaTipo === 'PJ' ? 'Pessoa Jur�dica' : '' }}</div>
			</div>
			<div class="col-md-2">
				<label for="apelido" class="control-label">Cadastro Especial</label>
				<div class="checkbox" data-ng-class="{ 'has-error box-error' : formularioPessoa.publicoAlvoConfirmacao.$invalid && (!formularioPessoa.publicoAlvoConfirmacao.$pristine || submitted)}">
					<label for="publicoAlvoConfirmacao">
						<input type="checkbox" name="publicoAlvoConfirmacao" id="publicoAlvoConfirmacao" data-ng-model="registro.publicoAlvoConfirmacao" data-ng-true-value="S" data-ng-false-value="N">
						P�blico Alvo
					</label>
				</div>
				<div class="checkbox">
					<label for="colaborador">
						<input type="checkbox" name="colaborador" id="colaborador" data-ng-model="registro.colaborador" data-ng-true-value="S" data-ng-false-value="N">
						Colaborador
					</label>
				</div>
			</div>
		</div>

		<div class="row pessoaFisica" data-ng-if="registro.pessoaTipo === 'PF'">
			<div class="col-md-3">
				<label for="cpf" class="control-label">CPF</label>
				<input type="text" class="form-control cpf" id="cpf" data-ng-model="registro.cpf" data-ng-blur="validarCPF(registro.cpf)" name="cpf" placeholder="CPF" />
			</div>
			<div class="col-md-2" data-ng-class="{ 'has-error box-error' : formularioPessoa.sexo.$invalid && (!formularioPessoa.sexo.$pristine || submitted)}">
				<label for="inputSexo" class="control-label">Sexo</label>
				<table>
					<tr>
						<td><input type="radio" id="sexo" name="sexo" value="M" data-ng-model="registro.sexo" required /></td>
						<td><h6><b>&nbsp; M &nbsp; </b></h6></td>
						<td><input type="radio" id="sexo" name="sexo" value="F" data-ng-model="registro.sexo" required /></td>
						<td><h6><b>&nbsp; F</b></h6></td>
					</tr>
				</table>
			</div>
			<div class="col-md-3">
				<label for="inputEscolaridade" class="control-label">Escolaridade</label>
				<select class="form-control" data-ng-model="registro.escolaridade" id="escolaridade" name="escolaridade" data-ng-options="item.codigo as item.descricao for item in apoio.escolaridadeList">
					<option value="">*** N�o Informado ***</option>
				</select>
			</div>
			<div class="col-md-3" data-ng-class="{ 'has-error box-error' : formularioPessoa.situacao.$invalid && (!formularioPessoa.situacao.$pristine || submitted)}">
				<label for="situacao" class="control-label">Situa��o</label><br>
				<select class="form-control" data-ng-model="registro.situacao" id="situacao" name="situacao" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList" required>
				</select>
			</div>
		</div>
	</div>
	<div class="col-md-2">
		<div class="visible-md visible-lg">
			<div data-ng-repeat="registro in registro.arquivoList">
				<div data-ng-if="registro.perfil == 'S'" data-ng-switch data-on="registro.arquivo.md5">
					<img data-ng-switch-when="" src="./resources/img/pessoa_padrao.png" width="130" height="160" /> <img data-ng-switch-default src="./resources/upload/{{registro.arquivo.md5}}{{registro.arquivo.extensao}}" width="130" height="160" />
				</div>
			</div>
			<img data-ng-show="!registro.arquivoList || registro.arquivoList.length == 0" src="./resources/img/pessoa_padrao.png" width="130" height="160" />
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-2">
		<label for="nascimento" class="control-label">Nascimento</label>
		<input class="form-control data" id="nascimento" data-ng-model="registro.nascimento" name="nascimento" placeholder="Data de Nascimento" />
	</div>
	<div class="col-md-2">
		<label for="nascimento" class="control-label">Idade</label>
		<input class="form-control" id="idade" data-ng-model="registro.idade" name="idade" placeholder="Idade" readonly="readonly" />
	</div>
	<div class="col-md-2">
		<label for="geracao" class="control-label">Gera��o</label>
		<input class="form-control" id="geracao" data-ng-model="registro.geracao" name="geracao" placeholder="Gera��o" readonly="readonly"/>
	</div>
	<div class="col-md-2" data-ng-class="{ 'has-error box-error' : formularioPessoa.situacao.$invalid && (!formularioPessoa.situacao.$pristine || submitted)}">
		<label for="situacao" class="control-label">Pais</label><br>
		<select class="form-control" data-ng-model="registro.situacao" id="situacao" name="situacao" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList" required>
		</select>
	</div>
	<div class="col-md-2" data-ng-class="{ 'has-error box-error' : formularioPessoa.situacao.$invalid && (!formularioPessoa.situacao.$pristine || submitted)}">
		<label for="situacao" class="control-label">Estado</label><br>
		<select class="form-control" data-ng-model="registro.situacao" id="situacao" name="situacao" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList" required>
		</select>
	</div>
	<div class="col-md-2" data-ng-class="{ 'has-error box-error' : formularioPessoa.situacao.$invalid && (!formularioPessoa.situacao.$pristine || submitted)}">
		<label for="situacao" class="control-label">Municipio</label><br>
		<select class="form-control" data-ng-model="registro.situacao" id="situacao" name="situacao" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList" required>
		</select>
	</div>
	<div class="col-md-3" data-ng-show="false">
		<label for="inputBrasileiro" class="control-label">Naturalizado</label>
		<table>
			<tr>
				<td><input type="radio" id="nacionalidade" data-ng-model="registro.nacionalidade" name="nacionalidade" value="BN" /></td>
				<td><h6><b>&nbsp; Sim &nbsp; </b></h6></td>
				<td><input type="radio" id="nacionalidade" data-ng-model="registro.nacionalidade" name="nacionalidade" value="N" /></td>
				<td><h6><b>&nbsp; N�o</b></h6></td>
			</tr>
		</table>
	</div>
</div>

<div class="row">
	<div class="col-md-3">
		<label for="inputEstadoCivil" class="control-label">Estado Civil</label>
		<select class="form-control" data-ng-model="registro.estadoCivil" id="estadoCivil" name="estadoCivil" data-ng-options="item.codigo as item.descricao for item in apoio.estadoCivilList">
			<option value="">*** N�o Informado ***</option>
		</select>
	</div>
</div>

<div class="row pessoaJuridica" data-ng-if="registro.pessoaTipo == 'PJ'">
	<div class="col-md-3">
		<label for="cnpj" class="control-label">CNPJ</label>
		<input class="form-control cnpj" data-ng-model="registro.cnpj" data-ng-blur="validarCNPJ(registro.cnpj)" id="cnpj" placeholder="cnpj" />
	</div>
	<div class="col-md-3">
		<label for="inputInscricaoEstadual" class="control-label">Iscri��o Estadual</label>
		<input class="form-control" data-ng-model="registro.inscricaoEstadual" id="inscricaoEstadual inscricao" name="inscricaoEstadual" placeholder="Iscri��o Estadual" />
	</div>
</div>

<div class="row">
	<div class="col-md-11">
		<label for="observacoes" class="control-label">Observa��es</label>
		<textarea class="form-control" data-ng-model="registro.observacoes" id="observacoes" name="observacoes" rows="3"></textarea>
	</div>
</div>

 -->