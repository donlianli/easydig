package com.donlian.jdk;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * 这个例子说明，可以采用blockingQueue建立有界队列
 * 使用offer方法放数据
 * 使用take方法取数据
 * @author donlianli@126.com
 */
public class BlockingQueueTest {
	public static void main(String argv[]) throws InterruptedException{
		final BlockingQueue<Integer> bqueue = new LinkedBlockingQueue<Integer>(3);
		Thread producer = new Thread(new Runnable(){
			Random rand = new Random();
			@Override
			public void run() {
				while(true){
					float rf = rand.nextFloat();
					int rt =(int)( rf*1000);
					boolean success = bqueue.offer(rt);
					System.out.println("sleep "+rt + " ms");
					if(!success){
						System.out.println("queue is full");
					}
					try {
						TimeUnit.MILLISECONDS.sleep(rt);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread consumer = new Thread(new Runnable(){
			Random rand = new Random();
			@Override
			public void run() {
				while(true){
					try {
						Integer r = bqueue.take();
						System.out.println("take "+r + " from queue");
						float rf = rand.nextFloat();
						int rt =(int)( rf*1000);
						System.out.println("consumer sleep "+rt + " ms");
						TimeUnit.MILLISECONDS.sleep(rt);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		producer.start();
		consumer.start();
		TimeUnit.DAYS.sleep(100);
	}
}
