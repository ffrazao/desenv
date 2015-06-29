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
<form id="form_usuario" action="usuario-cad/salvar" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<div class="col-md-4"></div>
	<div class="col-md-4">
		<div class="text-center">
			<h2 id="formulario_titulo"></h2>

			<input id="formulario_id" type="hidden" class="form-control" />

			<div class="input-group">
				<span class="input-group-addon"> <!--i class="glyphicon glyphicon-italic"></i-->&nbsp;<b>Nome</b>
				</span> <input id="formulario_nome" class="form-control" placeholder="Nome " data-ng-model="registro.pessoa.nome" />
			</div>
			<br />
			<div class="input-group">
				<span class="input-group-addon"> <!--i class="glyphicon glyphicon-user"></i-->&nbsp;<b>Nome
						de Usuário</b>
				</span> <input id="formulario_usuario" class="form-control"
					placeholder="Nome de Usuário" data-ng-model="registro.nomeUsuario" />
			</div>
			<br />
			<div class="input-group">
				<span class="input-group-addon"> <!--i class="glyphicon glyphicon-asterisk"></i-->
					<b>Situação</b>
				</span> <select id="formulario_situacao" data-ng-model="registro.usuarioStatusConta" class="form-control">
					<option value="A">Ativo</option>
					<option value="I">Inativo</option>
					<option value="B">Bloqueado</option>
				</select>
			</div>

			<br /> <br /> <br />

			<!--input type="submit" name="action" value="restore" />
                    <input type="submit" name="action" value="update" />
                    <input type="submit" name="action" value="delete" /-->
		</div>
	</div>

	<div class="col-md-6">
                    <div class="text-center"><h3>Módulos</h3></div>
                    <!--div id="div_perfils" class="scroll"></div-->
                    <div ng-repeat="modulo in moduloList">
                        <div class="col-md-2"></div>
                        <div class="col-md-8 tags">
                            <div class="col-md-8 text-left"><span class="glyphicon glyphicon-user"></span> <b> {{modulo.nome}} </b></div>
                            <div class="col-md-4 text-right"><input id="{{modulo.id}}" ng-true-value="modulo.id" type="checkbox" class="checkbox_modulos" /> </div>
                        </div>
                    </div>
                    
                </div>
                
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

	<br /> <br />

	<!--div class="col-md-12 text-center" style="margin-top: 20px;">
        <button id="btn_formulario_inserir" type="button" class="btn btn-primary" name="action">Inserir</button>
    </div-->
</form>
<!--/sec:authorize-->

<script>
    var idsM = idsModuloList;
    setTimeout(function(){
        $.each(idsM, function(i, data){
            $('.checkbox_modulos[id='+data+']').click();
            //console.log(data);
        });
    }, 150);
</script>
<script>
    var idsP = idsPerfilList;
    setTimeout(function(){
        $.each(idsP, function(i, data){
            $('.checkbox_perfils[id='+data+']').click();
            //console.log(data);
        });
    }, 150);
</script>
		</div>
	</div>
</div>