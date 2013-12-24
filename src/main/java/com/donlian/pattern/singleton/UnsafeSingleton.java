package com.donlian.pattern.singleton;

import java.util.concurrent.CountDownLatch;
/**
 * 此示例说明，if判断和赋值操作并非原子操作。
 * 两个可能会
 * @author donlianli@126.com
 *
 */
public class UnsafeSingleton {
	private static volatile UnsafeSingleton INSTANCE;
	private UnsafeSingleton(){
		System.out.println("init UnsafeSingleton");
	}
	public static UnsafeSingleton getInstance(){
		if(INSTANCE==null){
			INSTANCE = new UnsafeSingleton();
		}
		return INSTANCE;
	}
	public static void main(String []arg){
		final CountDownLatch latch = new CountDownLatch(1);
		int size =20;
		for(int i=0;i<size;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						latch.await();
						getInstance();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
		}
		latch.countDown();
	}
}
