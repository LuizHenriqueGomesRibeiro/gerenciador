package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.conexao;
import model.ModelUsuarios;

public class daoLogin {
	private Connection connection;
	
	public daoLogin() {
		connection = conexao.getConnection();
	}
	
	public boolean validarAutenticacao(ModelUsuarios usuario) throws SQLException {
		String sql="SELECT*FROM usuarios WHERE login = ? AND senha = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, usuario.getLogin());
		statement.setString(2, usuario.getSenha());
		ResultSet resultado = statement.executeQuery();
		
		if(resultado.next()) {
			return true;
		}
		
		return false;
	}
	
	public ModelUsuarios consultaLogin(ModelUsuarios usuario) {
		
		try {
			String sql = "SELECT*FROM usuarios WHERE login = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				usuario.setId(resultado.getInt("id"));
				usuario.setLogin(resultado.getString("login"));
				usuario.setSenha(resultado.getString("senha"));
			}
			
			return usuario;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
