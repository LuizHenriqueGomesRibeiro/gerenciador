package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import conexao.conexao;
import model.ModelProdutos;

public class daoFornecimento {

	private Connection connection;

	public daoFornecimento(){
		connection = conexao.getConnection();
	}
	
	public void gravarNovoFornecedor(String nome, ModelProdutos produtos_pai_id) {
		try {
			String sql = "INSERT INTO fornecimento(nome, produtos_pai_id) VALUES (?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setLong(2, produtos_pai_id.getId());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
