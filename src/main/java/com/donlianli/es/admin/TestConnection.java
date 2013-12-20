package com.donlianli.es.admin;

import java.util.Map;
import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoRequest;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;
/**
 * @author donlianli@126.com
 * 测试服务器的可用状态
 */
public class TestConnection {
	/**
	 * 测试ES可用连接数方法
	 * 同时也也可以用以校验ES是否可以连接上
	 */
	public static void main(String[] args) {
		//通过transport方式连接哦，否则没有意义了
		Client client = ESUtils.getClient();
		try{
			NodesInfoResponse response = client.admin().cluster()
					//超时时间设置为半分钟
					.nodesInfo(new NodesInfoRequest().timeout("30")).actionGet();
	        Map<String,NodeInfo> nodesMap = response.getNodesMap();
	        //打印节点信息
	    	for(Map.Entry<String, NodeInfo> entry : nodesMap.entrySet()){
	    		System.out.println(entry.getKey() + ":" + entry.getValue().getServiceAttributes()) ;
	    	}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("无法连接到Elasticsearch");
		}
	}
}
