package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;
import DAO.DaoGenerico;
import DAO.daoPedidos;
import DAO.daoVendas;
import Servlet.SQL.SQL;
import Util.ReportUtil;
import jakarta.servlet.ServletException;
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
    SQL sql = new SQL();
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

				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(daoVendas.listarVendas(sql.listaVendas(id), sql.somaValoresVendas(id), sql.somaQuantidadeVendas(id))));
				printWriter.close();

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("entradas")) {

				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(daoPedidos.listarPedidos(sql.listaPedidosProdutoId(id, "2"))));
				printWriter.close();

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {

				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(daoPedidos.listarPedidos(sql.listaPedidosProdutoId(id, "0, 1, 2"))));
				printWriter.close();

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {

				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(daoPedidos.listarPedidos(sql.listaPedidosUsuarioId(id, 1))));
				printWriter.close();

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormVendasPDF")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					List<ModelVendas> vendas = daoVendas.listarVendas(sql.listaVendas(id), sql.somaValoresVendas(id), sql.somaQuantidadeVendas(id));

					byte[] relatorio = new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());

					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.setContentType("application/octet-stream");
					response.getOutputStream().write(relatorio);
					response.getOutputStream().flush();
					
				} else {
					String sqlListaVendas = sql.listaVendasTempo(id, dataInicial, dataFinal); 
					String sqlSomaValores = sql.somaValoresVendasTempo(id, dataInicial, dataFinal);
					String sqlSomaQuantidade = sql.somaQuantidadeVendasTempo(id, dataInicial, dataFinal);
					List<ModelVendas> vendas = daoVendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);

					byte[] relatorio = new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());

					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.setContentType("application/octet-stream");
					response.getOutputStream().write(relatorio);
					response.getOutputStream().flush();
				}
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormEntradasPDF")) {
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {

					byte[] relatorio = new ReportUtil().geraReltorioPDF(daoPedidos.listarPedidos(sql.listaPedidosUsuarioId(id, 2)), "entradas", request.getServletContext());

					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.setContentType("application/octet-stream");
					response.getOutputStream().write(relatorio);
					response.getOutputStream().flush();
					
				} else {
					List<ModelPedidos> entradas = daoPedidos.listarPedidos(sql.listaPedidosUsuarioIdTempo(id, 2, dataInicial, dataFinal));

					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.setContentType("application/octet-stream");
					response.getOutputStream().write(new ReportUtil().geraReltorioPDF(entradas, "entradas", request.getServletContext()));
					response.getOutputStream().flush();
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