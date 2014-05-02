package com.donlian.rpc;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String param) {
		return param+" response from server;";
	}
}
