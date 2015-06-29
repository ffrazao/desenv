<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Teste Master Detail com o Angular JS</title>
</head>
<body data-ng-app="teste" data-ng-controller="Ctrl">
    <div class="" style="background-color: gray; width: 50%; max-width: 500px; min-width:400px; display: block; margin-top: 3cm; margin-left: auto;margin-right: auto;">
    XPTO abcd<br><br>das
    </div>

	<h1>Master</h1>
	<table id="masterTbl">
		<thead>
			<tr>
				<td>Id</td>
				<td>Nome</td>
			</tr>
		</thead>
		<tbody>
			<tr data-ng-repeat="itemM in master" data-ng-click="seleciona(itemM.id)">
				<td>{{itemM.id}}</td>
				<td>{{itemM.nome}}</td>
			</tr>
		</tbody>
	</table>
	<h1>Detail</h1>
	<table>
		<thead>
			<tr>
				<td>Id</td>
				<td>Nome</td>
			</tr>
		</thead>
		<tbody>
			<tr data-ng-repeat="itemD in selecionado">
				<td>{{itemD.id}}</td>
				<td>{{itemD.nome}}</td>
			</tr>
		</tbody>
	</table>
/{{endereco | json}}/
	<table>
	<tr>
	<td>País</td>
	<td>
		<select data-ng-model="endereco.pais.id" data-ng-options="item.id as item.nome for item in lista.paisList" data-ng-change="endereco.estado.id = null; endereco.municipio.id = null; endereco.cidade.id = null; endereco.baciaHidrografica.id = null; endereco.comunidade.id = null"></select>
	</td>
	</tr>
	<tr>
	<td>Estado</td>
	<td>
		<select data-ng-model="endereco.estado.id" data-ng-options="item.id as item.nome for item in lista.estadoList" data-ng-change="endereco.municipio.id = null; endereco.cidade.id = null; endereco.baciaHidrografica.id = null; endereco.comunidade.id = null"></select>
	</td>
	</tr>
	<tr>
	<td>Município</td>
	<td>
		<select data-ng-model="endereco.municipio.id" data-ng-options="item.id as item.nome for item in lista.municipioList" data-ng-change="endereco.cidade.id = null; endereco.baciaHidrografica.id = null; endereco.comunidade.id = null"></select>
	</td>
	</tr>
	<tr>
	<td>Cidade</td>
	<td>
		<select data-ng-model="endereco.cidade.id" data-ng-options="item.id as item.nome for item in lista.cidadeList" data-ng-change="endereco.baciaHidrografica.id = null; endereco.comunidade.id = null"></select>
	</td>
	</tr>
	<tr>
	<td>Bacia Hidrografica</td>
	<td>
		<select data-ng-model="endereco.baciaHidrografica.id" data-ng-options="item.xId as item.xNome for item in lista.baciaHidrograficaList"></select>
	</td>
	</tr>
	<tr>
	<td>Comunidade</td>
	<td>
		<select data-ng-model="endereco.comunidade.id" data-ng-options="item.xId as item.xNome for item in lista.comunidadeList"></select>
	</td>
	</tr>
	</table>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.5/angular.min.js"></script>
<script type="text/javascript" src="resources/js/angular-resource.js"></script>
<script type="text/javascript" src="resources/js/main-script.js"></script>

