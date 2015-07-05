<div class="panel panel-default">
    <div class="panel-heading container-fluid">
    	<!-- [{{telefoneK}}] -->
      <frz-navegador
        ng-model="telefoneNvg" exibe-texto-botao="false" 
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
            <th><frz-seletor ng-model="telefoneNvg" dados="registro.pessoaMeioContatos" ng-hide="true"></frz-seletor></th>
            <th>#</th>
            <th>Finalidade</th>
            <th>Telefone</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaMeioContatos | orderBy: 'nome' | pagina: telefoneNvg.paginaAtual : telefoneNvg.tamanhoPagina | limitTo: telefoneNvg.tamanhoPagina | filter: {'_a': '!E'}">
            <td>
              <input type="radio" ng-show="telefoneNvg.selecao.tipo === 'U'" ng-model="telefoneNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="telefoneNvg.selecao.tipo === 'M'" checklist-model="telefoneNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((telefoneNvg.paginaAtual-1) * telefoneNvg.tamanhoPagina)}}
            </td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.finalidadetelefone.$invalid && (!$parent.formularioPessoa.finalidadetelefone.$pristine || submitted) }">
	            	<select class="form-control" id="finalidadetelefone" name="finalidadetelefone" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select>
					<p data-ng-show="$parent.formularioPessoa.finalidadetelefone.$invalid && (!$parent.formularioPessoa.finalidadetelefone.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
				</div>
            </td>
            <td>
				{{item}}
            </td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>