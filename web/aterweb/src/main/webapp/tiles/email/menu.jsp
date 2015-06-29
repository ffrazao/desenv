<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default navbar-static-top navbar-inverse pin" role="navigation">
    <div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-email-navbar-collapse-1">
				<span class="sr-only">Alternar Navegação</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>		
        <div class="collapse navbar-collapse" id="menu-email-navbar-collapse-1">
			<ul class="nav navbar-nav">
                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Gestão"><i class="glyphicon glyphicon-cloud-download"></i> Atualizar <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                        <li><a href="email">Todas as contas</a></li>
                        <li class="divider"></li>
                        <li><a href="email">Fernando Frazão da Silva</a></li>
                        <li><a href="email">GETIN</a></li>
                    </ul>
                </li>
			    <li class="active"><a href="email"><i class="glyphicon glyphicon-envelope"></i> Nova Mensagem </a></li>
			    <li class="active"><a href="pessoa-cad"><i class="glyphicon glyphicon-user"></i> Catálogo </a></li>
                <li class="menu-item dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Tags"><i class="glyphicon glyphicon-list-alt"></i> Tags <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                        <li><a href="email">Nova Tag</a></li>
                        <li><a href="email">Gerenciar Tags</a></li>
                        <li class="divider"></li>
                        <li><a href="email">Remover Tag</a></li>
                        <li class="divider"></li>
                        <li><a href="email">Importante</a></li>
                        <li><a href="email">Trabalho</a></li>
                        <li><a href="email">Particular</a></li>
                        <li><a href="email">Pendente</a></li>
                        <li><a href="email">Adiar</a></li>
                    </ul>
                </li>
            </ul>
			<jsp:include page="../_modelo/menu-pesquisa-textual.jsp"></jsp:include>
			<button class="btn btn-primary btn-sm navbar-btn" type="button" onclick="document.location.href = baseUrl">
				ATER web
			</button>
			<jsp:include page="../_modelo/menu-login.jsp"></jsp:include>
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