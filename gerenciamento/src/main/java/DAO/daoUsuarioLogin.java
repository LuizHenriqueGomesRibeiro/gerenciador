package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.conexao;
import model.Login;

public class daoUsuarioLogin {
	
	private Connection connection; 
	
	public daoUsuarioLogin(){
		connection = conexao.getConnection();
	}
	
	public boolean validarLogin(Login login) {
		try {
			String sql="SELECT*fROM login WHERE login = ? AND senha = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login.getLogin());
			statement.setString(2, login.getSenha());
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next()) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
