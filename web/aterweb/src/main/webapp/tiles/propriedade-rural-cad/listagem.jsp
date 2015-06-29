
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="div_listagem">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title" style="height: 20px;">
                <div class="col-md-9">Listagem</div> 
                <div class="col-md-3">Propriedades encontradas: {{lista.length}}</div> 
            </h3>
        </div>
        <div class="panel-body">
            <div class="col-md-9">
                <table class="table table-condensed table-hover" hover>
                    <thead>
                        <tr>    
                            <th>
                                <button type="button" name="selectLinha" data-ng-click="alterarSelecao()" class="btn btn-default btn-xs">&#9737;</button>
                            </th>
                            <th>#</th>
                            <th>Nome</th>
                            <th>Situação Fundiária</th>
                            <th>Comunidade</th>
                            <!--th>Nome dos Produtores</th-->
                            <th>Outorga</th>
                            <!--th>Sistema Predominante de Produção</th-->
                        </tr>
                    </thead>
                    <tbody data-ng-repeat="linha in lista"> 
                        <tr data-ng-click="selecionado(linha, $event);">
                            <td>
                                <input type="radio" name="linhaSelecionada" data-ng-click="selecionou(linha)" data-ng-checked="linha.id == linhasSelecionadas[0].id" value="{{linha}}" data-ng-show="tipoSelecao === 'radio'">
                                <input type="checkbox" data-ng-click="selecionou(linha)" data-ng-true-value="linha" data-ng-false-value="false" data-ng-show="tipoSelecao === 'checkbox'">
                            </td>
                            
                            <td>
                                {{$index + 1}}
                            </td>
                            
                            <td>{{linha.nomePropriedade}}</td>
                            <td>
                                <span data-ng-if="linha.situacaoFundiaria == 'E'">Escritura Definitiva</span>
                                <span data-ng-if="linha.situacaoFundiaria == 'C'">Concessão de uso</span>
                                <span data-ng-if="linha.situacaoFundiaria == 'P'">Posse</span>
                            </td>
                            <td> {{linha.comunidadeList[0].nome}} </td>
                            <!--td>
                                <span data-ng-if="linha.proprietarioList == ''">-</span>
                                <li data-ng-repeat="proprietario in linha.proprietarioList">{{proprietario.nome}}</li>
                            </td-->
                            <td> 
                                <span data-ng-if="linha.outorga == 'S'">Sim</span>
                                <span data-ng-if="linha.outorga == 'N'">Não</span>
                            </td>
                            <!--td>{{linha.sistemaProducao}}</td-->
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div class="col-md-3" data-ng-show="linhasSelecionadas.length === 1">
                <div id="pessoa_visualizar" style="background-color: #DDD; text-align: center; padding: 4px; margin-left: -30px; bottom: 0; min-height: 600px;">
                    <span data-ng-if='linhasSelecionadas[0].arquivoList'>
                        
                        <img data-ng-src="{{baseUrl}}/arquivo/descer?arq={{linhasSelecionadas[0].arquivoList.arquivo.md5}}" class="col-md-12"  style="margin-bottom: 5px;" />
                    </span>
                    <span data-ng-if="!linhasSelecionadas[0].arquivoList">
                        <img data-ng-src='{{baseUrl}}/resources/img/propriedade_padrao.png' class="col-md-12" />
                    </span>
                    <br />
                    
                    <div>
                        <div id="map-canvas" class="col-md-12"></div>
                        <input type="hidden" id="latitude" value="{{linhasSelecionadas[0].latitude}}" />
                        <input type="hidden" id="longitude" value="{{linhasSelecionadas[0].longitude}}" />
                    </div>
                    
                    <b> <span class="label label-danger"> {{linhasSelecionadas[0].municipio}} </span> </b> 
                    <br />
                    <br />
                    
                    <b>Proprietários</b>
                    <br />
                        <span data-ng-if="linhasSelecionadas[0].proprietarioList == ''"> <span class="label label-danger">-</span> </span>
                        <li data-ng-repeat="proprietario in linhasSelecionadas[0].proprietarioList"> <span class="label label-danger"> {{proprietario.nome}} </span> </li>
                    <br />
                    <br />
                    
                    <b>Sistema de Produção</b>
                    <br />
                    <span class="label label-danger"> {{linhasSelecionadas[0].sistemaProducao}} </span>
                    <br />
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var map;
    function initialize(lat, long) {
        var local = [ lat, long ];

        if (lat === '') {
            local = ['-15.732805',
                     '-47.903791'];
        }

        var posicao = new google.maps.LatLng(local[0], local[1]);
        var mapOptions = {
            zoom: 15,
            center: posicao,
            panControl: false,
            zoomControl: false,
            scaleControl: false,
            mapTypeId: google.maps.MapTypeId.SATELLITE
        };

        map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
        map.setCenter(posicao);

        var marker = new google.maps.Marker({
            position: posicao,
            title: "Propriedade!",
            draggable: false
        });

        marker.setMap(map);
    }

</script>