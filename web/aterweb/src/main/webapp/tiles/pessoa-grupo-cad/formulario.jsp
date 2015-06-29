<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_formulario">
	<div class="panel panel-success">
		<div class="panel-heading">
			<h3 class="panel-title">Formulário</h3>
		</div>
		<div class="panel-body">
			<form>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
				<input type="hidden" name="id" data-ng-model="registro.id" />
				<input type="hidden" name="@class" data-ng-model="registro['@class']" />
				<input type="hidden" name="pessoaTipo" data-ng-model="registro.pessoaTipo" /> 
				<input type="hidden" name="pessoaGrupoTipo" data-ng-model="registro.pessoaGrupoTipo" />
				<input type="hidden" name="publicoAlvoConfirmacao" data-ng-model="registro.publicoAlvoConfirmacao" /> 
				<input type="hidden" name="pai" data-ng-model="registro.pai" />
				<div class="row">
					<div class="form-group col-md-12" data-ng-show="registro.id == null">
						<label for="selecionado" class="control-label">Novo Grupo Social - Selecionado [{{linhasSelecionadas === null || linhasSelecionadas.length === 0 ? "raiz" : linhasSelecionadas[0].nome}}] </label><br/>
						<div class="btn-group">
							<label class="Tooltip btn" data-title="No mesmo nível">
								<input type="radio" name="selecionado" data-ng-model="registroLocal" value="mesmoNivel" data-ng-disabled="linhasSelecionadas === null || linhasSelecionadas.length === 0">
								<img src="resources/img/no-mesmo-nivel.png" height="35px" width="35px"/>
								No mesmo nível
							</label>
							<label class="Tooltip btn" data-title="Como um Subgrupo">
								<input type="radio" name="selecionado" data-ng-model="registroLocal" value="noDescendente">
								<img src="resources/img/no-sub-nivel.png" height="35px" width="35px"/>
								Como um Subgrupo de [{{linhasSelecionadas === null || linhasSelecionadas.length === 0 ? "raiz" : linhasSelecionadas[0].nome}}]
							</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-5">
						<label for="nome" class="control-label">Nome do Grupo Social</label>
						<input class="form-control" id="nome" name="nome" placeholder="Nome do Grupo Social" data-ng-model="registro.nome" />
					</div>
					<div class="form-group col-md-3">
						<label for="nome" class="control-label">Sigla do Grupo Social</label>
						<input class="form-control" id="nome" name="nome" placeholder="Nome do Grupo Social" data-ng-model="registro.apelidoSigla" />
					</div>
					<div class="form-group col-md-4">
						<label for="gestorGrupo" class="control-label">Nível de Gestão do Grupo Social</label>
						<div class="btn-group">
							<label class="Tooltip btn" data-title="Técnico">
								<input type="radio" name="nivelGestao" data-ng-model="registro.nivelGestao" value="T" />
								<img src="resources/img/grupo-tecnico.png" height="35px" width="35px"/>
							</label>
							<label class="Tooltip btn" data-title="Unidade Organizacional">
								<input type="radio" name="nivelGestao" data-ng-model="registro.nivelGestao" value="U" />
								<img src="resources/img/grupo-unidade-organizacional.png" height="35px" width="35px"/>
							</label> 
							<label class="Tooltip btn" data-title="Instituição">
								<input type="radio" name="nivelGestao" data-ng-model="registro.nivelGestao" value="I" />
								<img src="resources/img/grupo-instituicao.png" height="35px" width="35px"/>
							</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-12">
						<label for="nome" class="control-label">Gestão e Compartilhamento</label><br />
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Membro(s)</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive text-center">
									<table class="table table-striped">
										<thead>
											<tr>
												<td>Gestor <input type="text" data-ng-model="nomeGestorFiltro" placeholder="filtrar" size="10"></td>
												<td>Proprietário</td>
												<td>Pode Modificar</td>
												<td>Acesso por tempo Indeterminado?</td>
												<td>Início Acesso</td>
												<td>Término Acesso</td>
												<td></td>
											</tr>
										</thead>
										<tbody>
											<tr data-ng-repeat="membro in registro.pessoaRelacionamentos | filter: nomeGestorFiltro | orderBy:'pessoa.nome'">
												<td>{{membro.pessoa.nome}}</td>
												<td><input type="radio" name="proprietario" data-ng-click="setMembroGrupoProprietario(registro.pessoaRelacionamentos, membro)" data-ng-model="membro.proprietario" value="S"></td>
												<td><input type="checkbox" class="form-control" data-ng-model="membro.podeModificar" data-ng-true-value="S" data-ng-false-value="N"></td>
												<td><input type="checkbox" class="form-control" data-ng-model="membro.relacionamento.indeterminado" data-ng-true-value="S" data-ng-false-value="N"></td>
												<td><input type="date" class="form-control" data-ng-model="membro.relacionamento.inicio" data-ng-show="membro.relacionamento.indeterminado === 'N'"></td>
												<td><input type="date" class="form-control" data-ng-model="membro.relacionamento.termino" data-ng-show="membro.relacionamento.indeterminado === 'N'"></td>
												<td>
													<button type="button" class="btn btn-danger btn-xs ttip" data-original-title="Remover" data-ng-click="removerMembroGrupo(membro)" >
														<span class="glyphicon glyphicon-trash"></span>
													</button>
												</td>
											</tr>
										</tbody>
										<tfoot></tfoot>
									</table>
								</div>
							</div>
							<div class="panel-footer text-right">
								<button class="btn btn-success novo" type="button" data-ng-click="novoMembroGrupo()" data-ng-disabled="registro.nivelGestao == null">Incluir novo Membro</button>
							</div>
						</div>
					</div>
				</div>	
				<div class="row">
					<div class="form-group col-md-9">
						<label for="nome" class="control-label">Observações</label><br />
						<textarea class="form-control" rows="5" data-ng-model="registro.observacoes"></textarea>
					</div>
					<div class="form-group col-md-3">
						<label for="situacao" class="control-label">Situação</label><br/>
						<div class="btn-group">
							<label class="btn">
								<input type="radio" data-ng-model="registro.situacao" value="A" name="situacao">
								Ativo
							</label>
							<label class="btn">
								<input type="radio" data-ng-model="registro.situacao" value="O" name="situacao">
								Inativo
							</label>
						</div>
					</div>
				</div>
			</form>

