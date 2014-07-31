package com.donlian.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

public class HesssianClient {
	public static void main(String[] argvs){
		String url = "http://hessian.caucho.com/test/test";

		HessianProxyFactory factory = new HessianProxyFactory();
//		factory.
//		BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);

//		System.out.println("hello(): " + basic.hello());
	}
}
