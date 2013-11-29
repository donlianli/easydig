package dig.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilTest {
	public static void main(String arg[]) throws Exception{
		Map<String,String> data = new HashMap<String,String>();
		String[] ids = new String[]{"1","2","3"};
//		data.put("ids", ids);
		data.put("s1", "s1");
		data.put("i1", "1");
		ParaBean b = new ParaBean();
		BeanUtils.populate(b, data);;
		System.out.println(b.getIds());
	}
}


