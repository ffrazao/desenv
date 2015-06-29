<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="div_formulario">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">{{nomePagina}}</h3>
		</div>
		<div class="panel-body">
			<form name="$parent.frmCadastro" novalidate>
			    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			    <input type="hidden" data-ng-model="registro.id" />
				<div class="container-fluid">
					<div class="row">
						<div class="form-group col-md-3" data-ng-class="{ 'statusInvalido' : frmCadastro.codigo.$invalid && (!frmCadastro.codigo.$pristine || submitted) }">
							<label class="control-label" for="codigo">Código da Atividade</label>
							<input type="text" class="form-control text-center codigoAtividade" id="codigo" name="codigo" readonly="readonly" data-ng-model="registro.codigo" required="required">
							<p data-ng-show="frmCadastro.codigo.$invalid && (!frmCadastro.codigo.$pristine || submitted)" class="help-block">
								Campo Obrigatório!
							</p>
						</div>
						<div class="form-group col-md-3"data-ng-class="{ 'statusInvalido' : frmCadastro.dataRegistro.$invalid && (!frmCadastro.dataRegistro.$pristine || submitted) }">
							<label class="control-label" for="dataRegistro">Data do Registro</label>
							<input type="text" class="form-control text-center" id="dataRegistro" name="dataRegistro" readonly="readonly" 
								data-ng-model="registro.registro" required="required" readonly="readonly" datepicker-popup="dd/MM/yyyy HH:mm:ss">
							<p data-ng-show="frmCadastro.dataRegistro.$invalid && (!frmCadastro.dataRegistro.$pristine || submitted)" class="help-block">
								Campo Obrigatório!
							</p>
						</div>
						<div class="form-group col-md-3" data-ng-class="{ 'statusInvalido' : frmCadastro.registradoPor.$invalid && (!frmCadastro.registradoPor.$pristine || submitted) }">
							<label class="control-label" for="registradoPor">Registrado Por</label>
							<input type="text" class="form-control" id="registradoPor" name="registradoPor" readonly="readonly" data-ng-model="registro.usuario.pessoa.nome" required="required">
							<p data-ng-show="frmCadastro.registradoPor.$invalid && (!frmCadastro.registradoPor.$pristine || submitted)" class="help-block">
								Campo Obrigatório!
							</p>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label" for="percentualConclusao">% Conclusão</label>
							<div class="form-control" id="percentualConclusao" name="percentualConclusao" 
								data-ng-class="{'texto-branco': registro.percentualConclusao > 0,'': registro.percentualConclusao<1, 'label-warning': registro.percentualConclusao>=1 && registro.percentualConclusao<3, 'label-info': registro.percentualConclusao>=3 && registro.percentualConclusao<7, 'label-success': registro.percentualConclusao>=7}"
							 	data-ng-disabled="registro.id">
								<rating ng-model="registro.percentualConclusao" min="0" max="10" on-hover="hoveringOver(value)" on-leave="overStar = null" readonly="registro.id"></rating>
   								<span class="label" data-ng-class="{'label-warning': percent<30, 'label-info': percent>=30 && percent<70, 'label-success': percent>=70}" data-ng-show="overStar && !isReadonly">{{percent}}%</span>
   								<span class="pull-right">{{registro.percentualConclusao*10}}%<span class="glyphicon glyphicon-trash" data-ng-click="registro.percentualConclusao = 0" data-ng-hide="registro.id"></span></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-4" data-ng-class="{ 'statusInvalido' : frmCadastro.situacao.$invalid && (!frmCadastro.situacao.$pristine || submitted) }">
							<label class="control-label" for="situacao">Situação</label>
							<select class="form-control" id="situacao" name="situacao" data-ng-model="registro.situacao" required="required" 
								data-ng-options="item.codigo as item.descricao for item in apoio.atividadeSituacaoListInicial"
								data-ng-disabled="registro.id"></select>
							<p data-ng-show="frmCadastro.situacao.$invalid && (!frmCadastro.situacao.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
						</div>
						<div class="form-group col-md-4" data-ng-class="{ 'statusInvalido' : frmCadastro.prioridade.$invalid && (!frmCadastro.prioridade.$pristine || submitted) }">
							<label class="control-label" for="prioridade">Prioridade</label>
							<div class="form-control text-center" id="prioridade" name="prioridade" data-ng-disabled="registro.id">
								<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="registro.prioridade" value="N" required="required" data-ng-disabled="registro.id">Normal</label>
								<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="registro.prioridade" value="B" required="required" data-ng-disabled="registro.id">Baixa</label>
								<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="registro.prioridade" value="A" required="required" data-ng-disabled="registro.id">Alta</label>
							</div>
							<p data-ng-show="frmCadastro.prioridade.$invalid && (!frmCadastro.prioridade.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
						</div>
						<div class="form-group col-md-4" data-ng-class="{ 'statusInvalido' : frmCadastro.formato.$invalid && (!frmCadastro.formato.$pristine || submitted) }">
							<label class="control-label" for="formato">Formato</label>
							<div class="form-control text-center">
								<label class="radio-inline"><input type="radio" name="formato" readonly="readonly" data-ng-model="registro.formato" value="P" required="required" data-ng-disabled="true">Planejamento</label>
								<label class="radio-inline"><input type="radio" name="formato" readonly="readonly" data-ng-model="registro.formato" value="E" required="required" data-ng-disabled="true">Execução</label>
							</div>
							<p data-ng-show="frmCadastro.formato.$invalid && (!frmCadastro.formato.$pristine || submitted)" class="help-block">
								Campo Obrigatório!
							</p>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<div class="checkbox pull-right">
								<label>
									<input type="checkbox" data-ng-model="apoio.somenteUmaAba" data-ng-click="apoio.expandirTudo(false)"> exibir somente uma aba de cada vez
								</label>
								<label>
									<input type="button" class="btn btn-success"  data-ng-hide="apoio.somenteUmaAba" value="Expandir Tudo" data-ng-click="apoio.expandirTudo(true)">
								</label>
								<label>
									<input type="button" class="btn btn-danger"  data-ng-hide="apoio.somenteUmaAba" value="Recolher Tudo" data-ng-click="apoio.expandirTudo(false)">
								</label>
								<label>
									<input type="checkbox" data-ng-click="apoio.exibeResumo = !apoio.exibeResumo" data-ng-model="apoio.exibeResumo"> exibir resumo
								</label>
							</div>
						</div>
					</div>
				</div>
				<accordion close-others="apoio.somenteUmaAba">
					<accordion-group is-open="controle.abas.quem">
						<accordion-heading>
							<b>Quem?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.quem, 'glyphicon-chevron-right': !controle.abas.quem}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.quem && apoio.exibeResumo">
									<thead>
										<tr><td><b>Demandante(s)</b></td><td><b>Executor(es)</b></td><td><b>Objeto(s)</b></td></tr>
									<thead>
									<tbody>
										<tr>
											<td>									
												<ul class="unstyled">
													<li ng-repeat="item in registro.atividadePessoaList | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
														{{item.pessoa.nome}}
													</li>
												</ul>
											</td>
											<td>
												<ul class="unstyled">
													<li ng-repeat="item in registro.atividadePessoaList | filter : filtrarExecutor() | orderBy : ['pessoa.nome']">
														<span data-ng-class="{'texto-negrito-sublinhado' : item.pessoa.id==apoio.responsavel}">{{item.pessoa.nome}}</span>
													</li>
												</ul>
											</td>
											<td>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-12" data-ng-class="{ 'statusInvalido' : frmCadastro.publicoEstimado.$invalid && (!frmCadastro.publicoEstimado.$pristine || submitted) }">
									<label class="control-label" for="publicoEstimado">Público Estimado</label>
									<input type="number" placeholder="total do público estimado" id="publicoEstimado" name="publicoEstimado" class="form-control" data-ng-model="registro.publicoEstimado" required="required">
									<p data-ng-show="frmCadastro.publicoEstimado.$invalid && (!frmCadastro.publicoEstimado.$pristine || submitted)" class="help-block danger error">Campo Obrigatório!</p>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6" data-ng-class="{ 'statusInvalido' : apoio.totalAtividadePessoaPorTipo(['D']) == 0 && submitted }">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Demandante</h3>
												</div>
												<div class="col-md-6" data-ng-show="apoio.totalAtividadePessoaPorTipo(['D'])">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.demandanteFiltro" data-ng-change="demandantePagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.demandanteFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive" data-ng-show="apoio.totalAtividadePessoaPorTipo(['D'])">
											<input type="hidden" name="demandanteList" data-ng-model="lixo1">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Nome</th>
														<th>Documento</th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr data-ng-repeat="item in registro.atividadePessoaList | filter: apoio.demandanteFiltro | filter : filtrarDemandante() | orderBy : ['pessoa.nome'] | offset : demandantePagina : tamanhoPagina | limitTo: tamanhoPagina">
														<td>{{$index + 1 + ((demandantePagina-1)*tamanhoPagina)}}</td>
														<td>{{item.pessoa.nome}}</td>
														<td>{{item.pessoa.cpf}}</td>
														<td>
															<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.pessoaRemover(item)" ><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</tbody>
												<tfoot data-ng-show="apoio.totalAtividadePessoaPorTipo(['D']) > tamanhoPagina">
													<tr>
														<td colspan="4">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="apoio.totalAtividadePessoaPorTipo(['D'])"
																data-ng-model="demandantePagina" data-ng-init="demandantePagina = 1"></pagination>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="panel-footer text-right">
											<label>Demandante: {{apoio.totalAtividadePessoaPorTipo(['D'])}}</label>
											<div class="btn-group">
												<button type="button" class="btn btn-success pull-left" data-ng-click="apoio.demandanteIncluir()">Incluir</button>
												<button type="button" class="btn btn-success dropdown-toggle pull-left" data-toggle="dropdown" aria-expanded="false">
													<span class="caret"></span>
													<span class="sr-only">Toggle Dropdown</span>
												</button>
												<ul class="dropdown-menu pull-left" role="menu" style="right: 0; left: auto;">
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.demandanteIncluir();"> Alguém </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(usuarioLogado.pessoa, 'D')"> Me Incluir </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(null, 'D')"> Minha Unidade </a>
													</li>
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguém da Minha Unidade </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(null, 'D')"> Minha Institução </a>
													</li>
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguma Unidade da Minha Instituição </a>
													</li class="sr-only">
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguém da Minha Instituição </a>
													</li>
												</ul>
											</div>
											<button type="button" class="btn btn-danger" data-ng-click="apoio.limparAtividadePessoaPorTipo(['D'])" data-ng-show="apoio.totalAtividadePessoaPorTipo(['D'])"> Limpar </button>
										</div>
									</div>
									<p data-ng-show="!apoio.totalAtividadePessoaPorTipo(['D']) && submitted" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
								<div class="form-group col-md-6" data-ng-class="{ 'statusInvalido' : apoio.totalAtividadePessoaPorTipo(['ER']) == 0 && submitted }">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Executor</h3>
												</div>
												<div class="col-md-6" data-ng-show="apoio.totalAtividadePessoaPorTipo(['ER', 'E'])">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.executorFiltro" data-ng-change="executorPagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.executorFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive" data-ng-show="apoio.totalAtividadePessoaPorTipo(['ER', 'E'])">
											<input type="hidden" name="executorList" data-ng-model="lixo2">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Nome</th>
														<th>Documento</th>
														<th>Responsável</th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr data-ng-repeat="item in registro.atividadePessoaList | filter: apoio.executorFiltro | filter : filtrarExecutor() | orderBy : ['pessoa.nome'] | offset : executorPagina : tamanhoPagina | limitTo: tamanhoPagina">
														<td>{{$index + 1 + ((executorPagina-1)*tamanhoPagina)}}</td>
														<td>{{item.pessoa.nome}}</td>
														<td>{{item.pessoa.cpf}}</td>
														<td><input type="radio" name="responsavelAtividade" data-ng-model="apoio.responsavel" data-ng-value="item.pessoa.id" data-ng-click="apoio.executorResponsavel(item)"></td>
														<td>
															<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.pessoaRemover(item)" ><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</tbody>
												<tfoot data-ng-show="apoio.totalAtividadePessoaPorTipo(['ER', 'E']) > tamanhoPagina">
													<tr>
														<td colspan="4">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="apoio.totalAtividadePessoaPorTipo(['ER', 'E'])"
																data-ng-model="executorPagina" data-ng-init="executorPagina = 1"></pagination>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="panel-footer text-right">
											<label>Responsavel: {{apoio.totalAtividadePessoaPorTipo(['ER'])}}</label>
											<label>Apoio: {{apoio.totalAtividadePessoaPorTipo(['E'])}}</label>
											<div class="btn-group">
												<button type="button" class="btn btn-success pull-left" data-ng-click="apoio.executorIncluir()">Incluir</button>
												<button type="button" class="btn btn-success dropdown-toggle pull-left" data-toggle="dropdown" aria-expanded="false">
													<span class="caret"></span>
													<span class="sr-only">Toggle Dropdown</span>
												</button>
												<ul class="dropdown-menu pull-left" role="menu" style="right: 0; left: auto;">
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.executorIncluir();"> Alguém </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(usuarioLogado.pessoa, 'E')"> Me Incluir </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(null, 'E')"> Minha Unidade </a>
													</li>
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguém da Minha Unidade </a>
													</li>
													<li>
														<a href="#" onclick="event.preventDefault();" data-ng-click="apoio.incluirPessoa(null, 'E')"> Minha Institução </a>
													</li>
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguma Unidade da Minha Instituição </a>
													</li class="sr-only">
													<li class="sr-only">
														<a href="#" onclick="event.preventDefault();"> Alguém da Minha Instituição </a>
													</li>
												</ul>
											</div>
											<button type="button" class="btn btn-danger" data-ng-click="apoio.limparAtividadePessoaPorTipo(['ER', 'E'])" data-ng-show="apoio.totalAtividadePessoaPorTipo(['ER', 'E'])"> Limpar </button>
										</div>
									</div>
									<p data-ng-show="!apoio.totalAtividadePessoaPorTipo(['ER', 'E']) && submitted" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
							<div class="row" data-ng-show="apoio.totalAtividadePessoaPorTipo(['D'])">
								<div class="form-group col-md-12">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Objeto</h3>
												</div>
												<div class="col-md-6" data-ng-show="registro.objetoList.length">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.objetoFiltro" data-ng-change="objetoPagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.objetoFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive">
											<table class="table table-striped" data-ng-show="registro.objetoList.length">
												<thead>
		                                             <tr>
		                                                 <th>#</th>
		                                                 <th>Tipo</th>
		                                                 <th>Descrição</th>
		                                                 <th>&nbsp;</th>
		                                             </tr>
		                                         </thead>
		                                         <tbody>
													<tr data-ng-repeat="item in registro.objetoList | filter: apoio.objetoFiltro | orderBy : ['descricao', 'tipo'] | offset : objetoPagina : tamanhoPagina | limitTo: tamanhoPagina">
		                                             	 <td>{{$index + 1 + ((objetoPagina-1)*tamanhoPagina)}}</td>
		                                                 <td>{{item.tipo}}</td>
		                                                 <td>{{item.descricao}}</td>
		                                                 <td>
		                                                     <button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.quemObjetoRemover(item)"><span class="glyphicon glyphicon-trash"></span></button>
		                                                 </td>
		                                             </tr>
		                                         </tbody>
		                                         <tfoot data-ng-show="registro.objetoList.length > tamanhoPagina">
													<tr>
														<td colspan="4">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="registro.objetoList.length"
																data-ng-model="objetoPagina" data-ng-init="objetoPagina = 1"></pagination>
														</td>
													</tr>
		                                         </tfoot>
		                                     </table>
			                             </div>
			                             <div class="panel-footer text-right">
			                             	<label>Total: {{registro.objetoList.length}}</label>
											<button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalQuemObjeto"> Incluir </button>
											<button type="button" class="btn btn-danger" data-ng-click="registro.objetoList = []" data-ng-show="registro.objetoList.length"> Limpar </button>
			                             </div>
			                         </div>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.oQue">
						<accordion-heading>
							<b>O quê?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.oQue, 'glyphicon-chevron-right': !controle.abas.oQue}"></i>
								<div class="table-responsive">
								<table class="table table-condensed"  data-ng-show="!controle.abas.oQue && apoio.exibeResumo">
									<tr><td colspan="5"><b>Finalidade:</b> {{registro.finalidade == 'O' ? 'Operacional (área fim)' : 'Administrativa (área meio)'}}</td></tr>
									<tr><td colspan="5"><b>Assunto</b></td></tr>
									<tr>
										<td>#</td>
										<td><b>Classificação</b></td>
										<td><b>Ação</b></td>
										<td><b>Descrição</b></td>
										<td><b>Transversal</b></td>
									</tr>
	                                <tr data-ng-repeat="item in registro.atividadeAssuntoList | orderBy : ['assuntoAcao.assunto.nome', 'assuntoAcao.acao.nome']">
	                                    <td>{{$index + 1}}</td>
	                                    <td>{{apoio.classificacaoAcao(item.assuntoAcao.assunto)}}</td>
	                                    <td>{{item.assuntoAcao.acao.nome}}</td>
	                                    <td>{{item.descricao}}</td>
	                                    <td>{{item.transversal == 'S'? 'Sim': 'Não'}}</td>
	                                </tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-12" data-ng-class="{ 'statusInvalido' : frmCadastro.finalidade.$invalid && (!frmCadastro.finalidade.$pristine || submitted) }">
									<label class="control-label" for="finalidade">Finalidade</label>
									<div class="form-control text-center">
										<label class="radio-inline"><input type="radio" id="finalidade" name="finalidade" data-ng-model="registro.finalidade" value="O" required="required" data-ng-disabled="registro.atividadeAssuntoList != null && registro.atividadeAssuntoList.length > 0">Operacional (área fim)</label>
										<label class="radio-inline"><input type="radio" id="finalidade" name="finalidade" data-ng-model="registro.finalidade" value="A" required="required" data-ng-disabled="registro.atividadeAssuntoList != null && registro.atividadeAssuntoList.length > 0">Administrativa (área meio)</label>
									</div>
									<p data-ng-show="frmCadastro.finalidade.$invalid && (!frmCadastro.finalidade.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
							<div class="row" data-ng-show="registro.finalidade">
								<div class="form-group col-md-12" data-ng-class="{ 'statusInvalido' : !registro.atividadeAssuntoList.length && (submitted) }">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Assunto</h3>
												</div>
												<div class="col-md-6" data-ng-show="registro.atividadeAssuntoList.length">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.assuntoFiltro" data-ng-change="assuntoPagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.assuntoFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive" data-ng-show="registro.atividadeAssuntoList.length">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Classificação</th>
														<th>Ação</th>
														<th>Descrição</th>
														<th>Transversal</th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr data-ng-repeat="item in registro.atividadeAssuntoList | filter: apoio.assuntoFiltro | orderBy : ['assuntoAcao.assunto.nome', 'assuntoAcao.acao.nome'] | offset : assuntoPagina : tamanhoPagina | limitTo: tamanhoPagina">
														<td>{{$index + 1 + ((assuntoPagina-1)*tamanhoPagina)}}</td>
														<td>{{apoio.classificacaoAcao(item.assuntoAcao.assunto)}}</td>
														<td>{{item.assuntoAcao.acao.nome}}</td>
														<td><textarea class="form-control" rows="2" data-ng-model="item.descricao"></textarea></td>
														<td>{{item.transversal == 'S'? 'Sim': 'Não'}}</td>
														<td>
															<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.removerAcao(item)" ><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</tbody>
												<tfoot data-ng-show="registro.atividadeAssuntoList.length > tamanhoPagina">
													<tr>
														<td colspan="6">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="registro.atividadeAssuntoList.length"
																data-ng-model="assuntoPagina" data-ng-init="assuntoPagina = 1"></pagination>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="panel-footer text-right">
											<label>Total: {{registro.atividadeAssuntoList.length}}</label>
											<button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalAssuntoAcao" data-ng-click="apoio.incluirAcao()" data-ng-show="registro.finalidade"> Incluir </button>
											<button type="button" class="btn btn-danger" data-ng-click="registro.atividadeAssuntoList = []" data-ng-show="registro.atividadeAssuntoList.length"> Limpar </button>
										</div>
									</div>
									<p data-ng-show="!registro.atividadeAssuntoList.length && submitted" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.quando">
						<accordion-heading>
							<b>Quando?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.quando, 'glyphicon-chevron-right': !controle.abas.quando}"></i>
							<div class="table-responsive">
								<table class="table table-condensed"  data-ng-show="!controle.abas.quando && apoio.exibeResumo">
									<tr><td><b>Início:</b> {{registro.inicio | date:'dd/MM/yyyy'}}</td><td><b>Previsão de Conclusão:</b> {{registro.previsaoConclusao | date:'dd/MM/yyyy'}}</td><td><b>Conclusão:</b> {{registro.conclusao | date:'dd/MM/yyyy'}}</td></tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-4">
									<div class="row">
										<div class="col-md-8 has-feedback" data-ng-class="{ 'statusInvalido' : frmCadastro.inicio.$invalid && (!frmCadastro.inicio.$pristine || submitted) }">
											<label class="control-label" for="inicio">Início</label>
											<input type="text" class="form-control" id="inicio" name="inicio" readonly="readonly" 
												datepicker-popup="dd/MM/yyyy" data-ng-model="registro.inicio" 
												ng-click="inicio.isOpen=true" is-open="inicio.isOpen"
												 required="required" max-date="registro.previsaoConclusao">											
											<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
											<p data-ng-show="frmCadastro.inicio.$invalid && (!frmCadastro.inicio.$pristine || submitted)" class="help-block">
												Campo Obrigatório!
											</p>
										</div>
										<div class="col-md-4" data-ng-show="registro.diaTodo == 'N'">
											<timepicker ng-model="registro.inicio" show-meridian="false" minute-step="10"></timepicker>
										</div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<div class="row">
										<div class="col-md-8 has-feedback" data-ng-class="{ 'statusInvalido' : frmCadastro.previsaoConclusao.$invalid && (!frmCadastro.previsaoConclusao.$pristine || submitted) }">
											<label class="control-label" for="previsaoConclusao">Previsão de Conclusão</label>
											<input type="text" class="form-control" id="previsaoConclusao" name="previsaoConclusao" readonly="readonly" 
												datepicker-popup="dd/MM/yyyy" data-ng-model="registro.previsaoConclusao" 
												ng-click="previsaoConclusao.isOpen=true" is-open="previsaoConclusao.isOpen"
												 required="required" min-date="registro.inicio">											
											<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
											<p data-ng-show="frmCadastro.previsaoConclusao.$invalid && (!frmCadastro.previsaoConclusao.$pristine || submitted)" class="help-block">
												Campo Obrigatório!
											</p>
										</div>
										<div class="col-md-4" data-ng-show="registro.diaTodo == 'N'">
											<timepicker ng-model="registro.previsaoConclusao" show-meridian="false" minute-step="10"></timepicker>
										</div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<div class="row">
										<div class="col-md-8 has-feedback" data-ng-class="{ 'statusInvalido' : frmCadastro.conclusao.$invalid && (!frmCadastro.conclusao.$pristine || submitted) }">
											<label class="control-label" for="conclusao">Conclusão</label>
											<input type="text" class="form-control" id="conclusao" name="conclusao" readonly="readonly" 
												datepicker-popup="dd/MM/yyyy" data-ng-model="registro.conclusao" 
												data-ng-click="conclusao.isOpen=true" is-open="conclusao.isOpen" min-date="registro.inicio"
												data-ng-required="registro.situacao === 'C'">											
											<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
											<p data-ng-show="frmCadastro.conclusao.$invalid && (!frmCadastro.conclusao.$pristine || submitted)" class="help-block">
												Campo Obrigatório!
											</p>
										</div>
										<div class="col-md-4" data-ng-show="registro.diaTodo == 'N'">
											<timepicker ng-model="registro.conclusao" show-meridian="false" minute-step="10"></timepicker>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-4">
									<div class="checkbox">
										<label>
											<input type="checkbox" data-ng-model="registro.diaTodo" data-ng-true-value="S" data-ng-false-value="N"> O dia todo
										</label>
									</div>
								</div>
								<div class="form-group col-md-4" data-ng-class="{ 'statusInvalido' : frmCadastro.duracaoPrevistaFormatada.$invalid && (!frmCadastro.duracaoPrevistaFormatada.$pristine || submitted) }">
									<label class="control-label" for="duracaoPrevistaFormatada">Duração Prevista</label>
									<input type="text" class="form-control" id="duracaoPrevistaFormatada" name="duracaoPrevistaFormatada" readonly="readonly" data-ng-model="registro.duracaoPrevistaFormatada" required="required">
									<p data-ng-show="frmCadastro.duracaoPrevistaFormatada.$invalid && (!frmCadastro.duracaoPrevistaFormatada.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
								<div class="form-group col-md-4" data-ng-class="{ 'statusInvalido' : frmCadastro.conclusao.$invalid && (!frmCadastro.conclusao.$pristine || submitted) }">
									<label class="control-label" for="duracaoRealFormatada">Duração Real</label>
									<input type="text" class="form-control" id="duracaoRealFormatada" name="duracaoRealFormatada" readonly="readonly" data-ng-model="registro.duracaoRealFormatada">
									<p data-ng-show="frmCadastro.conclusao.$invalid && (!frmCadastro.conclusao.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-2">
									<button type="button" class="btn btn-primary" data-ng-model="apoio.agendamento" data-ng-disabled="registro.agendamentoTipo != '_0'" btn-checkbox>
										Agendamento
									</button>
								</div>
								<div class="form-group col-md-6">
									<div class="panel panel-default" data-ng-show="apoio.agendamento">
										<div class="panel-heading">
											<h3 class="panel-title">A partir de {{registro.inicio | date: 'dd/MM/yyyy'}} repetir esta Atividade</h3>
										</div>
										<div class="panel-body">
											<label class="radio-inline" data-ng-repeat-start="item in apoio.agendamentoTipoList">
												<input type="radio" name="agendamentoTipo" data-ng-model="registro.agendamentoTipo" data-ng-value="item.codigo">{{item.descricao}}
											</label>
											<br data-ng-repeat-end>
										</div>
									</div>
								</div>
								<div class="form-group col-md-4" data-ng-show="apoio.agendamento && registro.agendamentoTipo && registro.agendamentoTipo != '_0'"
									 data-ng-class="{ 'statusInvalido' : frmCadastro.agendamentoTipoRepeticao.$invalid && (!frmCadastro.agendamentoTipoRepeticao.$pristine || submitted) }">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h3 class="panel-title">Até</h3>
										</div>
										<div class="panel-body">
											<div class="has-feedback" data-ng-class="{ 'statusInvalido' : frmCadastro.agendamentoTipoRepeticaoData.$invalid && (!frmCadastro.agendamentoTipoRepeticaoData.$pristine || submitted) }">
												<label class="radio-inline">
													<input type="radio" name="agendamentoTipoRepeticao" value="D" data-ng-model="registro.agendamentoTipoRepeticao"
														data-ng-required="registro.agendamentoTipo !== '_0'">
													<label class="control-label">O dia</label>
												</label><br/>
												<input type="text" class="form-control" id="agendamentoTipoRepeticaoData" name="agendamentoTipoRepeticaoData" readonly="readonly" 
													datepicker-popup="dd/MM/yyyy" data-ng-model="registro.agendamentoTermino" 
													data-ng-click="agendamentoTipoRepeticaoData.isOpen=true" is-open="agendamentoTipoRepeticaoData.isOpen"
													min-date="registro.inicio" data-ng-show="registro.agendamentoTipoRepeticao == 'D'" 
													data-ng-required="registro.agendamentoTipoRepeticao === 'D' && registro.agendamentoTipo !== '_0'">
												<span class="glyphicon glyphicon-calendar form-control-feedback" data-ng-show="registro.agendamentoTipoRepeticao == 'D'"></span>
												<p data-ng-show="frmCadastro.agendamentoTipoRepeticaoData.$invalid && (!frmCadastro.agendamentoTipoRepeticaoData.$pristine || submitted)" class="help-block">
													Campo Obrigatório!
												</p>
											</div>
											<div data-ng-class="{ 'statusInvalido' : frmCadastro.agendamentoTipoRepeticaoContagem.$invalid && (!frmCadastro.agendamentoTipoRepeticaoContagem.$pristine || submitted) }">
												<label class="radio-inline">
													<input type="radio" name="agendamentoTipoRepeticao" value="C" data-ng-model="registro.agendamentoTipoRepeticao"
														data-ng-required="registro.agendamentoTipo !== '_0'">
													<label class="control-label">A contagem de vezes</label>
												</label>
												<input type="number" class="form-control" id="agendamentoTipoRepeticaoContagem" name="agendamentoTipoRepeticaoContagem" 
													data-ng-model="registro.agendamentoContagem" data-ng-show="registro.agendamentoTipoRepeticao == 'C'" data-min="1" 
													data-ng-required="registro.agendamentoTipoRepeticao === 'C' && registro.agendamentoTipo !== '_0'">
												<p data-ng-show="frmCadastro.agendamentoTipoRepeticaoContagem.$invalid && (!frmCadastro.agendamentoTipoRepeticaoContagem.$pristine || submitted)" class="help-block">
													Campo Obrigatório ou inferior a 1!
												</p>
											</div>
										</div>
									</div>
									<p data-ng-show="frmCadastro.agendamentoTipoRepeticao.$invalid && (!frmCadastro.agendamentoTipoRepeticao.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.como">
						<accordion-heading>
							<b>Como?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.como, 'glyphicon-chevron-right': !controle.abas.como}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.como && apoio.exibeResumo">
									<tr><td colspan="6"><b>Método: </b> {{apoio.getNomeMetodo(registro.metodo.id)}}</td></tr>
									<tr><td colspan="6"><b>Subatividades</b></td></tr>
                                    <tr>
                                        <td>#</td>
                                        <td>Codigo</td>
                                        <td>Ações</td>
                                        <td>Método</td>
                                        <td>Demandantes</td>
                                        <td>Executores</td>
                                    </tr>
									<tr data-ng-repeat="item in registro.subatividadeList">
										<td>{{$index + 1}}</td>
										<td class="codigoAtividade">{{item.atividade.codigo}}</td>
										<td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.atividade.atividadeAssuntoList | orderBy : ['assuntoAcao.acao.nome']">
													{{subitem.assuntoAcao.acao.nome}} [{{apoio.classificacaoAcao(subitem.assuntoAcao.assunto)}}]
												</li>
											</ul>
                                        </td>
                                        <td>{{apoio.getNomeMetodo(item.atividade.metodo.id)}}</td>
                                        <td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.atividade.atividadePessoaList | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
													{{subitem.pessoa.nome}}
												</li>
											</ul>
										</td>
										<td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.atividade.atividadePessoaList | filter : filtrarExecutor() | orderBy : ['pessoa.nome']">
													<span data-ng-class="{'texto-negrito-sublinhado' : subitem.pessoa.id==apoio.responsavel}">{{subitem.pessoa.nome}}</span>
												</li>
											</ul>
										</td>
									</tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-12" data-ng-class="{ 'statusInvalido' : frmCadastro.metodo.$invalid && (!frmCadastro.metodo.$pristine || submitted) }">
									<label class="control-label" for="metodo">Método</label>
									<select id="metodo" name="metodo" class="form-control" data-placeholder="Selecione o método" data-ng-model="registro.metodo.id" data-ng-options="item.id as item.nome for item in apoio.metodoList" required="required"></select>
									<p data-ng-show="frmCadastro.metodo.$invalid && (!frmCadastro.metodo.$pristine || submitted)" class="help-block danger error">Campo Obrigatório!</p>
								</div>
								<div class="form-group col-md-12" data-ng-show="registro.metodo.id">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Subatividades</h3>
												</div>
												<div class="col-md-6" data-ng-show="registro.subatividadeList.length">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.subatividadeFiltro" data-ng-change="subatividadePagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.subatividadeFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive" data-ng-show="registro.subatividadeList.length">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Codigo</th>
														<th>Ações</th>
														<th>Método</th>
														<th>Demandantes</th>
														<th>Executores</th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr data-ng-repeat="item in registro.subatividadeList | filter: apoio.subatividadeFiltro | orderBy : ['registro'] | offset : subatividadePagina : tamanhoPagina | limitTo: tamanhoPagina">
														<td>{{$index + 1 + ((subatividadePagina-1)*tamanhoPagina)}}</td>
														<td class="codigoAtividade">{{item.atividade.codigo}}</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.atividade.atividadeAssuntoList | orderBy : ['assuntoAcao.acao.nome']">
																	{{subitem.assuntoAcao.acao.nome}} [{{apoio.classificacaoAcao(subitem.assuntoAcao.assunto)}}]
																</li>
															</ul>
														</td>
														<td>{{apoio.getNomeMetodo(item.atividade.metodo.id)}}</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.atividade.atividadePessoaList | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
																	{{subitem.pessoa.nome}}
																</li>
															</ul>
														</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.atividade.atividadePessoaList | filter : filtrarExecutor() | orderBy : ['pessoa.nome']">
																	<span data-ng-class="{'texto-negrito-sublinhado' : subitem.pessoa.id==apoio.responsavel}">{{subitem.pessoa.nome}}</span>
																</li>
															</ul>
														</td>
														<td>
															<button type="button" class="btn btn-warning btn-xs" data-ng-click="apoio.incluirSubatividade(item.atividade.id)" data-ng-show="item.id"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
															<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.removerSubatividade(item)" ><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</tbody>
												<tfoot data-ng-show="registro.subatividadeList.length > tamanhoPagina">
													<tr>
														<td colspan="7">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="registro.subatividadeList.length"
																data-ng-model="subatividadePagina" data-ng-init="subatividadePagina = 1"></pagination>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="panel-footer text-right">
											<label>Total: {{registro.subatividadeList.length}}</label>
											<button type="button" class="btn btn-success" data-ng-click="apoio.incluirSubatividade()"> Incluir </button>
											<button type="button" class="btn btn-danger" data-ng-click="registro.subatividadeList = []" data-ng-show="registro.subatividadeList.length"> Limpar </button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.porQue">
						<accordion-heading>
							<b>Por que?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.porQue, 'glyphicon-chevron-right': !controle.abas.porQue}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.porQue && apoio.exibeResumo">
									<tr><td colspan="6"><b>Natureza: </b> {{registro.natureza == 'D' ? 'Demanda': 'Oferta'}}</td></tr>
									<tr><td colspan="6"><b>Justificativa: </b> {{registro.justificativa}}</td></tr>
									<tr><td colspan="6"><b>Atividades Principais</b></td></tr>
                                    <tr>
                                        <td>#</td>
                                        <td>Codigo</td>
                                        <td>Ações</td>
                                        <td>Método</td>
                                        <td>Demandantes</td>
                                        <td>Executores</td>
                                    </tr>
									<tr data-ng-repeat="item in registro.principalAtividadeList">
										<td>{{$index + 1}}</td>
										<td class="codigoAtividade">{{item.principalAtividade.codigo}}</td>
										<td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.principalAtividade.atividadeAssuntoList | orderBy : ['assuntoAcao.acao.nome']">
													{{subitem.assuntoAcao.acao.nome}} [{{apoio.classificacaoAcao(subitem.assuntoAcao.assunto)}}]
												</li>
											</ul>
                                        </td>
                                        <td>{{apoio.getNomeMetodo(item.principalAtividade.metodo.id)}}</td>
                                        <td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.principalAtividade.atividadePessoaList | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
													{{subitem.pessoa.nome}}
												</li>
											</ul>
										</td>
										<td>
											<ul class="unstyled">
												<li data-ng-repeat="subitem in item.principalAtividade.atividadePessoaList | filter : filtrarExecutor() | orderBy : ['pessoa.nome']">
													<span data-ng-class="{'texto-negrito-sublinhado' : subitem.pessoa.id==apoio.responsavel}">{{subitem.pessoa.nome}}</span>
												</li>
											</ul>
										</td>
									</tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-12" data-ng-class="{ 'statusInvalido' : frmCadastro.natureza.$invalid && (!frmCadastro.natureza.$pristine || submitted) }">
									<label class="control-label" for="natureza">Natureza</label>
									<div class="form-control text-center">
										<label class="radio-inline"><input type="radio" name="natureza" id="natureza" data-ng-model="registro.natureza" value="D">Demanda</label>
										<label class="radio-inline"><input type="radio" name="natureza" id="natureza" data-ng-model="registro.natureza" value="O">Oferta</label>
									</div>
									<p data-ng-show="frmCadastro.natureza.$invalid && (!frmCadastro.natureza.$pristine || submitted)" class="help-block">
										Campo Obrigatório!
									</p>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-12">
									<label class="control-label" for="justificativa">Justificativa</label>
									<textarea class="form-control" rows="5" id="justificativa" name="justificativa" data-ng-model="registro.justificativa"></textarea>
								</div>
							</div>
							<div class="row" data-ng-show="registro.natureza">
								<div class="form-group col-md-12">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="row">
												<div class="col-md-6">
													<h3 class="panel-title">Ações Principais</h3>
												</div>
												<div class="col-md-6" data-ng-show="registro.principalAtividadeList.length">
													<div class="btn-group">
														<input type="search" placeholder="filtro" data-ng-model="apoio.principalAtividadeFiltro" data-ng-change="principalAtividadePagina=1"/>
														<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.principalAtividadeFiltro = null"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="table-responsive" data-ng-show="registro.principalAtividadeList.length">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Codigo</th>
														<th>Ações</th>
														<th>Método</th>
														<th>Demandantes</th>
														<th>Executores</th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr data-ng-repeat="item in registro.principalAtividadeList | filter: apoio.principalAtividadeFiltro | orderBy : ['registro'] | offset : principalAtividadePagina : tamanhoPagina | limitTo: tamanhoPagina">
														<td>{{$index + 1 + ((principalAtividadePagina-1)*tamanhoPagina)}}</td>
														<td class="codigoAtividade">{{item.principalAtividade.codigo}}</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.principalAtividade.atividadeAssuntoList | orderBy : ['assuntoAcao.acao.nome']">
																	{{subitem.assuntoAcao.acao.nome}} [{{apoio.classificacaoAcao(subitem.assuntoAcao.assunto)}}]
																</li>
															</ul>
														</td>
														<td>{{apoio.getNomeMetodo(item.principalAtividade.metodo.id)}}</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.principalAtividade.atividadePessoaList | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
																	{{subitem.pessoa.nome}}
																</li>
															</ul>
														</td>
														<td>
															<ul class="unstyled">
																<li data-ng-repeat="subitem in item.principalAtividade.atividadePessoaList | filter : filtrarExecutor() | orderBy : ['pessoa.nome']">
																	<span data-ng-class="{'texto-negrito-sublinhado' : subitem.pessoa.id==apoio.responsavel}">{{subitem.pessoa.nome}}</span>
																</li>
															</ul>
														</td>
														<td>
															<button type="button" class="btn btn-warning btn-xs sr-only" data-ng-click="apoio.principalAtividadeEditar(item.principalAtividade.id)" data-ng-show="item.id"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
															<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.principalAtividadeRemover(item)" ><span class="glyphicon glyphicon-trash"></span></button>
														</td>
													</tr>
												</tbody>
												<tfoot data-ng-show="registro.principalAtividadeList.length > tamanhoPagina">
													<tr>
														<td colspan="7">
															<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
																data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
																data-total-items="registro.principalAtividadeList.length"
																data-ng-model="principalAtividadePagina" data-ng-init="principalAtividadePagina = 1"></pagination>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="panel-footer text-right">
											<label>Total: {{registro.principalAtividadeList.length}}</label>
											<button type="button" class="btn btn-success" data-ng-click="apoio.incluirAtividadePrincipal()"> Incluir </button>
											<button type="button" class="btn btn-danger" data-ng-click="registro.principalAtividadeList = []" data-ng-show="registro.principalAtividadeList.length"> Limpar </button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.onde">
						<accordion-heading>
							<b>Onde?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.onde, 'glyphicon-chevron-right': !controle.abas.onde}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.onde && apoio.exibeResumo">
									<tr><td><b>Longitude:</b> {{registro.latitude}}</td><td><b>Latitude:</b> {{registro.longitude}}</td></tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-6">
								    <label for="inputLatitude" class="control-label">Latitude</label>
								    <input class="form-control" id="inputLatitude" data-ng-model="registro.latitude" tag="meioContatoEndereco" name=".latitude" placeholder="Latitude" onblur="exibirNoMapa()"/>
								</div>
								<div class="form-group col-md-6">
								    <label for="inputLongitude" class="control-label">Longitude</label>
								    <input class="form-control" id="inputLongitude" data-ng-model="registro.longitude" tag="meioContatoEndereco" name=".longitude" placeholder="Longitude" onblur="exibirNoMapa()" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-3 text-center">
									<button class="btn btn-success">No meu escritorio</button>
								</div>
								<div class="form-group col-md-3 text-center">
									<button class="btn btn-success">Em locais conhecidos...</button>
								</div>
								<div class="form-group col-md-3 text-center">
									<button class="btn btn-success">Em locais recentes...</button>
								</div>
								<div class="form-group col-md-3 text-center sr-only">
									<button type="button" class="btn btn-success" onclick="event.preventDefault();initialize();">Exibir Mapa</button>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-offset-2 col-md-8 text-center">
									<div class="input-group">
										<input type="text" class="form-control" 
											class="form-control" id="txtEndereco" name="txtEndereco" placeholder="Digite um endereço" onblur="exibirNoMapa()">
										<span class="input-group-btn sr-only">
											<button type="button" id="btnEndereco" name="btnEndereco" class="btn btn-danger btn-md" onclick="$('#txtEndereco').val('');"><span class="glyphicon glyphicon-trash"></span></button>
										</span>
										<i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-12">
									<div id="map-canvas"></div>
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.quantoCusta">
						<accordion-heading>
							<b>Quanto custa?</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.quantoCusta, 'glyphicon-chevron-right': !controle.abas.quantoCusta}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.quantoCusta && apoio.exibeResumo">
									<tr><td><b>Valor Estimado:</b> {{registro.valorEstimado}}</td><td><b>Valor Real:</b> {{registro.valorReal}}</td></tr>
								</table>
							</div>
						</accordion-heading>
						<div class="container-fluid">
							<div class="row">
								<div class="form-group col-md-6">
									<label class="control-label" for="valorEstimado">Valor Estimado</label>
									<input type="number" class="form-control" id="valorEstimado" name="valorEstimado" data-ng-model="registro.valorEstimado">
								</div>
								<div class="form-group col-md-6">
									<label class="control-label" for="valorReal">Valor Real</label>
									<input type="number" class="form-control" id="valorReal" name="valorReal" data-ng-model="registro.valorReal">
								</div>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.arquivos">
						<accordion-heading>
							<b>Arquivos</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.arquivos, 'glyphicon-chevron-right': !controle.abas.arquivos}"></i>
							<div class="table-responsive">
								<table class="table table-condensed" data-ng-show="!controle.abas.arquivos && apoio.exibeResumo">
									<tr>
										<th>#</th>
										<th>Nome</th>
										<th>Formato</th>
										<th>Data Upload</th>
										<th>Descricao</th>
									</tr>
									<tr data-ng-repeat="item in registro.atividadeArquivoList | orderBy : ['arquivo.nome']">
										<td>{{$index + 1}}</td>
										<td>{{item.arquivo.nome}}</td>
										<td>{{item.arquivo.tipo}}</td>
										<td>{{item.dataUpload | date:'dd/MM/yyyy HH:mm'}}</td>
										<td>{{item.descricao}}</td>
									</tr>
								</table>
							</div>
						</accordion-heading>
						<div class="form-group col-md-12">
							<div class="panel panel-default" data-ng-class="{ 'statusInvalido' : apoio.arquivoTamanhoTotal() > tamanhoMaxArquivo && submitted }">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-6">
											<h3 class="panel-title">Arquivos Anexos</h3>
										</div>
										<div class="col-md-6" data-ng-show="registro.atividadeArquivoList.length">
											<div class="btn-group">
												<input type="search" placeholder="filtro" data-ng-model="apoio.arquivosAnexosFiltro" data-ng-change="arquivosAnexosPagina=1"/>
												<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.arquivosAnexosFiltro = null"></span>
											</div>
										</div>
									</div>
								</div>
								<div class="table-responsive" data-ng-show="registro.atividadeArquivoList.length">
									<input type="hidden" name="arquivoTamanhoTotal" data-ng-model="lixo3">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Nome</th>
												<th>Formato</th>
												<th>Data Upload</th>
												<th>Tamanho</th>
												<th>Descricao</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="item in registro.atividadeArquivoList | filter: apoio.arquivosAnexosFiltro | orderBy : ['arquivo.nome'] | offset : arquivosAnexosPagina : tamanhoPagina | limitTo: tamanhoPagina">
												<td>{{$index + 1 + ((arquivosAnexosPagina-1)*tamanhoPagina)}}</td>
												<td>{{item.arquivo.nome}}</td>
												<td>{{item.arquivo.tipo}}</td>
												<td>{{item.dataUpload | date:'dd/MM/yyyy HH:mm:ss'}}</td>
												<td>{{apoio.arquivoTamanhoTotalFormatado(item.arquivo.tamanho)}}</td>
												<td><textarea class="form-control" rows="2" data-ng-model="item.descricao"></textarea></td>
												<td>
													<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.removerArquivo(item)" ><span class="glyphicon glyphicon-trash"></span></button>
												</td>
											</tr>
										</tbody>
										<tfoot>
											<tr>
												<td colspan="7">
													<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
														data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
														data-total-items="registro.atividadeArquivoList.length"
														data-ng-model="arquivosAnexosPagina" data-ng-init="arquivosAnexosPagina = 1"
														 data-ng-show="registro.atividadeArquivoList.length > tamanhoPagina"></pagination>
													<label data-ng-class="{'texto-erro': apoio.arquivoTamanhoTotal() > tamanhoMaxArquivo}">Tamanho dos arquivos anexos {{apoio.arquivoTamanhoTotalFormatado(apoio.arquivoTamanhoTotal())}}</label>
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
								<div class="panel-footer">
									<div class="row">
										<div class="col-md-8">
											<button data-ng-show="dropSupported" class="drop-boxx hidden-xs hidden-sm" data-ng-file-drop="onFileSelect($files);" data-ng-file-drop-available="dropSupported=true">
												<b>Solte arquivos aqui</b>
											</button>
											<div data-ng-show="!dropSupported">HTML5 Drop File não suportado em seu navegador</div>
										</div>
										<div class="col-md-4 text-right">
											<label>Total: {{registro.atividadeArquivoList.length}}</label>
											<input id="input_file" type="file" class="hide" data-ng-file-select="onFileSelect($files)" multiple="true"> 
											<button class="btn btn-success" onclick="$('#input_file').click()">Incluir</button>
											<button type="button" class="btn btn-danger" data-ng-click="apoio.limparArquivo()" data-ng-show="registro.atividadeArquivoList.length"> Limpar </button>
										</div>
									</div>
								</div>
								<p data-ng-show="apoio.arquivoTamanhoTotal() > tamanhoMaxArquivo && submitted" class="help-block">
									Tamanho total dos arquivos anexos {{apoio.arquivoTamanhoTotalFormatado(apoio.arquivoTamanhoTotal())}} superior ao máximo permitido que é de {{apoio.arquivoTamanhoTotalFormatado(tamanhoMaxArquivo)}}
								</p>
							</div>
						</div>
					</accordion-group>
					<accordion-group is-open="controle.abas.complemento" data-ng-show="registro.atividadeAssuntoList.length > 0">
						<accordion-heading>
							<b>Complemento</b>
							<i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': controle.abas.complemento, 'glyphicon-chevron-right': !controle.abas.complemento}"></i>
						</accordion-heading>
					</accordion-group>
				</accordion>
				<button class="sr-only" data-ng-click="ocorrlog = !ocorrlog">[]</button>
				<div class="container-fluid" data-ng-show="ocorrlog || registro.id">
					<div class="row">
						<div class="form-group col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-6">
											<h3 class="panel-title">Log do registro de ocorrências</h3>
										</div>
										<div class="col-md-6" data-ng-show="registro.ocorrenciaList.length">
											<div class="btn-group">
												<input type="search" placeholder="filtro" data-ng-model="apoio.ocorrenciaFiltro" data-ng-change="ocorrenciaPagina=1"/>
												<span type="button" class="glyphicon glyphicon-remove-circle form-inline" data-ng-click="apoio.ocorrenciaFiltro = null"></span>
											</div>
										</div>
									</div>
								</div>
								<div class="table-responsive" data-ng-show="registro.ocorrenciaList.length">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Data do Registro</th>
												<th>Registrado Por</th>
												<th>Situação</th>
												<th>% Conclusão</th>
												<th>Prioridade</th>
												<th>Incidente</th>
												<th>Relato</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="item in registro.ocorrenciaList | filter: apoio.ocorrenciaFiltro | orderBy : ['-registro'] | offset : ocorrenciaPagina : tamanhoPagina | limitTo: tamanhoPagina">
												<td>{{$index + 1 + ((ocorrenciaPagina-1)*tamanhoPagina)}}</td>
												<td>{{item.registro | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
												<td>{{item.usuario.pessoa.nome}}</td>
												<td>{{apoio.getNomeAtividadeSituacao(item.situacao)}}
													<table class="table" data-ng-show="item.situacao === 'S'">
														<tr>
															<td>Inicio</td>
															<td>{{item.inicioSuspensao | date: 'dd/MM/yyyy'}}</td>
														</tr>
														<tr>
															<td>Término</td>
															<td>
																<span data-ng-hide="$first && item.terminoSuspensao">{{item.terminoSuspensao | date: 'dd/MM/yyyy'}}</span>
																<div data-ng-show="$first" class="has-feedback">
																	<input type="text" class="form-control input-md" id="ocorrenciaTerminoSuspensaoS" name="ocorrenciaTerminoSuspensaoS" readonly="readonly"
																		placeholder="Término da Suspensão" datepicker-popup="dd/MM/yyyy" data-ng-model="item.terminoSuspensao" 
																		ng-click="ocorrenciaTerminoSuspensaoS.isOpen=true" is-open="ocorrenciaTerminoSuspensaoS.isOpen"
																		min-date="item.inicioSuspensao">
																</div>
															</td>
														</tr>
														<tr>
															<td>Motivo</td>
															<td>{{item.motivoSuspensao}}</td>
														</tr>
													</table>
												</td>
												<td nowrap>
													<div class="form-control"  
														data-ng-class="{'texto-branco': item.percentualConclusao > 0,'': item.percentualConclusao<1, 'label-warning': item.percentualConclusao>=1 && item.percentualConclusao<3, 'label-info': item.percentualConclusao>=3 && item.percentualConclusao<7, 'label-success': item.percentualConclusao>=7}">
														<rating ng-model="item.percentualConclusao" min="0" max="10" readonly="true"></rating>
						   								<span class="label text-nowrap" data-ng-class="{'label-warning': percent<30, 'label-info': percent>=30 && percent<70, 'label-success': percent>=70}" data-ng-show="overStar && !isReadonly">{{percent}}%</span>
						   								<span class="pull-right text-nowrap">{{item.percentualConclusao*10}}%</span>
						   							</div>
												</td>
												<td>{{apoio.getNomeAtividadePrioridade(item.prioridade)}}</td>
												<td>{{apoio.getNomeConfirmacao(item.incidente)}}</td>
												<td>{{item.relato}}</td>
												<td>
													<span data-ng-show="($index + 1 + ((ocorrenciaPagina-1)*tamanhoPagina)) === 1">
														<button type="button" class="btn btn-warning btn-xs" data-ng-click="apoio.ocorrenciaEditar(item)" data-toggle="modal" data-target="#modalOcorrencias"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
														<button type="button" class="btn btn-danger btn-xs" data-ng-click="apoio.ocorrenciaRemover(item)" ><span class="glyphicon glyphicon-trash"></span></button>
													</span>
												</td>
											</tr>
										</tbody>
										<tfoot data-ng-show="registro.ocorrenciaList.length > tamanhoPagina">
											<tr>
												<td colspan="9">
													<pagination style="margin:0px;" data-previous-text="&lsaquo;" data-next-text="&rsaquo;" data-first-text="&laquo;" data-last-text="&raquo;"
														data-boundary-links="true" data-max-size="5" data-items-per-page="tamanhoPagina" class="pagination-sm"
														data-total-items="registro.ocorrenciaList.length"
														data-ng-model="ocorrenciaPagina" data-ng-init="ocorrenciaPagina = 1"></pagination>
												</td>
											</tr>
										</tfoot>
									</table>
								</div>
								<div class="panel-footer text-right">
									<label>Total: {{registro.ocorrenciaList.length}}</label>
									<button type="button" class="btn btn-success" data-ng-click="apoio.ocorrenciaIncluir(registro)" data-toggle="modal" data-target="#modalOcorrencias" data-ng-hide="apoio.ocorrenciaPodeIncluir()"> Incluir </button>
									<button type="button" class="btn btn-danger" data-ng-click="registro.ocorrenciaList = []" data-ng-show="registro.ocorrenciaList.length"> Limpar </button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="col-md-8 botoes_rodape" data-ng-show="cadastroSubatividade">
	<button data-ng-click="popupSalvarSubatividade()" class="btn btn-success">Salvar</button>
	<button onclick="window.close()" class="btn btn-danger">Cancelar</button>
</div>

<div class="modal fade formulario" id="modalQuemObjeto" tabindex="-1" data-role="dialog" data-aria-labelledby="Objeto da Atividade" data-aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Objeto da Atividade</h4>
			</div>
			<form name="frmQuemObjeto" novalidate>
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="radio-inline"><input type="radio" name="tipoObjeto" value="T" data-ng-init="apoio.quem.objeto.tipo = 'T'" data-ng-model="apoio.quem.objeto.tipo">Todos os demandantes</label>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label class="radio-inline"><input type="radio" name="tipoObjeto" value="D" data-ng-model="apoio.quem.objeto.tipo" data-ng-click="apoio.quem.objeto.pessoa = null; apoio.quem.objeto.subTipo = null">O demandante</label>
							<div class="panel panel-success" data-ng-show="apoio.quem.objeto.tipo && apoio.quem.objeto.tipo != 'T' && apoio.quem.objeto.tipo != 'O'">
								<div class="panel-body">
									<label class="radio-inline" data-ng-repeat-start="item in registro.atividadePessoaList | filter: apoio.demandanteFiltro | filter : filtrarDemandante() | orderBy : ['pessoa.nome']">
										<input type="radio" name="demandante" data-ng-value="item.pessoa.id" data-ng-model="apoio.quem.objeto.pessoa" data-ng-click="apoio.quem.objeto.subTipo = null; apoio.quem.objeto.localPropriedade = null; apoio.quem.objeto.producao = null; apoio.quem.objeto.bemPatrimonial = null;">
										{{item.pessoa.nome}}
									</label>
									<br data-ng-repeat-end>
								</div>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="radio-inline"><input type="radio" name="tipoObjeto" value="O" data-ng-model="apoio.quem.objeto.tipo" data-ng-click="apoio.quem.objeto.pessoa = null; apoio.quem.objeto.subTipo = null">Outra pessoa</label>
							<div class="form-group"  data-ng-show="apoio.quem.objeto.tipo && apoio.quem.objeto.tipo != 'T' && apoio.quem.objeto.tipo != 'D'">
								<span data-ng-bind="apoio.outraPessoa.nome"></span>
								<button class="btn btn-primary" data-ng-click="apoio.incluirOutraPessoa()">Pesquisar</button>
							</div>
						</div>
					</div>
					<div class="row" data-ng-show="apoio.quem.objeto.tipo && apoio.quem.objeto.tipo != 'T' && apoio.quem.objeto.pessoa">
						<div class="form-group col-md-6">
							<label class="radio-inline"><input type="radio" name="subTipoObjeto" value="L" data-ng-model="apoio.quem.objeto.subTipo">Local ou propriedade</label>
							<div class="panel panel-success">
								<div class="panel-body">
									<label class="radio-inline"><input type="radio" name="localPropriedade" value="1" data-ng-model="apoio.quem.objeto.localPropriedade" data-ng-click="apoio.quem.objeto.subTipo = 'L'">Chacara 1</label><br/>
									<label class="radio-inline"><input type="radio" name="localPropriedade" value="2" data-ng-model="apoio.quem.objeto.localPropriedade" data-ng-click="apoio.quem.objeto.subTipo = 'L'">Chacara Santa Rosa</label><br/>
									<label class="radio-inline"><input type="radio" name="localPropriedade" value="3" data-ng-model="apoio.quem.objeto.localPropriedade" data-ng-click="apoio.quem.objeto.subTipo = 'L'">Chacara Mazé</label>
								</div>
							</div>
						</div>
						<div class="form-group col-md-6" data-ng-show="apoio.quem.objeto.localPropriedade">
							<label class="radio-inline"><input type="radio" name="subTipoObjeto" value="P" data-ng-model="apoio.quem.objeto.subTipo">Produção</label>
							<div class="panel panel-success" data-ng-show="apoio.quem.objeto.localPropriedade">
								<div class="panel-body">
									<label class="radio-inline"><input type="radio" name="producao" value="1" data-ng-model="apoio.quem.objeto.producao" data-ng-click="apoio.quem.objeto.subTipo = 'P'">Alface 1 ha</label><br/>
									<label class="radio-inline"><input type="radio" name="producao" value="2" data-ng-model="apoio.quem.objeto.producao" data-ng-click="apoio.quem.objeto.subTipo = 'P'">Abacate 30 pes</label><br/>
									<label class="radio-inline"><input type="radio" name="producao" value="3" data-ng-model="apoio.quem.objeto.producao" data-ng-click="apoio.quem.objeto.subTipo = 'P'">Gado 50 cab</label>
								</div>
							</div>
						</div>
					</div>
					<div class="row" data-ng-show="apoio.quem.objeto.tipo && apoio.quem.objeto.tipo != 'T' && apoio.quem.objeto.pessoa">
						<div class="form-group col-md-12" data-ng-show="apoio.quem.objeto.localPropriedade">
							<label class="radio-inline"><input type="radio" name="subTipoObjeto" value="B" data-ng-model="apoio.quem.objeto.subTipo">Bem patrimonial</label>
							<div class="panel-body">
								<label class="radio-inline"><input type="radio" name="bemPatrimonial" value="1" data-ng-model="apoio.quem.objeto.bemPatrimonial" data-ng-click="apoio.quem.objeto.subTipo = 'B'">Trator ZYX</label><br/>
								<label class="radio-inline"><input type="radio" name="bemPatrimonial" value="2" data-ng-model="apoio.quem.objeto.bemPatrimonial" data-ng-click="apoio.quem.objeto.subTipo = 'B'">Pivo de Irrigação</label><br/>
								<label class="radio-inline" data-ng-show="apoio.quem.objeto.localPropriedade"><input type="radio" name="bemPatrimonial" value="3" data-ng-model="apoio.quem.objeto.bemPatrimonial" data-ng-click="apoio.quem.objeto.subTipo = 'B'">Cavalo</label>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.quem.objeto.limpar()">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-ng-click="apoio.quemObjetoSalvar()">Salvar</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade formulario" id="modalOcorrencias" tabindex="-1" data-role="dialog" data-aria-labelledby="Ocorrências" data-aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Registro de Ocorrência</h4>
			</div>
			<div class="modal-body">
<form class="form-horizontal" name="frmOcorrencias" novalidate>
	<input type="hidden" data-ng-model="apoio.ocorrencia.id">
	<fieldset>
		<div class="form-group" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaRegistro.$invalid && (!frmOcorrencias.ocorrenciaRegistro.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaRegistro">Data do Registro</label>
			<div class="col-md-4">
				<input type="text" class="form-control input-md" id="ocorrenciaRegistro" name="ocorrenciaRegistro" readonly="readonly"
					placeholder="Registro" datepicker-popup="dd/MM/yyyy HH:mm:ss" data-ng-model="apoio.ocorrencia.registro" 
					is-open="false" required="required">											
				<p data-ng-show="frmOcorrencias.ocorrenciaRegistro.$invalid && (!frmOcorrencias.ocorrenciaRegistro.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaRegistradoPor.$invalid && (!frmOcorrencias.ocorrenciaRegistradoPor.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaRegistradoPor">Registrado Por</label>
			<div class="col-md-4">
				<input id="ocorrenciaRegistradoPor" name="ocorrenciaRegistradoPor"
					type="text" placeholder="Registrado Por"
					class="form-control input-md" required="required" readonly="readonly"
					data-ng-model="apoio.ocorrencia.usuario.pessoa.nome">
				<p data-ng-show="frmOcorrencias.ocorrenciaRegistradoPor.$invalid && (!frmOcorrencias.ocorrenciaRegistradoPor.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaRelato.$invalid && (!frmOcorrencias.ocorrenciaRelato.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaRelato">Relato</label>
			<div class="col-md-6">
				<textarea class="form-control" id="ocorrenciaRelato" name="ocorrenciaRelato" required="required" data-ng-model="apoio.ocorrencia.relato"></textarea>
				<p data-ng-show="frmOcorrencias.ocorrenciaRelato.$invalid && (!frmOcorrencias.ocorrenciaRelato.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label" for="ocorrenciaIncidente">É um incidente?</label>
			<div class="col-md-4">
				<div class="checkbox">
					<label for="ocorrenciaIncidente-0"> <input type="checkbox"
						name="ocorrenciaIncidente" id="ocorrenciaIncidente-0" data-ng-true-value="S" data-ng-false-value="N"
						data-ng-model="apoio.ocorrencia.incidente">
						Sim
					</label>
				</div>
			</div>
		</div>
		<div class="form-group" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaSituacao.$invalid && (!frmOcorrencias.ocorrenciaSituacao.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaSituacao">Situação</label>
			<div class="col-md-4">
				<select id="ocorrenciaSituacao" name="ocorrenciaSituacao" class="form-control" data-ng-model="apoio.ocorrencia.situacao" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.atividadeSituacaoListOcorrencia"></select>
				<p data-ng-show="frmOcorrencias.ocorrenciaSituacao.$invalid && (!frmOcorrencias.ocorrenciaSituacao.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group" data-ng-show="apoio.ocorrencia.situacao == 'N' || apoio.ocorrencia.situacao == 'E' || apoio.ocorrencia.situacao == 'C'">
			<label class="col-md-4 control-label" for="ocorrenciaPercentualConclusao">% Conclusão</label>
			<div class="col-md-4">
				<div id="ocorrenciaPercentualConclusao" name="ocorrenciaPercentualConclusao" class="form-control" data-ng-class="{'texto-branco': apoio.ocorrencia.percentualConclusao > 0,'': apoio.ocorrencia.percentualConclusao<1, 'label-warning': apoio.ocorrencia.percentualConclusao>=1 && apoio.ocorrencia.percentualConclusao<3, 'label-info': apoio.ocorrencia.percentualConclusao>=3 && apoio.ocorrencia.percentualConclusao<7, 'label-success': apoio.ocorrencia.percentualConclusao>=7}">
					<rating ng-model="apoio.ocorrencia.percentualConclusao" min="0" max="10" on-hover="hoveringOver(value)" on-leave="overStar = null"></rating>
					<span class="label" data-ng-class="{'label-warning': percent<30, 'label-info': percent>=30 && percent<70, 'label-success': percent>=70}" data-ng-show="overStar && !isReadonly">{{percent}}%</span>
					<span class="pull-right">{{apoio.ocorrencia.percentualConclusao*10}}%</span>
				</div>
			</div>
		</div>
		<div class="form-group" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaPrioridade.$invalid && (!frmOcorrencias.ocorrenciaPrioridade.$pristine || ocorenciaSubmitted) }" data-ng-show="apoio.ocorrencia.situacao == 'N' || apoio.ocorrencia.situacao == 'E' || apoio.ocorrencia.situacao == 'C'">
			<label class="col-md-4 control-label" for="ocorrenciaPrioridade">Prioridade</label>
			<div class="col-md-4">
				<div class="form-control text-center" id="ocorrenciaPrioridade" name="ocorrenciaPrioridade">
					<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="apoio.ocorrencia.prioridade" value="N" required="required">Normal</label>
					<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="apoio.ocorrencia.prioridade" value="B" required="required">Baixa</label>
					<label class="radio-inline"><input type="radio" name="prioridade" data-ng-model="apoio.ocorrencia.prioridade" value="A" required="required">Alta</label>
				</div>
				<p data-ng-show="frmOcorrencias.ocorrenciaPrioridade.$invalid && (!frmOcorrencias.ocorrenciaPrioridade.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group" data-ng-show="apoio.ocorrencia.situacao === 'S'" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaInicioSuspensao.$invalid && (!frmOcorrencias.ocorrenciaInicioSuspensao.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaInicioSuspensao">Início da Suspensão</label>
			<div class="col-md-4">
				<input type="text" class="form-control input-md" id="ocorrenciaInicioSuspensao" name="ocorrenciaInicioSuspensao" readonly="readonly"
					placeholder="Início da Suspensão" datepicker-popup="dd/MM/yyyy" data-ng-model="apoio.ocorrencia.inicioSuspensao" 
					ng-click="ocorrenciaInicioSuspensao.isOpen=true" is-open="ocorrenciaInicioSuspensao.isOpen"
					max-date="apoio.ocorrencia.terminoSuspensao" data-ng-required="apoio.ocorrencia.situacao === 'S'">											
				<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
				<p data-ng-show="frmOcorrencias.ocorrenciaInicioSuspensao.$invalid && (!frmOcorrencias.ocorrenciaInicioSuspensao.$pristine || ocorenciaSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group" data-ng-show="apoio.ocorrencia.situacao === 'S'">
			<label class="col-md-4 control-label" for="ocorrenciaTerminoSuspensao">Término da Suspensão</label>
			<div class="col-md-4">
				<input type="text" class="form-control input-md" id="ocorrenciaTerminoSuspensao" name="ocorrenciaTerminoSuspensao" readonly="readonly"
					placeholder="Término da Suspensão" datepicker-popup="dd/MM/yyyy" data-ng-model="apoio.ocorrencia.terminoSuspensao" 
					ng-click="ocorrenciaTerminoSuspensao.isOpen=true" is-open="ocorrenciaTerminoSuspensao.isOpen"
					min-date="apoio.ocorrencia.inicioSuspensao">
				<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
			</div>
		</div>
		<div class="form-group" data-ng-show="apoio.ocorrencia.situacao === 'S'" data-ng-class="{ 'statusInvalido' : frmOcorrencias.ocorrenciaMotivoSuspensao.$invalid && (!frmOcorrencias.ocorrenciaMotivoSuspensao.$pristine || ocorenciaSubmitted) }">
			<label class="col-md-4 control-label" for="ocorrenciaMotivoSuspensao">Motivo da Suspensão</label>
			<div class="col-md-6">
				<textarea class="form-control" id="ocorrenciaMotivoSuspensao" name="ocorrenciaMotivoSuspensao" data-ng-model="apoio.ocorrencia.motivoSuspensao" data-ng-required="apoio.ocorrencia.situacao === 'S'"></textarea>
			</div>
			<p data-ng-show="frmOcorrencias.ocorrenciaMotivoSuspensao.$invalid && (!frmOcorrencias.ocorrenciaMotivoSuspensao.$pristine || ocorenciaSubmitted)" class="help-block">
				Campo Obrigatório!
			</p>
		</div>
	</fieldset>
</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.ocorrenciaLimpar()">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-ng-click="apoio.ocorrenciaSalvar(frmOcorrencias.$valid)">Salvar</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade formulario" id="modalAssuntoAcao" tabindex="-1" data-role="dialog" data-aria-labelledby="Assunto e Ações da Atividade" data-aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
<form name="frmAssuntoAcao" novalidate>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">Assunto e Ações da Atividade</h4>
	</div>
	<div class="modal-body">
		<div class="form-group">
			<label class="col-md-4 control-label" for="assunto">Assunto e Ação</label>
			<div class="col-md-7" data-ng-class="{ 'statusInvalido' : frmAssuntoAcao.assuntoAcao.$invalid && (!frmAssuntoAcao.assuntoAcao.$pristine || assuntoAcaoSubmitted) }">
				<input type="hidden" name="assuntoAcao" data-ng-model="apoio.atividadeAssunto.assuntoAcao" data-ng-value="" required>
				<div class="row" style="height: 300px; overflow: auto;">                     
					<script type="text/ng-template" id="items_renderer_sub.html" charset="UTF-8">
						<div data-ui-tree-handle>
							<label>
								<input class="Tooltip" title="Selecione" type="radio" name="assuntoAcao" data-ng-model="apoio.atividadeAssunto.assuntoAcao" data-ng-value="assuntoAcao" required>
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
					<div data-ui-tree id="treeAtividadeAssunto" data-spacing="2" data-drag-enabled="false">
						<ol data-ui-tree-nodes="" data-ng-model="apoio.atividadeAssuntoList">
							<li data-ng-repeat="linha in apoio.atividadeAssuntoList" data-ui-tree-node="" data-ng-include="'items_renderer.html'" data-collapsed="true"></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="label label-default" data-ng-show="apoio.registroSelecaoAssuntoAcao.length > 0" data-ng-click="apoio.registroLimparSelecaoAssuntoAcao()">{{apoio.registroSelecaoAssuntoAcao.length}} item(s) selecionado(s)</div>
				</div>
				<p data-ng-show="frmAssuntoAcao.assuntoAcao.$invalid && (!frmAssuntoAcao.assuntoAcao.$pristine || assuntoAcaoSubmitted)" class="help-block">
					Campo Obrigatório!
				</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label" for="assunto">Descrição das Ações Executadas</label>
			<div class="col-md-7">
				<textarea class="form-control" rows="5" data-ng-model="apoio.atividadeAssunto.descricao"></textarea>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.pessoaLimpar()">Cancelar</button>
		<button type="button" class="btn btn-primary salvar" data-ng-click="apoio.acaoSalvar(frmAssuntoAcao.$valid)">Salvar</button>
	</div>
</form>
		</div>
	</div>
</div>
