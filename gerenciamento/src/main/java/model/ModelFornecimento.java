package model;

import java.sql.SQLException;

import DAO.DAOFornecimento;
import DAO.DAOProdutos;

public class ModelFornecimento {
	
	private String nome;
	private ModelProdutos produto_pai_id;
	private ModelUsuarios usuario_pai_id;
	private Long id;
	private Long tempoentrega;
	private int valor;
	
	public ModelUsuarios getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelUsuarios usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
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
