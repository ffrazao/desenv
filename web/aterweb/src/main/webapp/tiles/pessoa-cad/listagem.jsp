<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="div_listagem">
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