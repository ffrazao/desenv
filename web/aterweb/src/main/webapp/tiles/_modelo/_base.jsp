<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>

<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="pt_BR"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="pt_BR"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="pt_BR"> <![endif]-->
<!--[if IE 9]>    <html class="no-js ie9" lang="pt_BR"> <![endif]-->
<!-- Consider adding an manifest.appcache: h5bp.com/d/Offline -->
<!--[if gt IE 9]><!-->

<html data-ng-app="aterweb" itemscope itemtype="http://schema.org/Product" class="no-js" lang="pt_BR">
<!--<![endif]-->

<head>
	<jsp:include page="_comum_head.jsp" />
</head>

<body id="controlador" data-ng-controller="cadastroCtrl">

	<script src="resources/js/main-script.js" charset="UTF-8"></script>
	<script src="resources/js/config-angular.js" charset="UTF-8"></script>
	
	<jsp:include page="painel-espelho.jsp" />

	<div id="cabecalho" class="container">
		<tiles:insertAttribute name="cabecalho" />
	</div>

	<div id="menu" style="width: 100% !important; left: 0; right: 0;">
		<tiles:insertAttribute name="menu" />
	</div>

	<toaster-container toaster-options="{'position-class': 'toast-bottom-right'}"></toaster-container>
        
	<tiles:insertAttribute name="corpo" />

	<div id="footer">
		<div class="container">
			<tiles:insertAttribute name="rodape" />
		</div>
	</div>
</body>
</html>