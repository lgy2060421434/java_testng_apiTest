//package aout_li_util;
//
//import com.alibaba.fastjson.JSONPath;
//
//import static aout_li_requsetGetAndPost.BaseProcessor.GlobalVariables_Map;
//
//public class JSONPathUtil {
//    public static void transitionJSon(String  GlobalVariables,String result) {
////        如果GlobalVariables全局变量需要赋值，就拿到这个接口的结果的变量存到map集合里面方便调用
//        if (GlobalVariables != null && GlobalVariables.trim().length() > 0 && GlobalVariables.contains("$")) {
//            String [] GlobalVariables_Slips=GlobalVariables.split(",");
//            for (int i=0; i< GlobalVariables_Slips.length; i++){
//                Object GlobalVariables_value= JSONPath.read(result,GlobalVariables_Slips[i]);
////                如果得到的返回值是整数类型或者是其他类型，就用toString,如果得到的是null就用if结构里面的(需要转成String类型的)
//                if(GlobalVariables_value==null){
//                    String GlobalVariables_value_String=(String) GlobalVariables_value;
//                    GlobalVariables_Map.put(GlobalVariables_Slips[i],GlobalVariables_value_String);
//                }
//                String GlobalVariables_value_String=GlobalVariables_value.toString();
//                if (GlobalVariables_value_String.substring(0).equals("[")){
//                    char GlobalVariables_value1 []=GlobalVariables_value_String.toCharArray();
//                    GlobalVariables_Map.put(GlobalVariables_Slips[i],GlobalVariables_value1);
//                }
//                GlobalVariables_Map.put(GlobalVariables_Slips[i],GlobalVariables_value_String);
//            }
//        }
//    }
//}
