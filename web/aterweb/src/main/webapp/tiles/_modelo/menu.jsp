<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default navbar-static-top navbar-inverse pin" role="navigation">
    <div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Alternar Navegação</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>		
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
			    <li class="active"><a href="#"><i class="glyphicon glyphicon-asterisk"></i> Dashboard </a></li>
                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Cadastro"><i class="glyphicon glyphicon-plus"></i> Cadastro <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                        <li><a href="pessoa-cad">Pessoas</a></li>
                        <li class="divider"></li>
                        <li><a href="propriedade-rural-cad">Propriedades Rurais</a></li>
                        <li class="divider"></li>
                        <li><a href="pessoa-grupo-cad">Grupos Sociais</a></li>
                        <li class="divider"></li>
                        <li><a href="cadastro-geral-cad">Cadastro Geral de Produtores Rurais</a></li>
                    </ul>
                </li>
                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Atividades"><i class="glyphicon glyphicon-list-alt"></i> Atividades <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                        <li><a href="agenda-cad">Planejar Atividades</a></li>
                        <li class="divider"></li>
                        <li><a href="atividade-cad">Registrar Atividade</a></li>
                        <li class="divider"></li>
                        <li><a href="agenda-cad">Agenda</a></li>
                    </ul>
                </li>
                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Diagnóstico"><i class="glyphicon glyphicon-saved"></i> Diagnóstico <b class="caret"></b></a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                        <li><a href="indice-producao-cad">Índices de Produção</a></li>
                        <li class="divider"></li>
                        <li><a href="indice-social-cad">Índices Sociais</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-submenu">
                            <a tabindex="-1" >Enquete</a>
                            <ul class="dropdown-menu">
                                <li><a tabindex="-1" href="enquete-cad">Configuração</a></li>
                                <li class="dropdown-submenu">
                                    <a>Responder</a>
                                    <ul class="dropdown-menu">
                                        <li><a href="enquete-anonima#">Anônimo</a></li>
                                        <li><a href="enquete-identificada">Identificado</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
	                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Configurações"><i class="glyphicon glyphicon-cog"></i>Configurações <b class="caret"></b></a>
	                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
	                        <li><a href="usuario-cad"><span class="glyphicon glyphicon-user"></span> Usuários </a></li>
	                        <li class="divider"></li>
	                        <li><a href="modulo-cad"><span class="glyphicon glyphicon-th-large"></span> Módulos </a></li>
	                    </ul>
	                </li>
                </sec:authorize>
            </ul>
			<jsp:include page="menu-pesquisa-textual.jsp"></jsp:include>
			<button class="btn btn-primary btn-sm navbar-btn" type="button" onclick="document.location.href = baseUrl + 'email'">
				Email <span class="badge">6</span>
			</button>
			<jsp:include page="menu-login.jsp"></jsp:include>
        </div>
    </div>
</nav>

<script>
    //$(".pin").pin();
    $window = $(window);
    
    var onScroll = function() {
        posicaoY = $window.scrollTop();
        
        if(posicaoY <= $('.navbar').outerHeight()) {
            $(".navbar-static-top").removeClass("navbar-fixed-top");
        } else {
            $(".navbar-static-top").addClass("navbar-fixed-top");
        }
    };
    
    $window.scroll(onScroll);
</script>