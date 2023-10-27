package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import DAO.SQL.SQLRelatorio;
import conexao.conexao;
import model.ModelData;

public class DAORelatorio extends DAOComum {
	private Connection connection;
	SQLRelatorio sqlrelatorio = new SQLRelatorio();
	
	public DAORelatorio(){
		connection = conexao.getConnection();
	}
	
	public void alternarDataEValorVendas(ModelData dataVenda) throws SQLException, ParseException {
		if (buscarData(sqlrelatorio.buscaDataVenda(dataVenda))) {
			atualizarDataEValor(sqlrelatorio.atualizacaoDataEValorVenda(dataVenda));
		} else {
			inserirDataEValor(sqlrelatorio.insercaoDataEValorVenda(dataVenda));
		}
	}
	
	public void alternarDataEValorEntradas(ModelData modelData) throws SQLException, ParseException {
		if (buscarData(sqlrelatorio.buscaDataEntrada(modelData))) {
			atualizarDataEValor(sqlrelatorio.atualizacaoDataEValor(modelData));
		} else {
			inserirDataEValor(sqlrelatorio.insercaoDataEValor(modelData));
		}
	}
	
	public boolean buscarData(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next() && resultado.getInt(1) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void inserirDataEValor(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public void atualizarDataEValor(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
	
	public List<ModelData> listarDatasEntradas(String sql) throws SQLException, ParseException{
		return resultadoListarDatasEntradas(prepararListagem(sql));
	}
	
	public List<ModelData> listarDatasVendas(String sql) throws SQLException, ParseException{
		return retultadoListarDatasVendas(prepararListagem(sql));
	}
	
	public ResultSet prepararListagem(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultado;
	}
	
	public List<ModelData> resultadoListarDatasEntradas(ResultSet resultado) throws SQLException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		while(resultado.next()) {
			ModelData modelData = new ModelData();
			modelData.setDatavenda(dataentrada(resultado));
			modelData.setValortotal(valortotal(resultado));
			retorno.add(modelData);
		}
		return retorno;
	}
	
	public List<ModelData> retultadoListarDatasVendas(ResultSet resultado) throws SQLException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		while(resultado.next()) {
			ModelData modelData = new ModelData();
			modelData.setDatavenda(datavenda(resultado));
			modelData.setValortotal(valortotal(resultado));
			retorno.add(modelData);
		}
		return retorno;
	}
}
