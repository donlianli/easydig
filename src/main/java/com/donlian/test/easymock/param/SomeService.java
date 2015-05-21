package com.donlian.test.easymock.param;

public interface SomeService {
	public CallResult<Long> call(Params params);
	public CallResult<Integer> call(int i,long l);
	public int update(String msg);
}
