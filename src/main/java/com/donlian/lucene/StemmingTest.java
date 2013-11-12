package com.donlian.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
/**
 * 这样设置并没有去掉复数形式，只是
 * 把词转换成了小写
 * @author donlianli
 *
 */
public class StemmingTest {
	public static Directory directory;
	public static void main(String argv[]) throws Exception{
		String indexPath = "d:\\temp\\lucene";
		createIndex(indexPath);
		search(indexPath);
	}
	/**
	 * 返回IndexWriter
	 * */
	public static IndexWriter getWriter() throws Exception {
		Analyzer analyzer = new PorterStemAnalyzer();// 设置标准分词器
																	// ,默认是一元分词
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);// 设置IndexWriterConfig
		// iwc.setRAMBufferSizeMB(3);//设置缓冲区大小
		return new IndexWriter(directory, iwc);
	}

	public static void createIndex(String indexWriterPath) throws IOException {
		IndexWriter writer = null;
		try {
			directory = FSDirectory.open(new File(indexWriterPath));// 打开存放索引的路径
			writer = getWriter();
			Document doc = new Document();
			doc.add(new StringField("id", "25", Store.YES));// ID类型不分词存储
			doc.add(new TextField("name", "Dongliang.Lee", Store.YES));// name使用默认一元分词
			doc.add(new TextField("content", "Hello students was driving cars professionally", Store.YES));// 存储
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
	
	public static void  search(String indexPath) throws IOException {
		Directory d = FSDirectory.open(new File(indexPath));// 打开索引库
		IndexReader reader = DirectoryReader.open(d);// 流读取
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(new TermQuery(new Term("content", "car")),
				10);
		System.out.println(docs.totalHits);
		docs = searcher.search(new TermQuery(new Term("content", "drive")), 10);
		System.out.println(docs.totalHits);
		docs = searcher.search(new TermQuery(new Term("content", "profession")),10);
		System.out.println(docs.totalHits);
	}
}
