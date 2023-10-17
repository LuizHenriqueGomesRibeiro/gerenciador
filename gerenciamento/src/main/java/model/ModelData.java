package model;

import java.sql.SQLException;

import DAO.daoPedidos;
import DAO.SQL.SQLPedidos;
import jakarta.servlet.http.HttpServletRequest;

public class ModelData {
	private Long id;
	private ModelUsuarios usuario_pai_id;
	private String datavenda;
	private Long valortotal;
	
	public ModelData getModelData(ModelParametros parametros, HttpServletRequest request) throws NumberFormatException, SQLException {
		daoPedidos daopedidos = new daoPedidos();
		SQLPedidos sqlpedidos = new SQLPedidos();
		ModelData modelData = new ModelData();
		modelData.setDatavenda(parametros.getDataVenda());
		modelData.setUsuario_pai_id(parametros.getUsuario());
		modelData.setValortotal(daopedidos.listarPedidos(sqlpedidos.procuraPedido(Long.parseLong(request.getParameter("id")))).get(0).getValorTotal());
		return modelData;
	}
	
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
