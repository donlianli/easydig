package com.donlian.jdk;

import java.sql.Timestamp;

public class NoTime {

	public static void main(String[] args) throws InterruptedException {
		Timestamp t = new Timestamp(0);
		System.out.println( t.getNanos());
		Thread.sleep(1000);
		System.out.println( t.getNanos());
	}

}
