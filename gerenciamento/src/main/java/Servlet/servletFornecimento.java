package Servlet;

import java.io.IOException;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIFornecimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends APIFornecimento {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
	daoPedidos pedido = new daoPedidos();
	daoPedidos daopedidos = new daoPedidos();
	DAOFerramentas dao = new DAOFerramentas();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				incluirPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				cancelarPedido(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				deletarFornecedor(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarFornecedor")){
				cadastrarFornecedor(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void cadastrarFornecedor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosCadastrarFornecedor(request);
		setarAtributos(request, response);
	}
	
	protected void incluirPedido(HttpServletRequest request) throws Exception {
		parametrosIncluirPedido(request);
		setarAtributosAjax(request);
	}

	protected void confirmarPedido(HttpServletRequest request) throws Exception {
		parametrosConfirmarPedido(request);
	}
	
	protected void cancelarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosCancelarPedido(request);
	}

	protected void deletarFornecedor(HttpServletRequest request) throws Exception {
		parametrosDeletarFornecedor(request);
	}
}
