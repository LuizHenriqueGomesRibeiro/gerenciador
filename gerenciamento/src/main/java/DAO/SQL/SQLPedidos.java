package DAO.SQL;

public class SQLPedidos {
	public String listaPedidosValorData(int id, int status) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaPedidosValorDataTempo(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
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
	
	public String listaPedidosProdutoId(int id) {
		String sql = "SELECT * FROM pedidos WHERE produtos_pai_id = " + id;
		return sql;
	}

	public String listaPedidosProdutoId(int id, String status) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND produtos_pai_id = " + id;
		return sql;
	}
	
	public String listaPedidosUsuarioId(int id, int status) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id;
		return sql;
	}

	public String listaPedidosUsuarioIdTempo(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id + " AND dataentrega >= " + dataInicial + " AND dataentrega <= " + dataFinal;
		return sql;
	}
	
	public String procuraPedido(Long id) {
		String sql = "SELECT * FROM pedidos WHERE id = " + id;
		return sql;
	}
}
