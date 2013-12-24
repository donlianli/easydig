package com.donlian.concurrent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

import net.spy.memcached.MemcachedClient;
/**
 * 
 * @author donlianli@126.com
 *
 */
public class ConcurrentMapTest {
	private static final ConcurrentMap<String, FutureTask<Object>> cache
	= new ConcurrentHashMap<String, FutureTask<Object>>();
	private static MemcachedClient memCachedClient;
	static {
		try {
			memCachedClient = new MemcachedClient( new InetSocketAddress("192.168.1.103", 11211));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Object getInTask(String cacheKey, Callable<Object> caller) {
		// 未命中缓存，开始计算
		FutureTask<Object> f = cache.get(cacheKey);
		if (f == null) {
			FutureTask<Object> ft = new FutureTask<Object>(caller);
			f = cache.putIfAbsent(cacheKey, ft);
			if (f == null) {
				f = ft;
				ft.run();
			}
		}
		try {
			Object result = f.get();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 最后将计算任务去掉,虽然已经移除任务对象，但其他线程
			// 仍然能够获取到计算的结果，直到所有引用都失效，被垃圾回收掉
			boolean success = cache.remove(cacheKey, f);
			System.out.println("remove success:"+success);
		}
		return null;
	}
	public static List<Long> concurrentService(int para1,int param2){  
	    final String cacheKey = "IamKey";  
	    List<Long> list = (List<Long>)memCachedClient.get(cacheKey);  
	    if(list == null){  
	        Callable<Object> caller = new Callable<Object>() {  
	            public Object call() throws InterruptedException {  
	                System.out.println("Go to dao or rmi");  
	                List<Long> list = new ArrayList<Long>();  
	                list.add(1l);list.add(2l);  
	                //将计算结果缓存  
	                System.out.println("结果计算完毕，存入分布式缓存中");  
	                memCachedClient.set(cacheKey, 60, list);  
	                Thread.sleep(5);  
	                //计算结果，通常是访问数据库或者远程服务  
	                return list;  
	            }  
	        };  
	        List<Long> result = (List<Long>)getInTask(cacheKey,caller);  
	        return result;  
	    }  
	    else {  
	        System.out.println("1.缓存命中，直接返回");  
	        return list;  
	    }  
	}  
	public static void main(String[] args) {
		memCachedClient.flush();
		int threadCount=64;
		Thread[] ts = new Thread[threadCount];
		final CyclicBarrier barr = new CyclicBarrier(threadCount+1);
		for(int i=0;i<threadCount;i++){
			ts[i] = new Thread(new Runnable(){
				public void run() {
					try {
						barr.await();
						concurrentService(1,2);
						barr.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			ts[i].start();
		}
		
		try {
			long startTime=System.currentTimeMillis();
			barr.await();
			barr.await();
			long useTime = System.currentTimeMillis()-startTime;
			System.out.println("useTime:"+useTime);
			memCachedClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
