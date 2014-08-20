package com.donlian.hessian;

import java.io.File;
import java.io.FileWriter;

import com.caucho.hessian.client.HessianProxyFactory;

public class HesssianClient {
	public static void main(String[] argvs){
		String url = "http://hessian.caucho.com/test/test";

		HessianProxyFactory factory = new HessianProxyFactory();
//		factory.
//		BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);

//		System.out.println("hello(): " + basic.hello());
	}
	public Class getInterface(){
		 return null;
//		  String fileName = "d:/temp/UserService.java";
//		  File f = new File(fileName);
//		  FileWriter fw = new FileWriter(f);
//		  fw.write(source);
//		  fw.flush();
//		  fw.close();//这里只是产生一个JAVA文件,简单的IO操作
//		 
//		  // compile下面开始编译这个Store.java
//		  JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		  StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null,
//		    null, null);
//		  Iterable units = fileMgr.getJavaFileObjects(fileName);
//		  CompilationTask t = compiler.getTask(null, fileMgr, null, null, null,
//		    units);
//		  t.call();
//		  fileMgr.close();
//		 
//		  // load into memory and create an instance
//		  URL[] urls = new URL[] { new URL("file:/"
//		    + System.getProperty("user.dir") + "/src") };
//		  URLClassLoader ul = new URLClassLoader(urls);
//		  Class c = ul.loadClass("com.cjb.proxy.Dealer");
//		  System.out.println(c);
//		 
//		//客户端调用
//		 
//		  Constructor ctr = c.getConstructor(Store.class);
//		  Store s = (Store)ctr.newInstance(new Supermarket());//这里看到,这个我们这个代理类必须实现Store的原因
//		  s.sell();
	}
}
