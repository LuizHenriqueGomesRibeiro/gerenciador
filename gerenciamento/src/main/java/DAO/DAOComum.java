package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import DAO.SQL.SQLProdutos;
import conexao.conexao;
import model.ModelProdutos;

public class DAOComum extends DAOEntrada{
	private Connection connection;
	SQLProdutos sqlproduto = new SQLProdutos();
	
	public void excluir(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
	
	public void gravar(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public int somaQuantidade(String sql) throws SQLException {
		connection = conexao.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}
	
	public int somaValores(String sql) throws SQLException {
		connection = conexao.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		return resultado.getInt("soma");
	}
}
