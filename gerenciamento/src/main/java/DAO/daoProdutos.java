package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.conexao;
import model.ModelUsuarios;
import model.ModelProdutos;

public class daoProdutos {
	
	private Connection connection;
	
	daoLogin daoLogin = new daoLogin();

	public daoProdutos() {
		connection = conexao.getConnection();
	}
	
	public void gravarProduto(ModelProdutos modelProduto) {

		try {
			String sql = "INSERT INTO produtos(preco, quantidade, nome, usuario_pai_id) VALUES (?, ?, ?, ?);";
			
			ModelUsuarios usuario_pai_id = modelProduto.getUsuario_pai_id();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, modelProduto.getPreco());
			statement.setInt(2, modelProduto.getQuantidade());
			statement.setString(3, modelProduto.getNome());
			statement.setInt(4, usuario_pai_id.getId());
			
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}
	
	public List<ModelProdutos> listarProdutos(int id) throws SQLException {
		
		List<ModelProdutos> retorno = new ArrayList<ModelProdutos>();
		
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " LIMIT 10";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
		
			ModelProdutos produtos = new ModelProdutos();
			
			produtos.setId(resultado.getLong("id"));
			produtos.setQuantidade(resultado.getInt("quantidade"));
			produtos.setPreco(resultado.getInt("preco"));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(id));
			produtos.setNome(resultado.getString("nome"));
			
			retorno.add(produtos);
		}
		
		return retorno;
	}
	
	public int consultaProdutosPaginas(int usuario) throws Exception {

		String sql = "SELECT count(1) AS total FROM produtos WHERE usuario_pai_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, usuario);

		ResultSet resultado = statement.executeQuery();

		resultado.next();
		
		Double total = resultado.getDouble("total");
		Double porPagina = 10.0;
		Double pagina = total / porPagina;
		Double resto = total % porPagina;
		
		if(resto>0) {
			pagina++;
		}
		
		return pagina.intValue();
	}
	
	public List<ModelProdutos> consultaProdutosOffset(int usuario, int offset) throws Exception{
		
		List<ModelProdutos> retorno = new ArrayList<ModelProdutos>();
		
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + usuario + " ORDER BY quantidade OFFSET ? LIMIT 10";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, offset);
		
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelProdutos modelProdutos = new ModelProdutos();

			modelProdutos.setNome(resultado.getString("nome"));
			modelProdutos.setPreco(resultado.getInt("preco"));
			modelProdutos.setId(resultado.getLong("id"));
			modelProdutos.setQuantidade(resultado.getInt("quantidade"));

			retorno.add(modelProdutos);
		}

		return retorno;
	}
}
