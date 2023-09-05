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
		<li class="page-item"><button class="page-link"
				data-toggle="modal" data-target=".ui">Novo registro</button></li>
		<li class="page-item"><button class="page-link">Índice=></button></li>
		<%	
		int totalPagina = (int) request.getAttribute("totalPagina");
		for (int p = 0; p < totalPagina; p++) {
			String url = request.getContextPath() + "/servlet_cadastro_e_atualizacao_produtos?acao=paginar&pagina=" + (p * 10);
			out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
		}
		%>
		<li class="page-item"><button class="page-link">Configurações</button></li>
		<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar"><button class="page-link">Ir para caixa</button></a></li>
		<li class="page-item"><button class="page-link">Gerar relatório</button></li>
		<li class="page-item"><button class="page-link">Ajuda</button></li>
		<li class="page-item"><button class="page-link">Refrescar página</button></li>
		<li class="page-item"><a class="page-link"
			href="principal/principal.jsp">Voltar</a></li>
	</ul>
	<div id="json-content"></div>
	<div style="overflow-y: scroll; height: 250px;">
		<table class="table table-striped table-sm">
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
						<td style="height: 30px; width: 150px;"><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px; padding: 6px 0px 0px 23px;" 
							href="#" id="verPedidos" onclick="loadPedidos(${ml.id})">Ver pedidos</a></td>
						<td style="width: 40px;"><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px; color: red;" href="#" data-toggle="modal" 
							data-target=".exc" onclick="excData(${ml.id})"><p>Excluir</p></a></td>
						<td style="width: 40px;"><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px;" href="#" id="configuracoes" 
							onclick="loadData(${ml.id})">Fornecedores</a></td>
					</tr>
					<div class="modal fade bd-example-modal-lg ada" tabindex="-1"
						id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
						aria-hidden="true">
						<div class="modal-dialog modal-lg 5">
							<div class="modal-content">
								<div style="margin: 20px;">
									<form style="position: relative; width: 90%; margin: auto;"
										action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
										method="post" name="formulario_cadastro_produtos"
										id="formulario">

										<div class="mb-3">
											<label for="exampleInputEmail1" class="form-label">Preço
												por unidade</label> <input class="form-control" id="atualizacaoPreco" name="preco">
											<div class="form-text">...............................</div>
										</div>
										<div class="mb-3">
											<input  type="hidden" class="form-control" id="atualizacaoId" name="id">
										</div>
										<div class="mb-3">
											<label for="exampleInputEmail1" class="form-label">Quantidade
											</label> <input class="form-control" id="atualizacaoQuantidade"
												name="quantidade">
											<div class="form-text">...............................</div>
										</div>
										<div class="mb-3">
											<label for="exampleInputEmail1" class="form-label">Nome
											</label> <input class="form-control" id="atualizacaoNome" name="nome">
											<div class="form-text">...............................</div>
										</div>
										<input value="${usuario.id}" value="cadastrar" name="usuario_pai_id" type="hidden"> 
										<input type="submit" value="Submeter" class="btn btn-primary btn-user btn-block"
										style="margin-bottom: 20px;">
									</form>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</tbody>
		</table>
		<script type="text/javascript">
			jQuery(function(){
				jQuery("#buscar").click(function(){
					jQuery("#atualizacaoPreco").focus();
				});
			});
		</script>
	</div>
	<table class="table table-striped table-sm">
		<thead>
			<tr>
				<th>Soma dos valores</th>
			</tr>
			<tr>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${soma}"></c:out></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</tbody>
	</table>
	<div style="display: none; overflow-y: none;" id="tabelaFornecedores">
		<div style="width: 100%; margin: -16px 0px 0px 0px; padding: 0px 0px 0px 11px;">
			<div class="row">
			<div style="width: 400px;">
				<div class="col-sm">
					<form action="<%=request.getContextPath()%>/servletFornecimento" method="post" name="formularioFornecimento"
					id="formularioFornecimento">
						<div class="form-group">
							<div id="insiraNomeFornecedor"></div>
							<input style="position: relative; margin: -13px 0px 0px 0px;" class="form-control" id="nomeFornecedor" 
							name="nomeFornecedor" placeholder="Nome do novo fornecedor">
						</div>
						<div class="form-group">
							<label for="tempoentrega" class="form-label">
							</label> 
							<input style="position: relative; margin: -13px 0px 0px 0px;" class="form-control" id="tempoentrega" 
							name="tempoentrega" placeholder="tempo de entrega (em dias)">
						</div>
						<div class="form-group">
							<label for="valor" class="form-label">
							</label> 
							<input style="position: relative; margin: -13px 0px 0px 0px;" class="form-control" id="valor" 
							name="valor" placeholder="valor" onkeypress="$(this).mask('00/00/0000')">
						</div>
						<input id="configuracoesId" name="id" type="hidden">
						<button type="button" onclick="funcoes2()" class="btn btn-primary">Submit</button>
					</form>
				</div>
				</div>
				<div style="overflow-y: scroll; overflow-x: none; height: 250px; position: relative; margin: -10px 0px 0px 0px;" class="col-sm">
					<table style="" class="table table-striped table-sm" id="listaFornecedores">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Tempo de entrega</th>
								<th>Valor</th>
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
	<div class="modal fade bd-example-modal-lg ui" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div style="margin: 20px;">
					<form style="position: relative; width: 90%; margin: auto;"
						action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
						method="post" name="formulario_cadastro_produtos" id="formulario">

						<input type="hidden" value="cadastrar" name="acao">

						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">nome</label> <input
								class="form-control" id="nome" name="nome">
							<div class="form-text">...............................</div>
						</div>
						<input value="${usuario.id}" type="hidden" value="cadastrar"
							name="usuario_pai_id"> <input type="submit"
							value="Submeter" class="btn btn-primary btn-user btn-block"
							style="margin-bottom: 20px;">
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
					<input type="hidden" name="id" id="excId">
					<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
						<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
						<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade bd-example-modal-lg dar" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div style="padding: 0px 20px 20px 20px;">
				<form action="<%=request.getContextPath()%>/servletFornecimento" method="get" name="formularioFornecimento" id="formularioFornecimento">
					<div class="form-group">
						<div id="capturarId"></div>
					</div>
					<div class="mb-3">
						<label for="exampleInputEmail1" class="form-label">quantidade</label>
						<input class="form-control" id="quantidade" name="quantidade" onkeypress="$(this).mask('#.###.###.###.###', {reverse: true});">
						<div class="form-text">...............................</div>
					</div>
					<div class="mb-3">
					<!-- aqui teremos um api -->
						<label for="exampleInputEmail1" class="form-label">Data de pedido</label>
						<input class="form-control" id="dataPedido" name="dataPedido" 
						onkeypress="$(this).mask('00/00/0000')">
						<div class="form-text">...............................</div>
					</div>
					<input type="hidden" name="acao" value="incluirPedido">
					<div id="produtoIdIncluir"></div>
					<div id="capturarId"></div>
					<button type="button" onclick="pedido()" class="btn btn-primary">Submit</button>
				</form>
				</div>
			</div>
		</div>
	</div>
	<div id="tabelaHistoricoPedidos" style="width: 100%; overflow-y: scroll; overflow-x: none; height: 250px; position: relative; margin: -27px 0px 0px -16px;" class="col-sm">
		<table style="width: 100%;" class="table table-striped table-sm">
			<thead>
				<tr>
					<th>Data de entrega</th>
					<th>Data do pedido</th>
					<th>Confirmar entrega</th>
					<th>Cancelar entrega</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
	<script type="text/javascript">
	
	function pedido(){
		var urlAction = document.getElementById('formularioFornecimento').action;
		var quantidade = document.getElementById('quantidade').value;
		var dataPedido = document.getElementById('dataPedido').value;
		var idProduto = document.getElementById('idProduto').value;
		var id_fornecedor = document.getElementById('id_fornecedor').value;
		
		jQuery.ajax({

			method : "get",
			url : urlAction,
			data : '&idProduto='+ idProduto + '&dataPedido=' + dataPedido + '&quantidade=' + quantidade + '&fornecimento_pai_id=' + id_fornecedor + '&acao=incluirPedido',
			success : function(json, textStatus, xhr) {
				location.reload();
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
	function loadPedidoIdConfirmar(id1, id2, quantidade){
		var urlAction = document.getElementById('formularioFornecimento').action;
		jQuery.ajax({

			method : "get",
			url : urlAction,
			data : '&id='+ id1 + '&id_produto=' + id2 + '&quantidade=' + quantidade + '&acao=confirmarPedido',
			success : function(json, textStatus, xhr) {
				location.reload();
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
	function loadPedidoIdCancelar(id1, id2){
		var urlAction = document.getElementById('formularioFornecimento').action;
		jQuery.ajax({

			method : "get",
			url : urlAction,
			data : '&id='+ id1 + '&acao=cancelarPedido',
			success : function(json, textStatus, xhr) {
				location.reload();
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
	
	jQuery("#tabelaHistoricoPedidos").hide();
	
	function loadPedidos(id){
		jQuery("#tabelaFornecedores").hide();
		jQuery("#tabelaHistoricoPedidos").show();
		
		var urlAction = document.getElementById('formulario').action;
		jQuery.ajax({

			method : "get",
			url : urlAction,
			data : '&id='+ id + '&acao=historioPedidos',
			success : function(json, textStatus, xhr) {
				jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
				for(var p = 0; p < json.length; p++){	
					var string = JSON.stringify(json[p].dataentrega);
					var substrings = string.split('"');
					var novaString = substrings.join('');
					var string2 = JSON.stringify(json[p].datapedido);
					var substrings2 = string2.split('"');
					var novaString2 = substrings2.join('');
					jQuery('#tabelaHistoricoPedidos > table > tbody')
						.append('<tr><td>' + novaString + 
							'</td><td>' + novaString2 + 
							'</td><td><a href="#" onclick="loadPedidoIdConfirmar('+JSON.stringify(json[p].id)+','+id+','+JSON.stringify(json[p].quantidade)+')">Confirmar entrega</a></td><td><a href="#" onclick="loadPedidoIdCancelar('+JSON.stringify(json[p].id)+','+id+')">Cancelar entrega</a></td></tr>');
				}
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
	function funcoes(id){
		dataAtual();
		loadPedido(id);
	}
	
	function funcoes2(){
		
		var id = document.getElementById('configuracoesId').value;
		
		adicionarFornecedor();
		loadData(id);
	}
	
	function adicionarFornecedor() {
		
		var urlAction = document.getElementById('formularioFornecimento').action;
		var nomeFornecedor = document.getElementById('nomeFornecedor').value;
		var tempoentrega = document.getElementById('tempoentrega').value;
		var valor = document.getElementById('valor').value;
		var id = document.getElementById('configuracoesId').value;
		
		jQuery.ajax({

			method : "post",
			url : urlAction,
			data : '&nomeFornecedor='+nomeFornecedor+'&tempoentrega='+tempoentrega+'&valor='+valor+'&id='+id,
			success : function(json, textStatus, xhr) {
				jQuery('#nomeFornecedor').val('');
				jQuery('#tempoentrega').val('');
				jQuery('#valor').val('');
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
	function dataAtual() {
		  var input = document.getElementById('dataPedido');
		  var dataAtual = new Date();
		  var dia = dataAtual.getDate();
		  var mes = dataAtual.getMonth() + 1;
		  var ano = dataAtual.getFullYear();

		  if (dia < 10) {
		    dia = '0' + dia;
		  }

		  if (mes < 10) {
		    mes = '0' + mes;
		  }

		  input.value = dia + '/' + mes + '/' + ano;
		};
	
		jQuery("#valor").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});
		
		jQuery("#preco").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});
		
		jQuery("#configuracoesPreco").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});
		
		jQuery("#atualizacaoPreco").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});

		function loadData(id) {
			jQuery("#tabelaFornecedores").show();
			jQuery("#tabelaHistoricoPedidos").hide();
			var urlAction = document.getElementById('formulario').action;
			jQuery.ajax({

				method : "get",
				url : urlAction,
				data : '&id=' + id + '&acao=configuracoes',
				dataType: "text",
				success : function(response){
					
					var responseArray = response.split("|");
			        var jsonLista1 = responseArray[0];
			        var jsonLista2 = responseArray[1];
					
			        var jsonObj = JSON.parse(jsonLista2);
			        
			        var objeto = JSON.parse(jsonLista1);
			        var objetoNovamenteId = JSON.stringify(objeto.id);
			        var objetoNovamenteNome = JSON.stringify(objeto.nome);
			        
			        jQuery("#insiraNomeFornecedor > p").remove();
			        jQuery("#configuracoesId").val(objetoNovamenteId);
			        jQuery('#listaFornecedores > tbody > tr').remove();
			        jQuery('#produtoIdIncluir > input').remove();
			        
			        jQuery('#insiraNomeFornecedor').append('<p>Insira um novo fornecedor para ' + objetoNovamenteNome + '</p>');
			        jQuery('#produtoIdIncluir').append('<input type="hidden" id="idProduto" name="idProduto" value="'+objetoNovamenteId+'">');
			        
			        for(var p = 0; p < jsonObj.length; p++){
			        	
			        	const valorNumerico = parseFloat(JSON.stringify(jsonObj[p].valor)); 
			        	const valorMonetario = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}); 
			        	const tempoentregaDias = JSON.stringify(jsonObj[p].tempoentrega) + " dias";
			        	
			        	jQuery('#listaFornecedores > tbody').append('<tr><td>'+JSON.stringify(jsonObj[p].nome)+'</td><td>'+tempoentregaDias+'</td><td>'+valorMonetario+'</td><td style="height: 30px; width: 40px;"><a class="page-link" style="margin: -6px 0px -6px 0px; width: 118px; height: 37px;" href="#" data-toggle="modal" data-target=".dar" onclick="funcoes('+JSON.stringify(jsonObj[p].id)+')">Fazer pedido</a></td></tr>');
			        }
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
			});
		}
		
		function loadPedido(id) {
			jQuery("#capturarId").append("<input type='hidden' name='fornecimento_pai_id' id='id_fornecedor' value="+id+">");
		}
			
		function excData(id) {
			
			var urlAction = document.getElementById('formulario').action;
			jQuery.ajax({

				method : "get",
				url : urlAction,
				data : '&id=' + id + '&acao=exclusaoAjax',
				success : function(json, textStatus, xhr) {
					jQuery("#excId").val(json.id);
				}
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
			});
		}
		
		
	</script>
</html>