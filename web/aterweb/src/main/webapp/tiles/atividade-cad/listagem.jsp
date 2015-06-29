<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_listagem">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Listagem - Total com Repetição 0 | Total sem Repetição 0</h3>
        </div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th width="10">
                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
                            </th>
                            <th>Código/ <br> % conclusão/ <br> Situação</th>
                            <th>Início/<br>Previsão/<br>Conclusão</th>
                            <th>Demandante(s)</th>
                            <th>Executor(es)</th>
                            <th>Método</th>
                            <th>Assunto/Ação</th>
                            <th>Publ. Estimado</th>
                            <th>Publ. Real</th>
                        </tr>
                    </thead>
                    <tbody id="listagem">
                        <tr data-ng-repeat="linha in lista" data-ng-click="selecionado(linha, $event)">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
                            <td nowrap>
                            	<div class="codigoAtividade">{{linha.codigo}}</div>
								<div class="form-control" style="white-space: nowrap; width: 205px" id="percentualConclusao" name="percentualConclusao"  style="white-space: nowrap;"
									data-ng-class="{'texto-branco': linha.percentualConclusao > 0,'': linha.percentualConclusao<1, 'label-warning': linha.percentualConclusao>=1 && linha.percentualConclusao<3, 'label-info': linha.percentualConclusao>=3 && linha.percentualConclusao<7, 'label-success': linha.percentualConclusao>=7}"
									data-ng-show="['C','E','N'].indexOf(linha.situacao) >= 0">
									<rating ng-model="linha.percentualConclusao" min="0" max="10" readonly="true" style="white-space: nowrap;"></rating>
	   								<span class="label" style="white-space: nowrap;" data-ng-class="{'label-warning': percent<30, 'label-info': percent>=30 && percent<70, 'label-success': percent>=70}" data-ng-show="false">{{percent}}%</span>
	   								<span class="pull-right" style="white-space: nowrap;">{{linha.percentualConclusao*10}}%</span>
								</div>
								<div>
									<span>{{linha.registro | date:'dd/MM/yyyy'}}</span> <span>{{linha.situacaoDescricao}}</span>
								</div>
                            </td>
                            <td>
                            	<span style="white-space: nowrap">{{linha.inicio | date:'dd/MM/yyyy'}}</span>
                            	<span style="white-space: nowrap">{{linha.previsaoConclusao | date:'dd/MM/yyyy'}}</span>
                            	<span style="white-space: nowrap">{{linha.conclusao | date:'dd/MM/yyyy'}}</span>
                            </td>
                            <td><ul><li data-ng-repeat="demandante in linha.demandanteList">{{demandante.nome}}</li></ul></td>
                            <td><ul><li data-ng-repeat="executor in linha.executorList">{{executor.nome}}</li></ul></td>
                            <td>{{linha.metodo}}</td>
                            <td><ul><li data-ng-repeat="assuntoAcao in linha.assuntoAcaoList">{{assuntoAcao.assuntoNome}} / {{assuntoAcao.acaoNome}}</li></ul></td>
                            <td>{{linha.publicoEstimado}}</td>
                            <td>{{linha.publicoReal}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
			<button data-ng-click="irParaPagina(1)">|<</button> 
			<button data-ng-click="irParaPagina(filtro.numeroPagina - 1)"><</button> 
			Pagina: <input data-ng-model="filtro.numeroPagina" size="4">
			<button data-ng-click="executar()">Vai</button>
			<button data-ng-click="irParaPagina(filtro.numeroPagina -1 + 2)">></button> 
		</div>
	</div>
</div>