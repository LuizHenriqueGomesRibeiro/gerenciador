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
	DAOLogin daoLogin = new DAOLogin();
	
	public DAOFornecimento(){
		connection = conexao.getConnection();
	}
	
	public String verificarHaNomeFornecedor(String sql) throws SQLException {
		String validadorFornecedor;
		if(consultarFornecedor(sql).getId() != null) {
			validadorFornecedor = "haNome";
		}else {
			validadorFornecedor = "naoHaNome";
		}
		return json(validadorFornecedor);
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
		modelFornecimento.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(usuario_pai_id(resultado)));
		modelFornecimento.setTempoentrega(tempoentrega(resultado));
		modelFornecimento.setNome(nome(resultado));
		modelFornecimento.setValor(valor(resultado));
		return modelFornecimento;
	}
	
	public Double mediaValoresFornecimento(Long produto) throws Exception{
		PreparedStatement statement = connection.prepareStatement(sqlfornecimento.media(produto));
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return mediaValoresFornecimentoLimparDouble(resultado);
	}
	
	public Double mediaValoresFornecimentoLimparDouble(ResultSet resultado) throws Exception {
		String media = resultado.getString("media");
		int indexPonto = media.indexOf(".");
        Double mediaLimpa = Double.parseDouble(media.substring(0, indexPonto));
		return mediaLimpa;
	}
}
