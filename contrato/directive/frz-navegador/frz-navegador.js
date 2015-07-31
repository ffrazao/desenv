(function() {
	'use strict';

	var frzNavegadorModule = angular.module('frz.navegador', []);

	frzNavegadorModule.factory('FrzNavegadorParams', function() {
		var FrzNavegadorParams = function () {
			this.selecao = { tipo: 'U', checked: false, items: [], item: null, selecionado: false };
			this.scope = null;

			this.tamanhoPagina = 10;
			this.paginaAtual = 1;
			this.folhaAtual = 0;

			this.especialBotoesVisiveis = function (botoes) {
				for (var e in this.scope.estados) {
					if (e === 'ESPECIAL') {
						console.log(this.scope.estados[e]);
						this.scope.estados[e].visivel = botoes;
					}
				}
			};
			this.mudarEstado = function (novoEstado) {
				this.scope.mudarEstado(novoEstado);
			};
			this.limparEstados = function () {
				this.scope.historicoEstados = [];
			};
			this.estadoAtual = function() {
				return this.scope.historicoEstados[this.scope.historicoEstados.length - 1];
			};
			this.voltar = function() {
				this.scope.historicoEstados.pop();
			};
		};
		return FrzNavegadorParams;
	});

	// filtro para promover a paginação
	frzNavegadorModule.filter('pagina', function() {
		return function(lista, pagina, tamanho) {
			if (!angular.isObject(lista)) {
				return;
			}
			pagina = parseInt(pagina, 10) * parseInt(tamanho, 10);
			return lista.slice(pagina - tamanho, pagina);
		};
	});

	frzNavegadorModule.controller('FrzNavegadorCtrl', ['$scope', 'FrzNavegadorParams', 'toastr', function($scope, FrzNavegadorParams, toastr) {

		if ($scope.ngModel && !$scope.ngModel.hasOwnProperty('scope')) {
			$scope.ngModel = new FrzNavegadorParams();
			$scope.ngModel.isNullInstance = true;
		}
		if ($scope.ngModel && !$scope.ngModel.scope) {
			$scope.ngModel.scope = $scope;
		}

		var iniciarBotoes = function() {
			$scope.botoes = {
				'agir': {visivel: false, desabilitado: false},
				'cancelar': {visivel: false, desabilitado: false},
				'confirmar': {visivel: false, desabilitado: false},
				'editar': {visivel: false, desabilitado: false},
				'excluir': {visivel: false, desabilitado: false},
				'filtrar': {visivel: false, desabilitado: false},
				'incluir': {visivel: false, desabilitado: false},
				'limpar': {visivel: false, desabilitado: false},
				'navegar': {visivel: false, desabilitado: false},
				'restaurar': {visivel: false, desabilitado: false},
				'tamanhoPagina': {visivel: false, desabilitado: false},
				'visualizar': {visivel: false, desabilitado: false},
				'voltar': {visivel: false, desabilitado: false}
			};
		};

		iniciarBotoes();

		$scope.estados = {
			'ABRINDO': {
				executar: $scope.onAbrir,
				mudarEstado: false,
			},
			'FILTRANDO': {
				executar: $scope.onFiltrar,
				mudarEstado: true,
				visivel: [
				'agir',
				'confirmar',
				'incluir',
				'limpar',
				'voltar',
				],
				desabilitado: []
			},
			'LISTANDO': {
				executar: $scope.onListar,
				mudarEstado: true,
				visivel: [
				'agir',
				'excluir',
				'filtrar',
				'incluir',
				'navegar',
				'tamanhoPagina',
				'visualizar'
				],
				desabilitado: []
			},
			'VISUALIZANDO': {
				executar: $scope.onVisualizar,
				mudarEstado: true,
				visivel: [
				'agir',
				'editar',
				'excluir',
				'filtrar',
				'incluir',
				'navegar',
				'voltar'
				],
				desabilitado: []
			},
			'INCLUINDO': {
				executar: $scope.onIncluir,
				mudarEstado: true,
				visivel: [
				'cancelar',
				'confirmar',
				'limpar'
				],
				desabilitado: []
			},
			'EDITANDO': {
				executar: $scope.onEditar,
				mudarEstado: true,
				visivel: [
				'cancelar',
				'confirmar',
				'restaurar'
				],
				desabilitado: []
			},
			'EXCLUINDO': {
				executar: $scope.onExcluir,
				mudarEstado: true,
				visivel: [
				'cancelar',
				'confirmar'
				],
				desabilitado: []
			},
			'AGINDO': {
				executar: $scope.onAgir,
				mudarEstado: false,
			},
			'LIMPANDO': {
				executar: $scope.onLimpar,
				mudarEstado: false,
			},
			'RESTAURANDO': {
				executar: $scope.onRestaurar,
				mudarEstado: false,
			},
			'NAVEGANDO': {
				executar: $scope.onNavegar,
				mudarEstado: false,
			},
			'FOLHEANDO': {
				executar: $scope.onVisualizar,
				mudarEstado: false,
			},
			'VOLTANDO': {
				executar: $scope.onVoltar,
				mudarEstado: false,
			},
			'CONFIRMANDO': {
				executar: function () {
					switch ($scope.ngModel.estadoAtual()) {
						case 'FILTRANDO': $scope.ngModel.mudarEstado('CONFIRMANDO_FILTRO'); break;
						case 'INCLUINDO': $scope.ngModel.mudarEstado('CONFIRMANDO_INCLUSAO'); break;
						case 'EDITANDO': $scope.ngModel.mudarEstado('CONFIRMANDO_EDICAO'); break;
						case 'EXCLUINDO': $scope.ngModel.mudarEstado('CONFIRMANDO_EXCLUSAO'); break;
					}
				},
				mudarEstado: false,
			},
			'CONFIRMANDO_FILTRO': {
				executar: $scope.onConfirmarFiltrar,
				mudarEstado: false,
			},
			'CONFIRMANDO_INCLUSAO': {
				executar: $scope.onConfirmarIncluir,
				mudarEstado: false,
			},
			'CONFIRMANDO_EDICAO': {
				executar: $scope.onConfirmarEditar,
				mudarEstado: false,
			},
			'CONFIRMANDO_EXCLUSAO': {
				executar: $scope.onConfirmarExcluir,
				mudarEstado: false,
			},
			'CANCELANDO': {
				executar: function () {
					switch ($scope.ngModel.estadoAtual()) {
						case 'FILTRANDO': $scope.ngModel.mudarEstado('CANCELANDO_FILTRO'); break;
						case 'INCLUINDO': $scope.ngModel.mudarEstado('CANCELANDO_INCLUSAO'); break;
						case 'EDITANDO': $scope.ngModel.mudarEstado('CANCELANDO_EDICAO'); break;
						case 'EXCLUINDO': $scope.ngModel.mudarEstado('CANCELANDO_EXCLUSAO'); break;
					}
				},
				mudarEstado: false,
			},
			'CANCELANDO_FILTRO': {
				executar: $scope.onCancelarFiltrar,
				mudarEstado: false,
			},
			'CANCELANDO_INCLUSAO': {
				executar: $scope.onCancelarIncluir,
				mudarEstado: false,
			},
			'CANCELANDO_EDICAO': {
				executar: $scope.onCancelarEditar,
				mudarEstado: false,
			},
			'CANCELANDO_EXCLUSAO': {
				executar: $scope.onCancelarExcluir,
				mudarEstado: false,
			},
			'ESPECIAL': {
				executar: $scope.onEspecial,
				mudarEstado: true,
				visivel: [
				'agir',
				'cancelar',
				'confirmar',
				'editar',
				'excluir',
				'filtrar',
				'incluir',
				'limpar',
				'navegar',
				'restaurar',
				'tamanhoPagina',
				'visualizar',
				'voltar',
				],
			},
		};

		$scope.historicoEstados = [];

		$scope.mudarEstado = function (novoEstado) {
			try {
				$scope.estados[novoEstado].executar();
				if ($scope.estados[novoEstado].mudarEstado && $scope.ngModel.estadoAtual() !== 'ESPECIAL') {
					// esconder botoes
					iniciarBotoes();
					// tornar botões visiveis
					var botao = null;
					for (botao in $scope.estados[novoEstado].visivel) {
						$scope.botoes[$scope.estados[novoEstado].visivel[botao]].visivel = true;
					}
					// desabilitar botoes
					for (botao in $scope.estados[novoEstado].desabilitado) {
						$scope.botoes[$scope.estados[novoEstado].desabilitado[botao]].desabilitado = false;
					}
					if (novoEstado !== $scope.ngModel.estadoAtual()) {
						$scope.historicoEstados.push(novoEstado);
					}
				}
			} catch (erro) {
				toastr.error('Erro ao executar a operação!', erro);
				console.error('Erro ao executar a operação!', erro);
			}
		};

		$scope.temAcoesEspeciais = function() {
			if ($scope.acoesEspeciais && $scope.acoesEspeciais.length) {
				var e = $scope.ngModel.estadoAtual();
				for (var acao in $scope.acoesEspeciais) {
					for (var est in $scope.acoesEspeciais[acao].estado) {
						if ($scope.acoesEspeciais[acao].estado[est] === e) {
							if ($scope.acoesEspeciais[acao].selecaoAtiva) {
								return $scope.ngModel.selecao.selecionado;
							} else {
								return true;
							}
						}
					}
				}
			}
			return false;
		};

		$scope.acaoEspecial = function(item) {
			try {
				$scope.onAgir();
				item.acao();
			} catch (erro) {
				toastr.error('Erro ao executar a operação!', erro);
				console.error('Erro ao executar a operação!', erro);
			}
		};

		$scope.botaoNavegarVisivel = function () {
			if (!$scope.ngModel) {return;}
			var e = $scope.ngModel.estadoAtual();
			return $scope.botoes.navegar.visivel && (e !== 'VISUALIZANDO' || (e === 'VISUALIZANDO' && $scope.ngModel.selecao.tipo === 'M')) && $scope.dados && $scope.dados.length > 0;
		};

		$scope.botaoVoltarVisivel = function () {
			return $scope.botoes.voltar.visivel && $scope.historicoEstados.length > 1;
		};

		var getUltimaPagina = function() {
			if (!$scope.dados) {
				return 0;
			}
			var result = parseInt($scope.dados.length / $scope.ngModel.tamanhoPagina, 10);
			if ($scope.dados.length % $scope.ngModel.tamanhoPagina > 0) {
				result++;
			}
			return result;
		};

		var navegar = function (sentido) {
			var novaPagina = 0, ultimaPagina = getUltimaPagina();
			switch(sentido) {
				case 'primeiro':
				novaPagina = 1;
				break;
				case 'anterior':
				novaPagina = $scope.ngModel.paginaAtual - 1;
				break;
				case 'proximo':
				novaPagina = $scope.ngModel.paginaAtual + 1;
				break;
				case 'ultimo':
				novaPagina = ultimaPagina;
				$scope.onUltimaPagina();
				break;
			}
			novaPagina = (novaPagina < 1) ? 1 : novaPagina;
			$scope.ngModel.paginaAtual = novaPagina;
			if (novaPagina > ultimaPagina) {
				$scope.ngModel.paginaAtual = ultimaPagina;
				$scope.onProximaPagina();
			}
			$scope.ngModel.mudarEstado('NAVEGANDO');
		};

		var folhear = function (sentido) {
			var folha = $scope.ngModel.folhaAtual;
			switch(sentido) {
				case 'primeiro':
				for (folha = 0; folha < $scope.ngModel.selecao.items.length; folha++) {
					if (angular.isObject($scope.ngModel.selecao.items[folha])) {
						$scope.ngModel.folhaAtual = parseInt(folha);
						break;
					}
				}
				break;
				case 'anterior': 
				for (folha = parseInt($scope.ngModel.folhaAtual) - 1; folha >= 0; folha--) {
					if (angular.isObject($scope.ngModel.selecao.items[folha])) {
						$scope.ngModel.folhaAtual = parseInt(folha);
						break;
					}
				}
				break;
				case 'proximo': 
				for (folha = parseInt($scope.ngModel.folhaAtual) + 1; folha < $scope.ngModel.selecao.items.length; folha++) {
					if (angular.isObject($scope.ngModel.selecao.items[folha])) {
						$scope.ngModel.folhaAtual = parseInt(folha);
						break;
					}
				}
				break;
				case 'ultimo': 
				for (folha = $scope.ngModel.selecao.items.length - 1; folha >= 0; folha--) {
					if (angular.isObject($scope.ngModel.selecao.items[folha])) {
						$scope.ngModel.folhaAtual = parseInt(folha);
						break;
					}
				}
				break;
			}
			$scope.ngModel.mudarEstado('FOLHEANDO');
		};

		var vaiPara = function(sentido) {
			var e = $scope.ngModel.estadoAtual();
			if (e === 'LISTANDO') {
				navegar(sentido);
			} else if (e === 'VISUALIZANDO') {
				folhear(sentido);
			}
		};

		$scope.primeiro = function() {
			vaiPara('primeiro');
		};

		$scope.anterior = function() {
			vaiPara('anterior');
		};

		$scope.proximo = function() {
			vaiPara('proximo');
		};

		$scope.ultimo = function() {
			vaiPara('ultimo');
		};

	}]);

	// diretiva da barra de navegação de dados
	frzNavegadorModule.directive('frzNavegador', function() {
		return {
			require: ['^ngModel', '?dados', '?acoesEspeciais'],
			scope: {
				ngModel: '=',
				dados: '=',
				acoesEspeciais: '=',
				onAbrir: '&',
				onAgir: '&',
				onCancelarEditar: '&',
				onCancelarExcluir: '&',
				onCancelarFiltrar: '&',
				onCancelarIncluir: '&',
				onConfirmarEditar: '&',
				onConfirmarExcluir: '&',
				onConfirmarFiltrar: '&',
				onConfirmarIncluir: '&',
				onEditar: '&',
				onEspecial: '&',
				onExcluir: '&',
				onFiltrar: '&',
				onIncluir: '&',
				onLimpar: '&',
				onListar: '&',
				onNavegar: '&',
				onFolhear: '&',
				onRestaurar: '&',
				onVisualizar: '&',
				onVoltar: '&',
				onProximaPagina: '&',
				onUltimaPagina: '&',
			},
			restrict: 'E', 
			replace: true,
			controller: 'FrzNavegadorCtrl',
			link: function(scope, element, attributes) {
				scope.exibeTextoBotao = angular.isUndefined(attributes.exibeTextoBotao) || (attributes.exibeTextoBotao.toLowerCase() === 'true');
				// executar o estado inicial do navegador
				if (scope.ngModel) {scope.ngModel.mudarEstado('ABRINDO');}


				scope.botaoExibeTexto = true;
				scope.grupoBotoes = [
					{
						codigo: 'g1',
						ordem: 1,
						visivel: true,
						desabilitado: false,
						botoes: [{
								tipo: 'dropdown',
								codigo: 'b1',
								nome: 'xddd',
								ordem: 1,
								visivel: true,
								desabilitado: false,
								descricao: 'abcd dos bt dos bt1',
								classe: 'btn-warn',
								acao: function() {console.log('abc')},
								glyphicon: 'glyphicon-share-alt',
								subFuncoes: [
								{
									nome: 10, 
									acao: function() {console.log('sub acao click ' + this.nome)},
								}, 
								{
									nome: 25, 
									acao: function() {console.log('sub acao click ' + this.nome)},
								}, 
								{
									nome: 50, 
									acao: function() {console.log('sub acao click ' + this.nome)},
								}, 
								{
									nome: 100, 
									acao: function() {console.log('sub acao click ' + this.nome)},
								}, 
								]
							},
							{
								nome: 'b2',
								ordem: 2,
								visivel: true,
								desabilitado: false,
								classe: 'btn btn-sm btn-info',
								titulo: 'bt dos bt2',
								acao: function() {console.log('abc2')},
								glyphicon: 'glyphicon glyphicon-ok',
								etiqueta: 'abcd dos bt dos bt2',
							},
						],
					}, 
					{
						codigo: 'g2',
						ordem: 2,
						visivel: true,
						desabilitado: false,
						botoes: [
							{
								tipo: 'normal',
								codigo: 'b3',
								nome: 'bt dos bt3',
								descricao: 'abcd dos bt dos bt3',
								ordem: 1,
								visivel: true,
								desabilitado: true,
								classe: 'btn-info',
								glyphicon: 'glyphicon-ok',
								acao: function() {console.log('abc3')},
							},
							{
								tipo: 'normal',
								nome: 'b4',
								ordem: 2,
								visivel: true,
								desabilitado: false,
								classe: 'btn btn-sm btn-info',
								titulo: 'bt dos bt4',
								acao: function() {console.log('abc4')},
								glyphicon: 'glyphicon glyphicon-share-alt',
								etiqueta: 'abcd dos bt dos bt4',
							},
						],
					}, 
				];

			},
			templateUrl: 'directive/frz-navegador/frz-navegador.html',
		};
	});

	frzNavegadorModule.directive('frzSeletor', function() {
		return {
			template: 
			'<span>' +
			'	<button class="btn btn-default btn-xs" title="Multiseleção" ng-click="ngModel.selecao.tipo = \'M\'" ng-show="ngModel.selecao.tipo === \'U\'">' +
			'		<span class="glyphicon glyphicon-check" aria-hidden="true"></span>' +
			'	</button>' +
			'	<button class="btn btn-default btn-xs" title="Seleção Única" ng-click="ngModel.selecao.tipo = \'U\'" ng-show="ngModel.selecao.tipo === \'M\'">' +
			'		<span class="glyphicon glyphicon-record" aria-hidden="true"></span>' +
			'	</button>' +
			'	<input type="checkbox" ng-model="ngModel.selecao.checked" ng-show="ngModel.selecao.tipo === \'M\'" title="Marcar/Desmarcar Todos" ng-click="marcarElementos(ngModel.selecao.checked);"/>' +
			'</span>',
			restrict: 'E',
			require: ['^ngModel', '?dados'],
			scope: {
				ngModel: '=',
				dados: '=',
			},
			controller: ['$scope', function($scope) {
				$scope.marcarElementos = function(checked) {
					$scope.ngModel.selecao.items = checked ? angular.copy($scope.dados): [];
				};
			}],
			link: function (scope, element) {
				scope.$watch('ngModel.selecao.tipo', function() {
					if (!scope.ngModel || !scope.ngModel.selecao) {return;}
					if (scope.ngModel.selecao.tipo === 'U') {
						scope.ngModel.selecao.selecionado = scope.ngModel.selecao.item && angular.isDefined(scope.ngModel.selecao.item) && scope.ngModel.selecao.item;
					} else if (scope.ngModel.selecao.tipo === 'M') {
						scope.ngModel.selecao.selecionado = scope.ngModel.selecao.marcado > 0;				
					}
				}, true);

				scope.$watch('ngModel.selecao.item', function() {
					if (!scope.ngModel || !scope.ngModel.selecao) {return;}
					if (scope.ngModel.selecao.tipo === 'U') {
						scope.ngModel.selecao.selecionado = scope.ngModel.selecao.item && angular.isDefined(scope.ngModel.selecao.item) && scope.ngModel.selecao.item;
					}
				}, true);

				scope.$watch('ngModel.selecao.items', function() {
					if (!scope.ngModel || !scope.ngModel.selecao) {return;}
					if (!scope.ngModel.selecao.items) {
						return;
					}
					var marcado = 0, desmarcado = 0, total = scope.dados ? scope.dados.length : 0;
					out: for (var item in scope.dados) {
						for (var sel in scope.ngModel.selecao.items) {
							if (angular.equals(scope.dados[item], scope.ngModel.selecao.items[sel])) {
								marcado ++;
								continue out;
							}
						}
						desmarcado ++;
					}
					if ((desmarcado === 0) || (marcado === 0)) {
						scope.ngModel.selecao.checked = (marcado === total);
					}
					scope.ngModel.selecao.marcado = marcado;
					scope.ngModel.selecao.desmarcado = desmarcado;

					if (scope.ngModel.selecao.tipo === 'M') {
						scope.ngModel.selecao.selecionado = scope.ngModel.selecao.marcado > 0;
					}

					// grayed checkbox
					element.find('input[type=checkbox]').prop('indeterminate', (marcado !== 0 && desmarcado !== 0));
				}, true);
			},
		};
	});

	frzNavegadorModule.directive('checklistModel', ['$parse', '$compile', function($parse, $compile) {
		// contains
		function contains(arr, item, comparator) {
			if (angular.isArray(arr)) {
				for (var i = arr.length; i--;) {
					if (comparator(arr[i], item)) {
						return true;
					}
				}
			}
			return false;
		}

		// add
		function add(arr, item, comparator) {
			arr = angular.isArray(arr) ? arr : [];
			if(!contains(arr, item, comparator)) {
				arr.push(item);
			}
			return arr;
		}  

		// remove
		function remove(arr, item, comparator) {
			if (angular.isArray(arr)) {
				for (var i = arr.length; i--;) {
					if (comparator(arr[i], item)) {
						arr.splice(i, 1);
						break;
					}
				}
			}
			return arr;
		}

		// http://stackoverflow.com/a/19228302/1458162
		function postLinkFn(scope, elem, attrs) {
			// compile with `ng-model` pointing to `checked`
			$compile(elem)(scope);

			// getter / setter for original model
			var getter = $parse(attrs.checklistModel);
			var setter = getter.assign;
			var checklistChange = $parse(attrs.checklistChange);

			// value added to list
			var value = $parse(attrs.checklistValue)(scope.$parent);


			var comparator = angular.equals;

			if (attrs.hasOwnProperty('checklistComparator')){
				comparator = $parse(attrs.checklistComparator)(scope.$parent);
			}

			// watch UI checked change
			scope.$watch('checked', function(newValue, oldValue) {
				if (newValue === oldValue) { 
					return;
				} 
				var current = getter(scope.$parent);
				if (newValue === true) {
					setter(scope.$parent, add(current, value, comparator));
				} else {
					setter(scope.$parent, remove(current, value, comparator));
				}

				if (checklistChange) {
					checklistChange(scope);
				}
			});

			// declare one function to be used for both $watch functions
			function setChecked(newArr, oldArr) {
				scope.checked = contains(newArr, value, comparator);
			}

			// watch original model change
			// use the faster $watchCollection method if it's available
			if (angular.isFunction(scope.$parent.$watchCollection)) {
				scope.$parent.$watchCollection(attrs.checklistModel, setChecked);
			} else {
				scope.$parent.$watch(attrs.checklistModel, setChecked, true);
			}
		}

		return {
			restrict: 'A',
			priority: 1000,
			terminal: true,
			scope: true,
			compile: function(tElement, tAttrs) {
				if (tElement[0].tagName !== 'INPUT' || tAttrs.type !== 'checkbox') {
					throw 'frz-checklist-model deve ser usado em `input[type="checkbox"]`.';
				}

				if (!tAttrs.checklistValue) {
					throw 'Faltou informar `checklist-value`.';
				}

				// exclude recursion
				tElement.removeAttr('checklist-model');

				// local scope var storing individual checkbox model
				tElement.attr('ng-model', 'checked');

				return postLinkFn;
			}
		};
	}]);

}());