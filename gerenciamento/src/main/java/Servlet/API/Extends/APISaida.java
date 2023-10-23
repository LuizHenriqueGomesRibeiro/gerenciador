package Servlet.API.Extends;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.daoVendasRelatorio;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLVendas;
import Servlet.API.APIDespache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelProdutos;
import model.ModelVendas;

public class APISaida extends APIDespache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DaoGenerico dao = new DaoGenerico();
	daoProdutos daoproduto = new daoProdutos();
	daoEntradasRelatorio daoEntradasRelatorio = new daoEntradasRelatorio();
	daoVendasRelatorio daoVendasRelatorio = new daoVendasRelatorio();
	daoVendas daovendas = new daoVendas();
	daoFornecimento daoFornecimento = new daoFornecimento();
	SQLVendas sqlvendas = new SQLVendas();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	
	public HttpServletRequest parametrosVender(HttpServletRequest request) throws Exception {
		return parametrosVenderDataVenda(request);
	}
	
	public HttpServletRequest parametrosVenderDataVenda(HttpServletRequest request) throws Exception {
		ModelData dataVenda = new ModelData();
		dataVenda.setDatavenda(dataVenda(request));
		dataVenda.setValortotal(valor(request) * quantidade(request));
		dataVenda.setUsuario_pai_id(user(request));
		return parametrosVenderAlternarData(request, dataVenda);
	}
	
	public HttpServletRequest parametrosVenderAlternarData(HttpServletRequest request, ModelData dataVenda) throws Exception {
		daoVendasRelatorio.alternarDataEValor(dataVenda);
		return parametrosVenderAddProdutoCaixa(request);
	}
	
	public HttpServletRequest parametrosVenderAddProdutoCaixa(HttpServletRequest request) throws Exception {
		daoproduto.adicionaProdutoCaixa(id_produto(request), -quantidade(request));
		return parametrosVenderSetarVenda(request);
	}
	
	public HttpServletRequest parametrosVenderSetarVenda(HttpServletRequest request) throws SQLException, Exception {
		ModelVendas venda = new ModelVendas();
		venda.setProduto_pai(daoproduto.consultaProduto(id_produto(request), id(request)));
		venda.setQuantidade(quantidade(request));
		venda.setDataentrega(dataVenda(request));
		venda.setValortotal(valor(request) * quantidade(request));
		return parametrosVenderGravarVenda(request, venda);
	}
	
	public HttpServletRequest parametrosVenderGravarVenda(HttpServletRequest request, ModelVendas venda) throws Exception {
		daovendas.gravarVenda(venda, id(request));
		return request;
	}
	
	public String parametrosLoadProduto(HttpServletRequest request) throws NumberFormatException, Exception {
		ModelProdutos produto = daoproduto.consultaProduto(id_produto(request), id(request));
		Double medias = daoFornecimento.mediaValoresFornecimento(id_produto(request));
		return parametrosLoadProdutoJson(produto, medias);
	}
	
	public String parametrosLoadProdutoJson(ModelProdutos produto, Double medias) {
		String json = new Gson().toJson(produto) + "|" + new Gson().toJson(medias);
		return json;
	}
	
	public void parametrosCarregarListaVendas(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		String listaVendasTempo = sqlvendas.listaVendas(id(request));
		String somaValoresVendasTempo = sqlvendas.somaValoresVendas(id(request));
		String somaQuantidadeVendasTempo = sqlvendas.somaQuantidadeVendas(id(request));
		List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);
		List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(id(request));
		List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(id(request));
		Gson gson = new Gson();
		String json2 = gson.toJson(vendas);
		String json3 = gson.toJson(dataVendas);
		String json4 = gson.toJson(dataEntradas);
		PrintWriter out = response.getWriter();
	    out.print(json2 + "|" + json3 + "|" + json4);
	    out.flush();
	}
	
	public void parametrosCarregarListaVendasJsonTempo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String listaVendasTempo = sqlvendas.listaVendasTempo(id(request), dataInicial(request), dataFinal(request));
		String somaValoresVendasTempo = sqlvendas.somaValoresVendasTempo(id(request), dataInicial(request), dataFinal(request));
		String somaQuantidadeVendasTempo = sqlvendas.somaQuantidadeVendasTempo(id(request), dataInicial(request), dataFinal(request));
		List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);
		List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(id(request), dataInicial(request), dataFinal(request));
		List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(id(request));
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.print(new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas));
		printWriter.flush();
	}
	
	
}
