<!-- Frazão Inicio Principal -->

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
				<div class="form-control">{{registro.pessoaTipo === 'PF' ? 'Pessoa Física' : registro.pessoaTipo === 'PJ' ? 'Pessoa Jurídica' : '' }}</div>
			</div>
			<div class="col-md-2">
				<label for="apelido" class="control-label">Cadastro Especial</label>
				<div class="checkbox" data-ng-class="{ 'has-error box-error' : formularioPessoa.publicoAlvoConfirmacao.$invalid && (!formularioPessoa.publicoAlvoConfirmacao.$pristine || submitted)}">
					<label for="publicoAlvoConfirmacao">
						<input type="checkbox" name="publicoAlvoConfirmacao" id="publicoAlvoConfirmacao" data-ng-model="registro.publicoAlvoConfirmacao" data-ng-true-value="S" data-ng-false-value="N">
						Público Alvo
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
					<option value="">*** Não Informado ***</option>
				</select>
			</div>
			<div class="col-md-3" data-ng-class="{ 'has-error box-error' : formularioPessoa.situacao.$invalid && (!formularioPessoa.situacao.$pristine || submitted)}">
				<label for="situacao" class="control-label">Situação</label><br>
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
		<label for="geracao" class="control-label">Geração</label>
		<input class="form-control" id="geracao" data-ng-model="registro.geracao" name="geracao" placeholder="Geração" readonly="readonly"/>
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
				<td><h6><b>&nbsp; Não</b></h6></td>
			</tr>
		</table>
	</div>
</div>

<div class="row">
	<div class="col-md-3">
		<label for="inputEstadoCivil" class="control-label">Estado Civil</label>
		<select class="form-control" data-ng-model="registro.estadoCivil" id="estadoCivil" name="estadoCivil" data-ng-options="item.codigo as item.descricao for item in apoio.estadoCivilList">
			<option value="">*** Não Informado ***</option>
		</select>
	</div>
</div>

<div class="row pessoaJuridica" data-ng-if="registro.pessoaTipo == 'PJ'">
	<div class="col-md-3">
		<label for="cnpj" class="control-label">CNPJ</label>
		<input class="form-control cnpj" data-ng-model="registro.cnpj" data-ng-blur="validarCNPJ(registro.cnpj)" id="cnpj" placeholder="cnpj" />
	</div>
	<div class="col-md-3">
		<label for="inputInscricaoEstadual" class="control-label">Iscrição Estadual</label>
		<input class="form-control" data-ng-model="registro.inscricaoEstadual" id="inscricaoEstadual inscricao" name="inscricaoEstadual" placeholder="Iscrição Estadual" />
	</div>
</div>

<div class="row">
	<div class="col-md-11">
		<label for="observacoes" class="control-label">Observações</label>
		<textarea class="form-control" data-ng-model="registro.observacoes" id="observacoes" name="observacoes" rows="3"></textarea>
	</div>
</div>

<!-- Frazão Fim Principal -->