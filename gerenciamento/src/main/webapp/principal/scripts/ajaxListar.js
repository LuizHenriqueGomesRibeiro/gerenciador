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
	jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
	for(var p = 0; p < jsonObj.length; p++){
		const pedido = new Pedido(json[0].dataentrega, json[p].datapedido, json[p].quantidade, json[p].id);
		chamarString(pedido.dataentrega);
		var linkServletConfirmar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + pedido.id + '&quantidade=' + pedido.quantidade + '&acao=confirmarPedido"';
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
	alert("teste");
	var responseArray = response.split("|");
    imprimirResultadoLoadData(responseArray[0]);
    listarFornecedoresLoadData(responseArray[1]);
}

function imprimirResultadoLoadData(objeto){
    jQuery("#insiraNomeFornecedor > p").remove();
    jQuery("#configuracoesId").val(objeto.id);
    jQuery('#listaFornecedores > tbody > tr').remove();
    jQuery('#produtoIdIncluir > input').remove();
    jQuery('#insiraNomeFornecedor').append('<p>Insira um novo fornecedor para ' + objeto.nome + '</p>');
    jQuery('#produtoIdIncluir').append('<input type="hidden" id="idProduto" name="id_produto" value="' + objeto.id + '">');
}

function listarFornecedoresLoadData(json){
    for(var p = 0; p < json.length; p++){
		const fornecedor = 
			new Fornecedor(json[p].id, json[p].nome, json[p].tempoentrega + " dias", parseFloat(json[p].valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}));
    	imprimirResultadoListaFornecedores(fornecedor);
    }
}

function imprimirResultadoListaFornecedores(fornecedor){
	jQuery('#listaFornecedores > tbody').append(
		'<tr>' + 
			'<td>' + fornecedor.nome + '</td>' + 
			'<td>' + fornecedor.tempo + '</td>' + 
			'<td>' + fornecedor.valor + '</td>' +
			'<td style="height: 30px; color: red; padding: 0px 0px 0px 0px;">' + 
				'<a class="page-link" style="color: red; margin: -1px -28px -6px 0px; width: 160px; height: 37px;" href="#" onclick="funcoes3(' + fornecedor.id + ')">' + 
					'Deletar fornecedor</a>' + 
			'</td>' + 
			'<td>' + 
				'<a class="page-link" style="margin: -6px 0px -6px 0px; width: 118px; height: 37px;" href="#" data-toggle="modal" data-target=".dar" ' + 
					'onclick="funcoes(' + fornecedor.id + ')">Fazer pedido</a>' + 
			'</td>' + 
		'</tr>'
	);
}

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
				'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + json.id + '&acao=cancelarPedido">Cancelar entrega</a>' + 
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