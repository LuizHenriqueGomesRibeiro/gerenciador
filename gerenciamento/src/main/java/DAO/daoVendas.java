package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import DAO.SQL.SQLProdutos;
import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendas extends DAOComum{
	private Connection connection;
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlproduto = new SQLProdutos();
	
	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarDatas(int usuario_pai_id) throws SQLException {
		LocalDate startDate = LocalDate.parse("2023-01-01");
		LocalDate endDate = LocalDate.parse("2023-02-01");
		
		while (!startDate.isAfter(endDate)) {
			//createRecord(connection, startDate);
			String query = "INSERT INTO datavenda (datavenda, usuario_pai_id) VALUES (?, " + usuario_pai_id + ")";
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, java.sql.Date.valueOf(startDate));
			statement.executeUpdate();
			startDate = startDate.plusDays(1);
		}
	}
	
	/*
	public void gravarDatas(int usuario_pai_id) throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String teste1 = "2021-01-01";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(teste1));
        while (!calendar.getTime().after(stringToDate("2021-01-05"))) {
            String formattedDate = dateFormat.format(calendar.getTime());
            String query = "INSERT INTO datavenda (datavenda, usuario_pai_id) VALUES (" + formattedDate +", " + usuario_pai_id + ")";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
	}
	*/
	public void gravarVenda(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public List<ModelVendas> listarVendas(String sql, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadoListarVendas(resultado, sqlSomaValores, sqlSomaQuantidade);
	}
	
	public List<ModelVendas> resultadoListarVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		while(resultado.next()) {
			ModelVendas vendas = setVendas(resultado, sqlSomaValores, sqlSomaQuantidade);
			retorno.add(vendas);
		}
		return retorno;
	}
	
	public ModelVendas setVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException {
		ModelVendas vendas = new ModelVendas();
		vendas.setNome(produto(resultado).getNome());
		vendas.setId(id(resultado));
		vendas.setDataentrega(dataentrega(resultado));
		vendas.setValortotal(valortotal(resultado));
		vendas.setProduto_pai(produto(resultado));
		vendas.setQuantidade(quantidade(resultado));
		vendas.setValores(somaValores(sqlSomaValores));
		vendas.setQuantidadeTotal(somaQuantidade(sqlSomaQuantidade));
		return vendas;
	}
	
	public ModelProdutos produto(ResultSet resultado) throws SQLException {
		ModelProdutos produto = daoproduto.consultarProduto(sqlproduto.consultaProduto(produtos_pai_Id(resultado)));
		return produto;
	}
	
	
    
}
