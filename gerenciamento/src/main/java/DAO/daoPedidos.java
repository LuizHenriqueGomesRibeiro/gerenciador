package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
			String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega) VALUES (?, ?, ?, ?, ?, ?);";
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
			statement.execute();
			connection.commit();
			
			return pedido;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	
	
}
