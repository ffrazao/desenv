<!-- Fraz�o Inicio Grupo Social -->
<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">Grupos Sociais</h4>
		<div class="text-right bt-topo">
			<label>Filtrar N�vel de Gest�o:</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Todos</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> T�cnico</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Unidade Organizacional</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Institucional</label>
			<button class="btn btn-success btn-xs ttip" data-toggle="modal" data-target="#modalGrupoSocial" data-ng-click="apoio.pessoaGrupoIncluir()" data-original-title="Incluir">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
		</div>
	</div>
	<div class="panel-body">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Nome</th>
						<th colspan="3">N�vel Gest�o</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="grupo in registro.pessoaGrupoSocialList">
						<td>{{grupo.pessoa.nome}}</td>
						<td>{{grupo.nivelGestao}}</td>
						<td>
							<button class="btn btn-warning btn-xs ttip" data-original-title="Editar" data-toggle="modal" data-target="#modalGrupoSocial" data-ng-click="acoes.grupoSocial.editar(grupo)">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>
						</td>
						<td>
							<button class="btn btn-danger btn-xs ttip" data-original-title="Remover" data-ng-click="acoes.grupoSocial.remover(grupo)">
								<span class="glyphicon glyphicon-trash"></span>
							</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- Fraz�o Fim Grupo Social -->
