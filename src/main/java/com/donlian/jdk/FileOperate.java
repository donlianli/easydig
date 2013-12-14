package com.donlian.jdk;

import java.io.File;
import java.util.ArrayList;

/**
 * @Description 遍历指定文件夹下所有文件 结果存放到nameList集合中
 * @Author Li Yalin 2048
 * @Date 2007-8-13
 * @Version 1.0 B-Soft
 */
public class FileOperate {
	private ArrayList nameList = new ArrayList();
	private static String dirName = "d:\\temp\\es";
	public static void main(String argv[]){
		new FileOperate().getSubFile(dirName);
	}
	public void getSubFile(String FileName) {
		File parentF = new File(FileName);
		if (!parentF.exists()) {
			System.out.println("文件或目录不存在");
			return;
		}
		if (parentF.isFile()) {
			nameList.add(parentF.getAbsoluteFile());
			return;
		}
		String[] subFiles = parentF.list();
		for (int i = 0; i < subFiles.length; i++) {
			System.out.println();
//			getSubFile(dirName + "/" + subFiles[i]);
		}
	}

	public ArrayList getNameList() {
		return nameList;
	}

}