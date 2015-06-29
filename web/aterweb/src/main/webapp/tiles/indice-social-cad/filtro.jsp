<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_filtro">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Filtro</h3>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" name="$parent.fltCadastro" novalidate>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<fieldset>
					<div class="form-group">
						<label class="col-md-4 control-label" for="nome">Nome</label>	
						<div class="col-md-4">
							<input id="nome" name="nome" type="text" placeholder="Nome da Pessoa" class="form-control input-md" data-ng-model="filtro.nome">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="nome">Identificação</label>	
						<div class="col-md-4">
							<input id="identificacao" name="identificacao" type="text" placeholder="Identificação da Pessoa" class="form-control input-md" data-ng-model="filtro.identificacao">
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>