package model;

public class ModelProdutos {
	
	private int quantidade;
	private int preco;
	private Long id;
	private String nome;
	private ModelUsuarios usuario_pai_id;
	private String precoString;
	private int valorTotal;
	private String valorTotalString;
	private String dataentrega;
	private int quantidadePedida;
	private String quantidadePedidaString;
	
	public int getQuantidadePedida() {
		return quantidadePedida;
	}
	public void setQuantidadePedida(int quantidadePedida) {
		this.quantidadePedida = quantidadePedida;
	}
	public String getQuantidadePedidaString() {
		return quantidadePedidaString;
	}
	public void setQuantidadePedidaString(String quantidadePedidaString) {
		this.quantidadePedidaString = quantidadePedidaString;
	}
	public String getDataentrega() {
		return dataentrega;
	}
	public void setDataentrega(String dataentrega) {
		this.dataentrega = dataentrega;
	}
	public String getValorTotalString() {
		return valorTotalString;
	}
	public void setValorTotalString(String valorTotalString) {
		this.valorTotalString = valorTotalString;
	}
	public int getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getPrecoString() {
		return precoString;
	}
	public void setPrecoString(String precoString) {
		this.precoString = precoString;
	}
	public boolean isNovo() {
		if (this.id == null) {
			return true;
		} else if (this.id != null && this.id > 0) {
			return false;
		}
		return id == null;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getPreco() {
		return preco;
	}
	public void setPreco(int preco) {
		this.preco = preco;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ModelUsuarios getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelUsuarios usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
}

