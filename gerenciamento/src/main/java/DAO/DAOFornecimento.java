package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import DAO.SQL.SQLFornecimento;
import conexao.conexao;
import model.ModelFornecimento;
import model.ModelProdutos;

public class DAOFornecimento extends DAOComum{
	private Connection connection;
	SQLFornecimento sqlfornecimento = new SQLFornecimento();
	
	public DAOFornecimento(){
		connection = conexao.getConnection();
	}
	
	public void gravarNovoFornecedor(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public void excluirFornecedor(Long id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.exclui(id));
		statement.executeUpdate();
		connection.commit();
	}
	
	public ModelFornecimento consultarFornecedor(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return lerResultadoConsultarFornecedor(resultado);
	}
	
	public ModelFornecimento lerResultadoConsultarFornecedor(ResultSet resultado) throws SQLException{
		ModelFornecimento modelFornecimento = new ModelFornecimento();
		while (resultado.next()) {
			modelFornecimento = setFornecedor(resultado);
		}
		return modelFornecimento;
	}
	
	public List<ModelFornecimento> listarFornecedores(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return lerResultadoListarFornecedores(resultado);
	}
	
	public List<ModelFornecimento> lerResultadoListarFornecedores(ResultSet resultado) throws SQLException{
		List<ModelFornecimento> retorno = new ArrayList<ModelFornecimento>();
		while(resultado.next()){
			ModelFornecimento modelFornecimento = new ModelFornecimento();
			modelFornecimento = setFornecedor(resultado);
			retorno.add(modelFornecimento);
		}
		return retorno;
	}
	
	public ModelFornecimento setFornecedor(ResultSet resultado) throws SQLException {
		ModelFornecimento modelFornecimento = new ModelFornecimento();
		modelFornecimento.setId(id(resultado));
		modelFornecimento.setTempoentrega(tempoentrega(resultado));
		modelFornecimento.setNome(nome(resultado));
		modelFornecimento.setValor(valor(resultado));
		return modelFornecimento;
	}
	
	public Double mediaValoresFornecimento(Long produto) throws Exception{
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.media(produto));
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return resultado.getDouble("media");
	}
}
