package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.servletFornecimento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelParametros;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;
import model.ModelVendas;

public class API {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	
	public ModelPedidos parametrosConfirmarPedidoTeste(HttpServletRequest request, ModelUsuarios usuario) throws SQLException {
		ModelPedidos modelPedidos = new ModelPedidos();
		modelPedidos.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
		modelPedidos.setDatapedido(request.getParameter("dataPedido"));
		modelPedidos.setId_produto(Long.parseLong(request.getParameter("id_produto")));
		modelPedidos.setId_fornecedor(Long.parseLong(request.getParameter("id_fornecedor")));
		modelPedidos.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), usuario.getId()));
		ModelFornecimento fornecimento = new ModelFornecimento();
		fornecimento.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), usuario.getId()));
		fornecimento.setId(Long.parseLong(request.getParameter("id_fornecedor")));
		modelPedidos.setFornecedor_pai_id(daofornecedor.consultarFornecedor(fornecimento));
		modelPedidos.setUsuario_pai_id(usuario);
		return modelPedidos;
	}
	
	public ModelParametros parametrosIncluirPedido(HttpServletRequest request) {
		ModelParametros modelParametros = new ModelParametros();
		modelParametros.setQuantidade(request.getParameter("quantidade"));
		modelParametros.setDataPedido(request.getParameter("dataPedido").replaceAll("\\/", "-"));
		modelParametros.setId_produto(Long.parseLong(request.getParameter("id_produto")));
		modelParametros.setId_fornecedor(Long.parseLong(request.getParameter("id_fornecedor")));
		return modelParametros;
	}
	
	public ModelParametros parametrosConfirmarPedido(HttpServletRequest request) throws Exception {
		ModelParametros modelParametros = new ModelParametros();
		modelParametros.setStatus(2);
		modelParametros.setQuantidade(request.getParameter("quantidade"));
		modelParametros.setDataEntrega(request.getParameter("dataEntrega"));
		modelParametros.setUsuario(new servletFornecimento().getUsuarioLogado(request));
		return modelParametros;
	}
	
	public HttpServletRequest setarAtributos(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosLIMIT10(usuario.getId()), usuario.getId()));
		return plusAtributos(request, usuario);
	}
	
	public HttpServletRequest setarAtributosOFFSET(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("produtos", daoproduto.listarProdutos(sqlprodutos.listaProdutosOFFSET(usuario.getId(), Integer.parseInt(request.getParameter("pagina"))), usuario.getId()));
		return plusAtributos(request, usuario);
	}
	
	public HttpServletRequest plusAtributos(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(usuario.getId()));
		request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(usuario.getId())));
		request.setAttribute("usuario", usuario);
		return request;
	}
	
	public HttpServletResponse cabecarioImpressaoRelatorio(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
		response.setContentType("application/octet-stream");
		return response;
	}
	
	public HttpServletResponse impressaoRelatorio(HttpServletResponse response, byte[] relatorio) throws IOException {
		cabecarioImpressaoRelatorio(response);
		response.getOutputStream().write(relatorio);
		response.getOutputStream().flush();
		return response;
	}
	
	public HttpServletResponse cabecarioImpressaoJSON(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return response;
	}
	
	public void impressaoJSONPedidos(HttpServletResponse response, List<ModelPedidos> pedidos) throws IOException {
		PrintWriter printWriter = response.getWriter();
		cabecarioImpressaoJSON(response);
		printWriter.write(new Gson().toJson(pedidos));
		printWriter.close();
	}
	
	public void impressaoJSONVendas(HttpServletResponse response, List<ModelVendas> vendas) throws IOException {
		PrintWriter printWriter = response.getWriter();
		cabecarioImpressaoJSON(response);
		printWriter.write(new Gson().toJson(vendas));
		printWriter.close();
	}
	
	public void impressaoJSONProdutos(HttpServletResponse response, ModelProdutos produtos) throws IOException {
		PrintWriter printWriter = response.getWriter();
		cabecarioImpressaoJSON(response);
		printWriter.write(new Gson().toJson(produtos));
		printWriter.close();
	}
}
