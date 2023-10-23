package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.Despache;
import DAO.DaoGenerico;
import DAO.daoPedidos;
import DAO.daoVendas;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLVendas;
import Servlet.API.Extends.APIRelatorios;
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
public class ServletRelatorios extends APIRelatorios{
	private static final long serialVersionUID = 1L;
	
	daoVendas daoVendas = new daoVendas();
	daoPedidos daoPedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
	SQLVendas sqlvendas = new SQLVendas();
    SQLPedidos sqlpedidos = new SQLPedidos();
    Despache api = new Despache();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRelatorios() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		setarAtributosirParaRelatorios(request, response);
	}
	
	protected void vendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = vendas(request);
		impressaoJSON(response, json);
	}
	
	protected void entradas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = entradas(request);
		impressaoJSON(response, json);
	}
	
	protected void pedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = pedidos(request);
		impressaoJSON(response, json);
	}
	
	protected void cancelamentos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = cancelamentos(request);
		impressaoJSON(response, json);
	}
	
	protected void printFormVendasPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] relatorio = alternarPrintFormVendasPDF(request, response);
		impressaoPDF(response, relatorio);
	}
	
	protected void printFormEntradasPDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		byte[] relatorio = alternarPrintFormEntradasPDF(request, response);
		impressaoPDF(response, relatorio);
	}
}