<script>
var teste = angular.module('teste', ["ngResource"]);
teste.controller('Ctrl', function($scope, $http) {

	var dominio = "/aterweb/dominio";
	
	if (isUndefOrNull($scope.endereco)) {
		$scope.endereco = {
			"pais" : {
				"id" : 16261
			},
			"estado" : {
				"id" : 19014
			},
			"municipio" : {
				"id" : 19015
			},
			"cidade" : {
				"id" : 19034
			},
			"baciaHidrografica" : {
				"id" : 34266
			},
			"comunidade" : {
				"id" : 34052
			}
		};
	}

	$scope.getDominio = function(entidade, nomePrimaryKey, valorPrimaryKey,
			lista) {
		lista.splice(0, lista.length);
		$http.get(dominio, {
			params : {
				ent : entidade,
				npk : nomePrimaryKey,
				vpk : valorPrimaryKey
			}
		}).success(function(data, status, headers, config) {
			if (!isUndefOrNull(data.resultado)) {
				for (reg in data.resultado) {
					lista.push(data.resultado[reg]);
				}
			}
		}).error(function(data) {
			console.log(data);
		}, true);
	};
	$scope.atualizaPais = function(lista) {
		$scope.getDominio("PessoaGrupoPaisVi", null, null, lista);
	};
	$scope.atualizaEstado = function(lista, paiId) {
		$scope.getDominio("PessoaGrupoEstadoVi", "pessoaGrupoPaisVi.id",
				paiId, lista);
	};
	$scope.atualizaMunicipio = function(lista, paiId) {
		$scope.getDominio("PessoaGrupoMunicipioVi",
				"pessoaGrupoEstadoVi.id", paiId, lista);
	};
	$scope.atualizaCidade = function(lista, paiId) {
		$scope.getDominio("PessoaGrupoCidadeVi",
				"pessoaGrupoMunicipioVi.id", paiId, lista);
	};
	$scope.atualizaBaciaHidrografica = function(lista, paiId) {
		$scope.getDominio("PessoaRelacionamentoCidadeBaciaHidrograficaVi",
				"cidId", paiId, lista);
	};
	$scope.atualizaComunidade = function(lista, paiId) {
		$scope.getDominio("PessoaRelacionamentoCidadeComunidadeVi",
				"cidId", paiId, lista);
	};

	if (isUndefOrNull($scope.lista)) {
		$scope.lista = {
			paisList : [],
			estadoList : [],
			municipioList : [],
			cidadeList : [],
			comunidadeList : [],
			baciaHidrograficaList : []
		};
		$scope.atualizaPais($scope.lista.paisList);
	}

	// ativar o atualizador de endereço
	$scope.$watch("endereco.pais.id", function(newValue, oldValue, scope) {
		console.log("pais mudou");
		$scope.atualizaEstado($scope.lista.estadoList, $scope.endereco.pais.id);
	});

	$scope.$watch("endereco.estado.id", function(newValue, oldValue, scope) {
		console.log("estado mudou");
		$scope.atualizaMunicipio($scope.lista.municipioList, $scope.endereco.estado.id);
	});

	$scope.$watch("endereco.municipio.id", function(newValue, oldValue, scope) {
		console.log("municipio mudou");
		$scope.atualizaCidade($scope.lista.cidadeList, $scope.endereco.municipio.id);
	});

	$scope.$watch("endereco.cidade.id", function(newValue, oldValue, scope) {
		console.log("cidade mudou");
		$scope.atualizaComunidade($scope.lista.comunidadeList, $scope.endereco.cidade.id);
		$scope.atualizaBaciaHidrografica($scope.lista.baciaHidrograficaList, $scope.endereco.cidade.id);
	});

		//$(document).on("click","#masterTbl tbody tr", function() {
		//console.log("entrou 2");
		//$scope.seleciona($(this).children("td").eq(0).text());
		//});
		$scope.seleciona = function(pai) {
			console.log("asiiisss");
			$scope.selecionado = new Array();
			$.each($scope.detail, function(key, val) {
				if (pai === val.pai) {
					$scope.selecionado.push(val);
				}
			});
		}
		$scope.master = [ {
			id : 1,
			nome : "teste1"
		}, {
			id : 2,
			nome : "teste2"
		}, {
			id : 3,
			nome : "teste3"
		} ];
		$scope.detail = [ {
			id : 1,
			nome : "teste1sub1",
			pai : 1
		}, {
			id : 2,
			nome : "teste1sub2",
			pai : 1
		}, {
			id : 3,
			nome : "teste1sub3",
			pai : 1
		}, {
			id : 4,
			nome : "teste2sub1",
			pai : 2
		}, {
			id : 5,
			nome : "teste2sub2",
			pai : 2
		}, {
			id : 6,
			nome : "teste2sub3",
			pai : 2
		}, {
			id : 7,
			nome : "teste3sub1",
			pai : 3
		}, {
			id : 8,
			nome : "teste3sub2",
			pai : 3
		}, {
			id : 9,
			nome : "teste3sub3",
			pai : 3
		} ];

		$scope.selecionado = null;

	});
</script>
</html>