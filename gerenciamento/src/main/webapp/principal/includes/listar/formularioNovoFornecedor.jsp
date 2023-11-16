<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="divNovoFornecedor" style="width: 300px; margin: auto; margin-top: 40px;">
	<form action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos" method="post" name="formulario" id="formulario">
		<input type="hidden" value="cadastrarFornecedor" name="acao" />
		<div class="field-row">
			<label for="nomeFornecedor">Fornecedor:</label>
			<input oninput="mudarValorNomeFornecedor()" style="width: 250px;" type="text" id="nomeFornecedor" name="nomeFornecedor" 
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
		<input name="fornecedorNovoOuNao" id="fornecedorNovoOuNao" value="novo">
		<input name="haNomeFornecedor" id="haNomeFornecedor" value="naoHaNome">
		<input name="alterouNomeFornecedor" id="alterouNomeFornecedor" value="naoAlterou">
		<div style="width: 100%;">
			<div style="width: 134px; margin: auto; position: relative; top: 10px; display: inline;">
				<button onclick="funcoes2()" class="btn btn-primary" type="button">Criar fornecedor</button>
				<button onclick="abrirTabelaTodosFornecedores()" style="background-color: green;" type="button" class="btn btn-primary">Abrir fornecedores</button>
			</div>
		</div>
	</form>
</div>