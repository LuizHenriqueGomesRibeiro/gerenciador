package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuarios;

import java.io.IOException;
import java.sql.SQLException;

import DAO.DAOCadastroUsuario;
import DAO.DAOLogin;
import DAO.SQL.SQLLogin;
import Servlet.API.APIDespache;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_usuario
 */
public class servlet_cadastro_e_atualizacao_usuario extends APIDespache {
	private static final long serialVersionUID = 1L;
	
	private DAOCadastroUsuario daoCadastroUsuario = new DAOCadastroUsuario();
	private DAOLogin daoLogin = new DAOLogin();
	private SQLLogin sqllogin = new SQLLogin();

    public servlet_cadastro_e_atualizacao_usuario() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("abrirConfiguracoesUsuario")) {
				despache(request, response, false, null);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("validarEntrada")) {
				validarEntrada(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("mudarLogin")) {
				mudarLogin(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void validarEntrada(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelUsuarios usuario = new ModelUsuarios(login(request), senha(request));
		if(daoLogin.validarAutenticacao(sqllogin.validarUsuarioPorLoginESenha(usuario))) {
			despache(request, response, true, null);
		}else {
			String mensagem = "Login ou senha incorretos. Tente novamente.";
			despache(request, response, false, mensagem);
		}
	}
	
	protected void mudarLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(loginAntigo(request).equals(loginAntigoRepetido(request)) && loginNovo(request).equals(loginNovoRepetido(request)) &&
				loginNovo(request) != null && !loginNovo(request).isEmpty() && loginNovoRepetido(request) != null && !loginNovoRepetido(request).isEmpty()) {
			if(daoLogin.validarAutenticacao(sqllogin.validarUsuarioPorLogin(loginAntigo(request)))){
				if(loginAntigo(request).equals(loginNovo(request)) || loginAntigoRepetido(request).equals(loginNovoRepetido(request)) || 
						loginAntigo(request).equals(loginNovoRepetido(request)) || loginAntigoRepetido(request).equals(loginNovo(request))) {
					String mensagem = "Novo login não pode ser igual ao anterior.";
					despache(request, response, true, mensagem);
				}else {
					ModelUsuarios usuario = new ModelUsuarios(loginNovo(request), getUser(request).getId());
					daoLogin.atualizarLogin(sqllogin.atualizacaoLogin(usuario));
					String mensagem = "Login atualizado com sucesso.";
					despache(request, response, true, mensagem);
				}
			}else {
				String mensagem = "As informações não batem. Tente novamente.";
				despache(request, response, true, mensagem);
			}
		}else {
			String mensagem = "As informações não batem ou há campos vazios. Tente novamente.";
			despache(request, response, true, mensagem);
		}
	}
	
	protected void despache(HttpServletRequest request, HttpServletResponse response, boolean validacao, String mensagem) throws Exception {
		request.setAttribute("validarEntrada", validacao);
		request.setAttribute("mensagem", mensagem);
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String nome = request.getParameter("nome");
		
		ModelUsuarios login_cadastro = new ModelUsuarios();
		
		login_cadastro.setLogin(login);
		login_cadastro.setEmail(email);
		login_cadastro.setSenha(senha);
		login_cadastro.setNome(nome);
		
		if(daoCadastroUsuario.validarLogin(login) && daoCadastroUsuario.validarEmail(email)) {
			request.setAttribute("msg_email", "Este e-mail já está cadastrado");
			request.setAttribute("msg_login", "Este login já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}else if(daoCadastroUsuario.validarEmail(email)) {
			request.setAttribute("msg_email", "Este e-mail já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}else if(daoCadastroUsuario.validarLogin(login)) {
			request.setAttribute("msg_login", "Este login já está cadastrado");
			RequestDispatcher despache = request.getRequestDispatcher("cadastro.jsp");
			despache.forward(request, response);
		}else {
			daoCadastroUsuario.gravarUsuario(login_cadastro);
			request.setAttribute("msg", "Tudo certo! Faça o login.");
			RequestDispatcher despache = request.getRequestDispatcher("login.jsp");
			despache.forward(request, response);
		}
	}
}
