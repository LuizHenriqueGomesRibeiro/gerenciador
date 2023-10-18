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

public class daoFornecimento {

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
		return objetosListarFornecedores(resultado);
	}
	
	public List<ModelFornecimento> objetosListarFornecedores(ResultSet resultado) throws SQLException{
		List<ModelFornecimento> retorno = new ArrayList<ModelFornecimento>();
		ModelFornecimento fornecedores = new ModelFornecimento();
		return lerResultadoListarFornecedores(retorno, fornecedores, resultado);
	}
	
	public List<ModelFornecimento> lerResultadoListarFornecedores(List<ModelFornecimento> retorno, ModelFornecimento fornecedores, ResultSet resultado) throws SQLException{
		while(resultado.next()){
			fornecedores.setId(resultado.getLong("id"));
			fornecedores.setNome(resultado.getString("nome"));
			fornecedores.setTempoentrega(resultado.getLong("tempoentrega"));
			fornecedores.setValor(resultado.getLong("valor"));
			retorno.add(fornecedores);
		}
		return retorno;
	}
	
	public ModelFornecimento consultarFornecedor(ModelFornecimento modelFornecedor) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.consulta(modelFornecedor.getId(), modelFornecedor.getProduto_pai_id().getId()));
		ResultSet resultado = statement.executeQuery();
		return lerResultadoConsultarFornecedor(resultado, modelFornecedor);
	}
	
	public ModelFornecimento lerResultadoConsultarFornecedor(ResultSet resultado, ModelFornecimento modelFornecimento) throws SQLException{
		while (resultado.next()) {
			modelFornecimento.setId(resultado.getLong("id"));
			modelFornecimento.setTempoentrega(resultado.getLong("tempoentrega"));
			modelFornecimento.setNome(resultado.getString("nome"));
			modelFornecimento.setValor(resultado.getLong("valor"));
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
