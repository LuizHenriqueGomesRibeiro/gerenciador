package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Servlet.SQL.SQL;
import Util.BeanChart;
import conexao.conexao;
import model.ModelFornecimento;
import model.ModelPedidos;

public class daoPedidos {
	private Connection connection;
	DaoGenerico dao = new DaoGenerico();
	daoFornecimento daofornecimento = new daoFornecimento();
	SQL sql = new SQL();
	
	public daoPedidos(){
		connection = conexao.getConnection();
	}
	
	public ModelPedidos gravarPedido(ModelPedidos pedido) {
		try {
			Long id_produto = pedido.getFornecedor_pai_id().getProduto_pai_id().getId();
			ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(pedido.getFornecedor_pai_id().getId(), id_produto, pedido.getUsuario_pai_id().getId());
			int status = 0;
			String sql = "INSERT INTO pedidos(datapedido, quantidade, valor, valortotal, fornecimento_pai_id, dataentrega, produtos_pai_id, usuario_pai_id, status, nome)" + 
					" VALUES ('" + dao.converterDatas(pedido.getDatapedido()) + "', " + 
					pedido.getQuantidade() + ", " + 
					pedido.getValor() + ", " + 
					pedido.getQuantidade()*fornecedor.getValor() + ", " + 
					pedido.getFornecedor_pai_id().getId() + ", '" + 
					dao.converterDatas(pedido.getDataentrega()) + "', " + 
					fornecedor.getProduto_pai_id().getId() + ", " + 
					pedido.getUsuario_pai_id().getId() + ", " + 
					status + ", '" + 
					pedido.getNome() + "');";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.execute();
			connection.commit();
			
			return pedido;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return null;
		}
	}

	public List<ModelPedidos> listarPedidos(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
	    return resultadosListagem(resultado);
	}
		
	public List<ModelPedidos> resultadosListagem(ResultSet resultado) throws SQLException {
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		while(resultado.next()){
			ModelPedidos pedido = new ModelPedidos();
			pedido.setQuantidadeTotal(dao.somaQuantidade(sql.somaQuantidadePedido(resultado.getInt("usuario_pai_id"), resultado.getInt("status"))));
			pedido.setValores(dao.somaValores(sql.somaValoresPedido(resultado.getInt("usuario_pai_id"), resultado.getInt("status"))));
			pedido.setId(resultado.getLong("id"));
			pedido.setQuantidade(resultado.getLong("quantidade"));
			pedido.setValorTotal(resultado.getLong("valortotal"));
			pedido.setDataentrega(dao.converterDatas(resultado.getString("dataentrega")));
			pedido.setDatapedido(dao.converterDatas(resultado.getString("datapedido")));
			pedido.setValor(resultado.getLong("valor"));
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
	
	public void gravarCancelamento(Long id){
		try {
			String sql = "INSERT INTO cancelamentos(datacancelamento, pedido_pai_id) VALUES ('" + LocalDate.now() + "', " + id + ");";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public BeanChart listarEntradasGrafico(String sql) throws SQLException, ParseException{
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		List<Long> valores = new ArrayList<Long>();
		List<String> datas = new ArrayList<String>();
		
		BeanChart beanChart = new BeanChart();
		
		while(resultado.next()) {
			valores.add(resultado.getLong("valortotal"));
			datas.add(dao.converterDatas(resultado.getString("dataentrega")));
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}
}
