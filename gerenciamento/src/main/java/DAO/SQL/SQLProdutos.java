package DAO.SQL;

import model.ModelProdutos;

public class SQLProdutos {
	public String listaProdutosLIMIT10(int id) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " AND status = " + 1 + " LIMIT 10";
		return sql;
	}
	
	public String listaProdutosOFFSET(int id, int offset) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " ORDER BY quantidade OFFSET " + offset + " LIMIT 10";
		return sql;
	}
	
	public String gravaProduto(String nome, int id) {
		String sql = "INSERT INTO produtos(nome, usuario_pai_id, quantidade, status) VALUES ('" + nome + "', " + id + ", " + 0 + "," + 1 + ");";
		return sql;
	}
	
	public String atualizaProduto(ModelProdutos produto) {
		String sql = "UPDATE produtos SET preco = " + produto.getPreco() + ", quantidade = " + produto.getQuantidade()  + ", nome = " 
				+ produto.getNome() + ", valortotal = " + produto.getValorTotal() + " WHERE id = " + produto.getId() + "";
		return sql;
	}
	
	public String consultaProduto(Long id_produto) {
		String sql = "SELECT*FROM produtos WHERE id = " + id_produto;
		return sql;
	}
	
	public String mudancaStatus(Long id_produto) {
		String sql = "UPDATE produtos SET status = " + 0 + " WHERE id = " + id_produto;
		return sql;
	}
}
