package DAO.SQL;

import model.ModelUsuarios;

public class SQLLogin {
	
	public String validarUsuarioPorLoginESenha(ModelUsuarios usuario) {
		String sql = "SELECT * FROM usuarios WHERE login = '" + usuario.getLogin() + "' AND senha = '" + usuario.getSenha() + "'";
		return sql;
	}
	
	public String validarUsuarioPorLogin(String login) {
		String sql = "SELECT * FROM usuarios WHERE login = '" + login + "'";
		return sql;
	}
	
	public String atualizacaoLogin(ModelUsuarios usuario) {
		String sql = "UPDATE usuarios SET login = '" + usuario.getLogin() + "' WHERE id = " + usuario.getId();
		return sql;
	}
}
