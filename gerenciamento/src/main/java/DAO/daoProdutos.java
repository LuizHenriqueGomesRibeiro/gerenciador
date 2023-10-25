package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import conexao.conexao;
import model.ModelProdutos;

public class daoProdutos extends DAOComum{
	
	private Connection connection;
	DAOFerramentas dao = new DAOFerramentas();
	daoLogin daoLogin = new daoLogin();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	
	public daoProdutos() {
		connection = conexao.getConnection();
	}
	
	public void alternarProduto(ModelProdutos modelProduto) throws SQLException {
		if(modelProduto.isNovo()) {
			gravarProduto(sqlprodutos.gravaProduto(modelProduto.getNome(), modelProduto.getUsuario_pai_id().getId()));
		}else {
			atualizarProduto(sqlprodutos.atualizaProduto(modelProduto));
		}
	}
	
	public void gravarProduto(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
	}
	
	public void excluirProduto(Long id) throws SQLException {
		String sql = "DELETE FROM produtos WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
	
	public ModelProdutos consultarProduto(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadoConsultarProduto(resultado, new ModelProdutos());
	}
	
	public ModelProdutos resultadoConsultarProduto(ResultSet resultado, ModelProdutos modelProduto) throws SQLException {
		while (resultado.next()) {
			modelProduto.setId(id(resultado));
			modelProduto.setNome(nome(resultado));
			modelProduto.setPreco(preco(resultado));
			modelProduto.setQuantidade(quantidade(resultado));
			modelProduto.setValorTotal(preco(resultado) * quantidade(resultado));
		}
		return modelProduto;
	}
	
	public void atualizarProduto(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}

	public List<ModelProdutos> listarProdutos(String sql, int id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		return resultadosListagem(resultado, id);
	}
	
	public List<ModelProdutos> resultadosListagem(ResultSet resultado, int id) throws SQLException {
		List<ModelProdutos> retorno = new ArrayList<ModelProdutos>();
		while(resultado.next()){
			ModelProdutos produtos = new ModelProdutos();
	        produtos.setId(id(resultado));
			produtos.setQuantidade(quantidade(resultado));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(id));
			produtos.setNome(nome(resultado));
			retorno.add(produtos);
			alternarSomaValores(produtos, id(resultado));
			alternarSomaQuantidade(produtos, id(resultado));
		}
		return retorno;
	}
	
	public void alternarSomaValores(ModelProdutos produtos, Long id) throws SQLException {
		if(somaValores(sqlpedidos.somaValoresPedidoProdutoId(id, 0)) == 0) {
			produtos.setValorTotalString("Sem valores");
        }else {
        	produtos.setValorTotalString(dao.converterIntegerDinheiro(somaValores(sqlpedidos.somaValoresPedidoProdutoId(id, 0))));
        }
	}
	
	public void alternarSomaQuantidade(ModelProdutos produtos, Long id) throws SQLException {
		if(somaQuantidade(sqlpedidos.somaValoresPedidoProdutoId(id, 0)) == 0) {
        	produtos.setQuantidadePedidaString("Sem quantidades");
        }else {
        	produtos.setQuantidadePedidaString(dao.colocarPonto(String.valueOf(somaQuantidade(sqlpedidos.somaQuantidadePedidoProdutId(id, 0)))) + " unidades");
        }
	}
	
	public int somaProdutos(int usuario_pai_id) throws Exception{
		String sql = "SELECT SUM(valortotal) FROM pedidos WHERE usuario_pai_id = " + usuario_pai_id + " AND status = " + 0;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
		    int soma = resultado.getInt(1);
		    return soma;
		}else {
			return -1;
		}
	}
	
	public int consultaProdutosPaginas(int usuario) throws Exception {
		String sql = "SELECT count(1) AS total FROM produtos WHERE usuario_pai_id = " + usuario;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		resultado.next();
		
		Double total = resultado.getDouble("total");
		Double porPagina = 10.0;
		Double pagina = total / porPagina;
		Double resto = total % porPagina;
		
		if(resto>0) {
			pagina++;
		}
		
		return pagina.intValue();
	}
	
	public void adicionaProdutoCaixa(Long id, int quantidade) throws SQLException {
		String sql = "UPDATE produtos SET quantidade = quantidade + " + quantidade + " WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
		connection.commit();
	}
}	

