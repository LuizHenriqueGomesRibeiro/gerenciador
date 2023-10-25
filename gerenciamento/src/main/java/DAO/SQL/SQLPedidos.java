package DAO.SQL;

import java.time.LocalDate;

import DAO.DAOFerramentas;
import model.ModelPedidos;

public class SQLPedidos {
	DAOFerramentas dao = new DAOFerramentas();
	
	public String gravarPedido(ModelPedidos pedido) {
		String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega, produtos_pai_id, usuario_pai_id, status, nome)" + 
				" VALUES ('" + 
				dao.converterDatas(pedido.getDatapedido()) + "', " + 
				pedido.getQuantidade() + ", " + 
				pedido.getValor() + ", " + 
				pedido.getQuantidade()*pedido.getFornecedor_pai_id().getValor() + ", " + 
				pedido.getFornecedor_pai_id().getId() + ", '" + 
				dao.converterDatas(pedido.getDataentrega()) + "', " + 
				pedido.getProduto_pai_id().getId() + ", " + 
				pedido.getUsuario_pai_id().getId() + ", " + 
				0 + ", '" + 
				pedido.getNome() + "');";
		return sql;
	}
	
	public String buscarPedido(Long id) {
		String sql = "SELECT * FROM pedidos WHERE id = " + id;
		return sql;
	}
	
	public String gravarCancelamento(Long id) {
		String sql = "INSERT INTO cancelamentos(datacancelamento, pedido_pai_id) VALUES ('" + LocalDate.now() + "', " + id + ");";
		return sql;
	}
	
	public String listaPedidosValorData(int id, int status) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaPedidosValorData(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " AND dataentrega >= '" + dataInicial 
				+ "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String somaQuantidadePedido(int id, int status) {
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status;
		return sql;
	}
	
	public String somaValoresPedido(int id, int status) {
		String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status;
		return sql;
	}
	
	public String somaValoresPedidoProdutoId(Long id, int status) {
		String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE produtos_pai_id = " + id + " AND status = " + status;
		return sql;
	}
	
	public String somaQuantidadePedidoProdutId(Long id, int status) {
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE produtos_pai_id = " + id + " AND status = " + status;
		return sql;
	}
	
	public String listaPedidosProdutoId(Long id) {
		String sql = "SELECT * FROM pedidos WHERE produtos_pai_id = " + id;
		return sql;
	}

	public String listaPedidosProdutoId(Long id, int status) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND produtos_pai_id = " + id;
		return sql;
	}
	
	public String listaPedidosUsuarioId(int id) {
		String sql = "SELECT * FROM pedidos WHERE usuario_pai_id = " + id;
		return sql;
	}
	
	public String listaPedidosUsuarioId(int id, int status) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id;
		return sql;
	}

	public String listaPedidosUsuarioId(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id + " AND dataentrega >= " + dataInicial + " AND dataentrega <= " + dataFinal;
		return sql;
	}
	
	public String procuraPedido(Long id) {
		String sql = "SELECT * FROM pedidos WHERE id = " + id;
		return sql;
	}
}
