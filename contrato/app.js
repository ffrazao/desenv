angular.module('principal', ['ui.bootstrap', 'ui.utils', 'ui.router', 'ngAnimate', 'toastr', 'sticky', 'painel.vidro', 'frz.navegador', 'casa', 'contrato']);

// inicio: codigo para habilitar o modal recursivo
angular.module('principal').factory('$modalInstance', function () {
  return null;
});

angular.module('principal').factory('modalCadastro', function () {
  return null;
});
// fim: codigo para habilitar o modal recursivo

angular.module('principal').config(['$stateProvider', '$urlRouterProvider', 'toastrConfig', '$locationProvider',
  function($stateProvider, $urlRouterProvider, toastrConfig, $locationProvider) {
    //$locationProvider.html5Mode(true);
    $stateProvider.state('p', {templateUrl: 'casa/principal.html'});
    
    $stateProvider.state('p.bem-vindo', {
      url: '/',
      templateUrl: 'casa/bem-vindo.html'
    });

    $stateProvider.state('p.casa', {
      url: '',
      templateUrl: 'casa/index.html',
      controller: ['$stateParams', 'toastr', function($stateParams, toastr){
        console.log($stateParams.mensagem);
            //toastr.warning('Endereço não encontrado! ' + $stateParams.mensagem, 'Atenção!');
          }],
        });
    $stateProvider.state('login', {
      url: '/login',
      templateUrl: 'login/login.html',
      controller: 'LoginCtrl'
    });

    /* Add New States Above */
    $urlRouterProvider.otherwise(function ($injector, $location) {
      var $state = $injector.get('$state');

      $state.go('casa', {mensagem: 'Endereço não localizado!' + $location.$$absUrl}, {'location': true});
    });
    // configuracao do toastr
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

angular.module('principal').run(['$rootScope', '$modal', 
  function($rootScope, $modal) {
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
    // inicio funcoes de apoio
    $rootScope.exibirAlerta = function (mensagem) {
      var modalInstance = $modal.open({
        animation: true,
        template: 
        '<div class="modal-header">' +
        '  <h3 class="modal-title">Atenção!</h3>' +
        '</div>' +
        '<div class="modal-body">' +
        '<p class=\"bg-info\">' + mensagem + '</p>' +
        '</div>' +
        '<div class="modal-footer">' +
        '  <button class="btn btn-primary" ng-click=\"$dismiss(\'ok\')\">OK</button>' +
        '</div>',
        resolve: {},
      });
    };
    $rootScope.pegarConfirmacao = function (mensagem) {
      var modalInstance = $modal.open({
        animation: true,
        template: 
        '<div class="modal-header">' +
        '  <h3 class="modal-title">Confirme!</h3>' +
        '</div>' +
        '<div class="modal-body">' +
        '<p class=\"bg-info\">' + mensagem + '</p>' +
        '</div>' +
        '<div class="modal-footer">' +
        '  <button class="btn btn-primary" ng-click=\"$close(\'ok\')\">OK</button>' +
        '  <button class="btn btn-warning" ng-click=\"$dismiss(\'cancelar\')\">Cancelar</button>' +
        '</div>',
        resolve: {},
      });
      return modalInstance;
    };
    $rootScope.indiceDe = function(arr, item) {
      for (var i = arr.length; i--;) {
        if (angular.equals(arr[i], item)) {
          return i;
        }
      }
      return null;
    };
    // fim funcoes de apoio
}]);
angular.module('principal').controller('MenuCtrl', function ($scope) {
  $scope.tree = 
  [
  {
    name: 'Dashboard',
    link: '#',
  }, 
  {
    name: 'Cadastro',
    link: '#',
    subtree: [
    {
      name: 'Pessoa',
      link: 'p.pessoa.filtro',
    },
    {
      name: 'Grupo Social',
      link: 'p.grupoSocial.filtro',
    },
    {
      name: 'Propriedade Rural',
      link: 'p.propriedadeRural.filtro',
    },
    ]
  },
  {
    name: 'Atividade',
    link: '#',
    subtree: [
    {
      name: 'Planejar',
      link: '#',
    },
    {
      name: 'Registrar',
      link: 'p.modeloCadastro.filtro',
    },
    {
      name: 'Agenda',
      link: 'login',
    }
    ]
  },
  {
    name: 'Diagnóstico',
    link: '#',
    subtree: [
    {
      name: 'Índices de Produção',
      link: 'p.indiceProducao.filtro',
    },
    {
      name: 'Índices Sociais',
      link: 'p.modeloCadastro.filtro',
    },
    {
      name: 'Enquete',
      link: 'login',
      subtree: [
      {
        name: 'Configuração',
        link: '#',
      },
      {
        name: 'Responder',
        link: 'p.modeloCadastro.filtro',
        subtree: [
        {
          name: 'Anônimo',
          link: '#',
        },
        {
          name: 'Identificado',
          link: 'p.modeloCadastro.filtro',
        },
        ],
      },
      ],
    },
    ],
  },
  {
    name: 'Configuração',
    link: 'login',
    subtree: [
    {
      name: 'Usuário',
      link: 'p.usuario.filtro',
    },
    {
      name: 'Perfil',
      link: 'p.perfil.filtro',
    },
    {
      name: 'Log',
      link: 'p.log.filtro',
    },
    ],
  },
  ];
 });
angular.module('principal').controller('RenoveSuaSenhaCtrl', function ($scope, $modalInstance, toastr, registroOrig) {
  $scope.iniciar = function() {
    //$scope.registroOrig = {};
    console.log("ddd", registroOrig);
    $scope.reiniciar();
  };

  $scope.reiniciar = function() {
    $scope.submitted = false;
    $scope.registro = angular.copy(registroOrig);
    if ($scope.$parent.renoveSuaSenhaForm) {
      $scope.$parent.renoveSuaSenhaForm.$setPristine();
    }
    $('#novaSenha').focus();
  };
  $scope.iniciar();

  // métodos de apoio
  $scope.submitForm = function () {
    if (!$scope.$parent.renoveSuaSenhaForm.$valid) {
      $scope.submitted = true;
      toastr.error('Verifique os campos marcados', 'Erro');
      return;
    }
    toastr.success('Sua senha foi renovada');
    console.log('retornando ', angular.copy($scope.registro));
    $modalInstance.close(angular.copy($scope.registro));
  };
  $scope.cancelar = function () {
    $modalInstance.dismiss('cancel');
  };
});
angular.module('principal').controller('EsqueciMinhaSenhaCtrl', function ($scope, $modalInstance, toastr) {
  $scope.iniciar = function() {
    $scope.registroOrig = {};
    $scope.reiniciar();
  };
  $scope.reiniciar = function() {
    $scope.submitted = false;
    $scope.registro = angular.copy($scope.registroOrig);
    if ($scope.$parent.esqueciMinhaSenhaForm) {
      $scope.$parent.esqueciMinhaSenhaForm.$setPristine();
    }
    $('#email').focus();
  };

  $scope.iniciar();

  // métodos de apoio
  $scope.submitForm = function () {
    if (!$scope.$parent.esqueciMinhaSenhaForm.$valid) {
      $scope.submitted = true;
      toastr.error('Verifique os campos marcados', 'Erro');
      return;
    }
    toastr.success('Foi encaminhado um e-mail com instrucoes para recuperar seu acesso ao sistema');
    $modalInstance.close();
  };

  $scope.cancelar = function () {
    $modalInstance.dismiss('cancel');
  };
  
});
angular.module('principal').controller('LoginCtrl', function ($scope, $location, $modal, toastr, $state, $http) {
  $scope.iniciar = function() {
    $scope.registroOrig = $location.search();
    $scope.reiniciar();
  };
  $scope.reiniciar = function() {
    $scope.submitted = false;
    $scope.registro = angular.copy($scope.registroOrig);
    if ($scope.$parent.loginForm) {
      $scope.$parent.loginForm.$setPristine();
    }
    $('#usuario').focus();
  };

  $scope.iniciar();

  // métodos de apoio
  $scope.submitForm = function () {
    if (!$scope.$parent.loginForm.$valid) {
      $scope.submitted = true;
      toastr.error('Verifique os campos marcados', 'Erro');
      //$scope.mensagens.push({ tipo: 'danger', texto: 'Verifique os campos marcados' });
      return;
    }
    $scope.renoveSuaSenha();
    // $http.get("http://localhost:8080/login").success(function(data) {
    //   console.log('OK');
    // });

  };

  $scope.mensagens = [
    // { tipo: 'danger', texto: 'Oh snap! Change a few things up and try submitting again.' },
    // { tipo: 'success', texto: 'Well done! You successfully read this important alert message.' }
   ];

   $scope.closeAlert = function(index) {
    $scope.mensagens.splice(index, 1);
  };

  $scope.esqueciMinhaSenha = function (size) {
    var modalInstance = $modal.open({
      templateUrl: 'login/esqueci-minha-senha.html',
      controller: 'EsqueciMinhaSenhaCtrl',
      size: size
    });

    modalInstance.result.then(function () {
      $('#usuario').focus();
    }, function () {
      //$log.info('Modal dismissed at: ' + new Date());
    });
  };

  $scope.renoveSuaSenha = function (size) {
    var modalInstance = $modal.open({
      templateUrl: 'login/renove-sua-senha.html',
      controller: 'RenoveSuaSenhaCtrl',
      size: size,
      resolve: {
        registroOrig: function () {
          return $scope.registro;
        }
      }
    });

    modalInstance.result.then(function (registro) {
      $('#usuario').focus();
      $scope.registro.senha = angular.copy(registro.novaSenha);
    }, function () {
      //$log.info('Modal dismissed at: ' + new Date());
    });
  };

});