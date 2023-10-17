package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import DAO.SQL.SQLProdutos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelParametros;
import model.ModelPedidos;
import model.ModelUsuarios;
import model.ModelVendas;

public class API {
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	
	public ModelParametros parametrosIncluirPedido(HttpServletRequest request) {
		ModelParametros modelParametros = new ModelParametros();
		String dataPedido = request.getParameter("dataPedido").replaceAll("\\/", "-");
		modelParametros.setQuantidade(request.getParameter("quantidade"));
		modelParametros.setDataPedido(dataPedido);
		modelParametros.setId_produto(Long.parseLong(request.getParameter("id_produto")));
		modelParametros.setId_fornecedor(Long.parseLong(request.getParameter("id_fornecedor")));
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
	
	public HttpServletResponse impressaoRelatorio(HttpServletResponse response, byte[] relatorio) throws IOException {
		response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
		response.setContentType("application/octet-stream");
		response.getOutputStream().write(relatorio);
		response.getOutputStream().flush();
		return response;
	}
	
	public void impressaoJSONPedidos(HttpServletResponse response, List<ModelPedidos> pedidos) throws IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(new Gson().toJson(pedidos));
		printWriter.close();
	}
	
	public void impressaoJSONVendas(HttpServletResponse response, List<ModelVendas> vendas) throws IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		printWriter.write(new Gson().toJson(vendas));
		printWriter.close();
	}
}
