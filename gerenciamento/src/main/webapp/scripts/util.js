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

function excData(id) {
	jQuery("#excId").val(id);
		}