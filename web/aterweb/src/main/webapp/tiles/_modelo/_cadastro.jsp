<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/tiles/<tiles:getAsString name="modulo"/>/style.css" />

<script>
	var nomeModulo = "<tiles:getAsString name="modulo"/>";
	var cadModo = ${modo};
	var cadOpcao = ${opcao};
	var urlPadrao = ${urlPadrao};
	var cadFiltro = ${filtro};
	var cadId = ${id};
	var cadRegistro = ${registro};
	var cadTipoSelecao = ${tipoSelecao};
</script>

<div id="corpo" class="container" style="background-color: #FFF;">
	<div>
		<div class="col-md-5">
			<br />
			<h2>
				<tiles:getAsString name="titulo" />
			</h2>
		</div>

		<div class="col-md-7">
			<div id="barra-ferramenta" style="z-index: 1000; margin: 50px 0 0 0;">
				<jsp:include page="barra-ferramenta.jsp" />
			</div>
		</div>
	</div>

	<div class="row"></div>

	<div data-ng-view></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/tiles/<tiles:getAsString name="modulo"/>/script.js" charset="UTF-8"></script>
