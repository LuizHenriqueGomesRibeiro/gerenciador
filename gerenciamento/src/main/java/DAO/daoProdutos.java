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
import model.ModelProdutos;

public class daoProdutos {
	
	private Connection connection;
	
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
			
			statement.execute();
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
	
	public List<ModelProdutos> listarProdutos(int id) throws SQLException {
		
		List<ModelProdutos> retorno = new ArrayList<ModelProdutos>();
		String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + id + " LIMIT 10";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			
			ModelProdutos produtos = new ModelProdutos();
			daoPedidos pedido = new daoPedidos();
			
			produtos.setId(resultado.getLong("id"));
			
			int quantidade = pedido.somaQuantidade(resultado.getLong("id"));
			String quantidadeString = String.valueOf(quantidade);
			DecimalFormat formato = new DecimalFormat("#,###");
	        String quantidadeStringFormatado = formato.format(Double.parseDouble(quantidadeString));
	        
	        int valor = pedido.somaValores(resultado.getLong("id"));
	        
	        if(valor == 0) {
	        	produtos.setValorTotalString("Sem valores");
	        }else {
	        	
	        	NumberFormat format = NumberFormat.getInstance();
	        	format.setGroupingUsed(true);
	        
	        	String precoFormatado = format.format(valor);
	        	String precoFormatado00R$ = "R$" + precoFormatado + ",00";
	        	
	        	produtos.setValorTotalString(precoFormatado00R$);
	        }
	        
	        if(quantidade == 0) {
	        	produtos.setQuantidadePedidaString("Sem quantidades");
	        }else {
	        	produtos.setQuantidadePedidaString(quantidadeStringFormatado + " unidades");
	        }
	       
			produtos.setQuantidade(resultado.getInt("quantidade"));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(id));
			produtos.setNome(resultado.getString("nome"));

			retorno.add(produtos);
		}
		
		return retorno;
	}
	
	public String somaProdutos(int usuario_pai_id) throws Exception{
		
		String sql = "SELECT SUM(valortotal) FROM pedidos WHERE usuario_pai_id = ? AND status = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, usuario_pai_id);
		int a = 0;
		statement.setInt(2, a);
		ResultSet resultado = statement.executeQuery();
		int soma = 0;
		if (resultado.next()) {
		    soma = resultado.getInt(1);
		}
		
        NumberFormat format = NumberFormat.getInstance();

        format.setGroupingUsed(true);

        String numeroFormatado = format.format(soma);
		
		return numeroFormatado;
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
			
			ModelProdutos produtos = new ModelProdutos();
			daoPedidos pedido = new daoPedidos();
	        
			produtos.setId(resultado.getLong("id"));
			
			int quantidade = pedido.somaQuantidade(resultado.getLong("id"));
			String quantidadeString = String.valueOf(quantidade);
			DecimalFormat formato = new DecimalFormat("#,###");
	        String quantidadeStringFormatado = formato.format(Double.parseDouble(quantidadeString));
	        
	        int valor = pedido.somaValores(resultado.getLong("id"));
	        
	        if(valor == 0) {
	        	produtos.setValorTotalString("Sem valores");
	        }else {
	        	
	        	NumberFormat format = NumberFormat.getInstance();
	        	format.setGroupingUsed(true);
	        
	        	String precoFormatado = format.format(valor);
	        	String precoFormatado00R$ = "R$" + precoFormatado + ",00";
	        	
	        	produtos.setValorTotalString(precoFormatado00R$);
	        }
	        
	        if(quantidade == 0) {
	        	produtos.setQuantidadePedidaString("Sem quantidades");
	        }else {
	        	produtos.setQuantidadePedidaString(quantidadeStringFormatado);
	        }
	       
			produtos.setQuantidade(pedido.somaQuantidade(resultado.getLong("id")));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(usuario));
			produtos.setNome(resultado.getString("nome"));

			retorno.add(produtos);
		}
		return retorno;
	}
	
	public ModelProdutos adicionaProdutoCaixa(int id, int quantidade) throws SQLException {
		String sql = "UPDATE produtos SET quantidade = quantidade + ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ModelProdutos produto = new ModelProdutos();
		statement.setInt(1, quantidade);
		statement.setInt(2, id);
		
		statement.executeUpdate();
		connection.commit();
		
		return produto;
	}
}	

