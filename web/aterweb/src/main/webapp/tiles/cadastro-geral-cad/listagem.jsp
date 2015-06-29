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
                        <tr>
                            <th width="10">
                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
                            </th>
                            <th>#</th>
                            <th>Nome da Propriedade</th>
                            <th>Produtores Rurais / Moradores</th>
                        </tr>
                    </thead>
                    <tbody id="listagem" data-ng-repeat="linha in lista">
                        <tr data-ng-click="selecionado(linha, $event)" data-ng-class="{statusInvalido: linha.status == 'I'}">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
                            <td>{{(((paginaAtual-1)*50) + $index) + 1}}</td>
                            <td>{{linha.nome}}</td>
                            <td><ul><li data-ng-repeat="produtor in linha.produtores">{{produtor.nome}}</li></ul></td>
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