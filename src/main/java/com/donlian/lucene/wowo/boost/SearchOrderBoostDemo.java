package com.donlian.lucene.wowo.boost;

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
 * 搜索时加权示例程序。
 * 比如搜索"小肥羊火锅"
 */
public class SearchOrderBoostDemo {
	public static void main(String[] args) throws Exception{
		new SearchOrderBoostDemo().search();
		new SearchOrderBoostDemo().searchNoBoost();
	}
	/**
	 * @throws IOException
	 */
	public void search() throws IOException {
		float boots[] = new float[]{0.9f,0.8f,0.7f,0.6f,0.5f};
		IndexSearcher search = Utils.getIndexSearcherIK();
		Query q1 = new TermQuery(new Term(Utils.SEARCH_FIELD, "小肥羊"));
		q1.setBoost(boots[0]);
		Query q2 = new TermQuery(new Term(Utils.SEARCH_FIELD, "火锅"));
		q2.setBoost(boots[4]);
		BooleanQuery q= new BooleanQuery();
		q.add(q1, Occur.MUST);
		q.add(q2, Occur.SHOULD);
		
		TopDocs td = search.search(q,20);// 获取最高得分命中
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
	
	/**
	 * @throws IOException
	 */
	public void searchNoBoost() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		Query q1 = new TermQuery(new Term(Utils.SEARCH_FIELD, "小肥羊"));
		Query q2 = new TermQuery(new Term(Utils.SEARCH_FIELD, "火锅"));
		BooleanQuery q= new BooleanQuery();
		q.add(q1, Occur.MUST);
		q.add(q2, Occur.SHOULD);
		
		TopDocs td = search.search(q,30);// 获取最高得分命中
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
