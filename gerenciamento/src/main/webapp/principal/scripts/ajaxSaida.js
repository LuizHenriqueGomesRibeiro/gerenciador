class Pedido{
	constructor(dataentrega, datapedido, quantidade, id){
		this.dataentrega = dataentrega;
		this.datapedido = datapedido;
		this.quantidade = quantidade;
		this.id = id;
	}
}

function loadProduto(id){
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
	        alert(produto);
	        alert(mediaValor);
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
	jQuery("#footer").append("<button id=\"clicar\" type=\"button\" class=\"btn btn-primary\" onmousemove=\"limitar2()\" onclick=\"pedido(" + 
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
			location.reload();
		}
	});
}

