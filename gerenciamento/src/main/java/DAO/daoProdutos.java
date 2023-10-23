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

public class daoProdutos {
	
	private Connection connection;
	DaoGenerico dao = new DaoGenerico();
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
	
	public ModelProdutos consultaProduto(Long id_produto, int userLogado) throws SQLException {
		ModelProdutos modelProduto = new ModelProdutos();
		String sql = "SELECT*FROM produtos WHERE id = " + id_produto + " AND usuario_pai_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
			modelProduto.setId(resultado.getLong("id"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setPreco(resultado.getInt("preco"));
			modelProduto.setQuantidade(resultado.getInt("quantidade"));
			modelProduto.setValorTotal(resultado.getInt("preco") * resultado.getInt("quantidade"));
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
	        produtos.setId(resultado.getLong("id"));
			produtos.setQuantidade(resultado.getInt("quantidade"));
			produtos.setUsuario_pai_id(daoLogin.consultaUsuarioLogadoId(id));
			produtos.setNome(resultado.getString("nome"));
			retorno.add(produtos);
			alternarSomaValores(produtos, id);
			alternarSomaQuantidade(produtos, id);
		}
		return retorno;
	}
	
	public void alternarSomaValores(ModelProdutos produtos, int id) throws SQLException {
		System.out.println(sqlpedidos.somaValoresPedidoProdutoId(produtos.getId(), 0));
		if(dao.somaValores(sqlpedidos.somaValoresPedidoProdutoId(produtos.getId(), 0)) == 0) {
			produtos.setValorTotalString("Sem valores");
        }else {
        	produtos.setValorTotalString(dao.converterIntegerDinheiro(dao.somaValores(sqlpedidos.somaValoresPedidoProdutoId(produtos.getId(), 0))));
        }
	}
	
	public void alternarSomaQuantidade(ModelProdutos produtos, int id) throws SQLException {
		if(dao.somaQuantidade(sqlpedidos.somaValoresPedidoProdutoId(produtos.getId(), 0)) == 0) {
        	produtos.setQuantidadePedidaString("Sem quantidades");
        }else {
        	produtos.setQuantidadePedidaString(dao.colocarPonto(String.valueOf(dao.somaQuantidade(sqlpedidos.somaQuantidadePedidoProdutId(produtos.getId(), 0)))) + " unidades");
        }
	}
	
	public int somaProdutos(int usuario_pai_id) throws Exception{
		String sql = "SELECT SUM(valortotal) FROM pedidos WHERE usuario_pai_id = " + usuario_pai_id + " AND status = " + 0L;
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
	
	public ModelProdutos adicionaProdutoCaixa(Long id, int quantidade) throws SQLException {
		String sql = "UPDATE produtos SET quantidade = quantidade + " + quantidade + " WHERE id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ModelProdutos produto = new ModelProdutos();
		statement.executeUpdate();
		connection.commit();
		return produto;
	}
}	

