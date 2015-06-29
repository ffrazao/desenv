<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>

<div id="div_formulario">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Formulário</h3>
        </div>
        <div class="panel-body">
            <!--sec:authorize var="adm" access="hasRole('ROLE_ADMIN')"-->
            <form id="form_modulo" name="$parent.form_modulo" action="modulo-cad/salvar" method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="col-md-4"></div>
                <div class="col-md-4 text-center">
                    <h2 id="formulario_titulo"></h2>

                    <input id="formulario_id" type="hidden" class="form-control" />

                    <div class="input-group">
                        <span class="input-group-addon"> <!--i class="glyphicon glyphicon-italic"></i-->&nbsp;<b>Nome
                                do Módulo</b>
                        </span> <input id="formulario_nome" class="form-control"
                                       placeholder="Nome" data-ng-model="registro.nome" />
                    </div>
                    <br />
                </div>

                <br />
                <br />

                <div class="col-md-12"></div>
                <div class="col-md-3"></div>

                <div class="col-md-6">
                    <div class="text-center"><h3>Perfis</h3></div>
                    <!--div id="div_perfils" class="scroll"></div-->
                    <div ng-repeat="perfil in perfilsList">
                        <div class="col-md-2"></div>
                        <div class="col-md-8 tags">
                            <div class="col-md-8 text-left"><span class="glyphicon glyphicon-user"></span> <b> {{perfil.nome}} </b></div>
                            <div class="col-md-4 text-right"><input id="{{perfil.id}}" ng-true-value="perfil.id" type="checkbox" class="checkbox_perfils" /> </div>
                        </div>
                    </div>
                    
                </div>
            </form>
            <!--/sec:authorize-->
        </div>
    </div>
</div>
                
<script>
    var ids = idsPerfilList;
    setTimeout(function(){
        $.each(ids, function(i, data){
            $("#"+data).click();
            console.log(data);
        });
    }, 150);
</script>