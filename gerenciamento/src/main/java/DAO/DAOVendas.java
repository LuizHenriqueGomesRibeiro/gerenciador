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
import DAO.SQL.SQLVendas;
import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class DAOVendas extends DAOComum{
	private Connection connection;
	DAOProdutos daoproduto = new DAOProdutos();
	SQLProdutos sqlproduto = new SQLProdutos();
	SQLRelatorio sqlRelatorio = new SQLRelatorio();
	SQLVendas sqlvendas = new SQLVendas();
	
	public DAOVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarDatas(int usuario_pai_id, String datavenda) throws SQLException, ParseException {
		LocalDate startDate = LocalDate.parse(converterDatas(buscarVendas(sqlRelatorio.ultimoIdDataVenda()).getDataentrega()));
		startDate = startDate.plusDays(1);
		LocalDate endDate = LocalDate.parse(datavenda);
		while (!startDate.isAfter(endDate)) {
			gravarData(sqlvendas.gravacaoAutomaticaVenda(usuario_pai_id, startDate));
			startDate = startDate.plusDays(1);
		}
	}
	
	public void gravarData(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
	}
	
	public void gravarVenda(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public ModelVendas buscarVendas(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadoBuscarVendas(resultado);
	}
	
	public ModelVendas resultadoBuscarVendas(ResultSet resultado) throws SQLException, ParseException {
		ModelVendas venda = new ModelVendas();
		while(resultado.next()) {
			venda = setVendas(resultado);
		}
		return venda;
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
	
	public ModelVendas setVendas(ResultSet resultado) throws SQLException, ParseException {
		ModelVendas vendas = new ModelVendas();
		//vendas.setNome(produto(resultado).getNome());
		vendas.setId(id(resultado));
		vendas.setDataentrega(datavenda(resultado));
		vendas.setValortotal(valortotal(resultado));
		//vendas.setProduto_pai(produto(resultado));
		//vendas.setQuantidade(quantidade(resultado));
		return vendas;
	}
	
	public ModelVendas setVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException {
		ModelVendas vendas = new ModelVendas();
		vendas.setNome(produto(resultado).getNome());
		vendas.setId(id(resultado));
		vendas.setDataentrega(dataentrega(resultado));
		vendas.setValortotal(valortotal(resultado));
		//vendas.setProduto_pai(produto(resultado));
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
