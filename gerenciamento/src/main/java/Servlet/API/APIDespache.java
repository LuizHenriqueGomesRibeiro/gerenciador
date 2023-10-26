package Servlet.API;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuarios;

public class APIDespache extends APIEntradas {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	daoFornecimento daofornecedor = new daoFornecimento();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setarAtributos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		plusProdutos(request);
		plusAtributosComum(request);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}
	
	public void setarAtributosOffset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		plusProdutosOffset(request);
		plusAtributosComum(request);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}

	public void setarAtributosAjax(HttpServletRequest request) throws Exception {
		plusProdutos(request);
		plusAtributosComum(request);
	}

	public void setarAtributosirParaRelatorios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/relatorios.jsp").forward(request, response);
	}
	
	public void setarAtributosSaida(HttpServletRequest request, HttpServletResponse response) throws Exception {
		plusAtributosComum(request);
		request.getRequestDispatcher("principal/saida.jsp").forward(request, response);
	}

	public void setarAtributosOffsetAjax(HttpServletRequest request) throws Exception {
		plusProdutosOffset(request);
		plusAtributosComum(request);
	}
	
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
	
	public void impressaoJSON(HttpServletResponse response, String json) throws IOException {
		PrintWriter printWriter = response.getWriter();
		printWriter.print(json);
		printWriter.flush();
	}
	
	public void impressaoMultiJSON(HttpServletResponse response, String superJson) throws IOException {
		PrintWriter printWriter = response.getWriter();
		printWriter.print(superJson);
		printWriter.flush();
	}

	public HttpServletRequest plusAtributosComum(HttpServletRequest request) throws Exception {
		request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id(request)));
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id(request))));
		request.setAttribute("usuario", user(request));
		return plusProdutos(request);
	}
	
	public HttpServletRequest plusProdutos(HttpServletRequest request) throws SQLException, Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		return request;
	}
	
	public HttpServletRequest plusProdutosOffset(HttpServletRequest request) throws SQLException, Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosOFFSET(id(request), pagina(request)), id(request)));
		return request;
	}
}
