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
<body class="bg-gradient-primary">
	<div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">${msg}</h1>
                                    </div>
                                    <form action="servletLogin" method="post" name="formulario_login" class="signin-form" id="formulario">
                                    
                                    	<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
                                    	
                                        <div class="form-group">
                                            <input class="form-control form-control-user" placeholder="login" name="login" id="login"
												aria-describedby="emailHelp" style="margin-top:20%;">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                            name="senha" id="senha" placeholder="senha">
                                        </div>
                                        <input type="submit" value="Entrar" class="btn btn-primary btn-user btn-block">
                                        <hr>
                                        <a href="index.html" class="btn btn-google btn-user btn-block">
                                            <i class="fab fa-google fa-fw"></i> Login with Google
                                        </a>
                                        <a href="index.html"
											class="btn btn-facebook btn-user btn-block">
                                            <i class="fab fa-facebook-f fa-fw"></i> Login with Facebook
                                        </a>
                                    </form>
                                    <hr>
                                    <div class="text-center" style="margin-bottom:20%;">
                                        <a class="small" href="<%=request.getContextPath()%>/cadastro.jsp">Crie uma conta!</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="js/sb-admin-2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-3.7.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/validacao.js"></script>
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