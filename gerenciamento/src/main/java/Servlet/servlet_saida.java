package Servlet;

import java.io.IOException;
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
			int id = super.getUsuarioLogado(request).getId();
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {

				List<ModelProdutos> produtos = daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id), id);
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

				daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("idProduto")), -quantidade);
				ModelVendas venda = new ModelVendas();
				venda.setProduto_pai(daoproduto.consultaProduto(Long.parseLong(request.getParameter("idProduto")), id));
				venda.setQuantidade(quantidade);
				venda.setDataentrega(dao.converterDatas(request.getParameter("dataVenda")));
				venda.setValortotal(valor * quantidade);
				daovendas.gravarVenda(venda, id);
				
				request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(id), id));

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadProduto")) {
				ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(request.getParameter("id")), id);
				Double medias = daoFornecimento.mediaValoresFornecimento(Long.parseLong(request.getParameter("id")));

				String json = new Gson().toJson(produto) + "|" + new Gson().toJson(medias);
				super.impressaoJSON(response, json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadFinanceiro")) {

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("financeiro")) {

				request.getRequestDispatcher("principal/financeiro.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaVendas")) {
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);

					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					List<ModelVendas> vendas = daovendas.listarVendas(sqlvendas.listaVendas(id), sqlvendas.somaValoresVendas(id), sqlvendas.somaQuantidadeVendas(id));

					String json = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
					super.impressaoJSON(response, json);
				} else {
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);

					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					String listaVendasTempo = sqlvendas.listaVendasTempo(id, dataInicial, dataFinal);
					String somaValoresVendasTempo = sqlvendas.somaValoresVendasTempo(id, dataInicial, dataFinal);
					String somaQuantidadeVendasTempo = sqlvendas.somaQuantidadeVendasTempo(id, dataInicial, dataFinal);
					List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);

					String json = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
					super.impressaoJSON(response, json);
				}
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
