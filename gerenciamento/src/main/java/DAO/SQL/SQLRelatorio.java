package DAO.SQL;

import java.sql.PreparedStatement;

import model.ModelData;

public class SQLRelatorio {
	public String listaDatasVendas(int id) {
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = " + id + " ORDER BY datavenda ASC";
		return sql;
	}
	
	public String listaDatasVendas(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = " + id + " AND datavenda >= '" + dataInicial + "' AND datavenda <= '" + dataFinal 
				+ "' ORDER BY datavenda ASC";
		return sql;
	}
	
	public String listaDatasEntradas(int id) {
		String sql = "SELECT * FROM dataentrada WHERE usuario_pai_id = " + id + " ORDER BY dataentrada ASC";
		return sql;
	}
	
	public String listaDatasEntradas(int id, String dataInicial, String dataFinal) {
		String sql = "SELECT * FROM dataentrada WHERE usuario_pai_id = " + id + " AND dataentrada >= '" + dataInicial + "' AND dataentrada <= '" 
				+ dataFinal + "' ORDER BY dataentrada ASC";
		return sql;
	}
	
	public String insercaoDataEValor(ModelData dataEntrada) {
		String sql = "INSERT INTO dataentrada(dataentrada, valortotal, usuario_pai_id) VALUES(" + dataEntrada.getDatavenda() + ", " 
				+ dataEntrada.getValortotal() + ", " + dataEntrada.getUsuario_pai_id().getId() + ");";
		return sql;
	}
	
	public String atualizacaoDataEValor(ModelData dataEntrada) {
		String sql = "UPDATE dataentrada SET valortotal = valortotal + " + dataEntrada.getValortotal() + " WHERE usuario_pai_id = " + dataEntrada.getUsuario_pai_id().getId() 
				+ " AND dataentrada = " + dataEntrada.getDatavenda();
		return sql;
	}
	
	public String buscaDataEntrada(ModelData dataEntrada) {
		String sql = "SELECT COUNT(*) FROM dataentrada WHERE usuario_pai_id = " + dataEntrada.getUsuario_pai_id().getId() 
				+ " AND dataentrada = " + dataEntrada.getDatavenda();
		return sql;
	}
	
	public String buscaDataVenda(ModelData dataVenda) {
		String sql = "SELECT COUNT(*) FROM datavenda WHERE usuario_pai_id = " + dataVenda.getUsuario_pai_id().getId() + " AND datavenda = '" + dataVenda.getDatavenda() + "'";
		return sql;
	}
	
	public String insercaoDataEValorVenda(ModelData dataVenda) {
		String sql = "INSERT INTO datavenda(datavenda, valortotal, usuario_pai_id) VALUES('" + dataVenda.getDatavenda() + "', " + 
				dataVenda.getValortotal() + ", " + dataVenda.getUsuario_pai_id().getId() + ");";
		return sql;
	}
	
	public String atualizacaoDataEValorVenda(ModelData dataVenda) {
		String sql = "UPDATE datavenda SET valortotal = valortotal + " + dataVenda.getValortotal() 
			+ " WHERE usuario_pai_id = " + dataVenda.getUsuario_pai_id().getId() + " AND datavenda = '" + dataVenda.getDatavenda() + "'";
		return sql;
	}
}
