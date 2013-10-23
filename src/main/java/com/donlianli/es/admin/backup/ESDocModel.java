package com.donlianli.es.admin.backup;

import java.io.Serializable;

public class ESDocModel implements Serializable {
	//index name
	private String i;
	//type name
	private String t;
	//id
	private String id;
	//object source
	private String s;
	public ESDocModel(String indexName,String typeName,String docId,String docSource){
		this.i=indexName;
		this.t=typeName;
		this.id=docId;
		this.s=docSource;
	}
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i = i;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
}
