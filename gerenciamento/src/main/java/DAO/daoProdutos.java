package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import conexao.conexao;
import model.ModelUsuarios;
import model.ModelProdutos;

public class daoProdutos {
	
	private Connection connection;

	public daoProdutos() {
		connection = conexao.getConnection();
	}
	
	public void gravarProduto(ModelProdutos modelProduto) {
		try {
			String sql = "INSERT INTO produtos(preco, quantidade, nome, usuario_pai_id) VALUES (?, ?, ?, ?);";
			
			ModelUsuarios usuario_pai_id = modelProduto.getUsuario_pai_id();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, modelProduto.getPreco());
			statement.setInt(2, modelProduto.getQuantidade());
			statement.setString(3, modelProduto.getNome());
			statement.setInt(4, usuario_pai_id.getId());
			
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}
}
