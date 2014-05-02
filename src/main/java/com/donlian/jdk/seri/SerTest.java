package com.donlian.jdk.seri;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerTest {
	public static void main(String[] argvs){
//		serializeToConsole();
		serializeToByte();
	}
	public static void serializeToConsole() {
		try {
			Person ted = new Person("Ted", "张三", 39);
			Person charl = new Person("Charlotte", "li", 38);

			ted.setSpouse(charl);
			charl.setSpouse(ted);

			ObjectOutputStream oos = new ObjectOutputStream(System.out);
			oos.writeObject(ted);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void serializeToByte() {
		try {
			Person ted = new Person("Ted", "Neward", 39);
			Person charl = new Person("Charlotte", "中国", 38);

			ted.setSpouse(charl);
			charl.setSpouse(ted);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(ted);
			//print size
			System.out.println("size:"+baos.size());
			System.out.println(baos.toString());
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}