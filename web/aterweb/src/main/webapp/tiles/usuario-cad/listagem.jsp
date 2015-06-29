<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="div_listagem">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Listagem</h3>
		</div>
		<div class="panel-body">
<div>
	<table class="table table-striped table-bordered table-hover" hover>
	<thead>
		<tr>
			<th width="10">
			<button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs btnTrocar" data-toggle="tooltip" data-placement="top" title="Selecionar Multiplos">&#9737;</button>
			</th>
            <th>Nome</th>
			<th>Nome do Usuário</th>
			<th>Situação</th>
            <th>Módulo(s)</th>
            <th>Perfil(is)</th>
		</tr>
	</thead>

	<tbody id="listagem">
		<tr data-ng-click="selecionado(linha, $event)" data-ng-repeat="linha in lista">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
				<td>{{linha.nome}}</td>
				<td>{{linha.nomeUsuario}}</td>
				<td>{{linha.usuarioStatusConta}}</td>
				<td><span ng-repeat="modulos in linha.usuarioModuloList">{{modulos.nome}} - </span></td>
				<td><span ng-repeat="perfis in linha.usuarioPerfilList">{{perfis.nome}} - </span> </td>
			</tr>
	</tbody>
</table>

</div>
		</div>
	</div>
</div>