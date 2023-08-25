package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.conexao;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelUsuarios;

public class daoPedidos {
	private Connection connection;
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelPedidos gravarPedido(ModelPedidos pedido, int id_usuario) {
		try {
			String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega, produtos_pai_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			daoFornecimento daofornecimento = new daoFornecimento();
			Long id_preduto = pedido.getFornecedor_pai_id().getProduto_pai_id().getId();
			ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(pedido.getFornecedor_pai_id().getId(), id_preduto, id_usuario);
			
			statement.setString(1, pedido.getDataPedido());
			statement.setLong(2, pedido.getQuantidade());
			statement.setLong(3, pedido.getValor());
			statement.setLong(4, pedido.getQuantidade()*fornecedor.getValor());
			statement.setLong(5, pedido.getFornecedor_pai_id().getId());
			statement.setString(6, pedido.getDataEntrega());
			statement.setLong(7, fornecedor.getProduto_pai_id().getId());
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
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE produtos_pai_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, produtoId);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		System.out.println(resultado.getInt("soma"));
		
		return resultado.getInt("soma");
	}	
}
