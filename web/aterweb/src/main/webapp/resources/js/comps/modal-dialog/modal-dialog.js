/**
 * Exibir conteúdos modais
 */

var modalDialog = angular.module("modalDialog", []);

modalDialog.directive('modalDialog', function($parse) {
	return {
		restrict : 'E',
		//scope : true,
		replace : true,
		transclude : true,
		link : function(scope, element, attrs) {
			if (attrs.width) {
				scope.width = attrs.width;
			}
			if (attrs.identificador) {
				scope.identificador = attrs.identificador; 
			}
			if (attrs.titulo) {
				scope.titulo = attrs.titulo; 
			}
			
			// configurar o metodo de confirmacao de inclusao
			element.find("#salvarEndereco").bind('click', function (e) {
				scope.$apply(function() {
                    var fn = $parse(attrs.confirmacao);
                    fn(scope, {$event : e});
                });
			});
			
			element.find("#salvarEnderecoCancelar").bind('click', function (e) {
				scope.$apply(function(){
                    var fn = $parse(attrs.cancelamento);
                    fn(scope, {$event : e});
                });
			});

		},
		template:
			'<div class="modal fade" id="{{identificador}}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
			'	<div class="modal-dialog" style="width:{{width}}">' +
			'		<div class="modal-content">' +
			'			<div class="modal-header">' +
			'				<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
			'					<span aria-hidden="true">&times;</span>' +
			'				</button>' +
			'				<h4 class="modal-title" id="myModalLabel">{{titulo}}</h4>' +
			'			</div>' +
			'			<div class="modal-body">' +
			'				<div data-ng-transclude>' +
			'				</div>' +
			'			</div>' +
			'			<div class="modal-footer">' +
			'				<button type="button" class="btn btn-default" data-dismiss="modal" id="salvarEnderecoCancelar">Fechar</button>' +
			'				<button type="button" class="btn btn-primary" id="salvarEndereco">Salvar</button>' +
			'			</div>' +
			'		</div>' +
			'	</div>' +
			'</div>'
	};
});