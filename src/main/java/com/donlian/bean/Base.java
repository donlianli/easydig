package com.donlian.bean;

import java.io.Serializable;

public class Base implements Serializable {
	private static final long serialVersionUID = -1338926601310410513L;
	public int oid;
	public int id;
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
