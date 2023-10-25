package Servlet;

import java.io.IOException;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIFornecimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends APIFornecimento {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoPedidos pedido = new daoPedidos();
	daoPedidos daopedidos = new daoPedidos();
	DAOFerramentas dao = new DAOFerramentas();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();
	daoFornecimento daofornecedor = new daoFornecimento();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				incluirPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				cancelarPedido(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				deletarFornecedor(request);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarFornecedor")){
				cadastrarFornecedor(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void cadastrarFornecedor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelProdutos modelProdutos = new ModelProdutos();
		modelProdutos.setId(id_produto(request));
		new daoFornecimento().gravarNovoFornecedor(sqlFornecimento.gravar(nomeFornecedor(request), modelProdutos, tempoEntrega(request), valor(request)));
		setarAtributos(request, response);
	}
	
	protected void incluirPedido(HttpServletRequest request) throws Exception {
		fornecedorIncluirPedido(request);
	}
	
	public void fornecedorIncluirPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		ModelFornecimento modelFornecedor = daofornecedor.consultarFornecedor(sqlFornecimento.consulta(id_fornecedor(request)));
		pedidoIncluirPedido(request, modelFornecedor);
	}
	
	public void pedidoIncluirPedido(HttpServletRequest request, ModelFornecimento modelFornecedor) throws Exception {
		ModelPedidos modelPedido = new ModelPedidos();
		modelPedido.setValor(modelFornecedor.getValor());
		modelPedido.setFornecedor_pai_id(modelFornecedor);
		modelPedido.setQuantidade(quantidade(request));
		modelPedido.setDatapedido(dataPedido(request));
		modelPedido.setDataentrega(dao.plusDias(dataPedido(request), modelFornecedor.getTempoentrega()));
		modelPedido.setUsuario_pai_id(user(request));
		modelPedido.setProduto_pai_id(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		modelPedido.setNome(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))).getNome());
		persistenciaIncluirPedido(request, modelPedido);
	}
	
	public void persistenciaIncluirPedido(HttpServletRequest request, ModelPedidos modelPedido) throws Exception {
		daopedidos.gravarPedido(sqlpedidos.gravarPedido(modelPedido));
		setarAtributosAjax(request);
	}

	protected void confirmarPedido(HttpServletRequest request) throws Exception {
		System.out.println("Aqui estamos.");
		daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request)));
		daoproduto.adicionaProdutoCaixa(id_produto(request), quantidade(request));
		daopedidos.mudarStatus(id_pedido(request), 2);
	}
	
	protected void cancelarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
		daopedidos.mudarStatus(id_pedido(request), 1);
	}

	protected void deletarFornecedor(HttpServletRequest request) throws Exception {
		daofornecedor.excluirFornecedor(id_fornecedor(request));
	}
}
