package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLRelatorio;
import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendas extends DAOComum{
	private Connection connection;
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlproduto = new SQLProdutos();
	SQLRelatorio sqlRelatorio = new SQLRelatorio();
	
	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarDatas(int usuario_pai_id) throws SQLException {
		//LocalDate startDate = LocalDate.parse(converterDatas(buscarData(sqlRelatorio.maiorData(usuario_pai_id))));
		LocalDate startDate = LocalDate.parse("2023-01-01");
		LocalDate endDate = LocalDate.parse("2023-02-01");
		while (!startDate.isAfter(endDate)) {
			String query = "INSERT INTO datavenda (datavenda, valortotal, usuario_pai_id) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, java.sql.Date.valueOf(startDate)); // Setando a data no formato esperado pelo banco de dados
			statement.setInt(2, 0); // Valor fixo
			statement.setInt(3, usuario_pai_id);
			statement.execute();
			startDate = startDate.plusDays(1);
		}
	}
	
	/*
	public void gravarDatas(ModelVendas venda, int usuario_pai_id) throws SQLException, ParseException {
		//LocalDate startDate = LocalDate.parse(converterDatas(buscarData(sqlRelatorio.maiorData(usuario_pai_id))));
		LocalDate startDate = LocalDate.parse("2023-10-31");
		LocalDate endDate = LocalDate.parse("2023-12-01");
		System.out.println(startDate);
		//LocalDate endDate = LocalDate.parse(venda.getDataentrega());
		System.out.println(endDate);
		while (!startDate.isAfter(endDate)) {
			//int teste = contarDatas(sqlRelatorio.validacaoDatas(startDate));
				String sql = "INSERT INTO datavenda (datavenda, usuario_pai_id, valortotal) VALUES ('" + java.sql.Date.valueOf(startDate) + "', " + usuario_pai_id 
						+ ", " + 0 +")";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.execute();
			startDate = startDate.plusDays(1);
		}
	}
	*/
	public int contarDatas(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		return resultSet.getInt(1);
	}
	
	public String buscarData(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		String data = null;
		while(resultado.next()) {
			data = datavenda(resultado);
		}
		return data;
	}
	
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
