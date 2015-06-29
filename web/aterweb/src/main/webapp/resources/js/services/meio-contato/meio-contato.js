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
 * @modify 09/06/2014
 */

var meioContatoService = angular.module("meioContatoService", [] );
        
meioContatoService.factory('meioContatoService', function($http){
    var caminho = baseUrl + "dominio";
    
    function getPaises() {
        return $http.get(caminho, {params: {ent: 'PessoaGrupoPaisVi'}});
    }
    
    function getEstados(paisId) {
        return $http.get(caminho, {params: {ent: 'PessoaGrupoEstadoVi', npk: 'pessoaGrupoPaisVi.id', vpk: paisId}});
    }
    
    function getMunicipios(estadoId) {
        return $http.get(caminho, {params: {ent: 'PessoaGrupoMunicipioVi', npk: 'pessoaGrupoEstadoVi.id', vpk: estadoId}});
    }
    
    function getCidades(municipioId) {
        return $http.get(caminho, {params: {ent: 'PessoaGrupoCidadeVi', npk: 'pessoaGrupoMunicipioVi.id', vpk: municipioId}});
    };
    
    function getComunidades() {
        return $http.get(caminho + "?ent=PessoaGrupoComunidadeVi");
    };
    
    function getComunidadesBacias(pagina, cidadeId) {
        return $http.get(baseUrl + pagina + '/restaurarComunidadeBaciaHidrografica', {params: {id: cidadeId}});
    };
    
    return {
        getPaises            : getPaises,
        getEstados           : getEstados,
        getMunicipios        : getMunicipios,
        getCidades           : getCidades,
        getComunidades       : getComunidades,
        getComunidadesBacias : getComunidadesBacias
    };
});