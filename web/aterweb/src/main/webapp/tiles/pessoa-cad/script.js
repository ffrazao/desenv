var PAGINA = 'pessoa-cad';

function cadastroCtrl($scope,$rootScope,$http,$location,toaster,requisicaoService) {
	

	$scope.tabs = [ 
	{
		'nome' : 'Principal',
		'include' : 'tiles/pessoa-cad/tab-principal.jsp',
		'visivel' : true,
		'ativa'	: true,
	}, {
		'nome' : 'Beneficiário',
		'include' : 'tiles/pessoa-cad/tab-beneficiario.jsp',
		'visivel' : false,
	}, {
		'nome' : 'Colaborador',
		'include' : 'tiles/pessoa-cad/tab-colaborador.jsp',
		'visivel' : false,
	}, {
		'nome' : 'Diagnósticos',
		'include' : 'tiles/pessoa-cad/tab-diagnostico.jsp',
		'visivel' : false,
	}, {
		'nome' : 'Grupos Sociais',
		'include' : 'tiles/pessoa-cad/tab-grupo-social.jsp',
		'visivel' : true,
	}, {
		'nome' : 'Atividades',
		'include' : 'tiles/pessoa-cad/tab-atividade.jsp',
		'visivel' : true,
	}, {
		'nome' : 'Arquivos',
		'include' : 'tiles/pessoa-cad/tab-arquivo.jsp',
		'visivel' : true,
	}, {
		'nome' : 'Pendências',
		'include' : 'tiles/pessoa-cad/tab-pendencia.jsp',
		'visivel' : true,
	}, ];

	$scope.tabVisivelBeneficiario = function(visivel) {
		$scope.tabVisivel('Beneficiário', visivel);
		var outro = $scope.tabVisivel('Colaborador');
		$scope.tabVisivel('Diagnósticos', visivel || outro);
	};

	$scope.tabVisivelColaborador = function(visivel) {
		$scope.tabVisivel('Colaborador', visivel);
		var outro = $scope.tabVisivel('Beneficiário');
		$scope.tabVisivel('Diagnósticos', visivel || outro);
	};

	$scope.tabVisivel = function(tabNome, visivel) {
		for (var t in $scope.tabs) {
			if ($scope.tabs[t].nome === tabNome) {
				if (angular.isDefined(visivel)) {
					$scope.tabs[t].visivel = visivel;
					return;
				} else {
					return $scope.tabs[t].visivel;
				}
			}
		}
	};


	
	$rootScope.enderecoK = {"@class" : "gov.emater.aterweb.model.MeioContatoEndereco"};
	$scope.novoEndereco = function() {
		$scope.enderecoK = {};
		$scope.enderecoK["@class"] = "gov.emater.aterweb.model.MeioContatoEndereco";
		// iniciar estrutura
		if (isUndefOrNull($scope.enderecoK)) {
			$scope.enderecoK = {};
		}
		if (isUndefOrNull($scope.enderecoK.propriedadeRuralConfirmacao)) {
			$scope.enderecoK.propriedadeRuralConfirmacao = "N";
		}
		if (isUndefOrNull($scope.enderecoK.pessoaGrupoCidadeVi)) {
			$scope.enderecoK.pessoaGrupoCidadeVi = {};
		}
		if (isUndefOrNull($scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi)) {
			$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi = {};
		}
		if (isUndefOrNull($scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi)) {
			$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi = {};
		}
		if (isUndefOrNull($scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi)) {
			$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi = {};
		}
		if (isUndefOrNull($scope.enderecoK.propriedadeRural)) {
			$scope.enderecoK.propriedadeRural = {};
		}
		if (isUndefOrNull($scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi)) {
			$scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi = {};
		}
		if (isUndefOrNull($scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi)) {
			$scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi = {};
		}
	}
	$scope.salvarEndereco = function() {
		if (isUndefOrNull($rootScope.registro.pessoaMeioContatos)) {
			$rootScope.registro.pessoaMeioContatos = [];
		}
		$scope.enderecoK["@class"] = "gov.emater.aterweb.model.MeioContatoEndereco";
		$rootScope.registro.pessoaMeioContatos.push({meioContato: $scope.enderecoK});
		$('#endereco').modal('hide');
	}
	$scope.salvarEnderecoCancelar = function() {
		alert('cancel');
	}
    $scope.acoes = {};
    $scope.acoes.meioContato =  {}; //funções para meiocontato
    $scope.acoes.relacionamento = {};//funções para relacionamentos    
    $scope.paisSelecionado = {};
    $scope.estadoSelecionado = {};
    $scope.municipioSelecionado = {};
    
    $scope.emProcessamento(false);
    
    toaster.options = {positionClass : "toast-bottom-right"};
    $scope.selected = [];
	
    $scope.carregarParametros = function(){
    //CARREGAR LISTAS E OPÇÕES
		/*$http.get(baseUrl + "dominio",{params: {ent: 'PessoaGrupo',fetchs: 'filhos'}})
            .success(function(data){
                if(data.executou){
                    for(var i=0; i<data.resultado.length; i++){
                        //console.log(data.resultado[i], /^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i.test(data.resultado[i]));
                        if (/^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i.test(data.resultado[i])) {
                            data.resultado.splice(i, 1);
                        } else {
                            removerReferenciasJsonId(data.resultado[i]);
                        }
                    };
		
                    $scope.defined = data.resultado;

                    //console.log(data.resultado);
                }else{
                    toaster.pop('error', "", 'Erro ao tentar carregar parâmetros: RelacionamentoTipo');
                }
            })
            .error(function(data){
                toaster.pop('error', "Erro no servidor!", data);
                console.log('ERROR',data);
            });
	
        $http.get(baseUrl + "dominio",{params: {ent: 'PessoaGrupo', grupoSocial:'N'}})
            .success(function(data){
                if(data.executou){
                    $scope.relacionamentoTipo = data.resultado;
                }else{
                    toaster.pop('error', "", 'Erro ao tentar carregar parâmetros: RelacionamentoTipo');
                }
            })
            .error(function(data){
                toaster.pop('error', "Erro no servidor!", data);
                console.log('ERROR',data);
            });*/

        $http.get(baseUrl + "dominio",{params: {ent: 'OrganizacaoTipo'}})
            .success(function(data){
                if(data.executou){
                    $scope.organizacaoTipo = data.resultado;
                }else{
                    toaster.pop('error', "", 'Erro ao tentar carregar parâmetros: OrganizacaoTipo!');
                }
            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });

        $http.get(baseUrl + "dominio",{params: {ent: 'Setor'}})
            .success(function(data){
                if(data.executou){
                    $scope.setor = data.resultado;
                }else{
                    toaster.pop('error', "", 'Erro ao tentar carregar parâmetros: Setor!');
                }
            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });
        var anoAtual = (new Date().getFullYear());
        $scope.tradicao = [];
        for(var i = anoAtual - 60; i <= anoAtual; i++){
            $scope.tradicao.push(i);
        }
        
        $scope.retornaPais();
    };
    $scope.carregarParametros();
    
    $scope.retornaRelacionamentoFuncao = function(id){
        console.log(id);
        $http.get(baseUrl + "dominio",{params: {ent: 'RelacionamentoConfiguracaoVi',npk:'tipo_id',vpk:id}})
            .success(function(data){
                $scope.relacionamentoFuncao = data.resultado;

            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });
    };
    
    $scope.copiarObjeto = function(fonte,campo,valor){
        var retorno = null;
        angular.forEach(fonte,function(objeto,chaveObj){
            angular.forEach(objeto,function(value,key){
                if(campo === key && valor === value){
                    retorno = angular.copy(objeto);
                }
            });
        });
        return retorno;
    };
    //

    $scope.atualizaLocalizacoes = function(){
        var cidade = $scope.copiarObjeto($rootScope.selectCidades,'id',$scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi.id);
        var municipio = $scope.copiarObjeto($rootScope.selectMunicipios,'id',$scope.municipioSelecionado.id);
        var estado = $scope.copiarObjeto($rootScope.selectEstados,'id',$scope.estadoSelecionado.id);
        var pais = $scope.copiarObjeto($rootScope.selectPaises,'id',$scope.paisSelecionado.id);

        estado.pessoaGrupoPaisVi = pais;
        municipio.pessoaGrupoEstadoVi = estado;
        cidade.pessoaGrupoMunicipioVi = municipio;
        
        $scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi = angular.copy(cidade);
    };
    
    $scope.acoes.meioContato.editar = function(contato){
        
        $scope.acoes.meioContato.original = contato;
        $scope.acoes.meioContato.registro = angular.copy($scope.acoes.meioContato.original);
        
        if($scope.acoes.meioContato.original.meioContato.meioContatoTipo === 'END'){
            var cidade = valorCampoJson($rootScope.registro,contato.meioContato.pessoaGrupoCidadeVi);
            var municipio = valorCampoJson($rootScope.registro,cidade.pessoaGrupoMunicipioVi);
            var estado = valorCampoJson($rootScope.registro,municipio.pessoaGrupoEstadoVi);
            var pais = valorCampoJson($rootScope.registro,estado.pessoaGrupoPaisVi);

            //$scope.copiarObjeto($rootScope.selectPaises,$scope.paisSelecionado,'id',pais.id);
            $scope.paisSelecionado.id = pais.id;
            
            $scope.retornaEstados(pais.id);
            //$scope.copiarObjeto($rootScope.selectEstados,$scope.estadoSelecionado,'id',estado.id);
            $scope.estadoSelecionado.id = estado.id;
            
            $scope.retornaMunicipios(estado.id);
            //$scope.copiarObjeto($rootScope.selectMunicipios,$scope.municipioSelecionado,'id',municipio.id);
            $scope.municipioSelecionado.id = municipio.id;

            $scope.retornaCidades(municipio.id);
            //$scope.copiarObjeto($rootScope.selectCidades,$scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi,'id',cidade.id);
            $scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi = angular.copy(cidade);
        }
    };
    
    $scope.acoes.meioContato.salvar = function(){
        if($scope.acoes.meioContato.original === null){
            $rootScope.registro.pessoaMeioContatos.push($scope.acoes.meioContato.registro);
        }else{
            angular.copy($scope.acoes.meioContato.registro,$scope.acoes.meioContato.original);
            //$scope.registro.$apply();
        }
        
        delete $scope.acoes.meioContato.original;
        delete $scope.acoes.meioContato.registro;
    };
    
    $scope.acoes.meioContato.remover = function(contato){
        var index = $rootScope.registro.pessoaMeioContatos.indexOf(contato);
        $rootScope.registro.pessoaMeioContatos.splice(index,1);
    };

    $scope.acoes.meioContato.incluir = function(tipo){
        $scope.acoes.meioContato.original = null;
        $scope.acoes.meioContato.registro = {};
        
        $scope.acoes.meioContato.registro.meioContato = {};
        $scope.acoes.meioContato.registro.meioContato.meioContatoTipo = tipo;
        if(tipo === 'TEL'){
            $scope.acoes.meioContato.registro.meioContato['@class'] = 'gov.emater.aterweb.model.MeioContatoTelefonico';
        }else if(tipo === 'END') {
            $scope.acoes.meioContato.registro.meioContato['@class'] = 'gov.emater.aterweb.model.MeioContatoEndereco';
        }else if(tipo === 'EMA') {
            $scope.acoes.meioContato.registro.meioContato['@class'] = 'gov.emater.aterweb.model.MeioContatoEmail';
        }else{
            $scope.acoes.meioContato.registro.meioContato['@class'] = null;
        }
    };
    
    $scope.acoes.relacionamento.editar = function(relacionamento){
        $scope.acoes.relacionamento.original = relacionamento;
        $scope.acoes.relacionamento.registro = angular.copy($scope.acoes.relacionamento.original);
        
        var relTipo = valorCampoJson($rootScope.registro,$scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo);
        var relFuncao = valorCampoJson($rootScope.registro,$scope.acoes.relacionamento.registro.relacionamentoFuncao);

        //$scope.copiarObjeto($rootScope.selectPaises,$scope.paisSelecionado,'id',pais.id);
        $scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo.id = relTipo.id;

        $scope.retornaRelacionamentoFuncao(relTipo.id);
        //$scope.copiarObjeto($rootScope.selectEstados,$scope.estadoSelecionado,'id',estado.id);
        $scope.acoes.relacionamento.registro.relacionamentoFuncao.id = relFuncao.id;
    };
    
    $scope.acoes.relacionamento.salvar = function(){
        if($scope.acoes.relacionamento.registro.pessoa.pessoaTipo === 'PF'){
            $scope.acoes.relacionamento.registro.pessoa['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
        }else if($scope.acoes.relacionamento.registro.pessoa.pessoaTipo === 'PJ'){
            $scope.acoes.relacionamento.registro.pessoa['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
        }else{
            $scope.acoes.relacionamento.registro.pessoa['@class'] = null;
        }

        var relTipo = $scope.copiarObjeto($scope.relacionamentoTipo,'id',$scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo.id);
        var relFuncao = $scope.copiarObjeto($scope.relacionamentoFuncao,'id',$scope.acoes.relacionamento.registro.relacionamentoFuncao.id);

        $scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo = angular.copy(relTipo);
        $scope.acoes.relacionamento.registro.relacionamentoFuncao = angular.copy(relFuncao);
        
		if($scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo.codigo=='PROFISSIONAL'){
			$scope.acoes.relacionamento.registro.relacionamento['@class'] = 'gov.emater.aterweb.model.Emprego';
		}else if ($scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo.codigo=='LOTACAO'){
			$scope.acoes.relacionamento.registro.relacionamento['@class'] = 'gov.emater.aterweb.model.Lotacao';
		}else{
			$scope.acoes.relacionamento.registro.relacionamento['@class'] = 'gov.emater.aterweb.model.Relacionamento';
		}
		
		$scope.acoes.relacionamento.registro.podeModificar = 'N';
		$scope.acoes.relacionamento.registro.proprietario = 'N';
		
        if($scope.acoes.relacionamento.original === null){
            $rootScope.registro.pessoaRelacionamentos.push($scope.acoes.relacionamento.registro);
        }else{
            angular.copy($scope.acoes.relacionamento.registro,$scope.acoes.relacionamento.original);
            //$scope.registro.$apply();
        }
        
        delete $scope.acoes.relacionamento.original;
        delete $scope.acoes.relacionamento.registro;
    };
    
   $scope.acoes.relacionamento.remover = function(relacionamento){
        var index = $rootScope.registro.pessoaRelacionamentos.indexOf(relacionamento);
        $rootScope.registro.pessoaRelacionamentos.splice(index,1);
    };
    
    $scope.acoes.relacionamento.incluir = function(){
        $scope.acoes.relacionamento.original = null;
        $scope.acoes.relacionamento.registro = {};
        $scope.acoes.relacionamento.registro.pessoa = {};
    };
    
    $scope.executar = function(){
        requisicaoService.filtrar({params: $scope.filtro});
    };
    
    $scope.editar = function(){
        if($rootScope.linhasSelecionadas.length === 0) {
            toaster.pop('info', null, "Nenhum registro selecionado!");
        } else {
            id = $rootScope.linhasSelecionadas[$rootScope.linhaAtual].id;
            $scope.emProcessamento(true);
            $http.get(baseUrl + "pessoa-cad/restaurar",{params: {id: id}})
            .success(function(data){
                if(!data.executou){
                    toaster.pop('error', "ERRO", data.resultado.message);
                    $scope.emProcessamento(false);
                    return;
                }
                
                //console.log(data);
                $rootScope.registro = data.resultado;

                if($rootScope.registro.pessoaTipo === 'PF'){
                    $rootScope.registro['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
                }else if($rootScope.registro.pessoaTipo === 'PJ'){
                    $rootScope.registro['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
                }else{
                    $rootScope.registro['@class'] = null;
                }

                angular.forEach($rootScope.registro.pessoaMeioContatos, function(value, key){
                    if(value.meioContato.meioContatoTipo === 'END'){
                        var cidade = valorCampoJson($rootScope.registro,value.meioContato.pessoaGrupoCidadeVi);
                        var municipio = valorCampoJson($rootScope.registro,cidade.pessoaGrupoMunicipioVi);
                        var estado = valorCampoJson($rootScope.registro,municipio.pessoaGrupoEstadoVi);
                        var pais = valorCampoJson($rootScope.registro,estado.pessoaGrupoPaisVi);

                        value.meioContato.pessoaGrupoCidadeVi = angular.copy(cidade);
                        value.meioContato.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi = angular.copy(municipio);
                        value.meioContato.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi = angular.copy(estado);
                        value.meioContato.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi = angular.copy(pais);
    //                    value['@class'] = 'gov.emater.aterweb.model.MeioContatoEndereco';
                    }else if(value.meioContato.meioContatoTipo === 'EMA'){
    //                    value['@class'] = 'gov.emater.aterweb.model.MeioContatoEmail';

                    }else if(value.meioContato.meioContatoTipo === 'TEL'){
    //                    value['@class'] = 'gov.emater.aterweb.model.MeioContatoTelefonico';

                    }else{

                    }
                });
                
                angular.forEach($rootScope.registro.pessoaRelacionamentos, function(value, key){
                        var relacionamentoTipo = valorCampoJson($rootScope.registro,value.relacionamento.relacionamentoTipo);
                        var relacionamentoFuncao = valorCampoJson($rootScope.registro,value.relacionamentoFuncao);
                        var pessoa = valorCampoJson($rootScope.registro,value.pessoa);

                        value.relacionamento.relacionamentoTipo = angular.copy(relacionamentoTipo);
                        value.relacionamentoFuncao = angular.copy(relacionamentoFuncao);
                        value.pessoa = angular.copy(pessoa);

                });
                
                $rootScope.botoesAcao('editar');
                $location.path('/formulario');
                $scope.emProcessamento(false);
            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });
        }
    };
    
    $scope.incluir = function(){
        $scope.emProcessamento(true);
        try {
        	$rootScope.botoesAcao('incluir');
        	
        	$rootScope.registro = {};
        	$rootScope.registro.pessoaMeioContatos = [];
        	$rootScope.registro.pessoaRelacionamentos = [];
        	
        	$location.path('/formulario');
        } finally {
            $scope.emProcessamento(false);
        }
    };
    
    $scope.incluirPessoa = function(tipo){
            $rootScope.registro.pessoaTipo = tipo;
            
            if($rootScope.registro.pessoaTipo === 'PF'){
                $rootScope.registro['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
            }else if($rootScope.registro.pessoaTipo === 'PJ'){
                $rootScope.registro['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
            }else{
                $rootScope.registro['@class'] = null;
            }

            $rootScope.registro.publicoAlvoConfirmacao = 'S';
            $rootScope.registro.situacao = 'A';
    };
    
    $scope.salvar = function() {
        $scope.emProcessamento(true);
        if (!$scope.formularioPessoa.$valid) {
            $scope.emProcessamento(false);
            $rootScope.submitted = true;
            //angular.forEach($scope.formularioPessoa.$error);
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        
  
        var novo = angular.copy($rootScope.registro);
        remodelarCampoJson(novo,$rootScope.registro);
        //removerJsonId($scope.registro);
        removerReferenciasJsonId($rootScope.registro);
		removerArquivoList($rootScope.registro);

//				for (reg in $scope.registro["pessoaMeioContatos"]) {
//					
//					$scope.registro["pessoaMeioContatos"][reg] = angular.copy(copiaJsonId($scope.registro, $scope.registro["pessoaMeioContatos"][reg]));
//                                    }

        
        
        console.log($rootScope.registro);
        requisicaoService.salvar($rootScope.registro);
        
    };

    $scope.buscarPessoaRelacionamento = function(){
                //$scope.emProcessamento(true);
        window.$scope = $scope;
        window.open(baseUrl+'pessoa-cad/?modo=P', 99, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
    };
    
    $rootScope.popupSelecionar2 = function() {
        if($rootScope.linhasSelecionadas.length === 1){
            window.opener.$rootScope.$apply(function () {
            	var retorno = location.search.split('retorno=')[1];
            	window.opener.$rootScope[retorno]($rootScope.linhasSelecionadas);
            });
            // window.close();
        }else{
            toaster.pop('info', null, "Selecione um registro!");
        }
    }
    
    $rootScope.popupSelecionar = function(){
        if($rootScope.linhasSelecionadas.length === 1){
            window.opener.$scope.$apply(function () {
                 angular.copy($rootScope.linhasSelecionadas[0],window.opener.$scope.acoes.relacionamento.registro.pessoa);
            });
            window.close();
        }else{
            toaster.pop('info', null, "Selecione um registro!");
        }
    };
    
    $scope.validarCPF = function(cpf){
        if(cpf === null || cpf === undefined || cpf.length < 14){
            $rootScope.registro.cpf = '';
        }else{
            if(!$scope.funcaoValidarCPF(cpf)){
                $rootScope.registro.cpf = '';
                toaster.pop('error', "CPF", "O CPF informado é inválido!");
            }
        }
    };
    
    $scope.validarCNPJ = function(cnpj){
        if(cnpj === null || cnpj === undefined || cnpj.length < 18){
            $rootScope.registro.cnpj = '';
        }else{
            if(!$scope.funcaoValidarCNPJ(cnpj)){
                $rootScope.registro.cnpj = '';
                toaster.pop('error', "CNPJ", "O CNPJ informado é inválido!");
            }
        }
    };
    
    $scope.funcaoValidarCPF = function(cpf){  
        exp = /\.|\-/g;
        cpf = cpf.toString().replace( exp, "" ); 
        
        if(cpf === '11111111111' || cpf === '22222222222' || cpf === '33333333333' ||
           cpf === '44444444444' || cpf === '55555555555' || cpf === '66666666666' ||
           cpf === '77777777777' || cpf === '88888888888' || cpf === '99999999999' ||
           cpf === '00000000000'){
            return false;
        }
        
        var digitoDigitado = eval(cpf.charAt(9)+cpf.charAt(10));
        var soma1=0, soma2=0;
        var vlr =11;

        for(i=0;i<9;i++){
                soma1+=eval(cpf.charAt(i)*(vlr-1));
                soma2+=eval(cpf.charAt(i)*vlr);
                vlr--;
        }       
        soma1 = (((soma1*10)%11)===10 ? 0:((soma1*10)%11));
        soma2=(((soma2+(2*soma1))*10)%11);

        var digitoGerado=(soma1*10)+soma2;
        if(digitoGerado!==digitoDigitado){
            return false;
        }
        return true;
    };
    
    $scope.funcaoValidarCNPJ = function(cnpj){
        var valida = new Array(6,5,4,3,2,9,8,7,6,5,4,3,2);
        var dig1= new Number;
        var dig2= new Number;
        console.log(cnpj);
        exp = /\.|\-|\//g;
        cnpj = cnpj.toString().replace( exp, "" ).toString(); 
        console.log(cnpj);
        if(cnpj === '11111111111111' || cnpj === '222222222222222' || cnpj === '33333333333333' ||
           cnpj === '44444444444444' || cnpj === '555555555555555' || cnpj === '66666666666666' ||
           cnpj === '77777777777777' || cnpj === '888888888888888' || cnpj === '99999999999999' ||
           cnpj === '00000000000000'){
            return false;
        }

	var valida = new Array(6,5,4,3,2,9,8,7,6,5,4,3,2);
	var dig1= new Number;
	var dig2= new Number;

	var digito = new Number(eval(cnpj.charAt(12)+cnpj.charAt(13)));
		console.log(dig1,dig2,digito);
	for(i = 0; i<valida.length; i++){
		dig1 += (i>0? (cnpj.charAt(i-1)*valida[i]):0);	
		dig2 += cnpj.charAt(i)*valida[i];
                console.log(dig1,dig2);
	}
	dig1 = (((dig1%11)<2)? 0:(11-(dig1%11)));
	dig2 = (((dig2%11)<2)? 0:(11-(dig2%11)));
	console.log('final',dig1,dig2);
	if(((dig1*10)+dig2).toString() !== digito.toString()){
		return false;
        }
        return true;
    };
    
    $('.btnTrocar').tooltip()
    
    $scope.$watch('registro.nascimento', function(newValue, oldValue) {
    	$scope.registro.idade = null;
    	$scope.registro.geracao = null;
        if(newValue === oldValue || newValue === undefined || newValue.length !== 10){return;}
        var partes = newValue.split('/');
        var nascimento = new Date(partes[2],partes[1]-1,partes[0]);
        var hoje = new Date();
        var idade = hoje.getFullYear() - nascimento.getFullYear();
        if ( new Date(hoje.getFullYear(), hoje.getMonth(), hoje.getDate()) < 
                new Date(hoje.getFullYear(), nascimento.getMonth(), nascimento.getDate()) ) {
        	idade--;
        }
        $scope.registro.idade = idade >= 0 ? idade : null;
        if (idade >= 0 && idade < 12) {
            $scope.registro.geracao = "Criança";
        } else if (idade >= 12 && idade < 18) {
            $scope.registro.geracao = "Jovem";
        } else if (idade >= 18 && idade < 60) {
            $scope.registro.geracao = "Adulto";
        } else if (idade >= 60 && idade < 140) {
            $scope.registro.geracao = "Idoso";
        } else {
            $scope.registro.geracao = "Inválido";
        }
    });

//    $scope.$watch('acoes.relacionamento.registro.pessoa',function(newValue,oldValue){
//        if(newValue === oldValue){return;}
//        if(newValue === undefined){return;}
//        $scope.acoes.relacionamento.registro.pessoa = angular.copy(newValue);
//    });

    // codigo comum para verificar se algum id foi informado
    // caso positivo abre a tela em modo edicao de formulario
	if (cadId !== null) {
		$rootScope.linhasSelecionadas = [];
		angular.forEach(cadId,function(value, key) {
			$rootScope.linhasSelecionadas.push({id: value});	
		})
		cadId = null;
		$scope.editar();
	}
    
    if (cadRegistro !== null) {
    	$rootScope.registro = cadRegistro;
    	cadRegistro = null;
    }
    
    if (cadFiltro !== null) {
    	$scope.filtro = cadFiltro;
    	cadFiltro = null;
    }
    
	requisicaoService.dominio({
		params : {
			ent : 'RelacionamentoTipo',
			grupoSocial : 'N'
		}
	}).success(function(data) {
		$scope.relacionamentoTipo = data.resultado;
	}).error(function(data) {
		toaster.pop('error', "ERRO", data);
		console.log('ERROR', data);
	});

	$scope.retornaRelacionamentoFuncao = function(id) {
		console.log(id);
		requisicaoService.dominio({
			params : {
				ent : 'RelacionamentoConfiguracaoVi',
				npk : 'tipo_id',
				vpk : id
			}
		}).success(function(data) {
			$scope.relacionamentoFuncao = data.resultado;
		}).error(function(data) {
			toaster.pop('error', "ERRO", data);
			console.log('ERROR', data);
		});
	};
	
$scope.removerArquivo = function(arquivo){
        var index = $rootScope.registro.arquivoList.indexOf(arquivo);
        $rootScope.registro.arquivoList.splice(index,1);
    };
    
    if (isUndefOrNull($scope.apoio)) {
    	$scope.apoio = {};
    	$scope.apoio.filtroEnderecoListInit = function () {
    		$rootScope.registro.pessoaMeioContatos = [];
    	};
    }

    // carregar as tabelas de apoio ao formulario de cadastro
    if (isUndefOrNull($scope.apoio.meioContatoFinalidadeList)) {
    	requisicaoService.enumeracao("MeioContatoFinalidade").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.meioContatoFinalidadeList = [];
    			var codigo = "";
    			var descricao = "";
    			for (i in data.resultado) {
    				if (codigo !== "") {
    					codigo+=",";
    				}
    				if (descricao !== "") {
    					descricao+=" e ";
    				}
    				codigo+=data.resultado[i].codigo;
    				descricao+=data.resultado[i].descricao;
    				$scope.apoio.meioContatoFinalidadeList.push(data.resultado[i]);
    			}
				$scope.apoio.meioContatoFinalidadeList.push({"codigo": codigo, "descricao": descricao});
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
    
    if (isUndefOrNull($scope.apoio.estadoCivilList)) {
    	requisicaoService.enumeracao("EstadoCivil").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.estadoCivilList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.escolaridadeList)) {
    	requisicaoService.enumeracao("Escolaridade").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.escolaridadeList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.pessoaSituacaoList)) {
    	requisicaoService.enumeracao("PessoaSituacao").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.pessoaSituacaoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.cnhCategoriaList)) {
    	requisicaoService.enumeracao("CnhCategoria").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.cnhCategoriaList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.regimeCasamentoList)) {
    	requisicaoService.enumeracao("RegimeCasamento").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.regimeCasamentoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.camOrgaoList)) {
    	requisicaoService.enumeracao("CamOrgao").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.camOrgaoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.publicoAlvoCategoriaList)) {
    	requisicaoService.enumeracao("PublicoAlvoCategoria").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.publicoAlvoCategoriaList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
	
    if (isUndefOrNull($scope.apoio.publicoAlvoSegmentoList)) {
    	requisicaoService.enumeracao("PublicoAlvoSegmento").success(function(data) {
    		if (data.executou) {
    			$scope.apoio.publicoAlvoSegmentoList = data.resultado;
    		} else {
    			if (!isUndefOrNull(data["mensagem"])) {
    				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
    			} else {
    				document.write(data);
    			}
    		}
    	}).error(function(data) {
			if (!isUndefOrNull(data["mensagem"])) {
				toaster.pop('error','Erro ao Recuperar Informações', data.mensagem);
			} else {
				document.write(data);
			}
    	});
    }
    
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.pessoaGrupoIncluir = function() {
		window.$rootScope = $rootScope;
		window.open(baseUrl + 'pessoa-grupo-cad/?modo=P&retorno=pessoaGrupoIncluirRetorno', 99, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$rootScope.pessoaGrupoIncluirRetorno = function (valor) {
		if (isUndefOrNull($scope.registro.pessoaGrupoSocialList)) {
			$scope.registro.pessoaGrupoSocialList = [];
		}
		angular.forEach(valor, function(value, key) {
			var encontrou = false;
			for (pesq in $scope.registro.pessoaGrupoSocialList) {
				if ($scope.registro.pessoaGrupoSocialList[pesq].pessoa.id === value.id) {
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				var reg = {"pessoa" : value, "nivelGestao": "T"};
		 		$scope.registro.pessoaGrupoSocialList.push(reg);
			}
		});
	}
}