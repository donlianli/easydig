
package com.donlian.concurrent;

/**
 * 必须要加上 -server才能生效，因为在-server下程序运行速度很快做过优化，才会出现stop不了的情况。
 * 在Object上加上volatile就可见了。
 * 
 * 这个例子不能说明volatile关键字修饰对象，就会读取到对象域的最新值。之所以在server模式下面能够停止，也
 * 只是跟具体的虚拟机实现有关系。
 * volatile修饰关键字只能保证对象引用a的最新值及对象a的发布完整性。
 * 
 * 之所以线程没有终止，估计是因为新启动的线程没有跟主存同步。
 * 
 * 这个程序在单核的cpu上面也没有终止，说明编译器在编译代码的时候就已经做了优化.
 * 其实真正需要改的是在flag前面加上volatile关键字
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
	private  boolean  flag = true;

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

