package model;

import java.util.Date;

public class ModelPedidos {
	private ModelFornecimento fornecedor_pai_id;
	private Long id;
	private String datapedido;
	private String dataentrega;
	private Long quantidade;
	private Long valor;
	private Long valorTotal;
	
	public ModelFornecimento getFornecedor_pai_id() {
		return fornecedor_pai_id;
	}
	public void setFornecedor_pai_id(ModelFornecimento fornecedor_pai_id) {
		this.fornecedor_pai_id = fornecedor_pai_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDataPedido() {
		return datapedido;
	}
	public void setDataPedido(String dataPedido) {
		this.datapedido = dataPedido;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	public Long getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Long valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getDataEntrega() {
		return dataentrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataentrega = dataEntrega;
	}
}
