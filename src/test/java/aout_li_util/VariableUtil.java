package aout_li_util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import pojo.Variable;
import pojo.WriteBackData;

import static aout_li_requsetGetAndPost.BaseProcessor.GlobalVariables_Map;

/**
 * 参数化变量工具类
 *
 * @author Administrator
 */
public class VariableUtil {
    public static Logger logger = Logger.getLogger(VariableUtil.class);
    //	定义一个map集合,拿到所有的变量名和值
    public static Map<String, String> variableNameAndValuesMap = new HashMap<String, String>();
    //	定义一个Variablede对象的实体类集合
    public static List<Variable> variables = new ArrayList<Variable>();

    static {

//    	将表单数据封装成对象,然后存到集合里面去
        List<Variable> list = ExcelUtil.lode(PropertiesUtil.getExcelPath(), "变量", Variable.class);
        variables.addAll(list);
//        for (Variable variable : variables) {
//            if (variable.getReflectClass()!=null && variable.getReflectClass().trim().length()>0){
        //		调用方法将变量及变量的值加载到map集合
        loadVariablesToMap();
//		将拿到的变量回写到用例里面
        ExcelUtil.loadRownumAndCellMapping(PropertiesUtil.getExcelPath(), "变量");
//            }
//        }

    }

    /**
     * 替换变量参数
     *
     * @param paramsmeter 原来的参数
     */
    public static String replaceVariable(String paramsmeter) {
//     取出所有的变量名称(用set集合接收)
        Set<String> variableNames = variableNameAndValuesMap.keySet();
        for (String variableName : variableNames) {
//			判断如果测试数据中出现了变量名，就替换一下
            if (paramsmeter.contains(variableName)) {
                logger.info("这是取到的变量值：" + variableNameAndValuesMap.get(variableName));
                paramsmeter = paramsmeter.replace(variableName, variableNameAndValuesMap.get(variableName));
            }
        }
//        替换全局变量
        Set<String> GlobalVariablesNames = GlobalVariables_Map.keySet();
//        对于接口依赖的就替换
        for (String GlobalVariablesName : GlobalVariablesNames) {
//			判断如果测试数据中出现了$变量名，就替换一下
            if (paramsmeter.contains(GlobalVariablesName)) {
                logger.info("这是取到的变量值：" + GlobalVariables_Map.get(GlobalVariablesName));
                paramsmeter = paramsmeter.replace(GlobalVariablesName, GlobalVariables_Map.get(GlobalVariablesName));
            }
        }
        return paramsmeter;
    }

    /**
     * 把加载到的数据放在集合里面
     */
    private static void loadVariablesToMap() {
        // TODO Auto-generated method stub
        for (Variable variable : variables) {
            String variableName = variable.getName();
            String variableValue = variable.getValue();
//			如果是变量值是空的，则使用的值
            if (variableValue == null || variableValue.trim().length() == 0 && variable.getReflectClass() != null && variable.getReflectClass().trim().length() > 0) {
//				要反射的类
                String reflectClass = variable.getReflectClass();
//				要反射的方法
                String reflectMethod = variable.getReflectMethod();
                try {
//					通过反射拿到类型字节码字节码
                    Class clazz = Class.forName(reflectClass);
//					通过反射创建对象
                    Object obj = clazz.newInstance();
//					获取要反射调用的方法对象method
                    Method method = clazz.getMethod(reflectMethod);
//					反射调用方法，获取到方法的返回值 
                    variableValue = (String) method.invoke(obj);
//					保存要回写的变量数据到集合
                    ExcelUtil.backDatas.add(new WriteBackData("变量", variableName, "RefleectValue", variableValue));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            logger.info("变量名" + variableName + "变量值" + variableValue);
            variableNameAndValuesMap.put(variableName, variableValue);
        }
    }

}
