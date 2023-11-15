package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
import DAO.DAOFornecimento;
import DAO.DAOLogin;
import DAO.DAOPedidos;
import DAO.DAOProdutos;
import DAO.DAOVendas;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLVendas;
import Servlet.API.APIDespache;
import Util.ReportUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelPedidos;
import model.ModelUsuarios;
import model.ModelVendas;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends APIDespache{
	private static final long serialVersionUID = 1L;
	
	DAOVendas daoVendas = new DAOVendas();
	DAOPedidos daoPedidos = new DAOPedidos();
    DAOPedidos daopedidos = new DAOPedidos();
	DAOProdutos daoproduto = new DAOProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	DAOFornecimento daofornecedor = new DAOFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	DAOLogin daologin = new DAOLogin();
	SQLVendas sqlvendas = new SQLVendas();
	DAOVendas daovendas = new DAOVendas();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		try {
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("irParaRelatorios")) {
				irParaRelatorios(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vendas")) {
				vendas(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("entradas")) {
				entradas(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {
				pedidos(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {
				cancelamentos(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormVendasPDF")) {
				printFormVendasPDF(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormEntradasPDF")) {
				printFormEntradasPDF(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void irParaRelatorios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/relatorios.jsp").forward(request, response);
	}
	
	protected void vendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Gson gson = new Gson();
		String json;
		json = gson.toJson(daovendas.listarVendas(sqlvendas.listaVendas(id(request)), sqlvendas.somaValoresVendas(id(request)), sqlvendas.somaQuantidadeVendas(id(request))));
		impressaoJSON(response, json);
	}
	
	protected void entradas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2)));
		impressaoJSON(response, json);
	}
	
	protected void pedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request))));
		impressaoJSON(response, json);
	}
	
	protected void cancelamentos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 1)));
		impressaoJSON(response, json);
	}
	
	protected void printFormVendasPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] relatorio = alternarPrintFormVendasPDF(request, response);
		impressaoPDF(response, relatorio);
	}
	
	protected byte[] alternarPrintFormVendasPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (dataInicial(request) == null || dataInicial(request).isEmpty() || dataFinal(request) == null || dataFinal(request).isEmpty()) {
			return printFormVendasPDFSemDatas(request, response);
		} else {
			return printFormVendasPDFComDatas(request, response);
		}
	}
	
	public byte[] printFormVendasPDFSemDatas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sqlListaVendas = sqlvendas.listaVendas(id(request)); 
		String sqlSomaValores = sqlvendas.somaValoresVendas(id(request));
		String sqlSomaQuantidade = sqlvendas.somaQuantidadeVendas(id(request));
		List<ModelVendas> vendas = daovendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);
		return new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());
	}
	
	public byte[] printFormVendasPDFComDatas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(sqlvendas.listaVendas(id(request), dataInicial(request), dataFinal(request)));
		String sqlListaVendas = sqlvendas.listaVendas(id(request), dataInicial(request), dataFinal(request)); 
		String sqlSomaValores = sqlvendas.somaValoresVendas(id(request), dataInicial(request), dataFinal(request));
		String sqlSomaQuantidade = sqlvendas.somaQuantidadeVendas(id(request), dataInicial(request), dataFinal(request));
		List<ModelVendas> vendas = daovendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);
		return new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());
	}
	
	protected void printFormEntradasPDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		byte[] relatorio = alternarPrintFormEntradasPDF(request, response);
		impressaoPDF(response, relatorio);
	}
	
	public byte[] alternarPrintFormEntradasPDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		if (dataInicial(request) == null || dataInicial(request).isEmpty() || dataFinal(request) == null || dataFinal(request).isEmpty()) {
			return printFormEntradasPDFSemDatas(request, response);
		} else {
			return printFormEntradasPDFComDatas(request, response);
		}
	}
	
	public byte[] printFormEntradasPDFSemDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		return new ReportUtil().geraReltorioPDF(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2)), "entradas", request.getServletContext());
	}
	
	public byte[] printFormEntradasPDFComDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		List<ModelPedidos> entradas = daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2, dataInicial(request), dataFinal(request)));
		return new ReportUtil().geraReltorioPDF(entradas, "entradas", request.getServletContext());
	}
}