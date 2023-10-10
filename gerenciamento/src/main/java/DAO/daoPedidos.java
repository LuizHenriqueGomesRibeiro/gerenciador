package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Util.BeanChart;
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
			
			String data = transformarFormatoData(pedido.getDatapedido(), "dd/MM/yyyy", "yyyy-MM-dd");
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dataUtil;
			
			dataUtil = formatador.parse(data);
			Date dataSql = new Date(dataUtil.getTime());
			
			statement.setDate(1, dataSql);
			
			data = transformarFormatoData(pedido.getDataentrega(), "dd/MM/yyyy", "yyyy-MM-dd");
			dataUtil = formatador.parse(data);
			dataSql = new Date(dataUtil.getTime());
			
			statement.setDate(6, dataSql);
			
			daoFornecimento daofornecimento = new daoFornecimento();
			Long id_preduto = pedido.getFornecedor_pai_id().getProduto_pai_id().getId();
			ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(pedido.getFornecedor_pai_id().getId(), id_preduto, id_usuario);
			int a = 0;
			statement.setLong(2, pedido.getQuantidade());
			statement.setLong(3, pedido.getValor());
			statement.setLong(4, pedido.getQuantidade()*fornecedor.getValor());
			statement.setLong(5, pedido.getFornecedor_pai_id().getId());
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
	
	public List<ModelPedidos> listarPedidos(Long id, int status) throws SQLException {
		
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		
		String sql = "SELECT * FROM pedidos WHERE status = ? AND produtos_pai_id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
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
	        
	        String data = resultado.getString("dataentrega");
	        String[] parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			pedidos.setDataentrega(data);

			data = resultado.getString("datapedido");
			parte = data.split(" ");
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			pedidos.setDatapedido(data);
			
	        pedidos.setQuantidade(resultado.getLong("quantidade"));
	        pedidos.setId(resultado.getLong("id"));
	        pedidos.setValorTotal(resultado.getLong("valortotal"));
	        pedidos.setNome(resultado.getString("nome"));
	        
	        daoPedidos daopedido = new daoPedidos();
	        
	        ModelCancelamento cancelamento = daopedido.buscarCancelamento(resultado.getLong("id"));
	        pedidos.setDatacancelamento(cancelamento.getDatacancelamento());
	        
	        pedidos.setQuantidadeTotal(daopedido.somaQuantidade(id, status));
	        pedidos.setValores(daopedido.somaValores(id, status));
	        
	        System.out.println(daopedido.somaValores(id, status));
	        System.out.println(daopedido.somaQuantidade(id, status));
	        
			retorno.add(pedidos);
		}
		
		return retorno;
	}
	
	public List<ModelPedidos> listarRelatorioPorTempo(int id, int status, String dataInicial, String dataFinal) throws SQLException, ParseException {
		
		List<ModelPedidos> retorno = new ArrayList<ModelPedidos>();
		String sql = "SELECT * FROM pedidos WHERE status = ? AND usuario_pai_id = " + id + " AND dataentrega >= ? AND dataentrega <= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, status);
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil;
		
		dataUtil = formatador.parse(dataInicial);
		Date dataSql = new Date(dataUtil.getTime());
		dataUtil = formatador.parse(dataFinal);
		Date dataSql2 = new Date(dataUtil.getTime());
		statement.setDate(2, dataSql);
		statement.setDate(3, dataSql2);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()){
	        ModelPedidos pedidos = new ModelPedidos();
	        
	        String data = resultado.getString("dataentrega");
	        String[] parte = data.split(" ");
			
			String data2 = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			pedidos.setDataentrega(data2);

			String data3 = resultado.getString("datapedido");
			String[] parte2 = data3.split(" ");
			
			String data4 = transformarFormatoData(parte2[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
			pedidos.setDatapedido(data4);
	        
	        pedidos.setQuantidade(resultado.getLong("quantidade"));
	        pedidos.setId(resultado.getLong("id"));
	        pedidos.setValorTotal(resultado.getLong("valortotal"));
	        pedidos.setNome(resultado.getString("nome"));
	        
	        daoPedidos daopedido = new daoPedidos();
	        
	        ModelCancelamento cancelamento = daopedido.buscarCancelamento(resultado.getLong("id"));
	        pedidos.setDatacancelamento(cancelamento.getDatacancelamento());
	        
	        pedidos.setQuantidadeTotal(daopedido.somaQuantidadeRelatorio(id, status, dataInicial, dataFinal));
	        pedidos.setValores(daopedido.somaValoresRelatorio(id, status, dataInicial, dataFinal));
	        
	        System.out.println(daopedido.somaValores(id, status));
	        System.out.println(daopedido.somaQuantidade(id, status));
	        
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
	        pedido.setValorTotal(resultado.getLong("valortotal"));
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
	
	public static String transformarFormatoData(String dataString, String formatoOriginal, String novoFormato) {
		try {
			SimpleDateFormat formatoOriginalData = new SimpleDateFormat(formatoOriginal);
			SimpleDateFormat formatoNovoData = new SimpleDateFormat(novoFormato);

			java.util.Date data = formatoOriginalData.parse(dataString);

			String dataFormatada = formatoNovoData.format(data);

			return dataFormatada;
		} catch (ParseException e) {
			e.printStackTrace();
			
			return null;
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
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
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
			
			data = transformarFormatoData(parte[0], "yyyy-MM-dd", "dd/MM/yyyy");
			
			valores.add(valortotal);
			datas.add(data);
		}
		
		beanChart.setDatas(datas);
		beanChart.setValores(valores);
		
		return beanChart;
	}
}
