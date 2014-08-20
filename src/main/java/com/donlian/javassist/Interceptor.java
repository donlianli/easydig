package com.donlian.javassist;

import java.lang.reflect.Method;

public interface Interceptor {
	public int intercept(Object instance, Method method, Object[] Args) ;
}
