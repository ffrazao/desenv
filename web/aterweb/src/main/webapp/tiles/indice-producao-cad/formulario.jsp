<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="div_formulario">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Formulário</h3>
		</div>
		<div class="panel-body">
			<form name="frmCadastro" data-ng-submit="salvar(frmCadastro.$valid)" novalidate>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" data-ng-model="registro.id" />
				<div class="row">
					<div class="form-group col-md-12">
						<label class="control-label">Perspectiva</label> 
						<select class="form-control" data-ng-model="registro.produtoServicoPerspectiva" data-ng-change="apoio.produtoServicoPerspectivaAtualizar(registro.produtoServicoPerspectiva)" data-ng-disabled="registro.id">
							<option value=""></option>
							<option value="AGRICOLA">Agrícola</option>
							<option value="FLORES">&nbsp;&nbsp;&nbsp;Flores</option>
							<option value="ANIMAL">Animal</option>
							<option value="CORTE">&nbsp;&nbsp;&nbsp;Corte</option>
							<option value="LEITE">&nbsp;&nbsp;&nbsp;Leite</option>
							<option value="POSTURA">&nbsp;&nbsp;&nbsp;Postura</option>
							<option value="SERVICO">Serviço</option>
							<option value="AGROINDUSTRIA">&nbsp;&nbsp;&nbsp;Agro-Indústria</option>
							<option value="TURISMO">&nbsp;&nbsp;&nbsp;Turismo</option>
						</select>
					</div>
				</div>
				<div class="panel-body" data-ng-show="registro.produtoServicoPerspectiva == 'AGRICOLA' || registro.produtoServicoPerspectiva == 'FLORES'">
					<div class="row">
						<div class="form-group col-md-3">
							<label class="control-label">Tipo</label>
							<select class="form-control" data-ng-model="registro.tipo" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresTipoList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label">Sistema</label> 
							<select class="form-control" data-ng-model="registro.sistemaAgricola" data-ng-disabled="registro.id"data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresSistemaList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label">Proteção/ Época/ Forma</label>
							<select class="form-control" data-ng-model="registro.protecaoEpocaForma" data-ng-disabled="registro.id"data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresProtecaoEpocaFormaList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label">Uso D'Água</label>
							<select class="form-control" data-ng-model="registro.usoDagua" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresUsoDaguaList">
								<option value=""></option>
							</select>
						</div>
					</div>
				</div>
				<div class="panel-body" data-ng-show="registro.produtoServicoPerspectiva == 'ANIMAL' || registro.produtoServicoPerspectiva == 'CORTE' || registro.produtoServicoPerspectiva == 'LEITE' || registro.produtoServicoPerspectiva == 'POSTURA'">
					<div class="row">
						<div class="form-group col-md-6">
							<label class="control-label">Sistema</label>
							<select class="form-control" data-ng-model="registro.sistemaAnimal" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaAnimalSistemaList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-6">
							<label class="control-label">Exploração</label>
							<select class="form-control" data-ng-model="registro.exploracao" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaAnimalExploracaoList">
								<option value=""></option>
							</select>
						</div>
					</div>
				</div>
				<div class="panel-body" data-ng-show="registro.produtoServicoPerspectiva == 'SERVICO' || registro.produtoServicoPerspectiva == 'AGROINDUSTRIA'">
					<div class="row">
						<div class="form-group col-md-4">
							<label class="control-label">Projeto</label>
							<select class="form-control" data-ng-model="registro.projeto" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoProjetoList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-4">
							<label class="control-label">Condição</label> 
							<select class="form-control" data-ng-model="registro.condicao" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoCondicaoList">
								<option value=""></option>
							</select>
						</div>
						<div class="form-group col-md-4">
							<label class="control-label">Produto</label>
							<select class="form-control" data-ng-model="registro.produto" data-ng-disabled="registro.id" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoProdutoList">
								<option value=""></option>
							</select>
						</div>
					</div>
				</div>
				<div class="row" data-ng-hide="registro.produtoServicoPerspectiva == null || registro.produtoServicoPerspectiva == ''">
					<div class="form-group col-md-12">
						<label class="control-label">Produto ou Serviço</label>
						<div data-angular-treeview="true" 
							data-tree-id="produtoServicoTree"
							data-tree-model="apoio.produtoServico" 
							data-node-id="id"
							data-node-label="nome" 
							data-node-children="filhos" 
							data-ng-show="!registro.id">
						</div>
						<div data-ng-show="registro.id">
							<input type="hidden" data-ng-model="registro.produtoServico"/>
							{{registro.produtoServico.nome}}
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-4">
						<label class="control-label">Período da Produção</label><br />
						<div class="row">
							<div class="form-group col-md-2">
								<label class="control-label">Entre</label>
							</div>
							<div class="form-group col-md-4">
								<input class="form-control" placeholder="Início" data-ng-model="registro.inicio" />
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">e</label>
							</div>
							<div class="form-group col-md-4">
								<input class="form-control" placeholder="Término" data-ng-model="registro.termino" />
							</div>
						</div>
					</div>
					<div class="form-group col-md-3">
						<label class="control-label">Volume de Produção</label><br />
						<input class="form-control" placeholder="Volume" data-ng-model="registro.volume" />
					</div>
					<div class="form-group col-md-1">
						{{produtoServicoTree.currentNode.unidadeMedida.nome}}
					</div>
					<div class="form-group col-md-4">
						<label class="control-label">Total de Propriedades</label><br />
						<input class="form-control" placeholder="Propriedades" data-ng-model="registro.totalPropriedades" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-12">
						<label class="control-label">Comunidade</label>
						<select class="form-control" data-ng-model="registro.pessoaGrupo.id" data-ng-options="pessoaGrupo.id as pessoaGrupo.nome for pessoaGrupo in apoio.filtroPessoaGrupoComunidadeList" data-ng-disabled="registro.id">
							<option value=""></option>
						</select>
					</div>
				</div>

				<div class="row" data-ng-hide="registro.pessoaGrupo.id == null || registro.pessoaGrupo.id == ''">
					<div class="form-group col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Produção</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive text-center">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Propriedade Rural</th>
												<th>Volume</th>
												<th>Percentual</th>
												<th>Responsável pela Produção</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="producao in registro.producaoList">
												<td>{{$index + 1}}</td>
												<td>{{producao.propriedadeRural.nome}}</td>
												<td><input class="form-control" placeholder="Volume" data-ng-model="producao.volume" /></td>
												<td>{{registro.volume > 0 ? (producao.volume / registro.volume) * 100 : 0 | number: 2}}%</td>
												<td width="50%">
													<table>
														<thead>
															<tr>
																<td>Nome</td>
																<td>Tamanho</td>
																<td>Regime</td>
																<td>Volume</td>
																<td>Percentual</td>
															</tr>
														</thead>
														<tbody>
															<tr data-ng-repeat="responsavel in producao.responsavelList">
																<td>{{responsavel.exploracao.pessoaMeioContato.pessoa.nome}}</td>
																<td>{{responsavel.exploracao.area}}</td>
																<td>{{responsavel.exploracao.regime}}</td>
																<td><input class="form-control" placeholder="Volume" data-ng-model="responsavel.volume" /></td>
																<td>%</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
										<tfoot></tfoot>
									</table>
								</div>
							</div>
							<div class="panel-footer text-right">
								<button type="button" class="btn btn-success" data-ng-click="apoio.producaoListDefinir()" data-toggle="modal" data-target="#modalProducao">Definir</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal fade formulario" id="modalProducao" tabindex="-1" data-role="dialog" data-aria-labelledby="Endereço" data-aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Propriedades Rurais Produtoras</h4>
			</div>
			<div class="modal-body" data-ng-class="{statusInvalido: apoio.producao.status == 'I'}">
				<form name="frmProducao" novalidate>
					<input type="hidden" data-ng-value="apoio.producao.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<div>Perspectiva: {{produtoServicoTree.currentNode.perspectiva}}</div>
							<div>Local: {{registro.pessoaGrupo.nome}}</div>
							<div>Previsao: De {{registro.inicio}} a {{registro.termino}} total de {{registro.volume}} {{linha.produtoServico.unidadeMedida.nome}} em {{registro.totalPropriedades}} propriedades rurais</div>
							<div>Produto/Servico: {{produtoServicoTree.currentNode.nome}}</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label class="control-label">Pesquisa</label>
							<input class="form-control" placeholder="Nome da Propriedade ou do Produtor Rural" data-ng-model="pesquisa.$"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Produção</h3>
								</div>
								<div class="panel-body">
									<div class="table-responsive text-center">
										<table class="table table-striped">
											<thead>
												<tr>
													<th width="10">
														<button type="button" name="selectLinha" class="btn btn-default btn-xs">&#9737;</button>
													</th>
													<th>#</th>
													<th>Propriedade Rural</th>
													<th>Responsável pela Produção</th>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
												<tr data-ng-repeat="producao in apoio.producaoList | filter:pesquisa">
													<td>
														<input type="checkbox" data-ng-model="producao.selecionado" data-ng-click="apoio.selecionarProducao(producao)" data-ng-true-value="true" data-ng-false-value="false">
													</td>
													<td>{{(((paginaAtual-1)*50) + $index) + 1}}</td>
													<td>{{producao.nome}}, tamanho: {{producao.areaTotal}} ha</td>
													<td>
														<ul>
															<li data-ng-repeat="produtor in producao.exploracaoList">{{produtor.nome}}, tamanho: {{produtor.exploracaoArea}}, regime: {{produtor.exploracaoRegime}}</li>
														</ul>
													</td>
												</tr>
											</tbody>
											<tfoot></tfoot>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.producaoListLimpar()">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-ng-click="apoio.producaoListSalvar(frmProducao.$valid)">Salvar</button>
			</div>
		</div>
	</div>
</div>
{{registro}}{{produtoServicoTree.currentNode}}
<script src="https://maps.googleapis.com/maps/api/js?v=3.9&sensor=false" charset="UTF-8"></script>
<script>
	$(function() {
		$(".cpf").mask('000.000.000-00', {
			reverse : true
		});
		//    $(".data").mask('00/00/0000');
		$(".cep").mask('00000-000');
	});
</script>