<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<jsp:include page="/tiles/_modelo/cabecalho.jsp" />

<jsp:include page="menu.jsp" />

<div class="container-fluid" data-ng-controller="cadastroCtrl">
    <div class="row">
	    <div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><tiles:insertAttribute name="nome" /></h3>
				</div>
				<div class="panel-body">
					<ul class="nav nav-tabs">
						<li role="presentation" id="navEntrada" class="active">
							<a href="#entrada" data-toggle="tab">Entrada</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane fade in active" id="entrada">
							<div style="position: relative;border: 0px solid #aaa;height: 400px;">
								<bg-splitter orientation="vertical">
									<bg-pane min-size="150">
										<bg-splitter orientation="horizontal">
											<bg-pane min-size="50">
												<div data-angular-treeview="true" 
													data-tree-id="contasTree"
													data-tree-model="apoio.contas" 
													data-node-id="id"
													data-node-label="nome" 
													data-node-children="filhos">
												</div>
											</bg-pane>
											<bg-pane min-size="50">
									            <div class="table-responsive">
									                <table class="table table-striped table-hover">
									                    <thead>
									                        <tr>
									                            <th width="10">
									                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
									                            </th>
									                            <th>Data</th>
									                            <th>Estrela</th>
									                            <th>Anexo</th>
									                            <th>Assunto</th>
									                            <th>De</th>
									                        </tr>
									                    </thead>
									                    <tbody id="listagem">
									                        <tr data-ng-repeat="linha in lista" data-ng-click="selecionado(linha, $event)">
									                            <td>
									                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
									                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
									                            </td>
									                            <td>{{linha.codigo}}</td>
									                            <td>{{linha.codigo}}</td>
									                            <td>{{linha.codigo}}</td>
									                            <td>{{linha.codigo}}</td>
									                            <td>{{linha.codigo}}</td>
									                        </tr>
									                    </tbody>
									                </table>
									            </div>
											</bg-pane>
										</bg-splitter>
									</bg-pane>
									<bg-pane min-size="100">
										<div class="container-fluid">
											<div class="row">
												<nav class="navbar navbar-default navbar-inverse" role="navigation">
												    <div class="container-fluid">
														<div class="navbar-header">
															<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu_mensagem-navbar-collapse-1">
																<span class="sr-only">Alternar Navegação</span>
																<span class="icon-bar"></span>
																<span class="icon-bar"></span>
																<span class="icon-bar"></span>
															</button>
														</div>		
												        <div class="collapse navbar-collapse" id="menu_mensagem-navbar-collapse-1">
															<ul class="nav navbar-nav">
															    <li><a href="email"><i class="glyphicon glyphicon-envelope"></i> Responder </a></li>
															    <li><a href="email"><i class="glyphicon glyphicon-envelope"></i> Responder a Todos </a></li>
															    <li><a href="email"><i class="glyphicon glyphicon-envelope"></i> Encaminhar </a></li>
															    <li><a href="email"><i class="glyphicon glyphicon-folder-close"></i> Arquivar </a></li>
															    <li><a href="email"><i class="glyphicon glyphicon-trash"></i> Excluir </a></li>
												            </ul>
												        </div>
												    </div>
												</nav>
											</div>
										</div>
										<div class="row">
											<div class="col-xs-2">
												<label>De</label>
											</div>
											<div class="col-xs-10">
												<label>Fulano</label>
											</div>
										</div>
										<div class="row">
											<div class="col-xs-2">
												<label>Assunto</label>
											</div>
											<div class="col-xs-10">
												<label>Bla bla bla</label>
											</div>
										</div>
										<div class="row">
											<div class="col-xs-2">
												<label>Para</label>
											</div>
											<div class="col-xs-10">
												<label>Ciclano</label>
											</div>
										</div>
									</bg-pane>
								</bg-splitter>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/tiles/email/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/tiles/email/script.js" charset="UTF-8"></script>
