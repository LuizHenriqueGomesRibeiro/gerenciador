package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
       
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
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId()));
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + this.getUsuarioLogado(request).getId() + " ORDER BY quantidade OFFSET " + Integer.parseInt(request.getParameter("pagina")) + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, this.getUsuarioLogado(request).getId()));
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				
				String id = request.getParameter("id");
				
				daoproduto.excluirProduto(id);
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){

				request.setAttribute("usuario", super.getUsuarioLogado(request));
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);

				String id = request.getParameter("id");

				ModelProdutos dadosJsonUser1 = daoproduto.consultaProduto(Long.parseLong(id), super.getUsuarioLogado(request).getId());
				List<ModelFornecimento> dadosJsonUser2 = daoFornecimento.listarFornecedores(Integer.parseInt(id));
				Gson gson = new Gson();
				String json1 = gson.toJson(dadosJsonUser1);
				String json2 = gson.toJson(dadosJsonUser2);
				PrintWriter out = response.getWriter();
			    out.print(json1 + "|" + json2);
			    out.flush();
			    
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){

				String id = request.getParameter("id");

				ModelProdutos dadosJsonUser = daoproduto.consultaProduto(Long.parseLong(id), super.getUsuarioLogado(request).getId());
				Gson gson = new Gson();
				String json = gson.toJson(dadosJsonUser);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				String id = request.getParameter("id");
				int status = 0;
				String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND produtos_pai_id = " + id;
				List<ModelPedidos> pedidos = daopedidos.listarPedidos(sql);
				Gson gson = new Gson();
				String json = gson.toJson(pedidos);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
			
				String id = request.getParameter("id");
				String id_produto = request.getParameter("id_produto");
				String quantidade = request.getParameter("quantidade");
				
				daoproduto.consultaProduto(Long.parseLong(id_produto), super.getUsuarioLogado(request).getId());
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(id_produto), Integer.parseInt(quantidade));
				daopedidos.excluirPedido(Long.parseLong(id));
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){

				String id = request.getParameter("id");
				
				daopedidos.excluirPedido(Long.parseLong(id));
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
			String nome = request.getParameter("nome");
			
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setNome(nome);
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(super.getUsuarioLogado(request).getId()));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
			request.setAttribute("soma", numero);
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			
			List<ModelProdutos> produtos = daoproduto.listarProdutos("SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10", super.getUsuarioLogado(request).getId());
			request.setAttribute("produtos", produtos);
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			RequestDispatcher despache = request.getRequestDispatcher("principal/principal.jsp");
			despache.forward(request, response);
		}
		
	}
}
