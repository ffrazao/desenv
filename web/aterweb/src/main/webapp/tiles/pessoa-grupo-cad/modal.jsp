<script type="text/javascript" src="tiles/pessoa-grupo-cad/script.js"></script>

<div class="modal-header">
	<h3 class="modal-title">Sele��o de Grupo Social</h3>
</div>
<div class="modal-body">
	<div class="container-fluid">
		
<form name="$parent.formularioPessoaGrupo" novalidate="">
	<fieldset class="row">
		<div id="corpo" class="container-fluid" style="background-color: #FFF;">
			<div class="row">
				<frz-navegador
				  ng-model="relacionadoNvg" exibe-texto-botao="false" 
				  dados="lista"
				  acoes-especiais="acoesEspeciais"
				  on-abrir = "modalAbrir()"
				  on-agir = "modalAgir()"
				  on-editar = "modalEditar()"
				  on-especial = "modalEspecial()"
				  on-excluir = "modalExcluir()"
				  on-filtrar = "modalFiltrar()"
				  on-confirmar-filtrar = "modalConfirmarFiltrar()"
				  on-incluir = "modalIncluir()"
				  on-listar = "modalListar()"
				  on-navegar-primeiro = "modalNavegarPrimeiro()"
				  on-navegar-anterior = "modalNavegarAnterior()"
				  on-navegar-proximo = "modalNavegarPosterior()"
				  on-navegar-ultimo = "modalNavegarUltimo()"
				  on-proxima-pagina="modalProximaPagina()"
				  on-ultima-pagina="modalUltimaPagina()"
				  on-voltar="modalVoltar()"
				  on-visualizar="modalVisualizar()"
				  on-confirmar-incluir = "modalSalvar()"
				  on-confirmar-editar = "modalSalvar()"
				  ></frz-navegador>
			</div>

			<div ng-if="exibirModal === 'FI'" ng-include="'tiles/pessoa-grupo-cad/filtro.jsp'"></div>
			<div ng-if="exibirModal === 'LI'" ng-include="'tiles/pessoa-grupo-cad/listagem.jsp'"></div>
			<div ng-if="exibirModal === 'FO'" ng-include="'tiles/pessoa-grupo-cad/formulario.jsp'"></div>
		</div>

	</fieldset>
</form>

	</div>
</div>
<div class="modal-footer">
	<button class="btn btn-primary" ng-click="grupoSocialOk()">OK</button>
	<button class="btn btn-warning" ng-click="grupoSocialCancelar()">Cancelar</button>
</div>