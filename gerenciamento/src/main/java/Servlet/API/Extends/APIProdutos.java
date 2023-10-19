package Servlet.API.Extends;

import java.sql.SQLException;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.APIDespache;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelProdutos;
import model.ModelUsuarios;

public class APIProdutos extends APIDespache{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	daoLogin daologin = new daoLogin();
	
	public int id(HttpServletRequest request) throws Exception {
		return super.getUserId(request);
	}
	
	public Long id_pedido(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_pedido"));
	}
	
	public Long id_produto(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_produto"));
	}
	
	public int quantidade(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("quantidade"));
	}
	
	public void excluir(HttpServletRequest request) throws SQLException {
		daoproduto.excluirProduto(id_produto(request));
	}
	
	public String configuracoes(HttpServletRequest request) throws Exception {
		String produto = new Gson().toJson(daoproduto.consultaProduto(id_produto(request), super.getUserId(request)));
		String fornecedores = new Gson().toJson(daofornecedor.listarFornecedores(id_produto(request)));
		String json = produto + "|" + fornecedores;
		return json;
	}
	
	public String historicoPedidos(HttpServletRequest request) throws NumberFormatException, SQLException {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id_produto(request), 0)));
		return json;
	}
	
	public String exclusaoAjax(HttpServletRequest request) throws SQLException, Exception {
		String json = new Gson().toJson(daoproduto.consultaProduto(id_produto(request), id(request)));
		return json;
	}
	
	public void confirmarPedido(HttpServletRequest request) throws Exception {
		daoproduto.consultaProduto(id_produto(request), id(request));
		daoproduto.adicionaProdutoCaixa(id_produto(request), quantidade(request));
		daopedidos.mudarStatus(id_pedido(request), 2);
	}
	
	public void cancelarPedido(HttpServletRequest request) throws SQLException {
		daopedidos.mudarStatus(id_pedido(request), 1);
	}
	
	public void cadastrarPedido(HttpServletRequest request) throws Exception {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setNome(request.getParameter("nome"));
		modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(id(request)));
		daoproduto.alternarProduto(modelProduto);
	}
}
