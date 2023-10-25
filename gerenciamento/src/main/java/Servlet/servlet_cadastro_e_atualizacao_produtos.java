package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelProdutos;
import model.ModelUsuarios;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends APIProdutos {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	daoFornecimento daofornecedor = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	daoLogin daologin = new daoLogin();

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				listar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				paginar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				excluir(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){
				configuracoes(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				historicoPedidos(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				exclusaoAjax(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarProduto")){
				cadastrarProduto(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);
	}
	
	protected void paginar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);	
	}
	
	protected void excluir(HttpServletRequest request, HttpServletResponse response) throws Exception {
		daoproduto.excluirProduto(id_produto(request));
		setarAtributos(request, response);
	}
	
	protected void configuracoes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String produto = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		String fornecedores = new Gson().toJson(daofornecedor.listarFornecedores(id_produto(request)));
		String json = produto + "|" + fornecedores;
		impressaoJSON(response, json);
	}

	protected void historicoPedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id_produto(request), 0)));
		impressaoJSON(response, json);
	}
	
	protected void exclusaoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		impressaoJSON(response, json);
	}
	
	protected void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setNome(nome(request));
		modelProduto.setUsuario_pai_id(user(request));
		daoproduto.alternarProduto(modelProduto);
		setarAtributos(request, response);
	}
}
