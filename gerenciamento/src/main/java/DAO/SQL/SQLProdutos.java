package DAO.SQL;

public class SQLProdutos {
	public String listaProdutosLIMIT10(int id) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " LIMIT 10";
		return sql;
	}
	
	public String listaProdutosOFFSET(int id, int offset) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " ORDER BY quantidade OFFSET " + offset + " LIMIT 10";
		return sql;
	}
}
