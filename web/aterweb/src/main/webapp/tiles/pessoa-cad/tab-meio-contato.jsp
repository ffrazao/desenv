<!-- Frazão Inicio Grupo Social -->
<div class="container">
							<div class="tab-pane fade" id="meioContatos">
								<div class="container col-md-12">
									<div class="row">
										<div class="col-md-12">
<div class="panel panel-default">
	<div class="panel-heading">
		<div class="row">
			<div class="col-md-6">
				<h3 class="panel-title">Enderecos</h3>
			</div>
			<div class="col-md-6" data-ng-show="registro.pessoaMeioContatos.length">
				<div class="btn-group">
					<input type="search" placeholder="filtro" data-ng-model="apoio.filtroEndereco" data-ng-change="filtroEnderecoPagina=1"/>
					<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.filtroEndereco = null"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="table-responsive" data-ng-show="registro.pessoaMeioContatos.length">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Finalidade</th>
					<th>Endereço</th>
					<th>Propriedade Rural</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<tr data-ng-repeat="item in registro.pessoaMeioContatos | filter: apoio.filtroEndereco | offset : filtroEnderecoPagina : tamanhoPagina | limitTo: tamanhoPagina">
					<td>{{$index + 1 + ((filtroEnderecoPagina-1)*tamanhoPagina)}}</td>
					<td><select class="form-control" id="finalidadeEndereco" name="finalidadeEndereco" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select></td>
					<td><span data-ng-bind-html="item.meioContato.enderecoHtml | sanitize"></span></td>
					<td>
						{{item.meioContato.propriedadeRuralConfirmacao == 'S' ? 'Sim': 'Não'}}
					</td>
					<td>
						<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.filtroEnderecoRemover(item)"><span class="glyphicon glyphicon-trash"></span></button>
					</td>
				</tr>
			</tbody>
			<tfoot data-ng-show="registro.pessoaMeioContatos.length > tamanhoPagina">
				<tr>
					<td colspan="4">
						<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
							data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
							data-total-items="registro.pessoaMeioContatos.length"
							data-ng-model="filtroEnderecoPagina" data-ng-init="filtroEnderecoPagina = 1"></pagination>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<div class="panel-footer text-right">
		<label>Total: {{registro.pessoaMeioContatos.length}}</label>
		<button type="button" class="btn btn-success pull-left" data-toggle="modal" data-target="#endereco" data-ng-click="novoEndereco()">Incluir</button>
		<button type="button" class="btn btn-danger" data-ng-click="apoio.filtroEnderecoListInit()" data-ng-show="registro.pessoaMeioContatos.length"> Limpar </button>
	</div>
