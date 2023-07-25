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
					<h2 class="heading-section">Login #10</h2>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">
						<p class="w-100 text-center">${msg}</p>
						<form action="servletLogin" method="post" name="formulario_login" class="signin-form">
						
							<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
							
							<div class="form-group">
								<input type="text" class="form-control" placeholder="login" name="login">
							</div>
							<div class="form-group">
								<input id="password-field" type="password" class="form-control" name="senha"
									placeholder="senha"> <span
									toggle="#password-field"
									class="fa fa-fw fa-eye field-icon toggle-password"></span>
							</div>
							<div class="form-group">
								<input class="form-control btn btn-primary submit px-3" type="submit" value="Entrar">
							</div>
							<div class="form-group d-md-flex">
								<div class="w-50">
									<label class="checkbox-wrap checkbox-primary">Me lembre<input type="checkbox" checked> <span
										class="checkmark"></span>
									</label>
								</div>
								<div class="w-50 text-md-right">
									<a href="<%=request.getContextPath()%>/cadastro.jsp" style="color: #fff">Não tem uma conta?</a>
								</div>
							</div>
						</form>
						<p class="w-100 text-center">&mdash; Me siga no: &mdash;</p>
						<div class="social d-flex text-center">
							<a href="#" class="px-2 py-2 mr-md-1 rounded"><span
								class="ion-logo-facebook mr-2"></span> GitHub</a>
						</div>
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