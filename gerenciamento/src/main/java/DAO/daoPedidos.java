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
import model.ModelCancelamento;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;

public class daoPedidos {
	private Connection connection;
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelPedidos gravarPedido(ModelPedidos pedido, int id_usuario) {
		try {
			String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega, produtos_pai_id, usuario_pai_id, status, nome) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			daoFornecimento daofornecimento = new daoFornecimento();
			Long id_preduto = pedido.getFornecedor_pai_id().getProduto_pai_id().getId();
			ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(pedido.getFornecedor_pai_id().getId(), id_preduto, id_usuario);
			int a = 0;
			statement.setString(1, pedido.getDatapedido());
			statement.setLong(2, pedido.getQuantidade());
			statement.setLong(3, pedido.getValor());
			statement.setLong(4, pedido.getQuantidade()*fornecedor.getValor());
			statement.setLong(5, pedido.getFornecedor_pai_id().getId());
			statement.setString(6, pedido.getDataentrega());
			statement.setLong(7, fornecedor.getProduto_pai_id().getId());
			statement.setLong(8, id_usuario);
			statement.setInt(9, a);
			statement.setString(10, pedido.getNome());
			statement.execute();
			connection.commit();
			
			return pedido;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return null;
		}
	}
	
	public int somaQuantidade(Long produtoId) throws SQLException {
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE produtos_pai_id = ? AND status = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, produtoId);
		int status = 0;
		statement.setInt(2, status);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}	
	
	public int somaValores(Long produtoId) throws SQLException {
		String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE produtos_pai_id = ? AND status = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, produtoId);
		int status = 0;
		statement.setInt(2, status);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}
	
	public List<ModelPedidos> listarPedidos(Long id) throws SQLException {
		
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		
		String sql = "SELECT * FROM pedidos WHERE status = ? AND produtos_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		int status = 0;
		statement.setLong(1, status);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
	        ModelPedidos pedidos = new ModelPedidos();
	        
	        pedidos.setDataentrega(resultado.getString("dataentrega"));
	        pedidos.setDatapedido(resultado.getString("datapedido"));
	        pedidos.setQuantidade(resultado.getLong("quantidade"));
	        pedidos.setId(resultado.getLong("id"));
	        pedidos.setNome(resultado.getString("nome"));
	        
			retorno.add(pedidos);
		}
		
		return retorno;
	}
	
	public List<ModelPedidos> listarPedidosRelatorio(int id) throws SQLException {
		
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		
		String sql = "SELECT * FROM pedidos WHERE usuario_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
	        ModelPedidos pedidos = new ModelPedidos();
	        
	        pedidos.setDatapedido(resultado.getString("datapedido"));
	        pedidos.setValorTotal(resultado.getLong("valortotal"));
	        pedidos.setQuantidade(resultado.getLong("quantidade"));
	        pedidos.setId(resultado.getLong("id"));
	        pedidos.setNome(resultado.getString("nome"));
	        
			retorno.add(pedidos);
		}
		
		return retorno;
	}
	
	public List<ModelPedidos> listarRelatorio(int id, int status) throws SQLException {
		
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		
		String sql = "SELECT * FROM pedidos WHERE status = ? AND usuario_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, status);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
	        ModelPedidos pedidos = new ModelPedidos();
	        
	        pedidos.setDataentrega(resultado.getString("dataentrega"));
	        pedidos.setQuantidade(resultado.getLong("quantidade"));
	        pedidos.setId(resultado.getLong("id"));
	        pedidos.setValorTotal(resultado.getLong("valortotal"));
	        pedidos.setNome(resultado.getString("nome"));
	        pedidos.setDatapedido(resultado.getString("datapedido"));
	        
	        daoPedidos daopedido = new daoPedidos();
	        
	        ModelCancelamento cancelamento = daopedido.buscarCancelamento(resultado.getLong("id"));
	        System.out.println(cancelamento.getDatacancelamento());
	        pedidos.setDatacancelamento(cancelamento.getDatacancelamento());
	        
			retorno.add(pedidos);
		}
		
		return retorno;
	}
	
	public void mudarStatus(int id, int status) throws SQLException{
		String sql = "UPDATE pedidos SET status = ? WHERE id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, status);
		statement.setInt(2, id);

		statement.executeUpdate();
		connection.commit();
	}
	
	public ModelPedidos buscarPedido(Long id) throws SQLException {
		
		ModelPedidos pedido = new ModelPedidos();
		
		String sql = "SELECT * FROM pedidos WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			pedido.setDataentrega(resultado.getString("dataentrega"));
	        pedido.setDatapedido(resultado.getString("datapedido"));
	        pedido.setId(resultado.getLong("id"));
		}
		return pedido;	
	}
	
	public void excluirPedido(Long id) throws SQLException {
		
		String sql = "DELETE FROM pedidos WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		
		statement.execute();
		connection.commit();
	}
	
	public ModelCancelamento buscarCancelamento(Long id) throws SQLException {
		
		ModelCancelamento cancelamento = new ModelCancelamento();
		
		String sql = "SELECT * FROM cancelamentos WHERE pedido_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			cancelamento.setDatacancelamento(resultado.getString("datacancelamento"));
		}
		return cancelamento;	
	}
	
	public void gravarCancelamento(String datacancelamento, Long id){
		try {
			String sql = "INSERT INTO cancelamentos(datacancelamento, pedido_pai_id) VALUES (?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, datacancelamento);
			statement.setLong(2, id);
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
