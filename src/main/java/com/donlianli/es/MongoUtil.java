package com.donlianli.es;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * 
 * @author donlian
 *
 */
public class MongoUtil {
	public static MongoClient mongoClient ;
	public static DB db; 
	static {
		try {
			mongoClient = new MongoClient( "192.168.1.106" );
			db = mongoClient.getDB( "code" );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static DBCollection getCollectionByName(String colName){
			DBCollection coll = db.getCollection(colName);
			return coll;
	}
	public static void main(String[] arg){
		getCollectionByName("test");
	}
}
