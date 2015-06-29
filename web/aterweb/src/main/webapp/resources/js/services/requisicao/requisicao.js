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
 * @date 12/06/2014
 * @modify 29/07/2014
 */

var requisicao = angular.module("requisicaoService", []);
        
requisicao.factory('requisicaoService', function($http, $rootScope, $location, toaster){
    var caminho = baseUrl + PAGINA;
    
    function get(url, params) {
        return $http.get(url, params);
    }
    
    function post(url, params) {
        return $http.post(url, params);
    }
    
    function salvar(params) {
        $rootScope.emProcessamento(true);
        post(caminho + ACAO_SALVAR, params)
        .success(function(data){
            $rootScope.emProcessamento(false);
            if(data.executou) {
                toaster.pop('success', "Salvo", "Registro salvo com sucesso!");
                $location.path('/lista');
                $rootScope.botoesAcao('executar');
            } else {
                toaster.pop('error', "Erro", "Erro ao salvar!");
                console.log(data);
                if(!angular.isObject(data)){
                    $rootScope.erroSessao(data);
                }
            }
        }).error(function(data){
            $rootScope.emProcessamento(false);
            console.log("SALVAR => Ocorreu algum erro!");
            toaster.pop('error', "Erro no servidor", data,0);
            console.log(data);
        });
    };
    
    function restaurar(params) {
        $rootScope.emProcessamento(true);
        try {
            r = get(caminho + ACAO_RESTAURAR, params);
            return r;
        } finally {
            $rootScope.emProcessamento(false);
        }
    };
   
    //VERIFICAR SE EST� CORRETO
    function excluir(params) {
        $rootScope.emProcessamento(true);
        try {
            get(caminho + ACAO_EXCLUIR, params)
            .success(function(data){
                if(data.executou) {
                    toaster.pop('success', null, "Removido com sucesso!");
                } else {
                    toaster.pop('error', "Erro", "Não foi possível remover!");
                }
                return 1;
            }).error(function (data) {
            toaster.pop('error', "Erro", "Erro de parâmetro! > " + data);
        });
        
        } finally {
            $rootScope.emProcessamento(false);
            return 0;
        }
    };
    
    function filtrar(params) {
        $rootScope.botoesAcao('executar');
        $rootScope.linhasSelecionadas = [];
        $rootScope.emProcessamento(true);
        
        get(caminho + ACAO_FILTRAR, params)
        .success(function(data){
            if(data.executou) {
                $rootScope.lista = data.resultado;
                $location.path('/lista');
            } else {
                alert("Não foi encontrado nenhum registro!");
            }
        });
        
        $rootScope.emProcessamento(false);
    };
    
    function dominio(params) {
        return get("dominio", params);
    }
    
    function enumeracao(nomeEnumeracao) {
    	return get("enumeracao", {params: {"nome": nomeEnumeracao}});
    }
    
    function speech(texto) {
        data = "http://translate.google.com/translate_tts?tl=pt-br&q=%22"+texto+"%22";
        document.write('<audio src="'+data+'"><audio>');
    }
    
    return {
        get         : get,
        post        : post,
        salvar      : salvar,
        restaurar   : restaurar,
        excluir     : excluir,
        filtrar     : filtrar,
        dominio     : dominio,
        enumeracao  : enumeracao,
        speech      : speech
    };
});