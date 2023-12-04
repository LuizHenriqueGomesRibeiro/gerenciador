package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelUsuarios;

import java.io.IOException;

import DAO.DAOFerramentas;
import DAO.DAOLogin;
import DAO.DAOVendas;
import DAO.SQL.SQLLogin;
import Servlet.API.APIDespache;
import Servlet.API.APIEntradas;

@WebServlet(urlPatterns = {"/ServletLogin"}) 
public class servletLogin extends APIDespache {
	private static final long serialVersionUID = 1L;
	
	private DAOLogin daoLogin = new DAOLogin();
	private DAOVendas daoVendas = new DAOVendas();
	private DAOFerramentas daoferramentas = new DAOFerramentas();
	private SQLLogin sqlLogin = new SQLLogin();
	
	public servletLogin() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("login", login(request));

		try {
			if (login(request) != null && !login(request).isEmpty() && senha(request) != null && !senha(request).isEmpty()) {
				
				ModelUsuarios login_em_validacao = new ModelUsuarios();
				login_em_validacao.setSenha(senha(request));
				login_em_validacao.setLogin(login(request));
				
				if(daoLogin.validarAutenticacao(sqlLogin.validarUsuarioPorLoginESenha(login_em_validacao))) {
					daoVendas.gravarDatas(user(request).getId(), daoferramentas.converterDatas(daoferramentas.dataAtual()));
					plusProdutos(request);
					plusAtributosComum(request);
					RequestDispatcher redirecionar = request.getRequestDispatcher("principal/listar.jsp");
					redirecionar.forward(request, response);
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
