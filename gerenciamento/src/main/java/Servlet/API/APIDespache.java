package Servlet.API;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.Final.servlet_recuperacao_login;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuarios;

public class APIDespache extends servlet_recuperacao_login {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ModelUsuarios user(HttpServletRequest request) throws Exception {
		return super.getUser(request);
	}
	
	public int id(HttpServletRequest request) throws Exception {
		return super.getUserId(request);
	}
	
	public int pagina(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter("pagina"));
	}
	
	// <-----------------------------------------------------------Servlet_cadastro_e_atualizacao_produtos ---------------------------------------------------------------> //
	public void setarAtributosListar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		plusAtributos(request, response);
	}
	
	public void setarAtributosPaginar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosOFFSET(id(request), pagina(request)), id(request)));
		plusAtributos(request, response);
	}
	
	public void setarAtributosExcluir(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		plusAtributos(request, response);
	}

	public void setarAtributosCadastrar(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		plusAtributos(request, response);
	}
	
	public void plusAtributos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id(request)));
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id(request))));
		request.setAttribute("usuario", user(request));
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}
	// <----------------------------------------------------------- Servlet_cadastro_e_atualizacao_produtos --------------------------------------------------------------> //
	
	
	// <-------------------------------------------------------------------- ServletRelatorios ---------------------------------------------------------------------------> //
	public void setarAtributosirParaRelatorios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/relatorios.jsp").forward(request, response);
	}
	// <-------------------------------------------------------------------- ServletRelatorios ---------------------------------------------------------------------------> //
	
	public HttpServletRequest setarAtributos(HttpServletRequest request) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		return plusAtributos(request);
	}
	
	public HttpServletRequest setarAtributosSaida(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		request.getRequestDispatcher("principal/saida.jsp").forward(request, response);
		return request;
	}
	
	public HttpServletRequest setarAtributosOFFSET(HttpServletRequest request) throws Exception {
		int id = super.getUserId(request);
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosOFFSET(id(request), pagina(request)), id(request)));
		return plusAtributos(request);
	}
	
	public HttpServletRequest plusAtributos(HttpServletRequest request) throws Exception {
		request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id(request)));
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id(request))));
		request.setAttribute("usuario", id(request));
		return request;
	}
	
	// <------------------------------------------------------------------- genéricos / universais -----------------------------------------------------------------------> //
	public HttpServletResponse cabecarioImpressaoPDF(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
		response.setContentType("application/octet-stream");
		return response;
	}
	
	public HttpServletResponse impressaoPDF(HttpServletResponse response, byte[] relatorio) throws IOException {
		cabecarioImpressaoPDF(response);
		response.getOutputStream().write(relatorio);
		response.getOutputStream().flush();
		return response;
	}
	
	public HttpServletResponse cabecarioImpressaoJSON(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return response;
	}
	
	public void impressaoJSON(HttpServletResponse response, String json) throws IOException {
		PrintWriter printWriter = response.getWriter();
		cabecarioImpressaoJSON(response);
		printWriter.write(json);
		printWriter.close();
	}
	// <------------------------------------------------------------------- genéricos / universais -----------------------------------------------------------------------> //
}
