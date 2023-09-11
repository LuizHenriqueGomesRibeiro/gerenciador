<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-3.7.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.maskMoney.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body style="overflow: hidden;">
	<ul class="pagination" style="margin: 0px 0px -1px 0px;">
		<li class="page-item"><button class="page-link">Índice=></button></li>
		<li class="page-item"><button class="page-link">Configurações</button></li>
		<li class="page-item"><button class="page-link">Buscar</button></li>
		<li class="page-item"><button class="page-link">Gerar relatório</button></li>
		<li class="page-item"><button class="page-link">Ajuda</button></li>
		<li class="page-item"><button class="page-link">Refrescar página</button></li>
		<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?acao=listar">Voltar</a></li>
	</ul>
	<h1>Área de geração de relatórios</h1>
	<a style="text-decoration: none" class="page-link" onclick="gerarVendas()" href="#">Gerar relatório de vendas</a>
	<a style="text-decoration: none" class="page-link" href="<%=request.getContextPath()%>/ServletRelatorios?acao=entradas">Gerar relatório de entradas</a>
	<a style="text-decoration: none" class="page-link" href="<%=request.getContextPath()%>/ServletRelatorios?acao=pedidos">Gerar relatório de pedidos</a>
	<a style="text-decoration: none" class="page-link" href="<%=request.getContextPath()%>/ServletRelatorios?acao=cancelamentos">Gerar relatório de cancelamentos</a>
	<div style="display: none;">
		<form action="<%=request.getContextPath()%>/ServletRelatorios" method="get" name="formularioFantasma" id="formularioFantasma"></form>
	</div>
	<script type="text/javascript">
	
		function gerarVendas(){
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({

				method : "get",
				url : urlAction,
				data : '&acao=vendas',
				success : function(json, textStatus, xhr) {
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
	
	</script>
</html>