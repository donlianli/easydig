package com.donlian.jdk;


public class FinallyTest {
	public static boolean isRunning = false;
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					testFinally();
				}
				
			}).start();
		}
	}
	
	public static void testFinally(){
		synchronized(FinallyTest.class){
			if(isRunning){
				System.out.println(" method has running ,will exit ");
				return;
			}else{
				isRunning = true;
			}
		}
		try{
			System.out.println("begin run");
			Thread.sleep(3000);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.out.println("I am here,finally");
			isRunning = false;
		}
	}
}
