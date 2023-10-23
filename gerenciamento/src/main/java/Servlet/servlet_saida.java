package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.Despache;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.daoVendasRelatorio;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLVendas;
import Servlet.API.Extends.APISaida;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelProdutos;
import model.ModelVendas;

/**
 * Servlet implementation class servletLogin
 */
@WebServlet(urlPatterns = { "/servlet_saida" })
public class servlet_saida extends APISaida {
	private static final long serialVersionUID = 1L;

	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	daoVendas daovendas = new daoVendas();
	daoVendasRelatorio daoVendasRelatorio = new daoVendasRelatorio();
	daoEntradasRelatorio daoEntradasRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQLVendas sqlvendas = new SQLVendas();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	Despache api = new Despache();

	public servlet_saida() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		try {
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {
				caixaListar(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vender")) {
				vender(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadProduto")) {
				loadProduto(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadFinanceiro")) {

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("financeiro")) {
				financeiro(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaVendas")) {
				carregarListaVendas(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaEntradas")) {
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					
				} else {
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void caixaListar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributosSaida(request, response);
	}
	
	protected void vender(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosVender(request);
		setarAtributosAjax(request);
	}
	
	protected void loadProduto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = parametrosLoadProduto(request);
		impressaoJSON(response, json);
	}
	
	protected void financeiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/financeiro.jsp").forward(request, response);
	}
	
	protected void carregarListaVendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = super.parametrosCarregarListaVendas(request, response);
		impressaoJSON(response, json);
	}
}