</div>

										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div>
												<div class="panel panel-default">
													<div class="panel-heading">
														<h4 class="panel-title">E-Mail(s)</h4>
														<div class="bt-topo text-right">
															<button class="btn btn-success novo btn-xs ttip"
																data-toggle="modal" data-target="#modalMeioContatoEmail"
																data-ng-click="acoes.meioContato.incluir('EMA')"
																type="button" data-original-title="Incluir">
																<span class="glyphicon glyphicon-plus"></span>
															</button>
														</div>
													</div>
													<div class="panel-body">
														<div class="table-responsive text-center">
															<table class="table table-striped">
																<thead>
																	<tr>
																		<th>Finalidade</th>
																		<th>E-Mail</th>
																		<th>&nbsp;</th>
																	</tr>
																</thead>
																<tbody>
																	<tr
																		data-ng-repeat="contato in registro.pessoaMeioContatos | filter:{meioContato:{meioContatoTipo:'EMA'}}">
																		<td><span data-ng-if="contato.finalidade === 'C'">Comercial</span>
																			<span data-ng-if="contato.finalidade === 'P'">Pessoal</span>
																		</td>
																		<td>{{contato.meioContato.email}}</td>
																		<td>
																			<button type="button" data-toggle="modal"
																				data-target="#modalMeioContatoEmail"
																				data-ng-click="acoes.meioContato.editar(contato)"
																				class="btn btn-warning btn-xs">
																				<span class="glyphicon glyphicon-pencil"></span>
																			</button>&nbsp;
																			<button type="button"
																				data-ng-click="acoes.meioContato.remover(contato)"
																				class="btn btn-danger btn-xs">
																				<span class="glyphicon glyphicon-trash"></span>
																			</button>
																		</td>
																	</tr>
																</tbody>
																<tfoot></tfoot>
															</table>
														</div>
													</div>

												</div>
											</div>
											<div>
												<div class="modal fade formulario"
													id="modalMeioContatoEmail" tabindex="-1" role="dialog"
													aria-labelledby="Email" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"
																	aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">E-Mail</h4>
															</div>
															<div class="modal-body">
																<div class="form-group">
																	<label for="finalidadeMeioContato"
																		class="control-label">Finalidade</label><br> <input
																		type="radio" name="finalidadeMeioContatoEmail"
																		value="P"
																		data-ng-model="acoes.meioContato.registro.finalidade" />
																	Pessoal | <input type="radio"
																		name="finalidadeMeioContatoEmail" value="C"
																		data-ng-model="acoes.meioContato.registro.finalidade" />
																	Comercial

																</div>
																<div class="form-group">
																	<label for="meioMeioContato" class="control-label">Meio
																		de Contato</label><br> <input class="form-control"
																		title="E-Mail" id="meioMeioContato"
																		data-ng-model="acoes.meioContato.registro.meioContato.email" />
																</div>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-default cancelar"
																	data-dismiss="modal">Cancelar</button>
																<button type="button" class="btn btn-primary salvar"
																	data-dismiss="modal"
																	data-ng-click="acoes.meioContato.salvar()">Salvar</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="col-md-6">
											<div>
												<div class="panel panel-default">
													<div class="panel-heading">
														<h4 class="panel-title">Telefone(s)</h4>
														<div class="bt-topo text-right">
															<button class="btn btn-success novo btn-xs ttip"
																data-toggle="modal"
																data-target="#modalMeioContatoTelefone"
																data-ng-click="acoes.meioContato.incluir('TEL')"
																type="button" data-original-title="Incluir">
																<span class="glyphicon glyphicon-plus"></span>
															</button>
														</div>
													</div>
													<div class="panel-body">
														<div class="table-responsive text-center">
															<table class="table table-striped">
																<thead>
																	<tr>
																		<th>Finalidade</th>
																		<th>Número</th>
																		<th>&nbsp;</th>
																	</tr>
																</thead>
																<tbody>
																	<tr
																		data-ng-repeat="contato in registro.pessoaMeioContatos | filter:{meioContato:{meioContatoTipo:'TEL'}}">
																		<td><span data-ng-if="contato.finalidade === 'C'">Comercial</span>
																			<span data-ng-if="contato.finalidade === 'P'">Pessoal</span>
																		</td>
																		<td>{{contato.meioContato.numero}}</td>
																		<td>
																			<button type="button" data-toggle="modal"
																				data-target="#modalMeioContatoTelefone"
																				data-ng-click="acoes.meioContato.editar(contato)"
																				class="btn btn-warning btn-xs">
																				<span class="glyphicon glyphicon-pencil"></span>
																			</button>&nbsp;
																			<button type="button"
																				data-ng-click="acoes.meioContato.remover(contato)"
																				class="btn btn-danger btn-xs">
																				<span class="glyphicon glyphicon-trash"></span>
																			</button>
																		</td>
																	</tr>
																</tbody>
																<tfoot></tfoot>
															</table>
														</div>
													</div>

												</div>
											</div>
											<div>
												<div class="modal fade formulario"
													id="modalMeioContatoTelefone" tabindex="-1" role="dialog"
													aria-labelledby="Telefone" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"
																	aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">Telefone</h4>
															</div>
															<div class="modal-body">
																<div class="form-group">
																	<label for="finalidadeMeioContato"
																		class="control-label">Finalidade</label><br> <input
																		type="radio" name="finalidadeMeioContatoTel"
																		id="finalidadeMeioContato" value="P"
																		data-ng-model="acoes.meioContato.registro.finalidade" />
																	Pessoal | <input type="radio"
																		name="finalidadeMeioContatoTel"
																		id="finalidadeMeioContato" value="C"
																		data-ng-model="acoes.meioContato.registro.finalidade" />
																	Comercial
																</div>
																<div class="form-group">
																	<label for="meioMeioContato" class="control-label">Meio
																		de Contato</label><br> <input class="form-control"
																		id="meioMeioContato"
																		data-ng-model="acoes.meioContato.registro.meioContato.numero" />
																</div>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-default cancelar"
																	data-dismiss="modal">Cancelar</button>
																<button type="button" class="btn btn-primary salvar"
																	data-dismiss="modal"
																	data-ng-click="acoes.meioContato.salvar()">Salvar</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

</div>
<!-- Frazão Fim Grupo Social -->
