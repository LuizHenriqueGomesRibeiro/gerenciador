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
import model.ModelDataVenda;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendasRelatorio {
	private Connection connection;
	
	public daoVendasRelatorio(){
		connection = conexao.getConnection();
	}
	
	public Boolean buscarData(ModelDataVenda dataVenda) throws SQLException, ParseException {
		String sql = "SELECT COUNT(*) FROM datavenda WHERE usuario_pai_id = ? AND datavenda = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		String dataEmFormatacao = transformarFormatoData(dataVenda.getDatavenda(), "dd/MM/yyyy", "yyyy-MM-dd");
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataEmFormatacao);
		Date dataSql = new Date(dataUtil.getTime());
		
		statement.setLong(1, dataVenda.getUsuario_pai_id().getId());
		statement.setDate(2, dataSql);
		
		statement.setInt(1, dataVenda.getUsuario_pai_id().getId());
		ResultSet resultado = statement.executeQuery();
		
		if (resultado.next() && resultado.getInt(1) > 0) {
			System.out.println("Há registros");
            return true;
        } else {
        	System.out.println("Não há registros");
            return false;
        }
	}
	
	public void inserirDataEValor(ModelDataVenda dataVenda) throws SQLException, ParseException {
		String sql = "INSERT INTO datavenda(datavenda, valortotal, usuario_pai_id) VALUES(?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		String dataEmFormatacao = transformarFormatoData(dataVenda.getDatavenda(), "dd/MM/yyyy", "yyyy-MM-dd");
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataEmFormatacao);
		Date dataSql = new Date(dataUtil.getTime());
		
		statement.setDate(1, dataSql);
		statement.setInt(2, dataVenda.getValortotal());
		statement.setLong(3, dataVenda.getUsuario_pai_id().getId());
		
		statement.execute();
		connection.commit();
	}
	
	public void atualizarDataEValor(ModelDataVenda dataVenda) throws SQLException, ParseException {
		String sql = "UPDATE datavenda SET valortotal = valortotal + ? WHERE usuario_pai_id = ? AND datavenda = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		String dataEmFormatacao = transformarFormatoData(dataVenda.getDatavenda(), "dd/MM/yyyy", "yyyy-MM-dd");
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataEmFormatacao);
		Date dataSql = new Date(dataUtil.getTime());
		
		statement.setInt(1, dataVenda.getValortotal());
		statement.setLong(2, dataVenda.getUsuario_pai_id().getId());
		statement.setDate(3, dataSql);
		
		statement.executeUpdate();
		connection.commit();
	}
	
	public List<ModelDataVenda> listarDatasVendas(ModelDataVenda dataVenda) throws SQLException, ParseException{
		List<ModelDataVenda> retorno = new ArrayList<ModelDataVenda>();
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, dataVenda.getUsuario_pai_id().getId());
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelDataVenda dataVendas = new ModelDataVenda();
			
			String data = resultado.getString("datavenda");
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
