package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelProdutos;

import java.io.IOException;

import DAO.daoFornecimento;
import DAO.daoProdutos;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletFornecimento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String usuario_pai_id = request.getParameter("usuario_pai_id");
			String id = request.getParameter("id");
			
			daoProdutos daoprodutos = new daoProdutos();
			int id_int = Integer.parseInt(id);
			
			ModelProdutos modelProduto = daoprodutos.consultaProduto(id_int, super.getUsuarioLogado(request).getId());
			
			daoFornecimento daoFornecimento = new daoFornecimento();
			
			daoFornecimento.gravarNovoFornecedor(nomeFornecedor, modelProduto);
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
