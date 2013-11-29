package com.donlian.jdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 这个代码说明，在本地文件中序列化与反序列化。
 * 子类的属性会被自动设置。
 * 但是在hessian的反序列化时，会出现
 * 反序列化后为空的bug。
 * @author donlianli
 *
 */
public class BusiModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3459332498137431721L;
	private Integer intId;

	public Integer getIntId() {
		return intId;
	}

	public void setIntId(Integer intId) {
		this.intId = intId;
	}
	public static void main(String argv[]) throws Exception{
		File file = new File("test.out");
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
        BusiModel m= new BusiModel();
		m.setIntId(1);
        oout.writeObject(m);
        oout.close();

        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
        Object newM = oin.readObject(); // 没有强制转换到Person类型
        oin.close();
        BusiModel m2 = (BusiModel)newM;
		System.out.println(m2.getIntId());
	}
}
