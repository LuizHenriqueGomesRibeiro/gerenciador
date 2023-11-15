<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-3.7.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.maskMoney.js"></script>
<link rel="stylesheet" href="https://unpkg.com/98.css" />
<body style="overflow-x: hidden; background-color: #C0C0C0; color: black;">
	<div style="position: relative; top: 12px; left: 12px;">
		<ul class="pagination" style="margin: 0px 0px -1px 0px;">
			<li class="page-item"><button>Configurações</button></li>
			<li class="page-item"><a style="text-decoration: none" href="<%=request.getContextPath()%>/servlet_saida?acao=caixaListar"><button>Ir para caixa</button></a></li>
			<li class="page-item"><a style="text-decoration: none"
				href="<%=request.getContextPath()%>/servlet_saida?acao=financeiro"><button>Ir para setor de contabilidade</button></a></li>
			<li class="page-item"><button>Ajuda</button></li>
			<li class="page-item"><a href="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos?acao=listar"><button>Voltar</button></a></li>
		</ul>
	</div>
	<div style="width: 100%">
		<div style="margin-left: 20px; margin-top: 10px;">
			<h2>Setor de contabilidade</h2>
			<div>
				<form action="<%=request.getContextPath()%>/servlet_saida" method="get" name="formularioSaida" id="formularioSaida">
					<div class="row">
						<div class="col">
							<input type="date" class="form-control" id="dataInicial" name="dataInicial">
						</div>
						<div class="col">
							<input type="date" class="form-control" id="dataFinal" name="dataFinal">
						</div>
					</div>
					<p style="margin-left: 10px;">*Caso as datas não sejam especificadas, o servidor rodará todos os resultados.</p>
				</form>
				<ul class="pagination" style="margin: 0px 0px -1px 0px;">
					<li class="page-item"><button class="page-link" style="text-decoration: none; width: 198px;" onclick="carregarListaVendas()">Carregar lista de vendas</button></li>
				</ul>
			</div>
		</div>
	</div>
	<div style="display: flex; width: 100%;">
		<div style="width: 100%;">
			<div id="divListaVendas" style="display: none; width: 90%; position: relative; margin: auto; top: 14px;" class="sunken-panel">
				<div id="cabecario" style="overflow-y: scroll; overflow-x: none; height: 300px;"></div>
				<div id="tabela"></div>
			</div>
		</div>
	</div>
	<div style="width: 100%;">
		<div style="width: 90%; position: relative; margin: auto;">
			<div id="botao"></div>
		</div>
	</div>
	<div id="divCanvas" style="display: none; width: 90%; position: relative; margin: auto; top: 25px; margin-bottom: 50px;">
		<ul class="tree-view">
			<div style="width: 100%;" id="canvas"></div>
		</ul>
	</div>
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="js/sb-admin-2.min.js"></script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="js/demo/chart-area-demo.js"></script>
    <script src="js/demo/chart-pie-demo.js"></script>
