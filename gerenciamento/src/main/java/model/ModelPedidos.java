package model;


public class ModelPedidos {
	private ModelFornecimento fornecedor_pai_id;
	private int id;
	private String datapedido;
	private String datacancelamento;
	private String dataentrega;
	private int quantidade;
	private int valor;
	private int valorTotal;
	private String nome;
	private int valores;
	private int quantidadeTotal;
	private ModelUsuarios usuario_pai_id;
	private ModelProdutos produto_pai_id;
	private Long id_fornecedor;
	private Long id_produto;
	
	
	
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
	public ModelProdutos getProduto_pai_id() {
		return produto_pai_id;
	}
	public void setProduto_pai_id(ModelProdutos produto_pai_id) {
		this.produto_pai_id = produto_pai_id;
	}
	public ModelUsuarios getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelUsuarios usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
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

	@Override
	public String toString() {
		return "ModelPedidos [fornecedor_pai_id=" + fornecedor_pai_id + ", id=" + id + ", datapedido=" + datapedido
				+ ", datacancelamento=" + datacancelamento + ", dataentrega=" + dataentrega + ", quantidade="
				+ quantidade + ", valor=" + valor + ", valorTotal=" + valorTotal + ", nome=" + nome + ", valores="
				+ valores + ", quantidadeTotal=" + quantidadeTotal + ", usuario_pai_id=" + usuario_pai_id
				+ ", produto_pai_id=" + produto_pai_id + ", id_fornecedor=" + id_fornecedor + ", id_produto="
				+ id_produto + "]";
	}
	
	public String getDatacancelamento() {
		return datacancelamento;
	}
	public void setDatacancelamento(String datacancelamento) {
		this.datacancelamento = datacancelamento;
	}
	public ModelFornecimento getFornecedor_pai_id() {
		return fornecedor_pai_id;
	}
	public void setFornecedor_pai_id(ModelFornecimento fornecedor_pai_id) {
		this.fornecedor_pai_id = fornecedor_pai_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDatapedido() {
		return datapedido;
	}
	public void setDatapedido(String datapedido) {
		this.datapedido = datapedido;
	}
	public String getDataentrega() {
		return dataentrega;
	}
	public void setDataentrega(String dataentrega) {
		this.dataentrega = dataentrega;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public int getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
}
