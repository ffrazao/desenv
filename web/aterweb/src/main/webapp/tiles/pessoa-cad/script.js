var PAGINA = 'pessoa-cad';

function cadastroCtrl($scope,$http,$location,toaster,requisicaoService, FrzNavegadorParams, $modal_b, $modalInstance, registro) {

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

    $scope.acoes = {};
    $scope.acoes.meioContato =  {};   // funções para meiocontato
    $scope.acoes.relacionamento = {}; // funções para relacionamentos    
    
    $scope.emProcessamento(false);
    
    toaster.options = {positionClass : "toast-bottom-right"};
    $scope.selected = [];
    
	var sucessoInterno = function (ls, cb) {
		return function(data) {
			if (data.executou) {
				if (cb) {
					cb(data.resultado);
				} else {
					ls.splice(0, ls.length);
					for (i in data.resultado) {
						ls.push(data.resultado[i]);
					}
				}
			} else {
				erroInterno(data);
			}
		};
	};

	var erroInterno = function(data) {
		if (!isUndefOrNull(data["mensagem"])) {
			toaster.pop('error', 'Erro ao Recuperar Informações', data.mensagem);
		} else {
			document.write(data);
		}
	};
    
    var carregarEnum = function (lista, enumeracao, callback) {
        requisicaoService.enumeracao(enumeracao).success(sucessoInterno(lista, callback)).error(erroInterno);
    };
    
    var carregarDominio = function (lista, params, callback) {
    	requisicaoService.dominio(params).success(sucessoInterno(lista, callback)).error(erroInterno);
    };
    
    if (isUndefOrNull($scope.apoio)) {

        $scope.apoio = {
                camOrgaoList: [],
                cnhCategoriaList: [],
                confirmacaoList: [],
                escolaridadeList: [],
                estadoCivilList: [],
                geracaoList: [],
                meioContatoFinalidadeList: [],
                pessoaSituacaoList: [],
                publicoAlvoCategoriaList: [],
                publicoAlvoSegmentoList: [],
                regimeCasamentoList: [],
                regimeExploracaoList: [],
                sexoList: [],
                situacaoDapList: [],
                
                organizacaoTipoList: [],
                paisList: [],
                pessoaGrupoEstadoViList: [],
                pessoaGrupoMunicipioViList: [],
                pessoaGrupoOrganogramaViList: [],
                pessoaGrupoPaisViList: [],
                profissaoList: [],
                relacionamentoTipoList: [],
                setorList: [],
                
                tradicaoList: [],
        };

        carregarEnum($scope.apoio.camOrgaoList, "CamOrgao");
        carregarEnum($scope.apoio.confirmacaoList, "Confirmacao");
        carregarEnum($scope.apoio.cnhCategoriaList, "CnhCategoria");
        carregarEnum($scope.apoio.escolaridadeList, "Escolaridade");
        carregarEnum($scope.apoio.estadoCivilList, "EstadoCivil");
        carregarEnum($scope.apoio.geracaoList, "Geracao");        
        carregarEnum($scope.apoio.meioContatoFinalidadeList, "MeioContatoFinalidade", function (resultado) {
            var codigo = "";
            var descricao = "";
            $scope.apoio.meioContatoFinalidadeList.splice(0, $scope.apoio.meioContatoFinalidadeList.length);
            for (i in resultado) {
                if (codigo !== "") {
                    codigo+=",";
                }
                if (descricao !== "") {
                    descricao+=" e ";
                }
                codigo+=resultado[i].codigo;
                descricao+=resultado[i].descricao;
                $scope.apoio.meioContatoFinalidadeList.push(resultado[i]);
            }
            $scope.apoio.meioContatoFinalidadeList.push({"codigo": codigo, "descricao": descricao});
        });
        carregarEnum($scope.apoio.pessoaSituacaoList, "PessoaSituacao");
        carregarEnum($scope.apoio.publicoAlvoCategoriaList, "PublicoAlvoCategoria");
        carregarEnum($scope.apoio.publicoAlvoSegmentoList, "PublicoAlvoSegmento");
        carregarEnum($scope.apoio.regimeCasamentoList, "RegimeCasamento");
        carregarEnum($scope.apoio.regimeExploracaoList, "RegimeExploracao");        
        carregarEnum($scope.apoio.situacaoDapList, "SituacaoDap");
        carregarEnum($scope.apoio.sexoList, "Sexo");
        
        carregarDominio($scope.apoio.organizacaoTipoList, {params : { ent : 'OrganizacaoTipo', }});
        carregarDominio($scope.apoio.pessoaGrupoOrganogramaViList, {params : { ent : 'PessoaGrupoOrganogramaVi', order: 'sigla' }});
        carregarDominio($scope.apoio.pessoaGrupoPaisViList, {params : { ent : 'PessoaGrupoPaisVi', }}, function (resultado) {
            $scope.apoio.pessoaGrupoPaisViList.splice(0, $scope.apoio.pessoaGrupoPaisViList.length);
            for (i in resultado) {
                if (resultado[i].sigla === "BR") {
                	$scope.apoio.brasil = resultado[i]; 
                }
                $scope.apoio.pessoaGrupoPaisViList.push(resultado[i]);
            }
        });
        carregarDominio($scope.apoio.profissaoList, {params : { ent : 'Profissao', order: 'nome', }});
        carregarDominio($scope.apoio.relacionamentoTipoList, {params : {ent : 'RelacionamentoTipo', grupoSocial : 'N', }});
        carregarDominio($scope.apoio.setorList, {params : { ent : 'Setor', }});
        
        var anoAtual = (new Date().getFullYear());
        for (var i = anoAtual - 60; i <= anoAtual; i++) {
            $scope.apoio.tradicaoList.push(i);
        }
        
    	$scope.$watch("registro.nascimentoPais.id", function(newValue, oldValue, scope) {
    		if (!isUndefOrNull(newValue) && newValue > 0) {
    	        carregarDominio($scope.apoio.pessoaGrupoEstadoViList, {params : { ent : 'PessoaGrupoEstadoVi', npk: 'pessoaGrupoPaisVi.id', vpk: newValue, }});
    		} else {
    			$scope.apoio.pessoaGrupoEstadoViList.splice(0, $scope.apoio.pessoaGrupoEstadoViList.length);
    		}
    	});
    	
    	$scope.$watch("registro.nascimentoEstado.id", function(newValue, oldValue, scope) {
    		if (!isUndefOrNull(newValue) && newValue > 0) {
    	        carregarDominio($scope.apoio.pessoaGrupoMunicipioViList, {params : { ent : 'PessoaGrupoMunicipioVi', npk: 'pessoaGrupoEstadoVi.id', vpk: newValue, }});
    		} else {
    			$scope.apoio.pessoaGrupoMunicipioViList.splice(0, $scope.apoio.pessoaGrupoMunicipioViList.length);
    		}
    	});	
    }

	$scope.retornaRelacionamentoFuncao = function(id) {
		//console.log(id);
		requisicaoService.dominio({
			params : {
				ent : 'RelacionamentoConfiguracaoVi',
				npk : 'tipo_id',
				vpk : id
			}
		}).success(function(data) {
			if (data.executou) {
				$scope.relacionamentoFuncao.splice(0, $scope.relacionamentoFuncao.length);
				for (i in data.resultado) {
					$scope.relacionamentoFuncao.push(data.resultado[i]);
				}
			} else {
				toaster.pop('error', "ERRO", data);
				console.log('ERROR', data);
			}
		}).error(function(data) {
			toaster.pop('error', "ERRO", data);
			console.log('ERROR', data);
		});
	};
    	
	$scope.$watch("registro.publicoAlvoConfirmacao + registro.colaboradorConfirmacao", function() {
		$scope.tabVisivelBeneficiario(!isUndefOrNull($scope.registro) && !isUndefOrNull($scope.registro.publicoAlvoConfirmacao) && $scope.registro.publicoAlvoConfirmacao === 'S');
		$scope.tabVisivelColaborador(!isUndefOrNull($scope.registro) && !isUndefOrNull($scope.registro.colaboradorConfirmacao) && $scope.registro.colaboradorConfirmacao === 'S');
	});
	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        
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
        var cidade = $scope.copiarObjeto($scope.selectCidades,'id',$scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi.id);
        var municipio = $scope.copiarObjeto($scope.selectMunicipios,'id',$scope.municipioSelecionado.id);
        var estado = $scope.copiarObjeto($scope.selectEstados,'id',$scope.estadoSelecionado.id);
        var pais = $scope.copiarObjeto($scope.selectPaises,'id',$scope.paisSelecionado.id);

        estado.pessoaGrupoPaisVi = pais;
        municipio.pessoaGrupoEstadoVi = estado;
        cidade.pessoaGrupoMunicipioVi = municipio;
        
        $scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi = angular.copy(cidade);
    };
    
    $scope.acoes.meioContato.editar = function(contato){
        
        $scope.acoes.meioContato.original = contato;
        $scope.acoes.meioContato.registro = angular.copy($scope.acoes.meioContato.original);
        
        if($scope.acoes.meioContato.original.meioContato.meioContatoTipo === 'END'){
            var cidade = valorCampoJson($scope.registro,contato.meioContato.pessoaGrupoCidadeVi);
            var municipio = valorCampoJson($scope.registro,cidade.pessoaGrupoMunicipioVi);
            var estado = valorCampoJson($scope.registro,municipio.pessoaGrupoEstadoVi);
            var pais = valorCampoJson($scope.registro,estado.pessoaGrupoPaisVi);

            //$scope.copiarObjeto($scope.selectPaises,$scope.paisSelecionado,'id',pais.id);
            $scope.paisSelecionado.id = pais.id;
            
            $scope.retornaEstados(pais.id);
            //$scope.copiarObjeto($scope.selectEstados,$scope.estadoSelecionado,'id',estado.id);
            $scope.estadoSelecionado.id = estado.id;
            
            $scope.retornaMunicipios(estado.id);
            //$scope.copiarObjeto($scope.selectMunicipios,$scope.municipioSelecionado,'id',municipio.id);
            $scope.municipioSelecionado.id = municipio.id;

            $scope.retornaCidades(municipio.id);
            //$scope.copiarObjeto($scope.selectCidades,$scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi,'id',cidade.id);
            $scope.acoes.meioContato.registro.meioContato.pessoaGrupoCidadeVi = angular.copy(cidade);
        }
    };
    
    $scope.acoes.meioContato.salvar = function(){
        if($scope.acoes.meioContato.original === null){
            $scope.registro.pessoaMeioContatos.push($scope.acoes.meioContato.registro);
        }else{
            angular.copy($scope.acoes.meioContato.registro,$scope.acoes.meioContato.original);
            //$scope.registro.$apply();
        }
        
        delete $scope.acoes.meioContato.original;
        delete $scope.acoes.meioContato.registro;
    };
    
    $scope.acoes.meioContato.remover = function(contato){
        var index = $scope.registro.pessoaMeioContatos.indexOf(contato);
        $scope.registro.pessoaMeioContatos.splice(index,1);
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
        
        var relTipo = valorCampoJson($scope.registro,$scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo);
        var relFuncao = valorCampoJson($scope.registro,$scope.acoes.relacionamento.registro.relacionamentoFuncao);

        //$scope.copiarObjeto($scope.selectPaises,$scope.paisSelecionado,'id',pais.id);
        $scope.acoes.relacionamento.registro.relacionamento.relacionamentoTipo.id = relTipo.id;

        $scope.retornaRelacionamentoFuncao(relTipo.id);
        //$scope.copiarObjeto($scope.selectEstados,$scope.estadoSelecionado,'id',estado.id);
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
            $scope.registro.pessoaRelacionamentos.push($scope.acoes.relacionamento.registro);
        }else{
            angular.copy($scope.acoes.relacionamento.registro,$scope.acoes.relacionamento.original);
            //$scope.registro.$apply();
        }
        
        delete $scope.acoes.relacionamento.original;
        delete $scope.acoes.relacionamento.registro;
    };
    
   $scope.acoes.relacionamento.remover = function(relacionamento){
        var index = $scope.registro.pessoaRelacionamentos.indexOf(relacionamento);
        $scope.registro.pessoaRelacionamentos.splice(index,1);
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
        if($scope.linhasSelecionadas.length === 0) {
            toaster.pop('info', null, "Nenhum registro selecionado!");
        } else {
            id = $scope.linhasSelecionadas[$scope.linhaAtual].id;
            $scope.emProcessamento(true);
            $http.get(baseUrl + "pessoa-cad/restaurar",{params: {id: id}})
            .success(function(data){
                if(!data.executou){
                    toaster.pop('error', "ERRO", data.resultado.message);
                    $scope.emProcessamento(false);
                    return;
                }
                
                //console.log(data);
                $scope.registro = data.resultado;

                if($scope.registro.pessoaTipo === 'PF'){
                    $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
                }else if($scope.registro.pessoaTipo === 'PJ'){
                    $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
                }else{
                    $scope.registro['@class'] = null;
                }
                
                if (isUndefOrNull($scope.registro.nascimentoPais)) {
                	$scope.registro.nascimentoPais = $scope.apoio.brasil;
                }

                angular.forEach($scope.registro.pessoaMeioContatos, function(value, key){
                    if(value.meioContato.meioContatoTipo === 'END'){
                        var cidade = valorCampoJson($scope.registro,value.meioContato.pessoaGrupoCidadeVi);
                        var municipio = valorCampoJson($scope.registro,cidade.pessoaGrupoMunicipioVi);
                        var estado = valorCampoJson($scope.registro,municipio.pessoaGrupoEstadoVi);
                        var pais = valorCampoJson($scope.registro,estado.pessoaGrupoPaisVi);

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
                
                angular.forEach($scope.registro.pessoaRelacionamentos, function(value, key){
                        var relacionamentoTipo = valorCampoJson($scope.registro,value.relacionamento.relacionamentoTipo);
                        var relacionamentoFuncao = valorCampoJson($scope.registro,value.relacionamentoFuncao);
                        var pessoa = valorCampoJson($scope.registro,value.pessoa);

                        value.relacionamento.relacionamentoTipo = angular.copy(relacionamentoTipo);
                        value.relacionamentoFuncao = angular.copy(relacionamentoFuncao);
                        value.pessoa = angular.copy(pessoa);

                });
                
                $scope.botoesAcao('editar');
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
        	$scope.botoesAcao('incluir');
        	
        	$scope.registro = {};
            $scope.registro.nascimentoPais = $scope.apoio.brasil;

        	$scope.registro.pessoaMeioContatos = [];
        	$scope.registro.pessoaRelacionamentos = [];
        	
        	$location.path('/formulario');
        } finally {
            $scope.emProcessamento(false);
        }
    };
    
    $scope.incluirPessoa = function(tipo){
            $scope.registro.pessoaTipo = tipo;
            
            if($scope.registro.pessoaTipo === 'PF'){
                $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
            }else if($scope.registro.pessoaTipo === 'PJ'){
                $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
            }else{
                $scope.registro['@class'] = null;
            }

            //$scope.registro.publicoAlvoConfirmacao = 'S';
            $scope.registro.situacao = 'A';
    };
    
    $scope.salvar = function() {
        $scope.emProcessamento(true);
        if (!$scope.formularioPessoa.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            //angular.forEach($scope.formularioPessoa.$error);
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        
  
        var novo = angular.copy($scope.registro);
        remodelarCampoJson(novo,$scope.registro);
        //removerJsonId($scope.registro);
        removerReferenciasJsonId($scope.registro);
		removerArquivoList($scope.registro);

//				for (reg in $scope.registro["pessoaMeioContatos"]) {
//					
//					$scope.registro["pessoaMeioContatos"][reg] = angular.copy(copiaJsonId($scope.registro, $scope.registro["pessoaMeioContatos"][reg]));
//                                    }

        
        
        console.log($scope.registro);
        requisicaoService.salvar($scope.registro);
        
    };

    $scope.buscarPessoaRelacionamento = function(){
                //$scope.emProcessamento(true);
        window.$scope = $scope;
        window.open(baseUrl+'pessoa-cad/?modo=P', 99, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
    };
    
    $scope.popupSelecionar2 = function() {
        if($scope.linhasSelecionadas.length === 1){
            window.opener.$scope.$apply(function () {
            	var retorno = location.search.split('retorno=')[1];
            	window.opener.$scope[retorno]($scope.linhasSelecionadas);
            });
            // window.close();
        }else{
            toaster.pop('info', null, "Selecione um registro!");
        }
    }
    
    $scope.popupSelecionar = function(){
        if($scope.linhasSelecionadas.length === 1){
            window.opener.$scope.$apply(function () {
                 angular.copy($scope.linhasSelecionadas[0],window.opener.$scope.acoes.relacionamento.registro.pessoa);
            });
            window.close();
        }else{
            toaster.pop('info', null, "Selecione um registro!");
        }
    };
    
    $scope.validarCPF = function(cpf){
        if(cpf === null || cpf === undefined || cpf.length < 14){
            $scope.registro.cpf = '';
        }else{
            if(!$scope.funcaoValidarCPF(cpf)){
                $scope.registro.cpf = '';
                toaster.pop('error', "CPF", "O CPF informado é inválido!");
            }
        }
    };
    
    $scope.validarCNPJ = function(cnpj){
        if(cnpj === null || cnpj === undefined || cnpj.length < 18){
            $scope.registro.cnpj = '';
        }else{
            if(!$scope.funcaoValidarCNPJ(cnpj)){
                $scope.registro.cnpj = '';
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
    
    $('.btnTrocar').tooltip();
    
    $scope.$watch('registro.nascimento', function(newValue, oldValue) {
    	$scope.registro.idade = null;
    	$scope.registro.geracao = null;
    	var nascimento = null;
    	if(newValue instanceof Date) {
            nascimento = newValue;
    	} else {
    		if (newValue === oldValue || newValue === undefined || newValue.length !== 10) {return;}
    		var partes = newValue.split('/');
    		nascimento = new Date(partes[2],partes[1]-1,partes[0]);
    	}
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
		$scope.linhasSelecionadas = [];
		angular.forEach(cadId,function(value, key) {
			$scope.linhasSelecionadas.push({id: value});	
		})
		cadId = null;
		$scope.editar();
	}
    
    if (cadRegistro !== null) {
    	$scope.registro = cadRegistro;
    	cadRegistro = null;
    }
    
    if (cadFiltro !== null) {
    	$scope.filtro = cadFiltro;
    	cadFiltro = null;
    }
    
	$scope.removerArquivo = function(arquivo){
        var index = $scope.registro.arquivoList.indexOf(arquivo);
        $scope.registro.arquivoList.splice(index,1);
    };
    
	// funciona assim, definir uma funcao para chamar a tela
	$scope.apoio.pessoaGrupoIncluir = function() {
		window.$scope = $scope;
		window.open(baseUrl + 'pessoa-grupo-cad/?modo=P&retorno=pessoaGrupoIncluirRetorno', 99, "width=900px,height=600px,top=100px,left=300px,scrollbars=1");
	}
	
	// e outra para tratar o retorno
	$scope.pessoaGrupoIncluirRetorno = function (valor) {
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
	
	$scope.relacionadoNvg = new FrzNavegadorParams();
	
	$scope.relacionadoNvg.scope = $scope;
	
	$scope.modalAbrir = function() {
		$scope.exibirModal = 'FI';
		$scope.relacionadoNvg.mudarEstado('FILTRANDO');
	};
	
	$scope.modalFiltrar = function() {
		$scope.exibirModal = 'FI';
		//$scope.relacionadoNvg.mudarEstado('FILTRANDO');
	}

	$scope.modalConfirmarFiltrar = function() {
		$scope.emProcessamento(true);
		$http.get(baseUrl + PAGINA + ACAO_FILTRAR, {
			params : $scope.filtro
		}).success(
		function(data) {
			$scope.emProcessamento(false);
			if (data.executou) {
				$scope.lista = data.resultado;
				$scope.relacionadoNvg.mudarEstado('LISTANDO');
				$scope.exibirModal = 'LI';
			} else {
				toaster.pop('alert', 'Atenção', "Não foi encontrado nenhum registro!");
			}
		}).error(function(data) {
			$scope.emProcessamento(false);
			toaster.pop('error', 'Erro', "Não foi possível executar a operação!" + data);
		});
	};
	
	$scope.modalVoltar = function () {
		$scope.relacionadoNvg.voltar();
		if ($scope.relacionadoNvg.estadoAtual() === 'FILTRANDO') {
			$scope.exibirModal = 'FI';
		} else {
			$scope.exibirModal = 'LI';
		}		
	}
	
	$scope.modalVisualizar = function () {
        if(!$scope.relacionadoNvg.selecao.item) {
            toaster.pop('info', null, "Nenhum registro selecionado!");
        } else {
            id = $scope.relacionadoNvg.selecao.item.id;
            $scope.emProcessamento(true);
            $http.get(baseUrl + "pessoa-cad/restaurar",{params: {id: id}})
            .success(function(data){
                $scope.emProcessamento(false);
                if(!data.executou){
                    toaster.pop('error', "ERRO", data.resultado.message);
                    return;
                }
                
                //console.log(data);
                $scope.registro = data.resultado;

                if($scope.registro.pessoaTipo === 'PF'){
                    $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaFisica';
                }else if($scope.registro.pessoaTipo === 'PJ'){
                    $scope.registro['@class'] = 'gov.emater.aterweb.model.PessoaJuridica';
                }else{
                    $scope.registro['@class'] = null;
                }

                angular.forEach($scope.registro.pessoaMeioContatos, function(value, key){
                    if(value.meioContato.meioContatoTipo === 'END'){
                        var cidade = valorCampoJson($scope.registro,value.meioContato.pessoaGrupoCidadeVi);
                        var municipio = valorCampoJson($scope.registro,cidade.pessoaGrupoMunicipioVi);
                        var estado = valorCampoJson($scope.registro,municipio.pessoaGrupoEstadoVi);
                        var pais = valorCampoJson($scope.registro,estado.pessoaGrupoPaisVi);

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
                
                angular.forEach($scope.registro.pessoaRelacionamentos, function(value, key){
                        var relacionamentoTipo = valorCampoJson($scope.registro,value.relacionamento.relacionamentoTipo);
                        var relacionamentoFuncao = valorCampoJson($scope.registro,value.relacionamentoFuncao);
                        var pessoa = valorCampoJson($scope.registro,value.pessoa);

                        value.relacionamento.relacionamentoTipo = angular.copy(relacionamentoTipo);
                        value.relacionamentoFuncao = angular.copy(relacionamentoFuncao);
                        value.pessoa = angular.copy(pessoa);

                });
                
        		$scope.exibirModal = 'FO';
        		//$scope.relacionadoNvg.mudarEstado('VISUALIZANDO');
            })
            .error(function(data){
                toaster.pop('error', "ERRO", data);
                console.log('ERROR',data);
            });
        }
    }
	$scope.modalIncluir = function () {
        $scope.emProcessamento(true);
        try {
        	
        	$scope.registro = {};
        	$scope.registro.pessoaMeioContatos = [];
        	$scope.registro.pessoaRelacionamentos = [];
        	
    		$scope.exibirModal = 'FO';
    		$scope.relacionadoNvg.mudarEstado('INCLUINDO');
        } finally {
            $scope.emProcessamento(false);
        }
	}
	
	$scope.modalSalvar = function () {
        $scope.emProcessamento(true);
        if (!$scope.formularioPessoa.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            //angular.forEach($scope.formularioPessoa.$error);
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
  
        var novo = angular.copy($scope.registro);
        remodelarCampoJson(novo, $scope.registro);
        //removerJsonId($scope.registro);
        removerReferenciasJsonId($scope.registro);
		removerArquivoList($scope.registro);
        console.log($scope.registro);

        $scope.emProcessamento(true);
        $http.post(baseUrl + PAGINA + ACAO_SALVAR, $scope.registro)
        .success(function(data){
            $scope.emProcessamento(false);
            if(data.executou) {
                toaster.pop('success', "Salvo", "Registro salvo com sucesso!");
                $scope.relacionadoNvg.mudarEstado('VISUALIZANDO');
            } else {
                toaster.pop('error', "Erro", "Erro ao salvar!");
                console.log(data);
                if(!angular.isObject(data)){
                    $scope.erroSessao(data);
                }
            }
        }).error(function(data){
            $scope.emProcessamento(false);
            console.log("SALVAR => Ocorreu algum erro!");
            toaster.pop('error', "Erro no servidor", data,0);
            console.log(data);
        });
    }
}

function SubEnderecoCtrl($scope, FrzNavegadorParams, $modal_b, $modalInstance, registro, toaster, requisicaoService) {

	$scope.enderecoNvg = new FrzNavegadorParams();
	
	$scope.enderecoNvg.scope = $scope;
	
	if (registro != null) {
		$scope.dados = angular.copy(registro);
	}

	$scope.abrir = function() {
		$scope.enderecoNvg.mudarEstado('ESPECIAL');
		$scope.registro.pessoaMeioContatos = [];
//		for (var i = 0; i < 0; i++) {
//			$scope.novoEndereco();
//			$scope.enderecoK['_a'] = 'I';
//			$scope.registro.pessoaMeioContatos.push($scope.enderecoK);
//		}
		$scope.preparar($scope.registro.pessoaMeioContatos);
	};

	$scope.especial = function() {
		$scope.enderecoNvg.especialBotoesVisiveis([ 'agir', 'editar', 'excluir', 'incluir', 'navegar', 'tamanhoPagina', ]);
	};

	function enderecoModal() {
		var modalInstance = $modal_b.open({
			animation : $scope.animationsEnabled,
//			templateUrl : 'pessoaEnderecoFrm.html',
			templateUrl : 'tiles/pessoa-cad/form-endereco.jsp',
			controller : 'SubEnderecoCtrl',
			size : 'lg',
			resolve : {
				registro : function() {
					return $scope.enderecoK;
				}
			}
		});

		modalInstance.result.then(function(registro) {
			if (!registro) {
				return;
			}
			if (!$scope.registro.pessoaMeioContatos) {
				$scope.registro.pessoaMeioContatos = [];
			}
			var encontrou = false;
			for (var i in $scope.registro.pessoaMeioContatos) {
				if ($scope.registro.pessoaMeioContatos[i]['_s'] === registro['_s']) {
					if (registro['_a'] !== 'N') {
						registro['_a'] = 'A';
					}
					$scope.registro.pessoaMeioContatos[i] = registro;
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$scope.registro.pessoaMeioContatos.push(registro);
			}
		}, function() {
			//console.log('Modal dismissed at: ' + new Date());
		});
	}
	
	$scope.submitted = false;
	
	$scope.excluir = function() {
		if ($scope.enderecoNvg.selecao.tipo === 'U') {
			$scope.enderecoNvg.selecao.item['_a'] = 'E';
		} else if ($scope.enderecoNvg.selecao.tipo === 'M') {
			for (var i in $scope.enderecoNvg.selecao.items) {
				$scope.enderecoNvg.selecao.items[i]['_a'] = 'E';
			}
			$scope.enderecoNvg.selecao.items = [];
		}
		toaster.pop('info', null, "Registro(s) Excluido(s)!");
	};
	
	$scope.incluir = function() {
		$scope.novoEndereco();
		
		enderecoModal();
	};
	
	$scope.editar = function(id) {
		if ($scope.enderecoNvg.selecao.tipo === 'U') {
			$scope.enderecoK = angular.copy($scope.enderecoNvg.selecao.item);
			enderecoModal();
		} else if ($scope.enderecoNvg.selecao.tipo === 'M') {
			for (var i in $scope.enderecoNvg.selecao.items) {
				$scope.enderecoK = angular.copy($scope.enderecoNvg.selecao.items[i]);
				enderecoModal();
			}
			$scope.enderecoNvg.selecao.items = [];
		}
	};

//	$scope.items = [];
//	$scope.selected = {
//		item : $scope.items[0]
//	};

    $scope.processado = false;
    
	$scope.ok = function() {
        $scope.emProcessamento(true);
        if (!$scope.$parent.frmEndereco.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        $scope.emProcessamento(false);
        
    	requisicaoService.dominio({
    		params : {
    			ent : 'PessoaGrupo',
    			npk : 'id',
    			vpk : $scope.enderecoK.pessoaGrupoCidadeVi.id,
    		}
    	}).success(function(data) {
    		if (data.executou) {
        		$scope.enderecoK.pessoaGrupoCidadeVi.nome = data.resultado[0].nome;
        		$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.nome = data.resultado[0].pai.nome;
        		$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.nome = data.resultado[0].pai.pai.nome;
        		$scope.enderecoK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.nome = data.resultado[0].pai.pai.pai.nome;
    		}
    	});
        
    	if ($scope.enderecoK.propriedadeRural && $scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi && $scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi.id) {
        	requisicaoService.dominio({
        		params : {
        			ent : 'PessoaGrupo',
        			npk : 'id',
        			vpk : $scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi.id,
        		}
        	}).success(function(data) {
        		if (data.executou) {
        			$scope.enderecoK.propriedadeRural.pessoaGrupoComunidadeVi.nome = data.resultado[0].nome;
        		}
        	});
    	}
        
    	if ($scope.enderecoK.propriedadeRural && $scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi && $scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id) {
        	requisicaoService.dominio({
        		params : {
        			ent : 'PessoaGrupo',
        			npk : 'id',
        			vpk : $scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id,
        		}
        	}).success(function(data) {
        		if (data.executou) {
        			$scope.enderecoK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.nome = data.resultado[0].nome;
        		}
        	});
    	}
		$modalInstance.close($scope.enderecoK);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.novoEndereco = function() {
		$scope.enderecoK = {};
		$scope.enderecoK["@class"] = "gov.emater.aterweb.model.MeioContatoEndereco";
		$scope.enderecoK['_s'] = $scope.registro.pessoaMeioContatos ? $scope.registro.pessoaMeioContatos.length : 0; 
		$scope.enderecoK['_a'] = 'N'; 
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
};


function SubTelefoneCtrl($scope, FrzNavegadorParams, $modal_b, $modalInstance, registro, toaster, requisicaoService) {

	$scope.telefoneNvg = new FrzNavegadorParams();
	
	$scope.telefoneNvg.scope = $scope;
	
	if (registro != null) {
		$scope.dados = angular.copy(registro);
	}

	$scope.abrir = function() {
		$scope.telefoneNvg.mudarEstado('ESPECIAL');
		$scope.registro.pessoaMeioContatos = [];
//		for (var i = 0; i < 5; i++) {
//			$scope.novoTelefone();
//			$scope.telefoneK['_a'] = 'I';
//			$scope.registro.pessoaMeioContatos.push($scope.telefoneK);
//		}
		$scope.preparar($scope.registro.pessoaMeioContatos);
	};

	$scope.especial = function() {
		$scope.telefoneNvg.especialBotoesVisiveis([ 'agir', 'editar', 'excluir', 'incluir', 'navegar', 'tamanhoPagina', ]);
	};

	function telefoneModal() {
		var modalInstance = $modal_b.open({
			animation : $scope.animationsEnabled,
//			templateUrl : 'pessoaTelefoneFrm.html',
			templateUrl : 'tiles/pessoa-cad/form-telefone.jsp',
			controller : 'SubTelefoneCtrl',
			size : 'lg',
			resolve : {
				registro : function() {
					return $scope.telefoneK;
				}
			}
		});

		modalInstance.result.then(function(registro) {
			if (!registro) {
				return;
			}
			if (!$scope.registro.pessoaMeioContatos) {
				$scope.registro.pessoaMeioContatos = [];
			}
			var encontrou = false;
			for (var i in $scope.registro.pessoaMeioContatos) {
				if ($scope.registro.pessoaMeioContatos[i]['_s'] === registro['_s']) {
					if (registro['_a'] !== 'N') {
						registro['_a'] = 'A';
					}
					$scope.registro.pessoaMeioContatos[i] = registro;
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$scope.registro.pessoaMeioContatos.push(registro);
			}
		}, function() {
			//console.log('Modal dismissed at: ' + new Date());
		});
	}
	
	$scope.submitted = false;
	
	$scope.excluir = function() {
		if ($scope.telefoneNvg.selecao.tipo === 'U') {
			$scope.telefoneNvg.selecao.item['_a'] = 'E';
		} else if ($scope.telefoneNvg.selecao.tipo === 'M') {
			for (var i in $scope.telefoneNvg.selecao.items) {
				$scope.telefoneNvg.selecao.items[i]['_a'] = 'E';
			}
			$scope.telefoneNvg.selecao.items = [];
		}
		toaster.pop('info', null, "Registro(s) Excluido(s)!");
	};
	
	$scope.incluir = function() {
		$scope.novoTelefone();
		
		telefoneModal();
	};
	
	$scope.editar = function(id) {
		if ($scope.telefoneNvg.selecao.tipo === 'U') {
			$scope.telefoneK = angular.copy($scope.telefoneNvg.selecao.item);
			telefoneModal();
		} else if ($scope.telefoneNvg.selecao.tipo === 'M') {
			for (var i in $scope.telefoneNvg.selecao.items) {
				$scope.telefoneK = angular.copy($scope.telefoneNvg.selecao.items[i]);
				telefoneModal();
			}
			$scope.telefoneNvg.selecao.items = [];
		}
	};

//	$scope.items = [];
//	$scope.selected = {
//		item : $scope.items[0]
//	};

    $scope.processado = false;
    
	$scope.ok = function() {
        $scope.emProcessamento(true);
        if (!$scope.$parent.frmTelefone.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        $scope.emProcessamento(false);
        
		$modalInstance.close($scope.dados);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.novoTelefone = function() {
		$scope.telefoneK = {};
		$scope.telefoneK["@class"] = "gov.emater.aterweb.model.MeioContatoTelefone";
		$scope.telefoneK['_s'] = $scope.registro.pessoaMeioContatos ? $scope.registro.pessoaMeioContatos.length : 0; 
		$scope.telefoneK['_a'] = 'N'; 
		// iniciar estrutura
		$scope.telefoneK['numero'] = '61'; 
	}
};

function SubEmailCtrl($scope, FrzNavegadorParams, $modal_b, $modalInstance, registro, toaster, requisicaoService) {

	$scope.emailNvg = new FrzNavegadorParams();
	
	$scope.emailNvg.scope = $scope;
	
	if (registro != null) {
		$scope.dados = angular.copy(registro);
	}

	$scope.abrir = function() {
		$scope.emailNvg.mudarEstado('ESPECIAL');
		$scope.registro.pessoaMeioContatos = [];
//		for (var i = 0; i < 5; i++) {
//			$scope.novoEmail();
//			$scope.emailK['_a'] = 'I';
//			$scope.registro.pessoaMeioContatos.push($scope.emailK);
//		}
		$scope.preparar($scope.registro.pessoaMeioContatos);
	};

	$scope.especial = function() {
		$scope.emailNvg.especialBotoesVisiveis([ 'agir', 'editar', 'excluir', 'incluir', 'navegar', 'tamanhoPagina', ]);
	};

	function emailModal() {
		var modalInstance = $modal_b.open({
			animation : $scope.animationsEnabled,
//			templateUrl : 'pessoaEmailFrm.html',
			templateUrl : 'tiles/pessoa-cad/form-email.jsp',
			controller : 'SubEmailCtrl',
			size : 'lg',
			resolve : {
				registro : function() {
					return $scope.emailK;
				}
			}
		});

		modalInstance.result.then(function(registro) {
			if (!registro) {
				return;
			}
			if (!$scope.registro.pessoaMeioContatos) {
				$scope.registro.pessoaMeioContatos = [];
			}
			var encontrou = false;
			for (var i in $scope.registro.pessoaMeioContatos) {
				if ($scope.registro.pessoaMeioContatos[i]['_s'] === registro['_s']) {
					if (registro['_a'] !== 'N') {
						registro['_a'] = 'A';
					}
					$scope.registro.pessoaMeioContatos[i] = registro;
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$scope.registro.pessoaMeioContatos.push(registro);
			}
		}, function() {
			//console.log('Modal dismissed at: ' + new Date());
		});
	}
	
	$scope.submitted = false;
	
	$scope.excluir = function() {
		if ($scope.emailNvg.selecao.tipo === 'U') {
			$scope.emailNvg.selecao.item['_a'] = 'E';
		} else if ($scope.emailNvg.selecao.tipo === 'M') {
			for (var i in $scope.emailNvg.selecao.items) {
				$scope.emailNvg.selecao.items[i]['_a'] = 'E';
			}
			$scope.emailNvg.selecao.items = [];
		}
		toaster.pop('info', null, "Registro(s) Excluido(s)!");
	};
	
	$scope.incluir = function() {
		$scope.novoEmail();
		
		emailModal();
	};
	
	$scope.editar = function(id) {
		if ($scope.emailNvg.selecao.tipo === 'U') {
			$scope.emailK = angular.copy($scope.emailNvg.selecao.item);
			emailModal();
		} else if ($scope.emailNvg.selecao.tipo === 'M') {
			for (var i in $scope.emailNvg.selecao.items) {
				$scope.emailK = angular.copy($scope.emailNvg.selecao.items[i]);
				emailModal();
			}
			$scope.emailNvg.selecao.items = [];
		}
	};

//	$scope.items = [];
//	$scope.selected = {
//		item : $scope.items[0]
//	};

    $scope.processado = false;
    
	$scope.ok = function() {
        $scope.emProcessamento(true);
        if (!$scope.$parent.frmEmail.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        $scope.emProcessamento(false);
        
		$modalInstance.close($scope.dados);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.novoEmail = function() {
		$scope.emailK = {};
		$scope.emailK["@class"] = "gov.emater.aterweb.model.MeioContatoEmail";
		$scope.emailK['_s'] = $scope.registro.pessoaMeioContatos ? $scope.registro.pessoaMeioContatos.length : 0; 
		$scope.emailK['_a'] = 'N'; 
		// iniciar estrutura
	}
};

function SubRelacionamentoCtrl($scope, FrzNavegadorParams, $modal_b, $modalInstance, registro, toaster, requisicaoService) {

	$scope.relacionamentoNvg = new FrzNavegadorParams();
	
	$scope.relacionamentoNvg.scope = $scope;
	
	if (registro != null) {
		$scope.dados = angular.copy(registro);
	}

	$scope.abrir = function() {
		$scope.relacionamentoNvg.mudarEstado('ESPECIAL');
		$scope.registro.pessoaRelacionamentos = [];
//		for (var i = 0; i < 5; i++) {
//			$scope.novoRelacionamento();
//			$scope.relacionamentoK['_a'] = 'I';
//			$scope.registro.pessoaRelacionamentos.push($scope.relacionamentoK);
//		}
		$scope.preparar($scope.registro.pessoaRelacionamentos);
	};

	$scope.especial = function() {
		$scope.relacionamentoNvg.especialBotoesVisiveis([ 'agir', 'editar', 'excluir', 'incluir', 'navegar', 'tamanhoPagina', ]);
	};

	function relacionamentoModal() {
		var modalInstance = $modal_b.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'tiles/pessoa-cad/modal.jsp',
			controller : 'cadastroCtrl',
			size : 'lg',
			resolve : {
				registro : function() {
					return $scope.relacionamentoK;
				}
			}
		});

		modalInstance.result.then(function(registro) {
			if (!registro) {
				return;
			}
			if (!$scope.registro.pessoaRelacionamentos) {
				$scope.registro.pessoaRelacionamentos = [];
			}
			var encontrou = false;
			for (var i in $scope.registro.pessoaRelacionamentos) {
				if ($scope.registro.pessoaRelacionamentos[i]['_s'] === registro['_s']) {
					if (registro['_a'] !== 'N') {
						registro['_a'] = 'A';
					}
					$scope.registro.pessoaRelacionamentos[i] = registro;
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$scope.registro.pessoaRelacionamentos.push(registro);
			}
		}, function() {
			//console.log('Modal dismissed at: ' + new Date());
		});
	}
	
	$scope.submitted = false;
	
	$scope.excluir = function() {
		if ($scope.relacionamentoNvg.selecao.tipo === 'U') {
			$scope.relacionamentoNvg.selecao.item['_a'] = 'E';
		} else if ($scope.relacionamentoNvg.selecao.tipo === 'M') {
			for (var i in $scope.relacionamentoNvg.selecao.items) {
				$scope.relacionamentoNvg.selecao.items[i]['_a'] = 'E';
			}
			$scope.relacionamentoNvg.selecao.items = [];
		}
		toaster.pop('info', null, "Registro(s) Excluido(s)!");
	};
	
	$scope.incluir = function() {
		$scope.novoRelacionamento();
		
		relacionamentoModal();
	};
	
	$scope.editar = function(id) {
		if ($scope.relacionamentoNvg.selecao.tipo === 'U') {
			$scope.relacionamentoK = angular.copy($scope.relacionamentoNvg.selecao.item);
			relacionamentoModal();
		} else if ($scope.relacionamentoNvg.selecao.tipo === 'M') {
			for (var i in $scope.relacionamentoNvg.selecao.items) {
				$scope.relacionamentoK = angular.copy($scope.relacionamentoNvg.selecao.items[i]);
				relacionamentoModal();
			}
			$scope.relacionamentoNvg.selecao.items = [];
		}
	};

//	$scope.items = [];
//	$scope.selected = {
//		item : $scope.items[0]
//	};

    $scope.processado = false;
    
	$scope.ok = function() {
        $scope.emProcessamento(true);
        if (!$scope.$parent.frmRelacionamento.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
    	}
        $scope.emProcessamento(false);
        
		$modalInstance.close($scope.dados);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.novoRelacionamento = function() {
		$scope.relacionamentoK = {};
		$scope.relacionamentoK["@class"] = "gov.emater.aterweb.model.MeioContatoRelacionamento";
		$scope.relacionamentoK['_s'] = $scope.registro.pessoaRelacionamentos ? $scope.registro.pessoaRelacionamentos.length : 0; 
		$scope.relacionamentoK['_a'] = 'N'; 
		// iniciar estrutura
	}
};

function GrupoSocialCtrl($scope, FrzNavegadorParams, $modal_b, $modalInstance, registro, toaster, requisicaoService) {
    $scope.grupoSocialNvg = new FrzNavegadorParams();
    
    $scope.grupoSocialNvg.scope = $scope;
    
    if (registro != null) {
        $scope.dados = angular.copy(registro);
    }

    $scope.abrir = function() {
        $scope.grupoSocialNvg.mudarEstado('ESPECIAL');
        $scope.registro.pessoaMeioContatos = [];
//      for (var i = 0; i < 0; i++) {
//          $scope.novoGrupoSocial();
//          $scope.grupoSocialK['_a'] = 'I';
//          $scope.registro.pessoaMeioContatos.push($scope.grupoSocialK);
//      }
        $scope.preparar($scope.registro.pessoaMeioContatos);
    };

    $scope.especial = function() {
        $scope.grupoSocialNvg.especialBotoesVisiveis([ 'agir', 'editar', 'excluir', 'incluir', 'navegar', 'tamanhoPagina', ]);
    };

    function grupoSocialModal() {
		var modalInstance = $modal_b.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'tiles/pessoa-grupo-cad/modal.jsp',
			controller : 'cadastroCtrl',
			size : 'lg',
			resolve : {
				registro : function() {
					return $scope.relacionamentoK;
				}
			}
		});

		modalInstance.result.then(function(registro) {
			if (!registro) {
				return;
			}
			if (!$scope.registro.pessoaRelacionamentos) {
				$scope.registro.pessoaRelacionamentos = [];
			}
			var encontrou = false;
			for (var i in $scope.registro.pessoaRelacionamentos) {
				if ($scope.registro.pessoaRelacionamentos[i]['_s'] === registro['_s']) {
					if (registro['_a'] !== 'N') {
						registro['_a'] = 'A';
					}
					$scope.registro.pessoaRelacionamentos[i] = registro;
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				$scope.registro.pessoaRelacionamentos.push(registro);
			}
		}, function() {
			//console.log('Modal dismissed at: ' + new Date());
		});
	}
    
    $scope.submitted = false;
    
    $scope.excluir = function() {
        if ($scope.grupoSocialNvg.selecao.tipo === 'U') {
            $scope.grupoSocialNvg.selecao.item['_a'] = 'E';
        } else if ($scope.grupoSocialNvg.selecao.tipo === 'M') {
            for (var i in $scope.grupoSocialNvg.selecao.items) {
                $scope.grupoSocialNvg.selecao.items[i]['_a'] = 'E';
            }
            $scope.grupoSocialNvg.selecao.items = [];
        }
        toaster.pop('info', null, "Registro(s) Excluido(s)!");
    };
    
    $scope.incluir = function() {
        $scope.novoGrupoSocial();
        
        grupoSocialModal();
    };
    
    $scope.editar = function(id) {
        if ($scope.grupoSocialNvg.selecao.tipo === 'U') {
            $scope.grupoSocialK = angular.copy($scope.grupoSocialNvg.selecao.item);
            grupoSocialModal();
        } else if ($scope.grupoSocialNvg.selecao.tipo === 'M') {
            for (var i in $scope.grupoSocialNvg.selecao.items) {
                $scope.grupoSocialK = angular.copy($scope.grupoSocialNvg.selecao.items[i]);
                grupoSocialModal();
            }
            $scope.grupoSocialNvg.selecao.items = [];
        }
    };

//  $scope.items = [];
//  $scope.selected = {
//      item : $scope.items[0]
//  };

    $scope.processado = false;
    
    $scope.Ok = function() {
        $scope.emProcessamento(true);
        if (!$scope.$parent.frmGrupoSocial.$valid) {
            $scope.emProcessamento(false);
            $scope.submitted = true;
            toaster.pop('error', "Dados incorretos", "Verifique os campos destacados!");
            return;
        }
        $scope.emProcessamento(false);
        
        requisicaoService.dominio({
            params : {
                ent : 'PessoaGrupo',
                npk : 'id',
                vpk : $scope.grupoSocialK.pessoaGrupoCidadeVi.id,
            }
        }).success(function(data) {
            if (data.executou) {
                $scope.grupoSocialK.pessoaGrupoCidadeVi.nome = data.resultado[0].nome;
                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.nome = data.resultado[0].pai.nome;
                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.nome = data.resultado[0].pai.pai.nome;
                $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.nome = data.resultado[0].pai.pai.pai.nome;
            }
        });
        
        if ($scope.grupoSocialK.propriedadeRural && $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi && $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.id) {
            requisicaoService.dominio({
                params : {
                    ent : 'PessoaGrupo',
                    npk : 'id',
                    vpk : $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.id,
                }
            }).success(function(data) {
                if (data.executou) {
                    $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi.nome = data.resultado[0].nome;
                }
            });
        }
        
        if ($scope.grupoSocialK.propriedadeRural && $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi && $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id) {
            requisicaoService.dominio({
                params : {
                    ent : 'PessoaGrupo',
                    npk : 'id',
                    vpk : $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.id,
                }
            }).success(function(data) {
                if (data.executou) {
                    $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.nome = data.resultado[0].nome;
                }
            });
        }
        $modalInstance.close($scope.grupoSocialK);
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    $scope.novoGrupoSocial = function() {
        $scope.grupoSocialK = {};
        $scope.grupoSocialK["@class"] = "gov.emater.aterweb.model.MeioContatoGrupoSocial";
        $scope.grupoSocialK['_s'] = $scope.registro.pessoaMeioContatos ? $scope.registro.pessoaMeioContatos.length : 0; 
        $scope.grupoSocialK['_a'] = 'N'; 
        // iniciar estrutura
        if (isUndefOrNull($scope.grupoSocialK)) {
            $scope.grupoSocialK = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.propriedadeRuralConfirmacao)) {
            $scope.grupoSocialK.propriedadeRuralConfirmacao = "N";
        }
        if (isUndefOrNull($scope.grupoSocialK.pessoaGrupoCidadeVi)) {
            $scope.grupoSocialK.pessoaGrupoCidadeVi = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi)) {
            $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi)) {
            $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi)) {
            $scope.grupoSocialK.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.propriedadeRural)) {
            $scope.grupoSocialK.propriedadeRural = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi)) {
            $scope.grupoSocialK.propriedadeRural.pessoaGrupoComunidadeVi = {};
        }
        if (isUndefOrNull($scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi)) {
            $scope.grupoSocialK.propriedadeRural.pessoaGrupoBaciaHidrograficaVi = {};
        }
    }
};