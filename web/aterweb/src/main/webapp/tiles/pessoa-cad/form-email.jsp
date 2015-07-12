<div class="modal-header">
	<h3 class="modal-title">Email</h3>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<div class="row">
			<form class="form-horizontal" name="$parent.frmEmail">
				<fieldset>

	<div class="form-group">
		<label class="col-md-4 control-label" for="email">Endereço</label>
		<div class="col-md-6" data-ng-class="{ 'has-error' : $parent.frmEmail.email.$invalid && (!$parent.frmEmail.email.$pristine || submitted) }">
			<input type="email" id="email" name="email" placeholder="email" class="form-control input-md" data-ng-model="dados.email" required="true">
			<p data-ng-show="$parent.frmEmail.email.$invalid && (!$parent.frmEmail.email.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
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