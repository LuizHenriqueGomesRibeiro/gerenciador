package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelProdutos;

import java.io.IOException;
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
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrar")) {
				
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				
				System.out.println("--------------------------------------------------------------------");
				System.out.println("bloco cadastrar do doGet: o nome do usuário logado é: " + super.getUsuarioLogado(request));
				System.out.println("--------------------------------------------------------------------");
				
				RequestDispatcher despache = request.getRequestDispatcher("principal/cadastro_produtos.jsp");
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
			
			int preco_int = Integer.parseInt(preco);
			int quantidade_int = Integer.parseInt(quantidade);
			
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setPreco(preco_int);
			modelProduto.setQuantidade(quantidade_int);
			modelProduto.setNome(nome);
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(Integer.parseInt(usuario_pai_id)));
			request.setAttribute("msg","Produto cadastrado com sucesso");
			
			daoproduto.gravarProduto(modelProduto);
		
			RequestDispatcher despache = request.getRequestDispatcher("principal/cadastro_produtos.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			RequestDispatcher despache = request.getRequestDispatcher("principal/principal.jsp");
			despache.forward(request, response);
		}
		
	}
}
