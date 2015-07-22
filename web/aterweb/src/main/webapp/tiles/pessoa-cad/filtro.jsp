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
						<label class="col-md-4 control-label" for="categoria">Categoria</label>
						<div class="col-md-4">
							<select class="form-control" id="categoria" name="categoria" data-ng-model="filtro.categoria" data-ng-options="item.codigo as item.descricao for item in apoio.publicoAlvoCategoriaList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="ater">ATER</label>
						<div class="col-md-4">
							<select class="form-control" id="ater" name="ater" data-ng-model="filtro.ater" data-ng-options="item.codigo as item.descricao for item in apoio.confirmacaoList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="dap">DAP</label>
						<div class="col-md-4">
							<select class="form-control" id="dap" name="dap" data-ng-model="filtro.dap" data-ng-options="item.codigo as item.descricao for item in apoio.situacaoDapList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="genero">Gênero</label>
						<div class="col-md-4">
							<select class="form-control" id="genero" name="genero" data-ng-model="filtro.genero" data-ng-options="item.codigo as item.descricao for item in apoio.sexoList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="geracao">Geração</label>
						<div class="col-md-4">
							<select class="form-control" id="geracao" name="geracao" data-ng-model="filtro.geracao" data-ng-options="item.codigo as item.descricao for item in apoio.geracaoList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="setor">Setor</label>
						<div class="col-md-4">
							<select class="form-control" id="setor" name="setor" data-ng-model="filtro.setor" data-ng-options="item.id as item.nome for item in apoio.setorList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="ativo">Ativo</label>
						<div class="col-md-4">
							<select class="form-control" id="ativo" name="ativo" data-ng-model="filtro.ativo" data-ng-options="item.codigo as item.descricao for item in apoio.pessoaSituacaoList">
								<option value="">*** Ignorar ***</option>
							</select>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>