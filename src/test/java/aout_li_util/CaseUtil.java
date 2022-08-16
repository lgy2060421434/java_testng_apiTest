package aout_li_util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import pojo.Case;

/**
 * 用例工具类
 * 
 * @author Administrator
 *
 */
public class CaseUtil {
//	new 一个对象集合,静态方便调用
	public static List<Case> cases = new ArrayList<Case>();
	static {
		List<Case> list=ExcelUtil.lode(PropertiesUtil.getExcelPath(), "用例", Case.class);
		System.out.println(list);

//		addAll,一次性添加一个集合
		cases.addAll(list);
	}

	/**
	 * @param apiID     指定的接口编号
	 * @param cellNames 指定的列名
	 * @return
	 */

//	通过接口Id,和列,拿到想要的值,将数据添加到集合对象里面
	public static Object[][] getCaseDdatasByApiId(String apiID, String[] cellNames) {
		Class<Case> clazz = Case.class;
//		创建一个对象集合,保存指定接口的case对象
		ArrayList<Case> cslist = new ArrayList<Case>();
//		先拿到一组数据,可以得到该组数据的长度,方便后面调用
		for (Case cs : cases) {
//			循环处理
			if (cs.getApiId().equals(apiID)) {
				cslist.add(cs);
			}
		}
		
//		定义一个二维数组,通过反射将数据存进二维数组里面
		Object[][] datas = new Object[cslist.size()][cellNames.length];
//		一层循环得到集合的长度,
		for (int i = 0; i < cslist.size(); i++) {
			Case cs = cslist.get(i);
			for (int j = 0; j < cellNames.length; j++) {
//				要反射的方法名
				String methodName = "get" + cellNames[j];
//				获取到反射的方法对象 
				try {
					Method method = clazz.getMethod(methodName);
					String value = (String) method.invoke(cs);
					datas[i][j] = value;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return datas;
		// TODO Auto-generated method stub

	}
}