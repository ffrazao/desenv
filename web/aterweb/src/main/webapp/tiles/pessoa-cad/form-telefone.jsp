<div class="modal-header">
	<h3 class="modal-title">Endereço</h3>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<div class="row">
			<form class="form-horizontal" name="$parent.frmTelefone">
				<fieldset>

	<div class="form-group">
		<label class="col-md-4 control-label" for="numero">Número</label>
		<div class="col-md-6" data-ng-class="{ 'has-error' : $parent.frmTelefone.numero.$invalid && (!$parent.frmTelefone.numero.$pristine || submitted) }">
			<input id="numero" name="numero" type="text" placeholder="numero" class="form-control input-md" data-ng-model="dados.numero" required="true">
			<p data-ng-show="$parent.frmTelefone.numero.$invalid && (!$parent.frmTelefone.numero.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
		</div>
	</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button class="btn btn-primary" ng-click="ok()">OK</button>
	<button class="btn btn-warning" ng-click="cancel()">Cancelar</button>
</div>