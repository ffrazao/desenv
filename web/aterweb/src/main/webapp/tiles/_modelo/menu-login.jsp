<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<ul class="nav navbar-nav navbar-right">
		<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Bem vindo, <b><sec:authentication property="name" /></b> <span class="caret"></span></a>
			<ul class="dropdown-menu" role="menu">
				<li><a href="#">Meu Perfil</a></li>
				<li><a href="#">Alterar Senha</a></li>
				<li class="divider"></li>
				<li>
					<form class="navbar-form form-inline">
						<label class="control-label">Módulo</label>
						<div class="input-group" style="width: 300px">
							<select class="form-control" data-ng-model="moduloAcesso" data-ng-init="moduloAcesso = 'Principal'">
								<option value="Principal">Principal</option>
								<option value="Compras">Compras</option>
								<option value="Crédito Rural">Crédito Rural</option>
								<option value="Funcional">Funcional</option>
								<option value="Institucinal">Institucinal</option>
								<option value="Orçamento">Programação e Orçamento</option>
								<option value="Patrimônio">Patrimônio</option>
							</select>
							<span class="input-group-btn">
								<button id="pesquisaTextualBtn" class="btn btn-default" type="submit"><i class="glyphicon glyphicon-ok"></i></button>
							</span>
						</div>
					</form>
				</li>
				<li class="divider"></li>
				<li>
					<form class="navbar-form form-inline" id="formLogout">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> &nbsp; 
						<input class="btn btn-danger btn-sm pull-right" type="button" onclick="executarLogout()" value="Sair" style="margin-top: 0px;" />
					</form>
				</li>
			</ul>
		</li>
	</ul>
	<img class="navbar-right" src="<sec:authentication property="principal.pessoa.fotoPerfil" />" alt="Foto" height="50px" width="50px"/>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	<p class="navbar-text navbar-right"><a href="login" class="navbar-link">Efetuar o Login</a></p>
</sec:authorize>