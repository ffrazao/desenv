<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="div_filtro">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Filtro</h3>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" name="$parent.fltCadastro" novalidate>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<fieldset>
					<div class="form-group">
						<label class="col-md-4 control-label" for="nome">Nome</label>	
						<div class="col-md-4">
							<input id="nome" name="nome" type="text" placeholder="Nome da Pessoa" class="form-control input-md" data-ng-model="filtro.nome">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="categoria">Categoria</label>
						<div class="col-md-4">
							<select id="categoria" name="categoria" class="form-control">
								<option value="">Sem Filtro</option>
								<option>Empreendedor Patronal</option>
								<option>Empreendedor Familiar</option>
								<option>Habitante Patronal</option>
								<option>Habitante Familiar</option>
								<option>Trabalhador Familiar</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="ater">ATER</label>
						<div class="col-md-4">
							<select id="ater" name="ater" class="form-control">
								<option value="">Sem Filtro</option>
								<option value="S">Sim</option>
								<option value="N">Não</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="dap">DAP</label>
						<div class="col-md-4">
							<select id="dap" name="dap" class="form-control">
								<option value="">Sem Filtro</option>
								<option>Sim</option>
								<option>Não</option>
								<option>NDP</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="genero">Gênero</label>
						<div class="col-md-4">
							<select id="genero" name="genero" class="form-control">
								<option value="">Sem Filtro</option>
								<option>Masculino</option>
								<option>Feminino</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="geracao">Geração</label>
						<div class="col-md-4">
							<select id="geracao" name="geracao" class="form-control">
								<option value="">Sem Filtro</option>
								<option>Adulto</option>
								<option>Idoso</option>
								<option>Jovem</option>
								<option>Criança</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="setor">Setor</label>
						<div class="col-md-4">
							<select id="setor" name="setor" class="form-control">
								<option value="">Sem Filtro</option>
								<option>Agropecuária</option>
								<option>Agroindústria</option>
								<option>Artesanato</option>
								<option>Comércio</option>
								<option>Proc. Artesanal</option>
								<option>Serviço</option>
								<option>Turismo</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="ativo">Ativo</label>
						<div class="col-md-4">
							<select id="ativo" name="ativo" class="form-control" data-ng-model="filtro.situacao">
								<option value="">Sem Filtro</option>
								<option value="A">Ativo</option>
								<option value="U">Inativo por falta de uso</option>
								<option value="F">Inativo por falecimento</option>
								<option value="O">Inativo por outro motivo</option>
							</select>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>