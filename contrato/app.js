angular.module('principal', ['ui.bootstrap', 'ui.utils', 'ui.router', 'ngAnimate', 'toastr', 'frz.navegador', 'contrato', 'casa']);

angular.module('principal').config(['$stateProvider', '$urlRouterProvider', 'toastrConfig', function($stateProvider, $urlRouterProvider, toastrConfig) {

    $stateProvider.state('casa', {
        url: '',
        templateUrl: 'casa/index.html',
        controller: ['$stateParams', 'toastr', function($stateParams, toastr){
            console.log($stateParams.mensagem);
            toastr.warning('Endereço não encontrado! ' + $stateParams.mensagem, 'Atenção!');
        }],
    });
    /* Add New States Above */
    $urlRouterProvider.otherwise(function ($injector, $location) {
        var $state = $injector.get('$state');

        $state.go('casa', {mensagem: 'Endereço não localizado!' + $location.$$absUrl}, {'location': true});
    });

    angular.extend(toastrConfig, {
        allowHtml: false,
        autoDismiss: false,
        closeButton: true,
        closeHtml: '<button>&times;</button>',
        containerId: 'toast-container',
        extendedTimeOut: 1000,
        iconClasses: {
          error: 'toast-error',
          info: 'toast-info',
          success: 'toast-success',
          warning: 'toast-warning'
      },
      maxOpened: 0,    
      messageClass: 'toast-message',
      newestOnTop: true,
      onHidden: null,
      onShown: null,
      positionClass: 'toast-top-full-width',
      preventDuplicates: false,
      preventOpenDuplicates: true,
      progressBar: true,
      tapToDismiss: true,
      target: 'body',
      templates: {
          toast: 'directives/toast/toast.html',
          progressbar: 'directives/progressbar/progressbar.html'
      },
      timeOut: 4000,
      titleClass: 'toast-title',
      toastClass: 'toast'
  });

}]);

angular.module('principal').run(['$rootScope', function($rootScope) {

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

}]);