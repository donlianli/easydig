package com.donlian.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BusinessProcessorHandler implements InvocationHandler {

	private Object target = null;

	BusinessProcessorHandler(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("Before method execute");
		Object result = method.invoke(target, args);
		System.out.println("A method execute");
		return result;
	}

}
