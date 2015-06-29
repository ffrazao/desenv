<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--<script src="<spring:url value="resources/js/agenda-cad.js"/>" charset="UTF-8"></script>-->

<div class="modal fade bs-example-modal-lg" id="modalNovoEvento" tabindex="-1" role="dialog" aria-labelledby="Evento" aria-hidden="true" style="overflow-y: auto;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Novo Registro</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<label for="tituloEvento" class="control-label">Beneficiario</label><br>
							<input class="form-control" id="tituloEvento" data-ng-model="evento.novo.titulo"  />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<br>
							<button class="btn btn-primary salvar" data-ng-click="apoio.pesquisar = !apoio.pesquisar">Pesquisar</button>
						</div>
					</div>
					<div class="col-md-7">
						<div class="form-group" data-ng-show="apoio.pesquisar">
							<table class="table">
								<tr><td>#</td><td>Nome</td><td>Documento</td></tr>
								<tr><td><input type="radio" name="beneficiario"/></td><td>Fulano de Tal</td><td>cpf: 001.002.003-45</td></tr>
							</table>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="row">
						<div class="col-md-6">
							<label for="horaEventoInicio" class="control-label">Data</label><br>
							<input type="text" class="form-control" readonly="readonly" 
								datepicker-popup="dd/MM/yyyy" data-ng-model="apoio.hoje" 
								ng-click="inicio.isOpen=true" is-open="inicio.isOpen">											
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="horaEventoInicio" class="control-label">Técnico</label><br>
								<table class="table">
									<tr><td>#</td><td>Nome</td><td>Matrícula</td></tr>
									<tr><td><input type="radio" name="beneficiario"/></td><td>Fulano de Tal</td><td>123-4</td></tr>
									<tr><td><input type="radio" name="beneficiario"/></td><td>Ciclano de Tal</td><td>567-8</td></tr>
								</table>
							</div>
						</div>
					</div>
				</div>
					
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label" for="metodo">Método</label>
							<select id="metodo" name="metodo" class="form-control" data-placeholder="Selecione o método" data-ng-model="registro.metodo" data-ng-options="item as item.nome for item in apoio.metodoList | filter : apoio.filtraMetodo" required="required"></select>
						</div>
					</div>
					<div class="col-md-6" data-ng-show="registro.metodo.id">
						<div class="form-group">
							<label for="horaEventoInicio" class="control-label">Ações/Diretrizes</label><br>
							<table class="table">
								<tr><td>#</td><td>Diretriz</td><td>Acao</td><td>Observacoes</td><td></td></tr>
								<tbody data-ng-show="apoio.exibirAcao">
									<tr>
										<td>1</td>
										<td>Renda</td>
										<td>Boas Práticas Agrícolas</td>
										<td><textarea /></td>
										<td><button type="button" class="btn btn-danger btn-xs"
												data-ng-click="apoio.removerAcao(item)">
												<span class="glyphicon glyphicon-trash"></span>
											</button></td>
									</tr>
									<tr>
										<td>2</td>
										<td>Renda</td>
										<td>XPTO</td>
										<td><textarea /></td>
										<td><button type="button" class="btn btn-danger btn-xs">
												<span class="glyphicon glyphicon-trash"></span>
											</button></td>
									</tr>
								</tbody>
							</table>
							<button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalAcoes" data-ng-click="apoio.incluirAcao()"> Incluir </button>
						</div>
					</div>
				</div>

				
				
				
				
				
				
				<div class="clearfix "></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger remover" data-dismiss="modal" data-ng-click="remove()" data-ng-show="evento.novo.id!=''">Remover</button>
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-dismiss="modal" data-ng-click="addEvent()" >Salvar</button>
			</div>
		</div>
	</div>
</div>
<div class="clearfix "></div>
<div ui-calendar="uiConfig.calendar" data-ng-model="agenda" calendar="myCalendar"></div>
<div class="clearfix "></div>



<div class="modal fade formulario" id="modalAcoes" tabindex="-1" data-role="dialog" data-aria-labelledby="Ações" data-aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" data-aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Registro de Ações da Atividade</h4>
			</div>
			<div class="modal-body">
				<form name="frmAcoes" novalidate>
					<input type="hidden" data-ng-model="apoio.acao.id">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="control-label" for="classificacao">Classificação [{{apoio.assunto.id}}]</label>
							<div class="form-group collapsed"
								data-angular-treeview="true"
								data-tree-id="classificacaoTree"
								data-tree-model="apoio.atividadeAssuntoList"
								data-node-id="id"
								data-node-label="nome"
								data-node-children="filhos" 
								style="height:120px;overflow:scroll;">
							</div>
						</div>
					</div>
					<div class="row" data-ng-hide="!apoio.assunto.id">
						<div class="form-group col-md-12">
							<label class="control-label">Ação [{{apoio.acao.assuntoAcao.id}}]</label>
							<input class="form-control pull-right" type="text" data-ng-model="apoio.filtraAcao" placeholder="filtro">
						</div>
						<div class="form-group col-md-12">
							<select class="form-control" data-placeholder="Selecione a ação" data-ng-model="apoio.acao.assuntoAcao" size="5" data-ng-options="item as item.acao.nome for item in apoio.atividadeAssuntoAcaoList | filter : apoio.filtraAcao"></select>
						</div>
					</div>
					<div class="row" data-ng-hide="!apoio.acao.assuntoAcao.id">
						<div class="form-group col-md-12">
							<label class="control-label">Descrição</label>
							<textarea class="form-control" rows="5" data-ng-model="apoio.acao.descricao"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default cancelar" data-dismiss="modal" data-ng-click="apoio.pessoaLimpar()">Cancelar</button>
				<button type="button" class="btn btn-primary salvar" data-ng-click="apoio.acaoSalvar(frmAcoes.$valid)" data-ng-hide="!apoio.acao.assuntoAcao.id">Salvar</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	$('.time').mask('00:00');
});
</script>
<!-- 
				<div class="form-group">
					<div class="col-md-12">
						<label for="horaEvento" class="control-label">Dia todo</label> -
						<input type="radio" data-ng-model="evento.novo.diaTodo" value="S" /> Sim |
						<input type="radio" data-ng-model="evento.novo.diaTodo" value="N" /> Não
					</div>
				</div>
				<div class="form-group" data-ng-show="evento.novo.diaTodo != 'S'">
					<div class="col-md-6">
						<label for="horaEventoInicio" class="control-label">Início</label><br>
						<input class="form-control time" id="horaEventoInicio" data-ng-model="evento.novo.inicio.horas"  />
					</div>
					<div class="col-md-6">
						<label for="horaEventoFim" class="control-label">Término</label><br>
						<input class="form-control time" id="horaEventoFim" data-ng-model="evento.novo.fim.horas"  />
					</div>
				</div>
				
				<div class="clearfix "></div>
				<br>
				<div class="form-group">
					<div class="progress">
					  <div class="progress-bar" role="progressbar" aria-valuenow="{{evento.novo.percentualConclusao}}" aria-valuemin="0" aria-valuemax="100" data-ng-style="{'width': evento.novo.percentualConclusao + '%' }">
						{{evento.novo.percentualConclusao}}%
					  </div>
					</div>
				</div>
				
				<div class="clearfix "></div>
				
				<div class="form-group">
					<p>Pessoas relacionadas ao evento</p>
					<ul data-ng-repeat="registro in evento.novo.pessoaAcaoList">
						<li>{{registro.pessoa.nome}}</li>
					</ul>
				</div>
-->