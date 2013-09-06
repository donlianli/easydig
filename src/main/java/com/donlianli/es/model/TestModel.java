package com.donlianli.es.model;

import java.io.Serializable;
import java.util.List;
/**
 * 这个是为分组定义的一个模型
 * catIds通常为一对多的分类ID
 * @author donlian
 */
public class TestModel implements Serializable {
	private static final long serialVersionUID = 3174577828007649745L;
	//主ID
	private long id;
	//类型，为types之一
	private String type;
	/**
	 * 这里是一个列表
	 */
	private List<Integer> catIds;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Integer> getCatIds() {
		return catIds;
	}
	public void setCatIds(List<Integer> catIds) {
		this.catIds = catIds;
	}
}
