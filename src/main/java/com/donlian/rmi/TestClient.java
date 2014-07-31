package com.donlian.rmi;

import java.rmi.Naming;

public class TestClient {

	public static void main(String[] args) {
		try{  
            //调用远程对象，注意RMI路径与接口必须与服务器配置一致  
           Naming.lookup("rmi://10.9.210.212:19011/goodsService");  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
	}
}
