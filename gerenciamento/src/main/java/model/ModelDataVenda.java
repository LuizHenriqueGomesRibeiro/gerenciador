package model;

public class ModelDataVenda {
	private Long id;
	private ModelUsuarios usuario_pai_id;
	private String datavenda;
	private int valortotal;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ModelUsuarios getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelUsuarios usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
	public String getDatavenda() {
		return datavenda;
	}
	public void setDatavenda(String datavenda) {
		this.datavenda = datavenda;
	}
	public int getValortotal() {
		return valortotal;
	}
	public void setValortotal(int valortotal) {
		this.valortotal = valortotal;
	}
	
	
}
