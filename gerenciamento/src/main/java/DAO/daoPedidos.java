package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Util.BeanChart;
import conexao.conexao;
import model.ModelCancelamento;
import model.ModelFornecimento;
import model.ModelPedidos;

public class daoPedidos {
	private Connection connection;
	DaoGenerico dao = new DaoGenerico();
	
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
			statement.setString(1, dao.converterDatas(pedido.getDatapedido()));
			statement.setLong(2, pedido.getQuantidade());
			statement.setLong(3, pedido.getValor());
			statement.setLong(4, pedido.getQuantidade()*fornecedor.getValor());
			statement.setLong(5, pedido.getFornecedor_pai_id().getId());
			statement.setString(6, dao.converterDatas(pedido.getDataentrega()));
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
	
	public int somaQuantidade(int usuario_pai_id, int status) throws SQLException {
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE usuario_pai_id = ? AND status = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, usuario_pai_id);
		statement.setInt(2, status);
		
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}	
	
	public int somaValores(int usuario_pai_id, int status) throws SQLException {
		String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE usuario_pai_id = ? AND status = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, usuario_pai_id);
		statement.setInt(2, status);
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}
	
	public int somaQuantidadeRelatorio(int usuario_pai_id, int status, String dataInicial, String dataFinal) throws SQLException, ParseException {
		String sql = "SELECT SUM(quantidade) AS soma FROM pedidos WHERE usuario_pai_id = ? AND status = ? AND dataentrega >= ? AND dataentrega <= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, usuario_pai_id);
		statement.setInt(2, status);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(3, dataSql);
		statement.setDate(4, dataSql2);
		
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
	}	
	
	public int somaValoresRelatorio(int usuario_pai_id, int status, String dataInicial, String dataFinal) throws SQLException, ParseException {
		String sql = "SELECT SUM(valortotal) AS soma FROM pedidos WHERE usuario_pai_id = ? AND status = ? AND dataentrega >= ? AND dataentrega <= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, usuario_pai_id);
		statement.setInt(2, status);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(3, dataSql);
		statement.setDate(4, dataSql2);
		
		ResultSet resultado = statement.executeQuery();
			
		resultado.next();
		
		return resultado.getInt("soma");
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
			pedido.setId(resultado.getLong("id"));
			pedido.setQuantidade(resultado.getLong("quantidade"));
			pedido.setValorTotal(resultado.getLong("valortotal"));
			pedido.setDataentrega(dao.converterDatas(resultado.getString("dataentrega")));
			pedido.setDatapedido(dao.converterDatas(resultado.getString("datapedido")));
			pedido.setValor(resultado.getLong("valor"));
			pedido.setNome(resultado.getString("nome"));
			pedido.setQuantidadeTotal(somaQuantidade(resultado.getInt("usuario_pai_id"), resultado.getInt("status")));
			pedido.setValores(somaValores(resultado.getInt("usuario_pai_id"), resultado.getInt("status")));
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

	public BeanChart listarEntradasGrafico(int id_usuario, int status) throws SQLException{
		
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = ? AND status = ? ORDER BY dataentrega ASC";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id_usuario);
		statement.setInt(2, status);
		ResultSet resultado = statement.executeQuery();
		List<Long> valores = new ArrayList<Long>();
		List<String> datas = new ArrayList<String>();
		
		BeanChart beanChart = new BeanChart();
		
		while(resultado.next()) {
			Long valortotal = resultado.getLong("valortotal");
			
			String data = resultado.getString("dataentrega");
			String[] parte = data.split(" ");
			
			data = dao.transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
			valores.add(valortotal);
			datas.add(data);
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}
	
	
	public BeanChart listarEntradasGrafico(int id_usuario, int status, String dataInicial, String dataFinal) throws SQLException, ParseException{
		
		String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = ? AND status = ? AND dataentrega >= ? AND dataentrega <= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id_usuario);
		statement.setInt(2, status);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(3, dataSql);
		statement.setDate(4, dataSql2);
		
		ResultSet resultado = statement.executeQuery();
		List<Long> valores = new ArrayList<Long>();
		List<String> datas = new ArrayList<String>();
		
		BeanChart beanChart = new BeanChart();
		
		while(resultado.next()) {
			Long valortotal = resultado.getLong("valortotal");
			
			String data = resultado.getString("dataentrega");
			String[] parte = data.split(" ");
			
			data = dao.transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
			valores.add(valortotal);
			datas.add(data);
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}
}
