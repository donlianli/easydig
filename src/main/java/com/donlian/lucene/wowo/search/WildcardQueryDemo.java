package com.donlian.lucene.wowo.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;

import com.donlian.lucene.wowo.Utils;
/**
 * 通配符查询方式WildcardQuery
 * 可以指定搜索某个词根，中间可以使用'*','?'来代替多个字符和一个字符。
 * API已经说明，不要使用'*'开头进行查询，那样会遍历整个字典，效率非常低
 * 
 * 不足：当你设置'顶级*婚纱'搜索时，‘顶级品牌打造经典回忆！仅3999元，享原价11999元魔玛视觉婚纱套餐！'却搜索不出来
 * 可见，通配符只适用于词根的检索，而不适用分词前的搜索
 * @author donlianli@126.com
 * 2013年11月27日
 */
public class WildcardQueryDemo {

	public static void main(String[] args) throws Exception{
		new WildcardQueryDemo().wildcardQuery();
	}
	private void wildcardQuery() throws Exception{
		IndexSearcher search = Utils.getIndexSearcherIK();
		//匹配词根以'ipad'开头,而不是文档中以ipad开头
		Query q = new WildcardQuery(new Term(Utils.SEARCH_FIELD, "ipad*"));
		TopDocs td = search.search(q, 10);// 获取最高得分命中
		System.out.println("totalHits:"+td.totalHits);
		for (ScoreDoc doc : td.scoreDocs) {
			Document d = search.doc(doc.doc);
			String item = "score:" + doc.score
					+";goodsId:"+d.get("id")
					+";goodsName:" + d.get(Utils.SEARCH_FIELD);
			System.out.println(item);
		}
		
		q = new WildcardQuery(new Term(Utils.SEARCH_FIELD, "顶级*婚纱"));
		td = search.search(q, 10);// 获取最高得分命中
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
