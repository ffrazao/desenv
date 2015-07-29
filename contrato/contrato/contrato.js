angular.module('contrato', ['ui.bootstrap','ui.utils','ui.router','ngAnimate']);

angular.module('contrato').config(function($stateProvider) {

    $stateProvider.state('contrato', {
        url: '/contrato',
        templateUrl: 'contrato/index.html',
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

});

