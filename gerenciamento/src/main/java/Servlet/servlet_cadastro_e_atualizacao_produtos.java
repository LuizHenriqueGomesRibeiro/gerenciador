package Servlet;

import jakarta.servlet.RequestDispatcher;
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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoProdutos;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
       
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

				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
		
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				List<ModelProdutos> produtos = daoproduto.consultaProdutosOffset(super.getUsuarioLogado(request).getId(), offset);
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				
				String id = request.getParameter("id");
				
				daoproduto.excluirProduto(id);
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
				despache.forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){

				request.setAttribute("usuario", super.getUsuarioLogado(request));
				
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
				String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
				request.setAttribute("soma", numero);
		
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
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
			String usuario_pai_id = request.getParameter("usuario_pai_id");
			
			//preco = preco.replaceAll("\\.", "").replaceAll("\\,00", "");
			
	        //String preco_R$ = preco.replace("R$", "");
	        //preco_R$ = preco_R$.replaceAll("[^0-9]", "");

			//int preco_int = Integer.parseInt(preco_R$);
			//int quantidade_int = Integer.parseInt(quantidade);
			
			ModelProdutos modelProduto = new ModelProdutos();
			
			//modelProduto.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelProduto.setNome(nome);
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(Integer.parseInt(usuario_pai_id)));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
			request.setAttribute("soma", numero);
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
