package com.donlian.lucene.wowo.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;
/**
 * 前缀搜索
 * 去搜索词根以某个词开头的词根。
 * 其作用相当于在WildcardQuery中传递了 '关键词*'
 * @author donlianli@126.com
 * 2013年11月27日
 */
public class PrefixQueryDemo {
	public static void main(String[] args) throws Exception{
		new PrefixQueryDemo().prefixQuery();
	}
	public void prefixQuery() throws Exception{
		IndexSearcher search = Utils.getIndexSearcherIK();
		Query q = new PrefixQuery(new Term(Utils.SEARCH_FIELD, "畅游"));
		TopDocs td = search.search(q, 10);// 获取最高得分命中
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
