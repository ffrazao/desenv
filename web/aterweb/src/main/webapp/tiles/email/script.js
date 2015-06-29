/**
 * Script do módulo Email
 */

// Ativação do Angular JS
(function() {
	aterweb.controller('cadastroCtrl', function($scope, $rootScope, $window,
			$http, $location, toaster) {

		$scope.apoio = {};

		$scope.apoio.contas = [ {
			id : 1,
			nome : "fernando.frazao@emater.df.gov.br",
			filhos : [ {
				id : 11,
				nome : "Entrada"
			}, {
				id : 12,
				nome : "Rascunho"
			}, {
				id : 13,
				nome : "Enviados"
			}, {
				id : 14,
				nome : "Lixeira"
			} ]
		}, {
			id : 2,
			nome : "getin@emater.df.gov.br",
			filhos : [ {
				id : 21,
				nome : "Entrada"
			}, {
				id : 22,
				nome : "Rascunho"
			}, {
				id : 23,
				nome : "Enviados"
			}, {
				id : 24,
				nome : "Lixeira"
			} ]
		} ];

	});

})();