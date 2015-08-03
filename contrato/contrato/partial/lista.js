angular.module('contrato').controller('ContratoListaCtrl',['$scope', function($scope){
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

}]);