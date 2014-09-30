package com.donlian.classloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

public class JSTest3 {
	public static void main(String[] args) throws Exception {
		try {
			ClassPool pool1 = ClassPool.getDefault();
			CtClass point = pool1.makeClass("BadClass");
			CtMethod m = CtNewMethod.make("public void sayHello(){"
					+ "System.out.println(\"say hello\");}", point);
			point.addMethod(m);
			Class p1 = point.toClass(); 
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			ClassPool pool = new ClassPool(true);
			pool.appendClassPath(new LoaderClassPath(cl));
			CtClass anthoerPoint = pool.makeClass("BadClass");
			CtMethod m2 = CtNewMethod.make("public void sayHello2(){"
					+ "System.out.println(\"say hello\");}", point);
			anthoerPoint.addMethod(m2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
