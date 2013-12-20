package com.donlianli.es.oceandata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * 搜索模型
 * @author donlianli@126.com
 */
public class SearchModel implements Serializable{
	private static final long serialVersionUID = -3896661433277748421L;
	/**
	 * 唯一标识
	 */
	private String oid;
	
	private String firstKey;
	private String secondKey;
	private String thirdKey;
	/**
	 * 所属分类
	 */
	private List<Integer> catIds;
	/**
	 * 数据对应子表
	 */
	private String tableName;
	/**
	 * 上次更新时间
	 */
	private Date updateTime;
	/**
	 * 搜索结果是否可见
	 * （筛选条件)
	 */
	private Integer isShow;
	/**
	 * 搜索结果顺序
	 */
	private Long order;
	/**
	 * 搜索展示名称
	 */
	private String title;
	/**
	 * 搜索结果展示名称补充
	 */
	private String mainDesc;
	/**
	 * 搜索结果跳转的url
	 */
	private String titleUrl;
	/**
	 * 搜索结果被点击的次数
	 */
	private Long clickCount;
	
	public SearchModel(String oid2, String firstKey2, String secondKey2,
			String thirdKey2, String catId, String tableName2,
			BigDecimal isShow2, BigDecimal order2, Timestamp update,
			String mainDesc2, String mainTitle2, BigDecimal clickCount2) {
		this.oid = oid2;
		this.firstKey = firstKey2;
		this.secondKey = secondKey2;
		this.thirdKey = thirdKey2;
		this.catIds = Arrays.asList(Integer.valueOf(catId));
		this.tableName = tableName2;
		this.isShow = isShow2.intValue();
		this.order = order2.longValue();
		this.updateTime = update;
		this.mainDesc = mainDesc2;
		this.title = mainTitle2;
		this.clickCount = clickCount2.longValue();
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getFirstKey() {
		return firstKey;
	}
	public void setFirstKey(String firstKey) {
		this.firstKey = firstKey;
	}
	public String getSecondKey() {
		return secondKey;
	}
	public void setSecondKey(String secondKey) {
		this.secondKey = secondKey;
	}
	public String getThirdKey() {
		return thirdKey;
	}
	public void setThirdKey(String thirdKey) {
		this.thirdKey = thirdKey;
	}
	public List<Integer> getCatIds() {
		return catIds;
	}
	public void setCatIds(List<Integer> catIds) {
		this.catIds = catIds;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMainDesc() {
		return mainDesc;
	}
	public void setMainDesc(String mainDesc) {
		this.mainDesc = mainDesc;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public String getTitleUrl() {
		return titleUrl;
	}
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
	public Long getClickCount() {
		return clickCount;
	}
	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
