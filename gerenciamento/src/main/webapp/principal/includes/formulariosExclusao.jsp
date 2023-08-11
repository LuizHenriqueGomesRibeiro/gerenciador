<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">

<div class="modal exc0" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 0}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc1" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 1}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc2" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 2}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc3" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 3}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc4" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 4}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc5" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 5}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc6" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 6}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc7" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 7}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc8" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 8}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>
<div class="modal exc9" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Exclusão de registros</h5>
			</div>
			<div class="modal-body">
				<h5>Você tem certeza de que quer excluir este registro?</h5>
			</div>
			<form style="position: relative; width: 90%; margin: auto;"
				action="<%=request.getContextPath()%>/servlet_cadastro_e_atualizacao_produtos"
				method="get" name="formulario_cadastro_produtos" id="formulario">

				<c:forEach items="${produtos}" var="ml" varStatus="status">
					<c:if test="${status.index eq 9}">
						<input type="hidden" name="acao" id="acao" value="excluir">
						<input type="hidden" name="id" id="id" value="${ml.id}">
						<div style="width: 196px; position: relative; left: 50%; transform: translate(-50%, 0%); margin-bottom: 25px;">
							<input type="submit" value="Excluir" class="btn btn-primary btn-lg"> 
							<input type="button" value="Fechar" class="btn btn-danger btn-lg" data-dismiss="modal">
						</div>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
</div>