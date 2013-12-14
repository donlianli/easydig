package com.donlianli.es.code;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.donlianli.es.db.DatabaseUtils;

public class OracleLike {
	public static void main(String arf[]){
		Connection conn = DatabaseUtils.getOracleConnection();
		//无需分页，数量不多
		String sql = "select id, code, disease_name, pinyin, therioma_flag, tsbz_flag, other_flag"+
					" from icd_11 " +
						" where pinyin like '%高血压%' or pinyin" +
						" like '%高血压%' order by length(disease_name)";
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ICD> list = new ArrayList<ICD>();
		try{
			st = conn.prepareStatement(sql);
			long b = System.currentTimeMillis();
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
			long useTime = System.currentTimeMillis()-b;
			System.out.println("useTime:" + useTime);
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
	}
}
