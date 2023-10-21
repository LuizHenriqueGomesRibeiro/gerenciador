package Servlet;

import java.io.IOException;

import DAO.Despache;
import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIFornecimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;

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
	daoEntradasRelatorio daoEntradaRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String acao = request.getParameter("acao");
			ModelUsuarios usuario = super.getUser(request);
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				incluirPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				//Long id_pedido = Long.parseLong(request.getParameter("id"));
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				deletarFornecedor(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarFornecedor")){
				cadastrarFornecedor(request, response);
			}
		}catch (Exception e) {
		}
	}
	
	protected void cadastrarFornecedor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosCadastrarFornecedor(request);
		setarAtributosListar(request, response);
	}
	
	protected void incluirPedido(HttpServletRequest request) throws Exception {
		parametrosIncluirPedido(request);
		setarAtributos(request);
	}

	protected void confirmarPedido(HttpServletRequest request) throws Exception {
		//parametrosConfirmarPedido(request);
	}
	
	protected void deletarFornecedor(HttpServletRequest request) throws Exception {
		daofornecimento.excluirFornecedor(Long.parseLong(request.getParameter("id")));
	}
}
