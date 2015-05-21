package com.donlian.test.easymock.param;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;
public class POJOTest extends EasyMockSupport{
	private POJO pojo;
	private final LocalService localService = new LocalService();
   
    @Before 
    public void setUp() { 
    	pojo =  createMock(POJO.class); // 1 
    	localService.setPojo(pojo);
    } 
    @Test
    public void methd1(){
    	pojo.method1();
    	replayAll(); // replay all mocks at once
		localService.exeMethod1();
		verifyAll(); // verify all mocks at once
    }
}
