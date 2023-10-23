package Servlet.API.Extends;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLVendas;
import Servlet.API.APIDespache;
import Util.ReportUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelPedidos;
import model.ModelVendas;

public class APIRelatorios extends APIDespache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	daoLogin daologin = new daoLogin();
	SQLVendas sqlvendas = new SQLVendas();
	daoVendas daovendas = new daoVendas();

	public String vendas(HttpServletRequest request) throws SQLException, ParseException, Exception {
		Gson gson = new Gson();
		return gson.toJson(daovendas.listarVendas(sqlvendas.listaVendas(id(request)), sqlvendas.somaValoresVendas(id(request)), sqlvendas.somaQuantidadeVendas(id(request))));
	}
	
	public String entradas(HttpServletRequest request) throws SQLException, Exception {
		return new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2)));
	}
	
	public String pedidos(HttpServletRequest request) throws SQLException, Exception {
		return new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request))));
	}
	
	public String cancelamentos(HttpServletRequest request) throws SQLException, Exception {
		return new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 1)));
	}
	
	protected byte[] alternarPrintFormVendasPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (dataInicial(request) == null || dataInicial(request).isEmpty() || dataFinal(request) == null || dataFinal(request).isEmpty()) {
			return printFormVendasPDFSemDatas(request, response);
		} else {
			return printFormVendasPDFComDatas(request, response);
		}
	}
	
	public byte[] printFormVendasPDFSemDatas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sqlListaVendas = sqlvendas.listaVendas(id(request)); 
		String sqlSomaValores = sqlvendas.somaValoresVendas(id(request));
		String sqlSomaQuantidade = sqlvendas.somaQuantidadeVendas(id(request));
		List<ModelVendas> vendas = daovendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);
		return new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());
	}
	
	public byte[] printFormVendasPDFComDatas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sqlListaVendas = sqlvendas.listaVendasTempo(id(request), dataInicial(request), dataFinal(request)); 
		String sqlSomaValores = sqlvendas.somaValoresVendasTempo(id(request), dataInicial(request), dataFinal(request));
		String sqlSomaQuantidade = sqlvendas.somaQuantidadeVendasTempo(id(request), dataInicial(request), dataFinal(request));
		List<ModelVendas> vendas = daovendas.listarVendas(sqlListaVendas, sqlSomaValores, sqlSomaQuantidade);
		return new ReportUtil().geraReltorioPDF(vendas, "vendas", request.getServletContext());
	}
	
	public byte[] alternarPrintFormEntradasPDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		if (dataInicial(request) == null || dataInicial(request).isEmpty() || dataFinal(request) == null || dataFinal(request).isEmpty()) {
			return printFormEntradasPDFSemDatas(request, response);
		} else {
			return printFormEntradasPDFComDatas(request, response);
		}
	}
	
	public byte[] printFormEntradasPDFSemDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		return new ReportUtil().geraReltorioPDF(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2)), "entradas", request.getServletContext());
	}
	
	public byte[] printFormEntradasPDFComDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		List<ModelPedidos> entradas = daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(id(request), 2, dataInicial(request), dataFinal(request)));
		return new ReportUtil().geraReltorioPDF(entradas, "entradas", request.getServletContext());
	}
}
