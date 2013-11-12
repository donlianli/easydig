package com.donlian.lucene.wowo;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.donlian.jdbc.MysqlConnectionUtil;
import com.mysql.jdbc.StringUtils;

public class IndexManager {
	public static final String INDEX_DIR="d:/temp/wowo";
	public static Directory directory;
	/**
	 * 返回IndexWriter
	 * */
	public static IndexWriter getWriter() throws Exception {
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);// 设置标准分词器
		//启用非智能模式max_word模式分词更细
		Analyzer analyzer = new IKAnalyzer(false);									
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);// 设置IndexWriterConfig
		iwc.setRAMBufferSizeMB(30);//设置缓冲区大小
		return new IndexWriter(directory, iwc);
	}
	/**
	 * @indexPath 索引存放路径
	 * **/
	public static void add(String indexWriterPath) {
		IndexWriter writer = null;
		int n=0;
		long beginTime = System.currentTimeMillis();
		final int BATCH_SIZE=10000;
		try {
			directory = FSDirectory.open(new File(indexWriterPath));// 打开存放索引的路径
			writer = getWriter();
			
			Connection conn = MysqlConnectionUtil.getConnetcion();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from jeehe_goods");
			rs.setFetchSize(BATCH_SIZE);
			
			while(rs.next()){
				String goodsId = rs.getString(1);
				String catId = rs.getString(2);
				String saleCount = rs.getString(3);
				String actStartTime = rs.getString(4);
				String actEndTime = rs.getString(5);
				String goodsName = rs.getString(6);
				String shopPrice = rs.getString(7);
				String channelId = rs.getString(8);
				String isCash = rs.getString(9);
				Document doc = new Document();
				doc.add(new LongField("id", Long.parseLong(goodsId), Store.NO));
				doc.add(new IntField ("catId", Integer.parseInt(catId), Store.NO));
				doc.add(new IntField ("saleCount", Integer.parseInt(saleCount), Store.NO));
				doc.add(new LongField("actStartTime", Long.parseLong(actStartTime), Store.NO));
				doc.add(new LongField("actEndTime", Long.parseLong(actEndTime), Store.NO));
				doc.add(new TextField("goodsName", goodsName, Store.YES));
				String price = shopPrice.replace(".", "");
				int goodsPrice =0;
				if(!StringUtils.isNullOrEmpty(price)){
					goodsPrice =Integer.parseInt(price);
				}
				doc.add(new IntField("shopPrice", goodsPrice, Store.YES));
				doc.add(new IntField("channelId",  Integer.parseInt(channelId), Store.YES));
				doc.add(new IntField("isCash",  Integer.parseInt(isCash), Store.YES));
				writer.addDocument(doc);// 添加进写入流里
				n++;
				if(n%BATCH_SIZE==0){
					writer.commit();// 提交数据
					System.out.println(n+" commited");
				}
			}
			writer.commit();// 提交数据
			writer.forceMerge(1);// 优化压缩段,大规模添加数据的时候建议，少使用本方法，会影响性能
			System.out.println("添加完成,数量："+n);
			long useTime = System.currentTimeMillis()-beginTime;
			StringBuilder sb = new StringBuilder();
			if(useTime>60*1000 && useTime<60*1000*60){
				//大于1分，小于1小时
				sb.append(useTime/1000*60*60).append("分");
			}
			else {
				sb.append(useTime);
			}
			System.out.println("使用时间："+sb);
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (writer != null) {
				try {
					writer.close();// 关闭流
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {
		add(INDEX_DIR);// 调用添加方法
	}
}
