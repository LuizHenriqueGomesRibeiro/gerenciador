package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.SQL.SQLPedidos;
import conexao.conexao;
import model.ModelFornecimento;
import model.ModelPedidos;

public class DAOPedidos extends DAOComum{
	private Connection connection;
	DAOFornecimento daofornecimento = new DAOFornecimento();
	DAOProdutos daoprodutos = new DAOProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	
	public DAOPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelPedidos buscarPedido(String sql) throws Exception {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return buscarPedidoResultado(resultado);	
	}
	
	public ModelPedidos buscarPedidoResultado(ResultSet resultado) throws Exception {
		ModelPedidos pedido = new ModelPedidos(); 
		while(resultado.next()){
			pedido = setPedido(resultado);
		}
		return pedido;
	}
	
	public List<ModelPedidos> listarPedidos(String sql) throws Exception {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
	    return resultadosListagem(resultado);
	}
		
	public List<ModelPedidos> resultadosListagem(ResultSet resultado) throws Exception {
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		while(resultado.next()){
			ModelPedidos pedido = new ModelPedidos();
			pedido = setPedido(resultado);
			retorno.add(pedido);
		}
		return retorno;
	}
	
	public ModelPedidos setPedido(ResultSet resultado) throws Exception {
		ModelPedidos pedido = new ModelPedidos();
		pedido.setDataentrega(dataentrega(resultado));
		pedido.setDatapedido(datapedido(resultado));
		pedido.setId(id(resultado));
		pedido.setQuantidadeTotal(somaQuantidade(sqlpedidos.somaQuantidadePedido(usuario_pai_id(resultado), status(resultado))));
		pedido.setValores(somaValores(sqlpedidos.somaValoresPedido(usuario_pai_id(resultado), status(resultado))));
		pedido.setQuantidade(quantidade(resultado));
		pedido.setValorTotal(valortotal(resultado));
		pedido.setValor(valor(resultado));
		pedido.setNome(nome(resultado));
		pedido.setProduto_pai_id(daoprodutos.consultarProduto(sqlproduto.consultaProduto(produtos_pai_Id(resultado))));
		return pedido;
	}
	
	public void mudarStatus(String sql) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
}
