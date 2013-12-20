package com.donlian.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class HelloGuiceImpl implements HelloGuice {

	public void sayHello() {
		System.out.println("injected by guice");
	}
	
	public static void main(String[] args) {  
		   Injector injector = Guice.createInjector(new HelloModule()); 
		   HelloGuice hello = injector.getInstance(HelloGuice.class); 
		   hello.sayHello();
	}  
}
