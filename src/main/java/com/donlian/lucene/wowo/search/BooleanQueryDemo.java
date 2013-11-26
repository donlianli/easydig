package com.donlian.lucene.wowo.search;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;
/**
 * BooleanQuery提供了逻辑组合搜索条件。
 * 比如：搜索包含关键字"火锅"并且还包含"知春路"，并且包含
 * "地铁"的商品。
 * @author donlianli@126.com
 * 2013年11月26日
 */
public class BooleanQueryDemo {
	public static void main(String[] args) throws Exception{
		new BooleanQueryDemo().booleanQuery();
	}
	/**
	 * 包含上地和火锅的搜索
	 * @throws IOException
	 */
	public void booleanQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		Query q1 = new TermQuery(new Term(Utils.SEARCH_FIELD, "火锅"));
		Query q2 = new TermQuery(new Term(Utils.SEARCH_FIELD, "上地"));
		//排除词，这个词也必须是词根
		Query q3 = new TermQuery(new Term(Utils.SEARCH_FIELD, "集贤"));
		BooleanQuery q= new BooleanQuery();
		q.add(q1, Occur.MUST);
		q.add(q2, Occur.MUST);
		q.add(q3, Occur.MUST_NOT);
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
