/**
 * Script do m�dulo
 */

// Ativa��o do Angular JS
(function() {
	aterweb.controller('cadastroCtrl', function($scope, $rootScope, $window, $http, $location, toaster) {
			
            $scope.querystring = {};
            $.each(document.location.search.substr(1).split('&'), function(c, q) {
                var i = q.split('=');
                if (!isUndefOrNull(i) && i.length == 2) {
                    $scope.querystring[i[0].toString()] = i[1].toString();
                }
            });
            
            if( $scope.querystring["respostaVersaoId"] ) {
            	setTimeout(function(){
            		$("input[type = 'radio']").attr('disabled','disabled');
            		$("textarea").attr('readonly','readonly');
            		$scope.imprimir();
            	}, 500);
				
			}
            $scope.tipoEnquete = location.pathname; 
            $scope.anonimo = $scope.tipoEnquete.indexOf("identificada") === -1;

            $scope.modificarTamanho = function(valor, ordem) {
            	console.log(valor)
            	var retorno = "campos-menores";
                if(valor == "I") {
            		retorno += " hidden-print"
                }
                else if(valor == "Q") {
                	retorno = "fimPagina campos-menores";
                }
                //console.log(retorno)
                return retorno;
            }
            
            $scope.esconderResposta = function(a, b) {
            	
            	if($scope.querystring["respostaVersaoId"]) {
	            	if(a != b) {
	            		console.log("Diferente")
	            		return 'hidden';
	            	} else {
	            		console.log("Igual")
	            		return '';
	            	}
            	}
            }
            
            var formularioUrl = "enquete-formulario?anonimo=" + $scope.anonimo;
            if (!isUndefOrNull($scope.querystring["formularioId"]) || !isUndefOrNull($scope.querystring["respostaVersaoId"])) {
                if (!isUndefOrNull($scope.querystring["formularioId"])) {
                    formularioUrl += "&formularioId=" + $scope.querystring["formularioId"];
                } else {
                    formularioUrl += "&respostaVersaoId=" + $scope.querystring["respostaVersaoId"];
                }
                $rootScope.emProcessamento(true);
                $rootScope.lista = [];
                $http.get(baseUrl + formularioUrl)
                    .success(function (data) {
                        $rootScope.emProcessamento(false);
                        if (data.executou) {
                            $rootScope.registro = data.resultado;
                        } else {
                            $rootScope.registro = {};
                            toaster.pop('error', data.resultado.message);
                        }
                    })
                    .error(function (data) {
                        $rootScope.emProcessamento(false);
                        toaster.pop('error', "Erro no servidor", data, 0);
                });
            } else {
                $rootScope.emProcessamento(true);
                $rootScope.registro = {};
                $http.get(baseUrl + formularioUrl)
                    .success(function (data) {
                        $rootScope.emProcessamento(false);
                        if (data.executou) {
                            $rootScope.lista = data.resultado;
                        } else {
                            $rootScope.lista = [];
                            toaster.pop('error', data.mensagens["*"]);
                        }
                    })
                    .error(function (data) {
                        $rootScope.emProcessamento(false);
                        toaster.pop('error', "Erro no servidor", data, 0);
                });
            }

            $rootScope.refExclusao = null;

            $scope.responder = function(formularioId) {
                alert(formularioId);
            };

            $scope.retornar = function(respostaVersaoId) {
                window.location = $scope.tipoEnquete;
            };

            $scope.limpar = function(respostaVersaoId) {
                perguntas = $rootScope.registro.formulario.perguntaList;
                for (pergunta in perguntas) {
                    perguntas[pergunta].resposta.valor = "";
                }
            };

            $scope.imprimir = function() {
            	if($scope.registro.permiteImpressao == "S") {
            		window.print();
            	} else {
            		alert("ATENÇÃO! Este formulário não deve ser impresso!");
            	}
                
            };

            $scope.exibeFormulario = function() {
                return $scope.querystring['formularioId'] != null || $scope.querystring['respostaVersaoId'] != null;
            };

            $scope.refExcluir = function(id) {
                $rootScope.refExclusao = id;
                console.log($rootScope.refExclusao);
            };

            $scope.remover = function() {
                respostaVersaoId = $rootScope.refExclusao;
                $http.get(baseUrl + "enquete-remover?respostaVersaoId=" + respostaVersaoId)
                    .success(
                        function(data) {
                            $rootScope.emProcessamento(false);
                            toaster.pop('success', "Excluido", "Registro excluido com sucesso!");
                            $scope.retornar();
                    })
                    .error(function(data) {
                        $rootScope.emProcessamento(false);
                        console.log("EXCLUIR => Ocorreu algum erro!");
                        toaster.pop('error', "Erro no servidor", data, 0);
                        console.log(data);
                    });
            };

            $scope.salvar = function() {
            $scope.emProcessamento(true);
            var novo = angular.copy($rootScope.registro);
            console.log($rootScope.registro);
            $http.post(baseUrl + "enquete-salvar", $rootScope.registro).success(function(data) {
                $rootScope.emProcessamento(false);
                console.log(data);
                if(data.executou) {
                    toaster.pop('success', "Salvo", "Registro salvo com sucesso!");
                    qtd = data.resultado.respostaVersaoList.length;
                    idResposta = data.resultado.respostaVersaoList[qtd -1].id;
                    window.location.href = "./enquete-identificada?respostaVersaoId=" + idResposta;
                   
                } else {
                    toaster.pop('error', null, "Erro ao salvar! " + data.resultado.message);
                    console.log(data);
                }
            }).error(function(data){
                $rootScope.emProcessamento(false);
                toaster.pop('error', null, "SALVAR => Ocorreu algum erro!");
                console.log(data);
            });
            };
	
		$scope.avaliacoes = function () {
            $rootScope.emProcessamento(true);
            $.ajax({
                type: "GET",
                url: baseUrl + "enquete-avaliacao",
                async: false,
                success: function (data) {
                	 $rootScope.emProcessamento(false);
                     if (data.executou) {
                         return data.resultado;
                     } else {
                         toaster.pop('error', "erro");
                         return "";
                     }
                }
            });
		}
	});
})();