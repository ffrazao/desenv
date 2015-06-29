<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
                <th width="250">Nome</th>
                <th>Perfil</th>
            </tr>
        </thead>
        <tbody id="listagem">
			<tr data-ng-click="selecionado(linha, $event)" data-ng-repeat="linha in lista">
                <td>
                    <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                    <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                </td>
				<td>{{linha.nome}}</td>
				<td><span ng-repeat="perfis in linha.moduloPerfilList">{{perfis.nome}} - </span> </td>
			</tr>
        </tbody>
    </table>
</div>
		</div>
	</div>
</div>