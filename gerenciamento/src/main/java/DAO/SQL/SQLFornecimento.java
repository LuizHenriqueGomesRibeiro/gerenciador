package DAO.SQL;

import model.ModelProdutos;

public class SQLFornecimento {
	
	public String gravar(String nome, ModelProdutos produtos_pai_id, int tempoentrega, int valor) {
		String sql = "INSERT INTO fornecimento(nome, produtos_pai_id, tempoentrega, valor) VALUES (" + nome + "," + produtos_pai_id + "," + tempoentrega + "," + valor + ");";
		return sql;
	}
	
	public String lista(Long id) {
		String sql = "SELECT * FROM fornecimento WHERE produtos_pai_id = " + id;
		return sql;
	}
	
	public String consulta(Long id, Long produtos_pai_id) {
		String sql = "SELECT* FROM fornecimento WHERE id = " + id + " AND produtos_pai_id = " + produtos_pai_id;
		return sql;
	}
	
	public String exclui(Long id) {
		String sql = "DELETE FROM fornecimento WHERE id = " + id;
		return sql;
	}
	
	public String media(Long id) {
		String sql = "SELECT AVG(valor) AS media FROM fornecimento WHERE produtos_pai_id = " + id;
		return sql;
	}
}
