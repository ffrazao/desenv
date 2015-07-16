<div class="panel panel-default">
    <div class="panel-heading container-fluid">
    	<!-- [{{relacionamentoK}}] -->
      <frz-navegador
        ng-model="relacionamentoNvg" exibe-texto-botao="false" 
        dados="registro.pessoaRelacionamentos"
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
    <div class="painel-body"  ng-hide="registro.pessoaRelacionamentos.length">
      <div class="alert alert-warning" role="alert">
        <span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
        Nenhum registro localizado!
      </div>
    </div>
    <div class="table-responsible" ng-show="registro.pessoaRelacionamentos.length > 0">
      
      <table class="table table-striped">
        <thead>
          <tr>
            <th><frz-seletor ng-model="relacionamentoNvg" dados="registro.pessoaRelacionamentos" ng-hide="true"></frz-seletor></th>
            <th>#</th>
            <th>Finalidade</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaRelacionamentos | orderBy: 'nome' | pagina: relacionamentoNvg.paginaAtual : relacionamentoNvg.tamanhoPagina | limitTo: relacionamentoNvg.tamanhoPagina | filter: {'_a': '!E'}">
            <td>
              <input type="radio" ng-show="relacionamentoNvg.selecao.tipo === 'U'" ng-model="relacionamentoNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="relacionamentoNvg.selecao.tipo === 'M'" checklist-model="relacionamentoNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((relacionamentoNvg.paginaAtual-1) * relacionamentoNvg.tamanhoPagina)}}
            </td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.finalidaderelacionamento.$invalid && (!$parent.formularioPessoa.finalidaderelacionamento.$pristine || submitted) }">
	            	<select class="form-control" id="finalidaderelacionamento" name="finalidaderelacionamento" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select>
					<p data-ng-show="$parent.formularioPessoa.finalidaderelacionamento.$invalid && (!$parent.formularioPessoa.finalidaderelacionamento.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
				</div>
            </td>
            <td>
				{{item.relacionamento}}
            </td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>