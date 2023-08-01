<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/css/style.css">
<title></title>
</head>
<body class="img js-fullheight"
	style="background-image: url(assets/images/bg.jpg);">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">Cadastro</h2>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">
						<h3 class="mb-4 text-center">${msg}</h3>
						<form action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_usuario" method="post" name="formulario_cadastro" class="signin-form">
						
						    <input type="hidden" name="acao" id="acao" value="">
						    
							<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
							
							<p class="w-100 text-center">${msg_login}</p>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="login" name="login">
							</div>
							<p class="w-100 text-center">${msg_email}</p>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="email" name="email">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="nome" name="nome">
							</div>
							<div class="form-group">
								<input id="password-field" type="password" class="form-control" name="senha"
									placeholder="senha"> <span
									toggle="#password-field"
									class="fa fa-fw fa-eye field-icon toggle-password"></span>
							</div>
							<div class="form-group">
								<input class="form-control btn btn-primary submit px-3" type="submit" value="Cadastrar-se">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/popper.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/main.js"></script>
</body>
</html>