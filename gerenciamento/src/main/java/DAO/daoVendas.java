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

import Util.BeanChart;
import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendas {
private Connection connection;
	
	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarVenda(ModelVendas venda, int usuario_pai_id) {
		try {
			String sql = "INSERT INTO vendas(produtos_pai_id, dataentrega, valortotal, quantidade, nome, usuario_pai_id) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			String data = transformarFormatoData(venda.getDataentrega(), "dd/MM/yyyy", "yyyy-MM-dd");
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dataUtil;
			
			dataUtil = formatador.parse(data);
			Date dataSql = new Date(dataUtil.getTime());
			
			statement.setLong(1, venda.getProduto_pai().getId());
			statement.setDate(2, dataSql);
			statement.setInt(3, venda.getValortotal()*venda.getQuantidade());
			statement.setInt(4, venda.getQuantidade());
			statement.setString(5, venda.getProduto_pai().getNome());
			statement.setInt(6, usuario_pai_id);
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();	
		}
	}
	
	public List<ModelVendas> listarVendas(int id_usuario) throws SQLException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id_usuario);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelVendas vendas = new ModelVendas();
			Long id = resultado.getLong("produtos_pai_Id");
			daoProdutos daoproduto = new daoProdutos();
			ModelProdutos produto = daoproduto.consultaProduto(id, id_usuario);
			vendas.setNome(produto.getNome());
			vendas.setId(resultado.getInt("id"));
			
			String data = resultado.getString("dataentrega");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			vendas.setDataentrega(data);
			
			vendas.setValortotal(resultado.getInt("valortotal"));
			vendas.setProduto_pai(produto);
			vendas.setQuantidade(resultado.getInt("quantidade"));
			
			retorno.add(vendas);
		}
		
		return retorno;
	}
	
	public List<ModelVendas> listarVendasPorTempo(int id_usuario, String dataInicial, String dataFinal) throws SQLException, ParseException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		String sql = "SELECT * FROM vendas WHERE usuario_pai_id = ? AND dataentrega >= ? AND dataentrega <= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(2, dataSql);
		statement.setDate(3, dataSql2);
		
		statement.setInt(1, id_usuario);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelVendas vendas = new ModelVendas();
			Long id = resultado.getLong("produtos_pai_Id");
			daoProdutos daoproduto = new daoProdutos();
			ModelProdutos produto = daoproduto.consultaProduto(id, id_usuario);
			vendas.setNome(produto.getNome());
			vendas.setId(resultado.getInt("id"));
			
			String data = resultado.getString("dataentrega");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			vendas.setDataentrega(data);
			
			vendas.setValortotal(resultado.getInt("valortotal"));
			vendas.setProduto_pai(produto);
			vendas.setQuantidade(resultado.getInt("quantidade"));
			
			retorno.add(vendas);
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
	
	public BeanChart listarVendasGrafico(int id_usuario) throws SQLException{
		
		String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = ? ORDER BY dataentrega ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id_usuario);
		ResultSet resultado = statement.executeQuery();
		List<Long> valores = new ArrayList<Long>();
		List<String> datas = new ArrayList<String>();
		
		BeanChart beanChart = new BeanChart();
		
		while(resultado.next()) {
			Long valortotal = resultado.getLong("valortotal");
			
			String data = resultado.getString("dataentrega");
			String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
			valores.add(valortotal);
			datas.add(data);
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}
}
