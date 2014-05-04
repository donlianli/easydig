package com.donlian.rpc.proxy;
import java.lang.reflect.Proxy;
/**
 * 这个例子说明如何使用proxy实现AOP
 * @author donlianli
 *
 *Proxy.newProxyInstance方法会做如下几件事：
 *1，根据传入的第二个参数interfaces动态生成一个类，实现interfaces中的接口，该例中即BusinessProcessor接口的processBusiness方法。并且继承了Proxy类，重写了hashcode,toString,equals等三个方法。具体实现可参看 ProxyGenerator.generateProxyClass(...); 该例中生成了$Proxy0类
 *2，通过传入的第一个参数classloder将刚生成的类加载到jvm中。即将$Proxy0类load
 *3，利用第三个参数，调用$Proxy0的$Proxy0(InvocationHandler)构造函数 创建$Proxy0的对象，并且用interfaces参数遍历其所有接口的方法，并生成Method对象初始化对象的几个Method成员变量
 *4，将$Proxy0的实例返回给客户端。
 */
public class AOPProxy {
	public static void main(String[] args) {
		BusinessProcessorImpl bpimpl = new BusinessProcessorImpl();
		BusinessProcessorHandler handler = new BusinessProcessorHandler(bpimpl);
		BusinessProcessor bp = (BusinessProcessor) Proxy.newProxyInstance(
				bpimpl.getClass().getClassLoader(), bpimpl.getClass()
						.getInterfaces(), handler);
		bp.processBusiness();
	}
}
