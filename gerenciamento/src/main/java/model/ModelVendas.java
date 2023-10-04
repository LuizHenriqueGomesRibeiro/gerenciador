package model;

public class ModelVendas {
	private int id;
	private ModelProdutos produto_pai;
	private String dataentrega;
	private int valortotal;
	private int quantidade;
	private String nome;
	private int valores;
	private int quantidadeTotal;

	public int getValores() {
		return valores;
	}
	public void setValores(int valores) {
		this.valores = valores;
	}
	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ModelProdutos getProduto_pai() {
		return produto_pai;
	}
	public void setProduto_pai(ModelProdutos produto_pai) {
		this.produto_pai = produto_pai;
	}
	public String getDataentrega() {
		return dataentrega;
	}
	public void setDataentrega(String dataentrega) {
		this.dataentrega = dataentrega;
	}
	public int getValortotal() {
		return valortotal;
	}
	public void setValortotal(int valortotal) {
		this.valortotal = valortotal;
	}
}
