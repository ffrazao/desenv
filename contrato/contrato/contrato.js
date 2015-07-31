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

angular.module('contrato').controller('ContratoCtrl', ['$scope', 'toastr', 'FrzNavegadorParams', function($scope, toastr, FrzNavegadorParams) {

    $scope.cadastro = {lista : [{id:1}, {id:3}]};

    $scope.navegador = new FrzNavegadorParams($scope.cadastro.lista);

    $scope.abrir = function () {
        $scope.navegador.mudarEstado('LISTANDO');
    };

}]);