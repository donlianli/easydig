package com.donlian.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Lucene的演示Demo类
 * **/
public class CommonLuceneBasic {
	/**
	 * 抽象的父类文件夹
	 * */
	public static Directory directory;
	

	/**
	 * 返回IndexWriter
	 * */
	public static IndexWriter getWriter() throws Exception {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_32);// 设置标准分词器
																	// ,默认是一元分词
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_32,
				analyzer);// 设置IndexWriterConfig
		// iwc.setRAMBufferSizeMB(3);//设置缓冲区大小
		return new IndexWriter(directory, iwc);
	}

	/**
	 * @indexPath 索引存放路径
	 * **/
	public static void add(String indexWriterPath) {
		IndexWriter writer = null;
		try {
			directory = FSDirectory.open(new File(indexWriterPath));// 打开存放索引的路径
			writer = getWriter();
			Document doc = new Document();
			doc.add(new StringField("id", "1", Store.YES));// 存储
			doc.add(new StringField("name", "羊蝎子火锅", Store.YES));// 存储
			doc.add(new StringField("content", "我说我的眼里只有你,new version", Store.YES));// 存储
			writer.addDocument(doc);// 添加进写入流里
			
			doc = new Document();
			doc.add(new StringField("id", "12", Store.YES));// 存储
			doc.add(new StringField("name", "huoguo", Store.YES));// 存储
			doc.add(new StringField("content", "My name is huoguo", Store.YES));// 存储
			writer.addDocument(doc);// 添加进写入流里
			writer.forceMerge(1);// 优化压缩段,大规模添加数据的时候建议，少使用本方法，会影响性能
			writer.commit();// 提交数据
			System.out.println("添加成功");
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
		String path = "d:/temp/lucene3";
//		add(path);// 调用添加方法
		simpleSearch(path,"content","里只你");
	}
	/***
	 * 简单查询的方法
	 * 
	 * @param indexReadPath
	 *            读取的索引路径
	 * @param filed
	 *            查询的字段类型
	 * @param searchText查询的文本
	 * */

	public static void simpleSearch(String indexReadPath, String field,
			String searchText) {
		try {
			directory = FSDirectory.open(new File(indexReadPath));// 打开索引文件夹
			IndexReader reader = DirectoryReader.open(directory);// 读取目录
			IndexSearcher search = new IndexSearcher(reader);// 初始化查询组件

			// Query query=new TermQuery(new Term(field, searchText));//查询

			QueryParser parser = new QueryParser(Version.LUCENE_45, field,
					new StandardAnalyzer(Version.LUCENE_45));// 标准分析器查询时候一元分词效果
			Query query = parser.parse(searchText);

			TopDocs td = search.search(query, 10000);// 获取匹配上元素的一个docid
			ScoreDoc[] sd = td.scoreDocs;// 加载所有的Documnet文档
			System.out.println("本次命中数据:" + sd.length);
			for (int i = 0; i < sd.length; i++) {

				int z = sd[i].doc;// 获取每一个文档编号
				Document doc = search.doc(z);// 获取文档
				System.out.println("id:" + doc.get("id"));
				System.out.println("name:" + doc.get("name"));
				System.out.println("content:" + doc.get("content"));
			}

			reader.close();// 关闭资源
			directory.close();// 关闭连接

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}