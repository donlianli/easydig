package com.donlian.httpclient.collector;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import com.mysql.jdbc.StringUtils;

public class ProxyCollector {
	public static final String url = "http://proxy.com.ru/list_";

	public static void main(String[] args) {

		try {
			int insert = 0;
			int failedCount =0;
			System.out.println("begin");
			//78
			for(int page=58;page<78;page++){
				String pageurl = url+page+".html";
				Parser parser = new Parser(
						(HttpURLConnection) (new URL(pageurl)).openConnection());
				/**
				 * <table width="100%" cellspacing="0" 
				 * cellpadding="0" border="1"
				 *  style="TABLE-LAYOUT: fixed;border-collapse: collapse"
				 *   bordercolor="#CCCCCC">
				 */
				NodeFilter innerFilter = new TagNameFilter ("TABLE");
				//NodeFilter childFilter = new HasChildFilter(innerFilter);
				NodeFilter style = new HasAttributeFilter("style","TABLE-LAYOUT: fixed;border-collapse: collapse");
				NodeFilter filter1 = new AndFilter(innerFilter,style);
				NodeFilter width = new HasAttributeFilter("bordercolor","#CCCCCC");
				NodeFilter filter = new AndFilter(width,filter1);
				NodeList nodes = parser.extractAllNodesThatMatch(filter); 
				Connection con = MysqlUtils.getMysqlConnection();
				
				PreparedStatement sta = con.prepareStatement("insert into free_proxy(ip,port,ip_desc) values(?,?,?)");
				
	            if(nodes!=null) {
	            	//System.out.println("size="+nodes.size());
	                //目标node
	                Node tablenode = (Node) nodes.elementAt(3);
	                NodeList trs = tablenode.getChildren();
	                for(int i=2;i<trs.size();i++){
	                	Node trNode = (Node) trs.elementAt(i);
	                    if(!StringUtils.isEmptyOrWhitespaceOnly(trNode.getText())){
	                    	 NodeList tds = trNode.getChildren();
	                    	 String ip = tds.elementAt(2).toPlainTextString();
	                    	 String port = tds.elementAt(3).toPlainTextString();
	                    	 String desc = tds.elementAt(5).toPlainTextString();
//	                    	 System.out.println(ip
//	                    			 +":"+port
//	                    			 +":"+desc);
	                    	 printFormat(ip,port,desc);
	                    	 //保存到数据库
//	                    	 try{
//	                    		 sta.setString(1, tds.elementAt(2).toPlainTextString());
//	                        	 sta.setInt(2, Integer.parseInt(tds.elementAt(3).toPlainTextString()));
//	                        	 sta.setString(3, tds.elementAt(5).toPlainTextString()); 
//	                        	 insert++;
//	                        	 sta.execute();
//	                    	 }
//	                    	 catch(Exception e){
//	                    		 e.printStackTrace();
//	                    		 failedCount++;
//	                    	 }
	                    }
	                }
	              
	            } 
	            
	            else {
	            	System.out.println("not find");
	            }
			}
			  System.out.println("insert="+insert+",failed="+failedCount);
			
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	private static void printFormat(String ip, String port, String desc) {
		// PROXY_MAP.put("61.163.55.3","9000");
		System.out.println("PROXY_MAP.put(\""+ip+"\""+","
				+ "\""+port
				+ "\");");
	}
	
}
