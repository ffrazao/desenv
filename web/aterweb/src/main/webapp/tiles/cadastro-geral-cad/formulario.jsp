<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="div_formulario">
	<div class="panel panel-success">
		<div class="panel-heading">

			<h3 class="panel-title">Formulário</h3>
		</div>
		<div class="panel-body" data-ng-class="{statusInvalido: registro.status == 'I'}">
			<form name="frmCadastro" data-ng-submit="salvar(frmCadastro.$valid)" novalidate>
			    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			    <input type="hidden" data-ng-model="registro.id" />
				<div class="row">
					<div class="form-group col-md-5" data-ng-class="{ 'has-error' : frmCadastro.nomePropriedade.$invalid && (!frmCadastro.nomePropriedade.$pristine || submitted) }">
						<label class="control-label" for="nomePropriedade">Nome*</label>
						<input class="form-control" id="nomePropriedade" name="nomePropriedade" placeholder="Nome da Propriedade Rural" data-ng-model="registro.nome" required />
						<p data-ng-show="frmCadastro.nomePropriedade.$invalid && (!frmCadastro.nomePropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
					<div class="form-group col-md-5">
						<label class="control-label" for="razaoSocial">Razão Social</label>
						<input class="form-control" id="razaoSocial" placeholder="Razão Social da Propriedade Rural" data-ng-model="registro.razaoSocial" />
					</div>
					<div class="form-group col-md-2">
						<label class="control-label" for="cnpj">CNPJ</label>
						<input class="form-control" id="cnpj" placeholder="CNPJ da Propriedade Rural" data-ng-model="registro.cnpj" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastro.enderecoPropriedade.$invalid && (!frmCadastro.enderecoPropriedade.$pristine || submitted) }">
						<label class="control-label" for="enderecoPropriedade">Endereço*</label>
						<input class="form-control" id="enderecoPropriedade" name="enderecoPropriedade" placeholder="Endereço da Propriedade Rural" data-ng-model="registro.endereco" required />
						<p data-ng-show="frmCadastro.enderecoPropriedade.$invalid && (!frmCadastro.enderecoPropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
					<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastro.cidadePropriedade.$invalid && (!frmCadastro.cidadePropriedade.$pristine || submitted) }">
						<label class="control-label" for="cidadePropriedade">Cidade*</label>
						<input class="form-control" id="cidadePropriedade" name="cidadePropriedade" placeholder="Cidade da Propriedade Rural" data-ng-model="registro.cidade" required />
						<p data-ng-show="frmCadastro.cidadePropriedade.$invalid && (!frmCadastro.cidadePropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
					<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastro.bairroPropriedade.$invalid && (!frmCadastro.bairroPropriedade.$pristine || submitted) }">
						<label class="control-label" for="bairroPropriedade">Bairro*</label>
						<input class="form-control" id="bairroPropriedade" name="bairroPropriedade" placeholder="Bairro da Propriedade da Propriedade Rural" data-ng-model="registro.bairro" required />
						<p data-ng-show="frmCadastro.bairroPropriedade.$invalid && (!frmCadastro.bairroPropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
					<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastro.ufPropriedade.$invalid && (!frmCadastro.ufPropriedade.$pristine || submitted) }">
						<label class="control-label" for="ufPropriedade">UF*</label>
						<select class="form-control" id="ufPropriedade" name="ufPropriedade" data-ng-model="registro.uf" required>
							<option data-ng-repeat="estado in apoio.estado" value="{{estado.sigla}}">{{estado.nome}}</option>
						</select>
						<p data-ng-show="frmCadastro.ufPropriedade.$invalid && (!frmCadastro.ufPropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-4">
						<label class="control-label" for="telefone1">Telefone 1</label>
						<input class="form-control" id="telefone1" placeholder="Telefone 1" data-ng-model="registro.telefone1" />
					</div>
					<div class="form-group col-md-4">
						<label class="control-label" for="telefone2">Telefone 2</label>
						<input class="form-control" id="telefone2" placeholder="Telefone 2" data-ng-model="registro.telefone2" />
					</div>
					<div class="form-group col-md-2">
						<label class="control-label" for="geoReferenciamentoS">Georref. S</label>
						<input class="form-control" id="geoReferenciamentoS" placeholder="Georreferenciamento S" data-ng-model="registro.geoReferenciamentoS" />
					</div>
					<div class="form-group col-md-2">
						<label class="control-label" for="geoReferenciamentoW">Georref. W</label>
						<input class="form-control" id="geoReferenciamentoW" placeholder="Georreferenciamento W" data-ng-model="registro.geoReferenciamentoW" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-3">
						<label class="control-label" for="areaPropriedade">Área da Propriedade Rural</label>
						<input class="form-control" id="areaPropriedade" placeholder="Área da Propriedade Rural" data-ng-model="registro.areaPropriedade" />
					</div>
					<div class="form-group col-md-3">
						<label class="control-label" for="inicioAtividade">Início da Atividade Rural</label>
						<input class="form-control data" id="inicioAtividade" placeholder="Início da Atividade Rural" data-ng-model="registro.inicioAtividade" />
					</div>
					<div class="form-group col-md-3">
						<label class="control-label" for="numeroEmpregadoFixo">Número de Empregados Fixos</label>
						<input class="form-control" id="numeroEmpregadoFixo" placeholder="Número de Empregados Fixos da Propriedade Rural" data-ng-model="registro.numeroEmpregadoFixo" />
					</div>
					<div class="form-group col-md-3">
						<label class="control-label" for="principaisAtividadesProdutivas">Principais Atividades Produtivas</label>
						<input class="form-control" id="principaisAtividadesProdutivas" placeholder="Principais Atividades Produtivas da Propriedade Rural" data-ng-model="registro.principaisAtividadesProdutivas" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-3">
						<div class="checkbox">
							<label>
								<input type="checkbox" id="arrendatario" data-ng-model="registro.arrendatario" data-ng-true-value="S" data-ng-false-value="N" />Tem Arrendatário?
							</label>
						</div>
					</div>
					<div class="form-group col-md-3">
						<div class="checkbox">
							<label>
								<input type="checkbox" id="temReservaLegal" data-ng-model="registro.temReservaLegal" data-ng-true-value="S" data-ng-false-value="N" />Tem Reserva Legal?
							</label>
						</div>
					</div>
					<div class="form-group col-md-3">
						<div class="checkbox">
							<label>
								<input type="checkbox" id="temApp" data-ng-model="registro.temApp" data-ng-true-value="S" data-ng-false-value="N" />Tem APP?
							</label>
						</div>
					</div>
					<div class="form-group col-md-3">
						<div class="checkbox">
							<label>
								<input type="checkbox" id="temPlanoUtilizacao" data-ng-model="registro.temPlanoUtilizacao" data-ng-true-value="S" data-ng-false-value="N" />Tem Plano de Utilização?
							</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label class="control-label" for="situacaoFundiaria">Situação Fundiária</label><br />
						<label><input type="radio" name="situacaoFundiaria" id="situacaoFundiaria" data-ng-model="registro.situacaoFundiaria" value="R" /> Escritura Definitiva </label><br />
						<label><input type="radio" name="situacaoFundiaria" id="situacaoFundiaria" data-ng-model="registro.situacaoFundiaria" value="C" /> Concessão de Uso </label><br />
						<label><input type="radio" name="situacaoFundiaria" id="situacaoFundiaria" data-ng-model="registro.situacaoFundiaria" value="A" /> Arrendatário </label><br />
						<label><input type="radio" name="situacaoFundiaria" id="situacaoFundiaria" data-ng-model="registro.situacaoFundiaria" value="P" /> Posse </label><br />
						<label><input type="radio" name="situacaoFundiaria" id="situacaoFundiaria" data-ng-model="registro.situacaoFundiaria" value="O" /> Outros 
							<input class="form-control" id="situacaoFundiariaOutro" placeholder="Outra Situação Fundiária da Propriedade Rural" data-ng-model="registro.situacaoFundiariaOutro" data-ng-show="registro.situacaoFundiaria == 'O'" />
						</label><br />
					</div>
					<div class="form-group col-md-6" data-ng-class="{ 'has-error' : frmCadastro.statusPropriedade.$invalid && (!frmCadastro.statusPropriedade.$pristine || submitted) }">
						<label class="control-label" for="status">Status da Propriedade Rural*</label><br />
						<label><input type="radio" name="statusPropriedade" id="statusPropriedadeValido" data-ng-model="registro.status" value="V" required /> Válido </label><br />
						<label><input type="radio" name="statusPropriedade" id="statusPropriedadeInvalido" data-ng-model="registro.status" value="I" required /> Inválido </label><br />
						<p data-ng-show="frmCadastro.statusPropriedade.$invalid && (!frmCadastro.statusPropriedade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-12">
                         <div class="panel panel-default">
                             <div class="panel-heading">
                                 <h3 class="panel-title">Produtores Rurais / Moradores</h3>
                             </div>
                             <div class="panel-body">
                                 <div class="table-responsive text-center">
                                     <table class="table table-striped">
                                         <thead>
                                             <tr>
                                                 <th>#</th>
                                                 <th>Nome</th>
                                                 <th>Sexo</th>
                                                 <th>CPF</th>
                                                 <th>DAP</th>
                                                 <th>&nbsp;</th>
                                             </tr>
                                         </thead>
                                         <tbody>
                                             <tr data-ng-repeat="pessoa in registro.cadPessoaList">
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">{{$index + 1}}</td>
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">{{pessoa.nome}}</td>
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">{{(pessoa.sexo == "M") && "Masculino" || ""}}{{(pessoa.sexo == "F") && "Feminino" || ""}}</td>
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">{{pessoa.cpf}}</td>
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">{{pessoa.numeroDap}}</td>
                                                 <td data-ng-class="{statusInvalido: pessoa.status == 'I'}">
                                                     <button type="button" class="btn btn-warning btn-xs" data-ng-click="apoio.pessoaEditar(pessoa)" data-toggle="modal" data-target="#modalPessoa"  ><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                                     <button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.pessoaRemover(pessoa)" ><span class="glyphicon glyphicon-trash"></span></button>
                                                 </td>
                                             </tr>
                                         </tbody>
                                         <tfoot></tfoot>
                                     </table>
                                 </div>
                             </div>
                             <div class="panel-footer text-right">
                                 <button type="button" class="btn btn-success" data-ng-click="apoio.pessoaIncluir()" data-toggle="modal" data-target="#modalPessoa"> Incluir </button>
                             </div>
                         </div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal fade formulario" id="modalPessoa" tabindex="-1" data-role="dialog" data-aria-labelledby="Endereço" data-aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Cadastro de Produtor Rural ou Morador</h4>
			</div>
			<div class="modal-body" data-ng-class="{statusInvalido: apoio.pessoa.status == 'I'}">
				<form name="frmCadastroProdutor" novalidate>
					<input type="hidden" data-ng-value="apoio.pessoa.id" />
					<div class="row">
						<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastroProdutor.pessoaCpf.$invalid && (!frmCadastroProdutor.pessoaCpf.$pristine || pessoaSubmitted) }">
							<label class="control-label" for="pessoaCpf">CPF*</label>
							<input class="form-control" id="pessoaCpf" name="pessoaCpf" placeholder="CPF do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.cpf" data-ng-change="apoio.verificar = true" required />
							<p data-ng-show="frmCadastroProdutor.pessoaCpf.$invalid && (!frmCadastroProdutor.pessoaCpf.$pristine || pessoaSubmitted)" class="help-block">Campo Obrigatório!</p>
						</div>
						<div class="form-group col-md-1" data-ng-class="{ 'has-error' : frmCadastroProdutor.pessoaCpf.$invalid && (!frmCadastroProdutor.pessoaCpf.$pristine || pessoaSubmitted) }" data-ng-show="apoio.verificar">
							<br/><button class="btn btn-primary" data-ng-click="apoio.pessoaVerificar();">Verificar</button>
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-6" data-ng-class="{ 'has-error' : frmCadastroProdutor.pessoaNome.$invalid && (!frmCadastroProdutor.pessoaNome.$pristine || pessoaSubmitted) }">
							<label class="control-label" for="pessoaNome">Nome*</label>
							<input class="form-control" id="pessoaNome" name="pessoaNome" placeholder="Nome do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.nome" data-ng-required="true"/>
							<p data-ng-show="frmCadastroProdutor.pessoaNome.$invalid && (!frmCadastroProdutor.pessoaNome.$pristine || pessoaSubmitted)" class="help-block">Campo Obrigatório!</p>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaNascimento">Nascimento</label>
							<input class="form-control data" id="pessoaNascimento" placeholder="Data de Nascimento do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.nascimento" />
						</div>
						<div class="form-group col-md-3" data-ng-class="{ 'has-error' : frmCadastroProdutor.pessoaSexo.$invalid && (!frmCadastroProdutor.pessoaSexo.$pristine || pessoaSubmitted) }">
							<label class="control-label" for="pessoaSexo">Sexo*</label><br />
							<label><input type="radio" id="pessoaSexoMasculino" name="pessoaSexo" data-ng-model="apoio.pessoa.sexo" value="M" data-ng-required="true" /> Masculino </label>
							<label><input type="radio" id="pessoaSexoFeminino" name="pessoaSexo" data-ng-model="apoio.pessoa.sexo" value="F" data-ng-required="true" /> Feminino </label><br />
							<p data-ng-show="frmCadastroProdutor.pessoaSexo.$invalid && (!frmCadastroProdutor.pessoaSexo.$pristine || pessoaSubmitted)" class="help-block">Campo Obrigatório!</p>
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaNumeroInscricaoSefDf">Inscrição SEF DF</label>
							<input class="form-control" id="pessoaNumeroInscricaoSefDf" placeholder="Número de Inscrição SEF DF do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.numeroInscricaoSefDf" />
						</div>
						<div class="form-group col-md-9">
							<label class="control-label" for="pessoaNumeroDap">Número DAP</label>
							<input class="form-control" id="pessoaNumeroDap" placeholder="Número DAP do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.numeroDap" />
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-6">
							<label class="control-label" for="pessoaEndereco">Endereço</label>
							<input class="form-control" id="pessoaEndereco" placeholder="Endereço do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.endereco" />
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaBairro">Bairro</label>
							<input class="form-control" id="pessoaBairro" placeholder="Bairro do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.bairro" />
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaCidade">Cidade</label>
							<input class="form-control" id="pessoaCidade" placeholder="Cidade do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.cidade" />
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-5">
							<label class="control-label" for="pessoaUf">UF</label>
							<select class="form-control" id="pessoaUf" data-ng-model="apoio.pessoa.uf">
								<option data-ng-repeat="estado in apoio.estado" value="{{estado.sigla}}">{{estado.nome}}</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<label class="control-label" for="pessoaCep">CEP</label>
							<input class="form-control" id="pessoaCep" placeholder="CEP do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.cep" />
						</div>
						<div class="form-group col-md-2">
							<label class="control-label" for="pessoaCaixaPostal">Caixa Postal</label>
							<input class="form-control" id="pessoaCaixaPostal" placeholder="Caixa Postal do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.caixaPostal" />
						</div>
						<div class="form-group col-md-3">
							<div class="checkbox">
								<label>
									<input type="checkbox" id="pessoaResidePropriedade" data-ng-model="apoio.pessoa.residePropriedade" data-ng-true-value="S" data-ng-false-value="N" />Reside na Propriedade?
								</label>
							</div>
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaTelefone1">Telefone 1</label>
							<input class="form-control" id="pessoaTelefone1" placeholder="Telefone 1 do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.telefone1" />
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaTelefone2">Telefone 2</label>
							<input class="form-control" id="pessoaTelefone2" placeholder="Telefone 2 do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.telefone2" />
						</div>
						<div class="form-group col-md-6">
							<label class="control-label" for="pessoaEmail">E-mail</label>
							<input class="form-control" id="pessoaEmail" placeholder="E-mail do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.email" />
						</div>
					</div>
					<div class="row" data-ng-show="!apoio.verificar">
						<div class="form-group col-md-4">
							<label class="control-label" for="pessoaEscolaridade">Escolaridade</label>
							<select class="form-control" id="pessoaEscolaridade" data-ng-model="apoio.pessoa.escolaridade">
								<option data-ng-repeat="escolaridade in apoio.escolaridade" value="{{escolaridade.codigo}}">{{escolaridade.descricao}}</option>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="pessoaDataInformacoes">Data das Informações</label>
							<input class="form-control" id="pessoaDataInformacoes" placeholder="Data de Informações do Produtor Rural ou Morador" data-ng-model="apoio.pessoa.dataInformacoes" />
						</div>
						<div class="form-group col-md-5" data-ng-class="{ 'has-error' : frmCadastroProdutor.pessoaStatus.$invalid && (!frmCadastroProdutor.pessoaStatus.$pristine || pessoaSubmitted) }">
							<label class="control-label" for="status">Status do Produtor Rural ou Morador*</label><br />
							<label><input type="radio" name="pessoaStatus" id="pessoaStatusValido" data-ng-model="apoio.pessoa.status" value="V" required /> Válido </label>
							<label><input type="radio" name="pessoaStatus" id="pessoaStatusInvalido" data-ng-model="apoio.pessoa.status" value="I" required /> Inválido </label><br />
							<p data-ng-show="frmCadastroProdutor.pessoaStatus.$invalid && (!frmCadastroProdutor.pessoaStatus.$pristine || pessoaSubmitted)" class="help-block">Campo Obrigatório!</p>
						</div>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.pessoaLimpar()">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-ng-show="!apoio.verificar" data-ng-click="apoio.pessoaSalvar(frmCadastroProdutor.$valid)">Salvar</button>
			</div>
		</div>
	</div>
</div>

<script src="https://maps.googleapis.com/maps/api/js?v=3.9&sensor=false" charset="UTF-8"></script>
<script>
$(function(){
    $(".cpf").mask('000.000.000-00', {reverse: true});
//    $(".data").mask('00/00/0000');
    $(".cep").mask('00000-000');
});
</script>