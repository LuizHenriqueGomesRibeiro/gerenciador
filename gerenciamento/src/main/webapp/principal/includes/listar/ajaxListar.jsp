<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
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
	
	function abrirTabelaTodosFornecedores(){
		jQuery("#tabelaFornecedorUnitario").hide();
		jQuery("#tabelaTodosFornecedores").show();
		var urlAction = document.getElementById('formulario').action;
		var parametros = '&id_produto=' + jQuery("#configuracoesId").val() + '&acao=abrirTodosFornecedores';
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : parametros,
			success : function(json, textStatus, xhr) {
				var fornecedores = JSON.parse(json);
				jQuery('#listaTodosFornecedores > tbody > tr').remove();
			    for(var p = 0; p < fornecedores.length; p++){	
					const fornecedor = new Fornecedor(fornecedores[p].id, fornecedores[p].nome, fornecedores[p].tempoentrega + " dias", 
						parseFloat(fornecedores[p].valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}));
					jQuery('#listaTodosFornecedores > tbody').append(
						'<tr>' + 
							'<td>' + fornecedor.nome + '</td>' + 
							'<td style="color: red;>' + 
								'<a style="color: red;" href="#" onclick="funcoes3(' + fornecedor.id + ')">Deletar fornecedor</a>' + 
							'</td>' + 
							'<td>' + 
							'<a href="#" onclick="abrirFornecedorAntigo(' + "'" + fornecedor.nome + "'" + ')">Selecionar</a>' + 
							'</td>' + 
						'</tr>'
					);
			    }
			}
		});
	}

	function abrirFornecedorAntigo(nome){
		jQuery("#nomeFornecedor").val(nome);
		jQuery("#fornecedorNovoOuNao").val("antigo");
	}
	
	function mudarValorNomeFornecedor(){
		verificarFornecedorNomeBancoDeDados();
		verificarFornecedorNovoOuNao();
	}
	
	function verificarFornecedorNovoOuNao(){
		if(jQuery("#fornecedorNovoOuNao").val() == "antigo"){
			jQuery("#alterouNomeFornecedor").val("alterou");
		}
	}
	
	function verificarFornecedorNomeBancoDeDados(){
		var urlAction = document.getElementById('formulario').action;
		var parametros = '&nomeFornecedor=' + jQuery("#nomeFornecedor").val() + '&acao=verificarHaNomeFornecedor';
		jQuery.ajax({
			method: "get",
			url : urlAction,
			data : parametros,
			success : function(response){
				jQuery("#haNomeFornecedor").val(response.slice(1, -1));
			}
		});		
	}
	
	function loadData(id) {
		jQuery('#nomeFornecedor').val('');
		jQuery('#tempoentrega').val('');
		jQuery('#valor').val('');
		jQuery("#alterouNomeFornecedor").val("naoAlterou");
		jQuery("#fornecedorNovoOuNao").val("novo");
		jQuery("#tabelaTodosFornecedores").hide();
		jQuery("#tabelaFornecedorUnitario").show();
		jQuery("#tabelaFornecedores").show();
		jQuery("#tabelaHistoricoPedidos").hide();
		jQuery("#divNovoPedido").hide();
		jQuery("#divNovoFornecedor").show();
		var urlAction = document.getElementById('formulario').action;
		var parametros = '&id_produto=' + id + '&acao=configuracoes';
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : parametros,
			dataType: "text",
			success : function(response){
				var responseArray = response.split("|");
			    var produto = JSON.parse(responseArray[0]);
			    var fornecedores = JSON.parse(responseArray[1]);
			    
			    jQuery("#insiraNomeFornecedor > p").remove();
			    jQuery("#configuracoesId").val(produto.id);
			    jQuery('#listaFornecedores > tbody > tr').remove();
			    jQuery('#produtoIdIncluir > input').remove();
			    jQuery('#insiraNomeFornecedor').append('<p>Insira um novo fornecedor para ' + produto.nome + '</p>');
			    jQuery('#produtoIdIncluir').append('<input type="hidden" id="idProduto" name="id_produto" value="' + produto.id + '">');
			    
			    for(var p = 0; p < fornecedores.length; p++){
					const fornecedor = new Fornecedor(fornecedores[p].id, fornecedores[p].nome, fornecedores[p].tempoentrega + " dias", 
						parseFloat(fornecedores[p].valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}));
					jQuery('#listaFornecedores > tbody').append(
						'<tr>' + 
							'<td>' + fornecedor.nome + '</td>' + 
							'<td>' + fornecedor.tempo + '</td>' + 
							'<td>' + fornecedor.valor + '</td>' +
							'<td style="color: red;>' + 
								'<a style="color: red;" href="#" onclick="funcoes3(' + fornecedor.id + ')">Deletar fornecedor</a>' + 
							'</td>' + 
							'<td>' + 
								'<a href="#" onclick="abrirDivNovoPedido(' + fornecedor.id + ')">Fazer pedido</a>' + 
							'</td>' + 
						'</tr>'
					);
			    }
			}
		});
	}
	
	function loadPedidos(id){
		jQuery("#tabelaFornecedores").hide();
		jQuery("#tabelaHistoricoPedidos").show();
		var urlAction = document.getElementById('formulario').action;
		var parametros = '&id_produto='+ id + '&acao=historioPedidos';
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : parametros,
			success : function(json, textStatus, xhr) {
				json = JSON.parse(json);
				jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();				
				for(var p = 0; p < json.length; p++){
					const pedido = new Pedido(json[0].dataentrega, json[p].datapedido, json[p].quantidade, json[p].id);
					chamarString(pedido.dataentrega);
					var linkServletConfirmar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + pedido.id + '&quantidade=' + pedido.quantidade + 
						'&acao=confirmarPedido"';
					var linkServletCancelar = 'href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + pedido.id + '&acao=cancelarPedido"';
					jQuery('#tabelaHistoricoPedidos > table > tbody').append(
							'<tr>' + 	
								'<td>' + pedido.dataentrega + '</td>' + 
								'<td>' + pedido.datapedido + '</td>' + 
								'<td><a id="confirmarEntregaPedido" ' + linkServletConfirmar + ' >Confirmar entrega</a></td>' +  
								'<td><a id="confirmarCancelamentoPedido" ' + linkServletCancelar + ' >Cancelar entrega</a></td>' +  
							'</tr>'
						);
				}
			}
		});
	}

	document.querySelectorAll('table.interactive').forEach(element => {
	  element.addEventListener('click', (event) => {
		const row = event.path.find(element => element.tagName === 'TR' && element.parentElement.tagName === 'TBODY');
		if (row) {
		  row.classList.toggle('highlighted');
		}
	  })
	});

	function adicionarFornecedor() {
		if(jQuery("#fornecedorNovoOuNao").val() == "antigo"){
			if(jQuery("#alterouNomeFornecedor").val() == "alterou"){
				if(jQuery("#haNomeFornecedor").val() == "naoHaNome"){
					ajaxCriarFornecedor();
				}else{
					alert("Já há fornecedor com esse nome.");	
				}
			}else{
				ajaxCriarFornecedor();
			}
		}else{
			if(jQuery("#haNomeFornecedor").val() == "naoHaNome"){
				ajaxCriarFornecedor();
			}else{
				alert("Já há fornecedor com esse nome.");				
			}
		}		
	}
	
	function ajaxCriarFornecedor(){
		var urlAction = document.getElementById('formulario').action;
		var nomeFornecedor = document.getElementById('nomeFornecedor').value;
		var tempoentrega = document.getElementById('tempoentrega').value;
		var valor = document.getElementById('valor').value;
		var id = document.getElementById('configuracoesId').value;
		
		var urlAction = document.getElementById('formulario').action;
		var parametros = '&nomeFornecedor=' + nomeFornecedor + '&tempoentrega=' + tempoentrega + '&valor=' + valor + '&id_produto=' + id + '&acao=cadastrarFornecedor';
		jQuery.ajax({
			method : "post",
			url : urlAction,
			data : parametros,
			success : function(json, textStatus, xhr) {
				jQuery('#nomeFornecedor').val('');
				jQuery('#tempoentrega').val('');
				jQuery('#valor').val('');
			}
		});
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

	function loadTodosPedidos(){
		jQuery("#tabelaFornecedores").hide();
		jQuery("#tabelaHistoricoPedidos").show();
		var urlAction = document.getElementById('formulario').action;
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : '&acao=carregarTodosPedidos',
			success : function(json, textStatus, xhr) {
				json = JSON.parse(json);
				jQuery('#tabelaHistoricoPedidos > table > tbody > tr').remove();
				for(var p = 0; p < json.length; p++){
					jQuery('#tabelaHistoricoPedidos > table > tbody').append(
						'<tr>' + 
							'<td>' + json[p].dataentrega + '</td>' + 
							'<td>' + json[p].datapedido + '</td>' +  
							'<td>' + 
								'<a href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + json[p].id + '&id_produto=' + json[p].produto_pai_id.id 
									+ '&quantidade=' + json[p].quantidade + '&acao=confirmarPedido">Confirmar entrega</a>' +  
							'</td>' + 
							'<td>' + 
								'<a style="color: red;" href="servlet_cadastro_e_atualizacao_produtos?id_pedido=' + json[p].id + '&acao=cancelarPedido">Cancelar entrega</a>' + 
							'</td>' + 
						'</tr>'
					);
				}
			}
		});
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
		jQuery("#capturarId").append("<input name='id_fornecedor' id='id_fornecedor' value=" + id + ">");
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
	
	function abrirDivNovoPedido(id){
		dataAtual();
		jQuery("#divNovoPedido").show();
		jQuery("#divNovoFornecedor").hide();
		jQuery("#capturarId").append("<input type='hidden' name='id_fornecedor' id='id_fornecedor' value=" + id + ">");
	}

</script>