package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import DAO.API;
import DAO.DaoGenerico;
import DAO.daoPedidos;
import DAO.daoVendas;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLVendas;
import Util.ReportUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelPedidos;
import model.ModelVendas;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends servlet_recuperacao_login{
	private static final long serialVersionUID = 1L;
	
	daoVendas daoVendas = new daoVendas();
	daoPedidos daoPedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
	SQLVendas sqlvendas = new SQLVendas();
    SQLPedidos sqlpedidos = new SQLPedidos();
    API api = new API();
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
			int id = super.getUsuarioLogado(request).getId();
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("irParaRelatorios")) {

				request.getRequestDispatcher("principal/relatorios.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vendas")) {
				
				api.impressaoJSONVendas(response, daoVendas.listarVendas(sqlvendas.listaVendas(id), sqlvendas.somaValoresVendas(id), sqlvendas.somaQuantidadeVendas(id)));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("entradas")) {

				api.impressaoJSONPedidos(response, daoPedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id, "2")));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {
				
				api.impressaoJSONPedidos(response, daoPedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id)));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {

				api.impressaoJSONPedidos(response, daoPedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id, 1)));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormVendasPDF")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					List<ModelVendas> vendas = daoVendas.listarVendas(sqlvendas.listaVendas(id), sqlvendas.somaValoresVendas(id), sqlvendas.somaQuantidadeVendas(id));

					byte[] relatorio = new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());

					api.impressaoRelatorio(response, relatorio);
				} else {
					String sqlListaVendas = sqlvendas.listaVendasTempo(id, dataInicial, dataFinal); 
					String sqlSomaValores = sqlvendas.somaValoresVendasTempo(id, dataInicial, dataFinal);
					String sqlSomaQuantidade = sqlvendas.somaQuantidadeVendasTempo(id, dataInicial, dataFinal);
					List<ModelVendas> vendas = daoVendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);

					byte[] relatorio = new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());

					api.impressaoRelatorio(response, relatorio);
				}
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormEntradasPDF")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {

					byte[] relatorio = new ReportUtil().geraReltorioPDF(daoPedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id, 2)), "entradas", request.getServletContext());
					api.impressaoRelatorio(response, relatorio);
					
				} else {
					List<ModelPedidos> entradas = daoPedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioIdTempo(id, 2, dataInicial, dataFinal));
					byte[] relatorio = new ReportUtil().geraReltorioPDF(entradas, "entradas", request.getServletContext());
					api.impressaoRelatorio(response, relatorio);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}