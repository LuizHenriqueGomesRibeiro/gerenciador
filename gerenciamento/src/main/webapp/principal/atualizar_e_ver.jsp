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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.7.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
</head>
<body style="overflow: hidden;">
	<jsp:include page="includes/barra_de_navegacao.jsp"></jsp:include>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">
					<input type="hidden" value="atualizar" name="acao">

					<div class="mb-3">
						<label for="exampleInputEmail1" class="form-label">Preço
							por unidade</label> <input class="form-control" id="preco" name="preco" value="${produto.preco}">
						<div class="form-text">Preço por unidade</div>
					</div>
					<div class="mb-3">
						<label for="exampleInputEmail1" class="form-label">quantidade</label>
						<input class="form-control" id="quantidade" name="quantidade" value="${produto.quantidade}">
						<div class="form-text">...............................</div>
					</div>
					<div class="mb-3">
						<label for="exampleInputEmail1" class="form-label">nome</label> <input
							class="form-control" id="nome" name="nome" value="${produto.nome}">
						<div class="form-text">...............................</div>
					</div>
					<div class="mb-3">
						<input class="form-control" type="hidden" id="id"
							name="id" value="${produto.id}">
					</div>
					<div class="mb-3">
						<input class="form-control" type="hidden" id="id"
							name="usuario_pai_id" value="${usuario.id}">
					</div>
					<input type="submit" value="Submeter"
						class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</body>
</html>