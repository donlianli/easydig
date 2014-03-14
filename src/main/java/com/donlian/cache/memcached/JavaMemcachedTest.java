package com.donlian.cache.memcached;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class JavaMemcachedTest {
	private static MemCachedClient memCachedClient = new MemCachedClient();
	private List<Map<String,String>> list;
	// set up connection pool once at class load
	static {
		// server list and weights
		String[] servers =
			{
			  "192.168.1.105:11212",
			};

		Integer[] weights = { 10 };
		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();
		pool.setInitConn(10);  
        pool.setMinConn(5);  
        pool.setMaxConn(250);  
		// set the servers and the weights
		pool.setServers( servers );
		pool.setWeights( weights );

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don't set a connect timeout
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setSocketConnectTO( 0 );
		// initialize the connection pool
		pool.initialize();
	}
	private void initList() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "192.168.1.105" );
		DB db =mongoClient.getDB( "code" );
		DBCollection coll = db.getCollection("cell_phone_ownership");
		DBCursor cursor = coll.find();
		list = new ArrayList<Map<String,String>>(215647);
		long begin = System.currentTimeMillis();
		while(cursor.hasNext()){
			DBObject dbo = cursor.next();
			Map<String,String> map = new HashMap<String,String>();
			map.put("prefix", (String)dbo.get("prefix"));
			map.put("full_zone_name",  (String)dbo.get("full_zone_name"));
			list.add(map);
		}
		long end = System.currentTimeMillis();
		long duration = end-begin;
		System.out.println("查询mongodb使用：" + (duration)+"毫秒");
	}
	private void setMemcached(List<Map<String, String>> list) {
		int count=0;
		for(Map<String,String> code : list){
			String key= code.get("prefix");
			memCachedClient.set(key,code.get("full_zone_name"));
			Object val = memCachedClient.get(key);
			if(val == null){
				System.out.println("key " + key + " not get");
			}
			count++;
			if(count%1000==0){
				System.out.println("set " + count);
			}
		}
	}
	public void test () throws UnknownHostException{
		initList();
		System.out.println("begin get");
		long begin = System.currentTimeMillis();
		Thread[] tt = new Thread[200];
		for(int i=0;i<200;i++){
			tt[i] = new Thread(new Runnable(){
				public void run(){
					int count =0;
					for(Map<String,String> code : list){
						String key = code.get("prefix");
						String s = (String)memCachedClient.get(key);
						if(s==null){
							System.out.println("missed get ");
						}
						count++;
						if(count%1000==0){
							System.out.println("get " + count);
						}
					}
				}
			});
			tt[i] .start();
		}
		for(int i=0;i<10;i++){
			try{
				tt[i].join();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("over!");
		System.out.println("use time :" +(end-begin) + " ms");
	}
	public static void main(String[] args) throws IOException {
		new JavaMemcachedTest().test();
	}
}
