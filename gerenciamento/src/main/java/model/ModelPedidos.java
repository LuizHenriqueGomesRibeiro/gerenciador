package model;

import DAO.DaoGenerico;
import DAO.daoProdutos;

public class ModelPedidos {
	private ModelFornecimento fornecedor_pai_id;
	private int id;
	private String datapedido;
	private String datacancelamento;
	private String dataentrega;
	private Long quantidade;
	private Long valor;
	private Long valorTotal;
	private String nome;
	private int valores;
	private int quantidadeTotal;
	private ModelUsuarios usuario_pai_id;
	
	public ModelPedidos setPedido(ModelFornecimento fornecedor, ModelParametros parametros, ModelUsuarios usuario, ModelPedidos modelPedidos) {
		
		DaoGenerico dao = new DaoGenerico();
		daoProdutos daoproduto = new daoProdutos();
		
		modelPedidos.setFornecedor_pai_id(fornecedor);
		modelPedidos.setQuantidade(Long.parseLong(dao.tirarPonto(parametros.getQuantidade())));
		modelPedidos.setDatapedido(parametros.getDataPedido());
		modelPedidos.setDataentrega(dao.plusDias(parametros.getDataPedido(), fornecedor.getTempoentrega()));
		modelPedidos.setValor(fornecedor.getValor());
		modelPedidos.setNome(daoproduto.consultaProduto(parametros.getId_produto(), usuario.getId()).getNome());
		modelPedidos.setUsuario_pai_id(usuario);
		
		return modelPedidos;
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
			+ quantidade + ", valor=" + valor + ", valorTotal=" + valorTotal + ", nome=" + nome + "]";
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
