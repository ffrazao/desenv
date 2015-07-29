angular.module('contrato', ['ui.bootstrap', 'ui.utils', 'ui.router', 'ngAnimate', 'contrato']);

angular.module('contrato').config(function($stateProvider, $urlRouterProvider) {
    alert(1);

    $stateProvider.state('home', {
        url: '',
        templateUrl: '/home.html',
    });
    /* Add New States Above */
    $urlRouterProvider.otherwise('/home');

});

angular.module('contrato').run(function($rootScope) {

    $rootScope.safeApply = function(fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

});
