package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.conexao;
import model.Login;

public class daoLogin {
	private Connection connection;
	
	public daoLogin() {
		connection = conexao.getConnection();
	}
	
	public boolean validarAutenticacao(Login login) throws SQLException {
		String sql="SELECT*FROM login WHERE login = ? AND senha = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login.getLogin());
		statement.setString(2, login.getSenha());
		ResultSet resultado = statement.executeQuery();
		
		if(resultado.next()) {
			return true;
		}
		
		return false;
	}
	
	public Login consultaLogin(Login login) {
		
		try {
			String sql = "SELECT*FROM login WHERE login = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login.getLogin());
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				login.setId(resultado.getLong("id"));
				login.setLogin(resultado.getString("login"));
				login.setSenha(resultado.getString("senha"));
			}
			
			return login;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
