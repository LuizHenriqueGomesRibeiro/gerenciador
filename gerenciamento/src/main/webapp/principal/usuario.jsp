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
<link rel="stylesheet" href="https://unpkg.com/98.css" />
</head> 
<body style="overflow: hidden; background-color: #C0C0C0; color: black;">
	<div style="position: relative; top: 12px; left: 12px;">
		<ul class="pagination" style="margin: 0px 0px -1px 0px;">
			<li class="page-item"><button data-toggle="modal" data-target=".ui">Novo registro</button></li>
			<li class="page-item"><button>Configurações</button></li>
			<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar"><button>Ir para caixa</button></a></li>
			<li class="page-item"><a style="text-decoration: none"
				href="<%=request.getContextPath()%>/servlet_saida?acao=financeiro"><button>Ir para setor de contabilidade</button></a></li>
			<li class="page-item"><button>Ajuda</button></li>
			<li class="page-item"><button>Refrescar página</button></li>
		</ul>
	</div>
	<div style="width: 100vw;">
		<div style="width: 30vw; position: relative; margin: auto; top: 25px;">
			<c:choose>
				<c:when test="${!validarEntrada}">
					<label class="form-label">
						<h6>Faça o login novamente para ter acesso aos seus dados:</h6>
					</label>
					<form action="servlet_cadastro_e_atualizacao_usuario" method="get" name="formulario_login" id="formulario">
						<input type="hidden" value="validarEntrada" name="acao">
						<div class="form-outline mb-4">
							<label class="form-label" for="form2Example18">Login</label>
							<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="login" name="login" id="login"/>
						</div>
						<div class="form-outline mb-4">
							<label class="form-label" for="form2Example28">Senha</label>
							<input type="password" id="form2Example28" name="senha" id="senha" placeholder="senha" class="form-control form-control-lg"/>
						</div>
						<div class="pt-1 mb-4">
							<button class="btn btn-info btn-lg btn-block" type="submit">Entrar</button>
						</div>
						<p>${mensagem}</p>
					</form>
				</c:when>
				<c:otherwise>
					<label class="form-label">
						<h6>Escolha os campos a serem alterados:</h6>
					</label>
					<label class="form-label">
						<h6 style="color: red;">${mensagem}</h6>
					</label>
					<form action="servlet_cadastro_e_atualizacao_usuario" name="formularioLogin" id="formularioLogin" method="get">
						<div style="margin-bottom: 10px;">
							<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="login atual" name="loginAntigo" id="login"/>
						</div>
						<div style="margin-bottom: 10px;">
							<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="repita login atual" name="loginAntigoRepetido" id="login"/>
						</div>
						<div style="margin-bottom: 10px;">
							<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="login novo" name="loginNovo" id="login"/>
						</div>
						<div style="margin-bottom: 10px;">
							<input type="text" id="form2Example18" class="form-control form-control-lg" placeholder="repita login novo" name="loginNovoRepetido" id="login"/>
						</div>
						<input type="hidden" name="acao" value="mudarLogin"/>
						<button class="btn btn-info btn-lg btn-block" type="submit">Mudar login</button>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>