<div class="panel panel-default">
    <div class="panel-heading container-fluid">
    	<!-- [{{enderecoK}}] -->
    </div>
    <div class="table-responsible" ng-show="registro.pessoaMeioContatos.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Nome da Propriedade</th>
            <th>Explora��o</th>
            <th>�rea</th>
            <th>Mapa</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaMeioContatos | orderBy: 'nomePropriedadeRuralOuEstabelecimento' | filter: {'@class': 'gov.emater.aterweb.model.MeioContatoEndereco', '_a': '!E', 'propriedadeRuralConfirmacao': 'S'}">
            <td>
				<a href="#">{{item.nomePropriedadeRuralOuEstabelecimento}}</a>
			</td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.exploracao.$invalid && (!$parent.formularioPessoa.exploracao.$pristine || submitted) }">
	            	<select class="form-control" id="exploracao" name="exploracao" data-ng-model="item.exploracao" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.regimeExploracaoList"></select>
					<p data-ng-show="$parent.formularioPessoa.exploracao.$invalid && (!$parent.formularioPessoa.exploracao.$pristine || submitted)" class="help-block">Campo Obrigat�rio!</p>
				</div>
            </td>
            <td>
				<input class="form-control" type="text"/>
            </td>
            <td><img src="./resources/img/mapa.jpg" class="img-thumbnail"></td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>