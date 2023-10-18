package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.Despache;
import DAO.DaoGenerico;
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
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	Despache api = new Despache();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_cadastro_e_atualizacao_produtos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				listar(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				paginar(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				excluir(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){
				configuracoes(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				historicoPedidos(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				exclusaoAjax(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request, response, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				cancelarPedido(request, response, usuario);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = super.getUsuarioLogado(request).getId();
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			
			ModelProdutos modelProduto = new ModelProdutos();
			modelProduto.setNome(request.getParameter("nome"));
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(id));
			
			daoproduto.alternarProduto(modelProduto);

			super.setarAtributos(request, usuario);
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
		} catch (Exception e) {
			request.getRequestDispatcher("principal/principal.jsp").forward(request, response);
		}
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws Exception {
		super.setarAtributos(request, usuario);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}
	
	protected void paginar(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws Exception {
		super.setarAtributosOFFSET(request, usuario);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}
	
	protected void excluir(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws Exception {
		Long id_produto = Long.parseLong(request.getParameter("id"));
		daoproduto.excluirProduto(id_produto);
		super.setarAtributos(request, usuario);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}
	
	protected void configuracoes(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws Exception {
		Long id_produto = Long.parseLong(request.getParameter("id"));
		super.setarAtributos(request, usuario);
		String json = new Gson().toJson(daoproduto.consultaProduto(id_produto, usuario.getId())) + "|" + new Gson().toJson(daoFornecimento.listarFornecedores(id_produto));
		super.impressaoJSON(response, json);
	}

	protected void historicoPedidos(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws IOException, NumberFormatException, SQLException {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(Integer.parseInt(request.getParameter("id")), 0)));
		super.impressaoJSON(response, json);
	}
	
	protected void exclusaoAjax(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws IOException, NumberFormatException, SQLException {
		Long id_produto = Long.parseLong(request.getParameter("id"));
		String json = new Gson().toJson(daoproduto.consultaProduto(id_produto, usuario.getId()));
		super.impressaoJSON(response, json);
	}
	
	protected void confirmarPedido(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws IOException, NumberFormatException, SQLException {
		daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), usuario.getId());
		daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("id_produto")), Integer.parseInt(request.getParameter("quantidade")));
		daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 2);
	}
	
	protected void cancelarPedido(HttpServletRequest request, HttpServletResponse response, ModelUsuarios usuario) throws IOException, NumberFormatException, SQLException {
		daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 1);
	}
}
