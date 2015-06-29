<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="col-md-7"></div>
<div class="btn-group pull-right">
    <button id="btn_ferramenta_executar" type="button" class="btn btn-sm btn-primary btn-ferramenta" value="Executar" data-ng-click="executar()" data-toggle="tooltip" data-placement="left" title="Executar"><span class="glyphicon glyphicon-play"></span> <span class="hidden-xs">Executar</span></button>
    <button id="btn_ferramenta_filtrar" type="button" class="btn btn-sm btn-primary btn-ferramenta" value="Filtrar" data-ng-click="filtrar()" data-toggle="tooltip" data-placement="left" title="Filtrar"><span class="glyphicon glyphicon-filter"></span> <span class="hidden-xs">Filtrar</span></button>
    
    <button type="button" class="btn btn-default" disabled style="border: none;">&nbsp;</button>
    
    <button id="btn_ferramenta_incluir" type="button" class="btn btn-sm btn-success btn-ferramenta" value="Incluir" data-ng-click="incluir()" data-toggle="tooltip" data-placement="left" title="Incluir"><span class="glyphicon glyphicon-plus"></span> <span class="hidden-xs">Incluir</span></button>
    <button id="btn_ferramenta_editar" type="button" class="btn btn-sm btn-warning btn-ferramenta" value="Editar" data-ng-click="editar()" data-toggle="tooltip" data-placement="left" title="Editar"><span class="glyphicon glyphicon-pencil"></span> <span class="hidden-xs">Editar</span></button>
    <button id="btn_ferramenta_excluir" type="button" class="btn btn-sm btn-danger btn-ferramenta" data-toggle="modal" data-target="#modalConfirmaExcluir" value="Excluir" data-ng-click="modalExcluir()" data-toggle="tooltip" data-placement="left" title="Excluir" data-ng-show="registro.id"><span class="glyphicon glyphicon-minus"></span> <span class="hidden-xs">Excluir</span></button>
    
    <button type="button" class="btn btn-default" disabled style="border: none;">&nbsp;</button>
    
    <button id="btn_ferramenta_salvar" type="button" class="btn btn-sm btn-success btn-ferramenta" value="Salvar" data-ng-click="salvar()" data-toggle="tooltip" data-placement="left" title="Salvar"><span class="glyphicon glyphicon-floppy-disk"></span> <span class="hidden-xs">Salvar</span></button>
    <button id="btn_ferramenta_cancelar" type="button" class="btn btn-sm btn-warning btn-ferramenta" value="Cancelar" data-ng-click="cancelar()" data-toggle="tooltip" data-placement="left" title="Cancelar"><span class="glyphicon glyphicon-ban-circle"></span> <span class="hidden-xs">Cancelar</span></button>
    
	<div class="btn-group">
		<button id="btn_ferramenta_acoes" type="button" class="btn btn-sm btn-default dropdown-toggle btn-ferramenta pull-left" data-toggle="dropdown">
			<span class="glyphicon glyphicon-align-left"></span> <span class="hidden-xs">Ações</span>
			<span class="caret"></span>
		</button>
		<ul id="barra_ferramenta_acoes" class="dropdown-menu pull-left" style="right: 0; left: auto;">
			<li><a id="" href="javascript:window.print();">Imprimir</a></li>
			<li><a id="">Ação 2</a></li>
		</ul>
	</div>
      
    <button id="btn_ferramenta_exportar" type="button" class="btn btn-sm btn-default btn-ferramenta" value="Exportar" data-toggle="tooltip" data-placement="left" title="Exportar"><span class="glyphicon glyphicon-share"></span> <span class="hidden-xs">Exportar</span></button>
</div>
<br /><br />

<!-- Modal -->
<div class="modal fade" id="modalConfirmaExcluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Excluir</h4>
      </div>
      <div class="modal-body">
        Deseja realmente excluir {{modalExcluir.nome}}?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Não</button>
        <button type="button" class="btn btn-sm btn-primary" data-ng-click="excluir()">Sim</button>
      </div>
    </div>
  </div>
</div>

<script src="<spring:url value="resources/js/barra-ferramenta.js"/>" charset="UTF-8"></script>
