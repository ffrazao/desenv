<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="div_filtro">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Filtro</h3>
		</div>
		<div class="panel-body">
<form action="usuario-cad/filtro" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<div class="col-md-4"></div>
	<div class="col-md-4">
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-italic"></i></span> <input class="form-control"
				id="filtro_nome" name="nome" placeholder="Nome" />
		</div>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input class="form-control"
				id="filtro_nome_usuario" name="nomeUsuario"
				placeholder="Nome do Usuário" />
		</div>
	</div>
	<div class="col-md-12 hide">
		<br />
		<div class="text-center">
			<button type="button" id="btn_filtro_executar"
				class="btn btn-success" value="Filtrar">Executar</button>
		</div>
	</div>
</form>
		</div>
	</div>
</div>