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
	List<Long> valores = new ArrayList<Long>();
	
	public List<String> getDatas() {
		return datas;
	}
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
	public List<Long> getValores() {
		return valores;
	}
	public void setValores(List<Long> valores) {
		this.valores = valores;
	}
	
	
}
