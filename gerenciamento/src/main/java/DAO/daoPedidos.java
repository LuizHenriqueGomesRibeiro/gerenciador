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

public class daoPedidos extends DAOComum{
	private Connection connection;
	DAOFerramentas dao = new DAOFerramentas();
	daoFornecimento daofornecimento = new daoFornecimento();
	SQLPedidos sqlpedidos = new SQLPedidos();
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public void gravarPedido(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public ModelPedidos buscarPedido(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return buscarPedidoResultado(resultado);	
	}
	
	public ModelPedidos buscarPedidoResultado(ResultSet resultado) throws SQLException {
		ModelPedidos pedido = new ModelPedidos();
		while(resultado.next()){
			pedido.setDataentrega(dataentrega(resultado));
	        pedido.setDatapedido(datapedido(resultado));
	        pedido.setId(id(resultado));
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
			pedido.setQuantidadeTotal(somaQuantidade(sqlpedidos.somaQuantidadePedido(usuario_pai_id(resultado), status(resultado))));
			pedido.setValores(somaValores(sqlpedidos.somaValoresPedido(usuario_pai_id(resultado), status(resultado))));
			pedido.setId(id(resultado));
			pedido.setQuantidade(quantidade(resultado));
			pedido.setValorTotal(valortotal(resultado));
			pedido.setDataentrega(dataentrega(resultado));
			pedido.setDatapedido(datapedido(resultado));
			pedido.setValor(valor(resultado));
			pedido.setNome(nome(resultado));
			retorno.add(pedido);
		}
		return retorno;
	}
	
	public void mudarStatus(Long id, int status) throws SQLException{
		if(status == 1) {
			gravarCancelamento(sqlpedidos.gravarCancelamento(id));
		}
		String sql = "UPDATE pedidos SET status = " + status + " WHERE id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.executeUpdate();
		connection.commit();
	}
	
	public void gravarCancelamento(String sql) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
}
