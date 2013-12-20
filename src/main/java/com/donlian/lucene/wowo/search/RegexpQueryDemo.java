package com.donlian.lucene.wowo.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;

/**
 * 正则表达式搜索
 * 同通配符搜索（WildcardQuery）。当使用*开头搜索时，性能将很低。
 * 文档上描述查询基于一个fast的算法。
 * 但同时又注释到，这个搜索可能比较慢（can be slow)
 * @author donlianli@126.com
 * 2013年11月28日
 */
public class RegexpQueryDemo {
	public static void main(String arg[]) throws Exception{
		new RegexpQueryDemo().regexpQuery();
	}
	public void regexpQuery() throws Exception{
		IndexSearcher search = Utils.getIndexSearcherIK();
		//查询包含6-8位数字的结果
		Query q = new RegexpQuery(new Term(Utils.SEARCH_FIELD, "[0-9]{6,8}"));
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
