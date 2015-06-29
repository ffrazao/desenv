<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_filtro">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Filtro</h3>
        </div>
        <div class="panel-body">
			<form class="form-horizontal" name="$parent.fltCadastro" novalidate>
				<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#endereco">Endereço</button> -->
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<fieldset>
					<div class="form-group">
						<label class="col-md-4 control-label" for="codigo">Código da Atividade</label>
						<div class="col-md-2">                     
							<input type="text" class="form-control text-center codigoAtividade" id="codigo" name="codigo" data-ng-model="filtro.codigo" data-ui-mask="****.****-9">
						</div>
					</div>
				</fieldset>
				<fieldset data-ng-show="!filtro.codigo.length">
					<legend>Ou</legend>
					<div class="form-group">
						<label class="col-md-4 control-label" for="periodo">Período</label>  
						<div class="col-md-4">
							<div class="row">
								<div class="col-md-6 has-feedback" data-ng-class="{ 'statusInvalido' : fltCadastro.inicioFiltro.$invalid && (!fltCadastro.inicioFiltro.$pristine || submitted) }">
									<label class="control-label" for="inicioFiltro">Início</label>
									<input type="text" class="form-control" id="inicioFiltro" name="inicioFiltro" readonly="readonly" 
										datepicker-popup="dd/MM/yyyy" data-ng-model="filtro.inicio" 
										ng-click="inicioFiltro.isOpen=true" is-open="inicioFiltro.isOpen"
										required="required" max-date="filtro.termino">										
									<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
									<p data-ng-show="fltCadastro.inicioFiltro.$invalid && (!fltCadastro.inicioFiltro.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
								<div class="col-md-6 has-feedback" data-ng-class="{ 'statusInvalido' : fltCadastro.terminoFiltro.$invalid && (!fltCadastro.terminoFiltro.$pristine || submitted)}">
									<label class="control-label" for="terminoFiltro">Término</label>
									<input type="text" class="form-control" id="terminoFiltro" name="terminoFiltro" readonly="readonly" 
										datepicker-popup="dd/MM/yyyy" data-ng-model="filtro.termino" 
										ng-click="terminoFiltro.isOpen=true" is-open="terminoFiltro.isOpen"
										required="required" min-date="filtro.inicio">
									<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
									<p data-ng-show="fltCadastro.terminoFiltro.$invalid && (!fltCadastro.terminoFiltro.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Pessoa(s) Envolvida(s)</label>
						<div class="col-md-7">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-6">
											<h3 class="panel-title">Pessoa</h3>
										</div>
										<div class="col-md-6" data-ng-show="filtro.pessoaList.length">
											<div class="btn-group">
												<input type="search" placeholder="filtro" data-ng-model="apoio.filtroPessoa" data-ng-change="filtroPessoaPagina=1"/>
												<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.filtroPessoa = null"></span>
											</div>
										</div>
									</div>
								</div>
								<div class="table-responsive" data-ng-show="filtro.pessoaList.length">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Nome</th>
												<th>Documento</th>
												<th>Função</th>
												<th>Junção</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="item in filtro.pessoaList | filter: apoio.filtroPessoa | offset : filtroPessoaPagina : tamanhoPagina | limitTo: tamanhoPagina"
												data-ng-class="{ 'warning' : item.juncao == 'O' }">
												<td>{{$index + 1 + ((filtroPessoaPagina-1)*tamanhoPagina)}}</td>
												<td>{{item.pessoa.nome}}</td>
												<td>{{item.pessoa.cpf}}</td>
												<td>
													<select class="form-control" id="funcao" name="funcao" data-ng-model="item.funcao" data-ng-options="item.codigo as item.descricao for item in apoio.filtroFuncaoList"></select>
												</td>
												<td>
													<button type="button" class="btn btn-primary" 
														data-ng-model="item.juncao" 
														btn-checkbox 
														btn-checkbox-true="'O'" btn-checkbox-false="'E'"
														data-ng-show="($index + 1 + ((filtroPessoaPagina-1)*tamanhoPagina)) !== filtro.pessoaList.length">
														{{item.juncao === 'E'? 'E': 'Ou'}}
													</button>
												</td>
												<td>
													<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.filtroPessoaRemover(item)"><span class="glyphicon glyphicon-trash"></span></button>
												</td>
											</tr>
										</tbody>
										<tfoot data-ng-show="filtro.pessoaList.length > tamanhoPagina">
											<tr>
												<td colspan="4">
													<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
														data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
														data-total-items="filtro.pessoaList.length"
														data-ng-model="filtroPessoaPagina" data-ng-init="filtroPessoaPagina = 1"></pagination>
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
								<div class="panel-footer text-right">
									<label>Total: {{filtro.pessoaList.length}}</label>
									<div class="btn-group">
										<button type="button" class="btn btn-success pull-left" data-ng-click="apoio.filtroPessoaIncluir()">Incluir</button>
										<button type="button" class="btn btn-success dropdown-toggle pull-left" data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span>
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<ul class="dropdown-menu pull-left" role="menu" style="right: 0; left: auto;">
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroPessoaIncluir();"> Alguém </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroPessoaIncluirElemento(usuarioLogado.pessoa, 'Q')"> Me Incluir </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroPessoaIncluirElemento(null, 'D')"> Minha Unidade </a>
											</li>
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguém da Minha Unidade </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroPessoaIncluirElemento(null, 'D')"> Minha Institução </a>
											</li>
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguma Unidade da Minha Instituição </a>
											</li class="sr-only">
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguém da Minha Instituição </a>
											</li>
										</ul>
									</div>
									<button type="button" class="btn btn-danger" data-ng-click="apoio.filtroPessoaListInit()" data-ng-show="filtro.pessoaList.length"> Limpar </button>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Grupo(s) Social(ais)</label>
						<div class="col-md-7">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-6">
											<h3 class="panel-title">Grupo Social</h3>
										</div>
										<div class="col-md-6" data-ng-show="filtro.grupoSocialList.length">
											<div class="btn-group">
												<input type="search" placeholder="filtro" data-ng-model="apoio.filtroGrupoSocial" data-ng-change="filtroGrupoSocialPagina=1"/>
												<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.filtroGrupoSocial = null"></span>
											</div>
										</div>
									</div>
								</div>
								<div class="table-responsive" data-ng-show="filtro.grupoSocialList.length">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Nome</th>
												<th>Sigla</th>
												<th>Função</th>
												<th>Junção</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="item in filtro.grupoSocialList | filter: apoio.filtroGrupoSocial | offset : filtroGrupoSocialPagina : tamanhoPagina | limitTo: tamanhoPagina"
												data-ng-class="{ 'warning' : item.juncao == 'O' }">
												<td>{{$index + 1 + ((filtroGrupoSocialPagina-1)*tamanhoPagina)}}</td>
												<td>{{item.pessoa.nome}}</td>
												<td>{{item.pessoa.apelidoSigla}}</td>
												<td>
													<select class="form-control" id="funcao" name="funcao" data-ng-model="item.funcao" data-ng-options="item.codigo as item.descricao for item in apoio.filtroFuncaoList"></select>
												</td>
												<td>
													<button type="button" class="btn btn-primary" 
														data-ng-model="item.juncao" 
														btn-checkbox 
														btn-checkbox-true="'O'" btn-checkbox-false="'E'"
														data-ng-show="($index + 1 + ((filtroGrupoSocialPagina-1)*tamanhoPagina)) !== filtro.grupoSocialList.length">
														{{item.juncao === 'E'? 'E': 'Ou'}}
													</button>
												</td>
												<td>
													<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.filtroGrupoSocialRemover(item)"><span class="glyphicon glyphicon-trash"></span></button>
												</td>
											</tr>
										</tbody>
										<tfoot data-ng-show="filtro.grupoSocialList.length > tamanhoPagina">
											<tr>
												<td colspan="4">
													<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
														data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
														data-total-items="filtro.grupoSocialList.length"
														data-ng-model="filtroGrupoSocialPagina" data-ng-init="filtroGrupoSocialPagina = 1"></pagination>
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
								<div class="panel-footer text-right">
									<label>Total: {{filtro.grupoSocialList.length}}</label>
									<div class="btn-group">
										<button type="button" class="btn btn-success pull-left" data-ng-click="apoio.filtroGrupoSocialIncluir()">Incluir</button>
										<button type="button" class="btn btn-success dropdown-toggle pull-left" data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span>
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<ul class="dropdown-menu pull-left" role="menu" style="right: 0; left: auto;">
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroGrupoSocialIncluir();"> Alguém </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroGrupoSocialIncluirElemento(usuarioLogado.pessoa, 'Q')"> Me Incluir </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroGrupoSocialIncluirElemento(null, 'D')"> Minha Unidade </a>
											</li>
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguém da Minha Unidade </a>
											</li>
											<li>
												<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.filtroGrupoSocialIncluirElemento(null, 'D')"> Minha Institução </a>
											</li>
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguma Unidade da Minha Instituição </a>
											</li class="sr-only">
											<li class="sr-only">
												<a href="#" onclick="event.preventDefault();"> Alguém da Minha Instituição </a>
											</li>
										</ul>
									</div>
									<button type="button" class="btn btn-danger" data-ng-click="apoio.filtroGrupoSocialListInit()" data-ng-show="filtro.grupoSocialList.length"> Limpar </button>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="assunto">Assunto(s)</label>
						<div class="col-md-7">
							<div class="row" style="height: 300px; overflow: auto;">                     
								<script type="text/ng-template" id="items_renderer_sub.html" charset="UTF-8">
									<div data-ui-tree-handle>
										<label>
											<input type="checkbox" class="Tooltip" title="Selecione" data-ng-model="assuntoAcao.marcado" id="assuntoAcaoChk">
											{{assuntoAcao.acao.nome}}
										</label>
									</div>
								</script>
								<script type="text/ng-template" id="items_renderer.html" charset="UTF-8">
									<div data-ui-tree-handle>
										<a class="btn btn-primary btn-xs Tooltip" data-nodrag data-ng-click="toggleChildren(this)" title="Expandir/Recolher">
											<span class="glyphicon glyphicon-chevron-right" data-ng-show="collapsed"></span>
											<span class="glyphicon glyphicon-chevron-down" data-ng-show="!collapsed"></span>
										</a>
										<label>
											<input type="checkbox" class="Tooltip" title="Selecione" data-ng-model="linha.marcado" data-ng-click="selecionaAssuntoDescendente(this)" id="linhaChk">
											{{linha.nome}}
										</label>
									</div>
									<ol data-ui-tree-nodes="" data-ng-model="linha.filhos" data-ng-class="{hidden: collapsed}" data-ng-show="linha.filhos.length > 0">
										<li data-ng-repeat="linha in linha.filhos" data-ui-tree-node="" data-ng-include="'items_renderer.html'" data-collapsed="true"></li>
									</ol>
									<ol data-ui-tree-nodes="" data-ng-model="linha.acoes" data-ng-class="{hidden: collapsed}" data-ng-show="linha.acoes.length > 0">
										<li data-ng-repeat="assuntoAcao in linha.acoes" data-ui-tree-node="" data-ng-include="'items_renderer_sub.html'" data-collapsed="true"></li>
									</ol>
								</script>
								<div data-ui-tree id="admin-tree-root" data-spacing="2" data-drag-enabled="false">
									<ol data-ui-tree-nodes="" data-ng-model="filtro.atividadeAssuntoAcaoList">
										<li data-ui-tree-node data-ng-include="'items_renderer.html'" data-collapsed="false" data-ng-repeat="linha in filtro.atividadeAssuntoAcaoList"></li>
									</ol>
								</div>
							</div>
							<div class="row">
								<div class="label label-default" data-ng-show="apoio.contadorAtividadeAssuntoAcao > 0" data-ng-click="apoio.limparAtividadeAssuntoAcaoList()">{{apoio.contadorAtividadeAssuntoAcao}} item(s) selecionado(s)</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="metodo">Método(s)</label>
						<div class="col-md-4">
							<div class="btn-group">
								<input type="search" placeholder="filtro" data-ng-model="apoio.filtroMetodo"/>
								<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.filtroMetodo = null"></span>
							</div>
							<select id="metodo" name="metodo" class="form-control" data-placeholder="Selecione o método" data-ng-model="filtro.metodoIdList" data-ng-options="item.id as item.nome for item in apoio.metodoList | filter : apoio.filtroMetodo" multiple="multiple"></select>
							<div class="label label-default" data-ng-show="filtro.metodoIdList.length > 0" data-ng-click="filtro.metodoIdList = []">{{filtro.metodoIdList.length}} item(s) selecionado(s)</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="situacao">Situação(ões)</label>
						<div class="col-md-4">
							<div class="btn-group">
								<input type="search" placeholder="filtro" data-ng-model="apoio.filtroSituacao"/>
								<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.filtroSituacao = null"></span>
							</div>
							<select id="situacao" name="situacao" class="form-control" data-placeholder="Selecione a situação" data-ng-model="filtro.situacaoIdList" data-ng-options="item.codigo as item.descricao for item in apoio.atividadeSituacaoList | filter : apoio.filtroSituacao" multiple="multiple"></select>
							<div class="label label-default" data-ng-show="filtro.situacaoIdList.length > 0" data-ng-click="filtro.situacaoIdList = []">{{filtro.situacaoIdList.length}} item(s) selecionado(s)</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="situacao">Localização</label>
						<div class="col-md-7">
							<div id="map-canvas"></div>
							<button type="button" class="btn btn-success"> Salvar </button>
							<button type="button" class="btn btn-success"> Carregar </button>
							<button type="button" class="btn btn-danger" onclick="cleanFiltro();"> Limpar </button>
							<button type="button" class="btn btn-success" onclick="initializeFiltro();">Exibir Mapa</button>
							[{{drawingManagerControl}}]
<style>.angular-google-map-container { height: 500px; }</style>
<ui-gmap-google-map id="mapa" name="mapa" center='map.center' zoom='map.zoom' draggable="true" options="options" bounds="map.bounds">
	<ui-gmap-drawing-manager options="drawingManagerOptions" control="drawingManagerControl"></ui-gmap-drawing-manager>
</ui-gmap-google-map>
						</div>
					</div>
				</fieldset>
				<!-- [{{filtro}}] -->
            </form>
        </div>
    </div>
</div>

<modal-dialog data-identificador='endereco' data-titulo="Teste de Endereco" data-width="90%">
	<form class="form-horizontal" name="$parent.endereco" novalidate>
		<endereco data-dados="filtro.endereco" />
	</form>
</modal-dialog>
