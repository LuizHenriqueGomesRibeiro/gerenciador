package FILTER;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/principal/*"})
public class FiltroLogin implements Filter  {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Object usuario = httpRequest.getAttribute("usuario");
		String acao = httpRequest.getParameter("acao");
		if(usuario == null && acao == null) {
			request.setAttribute("msg", "Faça o login");
			request.getRequestDispatcher("../login.jsp?acao=carregar").forward(httpRequest, response);
		}else {
			request.getRequestDispatcher("/ServletLogin").forward(httpRequest, response);
		}
		
	}
}
