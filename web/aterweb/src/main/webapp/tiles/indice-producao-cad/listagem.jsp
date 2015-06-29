<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_listagem">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Listagem</h3>
        </div>
        <div class="panel-body">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                        <tr >
                            <th width="10">
                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
                            </th>
                            <th>#</th>
                            <th>Previsão de Produção</th>
                        </tr>
                    </thead>
                    <tbody id="listagem" data-ng-repeat="linha in lista">
                        <tr data-ng-click="selecionado(linha, $event)" data-ng-class="{statusInvalido: linha.status == 'I'}">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
                            <td>{{(((paginaAtual-1)*50) + $index) + 1}}</td>
                            <td>
                            	<div>Perspectiva: {{linha.perspectiva}}</div>
                            	<div>Local: {{linha.pessoaGrupoNome}}</div>
                            	<div>Previsao: De {{linha.inicio}} a {{linha.termino}} total de {{linha.volume | number: 3}} {{linha.unidadeMedida}} em {{linha.totalPropriedades | number: 0}} propriedades rurais</div>
                            	<div>Produto/Servico: {{linha.produtoServico}}</div>
								<div
									data-ng-show="linha.perspectiva=='AGRICOLA' || linha.perspectiva=='FLORES'">
									<h6>Proteção/Época/Forma: {{linha.protecaoEpocaForma}} Sistema: {{linha.sistemaAgricola}} Tipo: {{linha.tipo}} Uso D'água: {{linha.usoDagua}}</h6>
								</div>
								<div
									data-ng-show="linha.perspectiva=='ANIMAL' || linha.perspectiva=='LEITE' || linha.perspectiva=='CORTE' || linha.perspectiva=='POSTURA'">
									<h6>Exploração: {{linha.exploracao}} Sistema: {{linha.sistemaAnimal}}</h6>
								</div>
								<div
									data-ng-show="linha.perspectiva=='SERVICO' || linha.perspectiva=='AGROINDUSTRIA' || linha.perspectiva=='TURISMO'">
									<h6>Condição: {{linha.condicao}} Produto: {{linha.produto}} Projeto: {{linha.projeto}}</h6>
								</div>
								<table class="table table-bordered">
	                            	<thead>
	                            		<tr>
	                            			<th>#</th>
	                            			<th>Propriedade Rural</th>
	                            			<th>Volume</th>
	                            			<th>Percentual</th>
	                            			<th>Responsável pela Produção</th>
	                            		</tr>
	                            	</thead>
	                            	<tbody>
	                            		<tr data-ng-repeat="producao in linha.producaoList">
	                            			<td>{{$index + 1}}</td>
	                            			<td>{{producao.propriedadeRuralNome}}</td>
	                            			<td>{{producao.volume}}</td>
	                            			<td>{{producao.volume / linha.volume * 100 | number: 2}} %</td>
	                            			<td>
				                            	<table class="table table-bordered">
					                            	<thead>
					                            		<tr>
					                            			<th>#</th>
					                            			<th>Produtor</th>
					                            			<th>Volume</th>
					                            			<th>Percentual</th>
					                            		</tr>
					                            	</thead>
					                            	<tbody>
					                            		<tr data-ng-repeat="responsavel in producao.responsavelList">
	                            							<td>{{$index + 1}}</td>
					                            			<td>{{responsavel.pessoaNome}}</td>
					                            			<td>{{responsavel.volume}}</td>
					                            			<td>{{responsavel.volume / producao.volume * 100 | number: 2}} %</td>
					                            		</tr>
					                            	</tbody>
				                            	</table>
	                            			</td>
	                            		</tr>
	                            	</tbody>
                            	</table>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <button data-ng-click="irParaPagina(1)">|<</button> 
                <button data-ng-click="irParaPagina(filtro.numeroPagina - 1)"><</button> 
                Pagina: <input data-ng-model="filtro.numeroPagina" size="4">
                <button data-ng-click="executar()">Vai</button>
                <button data-ng-click="irParaPagina(filtro.numeroPagina -1 + 2)">></button> 
            </div>
        </div>
    </div>
</div>