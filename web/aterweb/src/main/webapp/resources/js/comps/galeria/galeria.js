/*
 ______      __   ___                      __                            
/\  _  \    /\ \ /\_ \                    /\ \              __           
\ \ \L\ \   \_\ \\//\ \      __   _ __    \ \ \      __  __/\_\  ____    
 \ \  __ \  /'_` \ \ \ \   /'__`\/\`'__\   \ \ \  __/\ \/\ \/\ \/\_ ,`\  
  \ \ \/\ \/\ \L\ \ \_\ \_/\  __/\ \ \/     \ \ \L\ \ \ \_\ \ \ \/_/  /_ 
   \ \_\ \_\ \___,_\/\____\ \____\\ \_\      \ \____/\ \____/\ \_\/\____\
    \/_/\/_/\/__,_ /\/____/\/____/ \/_/       \/___/  \/___/  \/_/\/____/                                                                       
 * 
 * @author Adler Luiz
 * @date 09/06/2014
 * @modify 03/07/2014
 */

var galeria = angular.module("galeria", ["angularFileUpload"]);

galeria.directive('galeria', function($rootScope){
    return {
        restrict: 'E',
        templateUrl: 'resources/js/comps/galeria/galeria.html',
        link: function(scope, element, attrs) {
            scope.fonte = attrs.fonte;
            scope.arquivos = $rootScope.registro.arquivoList;
        }
    };
});

galeria.directive('meusArquivos', function($rootScope){
    return {
        restrict: 'E',
        templateUrl: 'resources/js/comps/galeria/meus-arquivos.html',
        link: function(scope, element, attrs) {
            scope.fonte = attrs.fonte;
            scope.arquivos = $rootScope.registro.arquivoList;
        }
    };
});
