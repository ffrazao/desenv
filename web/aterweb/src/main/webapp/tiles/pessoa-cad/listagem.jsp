<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="div_listagem" ng-hide="exibirModal">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Listagem</h3>
        </div>
        <div class="panel-body">
            
            <div class="col-md-9 table-responsive">
                <table class="table table-hover table-condensed"> 
                    <thead>
                        <tr>
                            <th width="10">
                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs btnTrocar" data-toggle="tooltip" data-placement="top" title="Selecionar Multiplos">&#9737;</button>
                            </th>
                            <th>Nome</th>
                            <th>CPF / CNPJ</th>
                        </tr>
                    </thead>

                    <tbody id="listagem">
                        <tr data-ng-click="selecionado(linha, $event)" data-ng-repeat="linha in lista">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
                            <td>{{linha.nome}}</td>
                            <td>{{linha.cpf}}{{linha.cnpj}}</td>
                        </tr>
                    </tbody>
                </table>

            </div>

            <div class="col-md-3" data-ng-show="linhasSelecionadas.length === 1">
                <div id="pessoa_visualizar" style="background-color:#DDD; text-align: center; padding: 4px; margin-left: -30px; bottom: 0; min-height: 500px;">
                    <span>
                        <img data-ng-src='{{linhasSelecionadas[0].fotoPerfil}}' class="col-md-12" />
                        <br />
                    </span>
                    
                    <b> 
                        <span class="label label-danger"> 
                            <span data-ng-if="linhasSelecionadas[0].pessoaTipo == 'PF'">Pessoa Física</span>
                            <span data-ng-if="linhasSelecionadas[0].pessoaTipo == 'PJ'">Pessoa Juridica</span>
                        </span> 
                    </b>
                    <br /><br />
                    
                    <span class="label label-danger"> 
                        <span data-ng-if="linhasSelecionadas[0].cpf == null">CNPJ: <b>{{linhasSelecionadas[0].cnpj}}</b></span>
                        <span data-ng-if="linhasSelecionadas[0].cnpj == null">CPF: <b>{{linhasSelecionadas[0].cpf}}</b></span>
                    </span>
                    <br /><br />
                </div>
            </div>
        </div>
    </div>
</div>


<div id="div_listagem" ng-show="exibirModal">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Listagem</h3>
        </div>
        <div class="panel-body">
            <div class="col-md-9 table-responsive">
                <table class="table table-hover table-condensed"> 
                    <thead>
                        <tr>
                            <th width="10">
                                <frz-seletor ng-model="relacionadoNvg" dados="lista"></frz-seletor>
                            </th>
                            <th>#</th>
                            <th>Nome </th>
                            <th>CPF / CNPJ </th>
                        </tr>
                    </thead>

                    <tbody id="listagem">
                        <tr data-ng-repeat="linha in lista | orderBy: 'nome' | pagina: relacionadoNvg.paginaAtual : relacionadoNvg.tamanhoPagina | limitTo: relacionadoNvg.tamanhoPagina">
                            <td>
								<input type="radio" name="linhaSelecionada" ng-show="relacionadoNvg.selecao.tipo === 'U'" ng-model="relacionadoNvg.selecao.item" ng-value="linha"/>
								<input type="checkbox" ng-show="relacionadoNvg.selecao.tipo === 'M'" checklist-model="relacionadoNvg.selecao.items" checklist-value="linha"/>
                            </td>
                            <td>{{$index + 1 + ((relacionadoNvg.paginaAtual-1) * relacionadoNvg.tamanhoPagina)}}</td>
                            <td>{{linha.nome}}</td>
                            <td>{{linha.cpf}}{{linha.cnpj}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-md-3" data-ng-show="relacionadoNvg.selecao.item">
                <div id="pessoa_visualizar" style="background-color:#DDD; text-align: center; padding: 4px; margin-left: -30px; bottom: 0; min-height: 500px;">
                    <span>
                        <img data-ng-src='{{relacionadoNvg.selecao.item.fotoPerfil}}' class="col-md-12" />
                        <br />
                    </span>
                    <b> 
                        <span class="label label-danger"> 
                            <span data-ng-if="relacionadoNvg.selecao.item.pessoaTipo == 'PF'">Pessoa Física</span>
                            <span data-ng-if="relacionadoNvg.selecao.item.pessoaTipo == 'PJ'">Pessoa Juridica</span>
                        </span> 
                    </b>
                    <br /><br />
                    <span class="label label-danger"> 
                        <span data-ng-if="relacionadoNvg.selecao.item.cpf == null">CNPJ: <b>{{relacionadoNvg.selecao.item.cnpj}}</b></span>
                        <span data-ng-if="relacionadoNvg.selecao.item.cnpj == null">CPF: <b>{{relacionadoNvg.selecao.item.cpf}}</b></span>
                    </span>
                    <br /><br />
                </div>
            </div>
        </div>
    </div>
</div>
