package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;

import DAO.daoLogin;

/**
 * Servlet implementation class servletLogin
 */
@WebServlet(urlPatterns = { "/principal/ServletLogin"}) 
public class servletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private daoLogin daoLogin = new daoLogin();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				
				Login login_em_validacao = new Login();
				login_em_validacao.setSenha(senha);
				login_em_validacao.setLogin(login);
				
				if(daoLogin.validarAutenticacao(login_em_validacao)) {
					
					if(url == null || url.equals("null")) {
						url = "principal/principal.jsp";
						
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
					}
				}else {
					request.setAttribute("msg", "Usuário ou senha incorretos.");
					RequestDispatcher redirecionar = request.getRequestDispatcher("login.jsp");
					redirecionar.forward(request, response);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