</body>
<script type="text/javascript">

	function graficoVendas(jsonLista4, jsonLista5) {
		Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
		Chart.defaults.global.defaultFontColor = '#858796';
		
		function number_format(number, decimals, dec_point, thousands_sep) {
	
			number = (number + '').replace(',', '').replace(' ', '');
		  	var n = !isFinite(+number) ? 0 : +number,
		    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
		    sep = (typeof thousands_sep === 'undefined') ? '.' : thousands_sep,
		    dec = (typeof dec_point === 'undefined') ? ',' : dec_point,
		    s = '',
		    toFixedFix = function(n, prec) {
		    	var k = Math.pow(10, prec);
		      	return '' + Math.round(n * k) / k;
		    	};
		    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
		    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
		    if (s[0].length > 3) {
		    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
		    }
		    if ((s[1] || '').length < prec) {
		    	s[1] = s[1] || '';
		    	s[1] += new Array(prec - s[1].length + 1).join('0');
		    }
		  	return s.join(dec);
		}
		
		var json = JSON.parse(jsonLista4);
		
		var datas = json.map(function(data) {
			return data.datavenda;
		});

		var valores = json.map(function(data) {
			return data.valortotal;
		});
		
		var json2 = JSON.parse(jsonLista5);
		
		var datas2 = json2.map(function(data) {
			return data.datavenda;
		});

		var valores2 = json2.map(function(data) {
			return data.valortotal;
		});

		var ctx = document.getElementById("myAreaChart");
		var myLineChart = new Chart(
				ctx,{
					type: 'line',
					data: {
						labels: datas,
						datasets: [{
							label: "Vendas",
							lineTension: 0.3,
							backgroundColor: "rgba(78, 115, 223, 0.05)",
							borderColor: "rgba(78, 115, 223, 1)",
							pointRadius: 0,
							pointBackgroundColor: "rgba(78, 115, 223, 1)",
							pointBorderColor: "rgba(78, 115, 223, 1)",
							pointHoverRadius: 0,
							pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
							pointHoverBorderColor: "rgba(78, 115, 223, 1)",
							pointHitRadius: 10,
							pointBorderWidth : 2,
							data: valores
						}],
					},
					options: {
						maintainAspectRatio : false,
						layout: {
							padding: {
								left: 10,
								right: 25,
								top: 25,
								bottom: 0
							}
						},
						scales: {
							xAxes: [ {
								time: {
									unit: 'date'
								},
								gridLines: {
									display: false,
									drawBorder: false
								},
								ticks: {
									maxTicksLimit: 7
								}
							}],
							yAxes: [ {
								ticks: {
									maxTicksLimit : 5,
									padding : 10,
									callback: function(value, index, values) {
										return 'R$' + number_format(value) + ',00';
									}
								},
								gridLines : {
									color : "rgb(234, 236, 244)",
									zeroLineColor : "rgb(234, 236, 244)",
									drawBorder : false,
									borderDash : [ 2 ],
									zeroLineBorderDash : [ 2 ]
								}
							} ],
						},
						legend : {
							display : false
						},
						tooltips : {
							backgroundColor : "rgb(255,255,255)",
							bodyFontColor : "#858796",
							titleMarginBottom : 10,
							titleFontColor : '#6e707e',
							titleFontSize : 14,
							borderColor : '#dddfeb',
							borderWidth : 1,
							xPadding : 15,
							yPadding : 15,
							displayColors : false,
							intersect : false,
							mode : 'index',
							caretPadding : 10,
							callbacks : {
								label : function(tooltipItem, chart) {
									var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label
											|| '';
									return datasetLabel + ': R$'
											+ number_format(tooltipItem.yLabel);
								}
							}
						}
					}
				});
	}
	
	function graficoEntradas(jsonLista2) {
		var json = JSON.parse(jsonLista2);
		Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
		Chart.defaults.global.defaultFontColor = '#858796';
		
		function number_format(number, decimals, dec_point, thousands_sep) {
	
			number = (number + '').replace(',', '').replace(' ', '');
		  	var n = !isFinite(+number) ? 0 : +number,
		    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
		    sep = (typeof thousands_sep === 'undefined') ? '.' : thousands_sep,
		    dec = (typeof dec_point === 'undefined') ? ',' : dec_point,
		    s = '',
		    toFixedFix = function(n, prec) {
		    	var k = Math.pow(10, prec);
		      	return '' + Math.round(n * k) / k;
		    	};
		    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
		    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
		    if (s[0].length > 3) {
		    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
		    }
		    if ((s[1] || '').length < prec) {
		    	s[1] = s[1] || '';
		    	s[1] += new Array(prec - s[1].length + 1).join('0');
		    }
		  	return s.join(dec);
		}
		
		var labels = [];
		var data = [];
		
		for (var i = 0; i < json.valores.length; i++) {
			labels.push(json.valores[i]);
			data.push(json.datas[i]);
		}
		
		var ctx = document.getElementById("myAreaChart");
		var myLineChart = new Chart(
				ctx,{
					type: 'line',
					data: {
						labels: data,
						datasets: [{
							label: "Earnings",
							lineTension: 0.3,
							backgroundColor: "rgba(78, 115, 223, 0.05)",
							borderColor: "rgba(78, 115, 223, 1)",
							pointRadius: 3,
							pointBackgroundColor: "rgba(78, 115, 223, 1)",
							pointBorderColor: "rgba(78, 115, 223, 1)",
							pointHoverRadius: 3,
							pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
							pointHoverBorderColor: "rgba(78, 115, 223, 1)",
							pointHitRadius: 10,
							pointBorderWidth : 2,
							data: labels
						}],
					},
					options: {
						maintainAspectRatio : false,
						layout: {
							padding: {
								left: 10,
								right: 25,
								top: 25,
								bottom: 0
							}
						},
						scales: {
							xAxes: [ {
								time: {
									unit: 'date'
								},
								gridLines: {
									display: false,
									drawBorder: false
								},
								ticks: {
									maxTicksLimit: 7
								}
							}],
							yAxes: [ {
								ticks: {
									maxTicksLimit : 7,
									padding : 10,
									callback: function(value, index, values) {
										return 'R$' + number_format(value) + ',00';
									}
								},
								gridLines : {
									color : "rgb(234, 236, 244)",
									zeroLineColor : "rgb(234, 236, 244)",
									drawBorder : false,
									borderDash : [ 2 ],
									zeroLineBorderDash : [ 2 ]
								}
							} ],
						},
						legend : {
							display : false
						},
						tooltips : {
							backgroundColor : "rgb(255,255,255)",
							bodyFontColor : "#858796",
							titleMarginBottom : 10,
							titleFontColor : '#6e707e',
							titleFontSize : 14,
							borderColor : '#dddfeb',
							borderWidth : 1,
							xPadding : 15,
							yPadding : 15,
							displayColors : false,
							intersect : false,
							mode : 'index',
							caretPadding : 10,
							callbacks : {
								label : function(tooltipItem, chart) {
									var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label
											|| '';
									return datasetLabel + ': R$'
											+ number_format(tooltipItem.yLabel);
								}
							}
						}
					}
				});
	}
	
	</script>
	
	<script type="text/javascript">
	
	jQuery("#cabeçario").hide();
	
	function carregarListaEntradas(){
		
		var urlAction = document.getElementById('formularioSaida').action;
		var dataInicial = document.getElementById('dataInicial').value;
		var dataFinal = document.getElementById('dataFinal').value;
		
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : '&acao=carregarListaEntradas&dataInicial=' + dataInicial + '&dataFinal=' + dataFinal,
			success : function(response) {
				
				jQuery("#botao > a").remove();
				jQuery("#botao").append(
					"<a style='text-decoration: none' href='ServletRelatorios?acao=printFormEntradasPDF&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal + "'>" +
						"<button style=\"page-link\">Imprimir PDF de relatório de entradas</button>" +
					"</a>"
				);
				
				jQuery("#canvas > canvas").remove();
				jQuery("#canvas").append("<canvas id=\"myAreaChart\"></canvas>");
				jQuery("#letrascanvas").remove();
				jQuery("#letrascanvas").append("<h2>Gráfico de entradas e vendas por dia</h2>");
				
				var responseArray = response.split("|");
		        var jsonLista1 = responseArray[0];
		        var jsonLista2 = responseArray[1];
		        var json = JSON.parse(jsonLista2);
		        
				graficoEntradas(jsonLista1);
				
				var quantidadeTotal = json[0].quantidadeTotal;
				quantidadeTotal = quantidadeTotal.toLocaleString();
				quantidadeTotal = quantidadeTotal + " unidades";
				var valores = json[0].valores;
				valores = valores.toLocaleString();
				valores = "R$" + valores + ",00";
				
				jQuery("#tabela > table").remove();
				jQuery("#tabela").append(
					'<table class="interactive">' +
						'<thead>' + 
							'<tr>' + 
								'<th>Quantidades totais</th>' + 
								'<th>Valores totais</th>' + 
							'</tr>' + 	
						'</thead>' + 
						'<tbody>' +
							'<tr>' +
								'<td>' + quantidadeTotal + '</td>' +
								'<td>' + valores + '</td>' +
							'</tr>' +
						'</tbody>' + 
					'</table>'
				);
				
				jQuery("#cabecario > table").remove();
				jQuery("#cabecario").append('<table style="margin-top: 30px;" class="table table-striped table-sm">' +
					'<thead>' + 
						'<tr>' + 
							'<th>Nome</th>' + 
							'<th>Quantidade</th>' + 
							'<th>Valor total</th>' + 
							'<th>Data de pedido</th>' +
							'<th>Data de entrega</th>' +
						'</tr>' + 
					'</thead>' + 
					'<tbody></tbody>' + 
				'</table>');
				
				for(var p = 0; p < json.length; p++){
					var nome = JSON.stringify(json[p].nome);
					var quantidade = JSON.stringify(json[p].quantidade);
					quantidade = quantidade + " unidades";
					var valortotal = JSON.stringify(json[p].valorTotal);
					var valortotalFinal = "R$" + valortotal + ",00";
					var dataentrega = JSON.stringify(json[p].dataentrega);
					var datapedido = JSON.stringify(json[p].datapedido);
					
					jQuery("#cabecario > table > tbody").append(
							"<tr>" + 
								"<td>" + nome + "</td>" + 
								"<td>" + quantidade + "</td>" + 
								"<td>" + valortotalFinal +  "</td>" + 
								"<td>" + datapedido + "</td>" +  
								"<td>" + dataentrega + "</td>" +
							"</tr>");
				}				
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	

	function carregarListaVendas(){
		jQuery("#divCanvas").show();
		jQuery("#divListaVendas").show();
		var urlAction = document.getElementById('formularioSaida').action;
		var dataInicial = document.getElementById('dataInicial').value;
		var dataFinal = document.getElementById('dataFinal').value;
		
		jQuery.ajax({
			method : "get",
			url : urlAction,
			data : '&acao=carregarListaVendas&dataInicial=' + dataInicial + '&dataFinal=' + dataFinal,
			success : function(response) {
				jQuery("#botao > a").remove();
				jQuery("#botao").append(
					"<a style='text-decoration: none' href='ServletRelatorios?acao=printFormVendasPDF&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal + "'>" +
						"<button style=\"position: relative; top: 20px;\">Imprimir PDF de relatório de vendas</button>" +
					"</a>"
				);

				jQuery("#canvas > canvas").remove();
				jQuery("#canvas").append("<canvas id=\"myAreaChart\"></canvas>");
				
				var responseArray = response.split("|");
		        var jsonLista2 = responseArray[0];
		        var jsonLista4 = responseArray[1];
		        var jsonLista5 = responseArray[2];
		        
		        var json = JSON.parse(jsonLista2);
				graficoVendas(jsonLista4, jsonLista5);
				
				var quantidadeTotal = json[0].quantidadeTotal;
				quantidadeTotal = quantidadeTotal.toLocaleString();
				quantidadeTotal = quantidadeTotal + " unidades";
				var valores = json[0].valores;
				valores = valores.toLocaleString();
				valores = "R$" + valores + ",00";
				
				jQuery("#tabela > table").remove();
				jQuery("#tabela").append(
					'<table class="interactive" style="width: 100%;">' +
						'<thead>' + 
							'<tr>' + 
								'<th>Quantidades totais</th>' + 
								'<th>Valores totais</th>' + 
							'</tr>' + 	
						'</thead>' + 
						'<tbody>' +
							'<tr>' +
								'<td>' + quantidadeTotal + '</td>' +
								'<td>' + valores + '</td>' +
							'</tr>' +
						'</tbody>' + 
					'</table>'
				);
				
				jQuery("#letreiro > h2").remove();
				jQuery("#letreiro").append('<h2>Listagem de vendas por saída</h2>');

				jQuery("#cabecario > table").remove();
				jQuery("#cabecario").append(
					'<table class="interactive" style="width: 100%;">' +
						'<thead>' + 
							'<tr>' + 
								'<th>Nome</th>' + 
								'<th>Quantidade</th>' + 
								'<th>Valor total</th>' + 
								'<th>Data da venda</th>' +
							'</tr>' + 	
						'</thead>' + 
						'<tbody></tbody>' + 
					'</table>'
				);
				
				for(var p = 0; p < json.length; p++){
					var nome = JSON.stringify(json[p].nome);
					var quantidade = JSON.stringify(json[p].quantidade);
					quantidade = quantidade + " unidades";
					var valortotal = JSON.stringify(json[p].valortotal);
					valortotal = "R$" + valortotal + ",00";
					var dataentrega = JSON.stringify(json[p].dataentrega);
					
					jQuery("#cabecario > table > tbody").append(
						"<tr>" + 
							"<td>" + nome + "</td>" + 
							"<td>" + quantidade + "</td>" + 
							"<td>" + valortotal +  "</td>" + 
							"<td>" + dataentrega + "</td>" +  
						"</tr>");
				}			
			}
		}).fail(function(xhr, status, errorThrown) {
			alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		});
	}
	
</script>
</html>