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
	var jsonObj = JSON.parse(json);
	jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
	for(var p = 0; p < jsonObj.length; p++){
		var dataentrega = JSON.stringify(jsonObj[0].dataentrega);
		var datapedido = JSON.stringify(jsonObj[p].datapedido);
		var quantidade = JSON.stringify(jsonObj[p].quantidade);
		var id = JSON.stringify(jsonObj[p].id);
		chamarString(dataentrega);
		var linkServletConfirmar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + id + '&quantidade=' + quantidade + '&acao=confirmarPedido"';
		var linkServletCancelar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + id + '&acao=cancelarPedido"';
		imprimirResultadoLoadPedidos(dataentrega, datapedido, linkServletConfirmar, linkServletCancelar);
	}
}

function imprimirResultadoLoadPedidos(dataentrega, datapedido, linkServletConfirmar, linkServletCancelar){
	jQuery('#tabelaHistoricoPedidos > table > tbody').append(
		'<tr>' + 	
			'<td>' + dataentrega + '</td>' + 
			'<td>' + datapedido + '</td>' + 
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
	}).fail(function(xhr, status, errorThrown) {
		alert('Erro ao buscar usuário por nome: ' + xhr.responseText);	
	});
}

function resultadoLoadData(response){
	var responseArray = response.split("|");
    novoFornecedorLoadData(JSON.parse(responseArray[0]));
    listarFornecedoresLoadData(JSON.parse(responseArray[1]));
}

function novoFornecedorLoadData(objeto){
    var objetoNovamenteId = JSON.stringify(objeto.id);
    var objetoNovamenteNome = JSON.stringify(objeto.nome);
	imprimirResultadoLoadData(objetoNovamenteId, objetoNovamenteNome);
}

function imprimirResultadoLoadData(objetoNovamenteId, objetoNovamenteNome){
    jQuery("#insiraNomeFornecedor > p").remove();
    jQuery("#configuracoesId").val(objetoNovamenteId);
    jQuery('#listaFornecedores > tbody > tr').remove();
    jQuery('#produtoIdIncluir > input').remove();
    jQuery('#insiraNomeFornecedor').append('<p>Insira um novo fornecedor para ' + objetoNovamenteNome + '</p>');
    jQuery('#produtoIdIncluir').append('<input type="hidden" id="idProduto" name="id_produto" value="' + objetoNovamenteId + '">');
}

function listarFornecedoresLoadData(jsonObj){
    for(var p = 0; p < jsonObj.length; p++){
    	const valorMonetario = parseFloat(JSON.stringify(jsonObj[p].valor)).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}); 
    	const tempoentregaDias = JSON.stringify(jsonObj[p].tempoentrega) + " dias";
    	const nome = JSON.stringify(jsonObj[p].nome);
    	const id = JSON.stringify(jsonObj[p].id);
    	imprimirResultadoListaFornecedores(valorMonetario, tempoentregaDias, nome, id);
    }
}

function imprimirResultadoListaFornecedores(valorMonetario, tempoentregaDias, nome, id){
	jQuery('#listaFornecedores > tbody').append(
		'<tr>' + 
			'<td>' + nome + '</td>' + 
			'<td>'+tempoentregaDias+'</td>' + 
			'<td>'+valorMonetario+'</td>' +
			'<td style="height: 30px; color: red; padding: 0px 0px 0px 0px;">' + 
				'<a class="page-link" style="color: red; margin: -1px -28px -6px 0px; width: 160px; height: 37px;" href="#" onclick="funcoes3(' + id + ')">' + 
					'Deletar fornecedor</a>' + 
			'</td>' + 
			'<td>' + 
				'<a class="page-link" style="margin: -6px 0px -6px 0px; width: 118px; height: 37px;" href="#" data-toggle="modal" data-target=".dar" ' + 
					'onclick="funcoes(' + id + ')">Fazer pedido</a>' + 
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
	var jsonObj = JSON.parse(json);
	for(var p = 0; p < jsonObj.length; p++){
		var string = JSON.stringify(jsonObj[p].dataentrega);
		var string2 = JSON.stringify(jsonObj[p].datapedido);
		var id_pedido = JSON.stringify(jsonObj[p].id);
		var id_produto = JSON.stringify(jsonObj[p].produto_pai_id.id);
		var quantidade = JSON.stringify(jsonObj[p].quantidade);
		loadTodosPedidosImprimir(string, string2, id_pedido, id_produto, quantidade);
	}
}

function loadTodosPedidosImprimir(string, string2, id_pedido, id_produto, quantidade){
	jQuery('#tabelaHistoricoPedidos > table > tbody').append(
		'<tr>' + 
			'<td>' + string + '</td>' + 
			'<td>' + string2 + '</td>' +  
			'<td>' + 
				'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + id_pedido + '&id_produto=' + id_produto + '&quantidade=' + quantidade + 
					'&acao=confirmarPedido">Confirmar entrega</a>' +  
			'</td>' + 
			'<td>' + 
				'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + id_pedido + '&acao=cancelarPedido">Cancelar entrega</a>' + 
			'</td>' + 
		'</tr>'
	);
}

/*

function loadTodosPedidosResultado(json){
	jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
	var jsonObj = JSON.parse(json);
	for(var p = 0; p < jsonObj.length; p++){
		var jsonString = JSON.stringify(jsonObj[p]);
		loadTodosPedidosImprimir(jsonString);
	}
}

function loadTodosPedidosImprimir(jsonString){
	jQuery('#tabelaHistoricoPedidos > table > tbody').append(
		'<tr>' + 
			'<td>' + jsonString.dataentrega + '</td>' + 
			'<td>' + jsonString.datapedido + '</td>' +  
			'<td>' + 
				'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + jsonString.id + '&id_produto=' + jsonString.produto_pai_id.id 
					+ '&quantidade=' + jsonString.quantidade + '&acao=confirmarPedido">Confirmar entrega</a>' +  
			'</td>' + 
			'<td>' + 
				'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + jsonString.id + '&acao=cancelarPedido">Cancelar entrega</a>' + 
			'</td>' + 
		'</tr>'
	);
}
*/

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