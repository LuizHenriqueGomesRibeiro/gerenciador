package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.SQL.SQLPedidos;
import conexao.conexao;
import model.ModelFornecimento;
import model.ModelPedidos;

public class daoPedidos {
	private Connection connection;
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecimento = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelPedidos gravarPedido(ModelPedidos pedido) throws SQLException {
		ModelFornecimento fornecedor = daofornecimento.consultarFornecedor(pedido.getFornecedor_pai_id());
		int status = 0;
		String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega, produtos_pai_id, usuario_pai_id, status, nome)" + 
			" VALUES ('" + 
			dao.converterDatas(pedido.getDatapedido()) + "', " + 
			pedido.getQuantidade() + ", " + 
			pedido.getValor() + ", " + 
			pedido.getQuantidade()*fornecedor.getValor() + ", " + 
			pedido.getFornecedor_pai_id().getId() + ", '" + 
			dao.converterDatas(pedido.getDataentrega()) + "', " + 
			fornecedor.getProduto_pai_id().getId() + ", " + 
			pedido.getUsuario_pai_id().getId() + ", " + 
			status + ", '" + 
			pedido.getNome() + "');";
		System.out.println(sql);
		PreparedStatement statement = connection.prepareStatement(sql);
			
		statement.execute();
		connection.commit();
			
		return pedido;
	}
	
	public ModelPedidos buscarPedido(Long id) throws SQLException {
		
		ModelPedidos pedido = new ModelPedidos();
		
		String sql = "SELECT * FROM pedidos WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
			pedido.setDataentrega(resultado.getString("dataentrega"));
	        pedido.setDatapedido(resultado.getString("datapedido"));
	        pedido.setId(resultado.getInt("id"));
		}
		return pedido;	
	}
	
	public List<ModelPedidos> listarPedidos(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
	    return objetosListarPedidos(resultado);
	}
	
	public List<ModelPedidos> objetosListarPedidos(ResultSet resultado) throws SQLException{
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		return resultadosListagem(resultado, retorno);
	}
		
	public List<ModelPedidos> resultadosListagem(ResultSet resultado, List<ModelPedidos> retorno) throws SQLException {
		while(resultado.next()){
			ModelPedidos pedido = new ModelPedidos();
			pedido.setQuantidadeTotal(dao.somaQuantidade(sqlpedidos.somaQuantidadePedido(resultado.getInt("usuario_pai_id"), resultado.getInt("status"))));
			pedido.setValores(dao.somaValores(sqlpedidos.somaValoresPedido(resultado.getInt("usuario_pai_id"), resultado.getInt("status"))));
			pedido.setId(resultado.getInt("id"));
			pedido.setQuantidade(resultado.getLong("quantidade"));
			pedido.setValorTotal(resultado.getInt("valortotal"));
			pedido.setDataentrega(dao.converterDatas(resultado.getString("dataentrega")));
			pedido.setDatapedido(dao.converterDatas(resultado.getString("datapedido")));
			pedido.setValor(resultado.getInt("valor"));
			pedido.setNome(resultado.getString("nome"));
			retorno.add(pedido);
		}
		return retorno;
	}
	
	public void mudarStatus(Long id, int status) throws SQLException{
		if(status == 1) {
			gravarCancelamento(id);
		}
		String sql = "UPDATE pedidos SET status = " + status + " WHERE id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.executeUpdate();
		connection.commit();
	}
	
	public void gravarCancelamento(Long id) throws SQLException{
		String sql = "INSERT INTO cancelamentos(datacancelamento, pedido_pai_id) VALUES ('" + LocalDate.now() + "', " + id + ");";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	
}
