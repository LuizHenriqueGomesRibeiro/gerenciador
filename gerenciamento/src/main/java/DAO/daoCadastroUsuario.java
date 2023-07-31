package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.conexao;
import model.ModelUsuarios;

public class daoCadastroUsuario {
	
	private Connection connection;
	
	public daoCadastroUsuario(){
		connection = conexao.getConnection();
	}
	
	public ModelUsuarios gravarUsuario(ModelUsuarios login) {
		try {
			String sql = "INSERT INTO usuarios(login, senha, nome, email) VALUES (?, ?, ?, ?);";
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
	
	public boolean validarEmail(String email) {

		try {
			String sql = "SELECT count(1) > 0 AS existe FROM usuarios WHERE email = ?;";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultado = statement.executeQuery();

			resultado.next();

			return resultado.getBoolean("existe");

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean validarLogin(String login) {

		try {
			String sql = "SELECT count(1) > 0 AS existe FROM usuarios WHERE login = ?;";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			ResultSet resultado = statement.executeQuery();

			resultado.next();

			return resultado.getBoolean("existe");

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
