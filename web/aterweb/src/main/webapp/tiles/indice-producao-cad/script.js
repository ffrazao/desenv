var PAGINA = 'indice-producao-cad';

function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService){
    
    $scope.emProcessamento(false);
    
    $scope.paginaAtual = 1;
    $rootScope.produtoServicoTree = {};
//	$rootScope.produtoServicoTree.currentNode = null;
	$rootScope.filtroProdutoServicoTree = {};
	$rootScope.filtroProdutoServicoTree.currentNode = null;
	
    $scope.copiarObjeto = function(fonte,campo,valor) {
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
            if ($rootScope.filtroProdutoServicoTree.currentNode != null) {
            	var prodServ = angular.copy($rootScope.filtroProdutoServicoTree.currentNode.id);
            	$rootScope.filtro.producaoProdutoServico = {"id": prodServ};
            }
            var pfiltro = angular.copy($rootScope.filtro);
            remodelarCampoJson(pfiltro,$rootScope.filtro);
            //removerJsonId($rootScope.registro);
            removerReferenciasJsonId($rootScope.filtro);
    		$http.post(baseUrl + PAGINA + ACAO_FILTRAR, pfiltro
    		).success(function(data) {
    			$scope.lista = transformaJsonId(data.resultado);
    			$location.path('/lista');
    			$scope.paginaAtual = $rootScope.filtro.numeroPagina;
    		}).error(function(data) {
    			console.log('ERROR', data);
    			toaster.pop('ERROR', "", data);
    		});
    	} finally {
        	$scope.emProcessamento(false);
    	}
    };
    
    $scope.editar = function(){
        if($rootScope.linhasSelecionadas.length === 0) {
            toaster.pop('ERROR', "", "Nenhum registro selecionado!");
        } else {
            id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
        
            $rootScope.botoesAcao('editar');
            $http.get(baseUrl + PAGINA + ACAO_RESTAURAR,{params: {id: id}})
            .success(function(data){
                $rootScope.registro = transformaJsonId(data.resultado);
                if (!isUndefOrNull($rootScope.registro.produtoServico) && !isUndefOrNull($rootScope.registro.produtoServico.perspectiva)) {
                	$rootScope.registro.produtoServicoPerspectiva = $rootScope.registro.produtoServico.perspectiva;
                	$scope.apoio.produtoServicoCarregar($rootScope.registro.produtoServicoPerspectiva);
                }
                $location.path('/formulario');
            })
            .error(function(data){
                console.log('ERROR',data);
                toaster.pop('ERROR', "", data);
            });
        }
    };
    
    $scope.incluir = function() {
        $rootScope.botoesAcao('incluir');
        $rootScope.registro = {};
        $location.path('/formulario');
    };
    
    $scope.salvar = function(formValido) {
    	
//    	if (!formValido) {
//    		$rootScope.submitted = true;
//    		return;
//    	}
    	
        $scope.emProcessamento(true);
		if ($rootScope.registro.produtoServicoPerspectiva === "AGRICOLA") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAgricola";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "FLORES") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAgricolaFlores";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "ANIMAL") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAnimal";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "CORTE") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAnimalCorte";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "LEITE") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAnimalLeite";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "POSTURA") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoAnimalPostura";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "SERVICO") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoServico";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "AGROINDUSTRIA") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoServicoAgroIndustria";
		} else if ($rootScope.registro.produtoServicoPerspectiva === "TURISMO") {
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PerspectivaProducaoServicoTurismo";
		}
		if (!$rootScope.registro.id) {
			$rootScope.registro.produtoServico = {id: $rootScope.produtoServicoTree.currentNode.id};
		}
		$rootScope.registro.pessoaGrupo['@class'] = "gov.emater.aterweb.model.PessoaGrupo";
		
        var novo = angular.copy($rootScope.registro);
        remodelarCampoJson(novo,$rootScope.registro);
        //removerJsonId(novo);
        removerReferenciasJsonId(novo);
        
        delete novo["produtoServicoPerspectiva"];

        $http.post(baseUrl + PAGINA + ACAO_SALVAR, novo).success(function(data){
            if(data.executou) {
            	toaster.pop('info', null, "Salvo com sucesso!");
                
                if(true) {
                    $scope.executar();
                } else {
                    
                }
            } else {
            	console.log(data);
            	toaster.pop('ERROR', null, "Erro ao salvar!");
            }
        }).error(function(data){
            console.log(data);
        	toaster.pop('ERROR', null, "SALVAR => Ocorreu algum erro!");
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
        	toaster.pop('info', "", "Selecione um registro!");
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
//    	if (isUndefOrNull($rootScope.filtro.propriedadeStatus)) {
//    		$rootScope.filtro.propriedadeStatus = "V";
//    	}
//    	if (isUndefOrNull($rootScope.filtro.produtorStatus)) {
//            $rootScope.filtro.produtorStatus = "V";
//    	}
    };
    $scope.init();
    
    /*
     * Sess�o de funcoes e variaveis de apoio ao formulario principal
     */
    $scope.apoioSubmitted = false;
    $scope.apoio = {};
    
//    $scope.apoio.pessoaGrupoJsonCarregar = function() {
//		// CARREGAR LOCALIZACOES
//		$http.get(baseUrl + "pessoaGrupoJson")
//		.success(function(data) {
//			if (data.executou) {
//				$scope.apoio.pessoaGrupoJson = $.parseJSON(data.resultado);
//				console.log($scope.apoio.pessoaGrupoJson);
//			} else {
//				toaster.pop('error', "", 'Erro ao tentar carregar parametros: pessoaGrupoJson');
//			}
//		})
//		.error(function(data) {
//			toaster.pop('error', "Erro no servidor!", data);
//			console.log('ERROR', data);
//		});
//	}();
    // constantes de apoio
	(function() {
		if (isUndefOrNull($scope.apoio.producaoPerspectivaFloresTipoList)) {
			$scope.apoio.producaoPerspectivaFloresTipoList = [];
		}
		if ($scope.apoio.producaoPerspectivaFloresTipoList.length == 0) {
			$scope.apoio.producaoPerspectivaFloresTipoList = requisicaoService
					.enumeracao("PerspectivaProducaoFloresTipo").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaFloresTipoList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}
		
		if (isUndefOrNull($scope.apoio.producaoPerspectivaFloresSistemaList)) {
			$scope.apoio.producaoPerspectivaFloresSistemaList = [];
		}
		if ($scope.apoio.producaoPerspectivaFloresSistemaList.length == 0) {
			$scope.apoio.producaoPerspectivaFloresSistemaList = requisicaoService
					.enumeracao("PerspectivaProducaoSistema").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaFloresSistemaList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}

		if (isUndefOrNull($scope.apoio.producaoPerspectivaFloresProtecaoEpocaFormaList)) {
			$scope.apoio.producaoPerspectivaFloresProtecaoEpocaFormaList = [];
		}
		if ($scope.apoio.producaoPerspectivaFloresProtecaoEpocaFormaList.length == 0) {
			$scope.apoio.producaoPerspectivaFloresProtecaoEpocaFormaList = requisicaoService
					.enumeracao("PerspectivaProducaoFloresProtecaoEpocaForma").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaFloresProtecaoEpocaFormaList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}
		
		if (isUndefOrNull($scope.apoio.producaoPerspectivaFloresUsoDaguaList)) {
			$scope.apoio.producaoPerspectivaFloresUsoDaguaList = [];
		}
		if ($scope.apoio.producaoPerspectivaFloresUsoDaguaList.length == 0) {
			$scope.apoio.producaoPerspectivaFloresUsoDaguaList = requisicaoService
					.enumeracao("PerspectivaProducaoFloresUsoDagua").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaFloresUsoDaguaList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}

		if (isUndefOrNull($scope.apoio.producaoPerspectivaAnimalSistemaList)) {
			$scope.apoio.producaoPerspectivaAnimalSistemaList = [];
		}
		if ($scope.apoio.producaoPerspectivaAnimalSistemaList.length == 0) {
			$scope.apoio.producaoPerspectivaAnimalSistemaList = requisicaoService
					.enumeracao("PerspectivaProducaoSistema").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaAnimalSistemaList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}
		
		if (isUndefOrNull($scope.apoio.producaoPerspectivaAnimalExploracaoList)) {
			$scope.apoio.producaoPerspectivaAnimalExploracaoList = [];
		}
		if ($scope.apoio.producaoPerspectivaAnimalExploracaoList.length == 0) {
			$scope.apoio.producaoPerspectivaAnimalExploracaoList = requisicaoService
					.enumeracao("PerspectivaProducaoAnimalExploracao").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaAnimalExploracaoList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}

		if (isUndefOrNull($scope.apoio.producaoPerspectivaServicoProjetoList)) {
			$scope.apoio.producaoPerspectivaServicoProjetoList = [];
		}
		if ($scope.apoio.producaoPerspectivaServicoProjetoList.length == 0) {
			$scope.apoio.producaoPerspectivaServicoProjetoList = requisicaoService
					.enumeracao("PerspectivaProducaoServicoProjeto").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaServicoProjetoList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}
		
		if (isUndefOrNull($scope.apoio.producaoPerspectivaServicoCondicaoList)) {
			$scope.apoio.producaoPerspectivaServicoCondicaoList = [];
		}
		if ($scope.apoio.producaoPerspectivaServicoCondicaoList.length == 0) {
			$scope.apoio.producaoPerspectivaServicoCondicaoList = requisicaoService
					.enumeracao("PerspectivaProducaoServicoCondicao").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaServicoCondicaoList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}

		if (isUndefOrNull($scope.apoio.producaoPerspectivaServicoProdutoList)) {
			$scope.apoio.producaoPerspectivaServicoProdutoList = [];
		}
		if ($scope.apoio.producaoPerspectivaServicoProdutoList.length == 0) {
			$scope.apoio.producaoPerspectivaServicoProdutoList = requisicaoService
					.enumeracao("PerspectivaProducaoServicoProduto").success(
							function(data) {
								if (data.executou) {
									$scope.apoio.producaoPerspectivaServicoProdutoList = angular.copy(data.resultado);
								} else {
									toaster.pop('error', "Erro", "Erro no servidor!");
								}
							});
		}
	}());
	
    $scope.apoio.produtoServico = [];
	$scope.apoio.produtoServicoCarregar = function(perspectiva) {
		// CARREGAR LISTAS E OPCOES
		$http.get(baseUrl + PAGINA + "/produtoServicoPorPerspectiva", {
			params : {
				"perspectiva" : perspectiva
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.produtoServico = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: produtoServico');
			}
		})
		.error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', "Erro no servidor!", data);
		});
	};
	
	// combo pais
	$scope.apoio.filtroPessoaGrupoPaisListCarregar = function() {
		
		// CARREGAR LISTAS E OPCOES
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoPaisVi",
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoPaisList = data.resultado;
				// definir valor padrao
//				if ($rootScope.filtro.pessoaGrupoPais == null) {
//					if ($scope.apoio.filtropessoaGrupoPaisList != null) {
//						for (var i = 0; i < $scope.apoio.filtropessoaGrupoPaisList.length; i++) {
//							if ($scope.apoio.filtropessoaGrupoPaisList[i]['padrao'] == "S") {
//								$rootScope.filtro.pessoaGrupoPais = angular.copy($scope.apoio.filtropessoaGrupoPaisList[i]);
//								break;
//							}
//						}
//					}
//				}
//				alert($rootScope.filtro.pessoaGrupoPais);
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoPaisList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	}();
	$scope.apoio.filtroPessoaGrupoPaisListAtualizar = function() {
	    $scope.apoio.filtroPessoaGrupoEstadoListCarregar();
	    $scope.apoio.filtroPessoaGrupoMunicipioListCarregar();
	    $scope.apoio.filtroPessoaGrupoCidadeListCarregar();
	};
	
	// combo estado
	$scope.apoio.filtroPessoaGrupoEstadoListCarregar = function() {
		// CARREGAR LISTAS E OPCOES
		$scope.apoio.filtroPessoaGrupoEstadoList = [];		
		var localidade = null;
		if (!isUndefOrNull($rootScope.filtro.pessoaGrupoPais) && !isUndefOrNull($rootScope.filtro.pessoaGrupoPais.id)) {
			localidade = $rootScope.filtro.pessoaGrupoPais.id;
		}
		
		if (localidade == null) {
			return;
		}
		
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoEstadoVi",
				npk : "pessoaGrupoPaisVi.id",
				vpk : localidade,
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoEstadoList = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoEstadoList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	};
	$scope.apoio.filtroPessoaGrupoEstadoListAtualizar = function() {
	    $scope.apoio.filtroPessoaGrupoMunicipioListCarregar();
	    $scope.apoio.filtroPessoaGrupoCidadeListCarregar();
	};
	
	// combo municipio
	$scope.apoio.filtroPessoaGrupoMunicipioListCarregar = function() {
		// CARREGAR LISTAS E OPCOES
		$scope.apoio.filtroPessoaGrupoMunicipioList = [];		
		var localidade = null;
		if (!isUndefOrNull($rootScope.filtro.pessoaGrupoEstado) && !isUndefOrNull($rootScope.filtro.pessoaGrupoEstado.id)) {
			localidade = $rootScope.filtro.pessoaGrupoEstado.id;
		}
		
		if (localidade == null) {
			return;
		}
		
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoMunicipioVi",
				npk : "pessoaGrupoEstadoVi.id",
				vpk : localidade,
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoMunicipioList = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoMunicipioList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	};
	$scope.apoio.filtroPessoaGrupoMunicipioListAtualizar = function() {
	    $scope.apoio.filtroPessoaGrupoCidadeListCarregar();
	};

	// combo cidade
	$scope.apoio.filtroPessoaGrupoCidadeList = [];
	$scope.apoio.filtroPessoaGrupoCidadeListCarregar = function() {
		// CARREGAR LISTAS E OPCOES
		$scope.apoio.filtroPessoaGrupoCidadeList = [];		
		var localidade = null;
		if (!isUndefOrNull($rootScope.filtro.pessoaGrupoMunicipio) && !isUndefOrNull($rootScope.filtro.pessoaGrupoMunicipio.id)) {
			localidade = $rootScope.filtro.pessoaGrupoMunicipio.id;
		}
		
		if (localidade == null) {
			return;
		}
		
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoCidadeVi",
				npk : "pessoaGrupoMunicipioVi.id",
				vpk : localidade,
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoCidadeList = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoCidadeList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	};
	$scope.apoio.filtroPessoaGrupoCidadeListAtualizar = function() {
	};
	
	// combo comunidade
	$scope.apoio.filtroPessoaGrupoComunidadeListCarregar = function() {
		// CARREGAR LISTAS E OPCOES
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoComunidadeVi",
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoComunidadeList = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoComunidadeList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	}();
	
	// combo bacia hidrografica
	$scope.apoio.filtroPessoaGrupoBaciaHidrograficaListCarregar = function() {
		// CARREGAR LISTAS E OPCOES
		$http.get(baseUrl + "dominio", {
			params : {
				ent : "PessoaGrupoBaciaHidrograficaVi",
				npk : "pessoaGrupo.id",
				ord : "nome"
			}
		})
		.success(function(data) {
			if (data.executou) {
				$scope.apoio.filtroPessoaGrupoBaciaHidrograficaList = data.resultado;
			} else {
				toaster.pop('error', "", 'Erro ao tentar carregar parametros: filtroPessoaGrupoBaciaHidrograficaList');
			}
		})
		.error(function(data) {
			toaster.pop('error', "Erro no servidor!", data);
			console.log('ERROR', data);
		});
	}();
	
	$scope.apoio.produtoServicoPerspectivaAtualizar = function (perspectiva) {
		$scope.apoio.produtoServicoCarregar(perspectiva);
	};
	$scope.apoio.producaoListEditar = function (producaoList) {
//    	$scope.apoio.producaoListOriginal = producaoList;
//    	$scope.apoio.producaoList = angular.copy($scope.apoio.producaoListOriginal);
    	
    	
    };
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	$scope.apoio.selecionarProducao = function(linha) {
		if (isUndefOrNull(linha.selecionado)) {
			linha.selecionado = true;
		} else {
			linha.selecionado = !linha.selecionado;
		}
	};
    $scope.apoio.producaoListLimpar = function (producao) {
        delete $scope.apoio.producao;
//        delete $scope.apoio.producaoListOriginal;
        $scope.apoioSubmitted = false;
    };
    $scope.apoio.producaoRemover = function (producao) {
    	$rootScope.registro.producaoList.splice($rootScope.registro.producaoList.indexOf(producao), 1);
    };
    
    
    
    
    
    
    
    
	$scope.apoio.producaoListDefinir = function() {
		// criticar o parametro principal
		if (isUndefOrNull($rootScope.registro.pessoaGrupo) || isUndefOrNull($rootScope.registro.pessoaGrupo.id)) {
			toaster.pop('error', "", 'Comunidde N�o Informada');
			return;
		}
		
		// captar as prop rurais da comunidade informada
		$scope.apoio.producaoList = [];
		$http
			.get(baseUrl + PAGINA + "/propriedadeRuralPorPessoaGrupo", {
				params : {
					"pessoaGrupoId" : $scope.registro.pessoaGrupo.id
				}
			})
			.success(
				function(data) {
					if (data.executou) {
						if (!isUndefOrNull(data.resultado)) {
							$scope.apoio.producaoList = angular.copy(data.resultado);
						}
						if ($scope.apoio.producaoList.length == 0) {
							toaster.pop('error', "", 'Ainda nao ha propriedades rurais cadastradas nesta comunidade.');
							$('#modalProducao').modal('hide');
							return;
						}
						// marcar as produ;�es j[a
						for (apoio in $scope.apoio.producaoList) {
							for (registro in $rootScope.registro.producaoList) {
								if ($scope.apoio.producaoList[apoio].id == $rootScope.registro.producaoList[registro].propriedadeRural.id) {
									$scope.apoio.producaoList[apoio].selecionado = "true";
									break;
								}
							}
						}
					} else {
						toaster.pop('error', "", 'Erro ao tentar carregar parametros: produtoServico');
					}
			}).error(function(data) {
				toaster.pop('error', "Erro no servidor!", data);
				console.log('ERROR', data);
			});
	};
	$scope.apoio.producaoListSalvar = function (formValido) {
    	if (!formValido) {
    		$scope.apoioSubmitted = true;
    		return false;
    	}
    	if (isUndefOrNull($scope.apoio.producaoList)) {
    		$scope.apoio.producaoList = [];
    	}
		if (isUndefOrNull($rootScope.registro.producaoList)) {
			$rootScope.registro.producaoList = [];
		}
		for (apoio in $scope.apoio.producaoList) {
			if ($scope.apoio.producaoList[apoio].selecionado === "true") {
				var encontrou = false;
				for (registro in $rootScope.registro.producaoList) {
					if ($scope.apoio.producaoList[apoio].id == $rootScope.registro.producaoList[registro].propriedadeRural.id) {
						encontrou = true;
						break;
					}
				}
				if (!encontrou) {
					var responsavelList = [];
					for (responsavel in $scope.apoio.producaoList[apoio].exploracaoList) {
						responsavelList.push({
							exploracao : {
								id : $scope.apoio.producaoList[apoio].exploracaoList[responsavel].exploracaoId,
								pessoaMeioContato : {
									pessoa : {
										"@class": $scope.apoio.producaoList[apoio].exploracaoList[responsavel].classe,
										nome : $scope.apoio.producaoList[apoio].exploracaoList[responsavel].nome
									}
								},
								area: $scope.apoio.producaoList[apoio].exploracaoList[responsavel].exploracaoArea,
								regime: $scope.apoio.producaoList[apoio].exploracaoList[responsavel].exploracaoRegime
							}
						});
					}
					$rootScope.registro.producaoList
							.push({
								propriedadeRural : {
									id : $scope.apoio.producaoList[apoio].id,
									nome : $scope.apoio.producaoList[apoio].nome
								},
								responsavelList : responsavelList
							});
				}
			} else {
				for (registro in $rootScope.registro.producaoList) {
					if ($scope.apoio.producaoList[apoio].id == $rootScope.registro.producaoList[registro].propriedadeRural.id) {
						$rootScope.registro.producaoList.splice(registro, 1);
						break;
					}
				}
			}
		};
		// remover itens invalidos
		for (registro in $rootScope.registro.producaoList) {
			var encontrou = false;
			for (apoio in $scope.apoio.producaoList) {
				if ($scope.apoio.producaoList[apoio].id == $rootScope.registro.producaoList[registro].propriedadeRural.id) {
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$rootScope.registro.producaoList.splice(registro, 1);
			}
		}
		$scope.apoio.producaoListLimpar();
		$('#modalProducao').modal('hide');
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