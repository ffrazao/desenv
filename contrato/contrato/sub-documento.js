(function() {

	'use strict';

	angular.module('contrato').controller('ContratoDocumentoCtrl', ['$scope', 'FrzNavegadorParams', '$modal', '$modalInstance', 'toastr',
		function($scope, FrzNavegadorParams, $modal, $modalInstance, toastr) {

    // inicializacao
    var init = function() {
		if (!angular.isObject($scope.cadastro.registro.documento)) {
			$scope.cadastro.registro.documento = [];
		}
        $scope.contratoArquivoNvg = new FrzNavegadorParams($scope.cadastro.registro.documento);
    };
    if (!$modalInstance) {
        init();
    }

    // inicio: atividades do Modal
    $scope.modalOk = function () {
        // Retorno da modal
        $scope.cadastro.lista = [];
        $scope.cadastro.lista.push({id: 21, nome: 'Fernando'});
        $scope.cadastro.lista.push({id: 12, nome: 'Frazao'});

        $modalInstance.close($scope.cadastro);
        toastr.info('Operação realizada!', 'Informação');
    };
    $scope.modalCancelar = function () {
        // Cancelar a modal
        $modalInstance.dismiss('cancel');
        toastr.warning('Operação cancelada!', 'Atenção!');
    };
    $scope.modalAbrir = function (size) {
        // abrir a modal
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
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal
            $scope.contratoArquivoNvg.setDados(cadastroModificado.lista);
        }, function () {
            // processar o retorno negativo da modal
        });
    };
    if ($modalInstance === null) {
        // se objeto modal esta vazio abrir de forma normal
        $scope.modalEstado = null;
        for (var i = 0; i < 0; i++) {
           $scope.contratoArquivoNvg.dados.push({id: i, nome: 'nome ' + i});
        }
    } else {
        // recuperar o item
        $scope.modalEstado = 'filtro';
        // atualizar o cadastro
//        $scope.cadastro = angular.copy(modalCadastro);
    }
    // fim: atividades do Modal

    // inicio rotinas de apoio
    var vaiPara = function (estado) {
        if ($scope.modalEstado) {
            $scope.modalEstado = estado;
        } else {
//            $state.go('^.' + estado);
        }
    };
    var meuEstado = function (estado) {
        if ($scope.modalEstado) {
            return $scope.modalEstado === estado;
        } else {
  //          return $state.is('^.' + estado);
        }
    };
    var verRegistro = function() {
        if ($scope.contratoArquivoNvg.selecao.tipo === 'U') {
            $scope.cadastro.original = $scope.contratoArquivoNvg.selecao.item;
        } else {
            $scope.cadastro.original = $scope.contratoArquivoNvg.selecao.items[$scope.contratoArquivoNvg.folhaAtual];
        }
        $scope.cadastro.registro = angular.copy($scope.cadastro.original);
        $scope.contratoArquivoNvg.refresh();
    };
    $scope.seleciona = function(item) {
        if (!angular.isObject(item)) {
            return;
        }
        // apoio a selecao de linhas na listagem
        if ($scope.contratoArquivoNvg.selecao.tipo === 'U') {
            $scope.contratoArquivoNvg.selecao.item = item;
        } else {
            var its = $scope.contratoArquivoNvg.selecao.items;
            for (var i in its) {
                if (angular.equals(its[i], item)) {
                    its.splice(i, 1);
                    return;
                }
            }
            $scope.contratoArquivoNvg.selecao.items.push(item);
        }
    };
    $scope.mataClick = function(event, item) {
        event.stopPropagation();
        $scope.seleciona(item);
    };
    // fim rotinas de apoio

    // inicio das operaçoes atribuidas ao navagador
    $scope.abrir = function() {
        // ajustar o menu das acoes especiais
        $scope.contratoArquivoNvg.mudarEstado('ESPECIAL');
    };
    $scope.agir = function() {};
    $scope.ajudar = function() {};
    $scope.alterarTamanhoPagina = function() {};
    $scope.cancelar = function() {
        $scope.voltar();
        toastr.warning('Operação cancelada!', 'Atenção!');
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
        $scope.contratoArquivoNvg.submitido = true;
        if ($scope.frm.formulario.$invalid) {
            toastr.error('Verifique os campos marcados', 'Erro');
            return false;
        }
        $scope.contratoArquivoNvg.voltar();
        $scope.contratoArquivoNvg.mudarEstado('VISUALIZANDO');
        vaiPara('form');
        $scope.contratoArquivoNvg.submitido = false;
        return true;
    };
    $scope.confirmarEditar = function() {
        if (!$scope.confirmar()) {
            return;
        }
        angular.copy($scope.cadastro.registro, $scope.cadastro.original);
        toastr.info('Operação realizada!', 'Informação');
    };
    $scope.confirmarExcluir = function() {
        if (meuEstado('form')) {
            if ($scope.contratoArquivoNvg.selecao.tipo === 'U') {
                $scope.contratoArquivoNvg.dados.splice($scope.indiceDe($scope.contratoArquivoNvg.dados, $scope.contratoArquivoNvg.selecao.item), 1);
                $scope.contratoArquivoNvg.selecao.item = null;
                $scope.contratoArquivoNvg.mudarEstado('LISTANDO');
                vaiPara('lista');
            } else {
                var reg = $scope.contratoArquivoNvg.selecao.items[$scope.contratoArquivoNvg.folhaAtual];
                $scope.contratoArquivoNvg.dados.splice($scope.indiceDe($scope.contratoArquivoNvg.dados, reg), 1);
                $scope.contratoArquivoNvg.selecao.items.splice($scope.indiceDe($scope.contratoArquivoNvg.selecao.items, reg), 1);
                if (!$scope.contratoArquivoNvg.selecao.items.length) {
                    $scope.contratoArquivoNvg.mudarEstado('LISTANDO');
                    vaiPara('lista');
                } else {
                    if ($scope.contratoArquivoNvg.folhaAtual >= $scope.contratoArquivoNvg.selecao.items.length) {
                        $scope.contratoArquivoNvg.folhaAtual = $scope.contratoArquivoNvg.selecao.items.length -1;
                    }
                    verRegistro();
                    $scope.voltar();
                }
            }
        } else if (meuEstado('lista')) {
            if ($scope.contratoArquivoNvg.selecao.tipo === 'U') {
                $scope.contratoArquivoNvg.dados.splice($scope.indiceDe($scope.contratoArquivoNvg.dados, $scope.contratoArquivoNvg.selecao.item), 1);
                $scope.contratoArquivoNvg.selecao.item = null;
            } else {
                for (var item = $scope.contratoArquivoNvg.selecao.items.length; item--;) {
                    $scope.contratoArquivoNvg.dados.splice($scope.indiceDe($scope.contratoArquivoNvg.dados, $scope.contratoArquivoNvg.selecao.items[item]), 1);
                }
                $scope.contratoArquivoNvg.selecao.items = [];
            }
            $scope.voltar();
        }
        toastr.info('Operação realizada!', 'Informação');
    };
    $scope.confirmarFiltrar = function() {
        $scope.contratoArquivoNvg.mudarEstado('LISTANDO');
        vaiPara('lista');
        $scope.contratoArquivoNvg.setDados($scope.cadastro.lista);
    };
    $scope.confirmarIncluir = function() {
        if (!$scope.confirmar()) {
            return;
        }
        $scope.contratoArquivoNvg.dados.push($scope.cadastro.registro);
        if ($scope.contratoArquivoNvg.selecao.tipo === 'U') {
            $scope.contratoArquivoNvg.selecao.item = $scope.cadastro.registro;
        } else {
            $scope.contratoArquivoNvg.folhaAtual = $scope.contratoArquivoNvg.selecao.items.length;
            $scope.contratoArquivoNvg.selecao.items.push($scope.cadastro.registro);
        }
        $scope.contratoArquivoNvg.refresh();

        toastr.info('Operação realizada!', 'Informação');
    };
    $scope.editar = function() {
        $scope.contratoArquivoNvg.mudarEstado('EDITANDO');
        vaiPara('form');
        verRegistro();
    };
    $scope.excluir = function() {
        $scope.contratoArquivoNvg.mudarEstado('EXCLUINDO');
    };
    $scope.filtrar = function() {
        $scope.contratoArquivoNvg.mudarEstado('FILTRANDO');
        vaiPara('filtro');
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
        var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'contratoArquivoFrm.html',
            controller: 'ContratoDocumentoCtrl',
            size: 'lg',
            resolve: {
                modalCadastro: function () {
                    return angular.copy($scope.cadastro);
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal
            $scope.contratoArquivoNvg.setDados(cadastroModificado.lista);
        }, function () {
            // processar o retorno negativo da modal
        });
    };
    $scope.informacao = function() {};
    $scope.limpar = function() {
        var e = $scope.contratoArquivoNvg.estadoAtual();
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
        $scope.contratoArquivoNvg.mudarEstado('VISUALIZANDO');
        vaiPara('form');
        verRegistro();
    };
    $scope.voltar = function() {
        $scope.contratoArquivoNvg.voltar();
        var estadoAtual = $scope.contratoArquivoNvg.estadoAtual();
        if (!meuEstado('filtro') && ['FILTRANDO'].indexOf(estadoAtual) >= 0) {
            vaiPara('filtro');
        } else if (!meuEstado('lista') && ['LISTANDO', 'ESPECIAL'].indexOf(estadoAtual) >= 0) {
            vaiPara('lista');
        } else if (!meuEstado('form') && ['INCLUINDO', 'VISUALIZANDO', 'EDITANDO'].indexOf(estadoAtual) >= 0) {
            vaiPara('form');
        }
    };
    // fim das operaçoes atribuidas ao navagador

} // fim função
]);
})();