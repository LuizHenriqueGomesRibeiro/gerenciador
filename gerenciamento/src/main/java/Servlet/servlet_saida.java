package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelVendas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.daoVendasRelatorio;
import Servlet.SQL.SQL;
import Util.BeanChart;

/**
 * Servlet implementation class servletLogin
 */
@WebServlet(urlPatterns = { "/servlet_saida" })
public class servlet_saida extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;

	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	daoVendas daovendas = new daoVendas();
	daoVendasRelatorio daoVendasRelatorio = new daoVendasRelatorio();
	daoEntradasRelatorio daoEntradasRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQL sql = new SQL();

	public servlet_saida() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		try {
			int id = super.getUsuarioLogado(request).getId();
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {

				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id);
				request.setAttribute("produtos", produtos);

				request.getRequestDispatcher("principal/saida.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vender")) {
				int valor = dao.converterDinheiroInteger(request.getParameter("valor"));// R$####,00;
				int quantidade = Integer.parseInt(request.getParameter("quantidade"));
				ModelData dataVenda = new ModelData();

				dataVenda.setDatavenda(dao.converterDatas(request.getParameter("dataVenda")));
				dataVenda.setValortotal((long) (valor * quantidade));
				dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));

				boolean booleana = daoVendasRelatorio.buscarData(dataVenda);

				if (booleana) {
					daoVendasRelatorio.atualizarDataEValor(dataVenda);
				} else {
					daoVendasRelatorio.inserirDataEValor(dataVenda);
				}

				daoproduto.adicionaProdutoCaixa(Integer.parseInt(request.getParameter("idProduto")), -quantidade);
				ModelVendas venda = new ModelVendas();
				venda.setProduto_pai(daoproduto.consultaProduto(Long.parseLong(request.getParameter("idProduto")), id));
				venda.setQuantidade(quantidade);
				venda.setDataentrega(dao.converterDatas(request.getParameter("dataVenda")));
				venda.setValortotal(valor * quantidade);
				daovendas.gravarVenda(venda, id);
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadProduto")) {
				ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(request.getParameter("id")), id);
				Double medias = daoFornecimento.mediaValoresFornecimento(Long.parseLong(request.getParameter("id")));

				PrintWriter out = response.getWriter();
				out.print(new Gson().toJson(produto) + "|" + new Gson().toJson(medias));
				out.flush();

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadFinanceiro")) {

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("financeiro")) {

				RequestDispatcher despache = request.getRequestDispatcher("principal/financeiro.jsp");
				despache.forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaVendas")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				int status = 2;
				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					BeanChart bean = daovendas.listarVendasGrafico(sql.listaVendasValorData(id));
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql.listaEntradasValorData(id, status));

					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);

					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					List<ModelVendas> vendas = daovendas.listarVendas(sql.listaVendas(id), sql.somaValoresVendas(id), sql.somaQuantidadeVendas(id));

					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
					out.print(gson.toJson(bean) + "|" + gson.toJson(vendas) + "|" + gson.toJson(entradas) + "|" + gson.toJson(dataVendas) + "|" + gson.toJson(dataEntradas));
					out.flush();

				} else {
					BeanChart bean = daovendas.listarVendasGrafico(sql.listaVendasValorDataTempo(id, dataInicial, dataFinal));
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql.listaEntradasValorDataTempo(id, status, dataInicial, dataFinal));

					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);

					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					String listaVendasTempo = sql.listaVendasTempo(id, dataInicial, dataFinal);
					String somaValoresVendasTempo = sql.somaValoresVendasTempo(id, dataInicial, dataFinal);
					String somaQuantidadeVendasTempo = sql.somaQuantidadeVendasTempo(id, dataInicial, dataFinal);
					List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);

					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
					out.print(gson.toJson(bean) + "|" + gson.toJson(vendas) + "|" + gson.toJson(entradas) + "|" + gson.toJson(dataVendas) + "|" + gson.toJson(dataEntradas));
					out.flush();
				}
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaEntradas")) {
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				int status = 2;
				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					BeanChart bean = daopedidos.listarEntradasGrafico(sql.listaEntradasValorData(id, status));

					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql.listaEntradasValorData(id, status));

					PrintWriter out = response.getWriter();
					out.print(new Gson().toJson(bean) + "|" + new Gson().toJson(entradas));
					out.flush();
				} else {
					BeanChart bean = daopedidos.listarEntradasGrafico(sql.listaEntradasValorDataTempo(id, status, dataInicial, dataFinal));

					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql.listaEntradasTempo(id, status, dataInicial, dataFinal));

					PrintWriter out = response.getWriter();
					out.print(new Gson().toJson(bean) + "|" + new Gson().toJson(entradas));
					out.flush();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
