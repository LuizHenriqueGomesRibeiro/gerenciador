package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import conexao.conexao;
import model.ModelData;
import model.ModelProdutos;
import model.ModelVendas;

public class daoEntradasRelatorio {
	private Connection connection;
	
	public daoEntradasRelatorio(){
		connection = conexao.getConnection();
	}
	
	public void alternarData(ModelData modelData) throws SQLException, ParseException {
		if (buscarData(modelData)) {
			atualizarDataEValor(modelData);
		} else {
			inserirDataEValor(modelData);
		}
	}
	
	public Boolean buscarData(ModelData dataEntrada) throws SQLException, ParseException {
		String sql = "SELECT COUNT(*) FROM dataentrada WHERE usuario_pai_id = ? AND dataentrada = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, dataEntrada.getUsuario_pai_id().getId());
		statement.setString(2, dataEntrada.getDatavenda());
		
		statement.setInt(1, dataEntrada.getUsuario_pai_id().getId());
		ResultSet resultado = statement.executeQuery();
		
		if (resultado.next() && resultado.getInt(1) > 0) {
            return true;
        } else {
            return false;
        }
	}
	
	public void inserirDataEValor(ModelData dataEntrada) throws SQLException, ParseException {
		String sql = "INSERT INTO dataentrada(dataentrada, valortotal, usuario_pai_id) VALUES(?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, dataEntrada.getDatavenda());
		statement.setLong(2, dataEntrada.getValortotal());
		statement.setLong(3, dataEntrada.getUsuario_pai_id().getId());
		
		statement.execute();
		connection.commit();
	}
	
	public void atualizarDataEValor(ModelData dataEntrada) throws SQLException, ParseException {
		String sql = "UPDATE dataentrada SET valortotal = valortotal + ? WHERE usuario_pai_id = ? AND dataentrada = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, dataEntrada.getValortotal());
		statement.setLong(2, dataEntrada.getUsuario_pai_id().getId());
		statement.setString(3, dataEntrada.getDatavenda());
		
		statement.executeUpdate();
		connection.commit();
	}
	
	public List<ModelData> listarDatasEntradas(int id) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM dataentrada WHERE usuario_pai_id = " + id + " ORDER BY dataentrada ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelData dataVendas = new ModelData();
			
			String data = resultado.getString("dataentrada");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			dataVendas.setDatavenda(data);

			dataVendas.setValortotal(resultado.getInt("valortotal"));
			
			retorno.add(dataVendas);
		}
		
		return retorno;
	}
	
	public List<ModelData> listarDatasEntradas(int id, String dataInicial, String dataFinal) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM dataentrada WHERE usuario_pai_id = ? AND dataentrada >= ? AND dataentrada <= ? ORDER BY dataentrada ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(2, dataSql);
		statement.setDate(3, dataSql2);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelData dataVendas = new ModelData();
			
			String data = resultado.getString("dataEntrada");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			dataVendas.setDatavenda(data);

			dataVendas.setValortotal(resultado.getInt("valortotal"));
			
			retorno.add(dataVendas);
		}
		
		return retorno;
	}
	
	public static String transformarFormatoData(String dataString, String formatoOriginal, String novoFormato) {
		try {
			SimpleDateFormat formatoOriginalData = new SimpleDateFormat(formatoOriginal);
			SimpleDateFormat formatoNovoData = new SimpleDateFormat(novoFormato);

			java.util.Date data = formatoOriginalData.parse(dataString);

			String dataFormatada = formatoNovoData.format(data);

			return dataFormatada;
		} catch (ParseException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
}
