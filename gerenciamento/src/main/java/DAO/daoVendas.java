package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendas {
private Connection connection;
	
	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarVenda(ModelVendas venda) {
		try {
			String sql = "INSERT INTO vendas(produtos_pai_id, dataentrega, valortotal, quantidade, nome) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, venda.getProduto_pai().getId());
			statement.setString(2, venda.getDataentrega());
			statement.setInt(3, venda.getValortotal()*venda.getQuantidade());
			statement.setInt(4, venda.getQuantidade());
			statement.setString(5, venda.getProduto_pai().getNome());
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();	
		}
	}
	
	public List<ModelVendas> listarVendas(int id_usuario) throws SQLException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		String sql = "SELECT * FROM vendas";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelVendas vendas = new ModelVendas();
			Long id = resultado.getLong("produtos_pai_Id");
			daoProdutos daoproduto = new daoProdutos();
			ModelProdutos produto = daoproduto.consultaProduto(id, id_usuario);
			vendas.setNome(produto.getNome());
			vendas.setId(resultado.getInt("id"));
			vendas.setDataentrega(resultado.getString("dataentrega"));
			vendas.setValortotal(resultado.getInt("valortotal"));
			vendas.setProduto_pai(produto);
			vendas.setQuantidade(resultado.getInt("quantidade"));
			
			retorno.add(vendas);
		}
		
		return retorno;
	}
}
