package com.donlian.classloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class JSTest {
	public static void main(String[] args) throws Exception {
		try {
			CtClass point = ClassPool.getDefault().get("com.donlian.classloader.SomeClass");
			CtMethod m = CtNewMethod.make("public void sayHello(){"
					+ "System.out.println(\"say hello\");}", point);
			point.addMethod(m);
			Class p1 = point.toClass(); 
			SomeClass i1 = (SomeClass)p1.newInstance();
			//p1 是java.lang.Class，其加载类为bootstrap loader
			System.out.println(p1.getClass().getClassLoader());
			//i1 是javassist使用appClassLoader加载
			System.out.println(i1.getClass().getClassLoader());
			//
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			ClassPool pool = new ClassPool(true);
//			pool.appendClassPath(new LoaderClassPath(cl));
			CtClass anthoerPoint = pool.makeClass("GoodClass");
			//false
			System.out.println(ClassPool.getDefault()==pool);
			System.out.println(ClassPool.getDefault().equals(pool));
			CtMethod m2 = CtNewMethod.make("public void sayHello(){"
					+ "com.donlian.classloader.SomeClass js = new com.donlian.classloader.SomeClass();"
					+ "js.sayHello();}", anthoerPoint);
			anthoerPoint.addMethod(m2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class SomeClass {
}