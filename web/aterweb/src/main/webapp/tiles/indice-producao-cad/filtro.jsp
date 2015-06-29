<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="div_filtro">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Filtro</h3>
        </div>
        <div class="panel-body">
            <form action="indice-producao-cad" method="POST">
	        	<fieldset>
	        		<legend>Localização</legend>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-2">
							<label class="control-label">Pais</label>
							<select class="form-control" data-ng-model="filtro.pessoaGrupoPais.id" data-ng-change="apoio.filtroPessoaGrupoPaisListAtualizar()" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoPaisList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<label class="control-label">Estado</label>
							<select class="form-control" data-ng-model="filtro.pessoaGrupoEstado.id" data-ng-change="apoio.filtroPessoaGrupoEstadoListAtualizar()" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoEstadoList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<label class="control-label">Municipio</label>
							<select class="form-control" data-ng-model="filtro.pessoaGrupoMunicipio.id" data-ng-change="apoio.filtroPessoaGrupoMunicipioListAtualizar()" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoMunicipioList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
						<div class="form-group col-md-2">
							<label class="control-label">Cidade</label>
							<select class="form-control" data-ng-model="filtro.pessoaGrupoCidade.id" data-ng-change="apoio.filtroPessoaGrupoCidadeListAtualizar()" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoCidadeList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Comunidade</label>
							<select class="form-control" data-ng-model="filtro.pessoaGrupoComunidade.id" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoComunidadeList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Bacia Hidrográfica</label>
							<select class="form-control"  data-ng-model="filtro.pessoaGrupoBaciaHidrografica.id" data-ng-options="localizacao.id as localizacao.nome for localizacao in apoio.filtroPessoaGrupoBaciaHidrograficaList">
								<option value="">Sem Filtro</option>
							</select>
						</div>
					</div>
	        	</fieldset>
	        	<fieldset>
	        		<legend>Produção</legend>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-4">
							<label class="control-label">Perspectiva</label>
							<select class="form-control" data-ng-model="filtro.producaoPerspectiva" data-ng-change="apoio.produtoServicoPerspectivaAtualizar(filtro.producaoPerspectiva)">
								<option value="">Sem Filtro</option>
								<option value="AGRICOLA">Agrícola</option>
								<option value="FLORES">&nbsp;&nbsp;&nbsp;Flores</option>
								<option value="ANIMAL">Animal</option>
								<option value="CORTE">&nbsp;&nbsp;&nbsp;Corte</option>
								<option value="LEITE">&nbsp;&nbsp;&nbsp;Leite</option>
								<option value="POSTURA">&nbsp;&nbsp;&nbsp;Postura</option>
								<option value="SERVICO">Serviço</option>
								<option value="AGROINDUSTRIA">&nbsp;&nbsp;&nbsp;Agro-Indústria</option>
								<option value="TURISMO">&nbsp;&nbsp;&nbsp;Turismo</option>
							</select>
						</div>
					</div>
					<div class="row" data-ng-show="filtro.producaoPerspectiva == 'AGRICOLA' || filtro.producaoPerspectiva == 'FLORES'">
						<div class="row" >
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Tipo</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaFloresTipo" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresTipoList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Sistema</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaFloresSistema" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresSistemaList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
						</div>
						<div class="row" >
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Proteção/ Época/ Forma</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaFloresProtecaoEpocaForma" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresProtecaoEpocaFormaList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Uso D'Água</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaFloresUsoDagua" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaFloresUsoDaguaList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row" data-ng-show="filtro.producaoPerspectiva == 'ANIMAL' || filtro.producaoPerspectiva == 'CORTE' || filtro.producaoPerspectiva == 'LEITE' || filtro.producaoPerspectiva == 'POSTURA'">
						<div class="row" >
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Sistema</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaAnimalSistema" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaAnimalSistemaList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Exploração</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaAnimalExploracao" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaAnimalExploracaoList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row" data-ng-show="filtro.producaoPerspectiva == 'SERVICO' || filtro.producaoPerspectiva == 'AGROINDUSTRIA'">
						<div class="row" >
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Projeto</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaServicoProjeto" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoProjetoList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Condição</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaServicoCondicao" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoCondicaoList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
						</div>
						<div class="row" >
							<div class="form-group col-md-offset-2 col-md-3">
								<label class="control-label">Produto</label>
								<select class="form-control" data-ng-model="filtro.producaoPerspectivaServicoProduto" data-ng-options="item.codigo as item.descricao for item in apoio.producaoPerspectivaServicoProdutoList">
									<option value="">Sem Filtro</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Produto ou Serviço</label>
							<div data-angular-treeview="true" 
								data-tree-id="filtroProdutoServicoTree"
								data-tree-model="apoio.produtoServico" 
								data-node-id="id"
								data-node-label="nome" 
								data-node-children="filhos">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-2">
							<label class="control-label">Data da Produção</label>
							<input class="form-control" placeholder="Data" data-ng-model="filtro.producaoData" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Volume de Produção</label><br />
							<div class="row">
								<div class="form-group col-md-1">
									<label class="control-label">Entre</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control" placeholder="No mínimo" data-ng-model="filtro.producaoVolumeIni" />
								</div>
								<div class="form-group col-md-1">
									<label class="control-label">e</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control" placeholder="No máximo" data-ng-model="filtro.producaoVolumeFin" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>Propriedade Rural</legend>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Nome</label>
							<input class="form-control" placeholder="Nome da Propriedade" data-ng-model="filtro.propriedadeNome" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Área da Propriedade Rural</label><br />
							<div class="row">
								<div class="form-group col-md-1">
									<label class="control-label">Entre</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control" placeholder="no mínimo" data-ng-model="filtro.propriedadeAreaPropriedadeIni" />
								</div>
								<div class="form-group col-md-1">
									<label class="control-label">e</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control" placeholder="no máximo" data-ng-model="filtro.propriedadeAreaPropriedadeFin" />
								</div>
								<div class="form-group col-md-1">
									<label class="control-label">hectáres</label>
								</div>
							</div>
						</div>
					</div>
	        	</fieldset>
	        	<fieldset>
	        		<legend>Produtor Rural</legend>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Nome/Apelido/Sigla</label>
							<input class="form-control" placeholder="Nome/Apelido/Sigla do Produtor Rural ou Morador" data-ng-model="filtro.produtorNome" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-3">
							<label class="control-label">CPF/CNPJ</label>
							<input class="form-control" placeholder="CPF/CNPJ do Produtor Rural" data-ng-model="filtro.produtorCpfCnpj" />
						</div>
					</div>
	        	</fieldset>
            </form>
        </div>
    </div>
</div>
{{filtro}}