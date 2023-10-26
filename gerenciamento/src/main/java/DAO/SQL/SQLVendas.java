package DAO.SQL;

import model.ModelVendas;

public class SQLVendas {
	public String gravaVenda(ModelVendas venda, int usuario_pai_id) {
		String sql = "INSERT INTO vendas(produtos_pai_id, dataentrega, valortotal, quantidade, nome, usuario_pai_id) VALUES (" + venda.getProduto_pai().getId()
				+ ", '" + venda.getDataentrega() + "', '" + venda.getValortotal()*venda.getQuantidade() + "', " + venda.getQuantidade() 
				+ ", " + venda.getProduto_pai().getNome() + ", " + usuario_pai_id + ");";
		return sql;
	}
	
	public String listaVendas(int id) {
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + id;
		return sql;
	}
	
	public String listaVendas(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String listaVendasValorData(int id) {
		String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = " + id + " ORDER BY dataentrega ASC";
		return sql;
	}
	
	public String listaVendasValorData(int id, String dataInicial, String dataFinal) {
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
	
	public String somaValoresVendas(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT SUM(valortotal) AS soma FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
	
	public String somaQuantidadeVendas(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT SUM(quantidade) AS soma FROM vendas WHERE usuario_pai_id = " + id + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
		return sql;
	}
}
