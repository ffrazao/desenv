<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container-fluid">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Formulário</h3>
		</div>
		<div class="panel-body">
			<form name="$parent.formularioPessoa" novalidate>
				<fieldset>
					<div class="row text-center" data-ng-show="!registro.pessoaTipo">
						<h2>Cadastrar Pessoa</h2>
						<button class="btn btn-primary btn-lg" data-ng-click="incluirPessoa('PF')">Pessoa Física</button>
						<button class="btn btn-primary btn-lg" data-ng-click="incluirPessoa('PJ')">Pessoa Jurídica</button>
					</div>
					<div class="row" data-ng-show="registro.pessoaTipo">
						<div class="col-md-2">
							<div class="form-group">
								<label class="control-label">Foto</label>
								<div>
									<img ng-src="./resources/img/pessoa_padrao.png" class="img-thumbnail">
								</div>
							</div>
						</div>
						<div class="col-md-10">
							<div class="row">
								<div class="form-group col-md-3">
									<label class="control-label" for="tipoPessoa">Natureza</label>
									<div class="form-control">
										{{registro.pessoaTipo === 'PF' ? 'Pessoa Física' : registro.pessoaTipo === 'PJ' ? 'Pessoa Jurídica': ''}}
									</div>
								</div>
								<div class="form-group col-md-6">
									<label class="control-label">Nome Completo</label>
									<input class="form-control" type="text" ng-model="registro.nome"/> 
								</div>
								<div class="form-group col-md-3">
									<label class="control-label">Apelido</label>
									<input class="form-control" type="text" ng-model="registro.apelidoSigla"/> 
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-3">
									<label class="control-label">Situação</label> 
									<select class="form-control" data-ng-model="registro.situacao" id="situacao" name="situacao" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList" required>
									</select>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label">CPF</label>
									<input class="form-control" type="text" ng-model="registro.cpf"/> 
								</div>
							</div>
						</div>
					</div>
					<div class="container-fluid" data-ng-show="registro.pessoaTipo">
						<tabset data-ng-show="registro.pessoaTipo">
							<tab ng-repeat="tab in tabs | filter: {'visivel': true}" active="tab.ativa" disabled="tab.desabilitada">
								<tab-heading>
									{{$index + 1}} {{tab.nome}} <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								</tab-heading>
								<br/>
								<ng-include src="tab.include"/>
							</tab>
						</tabset>
					</div>
					<div class="row" data-ng-show="registro.pessoaTipo">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label" for="tipoPessoa">Observações</label>
								<textarea class="form-control" rows="4" data-ng-model="registro.observacoes"></textarea>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group col-md-8">
								<label class="control-label" for="tipoPessoa">Resp. Inclusão</label>
								<input class="form-control" type="text" readonly="true" />
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="tipoPessoa">Dt. Inclusão</label>
								<input class="form-control" type="text" readonly="true" />
							</div>
							<div class="form-group col-md-8">
								<label class="control-label" for="tipoPessoa">Resp. Atualização</label>
								<input class="form-control" type="text" readonly="true" />
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="tipoPessoa">Dt. Atualização</label>
								<input class="form-control" type="text" readonly="true" />
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>

<!--


					<div class="row" data-ng-show="registro.pessoaTipo">
						<div class="form-group col-md-12 text-center">
							<label class="control-label">{{registro.nome}}</label>
							<label class="control-label" data-ng-show="registro.nome.length && (registro.cpf.length || registro.cnpj.length)"> - </label>
							<label class="control-label" data-ng-show="registro.pessoaTipo === 'PF'">{{registro.cpf}}</label>
							<label class="control-label" data-ng-show="registro.pessoaTipo === 'PJ'">{{registro.cnpj}}</label>
						</div>
					</div>
					<div class="row sr-only" data-ng-show="registro.pessoaTipo">
						<div class="form-group col-md-8">
							<label for="nome" class="control-label" data-ng-show="registro.pessoaTipo === 'PF'">Nome</label>
							<label for="nome" class="control-label" data-ng-show="registro.pessoaTipo === 'PJ'">Razão Social</label>
							<input type="text" class="form-control" data-ng-disable="true" ng-model="registro.nome" readonly="true">
						</div>
						<div class="form-group col-md-4">
							<label for="nome" class="control-label" data-ng-show="registro.pessoaTipo === 'PF'">CPF</label>
							<input type="text" class="form-control" data-ng-disable="true" ng-model="registro.cpf" readonly="true" data-ng-show="registro.pessoaTipo === 'PF'">
							<label for="nome" class="control-label" data-ng-show="registro.pessoaTipo === 'PJ'">CNPJ</label>
							<input type="text" class="form-control" data-ng-disable="true" ng-model="registro.cnpj" readonly="true" data-ng-show="registro.pessoaTipo === 'PJ'">
						</div>
					</div>
				
					<div class="row" style="background-color: blue;" data-ng-show="registro.pessoaTipo">
						<tabset>
							<tab ng-repeat="tab in tabs | filter: {'visivel': true}">
								<tab-heading>
									{{$index + 1}} {{tab.nome}} <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								</tab-heading>
								<ng-include src="tab.include"/>
							</tab>
						</tabset>
					</div>




















 
<div class="modal fade" id="endereco" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 90%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Endereço</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="$parent.endereco" novalidate>
					<endereco data-dados="enderecoK" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="salvarEnderecoCancelar" data-ng-click="alert(2)">Fechar</button>
				<button type="button" class="btn btn-primary" id="salvarEndereco" data-ng-click="salvarEndereco()">Salvar</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="modalMapa">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">MAPA</h4>
			</div>
			<div class="modal-body">
				<br/>
				<p><div id="map-canvas"></div></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				<button type="button" class="btn btn-default">Google Maps</button>
			</div>
		</div>
	</div>
</div>

<script src="https://maps.googleapis.com/maps/api/js?v=3.9&sensor=false" charset="UTF-8"></script>

<script src="resources/js/libs/jquery-ui.custom.js" charset="UTF-8"></script>
<script src="resources/js/libs/jquery.cookie.js" charset="UTF-8"></script>
<script>
	$(document).ready(function() {
		atualizaMascara()
	});

	$("#btnInserirJuridica").click(function() {
		atualizaMascara()
	});

	$("#btnInserirFisica").click(function() {
		atualizaMascara()
	});

	function atualizaMascara() {
		setTimeout(function() {
			$(".cpf").mask('000.000.000-00', {
				reverse : true
			});
			$(".cnpj").mask('00.000.000/0000-00', {
				reverse : true
			});
			$(".data").mask('00/00/0000');
						$(".cep").mask('00000-000');
					}, 1000);
				}
				
				setTimeout(function() {
					$(".ttip").tooltip();
				}, 800);	
				
				
				//$("#pessoaGrupoTree").dynatree({get
				//    autoFocus: true,
				//    selectMode: 3,
				//    checkbox: true,
				//    minExpandLevel: 2,
				//    classNames: {checkbox: "dynatree-checkbox"},
				//    onActivate: function(node) {
				//        $scope.selecionado = node.data.valor;
				//        $scope.$digest();
				//    }
				//});
			</script>
 -->