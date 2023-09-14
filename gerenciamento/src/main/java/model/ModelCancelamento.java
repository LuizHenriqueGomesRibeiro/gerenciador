package model;

public class ModelCancelamento {
	private int id;
	private String datacancelamento;
	private ModelPedidos pedido_pai_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDatacancelamento() {
		return datacancelamento;
	}
	public void setDatacancelamento(String datacancelamento) {
		this.datacancelamento = datacancelamento;
	}
	public ModelPedidos getPedido_pai_id() {
		return pedido_pai_id;
	}
	public void setPedido_pai_id(ModelPedidos pedido_pai_id) {
		this.pedido_pai_id = pedido_pai_id;
	}
	
}
