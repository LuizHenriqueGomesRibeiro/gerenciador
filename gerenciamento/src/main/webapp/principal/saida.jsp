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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ajaxSaida.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-3.7.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.maskMoney.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://unpkg.com/98.css"/>
</head>
<body style="overflow: hidden; background-color: #C0C0C0; color: black;">
	<div style="position: relative; top: 12px; left: 12px;">
		<ul class="pagination" style="margin: 0px 0px -1px 0px;">
			<li class="page-item"><button>Índice=></button></li>
			<li class="page-item"><button>Configurações</button></li>
			<li class="page-item">
				<button><a style="text-decoration: none" href="<%=request.getContextPath()%>/ServletRelatorios?acao=irParaRelatorios">Ir para relatórios</a></button>
			</li>
			<li class="page-item"><button>Ajuda</button></li>
			<li class="page-item"><button>Refrescar página</button></li>
			<li class="page-item">
				<a href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?acao=listar"><button>Voltar</button></a>
			</li>
		</ul>
	</div>
	<div class="sunken-panel" style="overflow-y: scroll; height: 250px; width: 90%; margin: auto; position: relative; margin-top: 24px;">
		<table style="width: 100%;" class="interactive">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Quantidade em caixa</th>
					<th>Valor total dos pedidos</th>
					<th>Datas de entrega</th>
					<th>Ver</th>
					<th>Saída</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<tr>
						<td><c:out value="${ml.nome}"></c:out></td>
						<td><c:out value="${ml.quantidade}"></c:out></td>
						<td>generico</td>
						<td>generico</td>
						<td><a href="#">Informações</a></td>
						<td><a style="color: red;" id="focus" href="#" onclick="loadProduto(${ml.id})">Vender</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="knowHow" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Como o valor de venda é calculado?</h5>
				</div>
				<div style="padding: 10px 15px 0px 15px;">
					<p>(1) O valor proposto de venda é calculado a partir do dobro das médias dos valores por unidade dos fornecedores.</p>
					<p>(2) O valor de venda deve estar dentro do valor mínimo de venda, que é oitenta por cento do valor estipulado.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div style="width: 100vw;">
		<div style="width: 90%; margin: auto; top: 19px; position: relative;">
			<div id="divFormularioVenda">
				<form action="<%=request.getContextPath()%>/servlet_saida" method="get" name="formularioSaida" id="formularioSaida">
					<div class="mb-3">
						<input type="hidden" name="acao" value="vender"/>
							<div id="letreiro"></div>
						<input name="id_produto" id="id_produto" type="hidden"/>
						<input name="id" id="id" type="hidden">
						<input name="valorHidden" id="valorHidden" type="hidden"> 
						<input class="form-control" id="quantidadeHidden" name="quantidadeHidden" type="hidden">
						<input type="hidden" id="dataVenda" name="dataVenda" onkeypress="$(this).mask('00/00/0000')">
						
						<div class="field-row-stacked" style="width: 200px">
							<input onkeyup="limitar()" id="quantidade" name="quantidade" type="number" placeholder="Quantidade">
						</div>
						
						<div class="field-row-stacked" style="width: 200px">
							<input id="valor" name="valor" type="text" placeholder="Valor de venda">
						</div>
						
						<a onclick="carregarMargem()" href="#" style="text-decoration: none;" data-toggle="modal" data-target="#knowHow">Como calcular o valor da unidade</a>
						
						<button id="clicar" class="btn btn-primary" onmousemove="limitar2()" onclick="pedido(" + produto.id + ")">Save changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		jQuery("#divFormularioVenda").hide();
		
		class Pedido{
			constructor(dataentrega, datapedido, quantidade, id){
				this.dataentrega = dataentrega;
				this.datapedido = datapedido;
				this.quantidade = quantidade;
				this.id = id;
			}
		}
	
		function loadProduto(id){
			jQuery("#divFormularioVenda").show();
			jQuery("#letreiro > p").remove();
			idProduto(id);
			dataAtual();
			var urlAction = document.getElementById('formularioSaida').action;
			var parametros = '&acao=loadProduto&id_produto=' + id;
			loadProdutoAjax(urlAction, parametros);
		}
	
		function loadProdutoAjax(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				success : function(response) {
					var responseArray = response.split("|");
			        var produto = JSON.parse(responseArray[0]);
			        var mediaValor = JSON.parse(responseArray[1]);
			        loadProdutoResultados(produto, mediaValor);
				}
			});
		}
	
		function loadProdutoResultados(produto, mediaValor){
			loadProdutoDomAlterarValores(produto, mediaValor);
			loadProdutoDomAdicionarTags(produto, mediaValor);
		}
	
		function loadProdutoDomAlterarValores(produto, mediaValor){
			jQuery("#quantidade").val(produto.quantidade);
			jQuery("#quantidadeHidden").val(produto.quantidade);
			jQuery("#valorHidden").val(2*mediaValor);
			jQuery("#id").val(produto.id);
			jQuery("#valor").val("R$" + 2*mediaValor + ",00");
		}
	
		function loadProdutoDomAdicionarTags(produto, mediaValor){
			jQuery("#letreiro").append("<p>	*Deve ser inferior ou igual à quantidade em caixa: " + produto.quantidade + " unidades</p>");
			jQuery("#imprimirMargem").append("<p>(4) A atual margem-padrão para " + produto.nome + " é de vinte por cento</p><p>O dobro das médidas do produto '" + 
				produto.nome + "' é R$" + 2*mediaValor + ",00</p>" + "<p>Preço mínimo de venda: R$" + 1.6*mediaValor + ",00</p>");
			jQuery("#botao").append("<button id=\"clicar\" class=\"btn btn-primary\" onmousemove=\"limitar2()\" onclick=\"pedido(" + 
				produto.id + ")\">Save changes</button>");
			jQuery("#footer > button").remove();
			jQuery("#imprimirMargem > p").remove();
		}
				
		function pedido(id){
			var urlAction = document.getElementById('formularioSaida').action;
			var quantidade = document.getElementById('quantidade').value;
			var valor = document.getElementById('valor').value;
			var dataVenda = document.getElementById('dataVenda').value;
			var parametros = '&dataVenda=' + dataVenda + '&valor=' + valor + '&id_produto=' + id + '&quantidade=' + quantidade + '&acao=vender';
			pedidoAjax(urlAction, parametros);
		}
	
		function pedidoAjax(urlAction, parametros){
			jQuery.ajax({
				method : "get",
				url : urlAction,
				data : parametros,
				success : function(json, textStatus, xhr) {
				}
			});
		}
	
		function dataAtual() {
		  	var input = document.getElementById('dataVenda');
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
	
		function limitar2(){
			const input = document.getElementById('valor').value;
			const minValue = document.getElementById('valorHidden').value;
			const realMinValue = 0.8*minValue;
			var inputTrans = input.replace("R$", "").replace(".", "").trim();
			var inputTrans2 = inputTrans.split(",");
			var inputNovo = inputTrans2[0];
			
			if (parseInt(inputNovo) < parseInt(realMinValue)) {
				jQuery("#valor").val("R$" + realMinValue + ",00");
				jQuery("#clicar").attr("hidden", true);
		       	alert("O valor mínimo de venda é R$" + realMinValue + ",00");
		       	jQuery("#clicar").attr("hidden", false);
			}
		}
	
		function limitar(){
			const maxValue = document.getElementById('quantidadeHidden').value;
			const input = document.getElementById('quantidade').value;
			    if (parseInt(input) > parseInt(maxValue)) {
			       	 jQuery("#quantidade").val(maxValue);
			    }
		}
		
		function idProduto(id){
			jQuery("#id_produto").val(id);
		}
	
		jQuery("#valor").maskMoney({
			showSymbol : true,	
			symbol : "R$",
			decimal : ",",
			thousands : "."
		});
	
		jQuery("#valor").focus();
		
	</script>
</html>