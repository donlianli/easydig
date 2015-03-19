package com.donlian.concurrent;

import java.util.Random;
/**
 * 这个类说明，当synchronized方法加在静态方法时，多个线程不能同时进行这个线程
 * 到静态方法
 * @author donlianli@126.com
 *
 */
public class StaticMethodClass {
	public static synchronized void method1() throws InterruptedException{
		System.out.println(Thread.currentThread().getName()+";method1 in");
		Random r = new Random();
		int time = (int)r.nextFloat()*100;
		Thread.sleep(time);
		System.out.println(Thread.currentThread().getName()+";method1 out");
	}
	public static synchronized void method2() throws InterruptedException{
		System.out.println(Thread.currentThread().getName()+";method2 in");
		Random r = new Random();
		int time = (int)r.nextFloat()*100;
		Thread.sleep(time);
		System.out.println(Thread.currentThread().getName()+";method2 out");
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[5];
		Thread[] ts2 = new Thread[5];
		for(int i=0;i<5;i++){
			ts[i]= new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						method1();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			ts2[i]= new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						method2();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			ts[i].start();
			ts2[i].start();
		}
		
		for(int i=0;i<5;i++){
			ts[i].join();
			ts2[i].join();
		}
	}

}
