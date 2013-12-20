package com.donlianli.es.code;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;
import com.donlianli.es.db.DatabaseUtils;

public class ICDManager {
	
	public static void main(String[] argvs){
		ICDManager manager = new ICDManager();
		manager.indexDataDirect();
	}
	/**
	 * 直接将数据初始化到ES中
	 * 不创建mapping
	 */
	private void indexDataDirect() {
		List<ICD> icdList = getIcdListFromDB();	
		System.out.println(" get icd from db finish,size:" + icdList.size());
		bulkIndex(icdList);
	}
	
	private void bulkIndex(List<ICD> icdList) {
		Client client = ESUtils.getCodeClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		long b = System.currentTimeMillis();
		for(int i=0,l=icdList.size();i<l;i++){
			//业务对象
			ICD icd = icdList.get(i);
			String json = ESUtils.toJson(icd);
			IndexRequestBuilder indexRequest = client.prepareIndex("code","icd")
	        .setSource(json).setId(String.valueOf(icd.getId()));
			//添加到builder中
			bulkRequest.add(indexRequest);
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}
		long useTime = System.currentTimeMillis()-b;
		System.out.println("useTime:" + useTime);
	}
	private List<ICD> getIcdListFromDB() {
		Connection conn = DatabaseUtils.getOracleConnection();
		String sql = "select * from icd_11";
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ICD> list = new ArrayList<ICD>();
		try{
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()){
				BigDecimal id = rs.getBigDecimal("ID");
				String diseaseName = rs.getString("DISEASE_NAME");
				String code = rs.getString("CODE");
				String pinyin = rs.getString("PINYIN");
				String isTherioma = rs.getString("THERIOMA_FLAG");
				String isSpecialDisease = rs.getString("OTHER_FLAG");
				
				list.add(new ICD(id,diseaseName,code,pinyin,isTherioma,isSpecialDisease));
			}
			
			return list;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
			if(rs!= null){
				rs.close();
			}
			if(st!= null){
				st.close();
			}
			conn.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
