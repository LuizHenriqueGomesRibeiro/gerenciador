package Servlet.API.Extends;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLProdutos;
import Servlet.API.APIDespache;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;
import net.sf.jasperreports.components.table.fill.DatasetCloneObjectFactory;

public class APIFornecimento extends APIDespache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	daoPedidos daopedidos = new daoPedidos();
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecedor = new daoFornecimento();
	
	public ModelPedidos parametrosIncluirPedido(HttpServletRequest request, ModelUsuarios usuario) throws NumberFormatException, Exception {
		return fornecedorIncluirPedido(request);
	}
	
	public ModelPedidos fornecedorIncluirPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		ModelFornecimento modelFornecedor = new ModelFornecimento();
		modelFornecedor.setId(Long.parseLong(request.getParameter("id_fornecedor")));
		modelFornecedor.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), super.getUserId(request)));
		modelFornecedor = daofornecedor.consultarFornecedor(modelFornecedor);
		return pedidoIncluirPedido(request, modelFornecedor);
	}
	
	public ModelPedidos pedidoIncluirPedido(HttpServletRequest request, ModelFornecimento modelFornecedor) throws Exception {
		ModelPedidos modelPedido = new ModelPedidos();
		modelPedido.setValor(modelFornecedor.getValor());
		modelPedido.setFornecedor_pai_id(modelFornecedor);
		modelPedido.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
		modelPedido.setDatapedido(request.getParameter("dataPedido"));
		modelPedido.setDataentrega(dao.plusDias(request.getParameter("dataPedido"), modelFornecedor.getTempoentrega()));
		modelPedido.setProduto_pai_id(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), super.getUserId(request)));
		modelPedido.setUsuario_pai_id(super.getUser(request));
		modelPedido.setNome(daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), super.getUserId(request)).getNome());
		return modelPedido;
	}
	
	public ModelData parametrosConfirmarPedido(HttpServletRequest request) throws Exception {
		ModelData modelData = new ModelData();
		modelData.setDatavenda(request.getParameter("data"));
		modelData.setUsuario_pai_id(super.getUser(request));
		modelData.setValortotal(daopedidos.buscarPedido(Long.parseLong(request.getParameter("id"))).getValorTotal());
		return modelData;
	}
	
	public ModelProdutos adicionaProdutoCaixa(HttpServletRequest request) {
		ModelProdutos modelProduto = new ModelProdutos();
		modelProduto.setId(Long.parseLong(request.getParameter("id_produto")));
		modelProduto.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
		return modelProduto;
	}
	
	
}
