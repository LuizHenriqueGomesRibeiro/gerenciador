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
		<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/ServletRelatorios?acao=irParaRelatorios"><button class="page-link">Ir para relatórios</button></a></li>
		<li class="page-item"><button class="page-link">Ajuda</button></li>
		<li class="page-item"><button class="page-link">Refrescar página</button></li>
		<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?acao=listar">Voltar</a></li>
	</ul>
	<div id="json-content"></div>
	<div style="overflow-y: scroll; height: 250px;">
		<table class="table table-striped table-sm">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Quantidade em caixa</th>
					<th>Valor total dos pedidos</th>
					<th>Datas de entrega</th>
					<th>Ver</th>
					<th>Saída</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<tr>
						<td><c:out value="${ml.nome}"></c:out></td>
						<td><c:out value="${ml.quantidade}"></c:out></td>
						<td>generico</td>
						<td>generico</td>
						<td style="height: 30px; width: 40px;"><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px;" href="#">Inf</a></td>
						<td style="width: 40px;"><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px; color: red;"
							href="#" data-toggle="modal" data-target="#exampleModal"><p>Vender</p></a></td>
						<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">Saída de estoque</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<form action="<%=request.getContextPath()%>/servlet_saida" method="get" name="formularioSaida" id="formularioSaida">
											<div class="mb-3">
												<label for="exampleInputEmail1" class="form-label">quantidade <br/> 
												*Deve ser inferior ou igual à quantidade em caixa: ${ml.quantidade} unidades</label>
												<input class="form-control" id="quantidadeHidden" name="quantidadeHidden" value="${ml.quantidade}" type="hidden">
												<input class="form-control" id="quantidade" name="quantidade" value="${ml.quantidade}">
													<label for="exampleInputEmail1" class="form-label">Valor por unidade<br/> 
												<input class="form-control" id="valor" name="valor">
											</div>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
										<button type="button" class="btn btn-primary" onclick="pedido(${ml.id})">Save changes</button>
									</div>
								</div>
							</div>
						</div>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<a class="scroll-to-top rounded" href="#page-top"> 
		<i class="fas fa-angle-up"></i>
	</a>
	<script type="text/javascript">
	
		const input = document.getElementById("quantidade");
		const numeroMaximo = document.getElementById("quantidadeHidden").value;
	
		input.addEventListener("input", validarNumero);
	
		function validarNumero(event) {
		  	const valorInput = Number(event.target.value);
	
		  	if (valorInput > numeroMaximo) {
		    	event.target.value = numeroMaximo;
		  	}
		}
		
		function pedido(id){
			var urlAction = document.getElementById('formularioSaida').action;
			var quantidade = document.getElementById('quantidade').value;
			var valor = document.getElementById('valor').value;
			
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&valor=' + valor + '&idProduto=' + id + '&quantidade=' + quantidade + '&acao=vender',
				success : function(json, textStatus, xhr) {
					location.reload();
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
			});
		}
		
		jQuery("#valor").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});
	
	</script>
</html>