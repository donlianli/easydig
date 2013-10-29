package com.donlian.jdk;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	public static void main(String[] args) {
		int threadSize=10;
		Thread[] ts = new Thread[threadSize];
		final CyclicBarrier barr = new CyclicBarrier(threadSize+1);
		for(int i=0;i<threadSize;i++){
			ts[i] = new Thread(new Runnable(){
				public void run() {
					try {
						System.out.println("into run");
						barr.await();
						System.out.println("thread running");
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}
