<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
    <div class="row">
        <div class="text-center">
            <a href="./"> 
                <img width="450" height="150" src='<spring:url value="resources/img/logo.gif"/>' /> 
            </a>
        </div>
    </div>
    <div class="col-2 col-offset-2">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h2 class="panel-title">
                    <div class="text-left"> <span class="glyphicon glyphicon-leaf"></span> ${erro} <div>
                    <div class="text-right" style="margin-top: -20px;">${horario}</div>
                </h2>
            </div>
            <div class="panel-body">
                
                <div class="col-md-10">
                    <h2>ERRO ${codigo}</h2>
                    <div>${pagina}</div>
                </div>
                
                <div class="col-md-2">
                    <img id="img_erro" src="" width="150" height="150"></div>
                </div>
                
                <br />
                <br />
                
                <div class='row'>
                <div class='col-md-12' style='text-align: center; font-weight: bold;'> <h3>${mensagem}</h3> </div>
                </div>
                
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    <div>Detalhes <span style="font-size: 11px;">(StackTrace)</span></div>
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse">
                            <div class="panel-body">
                                <pre style="background-color: #ebe99d;">${stackTraceList}</pre>
                            </div>
                        </div>
                    </div>
                </div>				

            </div>
            <div class="panel-footer">
                <div class="text-center">
                    <button type="button" class="btn btn-primary" onclick="javascript: history.go(-2);"> <span class="glyphicon glyphicon-chevron-left"></span> Voltar</button>
                    <a type="button" class="btn btn-danger" href='mailto:teste@teste.com?Subject=${codigo} - ${erro}&body=stackTraceList: "${stackTraceList}"'> <span class="glyphicon glyphicon-info-sign"></span> Reportar Erro</a>
                </div>
            </div>
        </div>
    </div>
</div>
                            
<script>
    $("#img_erro").attr("src","resources/img/erro_" + ${codigo} + ".png");
</script>