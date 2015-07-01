/**
 * Codigo de habilitaçao do angular js
 */

var aterweb = angular.module("aterweb",
		[ 
		  "toaster"
		, "ngRoute"
		, "ngResource"
		, "ngAnimate"
		, "uiGmapgoogle-maps"
		, "galeria"
		, "modalDialog"
		, "endereco"
		, "meioContatoService"
		, "relacionamentos"
		, "requisicaoService"
		, "upload"
		, "angularTreeview"
		, "ui.tree"
		, "ui.calendar"
		, "ui.bootstrap"
		, "ui.utils"
		, "bgDirectives"
        , "frz.navegador"
		]);

aterweb.config(function($routeProvider, uiGmapGoogleMapApiProvider) {
	var filtroUrl = "/";
	var filtroTempl = "tiles/" + nomeModulo + "/filtro.jsp";
	var listaUrl = "/lista";
	var listaTempl = "tiles/" + nomeModulo + "/listagem.jsp";
	var formularioUrl = "/formulario";
	var formularioTempl = "tiles/" + nomeModulo + "/formulario.jsp";
	
//	if (cadOpcao.indexOf("FI") < 0) {
//		filtroTempl = "tiles/_modelo/vazio.jsp";
//	}
//	if (cadOpcao.indexOf("LI") < 0) {
//		listaTempl = "tiles/_modelo/vazio.jsp";
//	}
//	if (cadOpcao.indexOf("FO") < 0) {
//		formularioTempl = "tiles/_modelo/vazio.jsp";
//	}

	$routeProvider.when(filtroUrl, {
		templateUrl : filtroTempl,
		controller : "cadastroCtrl"
	}).when(listaUrl, {
		templateUrl : listaTempl,
		controller : "cadastroCtrl"
	}).when(formularioUrl, {
		templateUrl : formularioTempl,
		controller : "cadastroCtrl"
	}).otherwise({
		redirectTo : "/"
	});
	
	// config google maps
	uiGmapGoogleMapApiProvider.configure({
		//    key: 'your api key',
		v: '3.17',
		libraries: 'weather,geometry,visualization'
	});
});

