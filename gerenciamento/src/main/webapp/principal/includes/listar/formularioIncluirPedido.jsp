<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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