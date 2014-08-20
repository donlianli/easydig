package com.donlian.javassist;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BusiService c = new BusiService();
		Service i = (Service) DProxy.createProxy(BusiService.class,
				new MyInterceptor(c));
		i.Action(123);
	}

}
