package model;

public class ModelFornecimento {
	
	private int nome;
	private ModelProdutos produto_pai_id;
	private Long id;
	
	public int getNome() {
		return nome;
	}
	public void setNome(int nome) {
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
