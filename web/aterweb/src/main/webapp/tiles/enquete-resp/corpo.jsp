<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
@media print {
	.fimPagina{
		page-break-before: always;
		page-break-before: always;
		-webkit-page-break-before: always;
		-moz-page-break-before: always;
		-ie-page-break-before: always;
		padding: 1px;
	}
	.campos-menores {
		margin-bottom: 5px !important;
		padding: -100px !important;
		heigth: 100px !important;
	}
	.panel-body {
		padding: 1px !imoprtant;
		display: none;
	}
	textarea.form-control {
		height: '';
	}
	.panel-default {
		border-color: #FFF;
	}
}
</style>

<div class="container-fluid" style="padding-top: 2cm;" data-ng-controller="cadastroCtrl">
    <div class="row">
        <div class="panel form-group col-md-offset-2 col-md-8">
            <div class="panel-heading">
                <a href="{{baseUrl}}">
                    <img src="<spring:url value='resources/img/logo.gif'/>" width="85px" />
                </a>
            </div>
            <div class="panel-body">
                <h2 data-ng-show="lista.length > 0">Formularios Abertos</h2><br/>
                
                    <div class="panel panel-default col-md-6" data-ng-repeat="(key, item) in lista" style="min-height: 280px; padding:0;">
                        <div class="panel-heading">
                            <h1 class="panel-title" style="font-size: 13px;">   
                                <table>
                                    <!--tr>
                                        <td>
                                            <a data-ng-href="{{tipoEnquete}}?formularioId={{item.id}}" class="btn btn-success btn-xs ttip" data-original-title="Responder Enquete"><span class="glyphicon glyphicon-plus"></span></a>
                                        </td>
                                    <tr-->
                                        <td style='padding: 5px; margin-top: 5px;' valign="center">
                                            <a class="enquete">
                                                <p style="font-size: 14px; margin-top: 10px;" class="" data-original-title="Visualizar Respostas"><div data-ng-bind-html="item.formulario.nome | sanitize"></div><br /> <h5><b>{{item.complemento}}</b></h5></p>
                                            </a>
                                            <p><center> <span style="font-size:10px; color: red; font-weight: bold;">Este formulário estará aberto entre {{item.formulario.inicio}} e {{item.formulario.termino}}</span> </center></p>
                                        </td>
                                    </tr>
                                </table>
                            </h1>
                        </div>
                        <div class="panel-collapse collapse in">
                            <div class="panel-body">
                                <center>
                                    <a data-ng-href="enquete-identificada?formularioId={{item.id}}" class="btn btn-success btn-sm ttip" data-original-title="Responder Enquete" data-ng-show="(item.respostaVersaoList.length == 0)">Responder </a>
                                
                                    <!--a data-ng-href="enquete-identificada?formularioId={{item.id}}" class="btn btn-primary btn-sm ttip" data-original-title="Responder Enquete" >Visualizar Respostas </a-->
                                </center>
                                
                                <p data-ng-show="(item.respostaVersaoList.length > 0)">Respostas ({{item.respostaVersaoList.length}})
                                <ol>
                                    <li data-ng-repeat="respostaVersao in item.respostaVersaoList">{{respostaVersao.termino}} <a class="btn btn-primary btn-xs ttip" data-ng-href="{{tipoEnquete}}?respostaVersaoId={{respostaVersao.id}}" data-original-title="Visualizar"><span class="glyphicon glyphicon-search"></span></a> 
                                    <button class="btn btn-danger btn-xs ttip" ng-show="item.formulario.permitirExcluirResposta == 'S'" data-ng-click="refExcluir(respostaVersao.id)" data-original-title="Remover" data-toggle="modal" data-target="#ModalExcluir" ><span class="glyphicon glyphicon-minus"></span></button></li>
                                </ol>
                                </p>
                            </div>
                        </div>
                    </div>
            </div>
            <div data-ng-show="exibeFormulario()">
            	<h3 class="text-center"><span data-ng-bind-html="registro.formulario.nome | sanitize"></span></h3><br />
                <form name="frm" novalidate >
                    <h4><b>Responsável:</b> {{registro.usuario.pessoa.nome | uppercase}}<br/> <b>Complemento:</b> {{registro.complemento}}</h4>
                    <h6>
                    	<div class="hidden-print">
                    	<b>Este formulário estará aberto entre os dias</b> <span style="color: red;">{{registro.formulario.inicio}}</span> e <span style="color: red;">{{registro.formulario.termino}}</span>.</br>
                    	</div>
                    	<b>Identificação:</b> {{registro.formulario.perguntaList[0].resposta.respostaVersao.uuid}}
                    </h6>
                    <br class="hidden-print" />
                    <div class="panel panel-default form-group"
                         data-ng-repeat="pergunta in registro.formulario.perguntaList | orderBy: pergunta.ordem"  ng-class="modificarTamanho(pergunta.opcaoResposta.opcaoRespostaTipo, pergunta.opcaoResposta.ordem)">
                        <div class="panel-heading" style="border-color: #FFF !important;">
                            <div style="float: left;" data-ng-bind-html="pergunta.pergunta | sanitize">-</div>
                                
                            <label style="float: left;" class="ttip visible-print-block" data-original-title="{{opcao.descricao}}" data-ng-repeat="opcao in pergunta.opcaoResposta.opcaoValorList"
                                data-ng-show="pergunta.opcaoResposta.opcaoRespostaTipo == 'U'">
                                
                                <div ng-class="esconderResposta(pergunta.resposta.valor, opcao.id)" >
                                	&nbsp;-&nbsp;{{opcao.codigo}} -<div style="float: right;"> {{opcao.descricao}} </div>
                               	</div>

                            </label>
                           <div data-ng-show="pergunta.opcaoResposta.opcaoRespostaTipo != 'U'"> <div class="visible-print-block">&nbsp;{{pergunta.resposta.valor}}</div></div>
                        </div>
                        <div class="panel-body">
                            <h6  class=" hidden-print" >{{pergunta.explicacao}}</h6>
                            
                            	<textarea class="form-control hidden-print" rows="5" name="opcao_{{pergunta.id}}" data-ng-model="pergunta.resposta.valor" data-ng-show="pergunta.opcaoResposta.opcaoRespostaTipo == 'S'"></textarea>
                                
                                 <div class="visible-print-block" data-ng-show="pergunta.opcaoResposta.nome == 'Texto'"> {{pergunta.resposta.valor}}</div>
                            
                            <label class="ttip" data-original-title="{{opcao.descricao}}" data-ng-repeat="opcao in pergunta.opcaoResposta.opcaoValorList"
                                data-ng-show="pergunta.opcaoResposta.opcaoRespostaTipo == 'U'">
                                
                                <div class="hidden-print" ng-class="esconderResposta(pergunta.resposta.valor, opcao.id)" >
                                	<input type="radio" name="opcao_{{pergunta.id}}" value="{{opcao.id}}" data-ng-model="pergunta.resposta.valor" required /> 
                                	{{opcao.codigo}} &nbsp;&nbsp;&nbsp;
                               	</div>

                            </label>
                            <div data-ng-show="form.opcao_{{pergunta.id}}.$dirty && form.opcao_{{pergunta.id}}.$dirty">
                                Informacao obrigatoria!
                            </div>
                        </div>
                    </div>
                    
                    <div class="hidden-print">
                    <button type="button" class="btn btn-primary" data-ng-click="retornar()"><span class="glyphicon glyphicon-chevron-left"></span> Retornar</button>
                    <input type="submit" class="btn btn-success btn-salvar" value="Salvar" data-ng-click="salvar()" data-ng-show="querystring['formularioId'] != null"/>
                    <button type="reset" class="btn" data-ng-click="limpar()" data-ng-show="querystring['formularioId'] != null">Limpar</button>
                    <button type="button" class="btn" data-ng-click="imprimir()" data-ng-show="querystring['formularioId'] == null"><span class="glyphicon glyphicon-print"></span> Imprimir</button>
                    </div>
                    <br/>
                    <br/>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="ModalExcluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">Resposta de Enquete</h4>
                </div>
                <div class="modal-body">
                    <p>Deseja mesmo excluir a resposta? &hellip;</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" data-ng-click="remover()">Excluir</button>

                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--Fim da Modal -->


</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/tiles/enquete-resp/style.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/tiles/enquete-resp/script.js" charset="UTF-8"></script>

<script>
    //$('.collapse').collapse();
	
    setTimeout(function() {
        $(".ttip").tooltip();
    }, 800);

    $('textarea').autoResize();
</script>