aterweb.run(function(toaster, $rootScope, $http, $location, meioContatoService, $templateCache) {

	$templateCache.removeAll();
	
	$rootScope.emProcessamento = function(processando) {
    	$rootScope.processando = processando;
    };
   
    $rootScope.barraFerramentasBotoes = ['btn_ferramenta_executar', 'btn_ferramenta_filtrar', 
                                         'btn_ferramenta_incluir','btn_ferramenta_editar','btn_ferramenta_excluir', 
                                         'btn_ferramenta_salvar', 'btn_ferramenta_cancelar', 'btn_ferramenta_acoes', 
                                         'btn_ferramenta_exportar'];
    $rootScope.maquinaEstados = {
        filtrar : [ $rootScope.barraFerramentasBotoes[0], 
                    $rootScope.barraFerramentasBotoes[2] ],
        
        executar: [ $rootScope.barraFerramentasBotoes[1], 
                    $rootScope.barraFerramentasBotoes[2], 
                    $rootScope.barraFerramentasBotoes[3], 
                    $rootScope.barraFerramentasBotoes[4] ],
        
        editar  : [ $rootScope.barraFerramentasBotoes[1], 
                    $rootScope.barraFerramentasBotoes[2], 
                    $rootScope.barraFerramentasBotoes[4], 
                    $rootScope.barraFerramentasBotoes[5], 
                    $rootScope.barraFerramentasBotoes[6], 
                    $rootScope.barraFerramentasBotoes[7] ],
        
        incluir : [ $rootScope.barraFerramentasBotoes[1], 
                    $rootScope.barraFerramentasBotoes[4], 
                    $rootScope.barraFerramentasBotoes[5], 
                    $rootScope.barraFerramentasBotoes[6], 
                    $rootScope.barraFerramentasBotoes[7] ],
                
        agenda: [ $rootScope.barraFerramentasBotoes[1],
                  $rootScope.barraFerramentasBotoes[5]            
        ]
    };
    
    $rootScope.baseUrl = baseUrl;
    
    $rootScope.usuarioLogado = USUARIO_LOGADO;
        
    $rootScope.processando          = false;
    
    $rootScope.submitted            = false;
    
    $rootScope.tamanhoPagina		= 10;
    
    $rootScope.tamanhoMaxArquivo	= 10485760; // 10 MB
    
    $rootScope.filtro               = {};
    $rootScope.filtro.numeroPagina  = 1;
    $rootScope.lista                = {};
    $rootScope.registro             = {};
    
    $rootScope.linhaSelecionada     = {};
    $rootScope.linhasSelecionadas   = [];
    $rootScope.linhaAtual           = 0;
    $rootScope.tipoSelecao          = 'radio';
    
    $rootScope.selectPaises         = [];
    $rootScope.selectEstados        = [];
    $rootScope.selectMunicipios     = [];
    $rootScope.selectCidades        = [];
    $rootScope.selectComunidades    = [];
    $rootScope.comunidadesBacias    = [];
    
    $rootScope.arquivosUpload       = [];
    
    $rootScope.modalExcluir         = [];
	
	$rootScope.agenda         		= [[]];
    
//FIM ESCOPO ========================================================================================================================
    if(window.name === 'erroSessao' && window.location.pathname !== '/aterweb/login'){
        window.close();
    }
    
    $rootScope.botoesAcao = function(estado) {
        $rootScope.estadoAtual = estado;
        $rootScope.submitted = false;
        $(".btn-ferramenta").hide();
        angular.forEach($rootScope.maquinaEstados[$rootScope.estadoAtual], function(data){
            $("#"+data).show();
        });
    };
    
    if (!isUndefOrNull(nomeModulo) && nomeModulo.trim().length > 0 && isUndefOrNull($rootScope.estadoAtual)) {
    	$rootScope.botoesAcao('filtrar');
    	$location.path('/');
    }

    /*
     * FIXME arrumar este item pois estava dando erro na barra de menu 
      if($rootScope.estadoAtual === null ){
        $rootScope.botoesAcao('filtrar');
    }
    
    */

    $rootScope.split = function(string, val, index) {
        $rootScope.array = string.split(val);
        return $rootScope.result = $rootScope.array[index];
    };
    
    $rootScope.filtrar = function() {
        $rootScope.botoesAcao('filtrar');
        $rootScope.emProcessamento(true);
        try {
        	$location.path('/filtro');
        } finally {
        	$rootScope.emProcessamento(false);
        }
    };    
    
    $rootScope.cancelar = function() {
        $location.path('/lista');
        delete $rootScope.registro;
        $rootScope.botoesAcao('executar');
    };
    
    $rootScope.modalExcluir = function() {
        $rootScope.modalExcluir.nome = '';
    };
    
    $rootScope.excluir = function() {
        var id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
        
        requisicaoService.excluir({params: {id: id}})
        .success(function(data){
            if(data.executou) {
                alert("Excluido com sucesso!");
            } else {
                alert("Erro ao excluir!");
                console.log(data);
            }
        })
        .error(function(){
            console.log("Algum erro ao excluir!");
        });
    };
    
    $rootScope.alterarSelecao = function(){
        angular.element('.selecionado').removeClass('selecionado');
         
        if($rootScope.tipoSelecao === 'radio'){
            $rootScope.tipoSelecao = 'checkbox';
        }else{
            $rootScope.tipoSelecao = 'radio';
        }
        $rootScope.linhaSelecionada = {};
        $rootScope.linhasSelecionadas = [];
    };
    
    $rootScope.retornaPais = function() {
        $rootScope.selectPaises = [];
        meioContatoService.getPaises().success(function(data){
            angular.forEach(data.resultado, function(value, i) {
                $rootScope.selectPaises.push(value);
            });
        });
    };
    
    $rootScope.retornaEstados = function(paisId) {
        $rootScope.selectEstados = [];
        meioContatoService.getEstados(paisId).success(function(data){
            angular.forEach(data.resultado, function(value, i) {
                $rootScope.selectEstados.push(value);
            });
        });
    };
    
    $rootScope.retornaMunicipios = function(estadoId) {
        $rootScope.selectMunicipios = [];
        meioContatoService.getMunicipios(estadoId).success(function(data){
            angular.forEach(data.resultado, function(value, i) {
                $rootScope.selectMunicipios.push(value);
            });
        });
    };
    
    $rootScope.retornaCidades = function(municipioId) {
        $rootScope.selectCidades = [];
        meioContatoService.getCidades(municipioId).success(function(data){
            angular.forEach(data.resultado, function(value, i) {
                $rootScope.selectCidades.push(value);
            });
        });
    };
    
    $rootScope.retornaComunidades = function() {
        $rootScope.selectComunidades = [];
        meioContatoService.getComunidades().success(function(data){
            angular.forEach(data.resultado, function(value, i) {
                $rootScope.selectComunidades.push(value);
            });
        });
    };
    
    $rootScope.retornaComunidadeBacia = function(cidadeId) {
        //$http.get(baseUrl + PAGINA + '/restaurarComunidadeBaciaHidrografica', {params: {id: cidadeId}})
        meioContatoService.getComunidadesBacias(PAGINA, cidadeId)
        .success(function(data){
            //$rootScope.comunidades = data.resultado.comunidadesList;
            //$rootScope.bacias = data.resultado.baciaHidrograficaList;
            $rootScope.comunidadesBacias = data.resultado;
        });        
    };
    
    $rootScope.retornaEnderecoPorCep = function(cep, campo) {
        campo.descricao = "Carregando...";
        
        $.getJSON("http://kws.kamos.com.br/cep/" + cep, function(data){
            if(data) {
                campo.descricao = data.logradouro;
                $rootScope.$digest();
            }   
       });
    };
    
    $rootScope.selecionou = function(linha) {       
        //console.log(linha);
        if($rootScope.tipoSelecao === 'radio') {
            $rootScope.linhasSelecionadas = [linha];
        } else {
            if($rootScope.linhasSelecionadas.length === 0) {
                $rootScope.linhasSelecionadas.push(linha);
            } else {
                angular.forEach($rootScope.linhasSelecionadas, function(data, i){
                    if(data.id === linha.id) {
                        $rootScope.linhasSelecionadas.splice(i, 1);
                    } else {
                        $rootScope.linhasSelecionadas.push(linha);
                    }
                });
            }
        }
    };   
    
    $rootScope.selecionado = function(linha, event) {
        //console.log(linha);
        //console.log(event);
        $(event).click();
        if($rootScope.tipoSelecao === 'radio') {
            //$rootScope.linhaSelecionada = linha.id;
            $rootScope.linhasSelecionadas = [linha];
             angular.element('.selecionado').removeClass('selecionado');
             angular.element(event.currentTarget).addClass('selecionado');
             
            if(linha.latitude != undefined) {
                initialize(linha.latitude, linha.longitude);
            }
        }
    };
    
    $rootScope.popupCancelar = function(){
        window.close();
    };
    
    $rootScope.erroSessao = function(data){
        if(data.indexOf('LoginCtrl')<0){
            toaster.pop('error','ERRO', data);
        }else{
            toaster.pop('error','Sua sessão expirou','Clique aqui para logar novamente, seus dados serão perdidos!',0,'',
            function(){
                window.location.reload(true);
            });
        }
       //window.open(baseUrl+'login', 'erroSessao', "width=900px,height=600px,top=100px,left=300px,scrollbars=1"); 
    };
    
    $rootScope.tornarImagemPerfil = function(id) {
        angular.forEach($rootScope.registro.arquivoList, function(data){
            data.perfil = 'N';
            if(data.id === id) {
                data.perfil = 'S';
            }
        });
    };

});
aterweb.filter("sanitize", [ '$sce', function($sce) {
	return function(htmlCode) {
		return $sce.trustAsHtml(htmlCode);
	};
} ]);
aterweb.filter('offset', function() {
	return function(input, offset, size) {
		offset = parseInt(offset, 10) * size;
		if (isUndefOrNull(input)) {
			return;
		}
		return input.slice(offset - size, offset);
	};
});