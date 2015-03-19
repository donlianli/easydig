package com.donlian.jdk;

public class FloatTest {

	public static void main(String[] args) {
		float zero =0f;
		//zero(float)=0.0 0==zero:true
		System.out.println("zero(float)="+zero+" 0==zero:"+(zero==0));
		float minuszero =-0f;
		// -0==zero:true
		System.out.println("-0==zero:"+(minuszero==0));
		float pi =3.1415926f;
		//pi=3.1415925
		System.out.println("pi="+pi);
		double d = 5-pi;
		System.out.println("5-pi="+d);
		float f1=1-0.8f;
		//f1=0.19999999
		System.out.println("f1="+f1);
		System.out.println("f1 binary="+Float.toHexString(f1));
		float f2=0.2f;
		//f1=0.19999999
		System.out.println("f2="+f2);
		System.out.println("f2 binary="+Float.toHexString(f2));
		int i1 =(int)( 1+f1);
		System.out.println("i1="+i1);
		int i2 =(int)( 2.5f);
		System.out.println("i2="+i2);
		
		float f3=Integer.MAX_VALUE;
		System.out.println("f3="+f3);
	}
}
