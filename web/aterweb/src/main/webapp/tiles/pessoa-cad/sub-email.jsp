<div class="panel panel-default">
    <div class="panel-heading container-fluid">
    	<!-- [{{emailK}}] -->
      <frz-navegador
        ng-model="emailNvg" exibe-texto-botao="false" 
        dados="registro.pessoaMeioContatos"
        acoes-especiais="acoesEspeciais"
        on-abrir = "abrir()"
        on-agir = "agir()"
        on-editar = "editar()"
        on-especial = "especial()"
        on-excluir = "excluir()"
        on-filtrar = "filtrar()"
        on-incluir = "incluir()"
        on-listar = "listar()"
        on-navegar-primeiro = "navegarPrimeiro()"
        on-navegar-anterior = "navegarAnterior()"
        on-navegar-proximo = "navegarPosterior()"
        on-navegar-ultimo = "navegarUltimo()"
        on-proxima-pagina="proximaPagina()"
        on-ultima-pagina="ultimaPagina()"
        ></frz-navegador>
    </div>
    <div class="painel-body"  ng-hide="registro.pessoaMeioContatos.length">
      <div class="alert alert-warning" role="alert">
        <span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
        Nenhum registro localizado!
      </div>
    </div>
    <div class="table-responsible" ng-show="registro.pessoaMeioContatos.length > 0">
      
      <table class="table table-striped">
        <thead>
          <tr>
            <th><frz-seletor ng-model="emailNvg" dados="registro.pessoaMeioContatos" ng-hide="true"></frz-seletor></th>
            <th>#</th>
            <th>Finalidade</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaMeioContatos | orderBy: 'nome' | pagina: emailNvg.paginaAtual : emailNvg.tamanhoPagina | limitTo: emailNvg.tamanhoPagina | filter: {'@class': 'gov.emater.aterweb.model.MeioContatoEmail'}">
            <td>
              <input type="radio" ng-show="emailNvg.selecao.tipo === 'U'" ng-model="emailNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="emailNvg.selecao.tipo === 'M'" checklist-model="emailNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((emailNvg.paginaAtual-1) * emailNvg.tamanhoPagina)}}
            </td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.finalidadeemail.$invalid && (!$parent.formularioPessoa.finalidadeemail.$pristine || submitted) }">
	            	<select class="form-control" id="finalidadeemail" name="finalidadeemail" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select>
					<p data-ng-show="$parent.formularioPessoa.finalidadeemail.$invalid && (!$parent.formularioPessoa.finalidadeemail.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
				</div>
            </td>
            <td>
				{{item.email}}
            </td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>