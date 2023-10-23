package Servlet.API.Extends;

import java.sql.SQLException;
import java.text.ParseException;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.APIDespache;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

public class APIFornecimento extends APIDespache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	daoEntradasRelatorio daoEntradaRelatorio = new daoEntradasRelatorio();
	
	public void parametrosCadastrarFornecedor(HttpServletRequest request) throws NumberFormatException, SQLException {
		ModelProdutos modelProdutos = new ModelProdutos();
		modelProdutos.setId(id_produto(request));
		new daoFornecimento().gravarNovoFornecedor(sqlFornecimento.gravar(nomeFornecedor(request), modelProdutos, tempoEntrega(request), valor(request)));
	}
	
	public ModelPedidos parametrosIncluirPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		return fornecedorIncluirPedido(request);
	}
	
	public ModelPedidos fornecedorIncluirPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		ModelFornecimento modelFornecedor = new ModelFornecimento();
		modelFornecedor.setId(id_fornecedor(request));
		modelFornecedor.setProduto_pai_id(daoproduto.consultaProduto(id_produto(request), id(request)));
		modelFornecedor = daofornecedor.consultarFornecedor(modelFornecedor);
		return pedidoIncluirPedido(request, modelFornecedor);
	}
	
	public ModelPedidos pedidoIncluirPedido(HttpServletRequest request, ModelFornecimento modelFornecedor) throws Exception {
		ModelPedidos modelPedido = new ModelPedidos();
		modelPedido.setValor(modelFornecedor.getValor());
		modelPedido.setFornecedor_pai_id(modelFornecedor);
		modelPedido.setQuantidade(quantidade(request));
		modelPedido.setDatapedido(dataPedido(request));
		modelPedido.setDataentrega(dao.plusDias(dataPedido(request), modelFornecedor.getTempoentrega()));
		modelPedido.setUsuario_pai_id(user(request));
		modelPedido.setProduto_pai_id(daoproduto.consultaProduto(id_produto(request), id(request)));
		modelPedido.setNome(daoproduto.consultaProduto(id_produto(request), id(request)).getNome());
		return persistenciaIncluirPedido(request, modelPedido);
	}
	
	public ModelPedidos persistenciaIncluirPedido(HttpServletRequest request, ModelPedidos modelPedido) throws SQLException {
		daopedidos.gravarPedido(sqlpedidos.gravarPedido(modelPedido));
		return modelPedido;
	}
	
	public void parametrosConfirmarPedido(HttpServletRequest request) throws ParseException, Exception {
		daoproduto.consultaProduto(id_produto(request), id(request));
		daoproduto.adicionaProdutoCaixa(id_produto(request), quantidade(request));
		daopedidos.mudarStatus(id_pedido(request), 2);
	}
	
	public ModelProdutos adicionaProdutoCaixa(HttpServletRequest request) {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setId(id_produto(request));
		modelProduto.setQuantidade(quantidade(request));
		return modelProduto;
	}
	
	public void parametrosCancelarPedido(HttpServletRequest request) throws SQLException {
		daopedidos.mudarStatus(id_pedido(request), 1);
	}
	
	public void parametrosDeletarFornecedor(HttpServletRequest request) throws NumberFormatException, SQLException {
		daofornecedor.excluirFornecedor(id_fornecedor(request));
	}
}