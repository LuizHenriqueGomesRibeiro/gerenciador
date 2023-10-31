package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import DAO.SQL.SQLProdutos;
import model.ModelProdutos;

public class DAOEntrada extends DAOFerramentas{
	SQLProdutos sqlproduto = new SQLProdutos();
	
	public Long id(ResultSet resultado) throws SQLException {
		return resultado.getLong("id");
	}
	
	public String nome(ResultSet resultado) throws SQLException {
		return resultado.getString("nome");
	}
	
	public Long tempoentrega(ResultSet resultado) throws SQLException {
		return resultado.getLong("tempoentrega");
	}
	
	public int valor(ResultSet resultado) throws SQLException {
		return resultado.getInt("valor");
	}
	
	public String dataentrega(ResultSet resultado) throws SQLException, ParseException {
		return converterDatas(resultado.getString("dataentrega"));
	}

	public String datapedido(ResultSet resultado) throws SQLException, ParseException {
		return converterDatas(resultado.getString("datapedido"));
	}
	
	public int usuario_pai_id(ResultSet resultado) throws SQLException {
		return resultado.getInt("usuario_pai_id");
	}
	
	public Long produtos_pai_Id(ResultSet resultado) throws SQLException {
		return resultado.getLong("produtos_pai_Id");
	}
	
	public int status(ResultSet resultado) throws SQLException {
		return resultado.getInt("status");
	}
	
	public int quantidade(ResultSet resultado) throws SQLException {
		return resultado.getInt("quantidade");
	}
	
	public int valortotal(ResultSet resultado) throws SQLException {
		return resultado.getInt("valortotal");
	}
	
	public int preco(ResultSet resultado) throws SQLException {
		return resultado.getInt("preco");
	}
	
	public String dataentrada(ResultSet resultado) throws SQLException, ParseException {
		return converterDatas(resultado.getString("dataentrada"));
	}
	
	public String datavenda(ResultSet resultado) throws SQLException, ParseException {
		return converterDatas(resultado.getString("datavenda"));
	}
	
	public String max(ResultSet resultado) throws SQLException, ParseException {
		return resultado.getString("max");
	}
}
