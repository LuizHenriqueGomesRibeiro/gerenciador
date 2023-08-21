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
	
	public void gravarNovoFornecedor(String nome, ModelProdutos produtos_pai_id) {
		try {
			String sql = "INSERT INTO fornecimento(nome, produtos_pai_id) VALUES (?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setLong(2, produtos_pai_id.getId());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<ModelFornecimento> listarFornecedores(int id) throws SQLException {
		
		List<ModelFornecimento> retorno = new ArrayList<ModelFornecimento>();
		
		String sql = "SELECT * FROM fornecedores WHERE produtos_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			
			ModelFornecimento fornecedores = new ModelFornecimento();
			
			fornecedores.setId(resultado.getLong("id"));
			fornecedores.setNome(resultado.getString("nome"));
		
			retorno.add(fornecedores);
		}
		
		return retorno;
	}
}
