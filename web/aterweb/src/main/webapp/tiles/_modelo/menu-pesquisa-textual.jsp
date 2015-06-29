<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form class="navbar-form navbar-left" role="search">
	<div class="input-group">
		<input id="pesquisaTextualTxt" type="text" class="form-control" placeholder="Pesquisar algo">
		<span class="input-group-btn">
			<button id="pesquisaTextualBtn" class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
		</span>
	</div>					
</form>
	
<div class="modal fade" id="pesquisaTextualForm">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close btn btn-danger" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Pesquisa Textual</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="col-md-12">
						<div id="pesquisaTextualResultadoTxt">
						</div>
					</div>
				</div>
				<div class="clearfix "></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
</div>