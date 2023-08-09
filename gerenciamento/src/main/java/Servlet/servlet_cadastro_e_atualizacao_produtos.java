package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelProdutos;
import model.ModelUsuarios;

import java.io.IOException;
import java.util.List;

import DAO.daoLogin;
import DAO.daoProdutos;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
       
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
			System.out.println(acao);
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {

				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
			}
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				
				List<ModelProdutos> produtos = daoproduto.consultaProdutosOffset(super.getUsuarioLogado(request).getId(), offset);
				request.setAttribute("produtos", produtos);
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
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
			String preco = request.getParameter("preco");
			String quantidade = request.getParameter("quantidade");
			String nome = request.getParameter("nome");
			String usuario_pai_id = request.getParameter("usuario_pai_id");
			String id = request.getParameter("id");
			
			//System.out.println(id);
			
			int preco_int = Integer.parseInt(preco);
			int quantidade_int = Integer.parseInt(quantidade);
			
			
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelProduto.setPreco(preco_int);
			modelProduto.setQuantidade(quantidade_int);
			modelProduto.setNome(nome);
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(Integer.parseInt(usuario_pai_id)));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			
			List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
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