<div class="modal fade" id="modalMembroGrupoSocial">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close btn btn-danger" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title">Membro do Grupo Social [{{registro.nome}}]</h4>
			</div>
			<!--
			 Técnico: nome e cpf
			 Unidade organizacional: Instituição e nome
			 Instituição: nome e cnpj
			 -->
			<div class="modal-body" data-ng-show="registro.nivelGestao === 'T'">
				<div class="form-group">
					<div class="col-md-7">
						<label for="nomeMembro" class="control-label">Nome</label><br>
						<input class="form-control" title="Nome do Membro do Grupo" id="nomeMembro" data-ng-model="membroGrupo.filtro.nome" />
					</div>
					<div class="col-md-4">
						<label for="cpfMembro" class="control-label">CPF</label><br>
						<input class="form-control cpf" title="CPF do Membro do Grupo" id="cpfMembro" data-ng-model="membroGrupo.filtro.cpfCnpj" />
					</div>
	                <div class="com-md-1">
	                    <label class="control-label">&nbsp;</label><br>
	                    <button class="btn btn-default" type="button" id="buscarMembro" data-ng-click="buscarMembroGrupo()"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
	                </div>
				</div>
                <div class="clearfix "></div>
                
                <div class="form-group col-md-12">
                    <label for="pessoasEncontradas" class="control-label">Pessoas encontradas</label><br>
                    <select size="5" style="width:100%;" title="Pessoas" id="pessoasEncontradas" data-ng-model="membroGrupo.pessoaSelecionada">
                        <option data-ng-repeat="membro in membroGrupo.pessoasEncontradas" value="{{membro}}">{{membro.nome}}</option>
                    </select>
                </div>
                <div class="clearfix "></div>

				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-heading">
							<label class="panel-title">Privilégios</label>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<div class="col-md-4">
									<div class="checkbox">
										<label> <input type="checkbox" id="podeModificar" title="Pode Modificar" data-ng-disabled="membroGrupo.pessoaSelecionada == null" data-ng-model="membroGrupo.podeModificar" data-ng-true-value="S" data-ng-false-value="N"> Pode Modificar </label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label> <input type="checkbox" id="proprietario" title="Proprietario" data-ng-disabled="membroGrupo.pessoaSelecionada == null" data-ng-model="membroGrupo.proprietario" data-ng-true-value="S" data-ng-false-value="N"> Proprietário </label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix "></div>
				
				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-heading">
							<label> 
								<input type="checkbox" id="acessoGrupoIndeterminado" title="Acesso ao Grupo por Tempo Indeterminado" data-ng-model="membroGrupo.acessoGrupoTempoIndeterminado" data-ng-disabled="membroGrupo.pessoaSelecionada == null" data-ng-true-value="S" data-ng-false-value="N">
								Acesso ao Grupo por Tempo Indeterminado 
							</label>
						</div>
						<div class="panel-body" data-ng-show="membroGrupo.acessoGrupoTempoIndeterminado === 'N'">
							<div class="form-group">
								<div class="col-md-3">
									<label for="inicioAcessoGrupo" class="control-label">Início Acesso</label><br>
									<input type="date" id="inicioAcessoGrupo" class="form-control" data-ng-model="membroGrupo.filtro.inicio" title="Início de Acesso ao Grupo" >
								</div>
								<div class="col-md-3">
									<label for="nomeMembro" class="control-label">Término Acesso</label><br>
									<input type="date" id="terminoAcessoGrupo" class="form-control" data-ng-model="membroGrupo.filtro.termino" title="Término de Acesso ao Grupo" >
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix "></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
				<button type="button" class="btn btn-default" data-dismiss="modal" data-ng-click="salvarMembroGrupo()" data-ng-disabled="membroGrupo.pessoaSelecionada == null">
					Salvar
				</button>
			</div>
		</div>
	</div>
</div>
		</div>
	</div>
</div>

<script>
	$('.Tooltip').tooltip();
</script>