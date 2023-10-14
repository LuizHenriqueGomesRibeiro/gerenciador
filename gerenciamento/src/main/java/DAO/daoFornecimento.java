package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import conexao.conexao;
import model.ModelFornecimento;
import model.ModelProdutos;

public class daoFornecimento {

	private Connection connection;

	public daoFornecimento(){
		connection = conexao.getConnection();
	}
	
	public void gravarNovoFornecedor(String nome, ModelProdutos produtos_pai_id, Long tempoentrega, Long valor_R$Long) {
		try {
			String sql = "INSERT INTO fornecimento(nome, produtos_pai_id, tempoentrega, valor) VALUES (?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setLong(2, produtos_pai_id.getId());
			statement.setLong(3, tempoentrega);
			statement.setLong(4, valor_R$Long);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<ModelFornecimento> listarFornecedores(Long id) throws SQLException {
		List<ModelFornecimento> retorno = new ArrayList<ModelFornecimento>();
		String sql = "SELECT * FROM fornecimento WHERE produtos_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			ModelFornecimento fornecedores = new ModelFornecimento();
			
			fornecedores.setId(resultado.getLong("id"));
			fornecedores.setNome(resultado.getString("nome"));
			fornecedores.setTempoentrega(resultado.getLong("tempoentrega"));
			fornecedores.setValor(resultado.getLong("valor"));
			
			retorno.add(fornecedores);
		}
		return retorno;
	}
	
	public ModelFornecimento consultaFornecedor(Long id, Long produtoId, int usuarioId) {
		ModelFornecimento modelFornecimento = new ModelFornecimento();
		try {
			String sql = "SELECT* FROM fornecimento WHERE id = " + id + " AND produtos_pai_id = " + produtoId;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {
				daoProdutos daoprodutos = new daoProdutos();
				modelFornecimento.setId(resultado.getLong("id"));
				modelFornecimento.setTempoentrega(resultado.getLong("tempoentrega"));
				modelFornecimento.setNome(resultado.getString("nome"));
				modelFornecimento.setValor(resultado.getLong("valor"));
				modelFornecimento.setProduto_pai_id(daoprodutos.consultaProduto(produtoId, usuarioId));		
			}
			return modelFornecimento;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public void excluirFornecedor(String id) {
		try {
			String sql = "DELETE FROM fornecimento WHERE id = " + Long.parseLong(id);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Double mediaValoresFornecimento(Long produto) throws Exception{
		String sql = "SELECT AVG(valor) AS media FROM fornecimento WHERE produtos_pai_id = " + produto;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		Double total = resultado.getDouble("media");
		
		return total;
	}
}
