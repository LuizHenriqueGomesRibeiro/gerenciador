package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import conexao.conexao;
import model.ModelUsuarios;

public class daoPedidos {
	private Connection connection;
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelUsuarios gravarPedido(ModelUsuarios login) {
		try {
			String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login.getLogin());
			statement.setString(2, login.getSenha());
			statement.setString(3, login.getNome());
			statement.setString(4, login.getEmail());
			statement.execute();
			connection.commit();
			
			return login;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return null;
		}

	}
	
	
}
