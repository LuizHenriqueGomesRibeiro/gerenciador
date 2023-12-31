package Servlet.API;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
import DAO.DAOFornecimento;
import DAO.DAOPedidos;
import DAO.DAOProdutos;
import DAO.SQL.SQLProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelUsuarios;
import net.bytebuddy.description.type.TypeDescription.Generic;

public class APIDespache extends APIEntradas {
	DAOPedidos daopedidos = new DAOPedidos();
	DAOProdutos daoproduto = new DAOProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	DAOFornecimento daofornecedor = new DAOFornecimento();

	private static final long serialVersionUID = 1L;
	
	public void setarAtributos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		plusProdutos(request);
		plusAtributosComum(request);
		request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
	}

	public void setarAtributosAjax(HttpServletRequest request) throws Exception {
		plusProdutos(request);
		plusAtributosComum(request);
	}

	public void setarAtributosSaida(HttpServletRequest request, HttpServletResponse response) throws Exception {
		plusProdutos(request);
		request.getRequestDispatcher("principal/saida.jsp").forward(request, response);
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
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id(request))));
		request.setAttribute("usuario", user(request));
		return plusProdutos(request);
	}
	
	public HttpServletRequest plusProdutos(HttpServletRequest request) throws SQLException, Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id(request)), id(request)));
		return request;
	}
}