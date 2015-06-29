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
 * @date 24/06/2014
 * @modify 25/06/2014
 */

var relacionamentos = angular.module("relacionamentos", []);

relacionamentos.directive('relacionamentos', function($rootScope){
    return {
        restrict: 'E',
        templateUrl: 'resources/js/comps/relacionamentos/relacionamentos.html',
        link: function(scope, element, attrs) {
            scope.fonte = attrs.fonte;
            //scope.hectares = attrs.hectares;
            $rootScope.relacionamentos = scope.fonte;
        }
    };
});