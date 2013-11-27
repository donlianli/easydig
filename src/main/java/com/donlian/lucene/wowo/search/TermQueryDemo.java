package com.donlian.lucene.wowo.search;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.donlian.lucene.wowo.Utils;
/**
 * TermQuery就是去字典中查询
 * 精确等于该词的所有文档。
 * 比如说搜索词为“火锅”.
 * 则“高兴火锅”会被搜索到，而
 * “火热的锅”则不会被搜索到。
 * @author donlianli@126.com
 * 2013年11月26日
 */
public class TermQueryDemo {
	public static void main(String[] args) throws Exception{
		new TermQueryDemo().termQuery();
	}
	/**
	 * 搜索“火锅"在standard里面没有搜索到结果
	 * 在IK的分词器里面搜索到44325
	 * @throws IOException
	 */
	public void termQuery() throws IOException {
		IndexSearcher search = Utils.getIndexSearcherIK();
		Query q = new TermQuery(new Term(Utils.SEARCH_FIELD, "火锅"));
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
