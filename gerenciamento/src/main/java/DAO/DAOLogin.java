package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.conexao;
import model.ModelUsuarios;

public class DAOLogin extends DAOComum {
	private Connection connection;
	
	public DAOLogin() {
		connection = conexao.getConnection();
	}
	
	public void atualizarLogin(String sql) throws Exception{
		System.out.println(sql);
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
	
	public boolean validarAutenticacao(String  sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		if(resultado.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	public ModelUsuarios consultaLogin(ModelUsuarios usuario) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE login = " + usuario.getLogin();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {
			usuario.setId(resultado.getInt("id"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
		}
		return usuario;
	}

	public ModelUsuarios consultaLoginString(String login) throws Exception {
	
		String sql = "SELECT*FROM usuarios WHERE login = ?";
		
		ModelUsuarios usuario = new ModelUsuarios();
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			usuario.setId(resultado.getInt("id"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
		}
		return usuario;
	}

	public ModelUsuarios consultaUsuarioLogadoId(int id) throws Exception {
		String sql = "SELECT*FROM usuarios WHERE id = ?";
		
		ModelUsuarios modelUsuario = new ModelUsuarios();

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelUsuario.setId(resultado.getInt("id"));
			modelUsuario.setEmail(resultado.getString("email"));
			modelUsuario.setLogin(resultado.getString("login"));
			modelUsuario.setNome(resultado.getString("nome"));
		}
		return modelUsuario;
	
	}
}
