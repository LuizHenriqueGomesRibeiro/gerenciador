package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;

import DAO.daoCadastroUsuario;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_usuario
 */
public class servlet_cadastro_e_atualizacao_usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private daoCadastroUsuario daoCadastroUsuario = new daoCadastroUsuario();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_cadastro_e_atualizacao_usuario() {
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
		
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String nome = request.getParameter("nome");
		
		Login login_cadastro = new Login();
		
		login_cadastro.setLogin(login);
		login_cadastro.setEmail(email);
		login_cadastro.setSenha(senha);
		login_cadastro.setNome(nome);
		
		if(daoCadastroUsuario.validarLogin(login) && daoCadastroUsuario.validarEmail(email)) {
			request.setAttribute("msg_email", "Este e-mail já está cadastrado");
			request.setAttribute("msg_login", "Este login já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}
		else if(daoCadastroUsuario.validarEmail(email)) {
			request.setAttribute("msg_email", "Este e-mail já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}
		else if(daoCadastroUsuario.validarLogin(login)) {
			request.setAttribute("msg_login", "Este login já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}
		else {
			daoCadastroUsuario.gravarUsuario(login_cadastro);
			request.setAttribute("msg", "Usuário cadastrado com sucesso");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}
		
	}

}
