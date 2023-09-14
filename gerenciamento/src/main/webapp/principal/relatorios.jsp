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
	<a style="text-decoration: none" class="page-link" onclick="gerarEntradas()" href="#">Gerar relatório de entradas</a>
	<a style="text-decoration: none" class="page-link" onclick="gerarPedidos()" href="#">Gerar relatório de pedidos</a>
	<a style="text-decoration: none" class="page-link" onclick="gerarCancelamentos()" href="#">Gerar relatório de cancelamentos</a>
	<div style="display: none;">
		<form action="<%=request.getContextPath()%>/ServletRelatorios" method="get" name="formularioFantasma" id="formularioFantasma"></form>
	</div>
	<div>
		<div id="imprimirRelatorio" style="width: 100%; overflow-y: scroll; overflow-x: none; height: 350px; position: relative; margin: -2px 0px 0px -16px;" class="col-sm">
			<table style="width: 100%;" class="table table-striped table-sm">
				<tbody></tbody>
			</table>
		</div>
		<div id="botao">
		</div>
		<p>${relatorio}</p>
	</div>
	<script type="text/javascript">
	
		jQuery("#imprimirRelatorio").hide();
	
		function gerarVendas(){
			jQuery("#imprimirRelatorio").show();
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=vendas',
				success : function(json, textStatus, xhr) {
					jQuery("#botao > button").remove();
					jQuery("#botao").append("<button class='page-link' onclick='gerarPDFvendas()'>Imprimir PDF</button>");
					jQuery("#imprimirRelatorio > table > tbody > tr").remove();
					jQuery("#imprimirRelatorio > table > thead").remove();
					jQuery("#imprimirRelatorio > table").append("<thead><tr><th>Produto</th><th>Data da venda</th><th>Valor total</th><th>Quantidade</th></tr></thead>");
					for(var p = 0; p < json.length; p++){
						var numero = JSON.stringify(json[p].quantidade);
						var numeroComPontos = numero.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
						var string = JSON.stringify(json[p].dataentrega);
						var substrings = string.split('"');
						var novaString = substrings.join('');
						var string2 = JSON.stringify(json[p].nome);
						var substrings2 = string2.split('"');
						var novaString2 = substrings2.join('');
						const valorNumerico = parseFloat(JSON.stringify(json[p].valortotal)); 
			        	const valorMonetario = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}); 
						jQuery("#imprimirRelatorio > table > tbody").append("<tr><td>"+novaString2+"</td><td>"+novaString+"</td><td>"+valorMonetario+"</td><td>"+numeroComPontos+"</td></tr>");
					}
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
		
		function gerarPDFvendas(){
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=printFormVendasPDF',
				success : function(json, textStatus, xhr) {
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
		
		function gerarEntradas(){
			jQuery("#imprimirRelatorio").show();
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=entradas',
				success : function(json, textStatus, xhr) {
					jQuery("#imprimirRelatorio > table > thead").remove();
					jQuery("#imprimirRelatorio > table > tbody > tr").remove();
					jQuery("#imprimirRelatorio > table").append("<thead><tr><th>Produto</th><th>Data da venda</th><th>Valor total</th><th>Quantidade</th></tr></thead>");
					for(var p = 0; p < json.length; p++){
						var numero = JSON.stringify(json[p].quantidade);
						var numeroComPontos = numero.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
						var string = JSON.stringify(json[p].dataentrega);
						var substrings = string.split('"');
						var novaString = substrings.join('');
						var string2 = JSON.stringify(json[p].nome);
						var substrings2 = string2.split('"');
						var novaString2 = substrings2.join('');
						const valorNumerico = parseFloat(JSON.stringify(json[p].valorTotal)); 
			        	const valorMonetario = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'});	
			        	jQuery("#imprimirRelatorio > table > tbody").append("<tr><td>"+novaString2+"</td><td>"+novaString+"</td><td>"+valorMonetario+"</td><td>"+numeroComPontos+"</td></tr>");
					}
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
		
		function gerarPedidos(){
			jQuery("#imprimirRelatorio").show();
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=pedidos',
				success : function(json, textStatus, xhr) {
					jQuery("#imprimirRelatorio > table > thead").remove();
					jQuery("#imprimirRelatorio > table > tbody > tr").remove();
					jQuery("#imprimirRelatorio > table").append("<thead><tr><th>Produto</th><th>Data do pedido</th><th>Valor total</th><th>Quantidade</th></tr></thead>");
					for(var p = 0; p < json.length; p++){
						var numero = JSON.stringify(json[p].quantidade);
						var numeroComPontos = numero.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
						var string = JSON.stringify(json[p].datapedido);
						var substrings = string.split('"');
						var novaString = substrings.join('');
						var string2 = JSON.stringify(json[p].nome);
						var substrings2 = string2.split('"');
						var novaString2 = substrings2.join('');
						const valorNumerico = parseFloat(JSON.stringify(json[p].valorTotal)); 
			        	const valorMonetario = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'});	
			        	jQuery("#imprimirRelatorio > table > tbody").append("<tr><td>"+novaString2+"</td><td>"+novaString+"</td><td>"+valorMonetario+"</td><td>"+numeroComPontos+"</td></tr>");
					}
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
		
		function gerarCancelamentos(){
			jQuery("#imprimirRelatorio").show();
			var urlAction = document.getElementById('formularioFantasma').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=cancelamentos',
				success : function(json, textStatus, xhr) {
					jQuery("#imprimirRelatorio > table > thead").remove();
					jQuery("#imprimirRelatorio > table > tbody > tr").remove();
					jQuery("#imprimirRelatorio > table").append("<thead><tr><th>Produto</th><th>Data do pedido</th><th>Data do cancelamento</th><th>Valor total</th><th>Quantidade</th></tr></thead>");
					for(var p = 0; p < json.length; p++){
						var numero = JSON.stringify(json[p].quantidade);
						var numeroComPontos = numero.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
						var string = JSON.stringify(json[p].datapedido);
						var substrings = string.split('"');
						var novaString = substrings.join('');
						var string2 = JSON.stringify(json[p].nome);
						var substrings2 = string2.split('"');
						var novaString2 = substrings2.join('');
						var string3 = JSON.stringify(json[p].datacancelamento);
						var substrings3 = string3.split('"');
						var novaString3 = substrings3.join('');
						const valorNumerico = parseFloat(JSON.stringify(json[p].valorTotal)); 
			        	const valorMonetario = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'});	
			        	jQuery("#imprimirRelatorio > table > tbody").append("<tr><td>"+novaString2+"</td><td>"+novaString+"</td><td>"+novaString3+"</td><td>"+valorMonetario+"</td><td>"+numeroComPontos+"</td></tr>");
					}
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Error');
			});
		}
	
	
	</script>
</html>