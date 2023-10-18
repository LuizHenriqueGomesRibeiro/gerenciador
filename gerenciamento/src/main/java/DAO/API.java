package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

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
	
	public ModelPedidos parametrosIncluirPedido(HttpServletRequest request, ModelUsuarios usuario) throws SQLException {
		return fornecedorIncluirPedido(request, usuario);
	}
	
	public ModelPedidos fornecedorIncluirPedido(HttpServletRequest request, ModelUsuarios usuario) throws SQLException {
		ModelFornecimento modelFornecedor = new ModelFornecimento();
		modelFornecedor.setId(Long.parseLong(request.getParameter("id_fornecedor")));
		modelFornecedor.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), usuario.getId()));
		return pedidoIncluirPedido(request, modelFornecedor, usuario);
	}
	
	public ModelPedidos pedidoIncluirPedido(HttpServletRequest request, ModelFornecimento modelFornecedor, ModelUsuarios usuario) throws SQLException {
		ModelPedidos modelPedido = new ModelPedidos();
		modelPedido.setFornecedor_pai_id(daofornecedor.consultarFornecedor(modelFornecedor));
		modelPedido.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
		modelPedido.setDatapedido(request.getParameter("dataPedido"));
		modelPedido.setDataentrega(dao.plusDias(request.getParameter("dataPedido"), modelFornecedor.getTempoentrega()));
		modelPedido.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), usuario.getId()));
		return modelPedido;
	}
	
	public ModelData parametrosConfirmarPedido(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		ModelData modelData = new ModelData();
		modelData.setDatavenda(request.getParameter("data"));
		modelData.setUsuario_pai_id(usuario);
		modelData.setValortotal(daopedidos.buscarPedido(Long.parseLong(request.getParameter("id"))).getValorTotal());
		return modelData;
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
