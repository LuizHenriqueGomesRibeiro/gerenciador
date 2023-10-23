package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.SQL.SQLProdutos;
import Servlet.servletFornecimento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelParametros;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;
import model.ModelVendas;

public class Despache {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	/*
	public HttpServletRequest setarAtributos(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(usuario.getId()), usuario.getId()));
		return plusAtributos(request, usuario);
	}
	
	public HttpServletRequest setarAtributosOFFSET(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosOFFSET(usuario.getId(), Integer.parseInt(request.getParameter("pagina"))), usuario.getId()));
		return plusAtributos(request, usuario);
	}
	
	public HttpServletRequest plusAtributos(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(usuario.getId()));
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(usuario.getId())));
		request.setAttribute("usuario", usuario);
		return request;
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
	*/
}