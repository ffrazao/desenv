<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<table border="1" data-ng-controller="cadastroCtrl">
	<tr>
		<td>formulario_codigo</td>
		<td>tipo_avaliacao</td>
		<td>avaliador_empresa</td>
		<td>avaliador_matricula</td>
		<td>avaliador_nome</td>
		<td>avaliado_matricula</td>
		<td>avaliado_nome</td>
		<td>p01</td>
		<td>p02</td>
		<td>p03</td>
		<td>p04</td>
		<td>p05</td>
		<td>p06</td>
		<td>p07</td>
		<td>p08</td>
		<td>p09</td>
		<td>p10</td>
		<td>p11</td>
		<td>p12</td>
		<td>que_bom</td>
		<td>poderia_melhorar</td>
		<td>que_tal</td>
	</tr>
	<tr data-ng-repeat="f in avaliacoesDados">
		<td>{{f.formulario_codigo}}</td>
		<td>{{f.tipo_avaliacao}}</td>
		<td>{{f.avaliador_empresa}}</td>
		<td>{{f.avaliador_matricula}}</td>
		<td>{{f.avaliador_nome}}</td>
		<td>{{f.avaliado_matricula}}</td>
		<td>{{f.avaliado_nome}}</td>
		<td>{{f.p01}}</td>
		<td>{{f.p02}}</td>
		<td>{{f.p03}}</td>
		<td>{{f.p04}}</td>
		<td>{{f.p05}}</td>
		<td>{{f.p06}}</td>
		<td>{{f.p07}}</td>
		<td>{{f.p08}}</td>
		<td>{{f.p09}}</td>
		<td>{{f.p10}}</td>
		<td>{{f.p11}}</td>
		<td>{{f.p12}}</td>
		<td>{{f.que_bom}}</td>
		<td>{{f.poderia_melhorar}}</td>
		<td>{{f.que_tel}}</td>
	</tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/tiles/enquete-avaliacao/script.js" charset="UTF-8"></script>
