<div class="panel panel-default">
    <div class="panel-heading container-fluid">
      <frz-navegador
        ng-model="enderecoNvg" exibe-texto-botao="false" 
        dados="cadastro.registro.endereco"
        acoes-especiais="acoesEspeciais"
        on-abrir = "abrir()"
        on-agir = "agir()"
        on-editar = "editar()"
        on-especial = "especial()"
        on-excluir = "excluir()"
        on-filtrar = "filtrar()"
        on-incluir = "incluir('lg')"
        on-listar = "listar()"
        on-navegar-primeiro = "navegarPrimeiro()"
        on-navegar-anterior = "navegarAnterior()"
        on-navegar-proximo = "navegarPosterior()"
        on-navegar-ultimo = "navegarUltimo()"
        on-proxima-pagina="proximaPagina()"
        on-ultima-pagina="ultimaPagina()"
        ></frz-navegador>
    </div>
    <div class="painel-body"  ng-hide="cadastro.registro.endereco.length">
      <div class="alert alert-warning" role="alert">
        <span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
        Nenhum registro localizado!
      </div>
    </div>
    <div class="table-responsible" ng-show="cadastro.registro.endereco.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <th><frz-seletor ng-model="enderecoNvg" dados="cadastro.registro.endereco"></frz-seletor></th>
            <th>#</th>
            <th>Nome</th>
            <th>Endereco</th>
            <th>Fun��o</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="tel in cadastro.registro.endereco">
            <td>
              <input type="radio" ng-show="enderecoNvg.selecao.tipo === 'U'" ng-model="enderecoNvg.selecao.item" ng-value="tel"/>
              <input type="checkbox" ng-show="enderecoNvg.selecao.tipo === 'M'" checklist-model="enderecoNvg.selecao.items" checklist-value="tel"/>
            </td>
            <td>
              {{$index + 1 + ((enderecoNvg.paginaAtual-1) * enderecoNvg.tamanhoPagina)}}
            </td>
            <td>{{tel.pessoa.nome}}</td>
            <td>{{tel.EnderecoTipo.id}}</td>
            <td>{{tel.EnderecoFuncao.id}}</td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>
    <div class="panel-footer">
    </div>
</div>