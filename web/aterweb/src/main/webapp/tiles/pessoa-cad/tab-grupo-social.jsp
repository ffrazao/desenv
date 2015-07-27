<div class="panel panel-default" ng-controller="GrupoSocialCtrl">
    <div class="panel-heading container-fluid">
      <frz-navegador
        ng-model="grupoSocialNvg" exibe-texto-botao="false" 
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
            <th><frz-seletor ng-model="grupoSocialNvg" dados="registro.pessoaRelacionamentos" ng-hide="true"></frz-seletor></th>
            <th>#</th>
            <th>Finalidade</th>
            <th>Endereço</th>
            <th>Propriedade Rural?</th>
            <th>Mapa</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="item in registro.pessoaRelacionamentos | orderBy: 'logradouro' | pagina: grupoSocialNvg.paginaAtual : grupoSocialNvg.tamanhoPagina | limitTo: grupoSocialNvg.tamanhoPagina | filter: {'@class': 'gov.emater.aterweb.model.MeioContatoGrupoSocial', '_a': '!E'}">
            <td>
              <input type="radio" ng-show="grupoSocialNvg.selecao.tipo === 'U'" ng-model="grupoSocialNvg.selecao.item" ng-value="item"/>
              <input type="checkbox" ng-show="grupoSocialNvg.selecao.tipo === 'M'" checklist-model="grupoSocialNvg.selecao.items" checklist-value="item"/>
            </td>
            <td>
              {{$index + 1 + ((grupoSocialNvg.paginaAtual-1) * grupoSocialNvg.tamanhoPagina)}}
            </td>
            <td>
				<div data-ng-class="{ 'has-error' : $parent.formularioPessoa.finalidadeGrupoSocial.$invalid && (!$parent.formularioPessoa.finalidadeGrupoSocial.$pristine || submitted) }">
	            	<select class="form-control" id="finalidadeGrupoSocial" name="finalidadeGrupoSocial" data-ng-model="item.finalidade" required="required" data-ng-options="item.codigo as item.descricao for item in apoio.meioContatoFinalidadeList"></select>
					<p data-ng-show="$parent.formularioPessoa.finalidadeGrupoSocial.$invalid && (!$parent.formularioPessoa.finalidadeGrupoSocial.$pristine || submitted)" class="help-block">Campo Obrigatório!</p>
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
            <td><img src="./resources/img/mapa.jpg" class="img-thumbnail"></td>
          </tr>
        </tbody>
        <tfoot>
        </tfoot>
      </table>
    </div>
</div>

<!-- Frazão Inicio Grupo Social
<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">Grupos Sociais</h4>
		<div class="text-right bt-topo">
			<label>Filtrar Nível de Gestão:</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Todos</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Técnico</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Unidade Organizacional</label>
			<label><input type="radio" data-ng-model="acoes.grupoSocial.NivelGestao" name="grupoSocialNivelGestao"> Institucional</label>
			<button class="btn btn-success btn-xs ttip" data-toggle="modal" data-target="#modalGrupoSocial" data-ng-click="apoio.pessoaGrupoIncluir()" data-original-title="Incluir">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
		</div>
	</div>
	<div class="panel-body">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Nome</th>
						<th colspan="3">Nível Gestão</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="grupo in registro.pessoaGrupoSocialList">
						<td>{{grupo.pessoa.nome}}</td>
						<td>{{grupo.nivelGestao}}</td>
						<td>
							<button class="btn btn-warning btn-xs ttip" data-original-title="Editar" data-toggle="modal" data-target="#modalGrupoSocial" data-ng-click="acoes.grupoSocial.editar(grupo)">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>
						</td>
						<td>
							<button class="btn btn-danger btn-xs ttip" data-original-title="Remover" data-ng-click="acoes.grupoSocial.remover(grupo)">
								<span class="glyphicon glyphicon-trash"></span>
							</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
Frazão Fim Grupo Social -->
