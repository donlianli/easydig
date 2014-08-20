package com.donlian.javassist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MyInterceptor implements Interceptor {

	Object proxyed;

	public MyInterceptor(Object i) {
		proxyed = i;
	}

	@Override
	public int intercept(Object instance, Method method, Object[] Args) {
		try {
			System.out.println("before action");
			method.invoke(this.proxyed, Args);
			System.out.println("after action");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
