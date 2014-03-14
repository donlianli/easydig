package com.donlian.util;

public class TraceUtil {

	public static void main(String[] args) {
		System.out.println(getIpLong("172.16.52.69"));

	}
	public static Long getIpLong(String ip){
		String[] ips=ip.split("\\.");
		return (Long.parseLong(ips[0])<<24)+(Long.parseLong(ips[1])<<16)
				+(Long.parseLong(ips[2])<<8)+Long.parseLong(ips[3]);
	}
}
