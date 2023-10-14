package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
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
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId()));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + this.getUsuarioLogado(request).getId() + " ORDER BY quantidade OFFSET " + Integer.parseInt(request.getParameter("pagina")) + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, this.getUsuarioLogado(request).getId()));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				
				String id = request.getParameter("id");
				
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId()));
				daoproduto.excluirProduto(id);
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){

				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				request.setAttribute("produtos", daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId()));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));

				ModelProdutos dadosJsonUser1 = daoproduto.consultaProduto(Long.parseLong(request.getParameter("id")), super.getUsuarioLogado(request).getId());
				List<ModelFornecimento> dadosJsonUser2 = daoFornecimento.listarFornecedores(Long.parseLong(request.getParameter("id")));
				String json1 = new Gson().toJson(dadosJsonUser1);
				String json2 = new Gson().toJson(dadosJsonUser2);
				PrintWriter out = response.getWriter();
			    out.print(json1 + "|" + json2);
			    out.flush();
			    
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				ModelProdutos dadosJsonUser = daoproduto.consultaProduto(Long.parseLong(request.getParameter("id")), super.getUsuarioLogado(request).getId());
				String json = new Gson().toJson(dadosJsonUser);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
			
				Long id = Long.parseLong(request.getParameter("id"));
				String id_produto = request.getParameter("id_produto");
				int quantidade = Integer.parseInt(request.getParameter("quantidade"));
				
				daoproduto.consultaProduto(Long.parseLong(id_produto), super.getUsuarioLogado(request).getId());
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(id_produto), quantidade);
				daopedidos.mudarStatus(id, 2);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 1);
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
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setNome(request.getParameter("nome"));
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(super.getUsuarioLogado(request).getId()));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
			List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(this.getUsuarioLogado(request).getId())));
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			request.setAttribute("produtos", produtos);
			
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.getRequestDispatcher("principal/principal.jsp").forward(request, response);
		}
		
	}
}
