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
			<li class="page-item"><button>Índice=></button></li>
			<%	
			int totalPagina = (int) request.getAttribute("totalPagina");
			for (int p = 0; p < totalPagina; p++) {
				String url = request.getContextPath() + "/servlet_cadastro_e_atualizacao_produtos?acao=paginar&pagina=" + (p * 10);
				out.print("<li style=\"width: 25px;\" class=\"page-item\"><button onclick=\"window.location.href=" + url + "\">" + (p + 1) + "</button></li>");
			}
			%>
			<li class="page-item"><button>Configurações</button></li>
			<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar"><button>Ir para caixa</button></a></li>
			<li class="page-item"><a style="text-decoration: none"
				href="<%=request.getContextPath()%>/servlet_saida?acao=financeiro"><button>Ir para setor de contabilidade</button></a></li>
			<li class="page-item"><button>Ajuda</button></li>
			<li class="page-item"><button>Refrescar página</button></li>
			<li class="page-item"><button onclick="window.location.href='principal/principal.jsp'">Voltar</button></li>
		</ul>
	</div>
	<div id="json-content"></div>
	<div class="example">
		<div class="sunken-panel" style="position: relative; margin: auto; margin-bottom: 12px; margin-top: 24px; height: 250px; width: 90%;">
			<table style="width: 100%;" class="interactive">
				<thead>
					<tr>
					<th>Nome</th>
					<th>Quantidade pedida</th>
					<th>Valor total dos pedidos</th>
					<th>Datas de entrega</th>
					<th>Excluir</th>
					<th>Configurações</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${produtos}" var="ml" varStatus="status">
					<tr>
						<td><c:out value="${ml.nome}"></c:out></td>
						<td><c:out value="${ml.quantidadePedidaString}"></c:out></td>
						<td><c:out value="${ml.valorTotalString}"></c:out></td>
						<td><a class="" href="#" id="verPedidos" onclick="loadPedidos(${ml.id})">Ver pedidos</a></td>
						<td><a href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?id_produto=${ml.id}&acao=validarExclusao" 
							style="color: red;">Excluir</a></td>
						<td><a class="" href="#" id="configuracoes" onclick="loadData(${ml.id})">Fornecedores</a></td>
					</tr>
					<jsp:include page="includes/listar/formularioIncluirPedido.jsp"></jsp:include>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(function(){
			jQuery("#buscar").click(function(){
				jQuery("#atualizacaoPreco").focus();
			});
		});
	</script>
	<div style="position: relative; margin: auto; height: 58px; overflow: hidden; width: 90%" class="sunken-panel">
		<table style="position: relative; margin-bottom: 26px; color: black;" class="table table-striped table-sm">
			<thead>
				<tr>
					<th>Soma dos valores dos pedidos</th>
					<th>Abrir todos os pedidos</th>
				</tr>
			</thead>
			<tbody style="background-color: white;">
				<tr>
					<td><c:out value="${soma}"></c:out></td>
					<td>
						<a href="#" id="abrirPedidos" onclick="loadTodosPedidos()">Abrir</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div style="position: relative; width: 89.5%; margin: auto; display: none; overflow-y: none; top: 40px; left: -13px;" id="tabelaFornecedores">
		<div style="width: 100%; margin: -16px 0px 0px 0px; padding: 0px 0px 0px 11px;">
			<div class="row">
				<div style="width: 400px;">
					<jsp:include page="includes/listar/formularioNovoFornecedor.jsp"></jsp:include>
					<jsp:include page="includes/listar/formularioNovoPedido.jsp"></jsp:include>
				</div>
				<div id="tabelaFornecedorUnitario" style="width: calc(100% - 400px); overflow-y: scroll; overflow-x: none; height: 250px; position: relative;" 
				class="sunken-panel">
					<table style="width: 100%;" class="interactive" id="listaFornecedores">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Tempo de entrega</th>
								<th>Valor</th>
								<th>Configurações</th>
								<th>Pedidos</th>
							</tr>
						</thead>
						<tbody>	
						</tbody>
					</table>
				</div>
				<div id="tabelaTodosFornecedores" style="width: calc(100% - 400px); overflow-y: scroll; overflow-x: none; height: 250px; position: relative;" 
				class="sunken-panel">
					<table style="width: 100%;" class="interactive" id="listaTodosFornecedores">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Configurações</th>
								<th>Selecionar</th>
							</tr>
						</thead>
						<tbody>	
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(function(){
			jQuery("#configuracoes").click(function(){
				jQuery("#configuracoesPreco").focus();
			});
		});
		</script>
	<a class="scroll-to-top rounded" href="#page-top"> 
		<i class="fas fa-angle-up"></i>
	</a>
	<div class="modal fade bd-example-modal-lg ui" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div style="margin: 20px;">
					<form style="position: relative; width: 90%; margin: auto;"
						action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
						method="post" name="formulario_cadastro_produtos" id="formulario">
						<input type="hidden" value="cadastrarProduto" name="acao">
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">nome</label> 
							<input class="form-control" id="nome" name="nome">
						</div>
						<input value="${usuario.id}" type="hidden" value="cadastrar" name="usuario_pai_id"> 
						<input type="submit" value="Submeter" class="btn btn-primary btn-user btn-block" style="margin-bottom: 20px;">
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal exc" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Exclusão de registros</h5>
				</div>
				<div class="modal-body">
					<h5>Você tem certeza de que quer excluir este registro?</h5>
				</div>
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="get" name="formulario_cadastro_produtos" id="formulario">
					<input type="hidden" name="acao" id="acao" value="excluir">
					<input type="hidden" name="id_produto" id="excId">
					<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
						<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
						<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
					</div>
				</form>
			</div>
		</div>
	</div>
	<div style="width: 100%; margin-top: 13px;">
		<div style="width: 90%; margin: auto;" id="tabelaHistoricoPedidos" class="sunken-panel">
			<table style="width: 100%;" class="interactive">
				<thead>
					<tr>
						<th>Data de entrega</th>
						<th>Data do pedido</th>
						<th>Confirmar entrega</th>
						<th>Cancelar entrega</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<jsp:include page="includes/listar/ajaxListar.jsp"></jsp:include>
</html>