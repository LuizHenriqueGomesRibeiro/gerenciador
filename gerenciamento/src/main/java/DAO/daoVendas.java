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
	DaoGenerico dao = new DaoGenerico();
	daoProdutos daoproduto = new daoProdutos();

	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarVenda(ModelVendas venda, int usuario_pai_id) {
		try {
			String sql = "INSERT INTO vendas(produtos_pai_id, dataentrega, valortotal, quantidade, nome, usuario_pai_id) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, venda.getProduto_pai().getId());
			statement.setString(2, dao.converterDatas(venda.getDataentrega()));
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
	
	public List<ModelVendas> listarVendas(String sql, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelVendas vendas = new ModelVendas();
			ModelProdutos produto = daoproduto.consultaProduto(resultado.getLong("produtos_pai_Id"), resultado.getInt("usuario_pai_id"));
			vendas.setNome(produto.getNome());
			vendas.setId(resultado.getInt("id"));
			vendas.setDataentrega(dao.converterDatas(resultado.getString("dataentrega")));
			vendas.setValortotal(resultado.getInt("valortotal"));
			vendas.setProduto_pai(produto);
			vendas.setQuantidade(resultado.getInt("quantidade"));
			
			daoVendas daovenda = new daoVendas();
			
			vendas.setValores(dao.somaValores(sqlSomaValores));
			vendas.setQuantidadeTotal(dao.somaQuantidade(sqlSomaQuantidade));
			
			retorno.add(vendas);
		}
		
		return retorno;
	}
	
	public BeanChart listarVendasGrafico(String sql) throws SQLException, ParseException{
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		List<Long> valores = new ArrayList<Long>();
		List<String> datas = new ArrayList<String>();
		
		BeanChart beanChart = new BeanChart();
		
		while(resultado.next()) {
			valores.add(resultado.getLong("valortotal"));
			datas.add(dao.converterDatas(resultado.getString("dataentrega")));
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}	
}
