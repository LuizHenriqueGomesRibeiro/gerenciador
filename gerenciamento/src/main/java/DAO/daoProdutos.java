package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import conexao.conexao;
import model.ModelUsuarios;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

public class daoProdutos {
	
	private Connection connection;
	DaoGenerico dao = new DaoGenerico();
	daoLogin daoLogin = new daoLogin();

	public daoProdutos() {
		connection = conexao.getConnection();
	}
	
	public void gravarProduto(ModelProdutos modelProduto) {

		try {
			String sql = "INSERT INTO produtos(nome, usuario_pai_id, quantidade) VALUES (?, ?, ?);";
			
			ModelUsuarios usuario_pai_id = modelProduto.getUsuario_pai_id();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, modelProduto.getNome());
			statement.setLong(2, usuario_pai_id.getId());
			int a = 0;
			statement.setInt(3, a);
			
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void excluirProduto(String id) {
		try {
			String sql = "DELETE FROM produtos WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			statement.executeUpdate();
			
			connection.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}
	
	public ModelProdutos consultaProduto(Long id, int userLogado) {
		ModelProdutos modelProduto = new ModelProdutos();
		try {
			String sql = "SELECT*FROM produtos WHERE id = ? AND usuario_pai_id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.setLong(2, userLogado);
			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {
				modelProduto.setId(resultado.getLong("id"));
				modelProduto.setNome(resultado.getString("nome"));
				modelProduto.setPreco(resultado.getInt("preco"));
				modelProduto.setQuantidade(resultado.getInt("quantidade"));
				modelProduto.setValorTotal(resultado.getInt("preco")*resultado.getInt("quantidade"));
			}
			return modelProduto;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizarProduto(ModelProdutos modelProduto) {
		try {
			String sql = "UPDATE produtos SET preco = ?, quantidade = ?, nome = ?, valortotal = ? WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, modelProduto.getPreco());
			statement.setInt(2, modelProduto.getQuantidade());
			statement.setString(3, modelProduto.getNome());
			statement.setInt(4, modelProduto.getQuantidade()*modelProduto.getPreco());
			statement.setLong(5, modelProduto.getId());

			statement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}

	public List<ModelProdutos> listarProdutos(String sql, int id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		return resultadosListagem(resultado, id);
	}
	
	public List<ModelProdutos> resultadosListagem(ResultSet resultado, int id) throws SQLException {
		List<ModelProdutos> retorno = new ArrayList<ModelProdutos>();
		while(resultado.next()){
			ModelProdutos produtos = new ModelProdutos();
			DaoGenerico dao = new DaoGenerico(); 
			
			int status = 0;
			String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE produtos_pai_id = " + resultado.getLong("id") + " AND status = " + status;
			if(dao.somaValores(sql) == 0) {
	        	produtos.setValorTotalString("Sem valores");
	        }else {
	        	produtos.setValorTotalString(dao.converterIntegerDinheiro(dao.somaValores(sql)));
	        }
	        
	        if(dao.somaQuantidade(sql) == 0) {
	        	produtos.setQuantidadePedidaString("Sem quantidades");
	        }else {
	        	sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE produtos_pai_id = " + resultado.getLong("id") + " AND status = " + status;
	        	produtos.setQuantidadePedidaString(dao.colocarPonto(String.valueOf(dao.somaQuantidade(sql))) + " unidades");
	        }
	       
	        produtos.setId(resultado.getLong("id"));
			produtos.setQuantidade(resultado.getInt("quantidade"));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(id));
			produtos.setNome(resultado.getString("nome"));

			retorno.add(produtos);
		}
		return retorno;
	}
	
	public int somaProdutos(int usuario_pai_id) throws Exception{
		String sql = "SELECT SUM(valortotal) FROM pedidos WHERE usuario_pai_id = " + usuario_pai_id + " AND status = " + 0L;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
		    int soma = resultado.getInt(1);
		    return soma;
		}else {
			return -1;
		}
	}
	
	public int consultaProdutosPaginas(int usuario) throws Exception {
		String sql = "SELECT count(1) AS total FROM produtos WHERE usuario_pai_id = " + usuario;
		PreparedStatement statement = connection.prepareStatement(sql);

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
	
	public ModelProdutos adicionaProdutoCaixa(int id, int quantidade) throws SQLException {
		String sql = "UPDATE produtos SET quantidade = quantidade + " + quantidade + " WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ModelProdutos produto = new ModelProdutos();
		statement.executeUpdate();
		connection.commit();
		
		return produto;
	}
}	

