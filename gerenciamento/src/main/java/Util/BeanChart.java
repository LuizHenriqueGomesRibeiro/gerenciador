package Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanChart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> datas = new ArrayList<String>();
	List<Double> valores = new ArrayList<Double>();
	
	public List<String> getDatas() {
		return datas;
	}
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
	public List<Double> getValores() {
		return valores;
	}
	public void setValores(List<Double> valores) {
		this.valores = valores;
	}
	
	
}
