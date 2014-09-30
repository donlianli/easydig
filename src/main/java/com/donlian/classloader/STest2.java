package com.donlian.classloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class STest2 {
	public static void main(String[] args) throws Exception {
		try {
			ClassPool pool1 = ClassPool.getDefault();
			CtClass point = pool1.makeClass("BadClass");
			CtMethod m = CtNewMethod.make("public void sayHello(){"
					+ "System.out.println(\"say hello\");}", point);
			point.addMethod(m);
			Class p1 = point.toClass(); 
			ClassPool pool = new ClassPool(true);
			CtClass anthoerPoint = pool.makeClass("GoodClass");
			CtMethod m2 = CtNewMethod.make("public void sayHello(){"
					+ "BadClass js = new BadClass();"
					+ "js.sayHello();}", anthoerPoint);
			anthoerPoint.addMethod(m2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
