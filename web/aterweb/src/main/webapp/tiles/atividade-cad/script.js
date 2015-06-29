const PAGINA = 'atividade-cad';

function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService,$q,$upload) {
    
	$scope.map = {
		center : {
			latitude : -15.732805,
			longitude : -47.903791
		},
		zoom : 10
	};
	
	$scope.options = {scrollwheel: false};
	  $scope.drawingManagerOptions = {
	    drawingMode: google.maps.drawing.OverlayType.MARKER,
	    drawingControl: true,
	    drawingControlOptions: {
	      position: google.maps.ControlPosition.TOP_CENTER,
	        drawingModes: [
	          google.maps.drawing.OverlayType.MARKER,
	          google.maps.drawing.OverlayType.POLYGON,
	        ]
	    },
	    circleOptions: {
	      fillColor: '#ffff00',
	        fillOpacity: 1,
	        strokeWeight: 5,
	        clickable: false,
	        editable: true,
	        zIndex: 1
	      }
	    };
	  $scope.markersAndCircleFlag = true;
	  $scope.drawingManagerControl = {};
	  $scope.$watch('markersAndCircleFlag', function() {
	    if (!$scope.drawingManagerControl.getDrawingManager) {
	      return;
	    }
	    var controlOptions = angular.copy($scope.drawingManagerOptions);
	    if (!$scope.markersAndCircleFlag) {
	      controlOptions.drawingControlOptions.drawingModes.shift();
	      controlOptions.drawingControlOptions.drawingModes.shift();
	    }
	    $scope.drawingManagerControl.getDrawingManager().setOptions(controlOptions);
	  });
	
	$rootScope.filtro["endereco"] = {};
	
	/*$rootScope.filtro["endereco"] = {
		"logradouro": "QNL 23",
		"pais" : {
			"id" : 16261
		},
		"estado" : {
			"id" : 19014
		},
		"municipio" : {
			"id" : 19015
		},
		"cidade" : {
			"id" : 19034
		},
		"baciaHidrografica" : {
			"id" : 34266
		},
		"comunidade" : {
			"id" : 34052
		}
	};*/

	$scope.exibeEndereco = false;
	$scope.toggleModal = function() {
		$scope.exibeEndereco = !$scope.exibeEndereco;
	};

	$rootScope.emProcessamento(false);
	
	// Configurações do Formulário
	
    toaster.options = {positionClass : "toast-bottom-right"};
    
    $scope.nomePagina = (typeof nomePagina === "undefined" || nomePagina === null) ? 'Formulário' : nomePagina;
    
    $scope.cadastroSubatividade = $scope.nomePagina !== 'Formulário';
    
    if (isUndefOrNull($rootScope.registro)) {
    	$rootScope.registro = {};
    }
    
	/*
	 * Sessaoo de funcoes e variaveis de apoio ao formulario principal
	 */	
	if (isUndefOrNull($scope.apoio)) {
		$scope.apoio = {};
	}

	if (isUndefOrNull($scope.hoje)) {
		var data = new Date();
		$scope.hoje = angular.copy(data);
		data.setHours(0);
		data.setMinutes(0);
		data.setSeconds(0);
		data.setMilliseconds(0);
		$scope.hojeInicio = angular.copy(data);
		data.setHours(23);
		data.setMinutes(59);
		data.setSeconds(59);
		data.setMilliseconds(999);
		$scope.hojeTermino = angular.copy(data);
	}
	
	
	$scope.paginaAtual = 1;
		
	// Configurações do Filtro
	
	if (isUndefOrNull($rootScope.filtro["inicio"] || $rootScope.filtro["termino"])) {
		var filtroInicio = angular.copy($scope.hoje);
		var filtroTermino = angular.copy($scope.hoje);
		
		filtroInicio.setMonth(filtroInicio.getMonth()-1);
		filtroInicio.setHours(0);
		filtroInicio.setMinutes(0);
		filtroInicio.setSeconds(0);
		filtroInicio.setMilliseconds(0);
		
		filtroTermino.setHours(23);
		filtroTermino.setMinutes(59);
		filtroTermino.setSeconds(59);
		filtroTermino.setMilliseconds(999);
		
		$rootScope.filtro["inicio"] = filtroInicio;
		$rootScope.filtro["termino"] = filtroTermino;
	}
			
	$scope.filtroMinInicio = function() {
		var data = angular.copy($rootScope.filtro["termino"]);
		if (data > $scope.hoje) {
			data = angular.copy($scope.hoje);
		}
		data.setFullYear(data.getFullYear() - 1);
		return data;
	}

	$scope.filtroMaxTermino = function() {
		var data = angular.copy($rootScope.filtro["inicio"]);
		if (data > $scope.hoje) {
			data = angular.copy($scope.hoje);
		}
		data.setFullYear(data.getFullYear() + 1);
		return data;
	}
	
	$scope.selecionaAssuntoDescendente = function (item) {
		var elemento = item.$nodeScope.$modelValue;
		if (isUndefOrNull(elemento.marcado)) {
			elemento.marcado = false;
		}
		var marcado = !elemento.marcado; 
		marcarAssuntoAcao(elemento, marcado);
		if (!marcado) {
			//$(item.$nodeScope.$parent.$nodeElement).find("#linhaChk").prop("indeterminate", true);
		}
		$scope.apoio.contadorAtividadeAssuntoAcao = 0;
		$scope.apoio.contarAtividadeAssuntoAcaoList();
	}
	
	marcarAssuntoAcao = function(elemento, marcado) {
		if (!isUndefOrNull(elemento.acoes)) {
			for (idx in elemento.acoes) {
				elemento.acoes[idx].marcado = marcado; 
			}
		}
		if (!isUndefOrNull(elemento.filhos)) {
			for (idx in elemento.filhos) {
				elemento.filhos[idx].marcado = marcado;
				marcarAssuntoAcao(elemento.filhos[idx], marcado);
			}
		}
	}
	
//	if (isUndefOrNull($scope.apoio.filtroSelecaoAssuntoAcao)) {
//		$scope.apoio.filtroSelecaoAssuntoAcao = [];
//	}
	
//	$scope.apoio.filtroSelecionaAssuntoAcao = function(assunto, acao, checkbox) {
//		if (acao === null) {
//			var campo = checkbox.$element.context.childNodes["3"].childNodes["1"];
//		} else {
//			var campo = checkbox.$element.context.childNodes["1"].children["0"];
//		}
//		if (campo.checked) {
//			$scope.apoio.filtroSelecaoAssuntoAcao.push({
//				"assunto" : assunto.id,
//				"acao" : acao === null ? null : acao.id,
//				"campo" : campo,
//				"finalidade" : assunto.finalidade
//			});
//		} else {
//			for (reg in $scope.apoio.filtroSelecaoAssuntoAcao) {
//				if ($scope.apoio.filtroSelecaoAssuntoAcao[reg].assunto === assunto.id) {
//					if ((acao === null && $scope.apoio.filtroSelecaoAssuntoAcao[reg].acao === null)
//							|| (acao !== null && $scope.apoio.filtroSelecaoAssuntoAcao[reg].acao === acao.id)) {
//						$scope.apoio.filtroSelecaoAssuntoAcao.splice(reg, 1);
//						break;
//					}
//				}
//			}
//		}
//	};
	
//	$scope.apoio.filtroLimparSelecaoAssuntoAcao = function() {
//		for (reg in $scope.apoio.filtroSelecaoAssuntoAcao) {
//			$scope.apoio.filtroSelecaoAssuntoAcao[reg].campo.checked = false;
//		}
//		$scope.apoio.filtroSelecaoAssuntoAcao = [];
//	}
	
//	if (isUndefOrNull($scope.apoio.registroSelecaoAssuntoAcao)) {
//		$scope.apoio.registroSelecaoAssuntoAcao = [];
//	}
	
//	$scope.apoio.registroSelecionaAssuntoAcao = function(assunto, acao, checkbox) {
//		if (acao === null) {
//			var campo = checkbox.$element.context.childNodes["3"].childNodes["1"];
//		} else {
//			var campo = checkbox.$element.context.childNodes["1"].children["0"];
//		}
//		if (campo.checked) {
//			$scope.apoio.registroSelecaoAssuntoAcao.push({
//				"assunto" : assunto.id,
//				"acao" : acao === null ? null : acao.id,
//				"campo" : campo
//			});
//		} else {
//			for (reg in $scope.apoio.registroSelecaoAssuntoAcao) {
//				if ($scope.apoio.registroSelecaoAssuntoAcao[reg].assunto === assunto.id) {
//					if ((acao === null && $scope.apoio.registroSelecaoAssuntoAcao[reg].acao === null)
//							|| (acao !== null && $scope.apoio.registroSelecaoAssuntoAcao[reg].acao === acao.id)) {
//						$scope.apoio.registroSelecaoAssuntoAcao.splice(reg, 1);
//						break;
//					}
//				}
//			}
//		}
//	};
	
//	$scope.apoio.registroLimparSelecaoAssuntoAcao = function() {
//		for (reg in $scope.apoio.registroSelecaoAssuntoAcao) {
//			$scope.apoio.registroSelecaoAssuntoAcao[reg].campo.checked = false;
//		}
//		$scope.apoio.registroSelecaoAssuntoAcao = [];
//	}

	if (isUndefOrNull($scope.apoio.filtroFuncaoList)) {
    	requisicaoService.enumeracao("gov.emater.aterweb.mvc.dto.AtividadeFuncaoDto").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.filtroFuncaoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data[mensagem])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.open("text/html", sdocument.write(data));
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data[mensagem])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.open("text/html", document.write(data));
			}
    	});
    }
    
    if (isUndefOrNull($scope.apoio.filtroJuncaoList)) {
    	$scope.apoio.filtroJuncaoList = [ {
			"codigo" : "E",
			"descricao" : "E"
		}, {
			"codigo" : "O",
			"descricao" : "Ou"
		} ];
    }
	
    $scope.filtrar = function() {
        $rootScope.botoesAcao('filtrar');
        $rootScope.emProcessamento(true);
        try {
        	$location.path('/filtro');
        } finally {
        	$rootScope.emProcessamento(false);
        }
    };    
    
    $scope.executar = function() {
    	$rootScope.emProcessamento(true);
		if ($rootScope.filtro.numeroPagina < 1) {
			$rootScope.filtro.numeroPagina = 1;
		}
		var f = angular.copy($rootScope.filtro);
		removerCampo(f, ['@jsonId']);
		removerCampo(f.atividadeAssuntoAcaoList, ['nome', 'acao', 'assunto']);
		$http.get(baseUrl + PAGINA + ACAO_FILTRAR, {
			"params" : f
		}).success(function(data) {
			$scope.paginaAtual = $rootScope.filtro.numeroPagina;
			if (data.executou) {
				if (data.resultado && data.resultado) {
					removerJsonId(data.resultado);
					$scope.lista = data.resultado;
					$location.path('/lista');
					$rootScope.botoesAcao('executar');
				} else {
					toaster.pop('error', null, "Nenhum registro localizado!");
				}
			} else {
				toaster.pop('error', null, data.mensagem);
			}
			$rootScope.emProcessamento(false);
		}).error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', null, data);
			document.open("text/html", document.write(data));
			$rootScope.emProcessamento(false);
		});
	};
	
	$scope.apoio.ajustaRegistro = function () {
    	if (!isUndefOrNull($rootScope.registro.registro) && $rootScope.registro.registro.trim().length > 0) {
    		$rootScope.registro.registro = new Date($rootScope.registro.registro);
    	}
    	if (!isUndefOrNull($rootScope.registro.inicio) && $rootScope.registro.inicio.trim().length > 0) {
    		$rootScope.registro.inicio = new Date($rootScope.registro.inicio);
    	}
    	if (!isUndefOrNull($rootScope.registro.previsaoConclusao) && $rootScope.registro.previsaoConclusao.trim().length > 0) {
        	$rootScope.registro.previsaoConclusao = new Date($rootScope.registro.previsaoConclusao);
    	}
		angular.forEach($rootScope.registro.atividadeArquivoList, function(value, key) {
			value.dataUpload = new Date(value.dataUpload);
		});
		angular.forEach($rootScope.registro.ocorrenciaList, function(value, key) {
			value.registro = new Date(value.registro);
		});
	};
    
    $scope.incluir = function() {
    	var p = $http.get(baseUrl + PAGINA + ACAO_INCLUIR);
    	p.then(function (payload) {
    		removerJsonId(payload.data.resultado);
        	$rootScope.registro = payload.data.resultado;
        	$scope.apoio.ajustaRegistro();
			if (!$scope.cadastroSubatividade) {
	    		$rootScope.botoesAcao('incluir');
	    		$location.path('/formulario');
    		} 
            $(document).ready(function() {
            	initialize();
			});
    	});
    };
            
    $scope.editar = function(numId) {
		$rootScope.emProcessamento(true);
        if(isUndefOrNull(numId) && $rootScope.linhasSelecionadas.length === 0) {
        	toaster.pop("error", null, "Nenhum registro selecionado!");
        	return;
        } 
    	if (isUndefOrNull(numId)) {
    		var id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
    	} else {
    		var id = numId;        		
    	}
		$http.get(baseUrl + PAGINA + ACAO_RESTAURAR, {
			params : {
				"id" : id
			}
		}).success(function(data) {
			$rootScope.emProcessamento(false);
			if (data.executou) {
				removerJsonId(data.resultado);
				$rootScope.registro = angular.copy(data.resultado);
				$scope.apoio.ajustaRegistro();
	    		$rootScope.botoesAcao('editar');
	    		$location.path('/formulario');
			} else {
				toaster.pop('error', null, data.mensagem);
				console.log(data.mensagem);
			}
		}).error(function(data) {
			$rootScope.emProcessamento(false);
			toaster.pop('error', null, data);
			console.log(data);
		});
    };
    
    $scope.salvar = function() {
    	$rootScope.emProcessamento(true);
    	if ($rootScope.usuarioLogado && $scope.apoio.totalAtividadePessoaPorTipo(['D']) === 0) {
    		if (confirm("Nenhum DEMANDANTE pela atividade informado. Vc será o DEMANDANTE desta atividade?")) {
    			$scope.apoio.incluirPessoa($rootScope.usuarioLogado.pessoa, "D");
    		}
    	}
    	if ($rootScope.usuarioLogado && $scope.apoio.totalAtividadePessoaPorTipo(['ER']) === 0) {
    		if (confirm("Nenhum RESPONSÁVEL pela atividade informado. Vc será o RESPONSÁVEL por esta atividade?")) {
    			$scope.apoio.incluirPessoa($rootScope.usuarioLogado.pessoa, "E");
    		}
    	}
    	$scope.frmCadastro.demandanteList.$setValidity("required", $scope.apoio.totalAtividadePessoaPorTipo(['D']) !== 0);        
        $scope.frmCadastro.executorList.$setValidity("required", $scope.apoio.totalAtividadePessoaPorTipo(['ER']) !== 0);
        $scope.frmCadastro.arquivoTamanhoTotal.$setValidity("max", !($scope.apoio.arquivoTamanhoTotal() > $rootScope.tamanhoMaxArquivo));
        
        if (!$scope.frmCadastro.$valid) {
    		$rootScope.submitted = true;
        	$scope.apoio.somenteUmaAba = false;
        	$scope.apoio.expandirTudo(true)
			toaster.pop('error', "Erro ao salvar!", "Verifique os campos marcados");
        	$rootScope.emProcessamento(false);
    		return;
    	}

        // necessario salvar todos os arquivos antes de salvar o registro principal
        var carregarArquivos = [];
        var chamarCarregarArquivos = [];
        for (idx in $rootScope.registro.atividadeArquivoList) {
        	var atividadeArquivo = $rootScope.registro.atividadeArquivoList[idx];
        	if (!atividadeArquivo.arquivo.id) {
        		if (!atividadeArquivo.file) {
        			toaster.pop('error', "Erro ao salvar!", "Informações de Arquivos para upload inválidas");
        			return;
        		}
        		carregarArquivos.push(atividadeArquivo);
        		chamarCarregarArquivos.push($upload.upload({url : baseUrl + 'arquivo/subir', method: 'POST', file: atividadeArquivo.file}));
        	}
		}
        if (carregarArquivos.length > 0) {
        	$q.all(chamarCarregarArquivos).then(
				function(response) {
					for (idx in carregarArquivos) {
						carregarArquivos[idx].arquivo.id = response[idx].data.files[0].id;
						console.log(response[idx]);
					}
					$scope.salvarRegistro();
				}, function(response) {
					for (idx in carregarArquivos) {
						if (response[idx].status > 0) {
							toaster.pop('error', "Erro ao salvar!", response[idx].status + ': ' + response[idx].data);
						}
					}
				}, function(evt) {
					for (idx in carregarArquivos) {
						$scope.progress[idx] = Math.min(100, parseInt(100.0 * evt[idx].loaded / evt[idx].total));
					}
				}
			);
        } else {
        	$scope.salvarRegistro();
        };
    };
    
    $scope.salvarRegistro = function() {
    	if (window.opener) {
    		window.opener.$rootScope.$apply(function () {
    			var retorno = location.search.split("retorno=")[1];
    			window.opener.$rootScope[retorno]($rootScope.registro);
    		});
    		window.close();
    	} else {
    		$http.post(baseUrl + PAGINA + ACAO_SALVAR, angular.copy($rootScope.registro))
    		.success(function(data) {
    			$rootScope.emProcessamento(false);
    			if (data.executou) {
    				toaster.pop('info', null, "Salvo com sucesso!");
    				$scope.executar();
    			} else {
        			if (data.mensagem) {
        				toaster.pop('error', "SALVAR => Ocorreu algum erro!", data.mensagem);
        			} else {
        				document.open("text/html", document.write(data));
        			}
    				console.log(data);
    			}
    		}).error(function(data) {
    			$rootScope.emProcessamento(false);
    			console.log(data);
    			if (data.mensagem) {
    				toaster.pop('error', "SALVAR => Ocorreu algum erro!", data.mensagem);
    			} else {
    				document.open("text/html", document.write(data));
    			}
    		});
    	}
    };
    
    $rootScope.popupSelecionar2 = function() {
        if($rootScope.linhasSelecionadas.length === 1){
            window.opener.$rootScope.$apply(function () {
            	var retorno = location.search.split('retorno=')[1];
            	window.opener.$rootScope[retorno]($rootScope.linhasSelecionadas);
            });
            //window.close();
        }else{
            toaster.pop('info', null, "Selecione um registro!");
        }
    };

    $rootScope.popupSelecionar = function(){
        if($rootScope.linhasSelecionadas.length === 1){
            window.opener.$scope.$apply(function () {
                 angular.copy($rootScope.linhasSelecionadas[0],window.opener.$scope.acoes.relacionamento.registro.pessoa);
            });
            //window.close();
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

    // codigo comum para verificar se algum id foi informado
    // caso positivo abre a tela em modo edicao de formulario
	if (!(typeof cadId === "undefined" || cadId === null)) {
		$rootScope.linhasSelecionadas = [];
		angular.forEach(cadId,function(value, key) {
			$rootScope.linhasSelecionadas.push({id: value});	
		})
		cadId = null;
		$scope.editar();
	}

	if (isUndefOrNull($scope.classificacao)) {
		$scope.classificacao = $scope.operacional;
	}
		
	$scope.max = 10;
	$scope.isReadonly = false;

	$scope.hoveringOver = function(value) {
		$scope.overStar = value;
		$scope.percent = 100 * (value / $scope.max);
	};

	if (isUndefOrNull($scope.apoio.exibeResumo)) {
		$scope.apoio.exibeResumo = false;
	}
	
	if (isUndefOrNull($scope.controle)) {
		$scope.controle = {}
	}
	
	if (isUndefOrNull($scope.controle.abas)) {
		$scope.controle.abas = {
	    	arquivos : false,
	    	como : false,
	    	complemento : false,
	    	oQue : false,
	    	onde : false,
	    	porQue : false,
	    	quando : false,
	    	quantoCusta : false,
	    	quem : true
		}
	}
	
	if (isUndefOrNull($rootScope.registro.principalAtividadeList)) {
		$rootScope.registro.principalAtividadeList = [];
	}
	
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.incluirAtividadePrincipal = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'atividade-cad/?modo=P&retorno=incluirAtividadePrincipalRetorno', 199, "width=1200px,height=600px,top=100px,left=300px,scrollbars=1");
	};
	// e outra para tratar o retorno
	$rootScope.incluirAtividadePrincipalRetorno = function (valor) {
		var reg = {principalAtividade: valor[0]};
		reg.principalAtividade.metodo = {"id": $scope.apoio.getIdMetodo(reg.principalAtividade.metodo)};
		reg.principalAtividade.atividadeAssuntoList = [];
		for (linha in reg.principalAtividade.assuntoAcaoList) {
			var assuntoAcao = {
				"acao": {"nome": reg.principalAtividade.assuntoAcaoList[linha].acaoNome}, 
				"assunto": {"nome": reg.principalAtividade.assuntoAcaoList[linha].assuntoNome}
			}; 
			reg.principalAtividade.atividadeAssuntoList.push({"assuntoAcao": assuntoAcao});
		}
		reg.principalAtividade.atividadePessoaList = [];
		for (linha in reg.principalAtividade.demandanteList) {
			var pessoa = {
				"pessoa": {nome: reg.principalAtividade.demandanteList[linha].nome, "@class": reg.principalAtividade.demandanteList[linha]["@class"]},
				"funcao": "D"
			}; 
			reg.principalAtividade.atividadePessoaList.push(pessoa);
		}
		for (linha in reg.principalAtividade.executorList) {
			var pessoa = {
				"pessoa": {"nome": reg.principalAtividade.executorList[linha].nome, "@class": reg.principalAtividade.executorList[linha]["@class"]},
				"funcao": "E"
			}; 
			reg.principalAtividade.atividadePessoaList.push(pessoa);
		}
		$rootScope.registro.principalAtividadeList.push(reg);
	};
	
    $scope.apoio.principalAtividadeRemover = function (item) {
    	$rootScope.registro.principalAtividadeList.splice($rootScope.registro.principalAtividadeList.indexOf(item), 1);
    };
    
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.incluirOutraPessoa = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-cad/?modo=P&retorno=incluirOutraPessoaRetorno', 299, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$rootScope.incluirOutraPessoaRetorno = function (valor) {
		$scope.apoio.outraPessoa = valor[0];
	};

	if (isUndefOrNull($rootScope.registro.atividadePessoaList)) {
		$rootScope.registro.atividadePessoaList = [];
	}
	
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.demandanteIncluir = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-cad/?modo=P&retorno=demandanteIncluirRetorno', 399, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$rootScope.demandanteIncluirRetorno = function (valor) {
		angular.forEach(valor, function(value, key) {
			$scope.apoio.incluirPessoa(value, "D");
		});
	}
	
	$scope.apoio.filtroPessoaListInit = function () {
		$rootScope.filtro.pessoaList = [];
		if (!$rootScope.filtro.pessoaList.length && $rootScope.usuarioLogado) {
			$scope.apoio.filtroPessoaIncluirElemento($rootScope.usuarioLogado.pessoa, "Q");
		}	
	};
	
	$scope.apoio.filtroGrupoSocialListInit = function () {
		$rootScope.filtro.grupoSocialList = [];
		if (!$rootScope.filtro.grupoSocialList.length && $rootScope.usuarioLogado) {
			$scope.apoio.filtroGrupoSocialIncluirElemento($rootScope.usuarioLogado.pessoa, "Q");
		}	
	};
	
	$scope.apoio.filtroPessoaIncluirElemento = function (pessoa, funcao, juncao) {
		if (isUndefOrNull(pessoa) || isUndefOrNull(funcao)) {
			toaster.pop('error', null, "Informações de pessoa relacionada a atividade incompletas");
			return;
		}
		if (isUndefOrNull(juncao)) {
			juncao = "E";
		}
		var pf = {"@class": "java.util.HashMap",  "pessoa" : pessoa, "funcao": funcao, "juncao": juncao};
		if (isUndefOrNull($rootScope.filtro.pessoaList)) {
			$rootScope.filtro.pessoaList = [];
		}
		$rootScope.filtro.pessoaList.push(pf);
	}
	
	$scope.apoio.filtroGrupoSocialIncluirElemento = function (pessoa, funcao, juncao) {
		if (isUndefOrNull(pessoa) || isUndefOrNull(funcao)) {
			toaster.pop('error', null, "Informações do grupo social relacionado a atividade incompletas");
			return;
		}
		if (isUndefOrNull(juncao)) {
			juncao = "E";
		}
		var pf = {"@class": "java.util.HashMap",  "pessoa" : pessoa, "funcao": funcao, "juncao": juncao};
		if (isUndefOrNull($rootScope.filtro.grupoSocialList)) {
			$rootScope.filtro.grupoSocialList = [];
		}
		$rootScope.filtro.grupoSocialList.push(pf);
	}
	
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.filtroGrupoSocialIncluir = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-grupo-cad/?modo=P&retorno=filtroGrupoSocialIncluirRetorno', 3991, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$rootScope.filtroGrupoSocialIncluirRetorno = function (valor) {
		angular.forEach(valor, function(value, key) {
			$scope.apoio.filtroGrupoSocialIncluirElemento(value, "D");
		});
	};
	
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.filtroPessoaIncluir = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-cad/?modo=P&retorno=filtroPessoaIncluirRetorno', 399, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$rootScope.filtroPessoaIncluirRetorno = function (valor) {
		angular.forEach(valor, function(value, key) {
			$scope.apoio.filtroPessoaIncluirElemento(value, "D");
		});
	};
	
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.executorIncluir = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-cad/?modo=P&retorno=executorIncluirRetorno', 499, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	};
	
	$scope.apoio.incluirPessoa = function (pessoa, funcao) {
		if (isUndefOrNull(pessoa) || isUndefOrNull(funcao)) {
			toaster.pop('error', null, "Informações de pessoa relacionada a atividade incompletas");
			return;
		}
		var encontrou = false;
		for (pesq in $rootScope.registro.atividadePessoaList) {
			if ($rootScope.registro.atividadePessoaList[pesq].pessoa.id === pessoa.id) {
				toaster.pop('error', null, "A pessoa {0} já está relacionada a esta atividade".format(pessoa.nome));
				return;
			}
		}
		var reg = {"pessoa" : pessoa, "funcao": funcao};
		// verificar se ja foi definido algum responsavel
		if (funcao === "E" && $scope.apoio.totalAtividadePessoaPorTipo(["ER"]) === 0) {
			reg["funcao"] = "ER";
			$scope.apoio.responsavel = reg.pessoa.id;
		}
		if (isUndefOrNull($rootScope.registro.atividadePessoaList)) {
			$rootScope.registro.atividadePessoaList = [];
		}
		$rootScope.registro.atividadePessoaList.push(reg);
	}
	
	// e outra para tratar o retorno
	$rootScope.executorIncluirRetorno = function (valor) {
		if (isUndefOrNull($rootScope.registro.atividadePessoaList)) {
			$rootScope.registro.atividadePessoaList = [];
		}
		angular.forEach(valor, function(value, key) {
			$scope.apoio.incluirPessoa(value, "E");
		});
	}
	
	$scope.apoio.executorResponsavel = function (registro) {
		for (item in $rootScope.registro.atividadePessoaList) {
			var funcao = $rootScope.registro.atividadePessoaList[item].funcao;
			if (funcao === "E" || funcao === "ER") {
				$rootScope.registro.atividadePessoaList[item].funcao = "E";
			}
		}
		registro.funcao = "ER";
		$scope.apoio.responsavel = registro.pessoa.id;
	}
	
	$scope.apoio.totalAtividadePessoaPorTipo = function (funcaoInformada) {
		var result = 0;
		if (isUndefOrNull($rootScope.registro) || isUndefOrNull($rootScope.registro.atividadePessoaList)) {
			return result;
		}
		for (item in $rootScope.registro.atividadePessoaList) {
			var funcao = $rootScope.registro.atividadePessoaList[item].funcao;
			for (sub in funcaoInformada) {
				if (funcao === funcaoInformada[sub]) {
					result++;
					break;
				}
			}
		}
		return result;
	};
	
	$scope.apoio.limparAtividadePessoaPorTipo = function (funcaoInformada) {
		for (item in $rootScope.registro.atividadePessoaList) {
			var reg = $rootScope.registro.atividadePessoaList[item];
			var funcao = reg.funcao;
			for (sub in funcaoInformada) {
				if (funcao === funcaoInformada[sub]) {
					$scope.apoio.pessoaRemover(reg);
					break;
				}
			}
		}
	};
	
	$scope.apoio.quemObjetoSalvar = function() {
		$scope.apoio.quem.objeto = {"tipo": "Demandante", "descricao":"Ciclano"};
		if (!$rootScope.registro) {
			$rootScope.registro = {objetoList: []};
		}
		if (!$rootScope.registro.objetoList) {
			$rootScope.registro.objetoList = [];
		}
		$rootScope.registro.objetoList.push($scope.apoio.quem.objeto);
		$('#modalQuemObjeto').modal('hide');
	};
	
	$scope.apoio.quemObjetoRemover = function(item) {
    	$rootScope.registro.objetoList.splice($rootScope.registro.objetoList.indexOf(item), 1);
	};
	
	$scope.apoio.acaoInsereNovo = function (reg) {
		var encontrou = false;
		for (pesq in $rootScope.registro.atividadeAssuntoList) {
			if ($rootScope.registro.atividadeAssuntoList[pesq].assuntoAcao.id === reg.assuntoAcao.id) {
				$rootScope.registro.atividadeAssuntoList[pesq].descricao = reg.descricao;
				encontrou = true;
				break;
			}
		}
		if (!encontrou) {
			if (!$rootScope.registro.atividadeAssuntoList) {
				$rootScope.registro.atividadeAssuntoList = [];
			}
			$rootScope.registro.atividadeAssuntoList.push(reg);
		} else {
			toaster.pop('error', null, "O assunto {0}/ {1} já está relacionado a esta atividade. Somente o campo descrição foi atualizado.".format($scope.apoio.classificacaoAcao(reg.assuntoAcao.assunto), reg.assuntoAcao.acao.nome));
		}
	};
	
	$scope.apoio.acaoSalvar = function () {
		$rootScope.emProcessamento(true);
        if (!$scope.frmAssuntoAcao.$valid) {
        	$scope.assuntoAcaoSubmitted = true;
			toaster.pop('error', "Erro ao salvar!", "Verifique os campos marcados");
			$rootScope.emProcessamento(false);
    		return;
    	}
        $scope.apoio.acaoInsereNovo($scope.apoio.atividadeAssunto);
		
		$http.get(baseUrl + PAGINA + '/getAssuntoAcaoTransversalList', {
			"params" : {"assuntoAcaoId": $scope.apoio.atividadeAssunto.assuntoAcao.id}
		}).success(function(data) {
			if (data.executou) {
				removerJsonId(data.resultado);
				angular.forEach(data.resultado, function(value, key) {
					var reg = {
						"atividade" : {
							"id" : $rootScope.registro.id
						},
						"transversal" : "S",
						"descricao" : $scope.apoio.atividadeAssunto.descricao,
						"assuntoAcao" : angular.copy(data.resultado[key])
					}
					$scope.apoio.acaoInsereNovo(reg);
				});
			} else {
				toaster.pop('error', null, data.mensagem);
			}
			$rootScope.emProcessamento(false);
		}).error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', null, data);
			$rootScope.emProcessamento(false);
		});		
		$('#modalAssuntoAcao').modal('hide');
	}
	
	if (isUndefOrNull($scope.filtro.atividadeAssuntoAcaoList)) {
		$http.get(baseUrl + PAGINA + '/getAtividadeAssuntoCompletaList').success(function(data) {
			if (data.executou) {
				removerJsonId(data.resultado);
				$scope.filtro.atividadeAssuntoAcaoList = data.resultado;
			} else {
				toaster.pop('error', null, data.mensagem);
			}
		}).error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', null, data);
		});		
	}
	
	$scope.apoio.contadorAtividadeAssuntoAcao = 0;
	
	$scope.apoio.contarAtividadeAssuntoAcaoList = function () {
		return contarAtividadeAssuntoAcaoList($scope.filtro.atividadeAssuntoAcaoList);
	}
	
	$scope.apoio.limparAtividadeAssuntoAcaoList = function () {
		angular.element(document.getElementById("admin-tree-root")).scope().collapseAll();
	}
	
	contarAtividadeAssuntoAcaoList = function (lista) {
		// TODO ATENCAO! O CODIGO ABAIXO FOI SUBSTITUIDO, REFAZER
		if (isUndefOrNull(lista)) {
			return;
		}
		for (idx in lista) {
			if (isUndefOrNull(lista[idx])) {
				continue;
			}
			if (lista[idx].marcado) {
				$scope.apoio.contadorAtividadeAssuntoAcao++;
			} else {
				if (!isUndefOrNull(lista[idx].filhos)) {
					contarAtividadeAssuntoAcaoList(lista[idx].filhos, $scope.apoio.contadorAtividadeAssuntoAcao);
				}
				if (!isUndefOrNull(lista[idx]) && !isUndefOrNull(lista[idx].acoes)) {
					for (idx1 in lista[idx].acoes) {
						if (lista[idx].acoes[idx1].marcado) {
							$scope.apoio.contadorAtividadeAssuntoAcao++;
						}
					}
				}
			}
		}
	}
		
	if (isUndefOrNull($scope.apoio.operacionalAtividadeAssuntoList)) {
		$http.get(baseUrl + PAGINA + '/getAtividadeAssuntoList', {
			"params" : {"finalidade": "O"}
		}).success(function(data) {
			if (data.executou) {
				if (isUndefOrNull(data.resultado)) {
					$scope.apoio.operacionalAtividadeAssuntoList = [];
				} else {
					removerJsonId(data.resultado);
					$scope.apoio.operacionalAtividadeAssuntoList = data.resultado;
				}
			} else {
				toaster.pop('error', null, data.mensagem);
			}
		}).error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', null, data);
		});		
	}
	
	if (isUndefOrNull($scope.apoio.administrativaAtividadeAssuntoList)) {
		$http.get(baseUrl + PAGINA + '/getAtividadeAssuntoList', {
			"params" : {"finalidade": "A"}
		}).success(function(data) {
			if (data.executou) {
				if (isUndefOrNull(data.resultado)) {
					$scope.apoio.administrativaAtividadeAssuntoList = [];
				} else {
					removerJsonId(data.resultado);
					$scope.apoio.administrativaAtividadeAssuntoList = data.resultado;
				}
			} else {
				toaster.pop('error', null, data.mensagem);
			}
		}).error(function(data) {
			console.log('ERROR', data);
			toaster.pop('error', null, data);
		});		
	}
	
    calculaDuracao = function(inicio, termino) {
    	if (isUndefOrNull(inicio) || isUndefOrNull(termino)) {
    		return 0;
    	}
    	if ($rootScope.registro.diaTodo === 'S') {
    		inicio.setHours(0);
    		inicio.setMinutes(0);
    		inicio.setSeconds(0);
    		inicio.setMilliseconds(0);
    		
    		termino.setHours(23);
    		termino.setMinutes(59);
    		termino.setSeconds(59);
    		termino.setMilliseconds(1000);
    	}
    	return tempo = termino - inicio;
    }
	
    calculaDuracaoFormatada = function(tempo) {
    	if (isUndefOrNull(tempo)) {
    		return "";
    	}
    	tempo /= 1000;
    	var segundos = Math.round(tempo % 60);
    	tempo = Math.floor(tempo / 60);
    	var minutos = Math.round(tempo % 60);
    	tempo = Math.floor(tempo / 60);
    	var horas = Math.round(tempo % 24);
    	tempo = Math.floor(tempo / 24);
    	var dias = tempo;

    	if ($rootScope.registro.diaTodo === 'S') {
        	return dias + " dia(s)";
    	} else {
        	return dias + " dia(s), " + horas + " hora(s) " + minutos + " minuto(s)";
    	}
    }    
	
    if (isUndefOrNull($scope.apoio.ocorrencia)) {
    	$scope.apoio.ocorrencia = {percentualConclusao : 1, situacao: 'E'};
    }
    
	$scope.$watch("registro.inicio + registro.previsaoConclusao + registro.conclusao + registro.diaTodo",
		function(newValue, oldValue) {
			if (isUndefOrNull($rootScope.registro)) {
				return;
			}
			if (!isUndefOrNull($rootScope.registro.inicio)
					&& !isUndefOrNull($rootScope.registro.previsaoConclusao)
					&& $rootScope.registro.inicio <= $rootScope.registro.previsaoConclusao) {
				$rootScope.registro.duracaoPrevista = calculaDuracao(
						angular.copy($rootScope.registro.inicio),
						angular
								.copy($rootScope.registro.previsaoConclusao));
				$rootScope.registro.duracaoPrevistaFormatada = calculaDuracaoFormatada($rootScope.registro.duracaoPrevista);
			} else {
				$rootScope.registro.duracaoPrevista = null;
				$rootScope.registro.duracaoPrevistaFormatada = null;
			}
			if (!isUndefOrNull($rootScope.registro.inicio)
					&& !isUndefOrNull($rootScope.registro.conclusao)
					&& $rootScope.registro.inicio <= $rootScope.registro.conclusao) {
				$rootScope.registro.duracaoReal = calculaDuracao(
						angular.copy($rootScope.registro.inicio),
						angular.copy($rootScope.registro.conclusao));
				$rootScope.registro.duracaoRealFormatada = calculaDuracaoFormatada($rootScope.registro.duracaoReal);
			} else {
				$rootScope.registro.duracaoReal = null;
				$rootScope.registro.duracaoRealFormatada = null;
			}
			$scope.atualizaAgendamentoTipoList();
		}
	);
	
    $scope.$watch("registro.finalidade",
		function(newValue, oldValue) {
    		$scope.apoio.atividadeAssuntoList = null;
			if (isUndefOrNull(newValue) || isUndefOrNull($scope.apoio.operacionalAtividadeAssuntoList) || isUndefOrNull($scope.apoio.administrativaAtividadeAssuntoList)) {
				return;
			}
			if (newValue === "O") {
				$scope.apoio.atividadeAssuntoList = angular.copy($scope.apoio.operacionalAtividadeAssuntoList);
			} else if (newValue === "A") {
	    		$scope.apoio.atividadeAssuntoList = angular.copy($scope.apoio.administrativaAtividadeAssuntoList);
			}
		}
    );
    
    $scope.$watch('registro.percentualConclusao', function(newValue, oldValue) {
		if (isUndefOrNull($rootScope.registro) || isUndefOrNull($rootScope.registro.situacao)) {
			return;
		}
		if (isUndefOrNull(newValue) || newValue === 0) {
			$rootScope.registro.situacao = 'N';
		} else if (newValue === 10) {
				$rootScope.registro.situacao = 'C';
		} else if ($rootScope.registro.situacao === 'C' || $rootScope.registro.situacao === 'N') {
			$rootScope.registro.situacao = 'E';
		}
	});
        
    $scope.$watch('registro.situacao', function(newValue, oldValue) {
		if (isUndefOrNull($rootScope.registro) || isUndefOrNull($rootScope.registro.percentualConclusao)) {
			return;
		}

		if (isUndefOrNull(newValue) || newValue === "N") {
			$rootScope.registro.percentualConclusao = 0;
		} else if (newValue === "C") {
			$rootScope.registro.percentualConclusao = 10;
		} else if (newValue === "E") {
			if ($rootScope.registro.percentualConclusao === 10) {
				$rootScope.registro.percentualConclusao = 9;
			} else if ($rootScope.registro.percentualConclusao === 0) {
				$rootScope.registro.percentualConclusao = 1;
			}
		}
	});
    
	$scope.$watch('apoio.ocorrencia.percentualConclusao', function(newValue, oldValue) {
		if (isUndefOrNull($scope.apoio) || isUndefOrNull($scope.apoio.ocorrencia) || isUndefOrNull($scope.apoio.ocorrencia.situacao)) {
			return;
		}
		if (isUndefOrNull(newValue) || newValue === 0) {
			$scope.apoio.ocorrencia.situacao = 'N';
		} else if (newValue === 10) {
			$scope.apoio.ocorrencia.situacao = 'C';
		} else if ($scope.apoio.ocorrencia.situacao === 'C' || $scope.apoio.ocorrencia.situacao === 'N') {
			$scope.apoio.ocorrencia.situacao = 'E';
		}
	});
	
	$scope.$watch('apoio.ocorrencia.situacao', function(newValue, oldValue) {
		if (isUndefOrNull($scope.apoio) || isUndefOrNull($scope.apoio.ocorrencia) || isUndefOrNull($scope.apoio.ocorrencia.percentualConclusao)) {
			return;
		}
		if (isUndefOrNull(newValue) || newValue === "N") {
			$scope.apoio.ocorrencia.percentualConclusao = 0;
		} else if (newValue === "C") {
			$scope.apoio.ocorrencia.percentualConclusao = 10;
		} else if (newValue === "E") {
			if ($scope.apoio.ocorrencia.percentualConclusao === 10) {
				$scope.apoio.ocorrencia.percentualConclusao = 9;
			} else if ($scope.apoio.ocorrencia.percentualConclusao === 0) {
				$scope.apoio.ocorrencia.percentualConclusao = 1;
			}
		}
	});
	
	$scope.$watch('controle.abas.onde', function(newValue, oldValue) {
		if (newValue) {
			initialize();
		}
	});
	
	$scope.apoio.ocorrenciaUltima = function() {
		if (isUndefOrNull($rootScope.registro)) {
			return;
		}
		if (!isUndefOrNull($rootScope.registro.ocorrenciaList) && $rootScope.registro.ocorrenciaList.length > 0) {
			var situacao = angular.copy($rootScope.registro.ocorrenciaList[$rootScope.registro.ocorrenciaList.length - 1].situacao);
			var percentualConclusao = angular.copy($rootScope.registro.ocorrenciaList[$rootScope.registro.ocorrenciaList.length - 1].percentualConclusao);
			var prioridade = angular.copy($rootScope.registro.ocorrenciaList[$rootScope.registro.ocorrenciaList.length - 1].prioridade);
		} else {
			var situacao = angular.copy($rootScope.registro.situacao);
			var percentualConclusao = angular.copy($rootScope.registro.percentualConclusao);
			var prioridade = angular.copy($rootScope.registro.prioridade);
		}
		return {"situacao": situacao, "percentualConclusao" : percentualConclusao, "prioridade" : prioridade};
	};
	
	$scope.apoio.ocorrenciaPodeIncluir = function () {
		var ocorrenciaUltima = $scope.apoio.ocorrenciaUltima();
		if (isUndefOrNull(ocorrenciaUltima)) {
			return false;
		}
		return !(ocorrenciaUltima.situacao === "N" || ocorrenciaUltima.situacao === "E" || ocorrenciaUltima.situacao === "S"); 
	};
	
    $scope.apoio.ocorrenciaIncluir = function () {
    	$scope.ocorenciaSubmitted = false;
    	$scope.frmOcorrencias.$setPristine();
    	delete $scope.apoio.ocorrenciaOriginal;    	
    	var atividadeId = angular.copy($rootScope.registro.id);

    	if (isUndefOrNull($rootScope.registro.ocorrenciaList)) {
			$rootScope.registro.ocorrenciaList = [];
		}
    	var ocorrenciaUltima = $scope.apoio.ocorrenciaUltima();
    	var p = $http.get(baseUrl + PAGINA + "/ocorrenciaIncluir", {
			params : {
				"atividadeId" : atividadeId,
				"situacao" : ocorrenciaUltima.situacao,
				"percentualConclusao" : ocorrenciaUltima.percentualConclusao,
				"prioridade" : ocorrenciaUltima.prioridade
			}
		});
    	p.then(function (payload) {
            $scope.ocorenciaSubmitted = false;
            $scope.frmOcorrencias.$setPristine();
            removerJsonId(payload.data.resultado);
    		$scope.apoio.ocorrencia = payload.data.resultado;
        	if (!isUndefOrNull($scope.apoio.ocorrencia.registro) && $scope.apoio.ocorrencia.registro.trim().length > 0) {
        		$scope.apoio.ocorrencia.registro = new Date($scope.apoio.ocorrencia.registro);
        	}
        	if (!isUndefOrNull($scope.apoio.ocorrencia.inicioSuspensao) && $scope.apoio.ocorrencia.inicioSuspensao.trim().length > 0) {
        		$scope.apoio.ocorrencia.inicioSuspensao = new Date($scope.apoio.ocorrencia.inicioSuspensao);
        	}
        	if (!isUndefOrNull($scope.apoio.ocorrencia.terminoSuspensao) && $scope.apoio.ocorrencia.terminoSuspensao.trim().length > 0) {
        		$scope.apoio.ocorrencia.terminoSuspensao = new Date($scope.apoio.ocorrencia.terminoSuspensao);
        	}
    	});
    };
    
    $scope.apoio.ocorrenciaEditar = function (item) {
    	$scope.ocorenciaSubmitted = false;
    	$scope.frmOcorrencias.$setPristine();
        $scope.apoio.ocorrenciaOriginal = item;
    	$scope.apoio.ocorrencia = angular.copy($scope.apoio.ocorrenciaOriginal);
    };
    
    $scope.apoio.ocorrenciaRemover = function (item) {
    	$rootScope.registro.ocorrenciaList.splice($rootScope.registro.ocorrenciaList.indexOf(item), 1);
    };
    
    $scope.apoio.ocorrenciaLimpar = function () {
    	$scope.ocorenciaSubmitted = false;
    	$scope.frmOcorrencias.$setPristine();
        $scope.apoio.ocorrenciaIncluir();
    };
    
	$scope.apoio.ocorrenciaSalvar = function () {
		
		$rootScope.emProcessamento(true);
        if (!$scope.frmOcorrencias.$valid) {
        	$scope.ocorenciaSubmitted = true;
			toaster.pop('error', "Erro ao salvar!", "Verifique os campos marcados");
			$rootScope.emProcessamento(false);
    		return;
    	}

        var ocorrencia = angular.copy($scope.apoio.ocorrencia);
		if (!isUndefOrNull($scope.apoio.ocorrenciaOriginal)) {
			// edicao
			$rootScope.registro.ocorrenciaList.splice($rootScope.registro.ocorrenciaList.indexOf($scope.apoio.ocorrenciaOriginal), 1, ocorrencia);
			delete $scope.apoio.ocorrenciaOriginal;
		} else {			
			// inclusao
			$rootScope.registro.ocorrenciaList.push(ocorrencia);
		}
		$rootScope.emProcessamento(false);
		$('#modalOcorrencias').modal('hide');
	}
        
    $scope.apoio.incluirAcao = function () {
    	$scope.assuntoAcaoSubmitted = false;
    	$scope.frmAssuntoAcao.$setPristine();
		$scope.apoio.atividadeAssunto = {
			"atividade" : {
				"id" : $rootScope.registro.id
			},
			"assuntoAcao" : null,
			"descricao" : "",
			"transversal" : "N"
		};
//		for (reg in $scope.apoio.atividadeAssuntoList) {
//			$scope.apoio.atividadeAssuntoList[reg].collapsed = true;
//		}
//		angular.element('#treeAtividadeAssunto').scope().collapseAll();
    }
    
    $scope.apoio.classificacaoAcao = function (assunto) {
    	if (isUndefOrNull(assunto) || isUndefOrNull(assunto.nome)) {
    		return;
    	}
    	var result = assunto.nome; 
    	if (!isUndefOrNull(assunto.pai)) {
    		var pai = $scope.apoio.classificacaoAcao(assunto.pai);
    		if (!isUndefOrNull(pai)) {
    			result = pai + '/ ' + result; 
    		}
    	}
    	return result;
    }
    
    $scope.filtrarDemandante = function() {
    	return function(reg) {
        	if (isUndefOrNull(reg)) {
        		return true;
        	}
        	return reg.funcao === 'D';
    	}
    };
    
    $scope.filtrarExecutor = function() {
    	return function(reg) {
        	if (isUndefOrNull(reg)) {
        		return true;
        	}
        	return reg.funcao === 'E' || reg.funcao === 'ER';
    	}
    };
    
    $scope.apoio.pessoaRemover = function (item) {
    	$rootScope.registro.atividadePessoaList.splice($rootScope.registro.atividadePessoaList.indexOf(item), 1);
    };
    
    $scope.apoio.filtroPessoaRemover = function (item) {
    	$rootScope.filtro.pessoaList.splice($rootScope.filtro.pessoaList.indexOf(item), 1);
    	if (!$rootScope.filtro.pessoaList.length) {
    		$scope.apoio.filtroPessoaListInit();
    	}
    };
    
    $scope.apoio.filtroGrupoSocialRemover = function (item) {
    	$rootScope.filtro.grupoSocialList.splice($rootScope.filtro.grupoSocialList.indexOf(item), 1);
    	if (!$rootScope.filtro.grupoSocialList.length) {
    		$scope.apoio.filtrogrupoSocialListInit();
    	}
    };
    
    $scope.apoio.removerAcao = function (item) {
    	$rootScope.registro.atividadeAssuntoList.splice($rootScope.registro.atividadeAssuntoList.indexOf(item), 1);
    };
    
    $scope.apoio.limparArquivo = function() {
    	$scope.registro.atividadeArquivoList = []; 
    }
    
    $scope.apoio.removerArquivo = function (item) {
    	$rootScope.registro.atividadeArquivoList.splice($rootScope.registro.atividadeArquivoList.indexOf(item), 1);
    };
    
    // apoio ao como
    if (isUndefOrNull($scope.apoio.metodoList)) {
    	requisicaoService.dominio({
    		params : {
    			ent : "Metodo",
    			ord : "nome"
    		}
    	}).success(function(data) {
    		if (data.executou) {
    			$scope.apoio.metodoList = angular.copy(data.resultado);
    		}
    	});
    }
    
    $scope.apoio.getNomeMetodo = function(id) {
    	if (isUndefOrNull(id)) {
    		return "";
    	}
    	for (reg in $scope.apoio.metodoList) {
    		if ($scope.apoio.metodoList[reg].id === id) {
    			return $scope.apoio.metodoList[reg].nome;
    		}
    	}
    	return "Id {0} de método não localizado".format(id);
    };
    
    $scope.apoio.getIdMetodo = function(nome) {
    	if (isUndefOrNull(nome)) {
    		return "";
    	}
    	for (reg in $scope.apoio.metodoList) {
    		if ($scope.apoio.metodoList[reg].nome === nome) {
    			return $scope.apoio.metodoList[reg].id;
    		}
    	}
    	return "Nome {0} de método não localizado".format(nome);
    };
    
    if (isUndefOrNull($scope.apoio.somenteUmaAba)) {
    	$scope.apoio.somenteUmaAba = true;
    }
    
    $scope.apoio.expandirTudo = function (expandir) {
    	$scope.controle.abas.arquivos = expandir;
    	$scope.controle.abas.como = expandir;
    	$scope.controle.abas.complemento = expandir;
    	$scope.controle.abas.oQue = expandir;
    	$scope.controle.abas.onde = expandir;
    	$scope.controle.abas.porQue = expandir;
    	$scope.controle.abas.quando = expandir;
    	$scope.controle.abas.quantoCusta = expandir;
    	$scope.controle.abas.quem = expandir;
    }
    
    if (isUndefOrNull($scope.apoio.agendamentoTipo)) {
    	$scope.apoio.agendamentoTipo = "_0";
    }
    
    if (isUndefOrNull($scope.apoio.agendamentoTipoRepeticao)) {
    	$scope.apoio.agendamentoTipoRepeticao = "1";
    }
    
    // captar as enumeracoes de apoio ao agendamento
    if (isUndefOrNull($scope.apoio.agendamentoDiaSemanaList)
		|| isUndefOrNull($scope.apoio.agendamentoMesList)
		|| isUndefOrNull($scope.apoio.agendamentoTipoList)) {

    	$q.all([requisicaoService.enumeracao("DiaSemana"), requisicaoService.enumeracao("Mes"), requisicaoService.enumeracao("AgendamentoTipo")]).then(function(result) {
        	$scope.apoio.agendamentoDiaSemanaList  = result[0].data.resultado;
        	$scope.apoio.agendamentoMesList        = result[1].data.resultado;
    		$scope.apoio.agendamentoTipoListMatriz = result[2].data.resultado;
    	});
	}
    
    if (isUndefOrNull($scope.apoio.atividadeSituacaoList)) {
    	requisicaoService.enumeracao("AtividadeSituacao").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.atividadeSituacaoList = angular.copy(data.resultado);
    			$scope.apoio.atividadeSituacaoListInicial = [];
    			$scope.apoio.atividadeSituacaoListOcorrencia = [];
	    		angular.forEach(data.resultado, function(item, key) {
	    			if (item.inicial) {
	    				$scope.apoio.atividadeSituacaoListInicial.push(item);
	    			}
	    			if (item.ocorrencia) {
	    				$scope.apoio.atividadeSituacaoListOcorrencia.push(item);
	    			}
	    		});

    		} else {
    			if (!isUndefOrNull(data[mensagem])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.open("text/html", document.write(data));
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data[mensagem])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.open("text/html", document.write(data));
			}
    	});
    }
    
    $scope.apoio.getNomeAtividadeSituacao = function(codigo) {
    	if (isUndefOrNull(codigo)) {
    		return "";
    	}
    	for (reg in $scope.apoio.atividadeSituacaoList) {
    		if ($scope.apoio.atividadeSituacaoList[reg].codigo === codigo) {
    			return $scope.apoio.atividadeSituacaoList[reg].descricao;
    		}
    	}
    	return "Código {0} de situação não localizado".format(codigo);
    };
    
    if (isUndefOrNull($scope.apoio.atividadePrioridadeList)) {
    	requisicaoService.enumeracao("AtividadePrioridade").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.atividadePrioridadeList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data[mensagem])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.open("text/html", document.write(data));
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data[mensagem])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.open("text/html", document.write(data));
			}
    	});
    }
    
    $scope.apoio.getNomeAtividadePrioridade = function(codigo) {
    	if (isUndefOrNull(codigo)) {
    		return "";
    	}
    	for (reg in $scope.apoio.atividadePrioridadeList) {
    		if ($scope.apoio.atividadePrioridadeList[reg].codigo === codigo) {
    			return $scope.apoio.atividadePrioridadeList[reg].descricao;
    		}
    	}
    	return "Código {0} de situação não localizado".format(codigo);
    };

    if (isUndefOrNull($scope.apoio.confirmacaoList)) {
    	requisicaoService.enumeracao("Confirmacao").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.confirmacaoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data[mensagem])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.open("text/html", document.write(data));
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data[mensagem])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.open("text/html", document.write(data));
			}
    	});
    }
    
    $scope.apoio.getNomeConfirmacao = function(codigo) {
    	if (isUndefOrNull(codigo)) {
    		return "";
    	}
    	for (reg in $scope.apoio.confirmacaoList) {
    		if ($scope.apoio.confirmacaoList[reg].codigo === codigo) {
    			return $scope.apoio.confirmacaoList[reg].descricao;
    		}
    	}
    	return "Código {0} de confirmacao não localizado".format(codigo);
    };
    
    $scope.atualizaAgendamentoTipoList = function() {
    	if (isUndefOrNull($rootScope.registro) || isUndefOrNull($rootScope.registro.inicio)) {
        	$scope.apoio.agendamentoTipoList = [];
        	return;
    	}
    	var diaMes    = $rootScope.registro.inicio.getDate();
    	var diaSemana = $scope.apoio.agendamentoDiaSemanaList[$rootScope.registro.inicio.getDay()];
    	var mes       = $scope.apoio.agendamentoMesList[$rootScope.registro.inicio.getMonth()];
    	$scope.apoio.agendamentoTipoList = angular.copy($scope.apoio.agendamentoTipoListMatriz);
    	// ajustar as descricoes
    	$scope.apoio.agendamentoTipoList[3].descricao = $scope.apoio.agendamentoTipoList[3].descricao.format(diaSemana.descricao);        		
    	$scope.apoio.agendamentoTipoList[4].descricao = $scope.apoio.agendamentoTipoList[4].descricao.format(diaSemana.descricao);
    	$scope.apoio.agendamentoTipoList[5].descricao = $scope.apoio.agendamentoTipoList[5].descricao.format(diaSemana.descricao);
    	$scope.apoio.agendamentoTipoList[6].descricao = $scope.apoio.agendamentoTipoList[6].descricao.format(diaMes);
    	$scope.apoio.agendamentoTipoList[7].descricao = $scope.apoio.agendamentoTipoList[7].descricao.format(diaMes, mes.descricao);
    };
    
    $scope.onFileSelect = function($files) {
		// $files: an array of files selected, each file has name, size, and type.
		for (var i = 0; i < $files.length; i++) {
			var $file = $files[i];
			if ($file.size > $rootScope.tamanhoMaxArquivo) {
				toaster.pop('error','Tamanho de Arquivo Inválido', "O arquivo {0} excedeu ao limite máximo de {1}".format($file.name, $scope.apoio.arquivoTamanhoTotalFormatado($rootScope.tamanhoMaxArquivo)));
			} else {
				var atividadeArquivo = {"arquivo" : {"nome":$file.name, "tipo":$file.type, "tamanho":$file.size}, "dataUpload": new Date(), "descricao": "", "file": $file};
			    if (isUndefOrNull($rootScope.registro.atividadeArquivoList)) {
			    	$rootScope.registro.atividadeArquivoList = [];
			    	$scope.progress = [];
			    }
				$rootScope.registro.atividadeArquivoList.push(atividadeArquivo);
			}
		}
	};
	
	$scope.apoio.arquivoTamanhoTotal = function () {
		var result = 0;
		if (isUndefOrNull($rootScope.registro) || isUndefOrNull($rootScope.registro.atividadeArquivoList)) {
			return result;
		}
		angular.forEach($rootScope.registro.atividadeArquivoList, function(value, key) {
			result += value.arquivo.tamanho;
		});
		return result;
	}
	
	$scope.apoio.arquivoTamanhoTotalFormatado = function (numero) {
		return humanFileSize(numero); 
	}

	// Operações quando for cadastro de subatividades
	if ($scope.cadastroSubatividade) {
		var id = location.search.split('id=')[1];
		if (isUndefOrNull(id)) {
			$scope.incluir();
		} else {
			if (id.indexOf("&") > 0) {
				id = id.split('&')[0];
			}
			$scope.editar(id);
		}
	}

	// botao que faz o retorno
    $rootScope.popupSalvarSubatividade = function() {
    	$scope.salvar();
    }
    
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.incluirSubatividade = function(id) {
		window.$rootScope = $rootScope;
		if (isUndefOrNull(id)) {
			window.open(baseUrl + 'atividade-cad/subatividade/?retorno=incluirSubatividadeRetorno', 599, "width=1200px,height=600px,top=100px,left=300px,scrollbars=1");
		} else {
			window.open(baseUrl + 'atividade-cad/subatividade/?id=' + id + '&retorno=incluirSubatividadeRetorno', 699, "width=1200px,height=600px,top=100px,left=300px,scrollbars=1");
		}
	}
	
	// e outra para tratar o retorno
	$rootScope.incluirSubatividadeRetorno = function (valor) {
		var atividadeRelacionamento = {"atividade" : valor};
		if (isUndefOrNull($rootScope.registro.subatividadeList)) {
			$rootScope.registro.subatividadeList = [];
		} else {
			for (idx in $rootScope.registro.subatividadeList) {
				var atividade = $rootScope.registro.subatividadeList[idx].atividade; 
				if (!isUndefOrNull(atividade) && !isUndefOrNull(atividade.id)) {
					if (atividade.id === valor.id) {
						$rootScope.registro.subatividadeList[idx].atividade = valor;
						return;
					}
				}
			}
		}
		$rootScope.registro.subatividadeList.push(atividadeRelacionamento);
	}

    $scope.apoio.removerSubatividade = function (item) {
    	$rootScope.registro.subatividadeList.splice($rootScope.registro.subatividadeList.indexOf(item), 1);
    };
    
    $scope.toggleChildren = function (scope) {
    	if (scope.$nodeScope.collapsed) {
    		if (isUndefOrNull(scope.$nodeScope.$modelValue.filhos)) {
    			$rootScope.emProcessamento(true);
    			var filhos = $http.get(baseUrl + PAGINA + '/getAssuntoAcaoFilhos', {"params" : { "assuntoId" : scope.$nodeScope.$modelValue.id}});
				var acoes = $http.get(baseUrl + PAGINA + '/getAtividadeAssuntoAcaoList', {"params" : { "assuntoId" : scope.$nodeScope.$modelValue.id}});
    	    	$q.all([filhos, acoes]).then(function(result) {
					try {
						if (result[0].data.executou) {
							if (isUndefOrNull(result[0].data.resultado) || result[0].data.resultado.length == 0) {
								scope.$nodeScope.$modelValue.filhos = [];
							} else {
								removerJsonId(result[0].data.resultado);
								scope.$nodeScope.$modelValue.filhos = result[0].data.resultado;
							}
							removerJsonId(result[1].data.resultado);
							scope.$nodeScope.$modelValue.acoes = result[1].data.resultado;
							var marcado = scope.$nodeScope.$modelValue.marcado;
							if (isUndefOrNull(marcado)) {
								marcado = false;
							}
							marcarAssuntoAcao(scope.$nodeScope.$modelValue, marcado);
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
    	} else {
            scope.toggle();
        }
    };
    
	if (isUndefOrNull($rootScope.filtro.pessoaList)) {
		$scope.apoio.filtroPessoaListInit();
	}
		
}

// FUNCOES PARA MANIPULACAO DO MAPA GOOGLE
var map, mapFiltro, geocoder, marker;
var local = [0, 0];

$(document).on("change", "#inputLatitude, #inputLongitude", function() {
    local = [$("#inputLatitude").val(), $("#inputLongitude").val()];
    //console.log("AQUI "+ {{registro}})
    if (local[0] !== "") {
        initialize();
    }
});

function initialize() {
	try {
	    if ($("#inputLatitude").val() === "" || $("#inputLongitude").val() === "") {
	    	local = ['-15.732805', '-47.903791'];
	    	$("#inputLatitude").val(local[0]);
	    	$("#inputLongitude").val(local[1]);
	    } else {
	        local = [$("#inputLatitude").val(), $("#inputLongitude").val()];
	    }
	
	    var posicao = new google.maps.LatLng(local[0], local[1]);
	    var mapOptions = {
	        zoom: 10,
	        center: posicao,
	        panControl: true,
	        zoomControl: true,
	        scaleControl: true,
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    
	    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	    map.setCenter(posicao);
	
	    geocoder = new google.maps.Geocoder();
	    
	    marker = new google.maps.Marker({
	        position: posicao,
	        title: "Local de realização da atividade!",
	        draggable: true
	    });
	
	    marker.setMap(map);
	
	    google.maps.event.addDomListener(marker, "dragend", function() {
	        var point = marker.getPosition();
	        map.panTo(point);
	        // console.log(point)
	        $("#inputLatitude").val(point.k);
	        $("#inputLongitude").val(point.C);
	    });
	    
	    google.maps.event.addListener(marker, 'drag', function () {
	        geocoder.geocode({ 'latLng': marker.getPosition() }, function (results, status) {
	            if (status == google.maps.GeocoderStatus.OK) {
	                    if (results[0]) { 
	                    $('#txtEndereco').val(results[0].formatted_address);
	                    $('#inputLatitude').val(marker.getPosition().lat());
	                    $('#inputLongitude').val(marker.getPosition().lng());
	                }
	            }
	        });
	    });
	    
	    $("#txtEndereco").autocomplete({
	        source: function (request, response) {
	            geocoder.geocode({ 'address': request.term + ', Brasil', 'region': 'BR' }, function (results, status) {
	                response($.map(results, function (item) {
	                    return {
	                        label: item.formatted_address,
	                        value: item.formatted_address,
	                        latitude: item.geometry.location.lat(),
	                        longitude: item.geometry.location.lng()
	                    }
	                }));
	            })
	        },
	        select: function (event, ui) {
	            $("#inputLatitude").val(ui.item.latitude);
	            $("#inputLongitude").val(ui.item.longitude);
	            var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
	            marker.setPosition(location);
	            map.setCenter(location);
	            map.setZoom(16);
	        }
	    });
	    
	} catch (e) {
		console.error('ERRO ao iniciar o google maps', e);
	}
}

function initializeFiltro() {
	try {
	    var mapOptions = {
	        zoom: 10,
	        center: new google.maps.LatLng('-15.732805', '-47.903791'),
	        panControl: true,
	        zoomControl: true,
	        scaleControl: true,
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	
	    mapFiltro = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	
	    creator = new PolygonCreator(mapFiltro);
	    
	} catch (e) {
		alert('ERRO ao iniciar o google maps ' + e);
		console.error('ERRO ao iniciar o google maps', e);
	}
}

function carregarNoMapa(endereco) {
    geocoder.geocode({ 'address': endereco + ', Brasil', 'region': 'BR' }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[0]) {
                var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();

                $('#txtEndereco').val(results[0].formatted_address);
                $('#inputLatitude').val(latitude);
                $('#inputLongitude').val(longitude);

                var location = new google.maps.LatLng(latitude, longitude);
                marker.setPosition(location);
                map.setCenter(location);
                map.setZoom(16);
            }
        }
    });
}

function exibirNoMapa() {
    if($(this).val() != "") {
    	carregarNoMapa($("#txtEndereco").val());
    }
}

function cleanFiltro() {
	event.preventDefault();
	creator.destroy();
    creator = new PolygonCreator(mapFiltro);
}

$(document).ready(function() {
    setTimeout(function(){
        initializeFiltro();
    }, 1500);
});