package Servlet;

import com.google.gson.Gson;

import DAO.DAOFerramentas;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.APIDespache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends APIDespache {
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	daoFornecimento daofornecedor = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	daoLogin daologin = new daoLogin();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String acao = request.getParameter("acao");
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				listar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				paginar(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				excluir(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){
				configuracoes(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				historicoPedidos(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cadastrarProduto")){
				cadastrarProduto(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);
	}
	
	protected void paginar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);	
	}
	
	protected void excluir(HttpServletRequest request, HttpServletResponse response) throws Exception {
		daoproduto.excluirProduto(id_produto(request));
		setarAtributos(request, response);
	}
	
	protected void configuracoes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String produto = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request), id(request))));
		String fornecedores = new Gson().toJson(daofornecedor.listarFornecedores(id_produto(request)));
		String json = produto + "|" + fornecedores;
		impressaoJSON(response, json);
	}

	protected void historicoPedidos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(id_produto(request), 0)));
		impressaoJSON(response, json);
	}
	
	protected void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setNome(nome(request));
		modelProduto.setUsuario_pai_id(user(request));
		daoproduto.alternarProduto(modelProduto);
		setarAtributos(request, response);
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