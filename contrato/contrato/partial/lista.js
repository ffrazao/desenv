angular.module('contrato').controller('ContratoListaCtrl',['$scope', 'FrzNavegadorParams', function($scope, FrzNavegadorParams){
	
	$scope.navegador = new FrzNavegadorParams();

	$scope.cadastro = {lista : [{id:1}, {id:3}]};

	$scope.abrir = function () {
      $scope.navegador.mudarEstado('FILTRANDO');
  	};

}]);