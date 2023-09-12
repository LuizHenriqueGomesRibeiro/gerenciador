package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelVendas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import DAO.daoVendas;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends servlet_recuperacao_login{
	private static final long serialVersionUID = 1L;
	
	daoVendas daoVendas = new daoVendas();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRelatorios() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("irParaRelatorios")) {
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/relatorios.jsp");
			despache.forward(request, response);
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vendas")) {
			
			try {
				List<ModelVendas> vendas = daoVendas.listarVendas(super.getUsuarioLogado(request).getId());
				System.out.println(vendas);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("entradas")) {
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
