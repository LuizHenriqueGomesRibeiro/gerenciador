package DAO.SQL;

import model.ModelProdutos;

public class SQLProdutos {
	public String listaProdutosLIMIT10(int id) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " LIMIT 10";
		return sql;
	}
	
	public String listaProdutosOFFSET(int id, int offset) {
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " ORDER BY quantidade OFFSET " + offset + " LIMIT 10";
		return sql;
	}
	
	public String gravaProduto(String nome, int id) {
		String sql = "INSERT INTO produtos(nome, usuario_pai_id, quantidade) VALUES ('" + nome + "', " + id + ", " + 0 + ");";
		return sql;
	}
	
	public String atualizaProduto(ModelProdutos produto) {
		String sql = "UPDATE produtos SET preco = " + produto.getPreco() + ", quantidade = " + produto.getQuantidade()  + ", nome = " 
				+ produto.getNome() + ", valortotal = " + produto.getValorTotal() + " WHERE id = " + produto.getId() + "";
		return sql;
	}
}
