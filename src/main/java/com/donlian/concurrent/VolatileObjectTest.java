
package com.donlian.concurrent;

/**
 * @author ThrilleR 
 * 必须要加上 -server才能生效，因为在-server下程序运行速度很快做过优化，才会出现stop不了的情况。
 * 在Object上加上volatile就可见了。
 * 
 * 这个例子说明：暂时无解
 */
public class VolatileObjectTest implements Runnable {
	private  ObjectA a; // 加上volatile 就可以正常结束While循环了
	public static void main(String[] args) throws InterruptedException {
		// 如果启动的时候加上-server 参数则会 输出 Java HotSpot(TM) Server VM
		System.out.println(System.getProperty("java.vm.name"));
		
		VolatileObjectTest test = new VolatileObjectTest(new ObjectA());
		new Thread(test).start();
		System.out.println("default flag " + test.getA().isFlag());
		//不能去掉此休眠,去掉就不会停止
		Thread.sleep(1000);
		test.stop();
		System.out.println("set stop flag " + test.getA().isFlag());
		System.out.println("Main Thread " + test.getA().isFlag());
	}
	public VolatileObjectTest(ObjectA a) {
		this.a = a;
	}

	public ObjectA getA() {
		return a;
	}

	public void setA(ObjectA a) {
		this.a = a;
	}

	@Override
	public void run() {
		long i = 0;
		while (a.isFlagTrue()) {
			i++;
		}
		System.out.println("stop My Thread " + i);
	}

	public void stop() {
		a.setFlag(false);
	}
}

class ObjectA {
	private boolean flag = true;

	public boolean isFlag() {
		return flag;
	}
	public boolean isFlagTrue() {
		return flag==true;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}

