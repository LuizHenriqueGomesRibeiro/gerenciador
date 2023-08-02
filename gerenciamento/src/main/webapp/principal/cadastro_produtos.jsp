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
</head>
<body id="page-top">
	<div id="wrapper">
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page="includes/barra_de_navegacao.jsp"></jsp:include>
				<div class="container-fluid">
					<c:if test="${msg != '' || msg != null}">
						<h1 class="h3 mb-4 text-gray-800">${msg}</h1>
					</c:if>
					<c:if test="${msg == '' || msg == null}">
						<h1 class="h3 mb-4 text-gray-800">Cadastre um novo produto</h1>
					</c:if>  
				</div>
				<div style="position: relative; width: 100%;">
					<form 
						style="position: relative; width: 90%; margin: auto;"
						action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
						method="post"
						name="formulario_cadastro_produtos" id="formulario"
					>
						<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
						
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">Preço por unidade</label> 
							<input class="form-control" id="preco" name="preco">
							<div class="form-text">Preço por unidade</div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">quantidade</label> 
							<input class="form-control" id="quantidade" name="quantidade">
							<div class="form-text">...............................</div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">nome</label> 
							<input class="form-control" id="nome" name="nome">
							<div class="form-text">...............................</div>
						</div>
						<div class="mb-3">
							<input 
								class="form-control"  type="hidden"
								id="usuario_pai_id" name="usuario_pai_id" 
								value="${usuario.id}"
							>
						</div>
						<input type="submit" value="Cadastrar" class="btn btn-primary btn-user btn-block">
					</form>
				</div>
			</div>
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; Your Website 2020</span>
					</div>
				</div>
			</footer>
		</div>
	</div>
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
	<body>
</html>