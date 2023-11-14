<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>