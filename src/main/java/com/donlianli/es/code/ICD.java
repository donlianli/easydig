package com.donlianli.es.code;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * ICD抽象对象
 * @author donlianli@126.com
 */
public class ICD implements Serializable{
	private static final long serialVersionUID = 6934803011248581109L;
	//疾病ID
	private int id;
	//疾病编码
	private String code;
	//疾病名称
	private String diseaseName;
	//疾病加拼音
	private String mergeName;
	//汉语拼音简拼
	private String pinyin;
	//是否恶心肿瘤
	private boolean isTherioma;
	//是否住院特殊病种
	private boolean isSpecialDisease;
	
	public ICD(BigDecimal id, String diseaseName, String code,
			String pinyin, String isTherioma, String isSpecialDisease) {
		this.id = id.intValue();
		this.diseaseName = diseaseName;
		this.code = code;
		this.pinyin = pinyin;
		if("是".equals(isTherioma)){
			this.isTherioma = true;
		}
		else {
			this.isTherioma = false;
		}
		
		if("是".equals(isSpecialDisease)){
			this.isSpecialDisease = true;
		}
		else {
			this.isSpecialDisease = false;
		}
		this.mergeName = diseaseName + "," + pinyin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public boolean isTherioma() {
		return isTherioma;
	}
	public void setTherioma(boolean isTherioma) {
		this.isTherioma = isTherioma;
	}
	public boolean isSpecialDisease() {
		return isSpecialDisease;
	}
	public void setSpecialDisease(boolean isSpecialDisease) {
		this.isSpecialDisease = isSpecialDisease;
	}
	public String getMergeName() {
		return mergeName;
	}
	public void setMergeName(String mergeName) {
		this.mergeName = mergeName;
	}
	
}
