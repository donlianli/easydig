package com.donlian.guice;

import com.google.inject.AbstractModule;

public class HelloModule extends AbstractModule {

	@Override
	protected void configure() {
		 bind(HelloGuice.class).to(HelloGuiceImpl.class);  
	}

}
