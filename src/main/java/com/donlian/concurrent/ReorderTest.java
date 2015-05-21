package com.donlian.concurrent;
/**
 * 重排序示例
 * 如果输出是（0，0），则代码看起来应该是这样执行的。
 * 线程1：x=b(0)
 * 线程2：y=a(0)
 * 线程1：a=1
 * 线程2：b=1
 *
 * 在同一个线程内，重排序后的指令是
 * 线程1：x=b;a=1;(顺序颠倒）
 * 线程2：y=a;b=1;(顺序颠倒）
 */
public class ReorderTest {
    private static int x = 0, y = 0;
    private static int a = 0, b =0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread one = new Thread(new Runnable() {
                public void run() {
                    //由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                    shortWait(100000);
                    a = 1;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }


    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
