aterweb.factory('meioContatoService', function($http){
    var caminho = baseUrl + "dominio";
    
    function getPaises() {
        return $http.get(caminho, {params: {ent: 'LocalizacaoPaisVi'}});
    }
    
    function getEstados(paisId) {
        return $http.get(caminho, {params: {ent: 'LocalizacaoEstadoVi', npk: 'localizacaoPaisVi.id', vpk: paisId}});
    }
    
    function getMunicipios(estadoId) {
        return $http.get(caminho, {params: {ent: 'LocalizacaoMunicipioVi', npk: 'localizacaoEstadoVi.id', vpk: estadoId}});
    }
    
    function getCidades(municipioId) {
        return $http.get(caminho, {params: {ent: 'LocalizacaoCidadeVi', npk: 'localizacaoMunicipioVi.id', vpk: municipioId}});
    };
    
    return {
        getPaises: getPaises,
        getEstados: getEstados,
        getMunicipios: getMunicipios,
        getCidades: getCidades
    };
});