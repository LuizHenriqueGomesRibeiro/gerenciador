package Servlet.SQL;

public class SQL {
	
	
	public String listaProdutosLIMIT10(int id) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " LIMIT 10";
		return sql;
	}
	
	public String listaVendas(int id) {
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + id;
		return sql;
	}
	
	public String listaVendasTempo(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String listaVendasValorData(int id) {
		String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = " + id + " ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaVendasValorDataTempo(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "' ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaEntradas(int id, int status) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id;
		return sql;
	}
	
	public String listaEntradasTempo(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String listaEntradasValorData(int id, int status) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaEntradasValorDataTempo(int id, int status, String dataInicial, String dataFinal) {
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + id + " AND status = " + status + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String somaValoresVendas(int id) {
		String sql = "SELECT SUM(valortotal) AS soma FROM vendas WHERE usuario_pai_id = " + id;
		return sql;
	}
	
	public String somaQuantidadeVendas(int id) {
		String sql = "SELECT SUM(quantidade) AS soma FROM vendas WHERE usuario_pai_id = " + id;
		return sql;
	}
	
	public String somaValoresVendasTempo(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT SUM(valortotal) AS soma FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String somaQuantidadeVendasTempo(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT SUM(quantidade) AS soma FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
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
	
}
