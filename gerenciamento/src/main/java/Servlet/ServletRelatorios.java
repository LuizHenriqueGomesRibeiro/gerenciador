package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
