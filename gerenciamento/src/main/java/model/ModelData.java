package model;

public class ModelData {
	private Long id;
	private ModelUsuarios usuario_pai_id;
	private String datavenda;
	private Long valortotal;
	
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
	public Long getValortotal() {
		return valortotal;
	}
	public void setValortotal(Long valortotal) {
		this.valortotal = valortotal;
	}
	
	
}