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
<!--<script type="text/javascript" src="scripts/ajaxListar.js"></script>-->
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
			<li class="page-item"><a style="text-decoration: none" 
				href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar"><button>Ir para caixa</button></a></li>
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
					<td><a class="page-link" style="margin: -6px 0px -6px 0px; height: 37px; width: 180px;" href="#" 
						id="abrirPedidos" onclick="loadTodosPedidos()">Abrir</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div style="position: relative; width: 89.5%; margin: auto; display: none; overflow-y: none; top: 40px; left: -13px;" id="tabelaFornecedores">
		<div style="width: 100%; margin: -16px 0px 0px 0px; padding: 0px 0px 0px 11px;">
			<div class="row">
				<div style="width: 400px;">
				
				
					<div id="divNovoFornecedor" style="width: 300px; margin: auto; margin-top: 40px;">
						<form action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos" method="get" name="formulario" id="formulario">
							<input type="hidden" value="cadastrarFornecedor" name="acao" />
							<div class="field-row">
								<label for="nomeFornecedor">Fornecedor:</label> 
								<input style="width: 250px;" id="nomeFornecedor" type="text" id="nomeFornecedor" name="nomeFornecedor" 
									placeholder="Nome do novo fornecedor"/>
							</div>
							<div class="field-row">
								<label for="tempoentrega">Tempo de entrega:</label> 
								<input style="width: 220px;" id="tempoentrega" type="text" id="tempoentrega" name="tempoentrega" placeholder="tempo de entrega (em dias)"/>
							</div>
							<div class="field-row">
								<label for="valor">Valor por unidade:</label> 
								<input style="width: 222px;" type="text" id="valor" name="valor" placeholder="valor">
							</div>
							<input type="hidden" id="configuracoesId" name="id">
							<div style="width: 100%;">
								<div style="width: 134px; margin: auto; position: relative; top: 10px;">
									<button type="button" onclick="funcoes2()" class="btn btn-primary">Criar fornecedor</button>
								</div>
							</div>
						</form>
					</div>



					<div id="divNovoPedido" style="width: 300px; margin: auto; margin-top: 40px; display: none;">
						<form action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos" method="post" name="formulario" id="formulario">
							<div class="form-group">
								<div id="capturarId"></div>
							</div>
							<div class="field-row">
								<label for="exampleInputEmail1" class="form-label">Quantidade:</label>
								<input type="text" id="quantidade" name="quantidade" onkeypress="$(this).mask('#.###.###.###.###', {reverse: true});"
									placeholder="quantidade">
							</div>
							<div class="field-row">
								<label for="exampleInputEmail1" class="form-label">Data de pedido:</label>
								<input type="text" id="dataPedido" name="dataPedido" onkeypress="$(this).mask('00/00/0000')" placeholder="data de pedido">
							</div>
							<input type="hidden" name="acao" value="incluirPedido">
							<div id="produtoIdIncluir"></div>
							<div id="capturarId"></div>
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>



				<div style="width: calc(100% - 400px); overflow-y: scroll; overflow-x: none; height: 250px; position: relative;" class="sunken-panel">
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
						method="get" name="formulario_cadastro_produtos" id="formulario">

						<input type="hidden" value="cadastrarProduto" name="acao">

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
					<input type="hidden" name="id_produto" id="excId">
					<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
						<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
						<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 
	<div class="modal fade bd-example-modal-lg dar" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div style="padding: 0px 20px 20px 20px;">
					<form action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos" method="post" name="formulario" id="formulario">
						<div class="form-group">
							<div id="capturarId"></div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">quantidade</label>
							<input class="form-control" id="quantidade" name="quantidade" onkeypress="$(this).mask('#.###.###.###.###', {reverse: true});">
							<div class="form-text">...............................</div>
						</div>
						<div class="mb-3">
							<label for="exampleInputEmail1" class="form-label">Data de pedido</label>
							<input class="form-control" id="dataPedido" name="dataPedido" onkeypress="$(this).mask('00/00/0000')">
							<div class="form-text">...............................</div>
						</div>
						<input type="hidden" name="acao" value="incluirPedido">
						<div id="produtoIdIncluir"></div>
						<div id="capturarId"></div>
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	--> 
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
	<script type="text/javascript">
		document.querySelectorAll('table.interactive').forEach(element => {
		  element.addEventListener('click', (event) => {
			const row = event.path.find(element => element.tagName === 'TR' && element.parentElement.tagName === 'TBODY');
			if (row) {
			  row.classList.toggle('highlighted');
			}
		  })
		});
	
		class Pedido{
			constructor(dataentrega, datapedido, quantidade, id){
				this.dataentrega = dataentrega;
				this.datapedido = datapedido;
				this.quantidade = quantidade;
				this.id = id;
			}
		}
	
		class Fornecedor{
			constructor(id, nome, tempo, valor){
				this.id = id;
				this.nome = nome;
				this.tempo = tempo;
				this.valor = valor;
			}
		}
				
		function adicionarFornecedor() {
			var urlAction = document.getElementById('formulario').action;
			var nomeFornecedor = document.getElementById('nomeFornecedor').value;
			var tempoentrega = document.getElementById('tempoentrega').value;
			var valor = document.getElementById('valor').value;
			var id = document.getElementById('configuracoesId').value;
			var parametros = '&nomeFornecedor=' + nomeFornecedor + '&tempoentrega=' + tempoentrega + '&valor=' + valor + '&id_produto=' + id + '&acao=cadastrarFornecedor';
			ajaxAdicionarFornecedor(urlAction, parametros);
		}
	
		function ajaxAdicionarFornecedor(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				success : function(json, textStatus, xhr) {
					resultadoAdicionarFornecedor();
				}
			});
		}
	
		function resultadoAdicionarFornecedor(){
			jQuery('#nomeFornecedor').val('');
			jQuery('#tempoentrega').val('');
			jQuery('#valor').val('');
		}
	
		function loadPedidos(id){
			jQuery("#tabelaFornecedores").hide();
			jQuery("#tabelaHistoricoPedidos").show();
			var urlAction = document.getElementById('formulario').action;
			var parametros = '&id_produto='+ id + '&acao=historioPedidos';
			ajaxLoadPedidos(urlAction, parametros);
		}
	
		function ajaxLoadPedidos(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				success : function(json, textStatus, xhr) {
					resultadoLoadPedidos(json);
				}
			});
		}
	
		function resultadoLoadPedidos(json){
			json = JSON.parse(json);
			jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
			for(var p = 0; p < json.length; p++){
				const pedido = new Pedido(json[0].dataentrega, json[p].datapedido, json[p].quantidade, json[p].id);
				chamarString(pedido.dataentrega);
				var linkServletConfirmar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + pedido.id + '&quantidade=' + pedido.quantidade + 
					'&acao=confirmarPedido"';
				var linkServletCancelar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + pedido.id + '&acao=cancelarPedido"';
				imprimirResultadoLoadPedidos(pedido, linkServletConfirmar, linkServletCancelar);
			}
		}
	
		function imprimirResultadoLoadPedidos(pedido, linkServletConfirmar, linkServletCancelar){
			jQuery('#tabelaHistoricoPedidos > table > tbody').append(
				'<tr>' + 	
					'<td>' + pedido.dataentrega + '</td>' + 
					'<td>' + pedido.datapedido + '</td>' + 
					'<td><a id="confirmarEntregaPedido" ' + linkServletConfirmar + ' >Confirmar entrega</a></td>' +  
					'<td><a id="confirmarCancelamentoPedido" ' + linkServletCancelar + ' >Cancelar entrega</a></td>' +  
				'</tr>'
			);
		}
	
		function deletarFornecedor(id) {
			var urlAction = document.getElementById('formulario').action;
			var parametros = '&acao=deletarFornecedor&id_fornecedor=' + id;
			ajaxAdicionarFornecedor(urlAction, parametros);
		}
	
		function ajaxDeletarFornecedor(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				success : function(json, textStatus, xhr) {
				}
			});
		}
	
		function loadData(id) {
			jQuery("#tabelaFornecedores").show();
			jQuery("#tabelaHistoricoPedidos").hide();
			jQuery("#divNovoPedido").hide();
			jQuery("#divNovoFornecedor").show();
			var urlAction = document.getElementById('formulario').action;
			var parametros = '&id_produto=' + id + '&acao=configuracoes';
			ajaxLoadData(urlAction, parametros);
		}
	
		function ajaxLoadData(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				dataType: "text",
				success : function(response){
					resultadoLoadData(response);
				}
			});
		}
	
		function resultadoLoadData(response){
			var responseArray = response.split("|");
		    var produto = JSON.parse(responseArray[0]);
		    var fornecedores = JSON.parse(responseArray[1]);
		    imprimirResultadoLoadData(produto);
		    listarFornecedoresLoadData(fornecedores);
		}
	
		function imprimirResultadoLoadData(produto){
		    jQuery("#insiraNomeFornecedor > p").remove();
		    jQuery("#configuracoesId").val(produto.id);
		    jQuery('#listaFornecedores > tbody > tr').remove();
		    jQuery('#produtoIdIncluir > input').remove();
		    jQuery('#insiraNomeFornecedor').append('<p>Insira um novo fornecedor para ' + produto.nome + '</p>');
		    jQuery('#produtoIdIncluir').append('<input type="hidden" id="idProduto" name="id_produto" value="' + produto.id + '">');
		}
	
		function listarFornecedoresLoadData(fornecedores){
		    for(var p = 0; p < fornecedores.length; p++){
				const fornecedor = new Fornecedor(fornecedores[p].id, fornecedores[p].nome, fornecedores[p].tempoentrega + " dias", 
					parseFloat(fornecedores[p].valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}));
		    	imprimirResultadoListaFornecedores(fornecedor);
		    }
		}
		
		function imprimirResultadoListaFornecedores(fornecedor){
			jQuery('#listaFornecedores > tbody').append(
				'<tr>' + 
					'<td>' + fornecedor.nome + '</td>' + 
					'<td>' + fornecedor.tempo + '</td>' + 
					'<td>' + fornecedor.valor + '</td>' +
					'<td style="color: red;>' + 
						'<a style="color: red;" href="#" onclick="funcoes3(' + fornecedor.id + ')">' + 
							'Deletar fornecedor</a>' + 
					'</td>' + 
					'<td>' + 
						'<a href="#" onclick="abrirDivNovoPedido(' + fornecedor.id + ')">Fazer pedido</a>' + 
					'</td>' + 
				'</tr>'
			);
		}
		
		function abrirDivNovoPedido(id){
			jQuery("#divNovoPedido").show();
			jQuery("#divNovoFornecedor").hide();
		}
		
		/*
		function imprimirResultadoListaFornecedores(fornecedor){
			jQuery('#listaFornecedores > tbody').append(
				'<tr>' + 
					'<td>' + fornecedor.nome + '</td>' + 
					'<td>' + fornecedor.tempo + '</td>' + 
					'<td>' + fornecedor.valor + '</td>' +
					'<td style="color: red;>' + 
						'<a style="color: red;" href="#" onclick="funcoes3(' + fornecedor.id + ')">' + 
							'Deletar fornecedor</a>' + 
					'</td>' + 
					'<td>' + 
						'<a href="#" data-toggle="modal" data-target=".dar" ' + 
							'onclick="funcoes(' + fornecedor.id + ')">Fazer pedido</a>' + 
					'</td>' + 
				'</tr>'
			);
		}
		*/
		function loadTodosPedidos(){
			jQuery("#tabelaFornecedores").hide();
			jQuery("#tabelaHistoricoPedidos").show();
			var urlAction = document.getElementById('formulario').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&acao=carregarTodosPedidos',
				success : function(json, textStatus, xhr) {
					loadTodosPedidosResultado(json);
				}
			});
		}
	
		function loadTodosPedidosResultado(json){
			json = JSON.parse(json);
			jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
			for(var p = 0; p < json.length; p++){
				loadTodosPedidosImprimir(json[p]);
			}
		}
	
		function loadTodosPedidosImprimir(json){
			jQuery('#tabelaHistoricoPedidos > table > tbody').append(
				'<tr>' + 
					'<td>' + json.dataentrega + '</td>' + 
					'<td>' + json.datapedido + '</td>' +  
					'<td>' + 
						'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + json.id + '&id_produto=' + json.produto_pai_id.id 
							+ '&quantidade=' + json.quantidade + '&acao=confirmarPedido">Confirmar entrega</a>' +  
					'</td>' + 
					'<td>' + 
						'<a style="color: red;" href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + json.id + '&acao=cancelarPedido">Cancelar entrega</a>' + 
					'</td>' + 
				'</tr>'
			);
		}
	
		function excData(id){
			var urlAction = document.getElementById('formulario').action;
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : '&id_produto=' + id + '&acao=validarExclusao',
				success : function(json, textStatus, xhr) {
					location.reload();
				}
			});
		}
	
		jQuery('#configuracoes, #abrirPedidos, #verPedidos, #confirmarEntregaPedido, #confirmarCancelamentoPedido').click(function(event) {
			  event.preventDefault(); 
		});
		
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
			showSymbol: true,
			symbol: "R$",
			decimal: ",",
			thousands: "."
		});

		jQuery("#preco").maskMoney({
			showSymbol: true,
			symbol: "R$",
			decimal: ",",
			thousands: "."
		});

		jQuery("#configuracoesPreco").maskMoney({
			showSymbol: true,
			symbol: "R$",
			decimal: ",",
			thousands: "."
		});

		jQuery("#atualizacaoPreco").maskMoney({
			showSymbol: true,
			symbol: "R$",
			decimal: ",",
			thousands: "."
		});

		function loadPedido(id) {
			jQuery("#capturarId").append("<input type='hidden' name='id_fornecedor' id='id_fornecedor' value=" + id + ">");
		}

		function chamarString(data) {
			jQuery("#tabelaHistoricoPedidos").append("<input type='hidden' id='capturarData' value=" + data + "></input>");
		}

		function funcoes(id) {
			dataAtual();
			loadPedido(id);
		}

		function funcoes2() {
			var id = document.getElementById('configuracoesId').value;
			adicionarFornecedor();
			loadData(id);
		}

		function funcoes3(id) {
			deletarFornecedor(id);
			loadData(id);
		}

		jQuery("#tabelaHistoricoPedidos").hide();

	</script>
</html>