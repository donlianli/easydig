package com.donlian.jdk;

import java.io.Serializable;

public class BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12312L;
	private Integer intId;

	public Integer getIntId() {
		return intId;
	}

	public void setIntId(Integer intId) {
		this.intId = intId;
	}
	
}
