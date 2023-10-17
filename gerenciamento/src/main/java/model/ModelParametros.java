package model;

public class ModelParametros {
	private String quantidade;
	private String dataPedido;
	private String dataEntrega;
	private String dataVenda;
	private Long id_produto;
	private Long id_fornecedor;
	private int status;
	private ModelUsuarios usuario;
	private Long valorTotal;
	
	public Long getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Long valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}
	public ModelUsuarios getUsuario() {
		return usuario;
	}
	public void setUsuario(ModelUsuarios usuario) {
		this.usuario = usuario;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Long getId_produto() {
		return id_produto;
	}
	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}
	public Long getId_fornecedor() {
		return id_fornecedor;
	}
	public void setId_fornecedor(Long id_fornecedor) {
		this.id_fornecedor = id_fornecedor;
	}
}
