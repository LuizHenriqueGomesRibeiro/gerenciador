package model;

import java.sql.SQLException;

import DAO.daoFornecimento;
import DAO.daoProdutos;

public class ModelFornecimento {
	
	private String nome;
	private ModelProdutos produto_pai_id;
	private ModelUsuarios usuario_pai_id;
	private Long id;
	private Long tempoentrega;
	private Long valor;
	
	public ModelFornecimento setFornecedor(ModelPedidos pedido) throws SQLException {
		
		daoProdutos daoproduto = new daoProdutos();
		daoFornecimento daofornecimento = new daoFornecimento();
		
		ModelFornecimento fornecedor = new ModelFornecimento();
		fornecedor.setId(pedido.getId_fornecedor());
		fornecedor.setUsuario_pai_id(pedido.getUsuario_pai_id());
		fornecedor.setProduto_pai_id(daoproduto.consultaProduto(pedido.getId_produto(), pedido.getUsuario_pai_id().getId()));
		fornecedor = daofornecimento.consultarFornecedor(fornecedor);
		
		return fornecedor;
	}
	
	public ModelUsuarios getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelUsuarios usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	public Long getTempoentrega() {
		return tempoentrega;
	}
	public void setTempoentrega(Long tempoentrega) {
		this.tempoentrega = tempoentrega;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ModelProdutos getProduto_pai_id() {
		return produto_pai_id;
	}
	public void setProduto_pai_id(ModelProdutos produto_pai_id) {
		this.produto_pai_id = produto_pai_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
