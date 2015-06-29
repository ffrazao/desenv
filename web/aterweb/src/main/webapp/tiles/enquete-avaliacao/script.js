/**
 * Script do m�dulo
 */

// Ativa��o do Angular JS
(function() {
	aterweb.controller('cadastroCtrl', function($scope, $rootScope, $window, $http, $location, toaster) {
            	
        $scope.avaliacoesDados = [];
		$scope.avaliacoes = function () {
            $rootScope.emProcessamento(true);
            jQuery.ajax({
                type: "GET",
                url: baseUrl + "enquete-avaliacao-dados",
                async: false,
                success: function (data) {
                	 $rootScope.emProcessamento(false);
                     $scope.avaliacoesDados = data;
                }
            });
		}();
	});
})();