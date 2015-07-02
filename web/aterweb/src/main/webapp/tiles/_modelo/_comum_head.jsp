<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="temp" value="${pageContext.request}" />
<c:set var="baseUrl" value="${fn:replace(temp.requestURL, temp.requestURI, temp.contextPath)}/" />
<base href="${baseUrl}">

<sec:authorize access="isAuthenticated()">
<script>
const USUARIO_LOGADO = {
	id: <sec:authentication property="principal.id" />,
	nomeUsuario: '<sec:authentication property="principal.nomeUsuario" />'.replace(/&#32;/g, " "),
	pessoa: {
		id: <sec:authentication property="principal.pessoa.id" />,
		nome: '<sec:authentication property="principal.pessoa.nome" />'.replace(/&#32;/g, " "),
		apelidoSigla: '<sec:authentication property="principal.pessoa.apelidoSigla" />'.replace(/&#32;/g, " "),
		pessoaTipo: '<sec:authentication property="principal.pessoa.pessoaTipo" />'.replace(/&#32;/g, " "),
		fotoPerfil: '<sec:authentication property="principal.pessoa.fotoPerfil" />'
	}
};

if (!USUARIO_LOGADO.pessoa.pessoaTipo.indexOf("Grupo Social")) {	
	USUARIO_LOGADO.pessoa["@class"] = "gov.emater.aterweb.model.PessoaGrupo";
	USUARIO_LOGADO.pessoa.pessoaTipo = "GS";
} else if (!USUARIO_LOGADO.pessoa.pessoaTipo.indexOf("Pessoa F")) {	
	USUARIO_LOGADO.pessoa["@class"] = "gov.emater.aterweb.model.PessoaFisica";
	USUARIO_LOGADO.pessoa.pessoaTipo = "PF";
} else if (!USUARIO_LOGADO.pessoa.pessoaTipo.indexOf("Pessoa J")) {	
	USUARIO_LOGADO.pessoa["@class"] = "gov.emater.aterweb.model.PessoaJuridica";
	USUARIO_LOGADO.pessoa.pessoaTipo = "PJ";
}
</script>
</sec:authorize>
<sec:authorize access="isAnonymous()">
<script>
const USUARIO_LOGADO = null;
</script>
</sec:authorize>

<title>ATER web</title>

<!-- PROTECAO AJAX CONTRA ATAQUES CSRF -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="author" content="humans.txt">
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

<link rel="shortcut icon" href="resources/img/logo.ico" type="image/x-icon" />

<!-- CSS diversos -->    
<link rel="stylesheet" href="resources/css/bootstrap.css">
<link rel="stylesheet" href="resources/css/bootstrap-theme.css">
<link rel="stylesheet" href="resources/css/fullcalendar.css">
<link rel="stylesheet" href="resources/css/gallery/blueimp-gallery.min.css">
<link rel="stylesheet" href="resources/css/jquery.fileupload.css">
<link rel="stylesheet" href="resources/css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="resources/css/jquery.loadmask.css">
<link rel="stylesheet" href="resources/css/main-style.css">
<link rel="stylesheet" href="resources/css/toaster.css">
<link rel="stylesheet" href="resources/js/libs/owl-carousel/owl.carousel.css">
<link rel="stylesheet" href="resources/js/libs/owl-carousel/owl.theme.css">

<!-- CSS para angular -->    
<link rel="stylesheet" href="resources/css/angular-file-upload-common.css">
<link rel="stylesheet" href="resources/js/libs/angular.treeview-master/css/angular.treeview.css" type="text/css">
<link rel="stylesheet" href="resources/css/angular-ui-tree.min.css" type="text/css">

<script>
// variaveis padroes para chamar os enderecos das paginas e suas acoes
const baseUrl = "${baseUrl}";
const ACAO_EXCLUIR = "/excluir";
const ACAO_FILTRAR = "/filtrar";
const ACAO_INCLUIR = "/incluir";
const ACAO_PREPARAR = "/preparar";
const ACAO_RESTAURAR = "/restaurar";
const ACAO_SALVAR = "/salvar";
const ACAO_DETALHAR = "/detalhar";
const ACAO_DOMINIO = "/dominio";
const ACAO_POPUP = "/popup";
var nomeModulo = "";
</script>

<script type="text/javascript" src="resources/js/jquery-2.1.1.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/bootstrap.js" charset="UTF-8"></script>

<!-- plugins do bootstrap -->
<!-- 
<script type="text/javascript" src="resources/js/affix.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/alert.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/button.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/carousel.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/collapse.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/popover.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/scrollspy.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/tab.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/tooltip.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/transition.js" charset="UTF-8"></script> 
<script type="text/javascript" src="resources/js/dropdown.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/modal.js" charset="UTF-8"></script>
-->

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.9&sensor=false" charset="UTF-8"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=drawing" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/polygon.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/chart.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/gallery/jquery.blueimp-gallery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/javascript-canvas-to-blob/canvas-to-blob.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/javascript-load-image/load-image.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/javascript-templates/tmpl.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/jquery.loadmask.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/jquery.maskMoney.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/jquery.maskedinput.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/jquery.pin.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/fullcalendar.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/gcal.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery-ui.custom.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.cookie.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-ui.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-fp.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-process.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-validate.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-video.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-audio.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-image.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.iframe-transport.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.mask.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/main.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/moment.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/owl-carousel/owl.carousel.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/vendor/jquery.ui.widget.js" charset="UTF-8"></script>

<!-- <script type="text/javascript" src="resources/js/libs/lang-all.js" charset="UTF-8"></script> -->

<!-- Scripts Angular JS -->
<script type="text/javascript" src="resources/js/angular.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-route.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-resource.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-animate.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-sanitize.js" charset="UTF-8"></script>

<!-- Componentes Customizados do Angular JS -->
<script type="text/javascript" src="resources/js/lodash.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-google-maps.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/toaster.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/angular-file-upload/angular-file-upload.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/angular-file-upload/FileAPI.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/angular-file-upload/angular-file-upload-shim.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/angular-file-upload/angular-file-upload-html5-shim.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-i18n/angular-locale_pt-br.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/angular.treeview-master/angular.treeview.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/angular-ui-tree.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/jquery.fileupload-angular.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/libs/calendar.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/ui-bootstrap.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/ui-bootstrap-tpls.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/ui-utils.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/splitter.js" charset="UTF-8"></script>

<!-- Componentes Desenvolvidos -->
<script type="text/javascript" src="resources/js/comps/modal-dialog/modal-dialog.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/comps/endereco/endereco.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/comps/galeria/galeria.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/comps/relacionamentos/relacionamentos.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/comps/upload/upload.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/frz-navegador.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/services/meio-contato/meio-contato.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/services/requisicao/requisicao.js" charset="UTF-8"></script>