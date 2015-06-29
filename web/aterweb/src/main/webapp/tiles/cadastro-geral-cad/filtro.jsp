<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_filtro">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Filtro</h3>
        </div>
        <div class="panel-body">
            <form action="cadastro-geral-cad" method="POST">
	        	<fieldset>
	        		<legend>Propriedade Rural</legend>
					<div class="row">
                                            
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Nome da Propriedade</label>
                                                <input class="form-control" placeholder="Nome da Propriedade" data-ng-model="filtro.propriedadeNome" />
                                            </div>
                                            
                                            <div class="form-group col-md-3">
                                                <label class="control-label">Cidade</label>
                                                <input class="form-control" placeholder="Cidade da Propriedade Rural" data-ng-model="filtro.propriedadeCidade" />
                                            </div>
                                            
                                            <div class="form-group col-md-3">
                                                <label class="control-label">CNPJ</label>
                                                <input class="form-control" placeholder="XX.XXX.XXX/XXXX-XX" data-ng-model="filtro.propriedadeCnpj" />
                                            </div>
                                            
					</div>
                                        
					<div class="row"></div>
                                
					<div class="row">
                                            <div class="form-group col-md-8">
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
                                                        <label class="control-label">ha</label>
                                                    </div>
                                                </div>
                                            </div>
					</div>
					<div class="row">
						<div class="form-group col-md-2">
                                                    <label class="control-label">Arrendatário</label><br />
                                                    <label><input type="radio" name="propriedadeArrendatario" data-ng-model="filtro.propriedadeArrendatario" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeArrendatario" data-ng-model="filtro.propriedadeArrendatario" value="S" /> Tem </label><br />
                                                    <label><input type="radio" name="propriedadeArrendatario" data-ng-model="filtro.propriedadeArrendatario" value="N" /> Não tem </label><br />
						</div>
					
						<div class="form-group col-md-2">
                                                    <label class="control-label">Reserva Legal</label><br />
                                                    <label><input type="radio" name="propriedadeTemReservaLegal" data-ng-model="filtro.propriedadeTemReservaLegal" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeTemReservaLegal" data-ng-model="filtro.propriedadeTemReservaLegal" value="S" /> Tem </label><br />
                                                    <label><input type="radio" name="propriedadeTemReservaLegal" data-ng-model="filtro.propriedadeTemReservaLegal" value="N" /> Não tem </label><br />
						</div>
					
						<div class="form-group col-md-2">
                                                    <label class="control-label">APP</label><br />
                                                    <label><input type="radio" name="propriedadeTemApp" data-ng-model="filtro.propriedadeTemApp" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeTemApp" data-ng-model="filtro.propriedadeTemApp" value="S" /> Tem </label><br />
                                                    <label><input type="radio" name="propriedadeTemApp" data-ng-model="filtro.propriedadeTemApp" value="N" /> Não tem </label><br />
						</div>
					
						<div class="form-group col-md-2">
                                                    <label class="control-label">Plano de Utilização</label><br />
                                                    <label><input type="radio" name="propriedadeTemPlanoUtilizacao" data-ng-model="filtro.propriedadeTemPlanoUtilizacao" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeTemPlanoUtilizacao" data-ng-model="filtro.propriedadeTemPlanoUtilizacao" value="S" /> Tem </label><br />
                                                    <label><input type="radio" name="propriedadeTemPlanoUtilizacao" data-ng-model="filtro.propriedadeTemPlanoUtilizacao" value="N" /> Não tem </label><br />
						</div>
                                            
						<div class="form-group col-md-2">
                                                    <label class="control-label">Status da Propriedade Rural</label><br />
                                                    <label><input type="radio" name="propriedadeStatus" data-ng-model="filtro.propriedadeStatus" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeStatus" data-ng-model="filtro.propriedadeStatus" value="V" /> Válido </label><br />
                                                    <label><input type="radio" name="propriedadeStatus" data-ng-model="filtro.propriedadeStatus" value="I" /> Inválido </label><br />
						</div>
                                            
                                                <div class="form-group col-md-2">
                                                    <label class="control-label">Situação Fundiária</label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="" /> Ignorar </label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="R" /> Escritura Definitiva </label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="C" /> Concessão de Uso </label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="A" /> Arrendatário </label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="P" /> Posse </label><br />
                                                    <label><input type="radio" name="propriedadeSituacaoFundiaria" data-ng-model="filtro.propriedadeSituacaoFundiaria" value="O" /> Outros </label> 
						</div>
					</div>
	        	</fieldset>
                
	        	<fieldset>
	        		<legend>Produtor Rural ou Morador</legend>
					<div class="row">
                                            <div class="form-group col-md-4">
                                                <label class="control-label">Nome do Produtor Rural ou Morador</label>
                                                <input class="form-control" placeholder="Nome do Produtor Rural ou Morador" data-ng-model="filtro.produtorNome" />
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label class="control-label">CPF do Produtor Rural ou Morador</label>
                                                <input class="form-control" placeholder="XXX.XXX.XXX-XX" data-ng-model="filtro.produtorCpf" />
                                            </div>
                                            
                                            <div class="form-group col-md-5">
                                                <label class="control-label">Escolaridade</label>
                                                <select class="form-control" data-ng-model="filtro.produtorEscolaridade">
                                                    <option data-ng-repeat="escolaridade in apoio.escolaridade" value="{{escolaridade.codigo}}">{{escolaridade.nome}}</option>
                                                </select>
                                            </div>
                                            
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Nascimento</label>
							<div class="row">
								<div class="form-group col-md-1">
									<label class="control-label">Entre</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control data" placeholder="no mínimo" data-ng-model="filtro.produtorNascimentoIni" />
								</div>
								<div class="form-group col-md-1">
									<label class="control-label">e</label>
								</div>
								<div class="form-group col-md-3">
									<input class="form-control data" placeholder="no máximo" data-ng-model="filtro.produtorNascimentoFin" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Sexo</label><br />
							<label><input type="radio" name="produtorSexo" data-ng-model="filtro.produtorSexo" value="" /> Ignorar </label><br />
							<label><input type="radio" name="produtorSexo" data-ng-model="filtro.produtorSexo" value="M" /> Masculino </label><br />
							<label><input type="radio" name="produtorSexo" data-ng-model="filtro.produtorSexo" value="F" /> Feminino </label><br />
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-offset-2 col-md-3">
							<label class="control-label">Inscrição SEF DF</label>
							<input class="form-control" placeholder="Número de Inscrição SEF DF do Produtor Rural ou Morador" data-ng-model="filtro.produtorNumeroInscricaoSefDf" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Número DAP</label>
							<input class="form-control" placeholder="Número DAP do Produtor Rural ou Morador" data-ng-model="filtro.produtorNumeroDap" />
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-offset-2 col-md-8">
							<label class="control-label">Status do Produtor Rural ou Morador</label><br />
							<label><input type="radio" name="produtorStatus" data-ng-model="filtro.produtorStatus" value="" /> Ignorar </label><br />
							<label><input type="radio" name="produtorStatus" data-ng-model="filtro.produtorStatus" value="V" /> Válido </label><br />
							<label><input type="radio" name="produtorStatus" data-ng-model="filtro.produtorStatus" value="I" /> Inválido </label><br />
						</div>
					</div>
	        	</fieldset>
            </form>
        </div>
    </div>
</div>