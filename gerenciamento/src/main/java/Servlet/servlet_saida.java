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
			int id = super.getUser(request).getId();
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
		super.setarAtributosSaida(request, response);
	}
	
	protected void vender(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int valor = dao.converterDinheiroInteger(request.getParameter("valor"));// R$####,00;
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		ModelData dataVenda = new ModelData();

		dataVenda.setDatavenda(dao.converterDatas(request.getParameter("dataVenda")));
		dataVenda.setValortotal((long) (valor * quantidade));
		dataVenda.setUsuario_pai_id(super.getUser(request));

		boolean booleana = daoVendasRelatorio.buscarData(dataVenda);

		if (booleana) {
			daoVendasRelatorio.atualizarDataEValor(dataVenda);
		} else {
			daoVendasRelatorio.inserirDataEValor(dataVenda);
		}

		daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("idProduto")), -quantidade);
		ModelVendas venda = new ModelVendas();
		venda.setProduto_pai(daoproduto.consultaProduto(Long.parseLong(request.getParameter("idProduto")), super.getUserId(request)));
		venda.setQuantidade(quantidade);
		venda.setDataentrega(dao.converterDatas(request.getParameter("dataVenda")));
		venda.setValortotal(valor * quantidade);
		daovendas.gravarVenda(venda, super.getUserId(request));
		
		super.setarAtributos(request);
	}
	
	protected void loadProduto(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, Exception {
		ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(request.getParameter("id")), super.getUserId(request));
		Double medias = daoFornecimento.mediaValoresFornecimento(Long.parseLong(request.getParameter("id")));

		String json = new Gson().toJson(produto) + "|" + new Gson().toJson(medias);
		super.impressaoJSON(response, json);
	}
	
	protected void financeiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/financeiro.jsp").forward(request, response);
	}
	
	protected void carregarListaVendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		int id = super.getUserId(request);
		ModelData dataVenda = new ModelData();
		ModelData dataEntrada = new ModelData();
		dataVenda.setUsuario_pai_id(super.getUser(request));
		dataEntrada.setUsuario_pai_id(super.getUser(request));
		if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
			List<ModelVendas> vendas = daovendas.listarVendas(sqlvendas.listaVendas(id), sqlvendas.somaValoresVendas(id), sqlvendas.somaQuantidadeVendas(id));
			List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);
			List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

			String json = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
			super.impressaoJSON(response, json);
		} else {
			String listaVendasTempo = sqlvendas.listaVendasTempo(id, dataInicial, dataFinal);
			String somaValoresVendasTempo = sqlvendas.somaValoresVendasTempo(id, dataInicial, dataFinal);
			String somaQuantidadeVendasTempo = sqlvendas.somaQuantidadeVendasTempo(id, dataInicial, dataFinal);
			List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);
			List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);
			List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

			String json = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
			super.impressaoJSON(response, json);
		}
	}
	

}
