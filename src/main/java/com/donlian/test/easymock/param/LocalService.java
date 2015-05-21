package com.donlian.test.easymock.param;

public class LocalService {
	private SomeService someService;
	private POJO pojo;
	public CallResult<Long> query(int i,long l){
		Params p = new Params();
		p.setI(i);
		p.setS("somestring");
		return someService.call(p);
	}
	
	public CallResult<Long> query2(int i,long l){
		 someService.call(i,l);
		 return null;
	}
	
	public CallResult<Long> query3(Params p){
		return someService.call(p);
	}
	public int update(String msg){
		int a = someService.update(msg);
		System.out.println(a);
		return 2;
	}
	
	public SomeService getSomeService() {
		return someService;
	}
	public void setSomeService(SomeService someService) {
		this.someService = someService;
	}

	public POJO getPojo() {
		return pojo;
	}

	public void setPojo(POJO pojo) {
		this.pojo = pojo;
	}
	
	public void exeMethod1(){
		pojo.method1();
	}
}
