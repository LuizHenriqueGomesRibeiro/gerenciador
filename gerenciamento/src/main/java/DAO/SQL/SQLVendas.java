package DAO.SQL;

public class SQLVendas {
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
}
