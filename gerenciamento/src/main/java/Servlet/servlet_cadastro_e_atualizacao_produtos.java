package Servlet;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import DAO.DAOFerramentas;
import DAO.DAOFornecimento;
import DAO.DAOLogin;
import DAO.DAOPedidos;
import DAO.DAOProdutos;
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
	DAOPedidos daopedidos = new DAOPedidos();
	DAOProdutos daoproduto = new DAOProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DAOFerramentas dao = new DAOFerramentas();
	DAOFornecimento daofornecedor = new DAOFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	DAOLogin daologin = new DAOLogin();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("listar")) {
				listar(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("paginar")){
				paginar(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("configuracoes")){
				configuracoes(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("historioPedidos")){
				historicoPedidos(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("cancelarPedido")){
				cancelarPedido(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("deletarFornecedor")){
				deletarFornecedor(request);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("carregarTodosPedidos")){
				carregarTodosPedidos(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("validarExclusao")){
				validarExclusao(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("abrirTodosFornecedores")){
				abrirTodosFornecedores(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("verificarHaNomeFornecedor")){
				verificarHaNomeFornecedor(request, response);
			}
		} catch (Exception e) {
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("incluirPedido")) {
				incluirPedido(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("cadastrarFornecedor")){
				cadastrarFornecedor(request, response);
			}else if(acao(request) != null && !acao(request).isEmpty() && acao(request).equalsIgnoreCase("cadastrarProduto")){
				cadastrarProduto(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);
	}
	
	protected void paginar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setarAtributos(request, response);	
	}
	
	protected void configuracoes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String produto = new Gson().toJson(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request))));
		String fornecedores = new Gson().toJson(daofornecedor.listarFornecedores(sqlFornecimento.lista(id_produto(request))));
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
		new DAOFornecimento().gravar(sqlFornecimento.gravar(nomeFornecedor(request), id_produto(request), tempoEntrega(request), valor(request), id(request)));
		setarAtributos(request, response);
	}
	
	protected void incluirPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
		fornecedorIncluirPedido(request, response);
	}
	
	protected void fornecedorIncluirPedido(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {
		ModelFornecimento modelFornecedor = daofornecedor.consultarFornecedor(sqlFornecimento.consulta(id_fornecedor(request)));
		pedidoIncluirPedido(request, response, modelFornecedor);
	}
	
	protected void pedidoIncluirPedido(HttpServletRequest request, HttpServletResponse response, ModelFornecimento modelFornecedor) throws Exception {
		ModelPedidos modelPedido = new ModelPedidos();
		modelPedido.setValor(modelFornecedor.getValor());
		modelPedido.setFornecedor_pai_id(modelFornecedor);
		modelPedido.setQuantidade(quantidade(request));
		modelPedido.setDatapedido(dataPedido(request));
		modelPedido.setDataentrega(dao.plusDias(dataPedido(request), modelFornecedor.getTempoentrega()));
		modelPedido.setUsuario_pai_id(user(request));
		modelPedido.setProduto_pai_id(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request))));
		modelPedido.setNome(daoproduto.consultarProduto(sqlprodutos.consultaProduto(id_produto(request))).getNome());
		persistenciaIncluirPedido(request, response, modelPedido);
	}
	
	protected void persistenciaIncluirPedido(HttpServletRequest request, HttpServletResponse response, ModelPedidos modelPedido) throws Exception {
		daopedidos.gravar(sqlpedidos.gravarPedido(modelPedido));
		setarAtributos(request, response);
	}

	protected void confirmarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
		daoproduto.consultarProduto(sqlprodutos.consultaProduto(daopedidos.buscarPedido(sqlpedidos.buscarPedido(id_pedido(request))).getProduto_pai_id().getId()));
		daoproduto.adicionaProdutoCaixa(daopedidos.buscarPedido(sqlpedidos.buscarPedido(id_pedido(request))).getProduto_pai_id().getId(), quantidade(request));
		daopedidos.mudarStatus(sqlpedidos.atualizarStatusPedido(id_pedido(request), 2));
		setarAtributos(request, response);
	}
	
	protected void cancelarPedido(HttpServletRequest request, HttpServletResponse response) throws Exception {
		daopedidos.mudarStatus(sqlpedidos.atualizarStatusPedido(id_pedido(request), 1));
		daopedidos.excluir(sqlpedidos.gravarCancelamento(id_pedido(request)));
		setarAtributos(request, response);
	}

	protected void deletarFornecedor(HttpServletRequest request) throws Exception {
		daofornecedor.excluir(sqlFornecimento.exclui(id_fornecedor(request)));
	}
	
	protected void carregarTodosPedidos(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		String json = new Gson().toJson(daopedidos.listarPedidos(sqlpedidos.listaPedidosUsuarioId(getUserId(request), 0)));
		impressaoJSON(response, json);
	}
	
	protected void validarExclusao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ModelPedidos> resultado = daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoIdStatus(id_produto(request)));
		if(resultado.isEmpty() || resultado == null) {
			daoproduto.mudarStatus(sqlprodutos.mudancaStatus(id_produto(request)));
			setarAtributos(request, response);
		}else {
			setarAtributos(request, response);
		}
	}
	
	protected void abrirTodosFornecedores(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fornecedores = new Gson().toJson(daofornecedor.listarNomeFornecedores(sqlFornecimento.listaTodosFornecedores(id(request), id_produto(request))));
		impressaoJSON(response, fornecedores);
	}
	
	protected void verificarHaNomeFornecedor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String validadorFornecedor = daofornecedor.verificarHaNomeFornecedor(sqlFornecimento.consultaPorNome(id(request), nomeFornecedor(request)));
		impressaoJSON(response, validadorFornecedor);
	}
}