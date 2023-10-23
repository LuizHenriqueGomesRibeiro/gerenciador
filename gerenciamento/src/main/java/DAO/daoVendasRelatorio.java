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
	DaoGenerico dao = new DaoGenerico();
	private Connection connection;
	
	public daoVendasRelatorio(){
		connection = conexao.getConnection();
	}
	
	public void alternarDataEValor(ModelData dataVenda) throws SQLException, ParseException {
		boolean booleana = buscarData(dataVenda);
		if (booleana) {
			atualizarDataEValor(dataVenda);
		} else {
			inserirDataEValor(dataVenda);
		}
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
	
	public List<ModelData> listarDatasVendas(int id) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = ? ORDER BY datavenda ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelData dataVendas = new ModelData();
			dataVendas.setDatavenda(dao.converterDatas(resultado.getString("datavenda")));
			dataVendas.setValortotal(resultado.getInt("valortotal"));
			retorno.add(dataVendas);
		}
		
		return retorno;
	}
	
	public List<ModelData> listarDatasVendas(int id, String dataInicial, String dataFinal) throws SQLException, ParseException{
		List<ModelData> retorno = new ArrayList<ModelData>();
		String sql = "SELECT * FROM datavenda WHERE usuario_pai_id = " + id + " AND datavenda >= '" + dataInicial + "' AND datavenda <= '" + dataFinal 
				+ "' ORDER BY datavenda ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelData dataVendas = new ModelData();
			dataVendas.setDatavenda(dao.converterDatas(resultado.getString("datavenda")));
			dataVendas.setValortotal(resultado.getInt("valortotal"));
			retorno.add(dataVendas);
		}
		return retorno;
	}
}
