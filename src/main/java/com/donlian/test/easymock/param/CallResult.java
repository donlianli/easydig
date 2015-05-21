package com.donlian.test.easymock.param;

import java.io.Serializable;

public class CallResult<T> implements Serializable {
	private static final long serialVersionUID = -4361282531440085439L;
	public static final int CODE_FAILURE = -1;
	public static final int CODE_SUCCESS = 1;
	
	private final boolean success;
	private final int code;	
	private final String msg;	
	private final T resultObject;	
	public CallResult(boolean isSuccess, int code, String msg, T resultObject){
		this.success = isSuccess;
		this.code = code;
		this.msg = msg;
		this.resultObject = resultObject;
	}
	public boolean isSuccess() {
		return success;
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public T getResultObject() {
		return resultObject;
	}
	
}
