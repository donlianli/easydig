package com.donlian.jdk;
/**
 * 这个测试方法说明
 * 1、static方法锁住的是类。
 * 2、实例方法锁住的是实例锁。
 * 3、两个锁互不干扰
 * @author donlianli
 *
 */
public class TestLock {  
    public static void main(String[] args) {  
        final Lock lock = new Lock();  
        for(int i=0;i<10;i++){
        	 new Thread() {  
                 public void run() {  
                     Lock.staticMethod3();  
                 }  
             }.start(); 
             new Thread() {  
                 public void run() {  
                     Lock.staticMethod4();  
                 }  
             }.start();
        }
        
  
      
        
        try {  
            Thread.sleep(100);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
        new Thread() {  
            public void run() {  
                lock.method1();  
            }  
        }.start();  
        new Thread() {  
            public void run() {  
                lock.method2();  
            }  
        }.start();  
    }  
}  
  
class Lock {  
  
    public synchronized void method1() {  
        System.out.println("method1 start");  
        try {  
            Thread.sleep(1000 * 10);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("method1 end");  
    }  
    public synchronized void method2() {  
        System.out.println("method2 start");  
        try {  
            Thread.sleep(1000 * 10);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("method2 end");  
    } 
    public static synchronized void staticMethod3() {  
        System.out.println("staticMethod3 start");  
        try {  
            Thread.sleep(1000 * 10);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("staticMethod3 end");  
    } 
    public static synchronized void staticMethod4() {  
        System.out.println("staticMethod4 start");  
        try {  
            Thread.sleep(1000 * 10);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("staticMethod4 end");  
    } 
}  