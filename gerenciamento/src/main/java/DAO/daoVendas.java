package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
	
	public void gravarVenda(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public PreparedStatement prepararObjeto(PreparedStatement statement, ModelVendas venda, int usuario_pai_id) throws SQLException {
		statement.setLong(1, venda.getProduto_pai().getId());
		statement.setString(2, venda.getDataentrega());
		statement.setInt(3, venda.getValortotal()*venda.getQuantidade());
		statement.setInt(4, venda.getQuantidade());
		statement.setString(5, venda.getProduto_pai().getNome());
		statement.setInt(6, usuario_pai_id);
		return statement;
	}
	
	public List<ModelVendas> listarVendas(String sql, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadoListarVendas(resultado, sqlSomaValores, sqlSomaQuantidade);
	}
	
	public List<ModelVendas> resultadoListarVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		while(resultado.next()) {
			ModelVendas vendas = new ModelVendas();
			vendas.setNome(produto(resultado).getNome());
			vendas.setId(id(resultado));
			vendas.setDataentrega(dataentrega(resultado));
			vendas.setValortotal(valortotal(resultado));
			vendas.setProduto_pai(produto(resultado));
			vendas.setQuantidade(quantidade(resultado));
			vendas.setValores(somaValores(sqlSomaValores));
			vendas.setQuantidadeTotal(somaQuantidade(sqlSomaQuantidade));
			retorno.add(vendas);
		}
		return retorno;
	}
	
	public ModelProdutos produto(ResultSet resultado) throws SQLException {
		ModelProdutos produto = daoproduto.consultarProduto(sqlproduto.consultaProduto(produtos_pai_Id(resultado), usuario_pai_id(resultado)));
		return produto;
	}
}
