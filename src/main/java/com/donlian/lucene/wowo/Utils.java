package com.donlian.lucene.wowo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
/**
 * 
 * @author donlianli
 * Not Thread Safe
 */
public class Utils {
	/**
	 * 标准分词索引
	 */
	public static final String INDEX_DIR="d:/temp/wowo-std";
	/**
	 * IK MAX WORD 索引
	 */
	public static final String IK_INDEX_DIR="d:/temp/wowo-ik";

	public static Directory directory;
	public static IndexReader reader;
	public static IndexSearcher searcher;
	
	public static final String SEARCH_FIELD="goodsName";
	public String getIndexDir(){
		return INDEX_DIR;
	}
	/**
	 * 返回IK IndexWriter
	 * */
	public static IndexWriter getIKWriter() {
		try{
			directory = FSDirectory.open(new File(IK_INDEX_DIR));// 打开存放索引的路径
			//启用非智能模式max_word模式分词更细
			Analyzer analyzer = new IKAnalyzer(false);									
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_45,analyzer);// 设置IndexWriterConfig
			iwc.setRAMBufferSizeMB(30);//设置缓冲区大小
			return new IndexWriter(directory, iwc);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 返回标准分词IndexWriter
	 * */
	public static IndexWriter getWriter() {
		try{
			directory = FSDirectory.open(new File(INDEX_DIR));// 打开存放索引的路径
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);// 设置标准分词器
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_45,analyzer);// 设置IndexWriterConfig
			iwc.setRAMBufferSizeMB(30);//设置缓冲区大小
			return new IndexWriter(directory, iwc);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static IndexSearcher getIndexSearcher() {
		try{
			directory = FSDirectory.open(new File(INDEX_DIR));// 打开索引库
			reader = DirectoryReader.open(directory);// 流读取
			searcher = new IndexSearcher(reader);// 搜索
			return searcher;
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static IndexSearcher getIndexSearcherIK() {
		try{
			directory = FSDirectory.open(new File(IK_INDEX_DIR));// 打开索引库
			reader = DirectoryReader.open(directory);// 流读取
			searcher = new IndexSearcher(reader);// 搜索
			return searcher;
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 资源释放
	 */
	public static  void closeResource(){
		try{
			if(reader!=null){
				reader.close();
			}
			if(directory!=null){
				directory.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
