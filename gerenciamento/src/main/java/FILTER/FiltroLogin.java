package FILTER;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/principal/*"})
public class FiltroLogin implements Filter  {

	@SuppressWarnings("null")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		Object senha = httpRequest.getAttribute("senha");
		
		if(senha == null) {
			request.setAttribute("msg", "Por favor, faça o login");
			request.getRequestDispatcher("../login.jsp").forward(request, response);
		}else {
			chain.doFilter(request, response);
		}
	}
}
