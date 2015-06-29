/**
 * Script do M�dulo 
 */

var PAGINA = 'modulo-cad';

function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService) {
    $scope.emProcessamento(false);
    idsPerfilList = [];
    requisicaoService.get("dominio?ent=Perfil").success(function(data){
        if(data.executou) {
            $scope.perfilsList = data.resultado;
        }
    });
    
    
    $scope.salvar = function() {
        var perfilsSalvar = [];
        var perfilsChecados = $('.checkbox_perfils:checked');
        $.each(perfilsChecados, function(i, data){
            perfilsSalvar.push( { perfil: { id: parseInt(data.id) } } );
            //console.log(perfilsSalvar);
            $scope.registro.moduloPerfilList = perfilsSalvar;
        });
        
        $scope.emProcessamento(true);
        if (!$scope.form_modulo.$valid) {
            $scope.emProcessamento(false);
            $rootScope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
              
        console.log($rootScope.registro);
        removerReferenciasJsonId($rootScope.registro);
        requisicaoService.salvar($rootScope.registro);
        
    };
	
    $scope.incluir = function() {
    	$scope.emProcessamento(true);
    	$rootScope.botoesAcao('incluir');
        $location.path("/formulario");
    };
    
    $scope.executar = function() {
       	$scope.emProcessamento(true);
    	$rootScope.botoesAcao('executar');
        requisicaoService.filtrar({params: $scope.filtro});
       
    };
    
    $scope.editar = function(){
        if($rootScope.linhasSelecionadas.length === 0) {
            toaster.pop('info', null, "Nenhum registro selecionado!");
        } else {
            id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
            $scope.emProcessamento(true);
            $http.get(baseUrl + "modulo-cad/restaurar",{params: {id: id}})
            .success(function(data){
                if(!data.executou){
                    toaster.pop('error', "ERRO", data.resultado.message);
                    $scope.emProcessamento(false);
                    return;
                }                
                                
                $rootScope.botoesAcao('editar');
                $location.path('/formulario');
                $rootScope.registro = data.resultado;
                
                
                $.each($rootScope.registro.moduloPerfilList, function(i, data){
                    idsPerfilList.push(data.perfil.id);
                });
                
                $scope.emProcessamento(false);
            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });
        }
    };


    // codigo comum para verificar se algum id foi informado
    // caso positivo abre a tela em modo edicao de formulario
	if (cadId !== null) {
		$rootScope.linhasSelecionadas = [];
		angular.forEach(cadId,function(value, key) {
			$rootScope.linhasSelecionadas.push({id: value});	
		})
		cadId = null;
		$scope.editar();
	}
	
}


/*
$("#btn_ferramenta_deletar").hide();
//$("#formulario_nome").attr('disabled', 'disabled');

/* Funções *//*
function btnFiltrar() {
    /*$("#div_filtro").show().delay(1000);
    $("#div_filtro").toggle();*/
    
    //$("#btn_formulario_inserir").remove();
//}
/*
function incluir() {
    $("#formulario_titulo").html("Novo Módulo");
    $("#btn_formulario_inserir").text("Inserir");
    
    $("#formulario_id").val('');
    $("#formulario_nome").val('');
    
    $(".checkbox_perfils").removeAttr("checked");
    
    $("#formulario_nome").focus();
    
    //$("#div_formulario").visible();
    //$("#barra-ferramenta").children().eq(1).append('<button id="btn_formulario_inserir" type="button" class="btn btn-success" name="action">Salvar</button>')
}
/*
function editar() {
    check = $(".usuario_selecionado:checked");    
    id = $(".usuario_selecionado:checked").val();
    $("#btn_ferramenta_editar").hide();
    
    $(".checkbox_modulos").removeAttr("checked");
    $(".checkbox_perfils").removeAttr("checked");
    
    $.get("modulo-cad/restaurar", { id: id }, function(data) {
        if(data.executou) {
    
            $("#formulario_id").val(data.resultado.id);
            $("#formulario_nome").val(data.resultado.nome);
            
            $.each(data.resultado.moduloPerfils, function(l, perfils) {
                id = campoRecursivo(perfils.perfil, 'id');
                $(".checkbox_perfils[id="+id+"]").click();
                $(".checkbox_perfils[id="+id+"]").append('<input type="hidden" name="moduloPerfils['+l+'].id" value="'+perfils.id+'" />');
            });

            //$("#btn_formulario_inserir").text("Atualizar");
            
            $("#div_formulario").show();
        }
    })
    
    $("#barra-ferramenta").children().eq(1).append('<button id="btn_formulario_inserir" type="button" class="btn btn-success" name="action">Salvar</button>')
}

function excluir() {
    check = $(".usuario_selecionado:checked");
    id = $(".usuario_selecionado:checked").val();
    $.get("modulo-cad/excluir", { id: id }, function(data) {
        console.log(data);
        if(data.executou) {
            $(check).parent().parent().remove();
            alert(data.result);
        } else {
            alert("Falha " + data.resultado.message)
        }
    });
}

//Clicar no botão de executar filtro
function filtrar() {
    var nome = $("#filtro_nome").val();
    
    //var modulosArray = new Array();
    var perfilsArray = new Array();
    
    $.get("modulo-cad/filtrar", { nome: nome }, function(modulos) {  
        //var modulosFinal = '';
        var perfilsFinal = '';
        
        $("#listagem").html('');
        
        $.each(modulos.resultado, function(i, modulo){ 
            $.each(modulo.moduloPerfils, function(j, perfils) {
                perfilsFinal += ' <span class="label label-primary">' + campoRecursivo(perfils.perfil, 'nome') + '</span> ';
            })
            $("#listagem").append("<tr> <td> <input id='"+modulo.id+"' value='"+modulo.id+"' name='usuario' type='radio' class='usuario_selecionado' /> </td> <td><b>"+modulo.nome+"</b></td> <td>"+perfilsFinal+"</td> </tr>");
          
            perfilsFinal = '';
        })
    });
}

/* ESPECIFICO *//*
$(document).on("click", "#btn_formulario_inserir", function() {
    $.post("modulo-cad/salvar", $('#form_usuario').serialize() )
    //id: int_id, nome: txt_nome, login: txt_login, senha: txt_senha, loginModulos: [1]
    .success(function (pessoa){ 
        console.log(pessoa);
        if(pessoa.executou) {
            //Adiciona pessoa e atualiza a lista;
            int_id = $("#formulario_id").val();
            if(int_id) {
                if(pessoa.resultado.id) {
                    txt_nome = pessoa.resultado.nome;

                    txt_modulos = '';
                    txt_perfils = '';

                    $.each($(".checkbox_perfils:checked"), function(j, data) {
                        txt_perfils += '<span class="label label-primary">' + $(data).parent().parent().find('b').text() + '</span>';
                    })

                    pessoa = $("#listagem #"+int_id).parent().parent().children();
                    $(pessoa).eq(1).text(txt_nome);

                    $(pessoa).eq(2).html(txt_perfils);

                    $("#barra-ferramenta").children().eq(1).find('#btn_formulario_inserir').remove();
                }
            } else {
                $("#listagem").append("<tr> <td> <input id='"+pessoa.id+"' value='"+pessoa.id+"' type='radio' class='usuario_selecionado' /> </td> <td>"+pessoa.id+"</td> <td>"+pessoa.nome+"</td> <td>b</td> </tr>");   
            }
            $(".usuario_selecionado").removeAttr('checked');
            $("#btn_ferramenta_editar").show();
            $("#btn_formulario_inserir").remove();

            $("#div_formulario").hide().delay(1000);
            $("#div_listagem").show();
            /*
            $("#div_modulos").html('');
            $("#div_perfils").html('');*/
        /*} else {
            alert(pessoa.resultado.message);
        }
    })
    .error(function (erro){ console.log(erro.result) })
});
/*
$(document).on("click", ".usuario_selecionado", function() {
    $("#btn_ferramenta_editar").show();
    $("#btn_ferramenta_excluir").show();
    //$("#btn_ferramenta_excluir").show();
    //$(".usuario_selecionado").removeAttr("checked");
    
    //$(".usuario_selecionado[id="+this.value+"]").click();
    //return false;
});
/*
$(document).on("click", "table tbody tr", function() { 
    var id = $(this).children('td').eq(0).find('input').val();
    
    $("input[type=checkbox]").attr('checked', false);
    
    $("#"+id).attr('checked', true);
    $("#"+id).click();
});*/
