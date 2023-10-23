package Servlet.API;

import DAO.DaoGenerico;
import Servlet.API.Extends.Final.servlet_recuperacao_login;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelUsuarios;

public class APIEntradas extends servlet_recuperacao_login {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DaoGenerico dao = new DaoGenerico();
	
	public Long id_produto(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_produto"));
	}
	
	public Long id_fornecedor(HttpServletRequest request) {
		return Long.parseLong(request.getParameter("id_fornecedor"));
	}
	
	public int quantidade(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("quantidade"));
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
	
	public String dataVenda(HttpServletRequest request) { 
		return dao.converterDatas(request.getParameter("dataVenda"));
	}
	
	public ModelUsuarios user(HttpServletRequest request) throws Exception {
		return super.getUser(request);
	}
	
	public int id(HttpServletRequest request) throws Exception {
		return super.getUserId(request);
	}
	
	public int pagina(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("pagina"));
	}
}
