var PAGINA = 'agenda-cad';

function cadastroCtrl($rootScope, $scope, $http, $location, toaster, requisicaoService) {
    $rootScope.events = [];

    $scope.uiConfig = {
        calendar: {
            lang: 'pt-br',
            editable: true,
            monthNames: ['Janeiro', 'Fevereiro', 'Mar�o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
            monthNamesShort: ['Janeiro', 'Fevereiro', 'Mar�o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
            dayNames: ['Domingo', 'Segunda', 'Ter�a', 'Quarta', 'Quinta', 'Sexta', 'S�bado'],
            dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S�b'],
            buttonText: {
                prev: "<span class='fc-text-arrow'>&lsaquo;</span>",
                next: "<span class='fc-text-arrow'>&rsaquo;</span>",
                prevYear: "<span class='fc-text-arrow'>&laquo;</span>",
                nextYear: "<span class='fc-text-arrow'>&raquo;</span>",
                today: 'Hoje',
                month: 'M�s',
                week: 'Semana',
                day: 'Dia'
            },
            header: {
                left: 'month basicWeek basicDay',
                center: 'title',
                right: 'today prev,next'
            },
            titleFormat: {
                month: 'MMMM yyyy',
                week: "d 'de' MMM [ yyyy]{ '&#8212;' d 'de' [ MMM], yyyy}",
                day: "dddd, d 'de' MMM, yyyy"
            },
            columnFormat: {
                month: 'ddd',
                week: 'ddd d/M',
                day: 'dddd d/M'
            },
            dayClick: $scope.dataSelecionada,
            eventClick: $scope.eventoSelecionado,
            eventDrop: $scope.eventoSolto,
            eventResize: $scope.eventoRedimensionado
        }
    };

    $rootScope.evento = {};

    $scope.eventoRedimensionado = function () {
        console.log('teste');
    }

    $scope.eventoSolto = function (event) {
        var caminho = baseUrl + PAGINA
        var objSalvar = {
            id: event.id,
            nome: event.title,
            descricao: event.description,
            inicio: moment(event.start).format('DD/MM/YYYY HH:mm:00.000'),
            termino: moment(event.end).format('DD/MM/YYYY HH:mm:00.000'),
            diaTodo: event.allDay,
            percentualConclusao: event.percent,
            situacao: event.status,
            acaoTipo: event.type
        };

        console.log(objSalvar);

        $http.post(caminho + ACAO_SALVAR, objSalvar)
        .success(function (data) {
            $rootScope.emProcessamento(false);
            if (data.executou) {
                toaster.pop('success', "Salvo", "Registro movido com sucesso!");
            } else {
                toaster.pop('error', "Erro", "Erro ao mover o evento!");
                console.log(data);
                if (!angular.isObject(data)) {
                    $rootScope.erroSessao(data);
                }
            }
        }).error(function (data) {
            $rootScope.emProcessamento(false);
            console.log("SALVAR => Ocorreu algum erro!");
            toaster.pop('error', "Erro no servidor", data, 0);
            console.log(data);
        });

    }

    $scope.dataSelecionada = function (data) {
        $rootScope.evento.novo = {id: null, inicio: {horas:null}, fim: {horas:null}, diaTodo: 0 };
        data = moment(data);

        $rootScope.evento.novo.inicio.data = angular.copy(data);
        $rootScope.evento.novo.data = angular.copy(data.format('DD/MM/YYYY'));
        $("#modalNovoEvento").modal('show');
    };

    $scope.eventoSelecionado = function (event) {
        $rootScope.evento.novo = {
            id: event.id,
            titulo: event.title,
            descricao: event.description,
            inicio: { data: event.start, horas: moment(event.start).format("HH:mm") },
            fim: { data: event.end, horas: moment(event.end).format("HH:mm") },
            diaTodo: event.allDay,
            percentualConclusao: event.percent,
            situacao: event.status,
            acaoTipo: event.type,
            data: angular.copy(moment(event.start).format('DD/MM/YYYY')),
            pessoaAcaoList: event.pessoaAcaoList
        };

        console.log(event);
        $("#modalNovoEvento").modal('show');
    };

    $scope.remove = function () {
        var caminho = baseUrl + PAGINA
        var id = $rootScope.evento.novo.id;
        $http.get(caminho + ACAO_EXCLUIR, { params: { id: id } })
        .success(function (data) {
            if (data.executou) {
                angular.forEach($rootScope.agenda, function (lista, listaKey) {
                    angular.forEach(lista, function (obj, key) {
                        console.log(obj)
                        if (id === obj.id) {
                            lista.splice(key, 1);
                            toaster.pop('success', null, "Evento removido com sucesso!");
                            return 1;
                        }
                    });
                });
            } else {
                toaster.pop('error', "Erro", "N�o foi poss�vel remover o evento!");
            }
            
        }).error(function (data) {
            console.log(data);
            toaster.pop('error', "Erro", "Erro de par�metro!");
        });
        return 0;
    };

    $scope.changeView = function (view, calendar) {
        calendar.fullCalendar('changeView', view);
    };

    $scope.renderCalender = function (calendar) {
        calendar.fullCalendar('render');
    };

    $scope.addEvent = function () {
        var caminho = baseUrl + PAGINA
        $rootScope.emProcessamento(true);
        if (angular.isUndefined($rootScope.evento.novo.fim.data)) {
            $rootScope.evento.novo.fim.data = angular.copy($rootScope.evento.novo.inicio.data);
        }
        if (!$rootScope.evento.novo.diaTodo) {
            if (angular.isUndefined($rootScope.evento.novo.inicio.horas) || $rootScope.evento.novo.inicio.horas.length !== 5) {
                $rootScope.emProcessamento(false);
                toaster.pop('error', "Erro", "Hora de in�cio incorreto! Exemplo: 17:30");
                return 0;
            }
            if (angular.isUndefined($rootScope.evento.novo.fim.horas) || $rootScope.evento.novo.fim.horas.length !== 5) {
                $rootScope.emProcessamento(false);
                toaster.pop('error', "Erro", "Hora de t�rmino incorreto! Exemplo: 17:30");
                return 0;
            }
            var inicioH = $rootScope.evento.novo.inicio.horas.split(':');
            var fimH;

            if (angular.isUndefined($rootScope.evento.novo.fim.horas)) {
                fimH = angular.copy(inicioH);
            } else {
                fimH = $rootScope.evento.novo.fim.horas.split(':');
            }

            $rootScope.evento.novo.inicio.data.setHours(inicioH[0]);
            $rootScope.evento.novo.inicio.data.setMinutes(inicioH[1]);

            $rootScope.evento.novo.fim.data.setHours(fimH[0]);
            $rootScope.evento.novo.fim.data.setMinutes(fimH[1]);
        }

        var objSalvar = {
            id: $rootScope.evento.novo.id,
            nome: $rootScope.evento.novo.titulo,
            descricao: $rootScope.evento.novo.descricao,
            inicio: moment($rootScope.evento.novo.inicio.data).format('DD/MM/YYYY HH:mm:00.000'),
            termino: moment($rootScope.evento.novo.fim.data).format('DD/MM/YYYY HH:mm:00.000'),
            diaTodo: parseInt($rootScope.evento.novo.diaTodo),
            percentualConclusao: '100',
            situacao: 'E',
            acaoTipo: { id: 1, nome: 'ATER' }
        };
        console.log(objSalvar);
        $http.post(caminho + ACAO_SALVAR, objSalvar)
        .success(function (data) {
            $rootScope.emProcessamento(false);
            if (data.executou) {
                toaster.pop('success', "Salvo", "Registro salvo com sucesso!");
                var obj = data.resultado;

                var dataInicio = moment(obj.inicio, "DD-MM-YYYY HH:mm").toDate();
                if (angular.isUndefined(obj.termino)) {
                    var dataFim = angular.copy(dataInicio);
                } else {
                    var dataFim = moment(obj.termino, "DD-MM-YYYY HH:mm").toDate();
                }


                var novoEvento = {
                    id: obj.id,
                    title: obj.nome,
                    description: obj.descricao,
                    start: dataInicio,
                    end: dataFim,
                    allDay: obj.diaTodo,
                    className: ['openSesame'],
                    percent: obj.percentualConclusao,
                    status: obj.situacao,
                    type: obj.acaoTipo
                };
                
                angular.forEach($rootScope.agenda, function (lista, listaKey) {
                    angular.forEach(lista, function (obj, key) {
                        if (novoEvento.id === obj.id) {
                            lista.splice(key, 1);
                        }
                    });
                });

                

                $rootScope.agenda[0].push(novoEvento);



            } else {
                toaster.pop('error', "Erro", "Erro ao salvar!");
                console.log(data);
                if (!angular.isObject(data)) {
                    $rootScope.erroSessao(data);
                }
            }
        }).error(function (data) {
            $rootScope.emProcessamento(false);
            console.log("SALVAR => Ocorreu algum erro!");
            toaster.pop('error', "Erro no servidor", data, 0);
            console.log(data);
        });

    };

    $scope.executar = function () {
        var caminho = baseUrl + PAGINA;
        $rootScope.botoesAcao('agenda');
        $rootScope.linhasSelecionadas = [];
        $location.path('/formulario');
        $rootScope.emProcessamento(true);


        $http.get(caminho + ACAO_FILTRAR, $rootScope.filtro)
        .success(function (data) {
            console.log(data);
            if (data.executou) {
                $rootScope.registro = data.resultado;
                
                angular.forEach($rootScope.registro, function (obj, index) {
                    var dataInicio = moment(obj.inicio, "DD-MM-YYYY HH:mm").toDate();
                    if (angular.isUndefined(obj.termino)) {
                        var dataFim = angular.copy(dataInicio);
                    } else {
                        var dataFim = moment(obj.termino, "DD-MM-YYYY HH:mm").toDate();
                    }

                    /*
                    $rootScope.events.push({
                        id: obj.id,
                        title: obj.nome,
        
                        start: data,
                  
                        allDay: diaTodo,
                        className: ['openSesame']
 
                    });
                   */
                    $rootScope.events.push({
                        id: obj.id,
                        title: obj.nome,
                        description: obj.descricao,
                        start: dataInicio,
                        end: dataFim,
                        allDay: obj.diaTodo,
                        className: ['openSesame'],
                        percent: obj.percentualConclusao,
                        status: obj.situacao,
                        type: valorCampoJson($rootScope.registro, obj.acaoTipo),
                        pessoaAcaoList: obj.pessoaAcaoList
                    });
                });
                $rootScope.agenda = [$rootScope.events];
                console.log($rootScope.events);
                $rootScope.emProcessamento(false);
            } else {
                toaster.pop('error', null, "Erro ao tentar filtrar os eventos!");
            }
        });

        $rootScope.emProcessamento(false);
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
	
	if (isUndefOrNull($scope.apoio)) {
		$scope.apoio = {
			hoje : new Date()
		};
		
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
	}
	
	$scope.apoio.incluirAcao = function() {
		$scope.apoio.exibirAcao = !$scope.apoio.exibirAcao;
		$('#modalAcoes').modal('show');
	}

    
}