package Servlet.API;

import java.text.ParseException;

import DAO.DAOFerramentas;
import Servlet.API.Final.servlet_recuperacao_login;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelUsuarios;

public class APIEntradas extends servlet_recuperacao_login {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DAOFerramentas dao = new DAOFerramentas();
	
	public String loginAntigo(HttpServletRequest request) {
		return request.getParameter("loginAntigo");
	}
	
	public String loginAntigoRepetido(HttpServletRequest request) {
		return request.getParameter("loginAntigoRepetido");
	}
	
	public String loginNovo(HttpServletRequest request) {
		return request.getParameter("loginNovo");
	}
	
	public String loginNovoRepetido(HttpServletRequest request) {
		return request.getParameter("loginNovoRepetido");
	}
	
	public String acao(HttpServletRequest request) {
		return request.getParameter("acao");
	}
	
	public Long id_produto(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_produto"));
	}
	
	public Long id_fornecedor(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_fornecedor"));
	}
	
	public int quantidade(HttpServletRequest request) {
		return Integer.parseInt(dao.tirarPonto(request.getParameter("quantidade")));
	}
	
	public Long id_pedido(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_pedido"));
	}
	
	public String dataPedido(HttpServletRequest request) {
		return request.getParameter("dataPedido");
	}
	
	public String nomeFornecedor(HttpServletRequest request) {
		return request.getParameter("nomeFornecedor");
	}
	
	public int tempoEntrega(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("tempoentrega"));
	}
	
	public int valor(HttpServletRequest request) {
		return dao.converterDinheiroInteger(request.getParameter("valor"));
	}
	
	public String dataEntrega(HttpServletRequest request) {
		return request.getParameter("dataEntrega");
	}
	
	public String nome(HttpServletRequest request) {
		return request.getParameter("nome");
	}
	
	public String dataInicial(HttpServletRequest request) {
		return request.getParameter("dataInicial");
	}
	
	public String dataFinal(HttpServletRequest request) {
		return request.getParameter("dataFinal");
	}
	
	public String dataVenda(HttpServletRequest request) throws ParseException { 
		return dao.converterDatas(request.getParameter("dataVenda"));
	}
	
	public ModelUsuarios user(HttpServletRequest request) throws Exception {
		return super.getUser(request);
	}
	
	public int id(HttpServletRequest request) throws Exception {
		return getUserId(request);
	}
	
	public int pagina(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("pagina"));
	}
	
	public String senha(HttpServletRequest request) {
		return request.getParameter("senha");
	}
	
	public String url(HttpServletRequest request) {
		return request.getParameter("url");
	}

	public String login(HttpServletRequest request) {
		return request.getParameter("login");
	}
}
