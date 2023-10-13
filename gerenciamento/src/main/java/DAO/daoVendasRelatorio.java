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

import conexao.conexao;
import model.ModelData;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendasRelatorio {
	private Connection connection;
	
	public daoVendasRelatorio(){
		connection = conexao.getConnection();
	}
	
	public Boolean buscarData(ModelData dataVenda) throws SQLException, ParseException {
		String sql = "SELECT COUNT(*) FROM datavenda WHERE usuario_pai_id = " + dataVenda.getUsuario_pai_id().getId() + " AND datavenda = '" + dataVenda.getDatavenda() + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		if (resultado.next() && resultado.getInt(1) > 0) {
            return true;
        } else {
            return false;
        }
	}
	
	public void inserirDataEValor(ModelData dataVenda) throws SQLException, ParseException {
		String sql = "INSERT INTO datavenda(datavenda, valortotal, usuario_pai_id) VALUES('" + dataVenda.getDatavenda() + "', ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, dataVenda.getValortotal());
		statement.setLong(2, dataVenda.getUsuario_pai_id().getId());
		
		statement.execute();
		connection.commit();
	}
	
	public void atualizarDataEValor(ModelData dataVenda) throws SQLException, ParseException {
		String sql = "UPDATE datavenda SET valortotal = valortotal + ? WHERE usuario_pai_id = ? AND datavenda = '" + dataVenda.getDatavenda() + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, dataVenda.getValortotal());
		statement.setLong(2, dataVenda.getUsuario_pai_id().getId());
		
		statement.executeUpdate();
		connection.commit();
	}
	
	public List<ModelData> listarDatasVendas(ModelData dataVenda) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = ? ORDER BY datavenda ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, dataVenda.getUsuario_pai_id().getId());
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelData dataVendas = new ModelData();
			
			String data = resultado.getString("datavenda");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			dataVendas.setDatavenda(data);

			dataVendas.setValortotal(resultado.getLong("valortotal"));
			
			retorno.add(dataVendas);
		}
		
		return retorno;
	}
	
	public List<ModelData> listarDatasVendas(ModelData dataVenda, String dataInicial, String dataFinal) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = ? AND datavenda >= ? AND datavenda <= ? ORDER BY datavenda ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, dataVenda.getUsuario_pai_id().getId());
		
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
			
			String data = resultado.getString("datavenda");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			dataVendas.setDatavenda(data);

			dataVendas.setValortotal(resultado.getLong("valortotal"));
			
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
