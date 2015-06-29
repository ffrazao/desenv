/*
 ______      __   ___                      __                            
/\  _  \    /\ \ /\_ \                    /\ \              __           
\ \ \L\ \   \_\ \\//\ \      __   _ __    \ \ \      __  __/\_\  ____    
 \ \  __ \  /'_` \ \ \ \   /'__`\/\`'__\   \ \ \  __/\ \/\ \/\ \/\_ ,`\  
  \ \ \/\ \/\ \L\ \ \_\ \_/\  __/\ \ \/     \ \ \L\ \ \ \_\ \ \ \/_/  /_ 
   \ \_\ \_\ \___,_\/\____\ \____\\ \_\      \ \____/\ \____/\ \_\/\____\
    \/_/\/_/\/__,_ /\/____/\/____/ \/_/       \/___/  \/___/  \/_/\/____/                                                                       
 * 
 * @author Adler Luiz
 * @date 19/05/2014
 * @modify 09/06/2014
 */

var PAGINA = 'propriedade-rural-cad';

aterweb.controller('cadastroCtrl',
function cadastroCtrl($scope, $rootScope, $http, $location, toaster, requisicaoService) {    

    $scope.selectSistemaProducao    = [];
    
    $scope.acoes = {};
    $scope.acoes.relacionamento = {};
    $scope.acoes.relacionamento.registro = {};
    $scope.acoes.relacionamento.registro.pessoa = {};
    
    $scope.usoSolo = {};
    
//FIM ESCOPO ======================================================================================================================================
    $scope.retornaPais();
    $scope.retornaComunidades();
    
    //$http.get(baseUrl + "dominio", {params: {ent: 'SistemaProducao', ord: 'nome'}})
    requisicaoService.dominio({params: {ent: 'SistemaProducao', ord: 'nome'}})
    .success(function(data) {
        angular.forEach(data.resultado, function(value) {
            $scope.selectSistemaProducao.push(value);
        });
    });

    $scope.executar = function() {
        requisicaoService.filtrar({params: $rootScope.filtro});
    };
    
    $scope.registroAnterior = function() {
        if($rootScope.linhaAtual === 0) {
            toaster.pop('info', null, "Este é o primeiro registro!");
        } else {
            $rootScope.linhaAtual--;
            $scope.editar();
        }
    };
    
    $scope.registroProximo = function() {
        if($rootScope.linhaAtual === $rootScope.linhasSelecionadas.length-1) {
            toaster.pop('info', null, "Este é o último registro!");
        } else {
            $rootScope.linhaAtual++;
            $scope.editar();
        }
    };
    
    $scope.incluir = function() {
        $rootScope.registro = {};
        $rootScope.linhasSelecionadas = [];
        $rootScope.registro.propriedadeRuralPessoaGrupoList = [{},{}];
        $rootScope.botoesAcao('incluir');
        $location.path('formulario');
    };
    
    $scope.editar = function() {
        if($rootScope.linhasSelecionadas.length === 0) {
            toaster.pop('info', null, "Nenhum registro selecionado!");
        } else {
            $rootScope.emProcessamento(true);
            try {
            	
            	id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
            	
            	requisicaoService.restaurar({params: {id: id}})
            	.success(function(data){
            		if(data.executou) {
            			$rootScope.registro = data.resultado;
            			
            			angular.forEach($scope.registro.arquivoList, function(data, i){
            				$scope.arquivosUpload.push({arquivo :{ id: data.arquivo.id } });
            			});
            			
            			var tipoLocalizacao = $scope.registro.propriedadeRuralPessoaGrupoList;
            			
            			var idsComunidades = [];
            			var idsBacias = [];
            			
            			angular.forEach(tipoLocalizacao, function(data, i){
            				if(campoRecursivo(data.pessoaGrupo.pessoaGrupoTipo, 'codigo') === "COMUNIDADE") {
            					id = data.pessoaGrupo.id;
            					rel = data.id;
            					$('#comuEndereco option[value="'+id+'"]').attr('data-rel', rel);
            					idsComunidades.push(id);
            				} else if(campoRecursivo(data.pessoaGrupo.pessoaGrupoTipo, 'codigo') === "BACIA_HIDROGRAFICA") {
            					id = data.pessoaGrupo.id;
            					rel = data.id;
            					$('#baciaEndereco option[value="'+id+'"]').attr('data-rel', rel);
            					idsBacias.push(id);
            				}
            			});
            			$("#comuEndereco").val(idsComunidades);
            			$scope.comuEndereco = idsComunidades;
            			$("#baciaEndereco").val(idsBacias);
            			
            			var cidade = valorCampoJson($rootScope.registro, $rootScope.registro.meioContatoEndereco.pessoaGrupoCidadeVi);
            			var municipio = valorCampoJson($rootScope.registro, cidade.pessoaGrupoMunicipioVi);
            			var estado = valorCampoJson($rootScope.registro, municipio.pessoaGrupoEstadoVi);
            			var pais = valorCampoJson($rootScope.registro, estado.pessoaGrupoPaisVi);
            			
            			$rootScope.registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.id = pais.id;
            			
            			$rootScope.retornaEstados(pais.id);
            			$rootScope.registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.id = estado.id;
            			
            			$rootScope.retornaMunicipios(estado.id);
            			$rootScope.registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.id = municipio.id;
            			
            			$rootScope.retornaCidades(municipio.id);
            			$rootScope.registro.meioContatoEndereco.pessoaGrupoCidadeVi.id = cidade.id;
            			
            			$rootScope.retornaComunidadeBacia(cidade.id);
            			
            			if (!isUndefOrNull($rootScope.registro.propriedadeRuralPessoaGrupoList) && $rootScope.registro.propriedadeRuralPessoaGrupoList.length == 2) {
                			if($rootScope.registro.propriedadeRuralPessoaGrupoList[0].pessoaGrupo.pessoaGrupoTipo.codigo === "COMUNIDADE") {
                				$rootScope.registro.comunidade = $rootScope.registro.propriedadeRuralPessoaGrupoList[0].pessoaGrupo;
                				$rootScope.registro.bacia = $rootScope.registro.propriedadeRuralPessoaGrupoList[1].pessoaGrupo;
                			} else {
                				$rootScope.registro.bacia = $rootScope.registro.propriedadeRuralPessoaGrupoList[0].pessoaGrupo;
                				$rootScope.registro.comunidade = $rootScope.registro.propriedadeRuralPessoaGrupoList[1].pessoaGrupo;
                			}
            			}
            			
            			$location.path('/formulario');
            		} else {
            			toaster.pop('error', null, "Erro ao recuperar dados!");
            		}
            	})
            	.error(function(data){
            		console.log("EDITAR => Ocorreu algum erro!");
            		console.log(data);
            	});
            	
            	$rootScope.botoesAcao('editar');
            } finally {
                $rootScope.emProcessamento(false);
            }
        }
    };
    
    $scope.salvar = function() {

    	//$scope.registro.comunidade["@class"] = "gov.emater.aterweb.model.PessoaGrupo";
		//$scope.registro.bacia["@class"] = "gov.emater.aterweb.model.PessoaGrupo";
        
        if($rootScope.linhasSelecionadas.length !== 0) { // Se tiver id é edição, caso contrário é um novo registro;
            var entidade = angular.copy($rootScope.registro);

            if (isUndefOrNull(entidade.propriedadeRuralPessoaGrupoList) || entidade.propriedadeRuralPessoaGrupoList.length != 2) {
            	entidade.propriedadeRuralPessoaGrupoList = [{},{}];
            }
            entidade.propriedadeRuralPessoaGrupoList[0].pessoaGrupo = $scope.registro.comunidade;
            entidade.propriedadeRuralPessoaGrupoList[1].pessoaGrupo = $scope.registro.bacia;
            
/*            if(entidade.propriedadeRuralPessoaGrupoList[0].pessoaGrupo.pessoaGrupoTipo.codigo === "COMUNIDADE") {
                entidade.propriedadeRuralPessoaGrupoList[0].pessoaGrupo = $scope.registro.comunidade;
                entidade.propriedadeRuralPessoaGrupoList[1].pessoaGrupo = $scope.registro.bacia;
            } else {
                entidade.propriedadeRuralPessoaGrupoList[0].pessoaGrupo = $scope.registro.bacia;
                entidade.propriedadeRuralPessoaGrupoList[1].pessoaGrupo = $scope.registro.comunidade;
            }
*/            
        } else {
            var entidade = $rootScope.registro;
            
            if (isUndefOrNull(entidade.propriedadeRuralPessoaGrupoList) || entidade.propriedadeRuralPessoaGrupoList.length != 2) {
            	entidade.propriedadeRuralPessoaGrupoList = [{},{}];
            }
            entidade.propriedadeRuralPessoaGrupoList[0].pessoaGrupo = $rootScope.registro.bacia;
            entidade.propriedadeRuralPessoaGrupoList[1].pessoaGrupo = $rootScope.registro.comunidade;
        }
        if (!isUndefOrNull(entidade.meioContatoEndereco)) {
        	entidade.meioContatoEndereco['@class'] = "gov.emater.aterweb.model.MeioContatoEndereco";
        }
        
        remodelarCampoJson($rootScope.registro, entidade);
        
        requisicaoService.salvar(entidade);
    };
    
    $scope.montarArquivos = function() {
        
    };
    
    $scope.procurarPessoa = function() {
        procurarPessoa();
    };
    
    $scope.detalharPessoaPorId = function(id) {
        detalharPessoaPorId(id);
    };
    
    $scope.buscarPessoaRelacionamento = function() {
        window.$scope = $scope;
        window.open(baseUrl+'pessoa-cad/?modo=P', 99, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
    };
    
    $scope.acoes.relacionamento.salvar = function() {
        removerArquivoList($scope.acoes.relacionamento.registro);
        
        if(!$rootScope.registro.meioContatoEndereco) {
            $rootScope.registro.meioContatoEndereco = {};
        }
        $rootScope.registro.meioContatoEndereco.pessoaMeioContatoList = [];
        
        if($scope.acoes.relacionamento.registro.pessoa.pessoaTipo === 'PF'){
            $scope.acoes.relacionamento.registro.pessoa['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
        }else if($scope.acoes.relacionamento.registro.pessoa.pessoaTipo === 'PJ'){
            $scope.acoes.relacionamento.registro.pessoa['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
        }else{
            $scope.acoes.relacionamento.registro.pessoa['@class'] = null;
        }
        $scope.acoes.relacionamento.registro.finalidade = 'C';
        $scope.acoes.relacionamento.registro.meioContato = {};
        $scope.acoes.relacionamento.registro.meioContato = { '@class': "gov.emater.aterweb.model.MeioContatoEndereco", id: $rootScope.registro.meioContatoEndereco.id };
        
        $rootScope.registro.meioContatoEndereco.pessoaMeioContatoList.push($scope.acoes.relacionamento.registro);
        
        console.log($scope.acoes.relacionamento.registro);
    };
    
    $scope.acoes.relacionamento.editar = function(relacionamento){
        $scope.acoes.relacionamento.original = relacionamento;
        $scope.acoes.relacionamento.registro = angular.copy($scope.acoes.relacionamento.original);
        
        var relTipo = valorCampoJson($rootScope.registro,$scope.acoes.relacionamento.registro.exploracao.regime);
        var relFuncao = valorCampoJson($rootScope.registro,$scope.acoes.relacionamento.registro.exploracao.area);

       
        $scope.acoes.relacionamento.registro.exploracao.regime = relTipo;
        $scope.acoes.relacionamento.registro.exploracao.area = relFuncao;
    };
    
    $scope.acoes.relacionamento.remover = function(relacionamento){
        var index = $rootScope.registro.meioContatoEndereco.pessoaMeioContatoList.indexOf(relacionamento);
        $rootScope.registro.meioContatoEndereco.pessoaMeioContatoList.splice(index, 1);
    };
    
    requisicaoService.dominio( {params: {ent: 'RelacionamentoTipo', grupoSocial:'N'}} )
        .success(function(data) {
            $scope.relacionamentoTipo = data.resultado;
        })
        .error(function(data) {
            toaster.pop('error', "ERRO", data);
            console.log('ERROR', data);
        });

        $scope.retornaRelacionamentoFuncao = function(id){
        console.log(id);
        
    requisicaoService.dominio({params: {ent: 'RelacionamentoConfiguracaoVi', npk:'tipo_id', vpk:id}})
        .success(function(data) {
            $scope.relacionamentoFuncao = data.resultado;
        })
        .error(function(data) {
            toaster.pop('error', "ERRO", data);
            console.log('ERROR', data);
        });
    };
    
    $scope.calculaUsoSoloCulturasPerenes = function() {
        $rootScope.registro.usoSoloCulturaPereneHa = $scope.usoSolo.usoSoloCulturaPereneHa;
        $rootScope.registro.usoSoloCulturaPereneVlUnit = $("#inputUsoSoloCulturasPerenesUnitario").maskMoney('unmasked')[0];
        
        $scope.usoSolo.usoSoloCulturaPereneVlTot = $rootScope.registro.usoSoloCulturaPereneHa * $rootScope.registro.usoSoloCulturaPereneVlUnit;
    };

    $scope.removerArquivo = function(arquivo){
        var index = $rootScope.registro.arquivoList.indexOf(arquivo);
        $rootScope.registro.arquivoList.splice(index,1);
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

});