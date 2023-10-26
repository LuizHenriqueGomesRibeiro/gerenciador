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

public class daoFornecimento extends DAOComum {

	private Connection connection;
	SQLFornecimento sqlfornecimento = new SQLFornecimento();
	
	public daoFornecimento(){
		connection = conexao.getConnection();
	}
	
	public void gravarNovoFornecedor(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public List<ModelFornecimento> listarFornecedores(Long id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.lista(id));
		ResultSet resultado = statement.executeQuery();
		return lerResultadoListarFornecedores(resultado, new ArrayList<ModelFornecimento>());
	}
	
	public List<ModelFornecimento> lerResultadoListarFornecedores(ResultSet resultado, List<ModelFornecimento> retorno) throws SQLException{
		while(resultado.next()){
			ModelFornecimento fornecedores = new ModelFornecimento();
			fornecedores.setId(id(resultado));
			fornecedores.setNome(nome(resultado));
			fornecedores.setTempoentrega(tempoentrega(resultado));
			fornecedores.setValor(valor(resultado));
			retorno.add(fornecedores);
		}
		return retorno;
	}
	
	public ModelFornecimento consultarFornecedor(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return lerResultadoConsultarFornecedor(resultado, new ModelFornecimento());
	}
	
	public ModelFornecimento lerResultadoConsultarFornecedor(ResultSet resultado, ModelFornecimento modelFornecimento) throws SQLException{
		while (resultado.next()) {
			modelFornecimento.setId(id(resultado));
			modelFornecimento.setTempoentrega(tempoentrega(resultado));
			modelFornecimento.setNome(nome(resultado));
			modelFornecimento.setValor(valor(resultado));
			modelFornecimento.setProduto_pai_id(modelFornecimento.getProduto_pai_id());		
		}
		return modelFornecimento;
	}
	
	public void excluirFornecedor(Long id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.exclui(id));
		statement.executeUpdate();
		connection.commit();
	}
	
	public Double mediaValoresFornecimento(Long produto) throws Exception{
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.media(produto));
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return resultado.getDouble("media");
	}
}
