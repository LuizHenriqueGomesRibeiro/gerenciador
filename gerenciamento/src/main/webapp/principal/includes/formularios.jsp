<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">

<div class="modal fade bd-example-modal-lg ada0" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 0}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 0}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 0}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 0}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada1" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 1}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 1}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 1}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 1}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada2" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 2}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 2}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 2}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 2}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada3" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 3}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 3}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 3}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 3}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada4" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 0}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 4}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 4}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 4}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada5" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 5}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 5}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 5}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 5}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada6" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 6}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 6}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 6}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 6}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada7" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 7}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 7}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 7}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 7}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada8" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 8}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 8}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 8}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 8}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bd-example-modal-lg ada9" tabindex="-1"
	id="teste" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg 5">
		<div class="modal-content">
			<div style="margin: 20px;">
				<form style="position: relative; width: 90%; margin: auto;"
					action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
					method="post" name="formulario_cadastro_produtos" id="formulario">

					<c:forEach items="${produtos}" var="ml" varStatus="status">
						<c:if test="${status.index eq 9}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Preço
									por unidade</label> <input class="form-control" id="preco" name="preco"
									value="${ml.precoString}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
						<c:if test="${status.index eq 9}">
							<div class="mb-3">
								<input class="form-control" id="id" name="id" value="${ml.id}"
									type="hidden">
							</div>
						</c:if>
						<c:if test="${status.index eq 9}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Quantidade
								</label> <input class="form-control" id="quantidade" name="quantidade"
									value="${ml.quantidade}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>

						<c:if test="${status.index eq 9}">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Nome
								</label> <input class="form-control" id="nome" name="nome"
									value="${ml.nome}">
								<div class="form-text">...............................</div>
							</div>
						</c:if>
					</c:forEach>
					<input value="${usuario.id}" type="hidden" value="cadastrar"
						name="usuario_pai_id"> <input type="submit"
						value="Submeter" class="btn btn-primary btn-user btn-block"
						style="margin-bottom: 20px;">
				</form>
			</div>
		</div>
	</div>
</div>