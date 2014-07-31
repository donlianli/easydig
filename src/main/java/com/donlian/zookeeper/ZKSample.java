package com.donlian.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZKSample {
	public static final String path="/donlianli";
	public static void main(String[] args) throws Exception {
		String hostPort = "10.9.14.144:2181";
		/**
		 * 第二个参数为会话超时时间
		 */
		ZooKeeper zk = new ZooKeeper(hostPort, 30000, new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				 System.out.println("已经触发了【" + event.getType() + "】事件！"); 
			}
		});
		zk.exists("/testRootPath",  new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				 System.out.println("/testRootPath 已经触发了【" + event.getType() + "】事件！"); 
			}
		});
		Thread.sleep(80*60000);
		 // 关闭连接
		 zk.close(); 
	}

}
