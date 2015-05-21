package com.donlian.test.easymock.param;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;
public class LocalServiceTest extends EasyMockSupport{
	private SomeService someService;
	private final LocalService localService = new LocalService();
   
    @Before 
    public void setUp() { 
    	someService =  createMock(SomeService.class); // 1 
    	localService.setSomeService(someService);
    } 
    /**
     * 这个代码竟然执行成功。
     * 尽管实现方法返回的null，不符合期望的结果，但是结果却执行成功了
     */
    @Test
	public void testQuery2(){
		int i =1;
		long l=2l;
		CallResult<Integer> r = new CallResult<Integer>(true,1,"msg",1);
		expect(someService.call(i,l)).andReturn(r);
		replayAll(); // replay all mocks at once
		localService.query2(i, l);
		verifyAll(); // verify all mocks at once
	}
    @Test
    public void testUpdate(){
    	String msg = "msg";
    	expect(someService.update(msg)).andReturn(1);
    	replayAll(); // replay all mocks at once
		localService.update(msg);
		verifyAll(); // verify all mocks at once
    }
    /**
     * 当调用参数 是同一对象时，测试可以通过。
     */
    @Test
	public void testQuery3(){
		int i =100;
		String s="somestring";
		Params p = new Params();
		p.setI(i);
		p.setS(s);
		CallResult<Long> r = new CallResult<Long>(true,1,"msg",1l);
		
		expect(someService.call(p)).andReturn(r);
		replayAll(); // replay all mocks at once
		localService.query3(p);
		verifyAll(); // verify all mocks at once
	}
    
    /**
     */
	@Test
	public void testQuery(){
		int i =100;
		CallResult<Long> r = new CallResult<Long>(true,1,"msg",1l);
		Params p = new Params();
		p.setI(i);
		//这里只能使用isA或者eq。如果使用eq，参数应该重写equal方法
		expect(someService.call(p)).andReturn(r);
		replayAll(); // replay all mocks at once
		localService.query(i, 1l);
		verifyAll(); // verify all mocks at once
	}
}
