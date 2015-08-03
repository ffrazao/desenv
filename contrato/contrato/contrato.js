angular.module('contrato', ['ui.bootstrap','ui.utils','ui.router','ngAnimate', 'frz.navegador']);

angular.module('contrato').config(['$stateProvider', function($stateProvider) {

    $stateProvider.state('contrato', {
        url: '/contrato',
        templateUrl: 'contrato/contrato.html',
        abstract: true,
    });
    $stateProvider.state('contrato.filtro', {
        url: '',
        templateUrl: 'contrato/partial/filtro.html',
    });
    $stateProvider.state('contrato.lista', {
        url: '/lista',
        templateUrl: 'contrato/partial/lista.html',
    });
    $stateProvider.state('contrato.form', {
        url: '/form',
        templateUrl: 'contrato/partial/form.html',
    });
    /* Add New States Above */

}]);

angular.module('contrato').controller('ContratoCtrl', ['$scope', 'toastr', 'FrzNavegadorParams', '$state', '$rootScope', function($scope, toastr, FrzNavegadorParams, $state, $rootScope) {

    $scope.cadastro = {lista : []};

    for (var i = 0; i < 200; i++) {
        $scope.cadastro.lista.push({id: i, nome: 'nome ' + i});
    }

    $scope.navegador = new FrzNavegadorParams($scope.cadastro.lista);

    $scope.abrir = function() {
        // ajustar o menu das acoes especiais
        $scope.navegador.botao('acao', 'acao')['subFuncoes'] = [
            {
                nome: 'Enviar E-mail',
                descricao: 'Enviar e-mails',
                acao: function() {console.log($scope.navegador.tamanhoPagina);},
                exibir: function() {
                    return $scope.navegador.estadoAtual() === 'LISTANDO' && ($scope.navegador.selecao.tipo === 'U' && $scope.navegador.selecao.selecionado) ||
                        ($scope.navegador.selecao.tipo === 'M' && $scope.navegador.selecao.marcado > 0);
                },
            },
            {
                nome: 'Desbloquear Usuário',
                descricao: 'Desbloquear Usuários',
                acao: function() {console.log('sub acao click ' + this.nome);},
                exibir: function() {
                    return $scope.navegador.selecao.tipo === 'M' && $scope.navegador.selecao.marcado > 1;
                },
            },
        ];
        // ao iniciar ajustar o navegador com o estado da tela
        if ($state.is('^.filtro')) {
            $scope.navegador.mudarEstado('FILTRANDO');
        } else if ($state.is('^.lista')) {
            $scope.navegador.mudarEstado('LISTANDO');
        } else if ($state.is('^.form')) {
            $scope.navegador.mudarEstado('VISUALIZANDO');
        }
    };

    // rotina para sincronizar estado da tela e navegador
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){ 
        var estadoAtual = $scope.navegador.estadoAtual();
        if ($state.is('^.filtro') && ['FILTRANDO'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('FILTRANDO');
        } else 
        if ($state.is('^.lista') && ['LISTANDO', 'ESPECIAL'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('LISTANDO');
        } else 
        if ($state.is('^.form') && ['INCLUINDO', 'VISUALIZANDO', 'EDITANDO'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('INCLUINDO');
        }
    });

    $scope.temMaisRegistros = function() {
        console.log('tem mais registros?');
    };

    $scope.agir = function() {};
    $scope.ajudar = function() {};
    $scope.alterarTamanhoPagina = function() {};
    $scope.cancelar = function() {
        $scope.voltar();
    };
    $scope.cancelarEditar = function() {
        $scope.cancelar();
    };
    $scope.cancelarExcluir = function() {
        $scope.cancelar();
    };
    $scope.cancelarFiltrar = function() {
        $scope.cancelar();
    };
    $scope.cancelarIncluir = function() {
        $scope.cancelar();
    };
    $scope.confirmar = function() {
        $scope.navegador.mudarEstado('VISUALIZANDO');
        $state.go('^.form');
    };
    $scope.confirmarEditar = function() {
        $scope.confirmar();
    };
    $scope.confirmarExcluir = function() {
        $scope.voltar();
    };
    $scope.confirmarFiltrar = function() {
        $scope.navegador.mudarEstado('LISTANDO');
        $state.go('^.lista');
    };
    $scope.confirmarIncluir = function() {
        $scope.confirmar();
    };
    $scope.editar = function() {
        $scope.navegador.mudarEstado('EDITANDO');
        $state.go('^.form');
    };
    $scope.excluir = function() {
        $scope.navegador.mudarEstado('EXCLUINDO');
    };
    $scope.filtrar = function() {
        $scope.navegador.mudarEstado('FILTRANDO');
        $state.go('^.filtro');
    };
    $scope.folhearAnterior = function() {};
    $scope.folhearPrimeiro = function() {};
    $scope.folhearProximo = function() {};
    $scope.folhearUltimo = function() {};
    $scope.incluir = function() {
        $scope.navegador.mudarEstado('INCLUINDO');
        $state.go('^.form');
    };
    $scope.informacao = function() {};
    $scope.limpar = function() {};
    $scope.paginarAnterior = function() {};
    $scope.paginarPrimeiro = function() {};
    $scope.paginarProximo = function() {};
    $scope.paginarUltimo = function() {};
    $scope.restaurar = function() {};
    $scope.visualizar = function() {
        $scope.navegador.mudarEstado('VISUALIZANDO');
        $state.go('^.form');
    };
    $scope.voltar = function() {
        $scope.navegador.voltar();
        var estadoAtual = $scope.navegador.estadoAtual();
        if (!$state.is('^.filtro') && ['FILTRANDO'].indexOf(estadoAtual) >= 0) {
            $state.go('^.filtro');
        } else if (!$state.is('^.lista') && ['LISTANDO', 'ESPECIAL'].indexOf(estadoAtual) >= 0) {
            $state.go('^.lista');
        } else if (!$state.is('^.form') && ['INCLUINDO', 'VISUALIZANDO', 'EDITANDO'].indexOf(estadoAtual) >= 0) {
            $state.go('^.form');
        }
    };
}]);