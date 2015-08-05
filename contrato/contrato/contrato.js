angular.module('contrato', ['ui.bootstrap','ui.utils','ui.router','ngAnimate', 'frz.navegador']);

angular.module('contrato').config(['$stateProvider', function($stateProvider) {

    $stateProvider.state('contrato', {
        abstract: true,
        controller: 'ContratoCtrl',
        templateUrl: 'contrato/contrato.html',
        url: '/contrato',
    });
    $stateProvider.state('contrato.filtro', {
        templateUrl: 'contrato/filtro.html',
        url: '',
    });
    $stateProvider.state('contrato.lista', {
        templateUrl: 'contrato/lista.html',
        url: '/lista',
    });
    $stateProvider.state('contrato.form', {
        templateUrl: 'contrato/form.html',
        url: '/form/:id',
    });
    /* Add New States Above */

}]);

angular.module('contrato').controller('ContratoCtrl', 
    ['$scope', 'toastr', 'FrzNavegadorParams', '$state', '$rootScope', '$modal', '$log', '$modalInstance', 'modalCadastro', 
    function($scope, toastr, FrzNavegadorParams, $state, $rootScope, $modal, $log, $modalInstance, modalCadastro) {

    $scope.nomeFormulario = 'Cadastro de Contratos & Convênios';

    $scope.frm = {};

    // inicio: atividades do Modal
    $scope.modalEstado = 'filtro';

    $scope.modalOk = function () {
        $scope.cadastro.lista = [];
        $scope.cadastro.lista.push({id: 21, nome: 'Fernando'});
        $scope.cadastro.lista.push({id: 12, nome: 'Frazao'});

        $modalInstance.close($scope.cadastro);
    };

    $scope.modalCancelar = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.modalAbrir = function (size) {
        var modalInstance = $modal.open({
            animation: true,
            template: '<ng-include src=\"\'contrato/contrato-modal.html\'\"></ng-include>',
            controller: 'ContratoCtrl',
            size: size,
            resolve: {
                modalCadastro: function () {
                    return angular.copy($scope.cadastro);
                }
            }
        });

        modalInstance.result.then(function (cadastroModificado) {
            $scope.navegador.setDados(cadastroModificado.lista);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
    // fim: atividades do Modal

    // se dados do modal estao vazios
    if ($modalInstance === null) {
        // construir um novo item
        $scope.modalEstado = null;
        $scope.cadastro = {lista : []};
        for (var i = 0; i < 200; i++) {
            $scope.navegador.dados.push({id: i, nome: 'nome ' + i});
        }
    } else {
        // recuperar o item
        $scope.cadastro = angular.copy(modalCadastro);
    }

    $scope.navegador = new FrzNavegadorParams($scope.cadastro.lista);

    $scope.abrir = function() {
        // ajustar o menu das acoes especiais
        $scope.navegador.botao('acao', 'acao')['subFuncoes'] = [
        {
            nome: 'Enviar E-mail',
            descricao: 'Enviar e-mails',
            acao: function() {console.log($scope.navegador.tamanhoPagina);$scope.enviarEmailConfirmacao();},
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
        if (meuEstado('filtro')) {
            $scope.navegador.mudarEstado('FILTRANDO');
        } else if (meuEstado('lista')) {
            $scope.navegador.mudarEstado('LISTANDO');
        } else if (meuEstado('form')) {
            $scope.navegador.mudarEstado('VISUALIZANDO');
        }
    };

    var vaiPara = function (estado) {
        if ($scope.modalEstado) {
            $scope.modalEstado = estado;
        } else {
            $state.go('^.' + estado);
        }
    };

    var meuEstado = function (estado) {
        if ($scope.modalEstado) {
            return $scope.modalEstado === estado;
        } else {
            return $state.is('^.' + estado);
        }
    };

    // rotina para sincronizar estado da tela e navegador
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){ 
        var estadoAtual = $scope.navegador.estadoAtual();
        if (meuEstado('filtro') && ['FILTRANDO'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('FILTRANDO');
        } else 
        if (meuEstado('lista') && ['LISTANDO', 'ESPECIAL'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('LISTANDO');
        } else 
        if (meuEstado('form') && ['INCLUINDO', 'VISUALIZANDO', 'EDITANDO'].indexOf(estadoAtual) < 0) {
            $scope.navegador.mudarEstado('INCLUINDO');
        }
    });

    // apoio a selecao de linhas na listagem
    $scope.seleciona = function(item) {
        if ($scope.navegador.selecao.tipo === 'U') {
            $scope.navegador.selecao.item = item;
        } else {
            var its = $scope.navegador.selecao.items;
            for (var i in its) {
                if (angular.equals(its[i], item)) {
                    its.splice(i, 1);
                    return;
                }
            }
            $scope.navegador.selecao.items.push(item);
        }
    };
    $scope.mataClick = function(event, item) {
        event.stopPropagation();
        $scope.seleciona(item);
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
        $scope.navegador.submitido = true;
        if ($scope.frm.formulario.$invalid) {
            return;
        }
        $scope.navegador.voltar();
        $scope.navegador.mudarEstado('VISUALIZANDO');
        vaiPara('form');
        $scope.navegador.submitido = false;
    };
    $scope.confirmarEditar = function() {
        $scope.confirmar();
        angular.copy($scope.cadastro.registro, $scope.cadastro.original);
    };
    $scope.confirmarExcluir = function() {
        if ($scope.navegador.selecao.tipo === 'U') {
            $scope.navegador.dados.splice($scope.indiceDe($scope.navegador.dados, $scope.navegador.selecao.item), 1);
            $scope.navegador.selecao.item = null;
        } else {
            for (var item in $scope.navegador.selecao.items) {
                $scope.navegador.dados.splice($scope.indiceDe($scope.navegador.dados, $scope.navegador.selecao.items[item]), 1);
            }
            $scope.navegador.selecao.items = [];
        };
        $scope.voltar();
    };
    $scope.confirmarFiltrar = function() {
        $scope.navegador.mudarEstado('LISTANDO');
        vaiPara('lista');
    };
    $scope.confirmarIncluir = function() {
        $scope.confirmar();
        $scope.navegador.dados.push($scope.cadastro.registro);
    };
    $scope.editar = function() {
        $scope.navegador.mudarEstado('EDITANDO');
        vaiPara('form');
    };
    $scope.excluir = function() {
        $scope.navegador.mudarEstado('EXCLUINDO');
    };
    $scope.filtrar = function() {
        $scope.navegador.mudarEstado('FILTRANDO');
        vaiPara('filtro');
    };
    var verRegistro = function() {
        if ($scope.navegador.selecao.tipo === 'U') {
            $scope.cadastro.original = $scope.navegador.selecao.item;
        } else {
            $scope.cadastro.original = $scope.navegador.selecao.items[$scope.navegador.folhaAtual];
        };
        $scope.cadastro.registro = angular.copy($scope.cadastro.original);
    };

    $scope.folhearAnterior = function() {
        verRegistro();
    };
    $scope.folhearPrimeiro = function() {
        verRegistro();
    };
    $scope.folhearProximo = function() {
        verRegistro();
    };
    $scope.folhearUltimo = function() {
        verRegistro();
    };

    $scope.incluir = function() {
        $scope.navegador.mudarEstado('INCLUINDO');
        vaiPara('form');
        $scope.cadastro.registro = {};
    };
    $scope.informacao = function() {};
    $scope.limpar = function() {
        var e = $scope.navegador.estadoAtual();
        if ('FILTRANDO' === e) {
            $scope.cadastro.filtro = {};
        } else {
            $scope.cadastro.registro = {};
        }
    };
    $scope.paginarAnterior = function() {};
    $scope.paginarPrimeiro = function() {};
    $scope.paginarProximo = function() {};
    $scope.paginarUltimo = function() {};
    $scope.restaurar = function() {
        angular.copy($scope.cadastro.original, $scope.cadastro.registro);
    };
    $scope.visualizar = function() {
        $scope.navegador.mudarEstado('VISUALIZANDO');
        vaiPara('form');
        verRegistro();
    };
    $scope.voltar = function() {
        $scope.navegador.voltar();
        var estadoAtual = $scope.navegador.estadoAtual();
        if (!meuEstado('filtro') && ['FILTRANDO'].indexOf(estadoAtual) >= 0) {
            vaiPara('filtro');
        } else if (!meuEstado('lista') && ['LISTANDO', 'ESPECIAL'].indexOf(estadoAtual) >= 0) {
            vaiPara('lista');
        } else if (!meuEstado('form') && ['INCLUINDO', 'VISUALIZANDO', 'EDITANDO'].indexOf(estadoAtual) >= 0) {
            vaiPara('form');
        }
    };

    $scope.enviarEmailConfirmacao = function () {
        var conf = $scope.pegarConfirmacao('Confirme o envio do e-mail?');

        conf.result.then(function () {
          $scope.exibirAlerta('E-mail enviado');
        }, function () {
          toastr.warning('O e-mail não foi enviado...');
        });

    };
}]);