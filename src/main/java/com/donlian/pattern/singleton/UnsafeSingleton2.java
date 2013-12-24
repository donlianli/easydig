package com.donlian.pattern.singleton;

import java.util.concurrent.CountDownLatch;
/**
 * 双重锁模式
 */
public class UnsafeSingleton2 {
	private static volatile UnsafeSingleton2 INSTANCE;
	private int time1=0;
	private int time2=0;
	private int time3=0;
	private int time4=0;
	private UnsafeSingleton2(){
		this.setTime1(1);
		this.setTime2(2);
		this.setTime3(3);
		this.setTime4(4);
//		System.out.println("init finish");
	}
	public static UnsafeSingleton2 getInstance(){
		if(INSTANCE==null){
			synchronized(UnsafeSingleton2.class){
				if(INSTANCE == null){
					INSTANCE = new UnsafeSingleton2();
				}
			}
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
						System.out.println(getInstance().getTime1());
						System.out.println(getInstance().getTime2());
						System.out.println(getInstance().getTime3());
						System.out.println(getInstance().getTime4());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
		}
		latch.countDown();
	}
	public int getTime1() {
		return time1;
	}
	public void setTime1(int time1) {
		this.time1 = time1;
	}
	public int getTime2() {
		return time2;
	}
	public void setTime2(int time2) {
		this.time2 = time2;
	}
	public int getTime3() {
		return time3;
	}
	public void setTime3(int time3) {
		this.time3 = time3;
	}
	public int getTime4() {
		return time4;
	}
	public void setTime4(int time4) {
		this.time4 = time4;
	}

}
