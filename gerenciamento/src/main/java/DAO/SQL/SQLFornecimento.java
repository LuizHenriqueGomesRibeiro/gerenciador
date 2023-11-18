package DAO.SQL;

public class SQLFornecimento {
	
	public String gravar(String nome, Long produtos_pai_id, int tempoentrega, int valor, int usuario_pai_id) {
		String sql = "INSERT INTO fornecimento(nome, produtos_pai_id, tempoentrega, valor, usuario_pai_id) VALUES ('" + nome + "'," + produtos_pai_id + "," 
			+ tempoentrega + "," + valor + "," + usuario_pai_id + ");";
		return sql;
	}
	
	public String lista(Long id) {
		String sql = "SELECT * FROM fornecimento WHERE produtos_pai_id = " + id;
		return sql;
	}
	
	public String listaNomes(Long id) {
		String sql = "SELECT nome FROM fornecimento WHERE produtos_pai_id = " + id;
		return sql;
	}
	
	public String listaTodosFornecedores(int id, Long produtos_pai_id) {
		String sql = "SELECT * FROM fornecimento WHERE usuario_pai_id = " + id + " AND NOT produtos_pai_id = " + produtos_pai_id 
			+ " AND nome NOT IN (SELECT nome FROM fornecimento WHERE produtos_pai_id = " + produtos_pai_id + ")";
		return sql;
	}
	
	public String consulta(Long id) {
		String sql = "SELECT* FROM fornecimento WHERE id = " + id;
		return sql;
	}
	
	public String consultaPorNome(int id, String nome) {
		String sql = "SELECT * FROM fornecimento WHERE usuario_pai_id = " + id + " AND nome = '" + nome + "'";
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
