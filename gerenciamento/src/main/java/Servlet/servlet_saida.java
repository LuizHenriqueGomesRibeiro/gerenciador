package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import DAO.DAOFerramentas;
import DAO.DAOFornecimento;
import DAO.DAOLogin;
import DAO.DAOPedidos;
import DAO.DAOProdutos;
import DAO.DAOVendas;
import DAO.DAORelatorio;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLRelatorio;
import DAO.SQL.SQLVendas;
import Servlet.API.APIDespache;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelProdutos;
import model.ModelVendas;

@WebServlet(urlPatterns = { "/servlet_saida" })
public class servlet_saida extends APIDespache {
	private static final long serialVersionUID = 1L;

	SQLRelatorio sqlrelatorio = new SQLRelatorio();
	DAOLogin daologin = new DAOLogin();
	DAOProdutos daoproduto = new DAOProdutos();
	DAOFornecimento daoFornecimento = new DAOFornecimento();
	DAOPedidos daopedidos = new DAOPedidos();
	DAOVendas daovendas = new DAOVendas();
	DAORelatorio daoVendasRelatorio = new DAORelatorio();
	DAOFerramentas dao = new DAOFerramentas();
	SQLVendas sqlvendas = new SQLVendas();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAORelatorio daoRelatorio = new DAORelatorio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("caixaListar")) {
				caixaListar(request, response);
			} else if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("vender")) {
				vender(request, response);
			} else if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("loadProduto")) {
				loadProduto(request, response);
			} else if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("loadFinanceiro")) {

			} else if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("financeiro")) {
				financeiro(request, response);
			} else if (acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("carregarListaVendas")) {
				carregarListaVendas(request, response);
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
		venderSetarModelVendas(request, response);
	}
	
	protected void venderSetarModelVendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelVendas venda = new ModelVendas();
		venda.setProduto_pai(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request))));
		venda.setQuantidade(quantidade(request));
		venda.setDataentrega(dataVenda(request));
		venda.setValortotal(valor(request) * quantidade(request));
		venderGravarDatas(request, response, venda);
	}
	
	protected void venderGravarDatas(HttpServletRequest request, HttpServletResponse response, ModelVendas venda) throws Exception {
		daovendas.gravarDatas(id(request), dataVenda(request));
		daoproduto.adicionaProdutoCaixa(id_produto(request), -quantidade(request));
		daovendas.gravar(sqlvendas.gravaVenda(venda, id(request)));
		venderSetarDataVenda(request, response);
	}

	protected void venderSetarDataVenda(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelData dataVenda = new ModelData();
		dataVenda.setDatavenda(dataVenda(request));
		dataVenda.setValortotal(valor(request) * quantidade(request));
		dataVenda.setUsuario_pai_id(user(request));
		venderAlternarDataEValorVendas(request, response, dataVenda);
	}
	
	protected void venderAlternarDataEValorVendas(HttpServletRequest request, HttpServletResponse response, ModelData dataVenda) throws Exception {
		daoRelatorio.alternarDataEValorVendas(dataVenda);
		setarAtributosSaida(request, response);
	}
	
	protected void loadProduto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = parametrosLoadProduto(request);
		impressaoJSON(response, json);
	}
	
	public String parametrosLoadProduto(HttpServletRequest request) throws NumberFormatException, Exception {
		ModelProdutos produto = daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request)));
		Double medias = daoFornecimento.mediaValoresFornecimento(id_produto(request));
		return parametrosLoadProdutoJson(produto, medias);
	}
	
	public String parametrosLoadProdutoJson(ModelProdutos produto, Double medias) {
		String json = new Gson().toJson(produto) + "|" + new Gson().toJson(medias);
		return json;
	}
	
	protected void financeiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("principal/financeiro.jsp").forward(request, response);
	}
	
	protected void carregarListaVendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		parametrosCarregarListaVendasAlternar(request, response);
	}
	
	public void parametrosCarregarListaVendasAlternar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (dataInicial(request) == null || dataInicial(request).isEmpty() || dataFinal(request) == null || dataFinal(request).isEmpty()) {
			parametrosCarregarListaVendasJson(request, response);
		} else {
			parametrosCarregarListaVendasJsonTempo(request, response);
		}
	}
	
	public void parametrosCarregarListaVendasJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> sql = parametrosCarregarListaVendasJsonSQL(request);
		List<ModelVendas> vendas = daovendas.listarVendas(sql.get(0), sql.get(1), sql.get(2));
		List<ModelData> dataVendas = daoRelatorio.listarDatasVendas(sqlrelatorio.listaDatasVendas(id(request)));
		List<ModelData> dataEntradas = daoRelatorio.listarDatasEntradas(sqlrelatorio.listaDatasEntradas(id(request)));
		String superJson = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
		impressaoMultiJSON(response, superJson);
	}
	
	public void parametrosCarregarListaVendasJsonTempo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> sql = parametrosCarregarListaVendasJsonTempoSQL(request);
		List<ModelVendas> vendas = daovendas.listarVendas(sql.get(0), sql.get(1), sql.get(2));
		List<ModelData> dataVendas = daoRelatorio.listarDatasVendas(sqlrelatorio.listaDatasVendas(id(request), dataInicial(request), dataFinal(request)));
		List<ModelData> dataEntradas = daoRelatorio.listarDatasEntradas(sqlrelatorio.listaDatasEntradas(id(request), dataInicial(request), dataFinal(request)));
		String superJson = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
		impressaoMultiJSON(response, superJson);
	}
	
	public List<String> parametrosCarregarListaVendasJsonSQL(HttpServletRequest request) throws Exception{
		List<String> retorno = new ArrayList<String>();
		retorno.add(sqlvendas.listaVendas(id(request)));
		retorno.add(sqlvendas.somaValoresVendas(id(request)));
		retorno.add(sqlvendas.somaQuantidadeVendas(id(request)));
		return retorno;
	}
	
	public List<String> parametrosCarregarListaVendasJsonTempoSQL(HttpServletRequest request) throws Exception{
		List<String> retorno = new ArrayList<String>();
		retorno.add(sqlvendas.listaVendas(id(request), dataInicial(request), dataFinal(request)));
		retorno.add(sqlvendas.somaValoresVendas(id(request), dataInicial(request), dataFinal(request)));
		retorno.add(sqlvendas.somaQuantidadeVendas(id(request), dataInicial(request), dataFinal(request)));
		return retorno;
	}
}
