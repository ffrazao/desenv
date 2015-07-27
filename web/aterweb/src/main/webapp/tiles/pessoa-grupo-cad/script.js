/** 
 * Script do m�dulo pessoa-grupo-cad
 */

// identificacao do endereco url para este script
var PAGINA = "pessoa-grupo-cad";
(function () {
aterweb.controller("cadastroCtrl",
	function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService) {

		$scope.emProcessamento(false);
		toaster.options = {positionClass : "toast-bottom-right"};
		$scope.selected = [];

		// variaveis de apoio
		$scope.organizando = false; // permite fazer organiza��o dos grupos na tela de listagem
		
		if (!$rootScope.filtro) {
			$rootScope.filtro = {
				selecionarSomenteGestores : true,
				gestorGrupoTecnicoCheck : false,
				gestorGrupoUnidadeOrganizacionalCheck : false,
				gestorGrupoInstituicaoCheck : false,
				situacaoGrupo : ""
			}
		}
		if (!$rootScope.pessoaGrupo) {
			$rootScope.pessoaGrupo = {currentNode : null};
		}
		
		$scope.registroLocal = "noDescendente";
		//$scope.registro = {};
		//$scope.registro.nivelGestao = "T";
		
		$scope.estaoTodosSelecionados = function() {
		   return $rootScope.filtro.gestorGrupoTecnicoCheck === true &&
			   $rootScope.filtro.gestorGrupoUnidadeOrganizacionalCheck === true &&
			   $rootScope.filtro.gestorGrupoInstituicaoCheck === true;
		};
		
		$scope.selecionaTodos = function() {
           $rootScope.filtro.gestorGrupoTecnicoCheck = true;
		   $rootScope.filtro.gestorGrupoUnidadeOrganizacionalCheck = true;
		   $rootScope.filtro.gestorGrupoInstituicaoCheck = true;
		};
		
		$scope.removerMembroGrupo = function(membro) {
	        var index = $rootScope.registro.pessoaRelacionamentos.indexOf(membro);
	        $rootScope.registro.pessoaRelacionamentos.splice(index,1);
		}
		
		$scope.novoMembroGrupo = function() {
			$scope.membroGrupo = {
				"proprietario" : "N",
				"podeModificar" : "S",
				"acessoGrupoTempoIndeterminado" : "S"
			};
			$("#modalMembroGrupoSocial").modal("show");
		};
		
		$scope.buscarMembroGrupo = function() {
			try {
				$rootScope.emProcessamento(true);
				$http.get(baseUrl + 'pessoa-cad' + ACAO_FILTRAR, { params : $scope.membroGrupo.filtro })
						.success(function(data, status, headers, config) {
									if (!data.executou) {
										toaster.pop('error', null, data);
									} else if (typeof data.resultado === typeof undefined
											|| data.resultado === null
											|| data.resultado.length === 0) {
										toaster.pop('error', null, "Nenhum registro encontrado!");
									} else {
										$scope.membroGrupo.pessoasEncontradas = data.resultado;
									}
						})
						.error(function(data) {
							toaster.pop('error', null, data);
						});
			} finally {
				$rootScope.emProcessamento(false);
			}
		};
		
		$scope.salvarMembroGrupo = function() {
			if ($rootScope.registro.pessoaRelacionamentos == null) {
				$rootScope.registro.pessoaRelacionamentos = new Array();
			}
			var pessoaSelecionada = $.parseJSON($scope.membroGrupo.pessoaSelecionada);
			
			delete pessoaSelecionada["cpf"];
			delete pessoaSelecionada["cnpj"];
			
			pessoaRelacionamento = {
				pessoa : pessoaSelecionada,
				relacionamento : {"@class": "gov.emater.aterweb.model.Relacionamento", inicio:null, termino:null},
				podeModificar : $scope.membroGrupo.podeModificar ? $scope.membroGrupo.podeModificar : "N",
				proprietario : $scope.membroGrupo.proprietario ? $scope.membroGrupo.proprietario : "N"
			};
			if (pessoaRelacionamento.pessoa.pessoaTipo == 'PF') {
				pessoaRelacionamento.pessoa['@class'] = "gov.emater.aterweb.model.PessoaFisica";
			} else if (pessoaRelacionamento.pessoa.pessoaTipo == 'PJ') {
				pessoaRelacionamento.pessoa['@class'] = "gov.emater.aterweb.model.PessoaJuridica";
			}
			if ($scope.membroGrupo.acessoGrupoTempoIndeterminado === 'N') {
				pessoaRelacionamento.relacionamento.indeterminado = "N";
				pessoaRelacionamento.relacionamento.inicio = $scope.membroGrupo.filtro.inicio;
				pessoaRelacionamento.relacionamento.termino = $scope.membroGrupo.filtro.termino;
			} else {
				pessoaRelacionamento.relacionamento.indeterminado = "S";
				pessoaRelacionamento.relacionamento.inicio = null;
				pessoaRelacionamento.relacionamento.termino = null;
			}
			
			if (!$rootScope.registro.pessoaRelacionamentos) {
				$rootScope.registro.pessoaRelacionamentos = [];
			}
			
			if ($rootScope.registro.pessoaRelacionamentos.length === 0) {
				pessoaRelacionamento.proprietario = "S";
			}
			
			if (pessoaRelacionamento.proprietario === "S") {
				pessoaRelacionamento.podeModificar = "S";
		    	angular.forEach($rootScope.registro.pessoaRelacionamentos, function (r) {
		            r.proprietario = "N";
		        });
			}
			$rootScope.registro.pessoaRelacionamentos.push(pessoaRelacionamento);

			$scope.membroGrupo = null;
		};

		$scope.filtrar = function() {
			$rootScope.botoesAcao("filtrar");
			$location.path('/filtro');
		};
		
		$scope.excluir = function() {
			$http.get(baseUrl + PAGINA + ACAO_EXCLUIR, {
				params : {
					id : $rootScope.linhasSelecionadas[0].id
				}
			}).success(function(data) { 
    			toaster.pop('info', "Sucesso", "Registro EXCLUIDO com sucesso!");
    			$scope.executar();
			}).error(function(data) {
				toaster.pop('error', "ERRO", data);
			});
		}

	    $scope.executar = function() {
	    	$rootScope.emProcessamento(true);
	        $http.get(baseUrl + PAGINA + ACAO_FILTRAR, {params: angular.copy($rootScope.filtro)})
	             .success(function(data) {
	            	 try {
	 	                if (isUndefOrNull(data) || !data.executou || isUndefOrNull(data.resultado) || data.resultado.length === 0) {
		                	toaster.pop('error', null, "Nenhum registro encontrado!");
		                	return false;
		                }

	      	            $scope.lista = data.resultado;
	      	          
		    			$rootScope.botoesAcao("executar");
	      	            $location.path('/lista');
	            		 
	            	 } finally {
	 	            	$rootScope.emProcessamento(false);
	            	 }
	             })
	        .error(function(data) {
	        	try {
		        	$rootScope.emProcessamento(false);
		        	toaster.pop('error', null, data);
	        	} finally {
		            $location.path('/filtro');
	        	}
	        });
	    };
	    
	    $scope.setMembroGrupoProprietario = function(relacinamentos, registro) {
	    	angular.forEach(relacinamentos, function (r) {
	            r.proprietario = "N";
	        });
	    	registro.proprietario = "S";
	    };
	    
	    $scope.editar = function() {
			if (isUndefOrNull($rootScope.linhasSelecionadas) || $rootScope.linhasSelecionadas.length === 0) {
				toaster.pop('error', null, "Nenhum registro selecionado!");
				return;
			}
			$http.get(baseUrl + PAGINA + ACAO_RESTAURAR, {
				params : {
					id : $rootScope.linhasSelecionadas[0].id
				}
			}).success(function(data) {
				$rootScope.registro = data.resultado;
				
  	            // ajustar o campo data
  	            if ($rootScope.registro.pessoaRelacionamentos) {
      	            for (pr in $rootScope.registro.pessoaRelacionamentos) {
      	            	if ($rootScope.registro.pessoaRelacionamentos[pr].relacionamento) {
      	            		var indeterminado = "S";
      	            		if ($rootScope.registro.pessoaRelacionamentos[pr].relacionamento.inicio) {
      	            			$rootScope.registro.pessoaRelacionamentos[pr].relacionamento.inicio = dataToInputData($rootScope.registro.pessoaRelacionamentos[pr].relacionamento.inicio);
      	            			indeterminado = "N";
      	            		}
      	            		if ($rootScope.registro.pessoaRelacionamentos[pr].relacionamento.termino) {
      	            			$rootScope.registro.pessoaRelacionamentos[pr].relacionamento.termino = dataToInputData($rootScope.registro.pessoaRelacionamentos[pr].relacionamento.termino);
      	            			indeterminado = "N";
      	            		}
      	            		$rootScope.registro.pessoaRelacionamentos[pr].relacionamento.indeterminado = indeterminado;
      	            	}
      	            }
  	            }
				
				$scope.registroLocal = "mesmoNivel";
    			$rootScope.botoesAcao("editar");
				$location.path('/formulario');
			}).error(function(data) {
				toaster.pop('error', "ERRO", data);
			});
		};
	    

		$scope.incluir = function() {
			$rootScope.registro = {
				"@class" : "gov.emater.aterweb.model.PessoaGrupo",
				"situacao" : "A",
				"nivelGestao" : ""
			};
			$rootScope.botoesAcao('incluir');
			$location.path('/formulario');
		};

		$scope.salvar = function() {
			$rootScope.emProcessamento(true);
			$rootScope.registro['@class'] = "gov.emater.aterweb.model.PessoaGrupo";
			var dados = angular.copy($rootScope.registro);
			
			// resolvendo o grupo social pai
			var pai = {"@class": "gov.emater.aterweb.model.PessoaGrupo"};
			if (!isUndefOrNull($rootScope.linhasSelecionadas) && $rootScope.linhasSelecionadas.length > 0) {
				if ($scope.registroLocal === "noDescendente") {
					pai["id"] = angular.copy($rootScope.linhasSelecionadas[0]).id;
				} else if (!isUndefOrNull($rootScope.linhasSelecionadas[0].pai)) {
					pai["id"] = angular.copy($rootScope.linhasSelecionadas[0].pai).id;
				}
			}
			dados["pai"] = pai;
			dados["publicoAlvoConfirmacao"] = "N";
			
            // ajustar o campo data
            if (dados.pessoaRelacionamentos) {
  	            for (pr in dados.pessoaRelacionamentos) {
  	            	if (dados.pessoaRelacionamentos[pr].relacionamento) {
  	            		if (dados.pessoaRelacionamentos[pr].relacionamento.indeterminado === "S") {
  	            			dados.pessoaRelacionamentos[pr].relacionamento.inicio = null;
  	            			dados.pessoaRelacionamentos[pr].relacionamento.termino = null;
  	            		} else {
  	  	            		if (dados.pessoaRelacionamentos[pr].relacionamento.inicio) {
  	  	            		dados.pessoaRelacionamentos[pr].relacionamento.inicio = inputDataToData(dados.pessoaRelacionamentos[pr].relacionamento.inicio);
  	  	            		}
  	  	            		if (dados.pessoaRelacionamentos[pr].relacionamento.termino) {
  	  	            		dados.pessoaRelacionamentos[pr].relacionamento.termino = inputDataToData(dados.pessoaRelacionamentos[pr].relacionamento.termino);
  	  	            		}
  	            		}
  	            	}
  	            }
            }
			
			removerJsonId(dados);
			
			$http.post(baseUrl + PAGINA + ACAO_SALVAR, dados).success(
				function(data, status, headers, config) {
					try {
						if (data.executou) {
							$rootScope.botoesAcao("executar");
			    			$location.path('/lista');
			    			toaster.pop('info', "Sucesso", "Registro salvo com sucesso!");
			    			$scope.executar();
						} else {
							toaster.pop('error', "ERRO", "Erro ao salvar o registro: " + data.mensagem);
						}
					}
					finally {
						$rootScope.emProcessamento(false);
					}
				})
				.error(function(data) {
					$rootScope.emProcessamento(false);
					toaster.pop('error', "ERRO", "Erro ao salvar o registro: " + data.mensagem);
				});
		};
	
		// funcoes da arvore
		$scope.remove = function(scope) {
			scope.remove();
		};

		$scope.toggle = function(scope) {
			scope.toggle();
		};

		$scope.newSubItem = function(scope) {
			if (scope === null) {
				$scope.lista.push({
					"id" : $scope.lista.length,
					"nome" : "OxOx" + $scope.list.length,
					"filhos" : []
				});
				return;
			}
			
			var nodeData = scope.$modelValue;
			nodeData.filhos.push({
				id : nodeData.id * 10 + nodeData.filhos.length,
				nome : nodeData.nome + '.' + (nodeData.filhos.length + 1),
				filhos : []
			});
		};

		$scope.selectedItem = {};

		$scope.options = {
		};
		
		$scope.treeOptions = {
				
	    };

	    $scope.toggleChildren = function (scope) {
	    	if (scope.$nodeScope.collapsed) {
	    		$rootScope.emProcessamento(true);
	        	$http.get(
						baseUrl + PAGINA
								+ ACAO_RESTAURAR
								+ "Filhos",
						{params : {id : scope.$nodeScope.$modelValue.id}})
				.success(
						function(data, status, headers, config) {
							try {
								if (data.executou) {
									scope.$nodeScope.$modelValue.filhos = data.resultado;
									scope.toggle();
								} else {
									toaster.pop('error', "ERRO", "Erro ao carregar registros");
								}
							} finally {
								$rootScope.emProcessamento(false);
							}
						});
	    	} else {
                scope.toggle();
            }
	    };
/*
		$rootScope.$watch("registro.nivelGestao", function(newVal, oldVal, attr) {
			if (newVal !== "" && newVal !== oldVal) {
				if (!confirm("Confirma")) {
					newVal = oldVal;
				}
			}
		});
		
		$rootScope.mudarNivelGestao = function (evt) {
			if (!isUndefOrNull($rootScope.registro.nivelGestao) && $rootScope.registro.nivelGestao != "") {
				if ($rootScope.registro.nivelGestao === evt.target.value || (!confirm('Are you sure you want to select this ?'))) {
					evt.preventDefault();
					return;
				}
			}
			$rootScope.registro.nivelGestao = evt.target.value;
		};*/

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
		
	    $rootScope.popupSelecionar2 = function() {
	        if($rootScope.linhasSelecionadas.length === 1){
	            window.opener.$rootScope.$apply(function () {
	            	var retorno = location.search.split('retorno=')[1];
	            	window.opener.$rootScope[retorno]($rootScope.linhasSelecionadas);
	            });
	            window.close();
	        } else {
	            toaster.pop('info', null, "Selecione um registro!");
	        }
	    }
	    
	    $scope.grupoSocialOk = function() {
	        $scope.emProcessamento(true);
	        if (!$scope.$parent.frmGrupoSocial.$valid) {
	            $scope.emProcessamento(false);
	            $scope.submitted = true;
	            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
	            return;
	        }
	        $scope.emProcessamento(false);
	        
	        requisicaoService.dominio({
	            params : {
	                ent : 'PessoaGrupo',
	                npk : 'id',
	                vpk : $scope.grupoSocialK.pessoaGrupoCidadeVi.id,
	            }
	        }).success(function(data) {
	            if (data.executou) {
	                $scope.grupoSocialK.pessoaGrupoCidadeVi.nome = data.resultado[0].nome;
	                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.nome = data.resultado[0].pai.nome;
	                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.nome = data.resultado[0].pai.pai.nome;
	                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.nome = data.resultado[0].pai.pai.pai.nome;
	            }
	        });
	        
	        if ($scope.grupoSocialK.propriedadeRural && $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi && $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.id) {
	            requisicaoService.dominio({
	                params : {
	                    ent : 'PessoaGrupo',
	                    npk : 'id',
	                    vpk : $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.id,
	                }
	            }).success(function(data) {
	                if (data.executou) {
	                    $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.nome = data.resultado[0].nome;
	                }
	            });
	        }
	        
	        if ($scope.grupoSocialK.propriedadeRural && $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi && $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id) {
	            requisicaoService.dominio({
	                params : {
	                    ent : 'PessoaGrupo',
	                    npk : 'id',
	                    vpk : $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id,
	                }
	            }).success(function(data) {
	                if (data.executou) {
	                    $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.nome = data.resultado[0].nome;
	                }
	            });
	        }
	        $modalInstance.close($scope.grupoSocialK);
	    };

	    $scope.grupoSocialCancelar = function() {
	        $modalInstance.dismiss('cancel');
	    };

});


function carregaArvore(dados, root, pk, nomeCampo, fk, valor) {
	// se n�o houver dados retornar
	if (dados == null || dados.length <= 0) {
		return null;
	}
	$
			.each(
					dados,
					function(key, value) {
						// captar dados
						value = valorCampoJson(dados, value);

						var campo_pk = valorCampoJson(dados, value[pk]);
						var campo_nomeCampo = valorCampoJson(dados,
								value[nomeCampo]);
						var campo_fk = valorCampoJson(dados, value[fk]);

						var pai = valorCampoJson(dados, valor);

						// console.log("pk: [" + campo_pk + "] nomeCampo: ["
						// + campo_nomeCampo + "] fk: [" + campo_fk + "] pai
						// =>" + pai+"]");

						// se a foreign key for igual ao valor pesquisado
						if ((campo_fk == null && pai == null)
								|| (campo_fk != null && campo_fk == pai)
								|| (campo_fk != null && campo_fk['id'] != null && campo_fk['id'] == pai)) {
							// criar um novo no
							var no = {
								title : campo_nomeCampo,
								valor : value,
							};
							// verificar se h� sub itens
							sub = carregaArvore(dados, new Array(), pk,
									nomeCampo, fk, campo_pk);
							if (sub.length > 0) {
								no.children = sub;
								no.isFolder = true;
							}
							// adicionar no a arvore
							root.push(no);
						}
					});
	return root;
};

})();