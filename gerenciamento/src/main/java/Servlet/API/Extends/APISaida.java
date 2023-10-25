package Servlet.API.Extends;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.DAORelatorio;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLRelatorio;
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
	DAOFerramentas dao = new DAOFerramentas();
	daoProdutos daoproduto = new daoProdutos();
	DAORelatorio daoRelatorio = new DAORelatorio();
	daoVendas daovendas = new daoVendas();
	daoFornecimento daoFornecimento = new daoFornecimento();
	SQLVendas sqlvendas = new SQLVendas();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLRelatorio sqlrelatorio = new SQLRelatorio();
	
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
		daoRelatorio.alternarDataEValorVendas(dataVenda);
		return parametrosVenderAddProdutoCaixa(request);
	}
	
	public HttpServletRequest parametrosVenderAddProdutoCaixa(HttpServletRequest request) throws Exception {
		daoproduto.adicionaProdutoCaixa(id_produto(request), -quantidade(request));
		return parametrosVenderSetarVenda(request);
	}
	
	public HttpServletRequest parametrosVenderSetarVenda(HttpServletRequest request) throws SQLException, Exception {
		ModelVendas venda = new ModelVendas();
		venda.setProduto_pai(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
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
		ModelProdutos produto = daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request)));
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
		List<ModelData> dataVendas = daoRelatorio.listarDatasVendas(sqlrelatorio.listaDatasVendas(id(request)));
		List<ModelData> dataEntradas = daoRelatorio.listarDatasEntradas(sqlrelatorio.listaDatasEntradas(id(request)));
		String superJson = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
		impressaoMultiJSON(response, superJson);
	}
	
	public void parametrosCarregarListaVendasJsonTempo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String listaVendasTempo = sqlvendas.listaVendasTempo(id(request), dataInicial(request), dataFinal(request));
		String somaValoresVendasTempo = sqlvendas.somaValoresVendasTempo(id(request), dataInicial(request), dataFinal(request));
		String somaQuantidadeVendasTempo = sqlvendas.somaQuantidadeVendasTempo(id(request), dataInicial(request), dataFinal(request));
		List<ModelVendas> vendas = daovendas.listarVendas(listaVendasTempo, somaValoresVendasTempo, somaQuantidadeVendasTempo);
		List<ModelData> dataVendas = daoRelatorio.listarDatasVendas(sqlrelatorio.listaDatasVendas(id(request), dataInicial(request), dataFinal(request)));
		List<ModelData> dataEntradas = daoRelatorio.listarDatasEntradas(sqlrelatorio.listaDatasEntradas(id(request), dataInicial(request), dataFinal(request)));
		String superJson = new Gson().toJson(vendas) + "|" + new Gson().toJson(dataVendas) + "|" + new Gson().toJson(dataEntradas);
		impressaoMultiJSON(response, superJson);
	}
}
