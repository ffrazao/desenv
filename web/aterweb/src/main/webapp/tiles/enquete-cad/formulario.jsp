<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="div_formulario">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Formulário</h3>
		</div>
		<div class="panel-body">
<form action="pessoa-cad" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<table>
		<tr>
			<td>id:</td>
			<td><input name="id" /></td>
			<td></td>
		</tr>
		<tr>
			<td>nome:</td>
			<td><input name="login" /></td>
			<td></td>
		</tr>
		<tr>
			<td>identificação:</td>
			<td><input name="nome" /></td>
			<td></td>
		</tr>
		<tr>
			<td>ações:</td>
			<td><input name="senha" /></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" name="action"
				value="salvar"></td>
		</tr>
	</table>
</form>
		</div>
	</div>
</div>