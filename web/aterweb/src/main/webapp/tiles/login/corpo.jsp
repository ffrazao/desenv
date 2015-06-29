<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<br>
<br>
<br>
<div class="container" data-ng-controller="LoginCtrl">
	<div class="col-md-6 col-md-offset-3">
		<form action="<c:url value='j_spring_security_check' />" method="POST">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<div class="panel panel-primary" style="background-color: rgba(255,255,255, 0.2) !important; border: 1px solid rgba(255,255,255,0.5) !important;">
				<div style="width: 95%; height: 95%; float:left; position:absolute; background-color:rgba(255,255,255, 0.3); -webkit-filter: blur(10px) !important; z-index:-1000 !important;"><br /><br /><br /><br /><br /></div>
				<form:errors />
				<div class="panel-body">
					<br />
					<div class="text-center" style="margin-left: -30px;">
						<img width="320" height="100"
						src='<spring:url value="resources/img/logo.gif"/>' />
					</div>
					<br /> <br />
					<c:if
						test="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message != null}">
						<div class="alert alert-warning">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>
					</c:if>
					<c:if test="${param.logout != null}">
						<div class="alert alert-danger">Você foi desconectado!</div>
					</c:if>
					<div id="msgServidor"></div>
					<div class="form-group">
						<div class="input-group col-md-8 col-md-offset-2 col-xs-offset-2 col-xs-8">
							<input type="text" class="form-control" id="username" name="j_username"
								placeholder="Nome do Usuário" data-ng-model="nomeUsuario">
						</div>
						<br><br>
						<div class="input-group col-md-8 col-md-offset-2 col-xs-offset-2 col-xs-8">
							<input type="password" class="form-control" id="password"
								name="j_password" placeholder="Senha" data-ng-model="senha">
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-md-5 col-md-offset-2 col-xs-offset-2 col-xs-5">
							<input type="checkbox" name="administrador" data-ng-checked="administradorChecked" value="true">
							<label>Administrador</label>
						</div>
						<div class="col-md-3 col-xs-3">
							<button type="submit" class="btn btn-success btn-lg">
								<b>Entrar</b> <span class="glyphicon glyphicon-chevron-right"></span>
							</button>
						</div>
					</div>
					<br /> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
				<div class="panel-footer text-right"
					style="background-color: rgba(255, 255, 255, 0.2) !important; border: 1px solid rgba(255, 255, 255, 0.5) !important;">
					<div class="text-center">
						<a href="#" class="btn-mudar-senha" onclick="$('#modalMudarSenhaAtual').modal('show');return false;">Mudar a Senha Atual</a> | 
						<a href="#"	onclick="$('#modalEsqueciMinhaSenha').modal('show');return false;">Esqueci Minha Senha</a>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal fade" id="modalMudarSenhaAtual">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">Mudar Sua Senha Atual</h4>
				</div>

				<div class="modal-body">
					<form>
						<div class="panel-body">
							<div class="form-group">
								<label for="id_nomeUsuario">Usuário:</label>
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-user"></span></span> <input type="text"
										class="form-control" id="id_nomeUsuario" name="nomeUsuario"
										placeholder="Nome do Usuário" data-ng-model="nomeUsuario">
								</div>
							</div>
							<div class="form-group">
								<label for="id_senha">Senha Atual</label>
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-asterisk"></span></span> <input
										type="password" class="form-control" id="id_senha"
										name="senha" placeholder="Senha Atual" data-ng-model="senha">
								</div>
							</div>
							<div class="form-group">
								<label for="id_novaSenha">Nova Senha</label>
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-asterisk"></span></span> <input
										type="password" class="form-control" id="id_novaSenha"
										name="novaSenha" placeholder="Nova Senha" data-ng-model="novaSenha">
								</div>
							</div>
							<div class="form-group">
								<label for="id_repetirNovaSenha">Repetir Nova Senha</label>
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-asterisk"></span></span> <input
										type="password" class="form-control" id="id_repetirNovaSenha"
										name="repetirNovaSenha" placeholder="Repetir Nova Senha"
										data-ng-model="repetirNovaSenha">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<span
						class="conferido-{{mudarSenhaAtualPronto()? 'true': 'false'}}">({{mudarSenhaAtualPronto()?
						'Conferido': 'Não Conferido'}})</span>
					<button type="submit" class="btn btn-success"
						data-ng-click="mudarSenhaAtual()"
						data-ng-disabled="!mudarSenhaAtualPronto()">
						OK <span class="glyphicon glyphicon-flash"></span>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modalEsqueciMinhaSenha">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn btn-danger"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">Esqueci Minha Senha</h4>
				</div>
				<form>
					<div class="modal-body">
						<br>
						<p class="text-center">Entre em contato com a GETIN 3311-9350
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	function LoginCtrl($scope,toaster) {
		toaster.options = {positionClass : "toast-bottom-right"};
		$scope.nomeUsuario = "${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}".split(' |')[0]
		if("${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}".indexOf("Crie uma senha para o usuário") > 0) {
			$(".btn-mudar-senha").click();
			//$("#id_senha").parent().parent().hide();
		}
		//$scope.senha = "a";
		
		//$scope.nomeUsuario = usuario;
		$scope.administradorChecked = false;
		$scope.novaSenha = "";
		$scope.repetirNovaSenha = "";

		$scope.mudarSenhaAtualPronto = function() {
			if (isUndefOrNull($scope.nomeUsuario)
					|| isUndefOrNull($scope.novaSenha)
					|| isUndefOrNull($scope.repetirNovaSenha)) {
				return false;
			}
			return $scope.nomeUsuario.length > 0
			    && $scope.novaSenha.length > 0 
			    && $scope.novaSenha == $scope.repetirNovaSenha;
		}
		$scope.mudarSenhaAtual = function() {
			//alert(JSON.stringify($("#modalMudarSenhaAtual").find('form').serialize()));

			$.ajax({
				async : false,
				type : 'POST',
				contentType : 'application/json',
				url : baseUrl + "mudarSenhaAtual",
				dataType : 'json',

				data : '{"nomeUsuario": "' + $scope.nomeUsuario
						+ '", "senha": "' + $scope.senha + '", "novaSenha": "'
						+ $scope.novaSenha + '", "repetirNovaSenha": "'
						+ $scope.repetirNovaSenha + '"}'
				,
				success : function(data) {
					if (data.executou === true) {
						toaster.pop('info', null, "Salvo com sucesso");
						$scope.senha = angular.copy($scope.novaSenha);
						$scope.novaSenha = "";
						$scope.repetirNovaSenha = "";
						$("#modalMudarSenhaAtual").modal('hide');
					} else {
						toaster.pop('error', "ERRO", "Senha atual invalida. Ou usuário não localizado");
					}
					console.log(data);
				}
			});
		};
	}
</script>