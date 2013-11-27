package com.donlian.lucene.wowo.search;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;
/**
 * 基于距离的查询
 * phraseQuery的意义在于可以设置slop即多个关键词之间的间隔。
 * slop主要就是设置词根之间的距离查询的。即两个查询词之间的偏移量，至于是片前还是偏后
 * 程序不关心。
 * <p>
 * Phrase查询允许多个关键词之间存在一定的距离。
 * </p>
 * @author donlianli@126.com
 * 2013年11月26日
 */
public class PhraseQueryDemo {
	public static void main(String[] args) throws Exception{
		new PhraseQueryDemo().phraseQuery();
	}
	/**
	 * @throws IOException
	 */
	public void phraseQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		PhraseQuery q= new PhraseQuery();
		q.add(new Term(Utils.SEARCH_FIELD, "火锅"));
		q.add(new Term(Utils.SEARCH_FIELD, "上地"));
		//设置词之间的间隔
		q.setSlop(15);
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
