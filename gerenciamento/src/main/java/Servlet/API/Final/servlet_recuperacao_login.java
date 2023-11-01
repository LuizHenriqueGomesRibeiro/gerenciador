package Servlet.API.Final;

import java.io.Serializable;

import DAO.DAOLogin;
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
	
	DAOLogin daologin = new DAOLogin();
	
    public ModelUsuarios getUser(HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();	
    	String login = (String) session.getAttribute("login");

    	return daologin.consultaLoginString(login);
    }
    
    public int getUserId(HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();	
    	String login = (String) session.getAttribute("login");

    	return daologin.consultaLoginString(login).getId();
    }
}
