<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
<body style="overflow: hidden;">
	<ul class="pagination" style="margin: 0px 0px -1px 0px;">
		<li class="page-item"><button class="page-link">Índice=></button></li>
		<li class="page-item"><button class="page-link">Configurações</button></li>
		<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar">
			<button class="page-link">Ir para caixa</button></a></li>
		<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/ServletRelatorios?acao=irParaRelatorios">
			<button class="page-link">Ir para relatórios</button></a></li>
		<li class="page-item"><button class="page-link">Ajuda</button></li>
		<li class="page-item"><button class="page-link">Refrescar página</button></li>
		<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?acao=listar">
			Voltar</a></li>
	</ul>
	<div style="margin-left: 20px; margin-top: 10px;">
		<h2>Setor de contabilidade</h2>
		<div>
			<form action="<%=request.getContextPath()%>/servlet_saida" method="get" name="formularioSaida" id="formularioSaida">
				<div class="row">
					<div class="col">
						<input onkeypress="$(this).mask('00/00/0000')" class="form-control" id="dataInicial" name="dataInicial">
					</div>
					<div class="col">
						<input onkeypress="$(this).mask('00/00/0000')" class="form-control" id="dataFinal" name="dataFinal">
					</div>
				</div>
				<p style="margin-left: 10px;">*Caso as datas não sejam especificadas, o servidor rodará todos os resultados.</p>
			</form>
			<ul class="pagination" style="margin: 0px 0px -1px 0px;">
				<li class="page-item"><button class="page-link" style="text-decoration: none; width: 198px;" onclick="carregarListaVendas()">Carregar lista de vendas</button></li>
				<li class="page-item"><button class="page-link" style="text-decoration: none; width: 218px;">Carregar lista de entradas</button></li>
				<li class="page-item"><button class="page-link" style="text-decoration: none; width: 198px;">Carregar lista de Lucros</button></li>
			</ul>
		</div>
	</div>
</body>
<script type="text/javascript">

	function carregarListaVendas(){
		
		var urlAction = document.getElementById('formularioSaida').action;
		var dataInicial = document.getElementById('dataInicial').value;
		var dataFinal = document.getElementById('dataFinal').value;
		
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : '&acao=carregarListaVendas&dataInicial=' + dataInicial + '&dataFinal=' + dataFinal,
			success : function(json, textStatus, xhr) {
				alert(json);
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
</script>
</html>