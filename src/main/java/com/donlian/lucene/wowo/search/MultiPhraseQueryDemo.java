package com.donlian.lucene.wowo.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;
/**
 * MultiPhrase实现功能类似正则表达式查询。
 * 当距离slop设置的足够大时，前缀搜索和后缀搜索的文档集合是一样的。
 * 但是文档分数不一样。
 * @author donlianli@126.com
 * 2013年11月26日
 */
public class MultiPhraseQueryDemo {
	private List<String> list = new ArrayList<String>();
	private int slop=5;
	public static void main(String[] args) throws Exception{
		MultiPhraseQueryDemo m = new MultiPhraseQueryDemo();
		m.prefixPhraseQuery();
		m.suffixPhraseQuery();
		m.multiPhraseQuery();
		System.out.println(m.list);
	}
	/**
	 * 前缀搜索
	 * 搜索逻辑：
	 * 国贸+(火锅|电影|KTV)
	 * 
	 * 国贸与后面的数组是AND关系
	 * 数组之间是OR关系
	 */
	public void prefixPhraseQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		MultiPhraseQuery q= new MultiPhraseQuery();
		q.add(new Term(Utils.SEARCH_FIELD, "国贸"));
		//词组之间是OR的关系
		q.add(new Term[]{new Term(Utils.SEARCH_FIELD, "火锅")
						,new Term(Utils.SEARCH_FIELD, "电影")
						,new Term(Utils.SEARCH_FIELD, "KTV")});
		//设置词之间的间隔
		q.setSlop(slop);
		TopDocs td = search.search(q, 30);// 获取最高得分命中
		System.out.println("totalHits:"+td.totalHits);
		for (ScoreDoc doc : td.scoreDocs) {
			Document d = search.doc(doc.doc);
			String item = "score:" + doc.score
					+";goodsId:"+d.get("id")
					+";goodsName:" + d.get(Utils.SEARCH_FIELD);
			list.add(d.get("id"));
			System.out.println(item);
		}
		Utils.closeResource();
	}
	
	/**
	 * 后缀搜索
	 * 搜索逻辑：
	 *(火锅||电影||KTV) && 国贸
	 * 
	 * 国贸与后面的数组是AND关系
	 * 数组之间是OR关系
	 */
	public void suffixPhraseQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		MultiPhraseQuery q= new MultiPhraseQuery();
		//词组之间是OR的关系
		q.add(new Term[]{new Term(Utils.SEARCH_FIELD, "火锅")
						,new Term(Utils.SEARCH_FIELD, "电影")
						,new Term(Utils.SEARCH_FIELD, "KTV")});
		
		q.add(new Term(Utils.SEARCH_FIELD, "国贸"));
		//设置词之间的间隔
		q.setSlop(slop);
		TopDocs td = search.search(q, 30);// 获取最高得分命中
		System.out.println("totalHits:"+td.totalHits);
		for (ScoreDoc doc : td.scoreDocs) {
			Document d = search.doc(doc.doc);
			String item = "score:" + doc.score
					+";goodsId:"+d.get("id")
					+";goodsName:" + d.get(Utils.SEARCH_FIELD);
			System.out.println(item);
			list.remove(d.get("id"));
		}
		
		Utils.closeResource();
	}
	
	/**
	 * 混合搜索
	 * 搜索逻辑：
	 * 国贸&& (火锅||电影||KTV) && (停车)
	 * 
	 * 国贸与后面的数组是AND关系
	 * 数组之间是OR关系
	 */
	public void multiPhraseQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		MultiPhraseQuery q= new MultiPhraseQuery();
		q.add(new Term(Utils.SEARCH_FIELD, "国贸"));
		//词组之间是OR的关系
		q.add(new Term[]{new Term(Utils.SEARCH_FIELD, "火锅")
						,new Term(Utils.SEARCH_FIELD, "电影")
						,new Term(Utils.SEARCH_FIELD, "KTV")});
		
		q.add(new Term[]{new Term(Utils.SEARCH_FIELD, "人")});
		
		//设置词之间的间隔
		q.setSlop(slop);
		TopDocs td = search.search(q, 10);// 获取最高得分命中
		//总数 17
		System.out.println("totalHits:"+td.totalHits);
		for (ScoreDoc doc : td.scoreDocs) {
			Document d = search.doc(doc.doc);
			String item = "score:" + doc.score
					+";goodsId:"+d.get("id")
					+";goodsName:" + d.get(Utils.SEARCH_FIELD);
			System.out.println(item);
		}
		Utils.closeResource();
	}
}
