package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DAO.SQL.SQLProdutos;
import DAO.SQL.SQLRelatorio;
import conexao.conexao;
import model.ModelProdutos;
import model.ModelVendas;

public class daoVendas extends DAOComum{
	private Connection connection;
	daoProdutos daoproduto = new daoProdutos();
	SQLProdutos sqlproduto = new SQLProdutos();
	SQLRelatorio sqlRelatorio = new SQLRelatorio();
	
	public daoVendas(){
		connection = conexao.getConnection();
	}
	
	public void gravarDatas(String dataFinal, int usuario_pai_id) throws SQLException, ParseException {
        String dataInicial = buscarData(sqlRelatorio.buscaData(usuario_pai_id));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(converterDatas(dataInicial)));
        while (!calendar.getTime().after(stringToDate(dataFinal))) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(calendar.getTime());
            efetivarGravacao(formattedDate, usuario_pai_id);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
	}
	
	public String buscarData(String sql) throws SQLException, ParseException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		String data = null;
		while(resultado.next()) {
			data = datavenda(resultado);
		}
		return data;
	}
	
	public void efetivarGravacao(String formattedDate, int usuario_pai_id) throws SQLException {
		String sql = "INSERT INTO datavenda (datavenda, usuario_pai_id) VALUES (" + formattedDate + ", " + usuario_pai_id + ")";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
	}
	
	public void gravarVenda(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public List<ModelVendas> listarVendas(String sql, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadoListarVendas(resultado, sqlSomaValores, sqlSomaQuantidade);
	}
	
	public List<ModelVendas> resultadoListarVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException{
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		while(resultado.next()) {
			ModelVendas vendas = setVendas(resultado, sqlSomaValores, sqlSomaQuantidade);
			retorno.add(vendas);
		}
		return retorno;
	}
	
	public ModelVendas setVendas(ResultSet resultado, String sqlSomaValores, String sqlSomaQuantidade) throws SQLException, ParseException {
		ModelVendas vendas = new ModelVendas();
		vendas.setNome(produto(resultado).getNome());
		vendas.setId(id(resultado));
		vendas.setDataentrega(dataentrega(resultado));
		vendas.setValortotal(valortotal(resultado));
		vendas.setProduto_pai(produto(resultado));
		vendas.setQuantidade(quantidade(resultado));
		vendas.setValores(somaValores(sqlSomaValores));
		vendas.setQuantidadeTotal(somaQuantidade(sqlSomaQuantidade));
		return vendas;
	}
	
	public ModelProdutos produto(ResultSet resultado) throws SQLException {
		ModelProdutos produto = daoproduto.consultarProduto(sqlproduto.consultaProduto(produtos_pai_Id(resultado)));
		return produto;
	}
	
	
    
}
