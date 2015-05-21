package com.donlian.test.easymock.param;

import java.io.Serializable;
import java.util.List;

public class Params implements Serializable{
	private static final long serialVersionUID = -4630422906851260484L;
	private int i;
	private String s;
	private List<Long> list;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public List<Long> getList() {
		return list;
	}
	public void setList(List<Long> list) {
		this.list = list;
	}
	
}
