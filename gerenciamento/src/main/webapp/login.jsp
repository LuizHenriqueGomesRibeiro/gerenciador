<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=request.getContextPath()%>/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/sb-admin-2.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://unpkg.com/98.css"/>
</head>
<body style="overflow: hidden; background-color: #C0C0C0; color: black;">
	<section class="vh-100">
		<div class="container-fluid">
			<div class="row">
				<div style="width: 600px;" class="col-sm-6 text-black">
					<div style="width: 350px;" class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
						<div style="width: 70%; position: absolute; left: 50%; top: 50%; transform: translate(-50%, -50%);">
							<form action="servletLogin" method="post" name="formulario_login" id="formulario">

								<input type="hidden" value="<%=request.getParameter("url")%>" name="url">

								<div class="form-outline mb-4">
									<label class="form-label" for="form2Example18">Login</label>
									<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="login" name="login" id="login" />
								</div>
								<div class="form-outline mb-4">
									<label class="form-label" for="form2Example28">Senha</label>
									<input type="password" id="form2Example28" name="senha" id="senha" placeholder="senha" class="form-control form-control-lg"/>
								</div>
								<div class="pt-1 mb-4">
									<button class="btn btn-info btn-lg btn-block" type="submit">Entrar</button>
								</div>
								<p>${msg}</p>
								<p>
									Não tem uma conta? 
									<a href="<%=request.getContextPath()%>/cadastro.jsp" class="link-info">Cadastro</a>
								</p>
							</form>
						</div>
					</div>
				</div>
				<div class="col-sm-6 px-0 d-none d-sm-block" style="position: relative; left: 20px;">
					<img
						src="https://images.pexels.com/photos/459728/pexels-photo-459728.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
						alt="Login image" class="w-100 vh-100"
						style="object-fit: cover; object-position: left;">
				</div>
			</div>
		</div>
	</section>
	<script src="<%=request.getContextPath() %>/vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath() %>/vendor/jquery-easing/jquery.easing.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/sb-admin-2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-3.7.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/validacao.js"></script>
</body>
</html>