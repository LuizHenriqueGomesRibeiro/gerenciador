package model;

import java.util.Date;

public class ModelFornecimento {
	
	private String nome;
	private ModelProdutos produto_pai_id;
	private Long id;
	private Long tempoentrega;
	private Long valor;
	
	
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