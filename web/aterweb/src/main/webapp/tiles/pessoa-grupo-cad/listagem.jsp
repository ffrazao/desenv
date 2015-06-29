<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="div_listagem">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Listagem</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="form-group">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th width="10">
									<button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
								</th>
								<th>
									Nome
								</th>
							</tr>
						</thead>
					</table>
					<script type="text/ng-template" id="items_renderer.html" charset="UTF-8">
						<div data-ui-tree-handle>
							<a class="btn btn-primary btn-xs Tooltip" data-nodrag data-ng-click="toggleChildren(this)" title="Expandir/Recolher">
								<span class="glyphicon glyphicon-chevron-right" data-ng-show="collapsed"></span>
								<span class="glyphicon glyphicon-chevron-down" data-ng-show="!collapsed"></span>
							</a>
							<label>
								<input class="Tooltip" title="Seleção unitária" type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
								<input class="Tooltip" title="Seleção múltipla" type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
								<img src="resources/img/grupo-tecnico.png" height="35px" width="35px" data-ng-show="linha.nivelGestao=='T'"/>
								<img src="resources/img/grupo-unidade-organizacional.png" height="35px" width="35px" data-ng-show="linha.nivelGestao=='U'"/>
								<img src="resources/img/grupo-instituicao.png" height="35px" width="35px" data-ng-show="linha.nivelGestao=='I'"/>
								{{linha.nome}}
							</label>
							<a class="pull-right btn btn-danger btn-xs Tooltip" data-nodrag data-ng-click="remove(this)" data-ng-show="organizando" title="Excluir">
								<span class="glyphicon glyphicon-remove"></span>
							</a>
							<a class="pull-right btn btn-success btn-xs" data-nodrag data-ng-click="newSubItem(this)" style="margin-right: 8px;" data-ng-show="false">
								<span class="glyphicon glyphicon-plus"></span>
							</a>
						</div>
						<ol data-ui-tree-nodes="treeOptions" data-ng-model="linha.filhos" data-ng-class="{hidden: collapsed}">
							<li data-ng-repeat="linha in linha.filhos | orderBy:'nome':reverse" data-ui-tree-node data-ng-include="'items_renderer.html'" data-collapsed="false">
							</li>
						</ol>
					</script>
					<div data-ui-tree="treeOptions" data-spacing="2" data-drag-enabled="organizando" id="admin-tree-root">
						<ol data-ui-tree-nodes data-ng-model="lista">
							<li data-ng-repeat="linha in lista | orderBy:'nome':reverse" data-ui-tree-node data-ng-include="'items_renderer.html'" data-collapsed="false"></li>
						</ol>
					</div>
					<button data-ng-click="newSubItem(null)" data-ng-show="false">Inserir</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
setTimeout(function() {
    $(".Tooltip").tooltip();
}, 800);
</script>