<div class="panel panel-default">
    <div class="panel-heading container-fluid">
      <frz-navegador
        ng-model="enderecoNvg" exibe-texto-botao="false" 
        dados="registro.pessoaMeioContatos"
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
            <th><frz-seletor ng-model="enderecoNvg" dados="registro.pessoaMeioContatos"></frz-seletor></th>
            <th>#</th>
            <th>Nome</th>
            <th>Endereço</th>
            <th>Função</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaMeioContatos | orderBy: 'nome' | pagina: enderecoNvg.paginaAtual : enderecoNvg.tamanhoPagina | limitTo: enderecoNvg.tamanhoPagina">
            <td>
              <input type="radio" ng-show="enderecoNvg.selecao.tipo === 'U'" ng-model="enderecoNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="enderecoNvg.selecao.tipo === 'M'" checklist-model="enderecoNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((enderecoNvg.paginaAtual-1) * enderecoNvg.tamanhoPagina)}}
            </td>
            <td>{{item.pessoa.nome}}</td>
            <td>{{item}}</td>
            <td></td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>