package com.donlian.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.helpers.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 2014年1月14日
 * @author lidongliang
 * 解析monitor的log数据，并存储到新的文件中
 */
public class MonitorLogUtils {
	public static final String LOG="log";
	public static final String STATS="stats";
	public static String PRO_NAME="goodscenter";
    //源目录
    public static String SRC_DIR="/home/"+PRO_NAME+"/monitor/"+LOG;
    
    public static String DEST_DIR="/root/monitor/"+PRO_NAME;
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 返回列表仅包含文件名
	 * @param directory
	 * @return
	 */
	public static List<String> getAllFileListByDir(String directory){
		File dir = new File(directory);
		if(dir.isDirectory()){
			List<String> list = new ArrayList<String>();
			for(String s :dir.list()){
				String abFileName=directory+"/"+s;
				File file = new File(abFileName);
				if(file.isFile()){
					list.add(s);
				}
			}
			return list;
		}
		else {
			System.out.println(directory + " is not a directory");
		}
		return Collections.emptyList();
	}
	@SuppressWarnings("unchecked")
	public static void extStatInfo(String file,String dir){
		String fileName = dir+"/"+file;
		try{
			System.out.println("stats '"+fileName+"'");
			List<Map<String,Object>> data = mapper.readValue(new File(fileName), List.class);
			if(data!=null && data.size()>0){
				long size=data.size();
				long allCount =0,allError=0;
				long max=Long.MIN_VALUE,min=Long.MAX_VALUE;
				for(Map<String,Object> map: data){
					Integer error =(Integer) map.get("error_Count");
					Integer total = (Integer) map.get("total_Count");
					Long time = (Long) map.get("catch_Tm_Long");
					allCount+=total;
					allError+=error;
					if(time>max){
						max=time;
					}
					if(time<min){
						min=time;
					}
				}
				print2file(fileName,size,allCount,allError,max,min);
				System.out.println("stats '"+fileName+"' finish ");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void print2file(String fileName,long size, long allCount, long allError,
			long max, long min) {
		//System.out.println(fileName.indexOf(LOG));
		String suffix = fileName.substring(fileName.indexOf(LOG)+4).replace(LOG,"");
		String newName = DEST_DIR+"/"+suffix+STATS;
		System.out.println("save to "+newName);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("project", PRO_NAME);
		data.put("fromTime", formater.format(new Date(min)));
		data.put("toTime", formater.format(new Date(max)));
		data.put("avgCount", size==0?0:allCount/size);
		data.put("avgErrCount",  size==0?0:allError/size);
		try{
			File destFile = new File(newName);
			if(!destFile.exists()){
				String destDir = destFile.getParent();
				File dir = new File(destDir);
				FileUtils.mkdir(dir, true);
			}
			mapper.writerWithDefaultPrettyPrinter().writeValue(destFile, data);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static List<String> getAllSubDir(String srcDir) {
		File dir = new File(srcDir);
		if(dir.isDirectory()){
			List<String> list = new ArrayList<String>();
			for(File f :dir.listFiles()){
				if(f.isDirectory()){
					list.add(f.getName());
				//	System.out.println(f.getName());
				}
			}
			return list;
		}
		else {
			System.out.println(srcDir + " is not a directory");
		}
		return Collections.emptyList();
	}
	public static void main(String argv[]){
		String srcDir = SRC_DIR;
		String desDir = DEST_DIR;
		String projectName = System.getProperty("project");
		if(projectName!=null && !"".equals(projectName.trim())){
			srcDir = srcDir.replace(PRO_NAME, projectName);
			desDir = desDir.replace(PRO_NAME, projectName);
			SRC_DIR = SRC_DIR.replace(PRO_NAME,projectName);
			DEST_DIR = DEST_DIR.replace(PRO_NAME,projectName);
			PRO_NAME = projectName;
		}
		List<String> subDirs = getAllSubDir(srcDir);
		for(String subDir: subDirs){
			List<String> srcList = getAllFileListByDir(srcDir+"/"+subDir);
			List<String> desList = getAllFileListByDir(desDir+"/"+subDir);
			for(int i=0,l=desList.size();i<l;i++){
				desList.set(i,desList.get(i).replace(STATS,LOG));
			}
			srcList.removeAll(desList);
			if(srcList.size()>0){
				for(String fileName:srcList){
					extStatInfo(fileName,srcDir+"/"+subDir);
				}
			}
		}
	}
}
