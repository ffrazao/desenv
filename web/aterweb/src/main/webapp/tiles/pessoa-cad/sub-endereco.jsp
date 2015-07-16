<div class="panel panel-default">
    <div class="panel-heading container-fluid">
    	<!-- [{{enderecoK}}] -->
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
            <th><frz-seletor ng-model="enderecoNvg" dados="registro.pessoaMeioContatos" ng-hide="true"></frz-seletor></th>
            <th>#</th>
            <th>Finalidade</th>
            <th>Endereço</th>
            <th>Propriedade Rural?</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaMeioContatos | orderBy: 'logradouro' | pagina: enderecoNvg.paginaAtual : enderecoNvg.tamanhoPagina | limitTo: enderecoNvg.tamanhoPagina | filter: {'@class': 'gov.emater.aterweb.model.MeioContatoEndereco', '_a': '!E'}">
            <td>
              <input type="radio" ng-show="enderecoNvg.selecao.tipo === 'U'" ng-model="enderecoNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="enderecoNvg.selecao.tipo === 'M'" checklist-model="enderecoNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((enderecoNvg.paginaAtual-1) * enderecoNvg.tamanhoPagina)}}
            </td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.finalidadeEndereco.$invalid && (!$parent.formularioPessoa.finalidadeEndereco.$pristine || submitted) }">
	            	<select class="form-control" id="finalidadeEndereco" name="finalidadeEndereco" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select>
					<p data-ng-show="$parent.formularioPessoa.finalidadeEndereco.$invalid && (!$parent.formularioPessoa.finalidadeEndereco.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
				</div>
            </td>
            <td>
				{{item.nomePropriedadeRuralOuEstabelecimento}}<br>
				{{item.logradouro}}, {{item.complemento}}, {{item.numero}}<br>
				{{item.propriedadeRuralConfirmacao === 'N' ? item.bairro : ''}}
				{{item.pessoaGrupoCidadeVi.nome}}, {{item.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.nome}}<br>
				{{item.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.nome}}, {{item.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.nome}}<br>
				{{item.cep ? 'CEP: ' : ''}}{{item.cep}} {{item.codigoIbge ? 'Cód. IBGE: ' : ''}}{{item.codigoIbge}}
            </td>
            <td>
            	{{item.propriedadeRuralConfirmacao === 'S' ? 'Sim' : 'Não'}}<br>
            	{{item.propriedadeRuralConfirmacao === 'S' ? 'Comunidade: ' : ''}}{{item.propriedadeRural.pessoaGrupoComunidadeVi.nome}}<br>
            	{{item.propriedadeRuralConfirmacao === 'S' ? 'Bacia Hidrográfica: ' : ''}}{{item.propriedadeRural.pessoaGrupoBaciaHidrograficaVi.nome}}
            </td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>

</div>