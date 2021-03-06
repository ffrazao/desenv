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
						<label class="col-md-4 control-label" for="endereco">Endereço</label>	
						<div class="col-md-4">
							<input id="endereco" name="endereco" type="text" placeholder="Endereço da Propriedade" class="form-control input-md" data-ng-model="filtro.nome">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="comunidade">Comunidade</label>
						<div class="col-md-4">
							<select id="comunidade" name="comunidade" class="form-control comunidades" data-ng-model="filtro.comunidade.id" data-ng-options="comunidades.id as comunidades.nome for comunidades in selectComunidades">
								<option value="">Sem Filtro</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="sistemaProducao">Sistema de Produção</label>
						<div class="col-md-4">
							<select id="sistemaProducao" name="sistemaProducao" class="form-control" data-ng-model="filtro.sistemaProducao" data-ng-options="sistemaProducao.id as sistemaProducao.nome for sistemaProducao in selectSistemaProducao">
								<option value="">Sem Filtro</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="situacaoFundiaria">Situação Fundiária</label>
						<div class="col-md-4">
							<select id="situacaoFundiaria" name="situacaoFundiaria" class="form-control" data-ng-model="filtro.situacaoFundiaria">
								<option value="">Sem Filtro</option>
								<option value="E">Escritura Definitiva</option>
								<option value="C">Concessão de Uso</option>
								<option value="P">Posse</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="outorga">Outorga</label>
						<div class="col-md-4">
							<select id="outorga" name="outorga" class="form-control" data-ng-model="filtro.outorga">
								<option value="">Sem Filtro</option>
								<option value="S">Sim</option>
								<option value="N">Não</option>
							</select>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>