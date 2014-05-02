package com.donlian.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Vector;
/**
 * 
 * @author lidongliang
 * 打印出对象的所有属性的工具类
 * 使用方法示例
 * BeanDebugger.dump(object);
 */
public class BeanDebugger {
	public static void main(String argv[]){
		/**
		 * 会将继承过来的属性也都拿到
		 */
		CommItem c = new CommItem();
		Student s = new Student();
		s.setGrade(2);
		c.setStudent(s);
		printObject(c);
	}
	/**
	 * 
	 * 从 bean 中读取有效的属性描述符. NOTE: 名称为 class 的 PropertyDescriptor 被排除在外.
	 * @param bean
	 *            Object - 需要读取的 Bean
	 * @return PropertyDescriptor[] - 属性列表
	 */
	public static PropertyDescriptor[] getAvailablePropertyDescriptors(
			Object bean) {
		try {
			Class clazz = bean.getClass();
			// 从 Bean 中解析属性信息并查找相关的 write 方法
			BeanInfo info = Introspector.getBeanInfo(clazz);
			if (info != null) {
				PropertyDescriptor pd[] = info.getPropertyDescriptors();
				Vector<PropertyDescriptor> columns = new Vector<PropertyDescriptor>();
				for (int i = 0; i < pd.length; i++) {
					String fieldName = pd[i].getName();
					if (fieldName != null && !fieldName.equals("class")) {
						columns.add(pd[i]);
					}
				}
				PropertyDescriptor[] arrays = new PropertyDescriptor[columns.size()];
				for (int j = 0; j < columns.size(); j++) {
					arrays[j] = (PropertyDescriptor) columns.get(j);
				}
				return arrays;
			}
			else {
				System.out.println("find no beanInfo");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}


	private static void printObject(Object bean) {
		if(bean == null) return ;
		java.beans.PropertyDescriptor[] descriptors = getAvailablePropertyDescriptors(bean);
		for (int i = 0; descriptors != null && i < descriptors.length; i++) {
			java.lang.reflect.Method readMethod = descriptors[i]
					.getReadMethod();
			try {
				String proName = descriptors[i].getName();
				Object value = readMethod.invoke(bean, new Object[]{});
				println("[" + bean.getClass().getName() + "]."
						+ proName + "("
						+ descriptors[i].getPropertyType().getName() + ") = "
						+ value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(descriptors==null || descriptors.length<1){
			System.out.println("has no descriptors");
		}
	}
	public static void println(String msg){
		System.out.println(msg);
	}
}