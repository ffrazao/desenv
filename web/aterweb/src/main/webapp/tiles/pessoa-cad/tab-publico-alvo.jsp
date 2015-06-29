<!-- Frazão Inicio Principal -->
<div class="container">

	<div class="col-md-12" style="margin-left: -14px">

		<div data-ng-show="registro.pessoaTipo === 'PF'">
			<div class="form-group col-md-2">
				<label for="categoria" class="control-label">Categoria</label>
				<select class="form-control" data-ng-model="registro.publicoAlvo.categoria" id="categoria" name=".categoria" 
					data-ng-options="item.codigo as item.descricao for item in apoio.publicoAlvoCategoriaList">
					<option value="">*** Não Informado ***</option>
				</select>
			</div>
			<div class="form-group col-md-2">
				<label for="segmento" class="control-label">Segmento</label>
				<select class="form-control" data-ng-model="registro.publicoAlvo.segmento" id="segmento" name=".segmento" 
					data-ng-options="item.codigo as item.descricao for item in apoio.publicoAlvoSegmentoList">
					<option value="">*** Não Informado ***</option>
				</select>
			</div>
		</div>
		<div data-ng-show="registro.pessoaTipo === 'PJ'">
			<div class="form-group col-md-2">
				<label for="organizacao" class="control-label">Organização</label>
				<select class="form-control relacionamento_tipos"
					id="organizacao"
					data-ng-model="registro.publicoAlvo.organizacaoTipo.id"
					data-ng-options="orgTipo.id as orgTipo.nome for orgTipo in organizacaoTipo">
					<!--GERADO POR JSON-->
				</select>
			</div>
		</div>


		<div class="form-group col-md-3">
			<label for="atividades" class="control-label">Principais
				atividades produtivas</label> <input class="form-control"
				id="atividades" name=".atividades"
				data-ng-model="registro.publicoAlvo.atividades" />
		</div>
		<div class="form-group col-md-2">
			<label for="tradicao" class="control-label">Tradição</label>
			<select class="form-control tradicao" id="tradicao"
				name=".tradicao"
				data-ng-model="registro.publicoAlvo.tradicao"
				data-ng-options="trad for trad in tradicao">
				<!--GERADO POR JSON-->
			</select>
		</div>



		<div class="form-group col-md-2">
			<label for="salarioMensal" class="control-label">Salário
				Mensal</label> <input class="form-control" id="salarioMensal"
				data-ng-model="registro.publicoAlvo.salarioMensal" />

		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-default ">
			<div class="panel-heading">
				<h4 class="panel-title">DAP - Declaração de Aptidão ao
					PRONAF</h4>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="form-group col-md-4">
						<label for="dapRegistro" class="control-label">Registro</label>
						<input class="form-control"
							data-ng-model="registro.publicoAlvo.dapNumero"
							maxlength="30" placeholder="Registro" />
					</div>
					<div class="form-group col-md-4">
						<label for="dapObservacao" class="control-label">Observação</label>
						<input class="form-control"
							data-ng-model="registro.publicoAlvo.dapObservacao"
							maxlength="30" placeholder="Observação" />
					</div>
					<div class="form-group col-md-4">
						<label for="dapSituacao" class="control-label">Situação</label>
						<input class="form-control"
							data-ng-model="registro.publicoAlvo.dabSituacao"
							maxlength="30" placeholder="Situação" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6"
		data-ng-show="registro.pessoaTipo === 'PF'">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Carteira do Produtor</h4>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="form-group col-md-4">
						<label for="dapRegistro" class="control-label">Número</label>
						<input class="form-control" id="dapRegistro"
							data-ng-model="registro.publicoAlvo.carteiraProdutorNumero"
							maxlength="30" placeholder="Número" />
					</div>
					<div class="form-group col-md-4">
						<label for="cnhPrimeiraHabilitacao" class="control-label">Primeira
							Habilitação</label> <input class="form-control data"
							id="cnhPrimeiraHabilitacao"
							data-ng-model="registro.publicoAlvo.carteiraProdutorEmissao"
							placeholder="00/00/0000" />
					</div>
					<div class="form-group col-md-4">
						<label for="cnhValidade" class="control-label">Validade</label>
						<input class="form-control data" id="cnhValidade"
							data-ng-model="registro.publicoAlvo.carteiraProdutorExpiracao"
							placeholder="00/00/0000" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6"
		data-ng-show="registro.pessoaTipo === 'PF'">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Benefício Social</h4>
			</div>
			<div class="panel-body">
				<table>

					<tr>
						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocAposentadoriaPensao"
							data-ng-model="registro.publicoAlvo.benefSocAposentadoriaPensao" />
						</td>

						<td><label class="control-label">
								Aposentadoria Pensão</label></td>

						<!-- input type="radio" value="S" id="benefSocAposentadoriaPensao" data-ng-model="registro.publicoAlvo.benefSocAposentadoriaPensao" /> Sim |
                                    <input type="radio" value="N" id="benefSocAposentadoriaPensao" data-ng-model="registro.publicoAlvo.benefSocAposentadoriaPensao" /-->



						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocCtpsAssinada"
							data-ng-model="registro.publicoAlvo.benefSocCtpsAssinada" /></td>


						<td><label class="control-label">
								CTPS Assinada</label></td>

						<!--  input type="radio" value="S" id="benefSocCtpsAssinada" data-ng-model="registro.publicoAlvo.benefSocCtpsAssinada" /> Sim |
                                    <input type="radio" value="N" id="benefSocCtpsAssinada" data-ng-model="registro.publicoAlvo.benefSocCtpsAssinada" /> Não   -->



						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocNecessidEspec"
							data-ng-model="registro.publicoAlvo.benefSocNecessidEspec" /></td>


						<td><label class="control-label">Necessidade
								Especiais</label></td>

						<!--  input type="radio" value="S" id="benefSocNecessidEspec" data-ng-model="registro.publicoAlvo.benefSocNecessidEspec" /> Sim |
                                    <input type="radio" value="N" id="benefSocNecessidEspec" data-ng-model="registro.publicoAlvo.benefSocNecessidEspec" /> Não-->



						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocProgSociais"
							data-ng-model="registro.publicoAlvo.benefSocProgSociais" />
						</td>
						<td><label class="control-label">Programas
								Sociais</label></td>

					</tr>
					<!-- input type="radio" value="S" id="benefSocProgSociais" data-ng-model="registro.publicoAlvo.benefSocProgSociais" /> Sim |
                                    <input type="radio" value="N" id="benefSocProgSociais" data-ng-model="registro.publicoAlvo.benefSocProgSociais" /> Não -->

				</table>
			</div>
		</div>
	</div>


	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Projeto Especial</h4>
			</div>
			<div class="panel-body">

				<table>
					<tr>
						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocProgSociais"
							data-ng-model="registro.publicoAlvo.projetoEspecAtepa" />
						</td>
						<td class=" col-md-3"><label
							class="control-label">ATEPA</label></td>

						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocProgSociais"
							data-ng-model="registro.publicoAlvo.projetoEspecBsm" />
						</td>
						<td class=" col-md-3"><label
							class="control-label">BSM</label></td>

						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocProgSociais"
							data-ng-model="registro.publicoAlvo.projetoEspecIncra" />
						</td>
						<td class=" col-md-3"><label
							class="control-label">INCRA</label></td>

						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N" id="benefSocProgSociais"
							data-ng-model="registro.publicoAlvo.projetoEspecSustentabilidade" />
						</td>
						<td class=" col-md-3"><label
							class="control-label">Sustentabilidade</label></td>

					</tr>

				</table>

				<!--  <div class="row">
					<div class="form-group col-md-3">
						<label for="projetoEspecAtepa" class="control-label">ATEPA</label>
							 <input type="radio" value="S"
							id="projetoEspecAtepa"
							data-ng-model="registro.publicoAlvo.projetoEspecAtepa" />
						Sim | <input type="radio" value="N" id="projetoEspecAtepa"
							data-ng-model="registro.publicoAlvo.projetoEspecAtepa" />
						Não
					</div>
					<div class="form-group col-md-3">
						<label for="projetoEspecBsm" class="control-label">BSM</label>
						<br> <input type="radio" value="S"
							id="projetoEspecBsm"
							data-ng-model="registro.publicoAlvo.projetoEspecBsm" />
						Sim | <input type="radio" value="N" id="projetoEspecBsm"
							data-ng-model="registro.publicoAlvo.projetoEspecBsm" />
						Não
					</div>
					<div class="form-group col-md-3">
						<label for="projetoEspecIncra" class="control-label">INCRA</label>
						<br> <input type="radio" value="S"
							id="projetoEspecIncra"
							data-ng-model="registro.publicoAlvo.projetoEspecIncra" />
						Sim | <input type="radio" value="N" id="projetoEspecIncra"
							data-ng-model="registro.publicoAlvo.projetoEspecIncra" />
						Não
					</div>
					<div class="form-group col-md-3">
						<label for="projetoEspecSustentabilidade"
							class="control-label">Sustentabilidade</label> <br>
						<input type="radio" value="S"
							id="projetoEspecSustentabilidade"
							data-ng-model="registro.publicoAlvo.projetoEspecSustentabilidade" />
						Sim | <input type="radio" value="N"
							id="projetoEspecSustentabilidade"
							data-ng-model="registro.publicoAlvo.projetoEspecSustentabilidade" />
						Não
					</div>

				</div> -->
			</div>
		</div>
	</div>

	<div class="col-md-12"
		data-ng-show="registro.pessoaTipo === 'PJ'">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Compra Institucional</h4>
			</div>
			<div class="panel-body">
				<table>
				   <tr>
					<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N"
							id="compraInstitucPaaConab"
							data-ng-model="registro.publicoAlvo.compraInstitucPaaConab" /></td>
						<td class=" col-md-3"><label for="compraInstitucPaaConab" class="control-label">PAA
							Conab</label></td>
																			
						<td><input
							type="checkbox" ng-true-value="S"
							ng-false-value="N" id="compraInstitucPaaEstoque"
							data-ng-model="registro.publicoAlvo.compraInstitucPaaEstoque" /></td>
						<td class=" col-md-3"><label for="compraInstitucPaaEstoque"
							class="control-label">PAA Estoque</label></td> 
			
						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N"
							id="compraInstitucPaaTermoAdesao"
							data-ng-model="registro.publicoAlvo.compraInstitucPaaTermoAdesao" /></td>
						<td class=" col-md-3"><label for="compraInstitucPaaTermoAdesao"
							class="control-label">PAA Termo de Adesão</label></td>
					
						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N"
							id="compraInstitucPapa"
							data-ng-model="registro.publicoAlvo.compraInstitucPapa" /></td>
						<td class=" col-md-2"><label for="compraInstitucPapa" class="control-label">PAPA</label></td>										
					
						<td><input type="checkbox" ng-true-value="S"
							ng-false-value="N"
							id="compraInstitucPnae"
							data-ng-model="registro.publicoAlvo.compraInstitucPnae" /></td>
						<td class=" col-md-2"><label for="compraInstitucPnae" class="control-label">PNAE</label></td>
					</tr>																												
				</table>
			</div>
		</div>
	</div>

	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Patrimonio</h4>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="form-group col-md-2">
						<label for="patrimonioBenfeitorias" class="control-label">Benfeitorias</label>
						<br> <input class="form-control" type="text"
							id="patrimonioBenfeitorias"
							data-ng-model="registro.publicoAlvo.patrimonioBenfeitorias" />
					</div>
					<div class="form-group col-md-2">
						<label for="patrimonioDividas" class="control-label">Dívidas</label>
						<br> <input class="form-control" type="text"
							id="patrimonioDividas"
							data-ng-model="registro.publicoAlvo.patrimonioDividas" />
					</div>
					<div class="form-group col-md-2">
						<label for="patrimonioMaquinaEquip" class="control-label">Equipamentos</label>
						<br> <input class="form-control" type="text"
							id="patrimonioMaquinaEquip"
							data-ng-model="registro.publicoAlvo.patrimonioMaquinaEquip" />
					</div>
					<div class="form-group col-md-2">
						<label for="patrimonioOutros" class="control-label">Outros</label>
						<br> <input class="form-control" type="text"
							id="patrimonioOutros"
							data-ng-model="registro.publicoAlvo.patrimonioOutros" />
					</div>
					<div class="form-group col-md-2">
						<label for="patrimonioSemovente" class="control-label">Semovente</label>
						<br> <input class="form-control" type="text"
							id="patrimonioSemovente"
							data-ng-model="registro.publicoAlvo.patrimonioSemovente" />
					</div>
					<div class="form-group col-md-2">
						<label for="patrimonioTerras" class="control-label">Terras</label>
						<br> <input class="form-control" type="text"
							id="patrimonioTerras"
							data-ng-model="registro.publicoAlvo.patrimonioTerras" />
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Renda Bruta Anual</h4>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="form-group col-md-4">
						<label for="rendaBrutaAnualAssalariado"
							class="control-label">Assalariado</label> <br> <input
							class="form-control" type="text"
							id="rendaBrutaAnualAssalariado"
							data-ng-model="registro.publicoAlvo.rendaBrutaAnualAssalariado" />
					</div>
					<div class="form-group col-md-4">
						<label for="rendaBrutaAnualOutras" class="control-label">Outras</label>
						<br> <input class="form-control" type="text"
							id="rendaBrutaAnualOutras"
							data-ng-model="registro.publicoAlvo.rendaBrutaAnualOutras" />
					</div>
					<div class="form-group col-md-4">
						<label for="rendaBrutaAnualPropriedade"
							class="control-label">Propriedade</label> <br> <input
							class="form-control" type="text"
							id="rendaBrutaAnualPropriedade"
							data-ng-model="registro.publicoAlvo.rendaBrutaAnualPropriedade" />
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Força Trabalho</h4>
			</div>
			<div class="panel-body">
				<div class="col-md-2"></div>
				<div class="row">
					<div class="form-group col-md-4">
						<label for="forcaTrabEventual" class="control-label">Eventual</label>
						<br> <input class="form-control" type="text"
							id="forcaTrabEventual"
							data-ng-model="registro.publicoAlvo.forcaTrabEventual" />
					</div>
					<div class="form-group col-md-4">
						<label for="forcaTrabPermanente" class="control-label">Permanente</label>
						<br> <input class="form-control" type="text"
							id="forcaTrabPermanente"
							data-ng-model="registro.publicoAlvo.forcaTrabPermanente" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Frazão Fim Público Alvo -->
