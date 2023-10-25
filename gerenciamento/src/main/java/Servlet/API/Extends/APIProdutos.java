package Servlet.API.Extends;

import java.sql.SQLException;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
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
	DAOFerramentas dao = new DAOFerramentas();
	daoFornecimento daofornecedor = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	daoLogin daologin = new daoLogin();
	/*
	public void excluir(HttpServletRequest request) throws SQLException {
		daoproduto.excluirProduto(id_produto(request));
	}
	
	public String parametrosConfiguracoes(HttpServletRequest request) throws Exception {
		String produto = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		String fornecedores = new Gson().toJson(daofornecedor.listarFornecedores(id_produto(request)));
		String json = produto + "|" + fornecedores;
		return json;
	}
	
	public String parametrosHistoricoPedidos(HttpServletRequest request) throws NumberFormatException, SQLException {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id_produto(request), 0)));
		return json;
	}
	
	public String parametrosExclusaoAjax(HttpServletRequest request) throws SQLException, Exception {
		String json = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		return json;
	}
	
	public void parametrosCadastrarProduto(HttpServletRequest request) throws Exception {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setNome(nome(request));
		modelProduto.setUsuario_pai_id(user(request));
		daoproduto.alternarProduto(modelProduto);
	}
	*/
}
