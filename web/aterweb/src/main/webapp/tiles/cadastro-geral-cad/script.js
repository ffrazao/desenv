var PAGINA = 'cadastro-geral-cad';

function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService){
    
    toaster.options = {positionClass : "toast-bottom-right"};
    
    $scope.emProcessamento(false);
    
    $scope.paginaAtual = 1;
    
    $scope.copiarObjeto = function(fonte,campo,valor){
        var retorno = null;
        angular.forEach(fonte,function(objeto,chaveObj){
            angular.forEach(objeto,function(value,key){
                if(campo === key && valor === value){
                    retorno = angular.copy(objeto);
                }
            });
        });
        return retorno;
    };

    $scope.executar = function(){
    	$scope.emProcessamento(true);
    	try {
            $rootScope.botoesAcao('executar');
            if ($rootScope.filtro.numeroPagina < 1) {
            	$rootScope.filtro.numeroPagina = 1;
            }
            var pfiltro = {};
            angular.copy($rootScope.filtro, pfiltro);
    		$http.get(baseUrl + PAGINA + ACAO_FILTRAR, {
    			params : pfiltro
    		}).success(function(data) {
    			console.log(data);
    			$scope.lista = data.resultado;
    			$location.path('/lista');
    			$scope.paginaAtual = $rootScope.filtro.numeroPagina;
    		}).error(function(data) {
    			console.log('ERROR', data);
    		});
    	} finally {
        	$scope.emProcessamento(false);
    	}
    };
    
    $scope.editar = function(){
        if($rootScope.linhasSelecionadas.length === 0) {
        	toaster.pop('error', null, "Nenhum registro selecionado!");
        } else {
            id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
        
            $rootScope.botoesAcao('editar');
            $http.get(baseUrl + PAGINA + ACAO_RESTAURAR,{params: {id: id}})
            .success(function(data){
                console.log(data);
                $rootScope.registro = data.resultado;
                $location.path('/formulario');
            })
            .error(function(data){
                console.log('ERROR',data);
            });
        }
    };
    
    $scope.incluir = function(){
        $rootScope.botoesAcao('incluir');
        $rootScope.registro = {};                
        $location.path('/formulario');
    };
    
    $scope.salvar = function(formValido) {
    	
    	if (document.forms['frmCadastro'].className.indexOf("ng-invalid") > 0) {
    		$rootScope.submitted = true;
    		return;
    	}
    	
        $scope.emProcessamento(true);
        var novo = angular.copy($rootScope.registro);
        remodelarCampoJson(novo,$rootScope.registro);
        //removerJsonId($scope.registro);
        removerReferenciasJsonId($rootScope.registro);

        console.log($rootScope.registro);
        $http.post(baseUrl + PAGINA + ACAO_SALVAR, $rootScope.registro).success(function(data){
            console.log(data);
            if(data.executou) {
            	toaster.pop('info', null, "Salvo com sucesso!");
                if(true) {
                    $scope.executar();
                } else {
                    
                }
            } else {
            	toaster.pop('error', null, "Erro ao salvar!");
                console.log(data);
            }
        }).error(function(data){
        	toaster.pop('error', null, "SALVAR => Ocorreu algum erro!");
            console.log(data);
        });
        
        $scope.emProcessamento(false);
    };
    
    $rootScope.popupSelecionar = function(){
        if($rootScope.linhasSelecionadas.length === 1){
            window.opener.$scope.$apply(function () {
                 angular.copy($rootScope.linhasSelecionadas[0],window.opener.$scope.acoes.relacionamento.registro.pessoa);
            });
            window.close();
        }else{
        	toaster.pop('error', null, "Selecione um registro!");
        }
    };
    
    $scope.irParaPagina = function(novaPagina) {
    	if(novaPagina < 1) {
    		novaPagina = 1;
    	}
    	if ($rootScope.filtro.numeroPagina != novaPagina) {
        	$rootScope.filtro.numeroPagina = novaPagina; 
        	$scope.executar();
    	}
    };

    $scope.init = function () {
    	if (isUndefOrNull($rootScope.filtro.propriedadeStatus)) {
    		$rootScope.filtro.propriedadeStatus = "V";
    	}
    	if (isUndefOrNull($rootScope.filtro.produtorStatus)) {
            $rootScope.filtro.produtorStatus = "V";
    	}
    };
    $scope.init();

	/*
	 * Sess�o de funcoes e variaveis de apoio ao formulario principal
	 */
	$scope.pessoaSubmitted = false;
	$scope.apoio = {};
	$scope.apoio.verificar = true;

	requisicaoService.dominio({
		params : {
			ent : "PessoaGrupoEstadoVi",
			npk : "pessoaGrupoPaisVi.id",
			vpk : 1,
			ord : "sigla"
		}
	}).success(function(data) {
		$scope.apoio.estado = [ {
			"sigla" : "",
			"nome" : "Selecione"
		} ];
		angular.forEach(data.resultado, function(objeto, chaveObj) {
			$scope.apoio.estado.push({
				"sigla" : objeto.sigla,
				"nome" : objeto.nome
			});
		});
	});

	requisicaoService.enumeracao("Escolaridade").success(function(data) {
		$scope.apoio.escolaridade = [ {
			"codigo" : "",
			"descricao" : "Selecione"
		} ];
		angular.forEach(data.resultado, function(objeto, chaveObj) {
			$scope.apoio.escolaridade.push({
				"codigo" : objeto.codigo,
				"descricao" : objeto.descricao
			});
		});
	});
    $scope.apoio.pessoaIncluir = function () {
    	$scope.apoio.pessoaOriginal = null;
        $scope.apoio.pessoa = {};
    	$scope.apoio.verificar = true;
    };
    $scope.apoio.pessoaEditar = function (pessoa) {
    	$scope.apoio.pessoaOriginal = pessoa;
    	$scope.apoio.pessoa = angular.copy($scope.apoio.pessoaOriginal);
    	$scope.apoio.verificar = false;
    };
	$scope.apoio.pessoaVerificar = function(cpfCnpj) {
		var numCpfCnpj = angular.copy($scope.apoio.pessoa.cpf);
		if (!validarCpfCnpj(numCpfCnpj)) {
			toaster.pop('error', "", 'N�mero de CPF ou CNPJ inv�lido!');
			return;
		}
		numCpfCnpj = formataCpfCnpj(numCpfCnpj);
		requisicaoService.dominio({
			params : {
				ent : "CadPessoa",
				npk : "cpf",
				vpk : numCpfCnpj
			}
		}).success(function(data) {
			$scope.apoio.pessoa = {};
			if (data.executou && !isUndefOrNull(data.resultado)) {
				$scope.apoio.pessoa = angular.copy(data.resultado[0]);
			}
			if (isUndefOrNull($scope.apoio.pessoa.cpf) || ($scope.apoio.pessoa.cpf.trim().length == 0)) {
				$scope.apoio.pessoa.cpf = numCpfCnpj;
			}
			$scope.apoio.verificar = false;
		});
	};
    $scope.apoio.pessoaSalvar = function (formValido) {
    	if (!formValido) {
    		$scope.pessoaSubmitted = true;
    		return false;
    	}
    	if ($scope.apoio.pessoaOriginal === null) {
    		if (isUndefOrNull($scope.registro.cadPessoaList)) {
    			$scope.registro.cadPessoaList = [];
    		}
    		$scope.registro.cadPessoaList.push($scope.apoio.pessoa);
    	} else {
    		angular.copy($scope.apoio.pessoa, $scope.apoio.pessoaOriginal);
    	};
    	$scope.apoio.pessoaLimpar();
    	$('#modalPessoa').modal('hide');
	};
    $scope.apoio.pessoaRemover = function (pessoa) {
		$scope.registro.cadPessoaList.splice($scope.registro.cadPessoaList.indexOf(pessoa), 1);
    };
    $scope.apoio.pessoaLimpar = function (pessoa) {
        delete $scope.apoio.pessoa;
        delete $scope.apoio.pessoaOriginal;
        $scope.pessoaSubmitted = false;
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