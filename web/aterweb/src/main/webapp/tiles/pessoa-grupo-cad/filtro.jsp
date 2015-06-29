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
						<label class="col-md-4 control-label" for="nome">Nome do Grupo Social</label>	 
						<div class="col-md-4">
							<input id="nome" name="nome" type="text" placeholder="Nome do Grupo Social" class="form-control input-md" data-ng-model="filtro.nome">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="gestorGrupo">Nível de Gestão do Grupo Social</label>
						<div class="col-md-4">
							<label>
								<input type="checkbox" name="gestorGrupoTodosCheck" data-ng-checked="estaoTodosSelecionados()" data-ng-click="selecionaTodos()"/> Todos 
							</label> <br />
							<label>
								<input type="checkbox" name="gestorGrupoTecnicoCheck" data-ng-model="filtro.gestorGrupoTecnicoCheck" data-ng-click="estaoTodosSelecionados()" />
								<img src="resources/img/grupo-tecnico.png" height="35px" width="35px"/>
								Técnico
							</label><br /> 
							<label>
								<input type="checkbox" name="gestorGrupoUnidadeOrganizacionalCheck" data-ng-model="filtro.gestorGrupoUnidadeOrganizacionalCheck" data-ng-click="estaoTodosSelecionados()" />
								<img src="resources/img/grupo-unidade-organizacional.png" height="35px" width="35px"/>
								Unidade Organizacional
							</label><br /> 
							<label>
								<input type="checkbox" name="gestorGrupoInstituicaoCheck" data-ng-model="filtro.gestorGrupoInstituicaoCheck" data-ng-click="estaoTodosSelecionados()" /> 
								<img src="resources/img/grupo-instituicao.png" height="35px" width="35px"/>
								Instituição
							</label> 
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="situacaoGrupo">Situação</label>	 
						<div class="col-md-4">
							<label>
								<input type="radio" name="situacaoGrupo" data-ng-model="filtro.situacaoGrupo" value="">Todos
							</label><br>
							<label>
								<input type="radio" name="situacaoGrupo" data-ng-model="filtro.situacaoGrupo" value="A">Ativos
							</label><br>
							<label>
								<input type="radio" name="situacaoGrupo" data-ng-model="filtro.situacaoGrupo" value="O">Inativos
							</label>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>