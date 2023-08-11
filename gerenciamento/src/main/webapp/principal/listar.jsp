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
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
rel="stylesheet">
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-3.7.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.validate.js"></script>
</head>
<body style="overflow: hidden;">
	<jsp:include page="includes/barra_de_navegacao.jsp"></jsp:include>
	<ul class="pagination" style="margin: -24px 0px -1px 0px;">
		<li class="page-item"><button class="page-link" data-toggle="modal" data-target=".ui">Novo registro</button></li>
		<li class="page-item"><button class="page-link">Índice =></button></li>
		<%	
		int totalPagina = (int) request.getAttribute("totalPagina");
		for (int p = 0; p < totalPagina; p++) {
			String url = request.getContextPath() + "/servlet_cadastro_e_atualizacao_produtos?acao=paginar&pagina=" + (p * 10);
			out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
		}
		%>
		<li class="page-item"><button class="page-link">Configurações</button></li>
		<li class="page-item"><button class="page-link">Buscar</button></li>
		<li class="page-item"><button class="page-link">Gerar relatório</button></li>
		<li class="page-item"><button class="page-link">Ajuda</button></li>
		<li class="page-item"><button class="page-link">Refrescar página</button></li>
		<li class="page-item"><a class="page-link" href="principal/principal.jsp">Voltar</a></li>
	</ul>
	<div id="json-content"></div>
	<div style="overflow-y: scroll; height: 300px;">
		<table class="table table-hover table-sm">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Preço</th>
					<th>Quantidade</th>
					<th>Preço total</th>
					<th>Ver</th>
					<th>Excluir</th>
					<th>Configurações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<tr>		
						<td><c:out value="${ml.nome}"></c:out></td>
						<td><c:out value="${ml.precoString}"></c:out></td>
						<td><c:out value="${ml.quantidade}"></c:out></td>
						<td><c:out value="${ml.valorTotalString}"></c:out></td>
						<td style="height: 30px; width: 40px;"><a class="page-link"
							style="margin: -6px 0px -6px 0px; height: 37px;"
							href="#" data-toggle="modal" data-target=".ada${status.index}" id="buscar">Ver</a>
						</td>
						<td style="width: 40px;"><a class="page-link"
							style="margin: -6px 0px -6px 0px; height: 37px; color: red;" href="#"
							data-toggle="modal" data-target=".exc${status.index}"><p>Excluir</p></a>
						</td>
						<td style="width: 40px;"><a class="page-link"
							style="margin: -6px 0px -6px 0px; height: 37px;" href="#"><p>Configurações</p></a>
						</td>
					</tr>
					<jsp:include page="includes/formularios.jsp"></jsp:include>
					<jsp:include page="includes/formulariosExclusao.jsp"></jsp:include>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
	<table class="table table-hover table-sm">
			<thead>
				<tr>
					<th>Soma dos valores</th>
					<th>Nome</th>
					<th>Preço</th>
					<th>Quantidade</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><c:out value="${soma}"></c:out></td>
				</tr>
			</tbody>
		</table>
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
	<script src="js/sb-admin-2.min.js"></script>
	<div class="modal fade bd-example-modal-lg ui" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div style="margin: 20px;">
					<form style="position: relative; width: 90%; margin: auto;"
						action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
						method="post" name="formulario_cadastro_produtos" id="formulario">
						
						<input type="hidden" value="cadastrar" name="acao">

						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">Preço
								por unidade</label> <input class="form-control" id="preco" name="preco">
							<div class="form-text">Preço por unidade</div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">quantidade</label>
							<input class="form-control" id="quantidade" name="quantidade">
							<div class="form-text">...............................</div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">nome</label> <input
								class="form-control" id="nome" name="nome">
							<div class="form-text">...............................</div>
						</div>
						<input value="${usuario.id}" type="hidden" value="cadastrar" name="usuario_pai_id">
						<input type="submit" value="Submeter" class="btn btn-primary btn-user btn-block" style="margin-bottom: 20px;">
					</form>
				</div>
			</div>
		</div>
	</div>
</html>