package Servlet;

import java.io.Serializable;

import DAO.daoLogin;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelUsuarios;

/**
 * Servlet implementation class servlet_recuperacao_login
 */
public class servlet_recuperacao_login extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	
    public ModelUsuarios getUsuarioLogado(HttpServletRequest request) throws Exception {
    	
    	HttpSession session = request.getSession();
    	
    	String login = (String) session.getAttribute("login");
    	
		System.out.println("--------------------------------------------------------------------");
    	System.out.println("We are inside the servlet_recuperacao_login and the user name is: " + login);
		System.out.println("--------------------------------------------------------------------");

    	return daologin.consultaLoginString(login);
    }
}
