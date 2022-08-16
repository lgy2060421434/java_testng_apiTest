package aout_li_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aout_li_requsetGetAndPost.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import pojo.WriteBackData;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.log4j.Logger;

public class ExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    //       判断接口类型的方法(get&post)
    public static Map<String, String> doGetOrPost(String url, String type, Map<String, Object> params, String isNeedToken, String CaseId, String fileUp) {
        Map<String, String> result = new HashMap<String, String>();
        if (fileUp != null && fileUp.trim().length() > 0) {
            return form_dataDoPost.doPost(url, params, isNeedToken, CaseId, fileUp);
        } else if ("post".equalsIgnoreCase(type)) {
            return getDoPost.doPost(url, params, isNeedToken, CaseId);
        } else if ("get".equalsIgnoreCase(type)) {
            return getDoGet.doGet(url, params, isNeedToken, CaseId);
        }
        return result;

    }


    //  创建amp集合存列号和行号对应关系
    public static Map<String, Integer> rowIdentifileRowNumeMaping = new HashMap<String, Integer>();
    public static Map<String, Integer> cellNameCellnumMapping = new HashMap<String, Integer>();
    public static List<WriteBackData> backDatas = new ArrayList<WriteBackData>();
    public static Map<String, String> result_code = new HashMap<String, String>();
    public static Map<String, String> GlobalVariables_Map = new HashMap<String, String>();

    static {
        loadRownumAndCellMapping(PropertiesUtil.getExcelPath(), "用例");
    }

    /**
     * 获取caseID所在的行号,以及列名所在的列号
     *
     * @param excelPath
     * @param sheetName
     */
    public static void loadRownumAndCellMapping(String excelPath, String sheetName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Row titleRow = sheet.getRow(0);
            if (titleRow != null) {
                int lastCellNum = titleRow.getLastCellNum();
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = titleRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    value = value.substring(0, value.indexOf("("));
                    int cellNum = cell.getAddress().getColumn();
//					存列名和列索引
                    cellNameCellnumMapping.put(value, cellNum);
                }
            }
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell fristCell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
//				System.out.println(fristCell);
                fristCell.setCellType(CellType.STRING);
                String value = fristCell.getStringCellValue();
                int rowNum = row.getRowNum();
//				存行名和行索引
                rowIdentifileRowNumeMaping.put(value, rowNum);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    //       重写数据加载方法        (利用泛行解决类型侵入)
    public static <T> List<T> lode(String excelPath, String SheetName, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
//		拿到workbook对象
        try {
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = workbook.getSheet(SheetName);
//			获取第一行的数据
            Row fristRow = sheet.getRow(0);
//			获取第一行的列号数
            int lastCell = fristRow.getLastCellNum();
//			有多少列数组就是多大
            String[] fieds = new String[lastCell];
//          循环拿到第一行的标题
            for (int i = 0; i < lastCell; i++) {
//				拿到第一列的数据,如:apiId(接口编号)
                Cell cell = fristRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
//				将列数据全部转成string类型
                cell.setCellType(CellType.STRING);
//				拿到第一行的标题值
                String title = cell.getStringCellValue();
                title = title.substring(0, title.indexOf("("));
//				String methName="set"+title;
                fieds[i] = title;
            }

//          获取表里所有的数据,laseRow是总共数据的行量
            int lastRow = sheet.getLastRowNum();
            for (int i = 1; i <= lastRow; i++) {
//				创建cs对象字节码
                T obj = clazz.newInstance();
//				拿到一个数据行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
//				取出表里的数据,反射封装到case对象里面
                for (int j = 0; j < lastCell; j++) {

                    Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
//					通过反射的方法名
                    String methodName = "set" + fieds[j];
//						需要获取反射的方法对象
                    Method method = clazz.getDeclaredMethod(methodName, String.class);
//						完成反射调用
                    method.invoke(obj, value);
                }
                list.add(obj);
//				将数据都保存到集合里面case 用例表单的数据,relist是接口信息的数据,instanceof判断对象类型的语法
//				if (obj instanceof Case) {
//					Case cs = (Case) obj;
//					CaseUtil.cases.add(cs);
//				} else if (obj instanceof Rest) {
//					Rest rest = (Rest) obj;
//					RestUtil.relist.add(rest);
//				}
//				else if (obj instanceof Variable ) {
//					Variable var = (Variable) obj;
//					VariableUtil.variables.add(var);
//				}

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 将响应的结果数据显示在用例表里面
     *
     * @param caseId             用例编号
     * @param ActualResponseData 需要回写到数据列的列名
     * @param result             得到的结果
     */
    public static void writeBackData(String excelPath, String sheetName, String caseId, String ActualResponseData,
                                     String result) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int rownum = rowIdentifileRowNumeMaping.get(caseId);
            Row row = sheet.getRow(rownum);
            int cellnum = cellNameCellnumMapping.get(ActualResponseData);
            Cell cell = row.getCell(cellnum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
//			将响应结果写到用例表里面
            cell.setCellValue(result);
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("找不到指定的文件目录");
                e.printStackTrace();
            }
        }

    }

    /**
     * 批量回写数据的方法(得到的接口编号存到map集合里面,再循环取出回写到对应的列里面)
     *
     * @param excelPath
     */
    public static void batchWriteBackDates(String excelPath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            System.out.println(backDatas);
            for (WriteBackData backData : backDatas) {
                String sheetName = backData.getSheetName();
                Sheet sheet = workbook.getSheet(sheetName);
                String rowIdentifile = backData.getRowIdentifier();
                int rowNum = rowIdentifileRowNumeMaping.get(rowIdentifile);
                Row row = sheet.getRow(rowNum);
                String cellName = backData.getCellName();
                int cellnum = cellNameCellnumMapping.get(cellName);
                Cell cell = row.getCell(cellnum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String value = backData.getResult();
                cell.setCellValue(value);

            }
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("找不到指定的文件目录");
                e.printStackTrace();
            }
        }

    }

    public static Map<String, String> upMath(String paramsmeter, String type, String url, String isNeedToken, String CaseId, String fileUp) {
        if (paramsmeter.contains(":[") && paramsmeter.contains("]")) {
            if ("post".equalsIgnoreCase(type)) {
                result_code = BodyPost.doPost(url, paramsmeter, isNeedToken, CaseId);
            } else if ("get".equalsIgnoreCase(type)) {
                result_code = BodyGet.doGet(url, paramsmeter, isNeedToken, CaseId);
            }
        } else {
//		json解析数据将json格式数据转成map形式
            Map<String, Object> params = (Map) JSONObject.parse(paramsmeter);
            logger.info("调用接口,请求数据,拿到的isNeedToken是：" + isNeedToken);
//		将数据放进方法里面根据类型决定调用什么方法,发送请求,得到响应结果
            result_code = ExcelUtil.doGetOrPost(url, type, params, isNeedToken, CaseId, fileUp);
        }
        return result_code;
    }

    //    接口依赖用的返回赋值
    public static Map<String, String> getGlobalVariables(String GlobalVariables, String result, String CaseId) {
        if (GlobalVariables != null && GlobalVariables.trim().length() > 0 && GlobalVariables.contains("$")) {
            logger.info("得到json全局值：" + GlobalVariables);
            String[] GlobalVariables_Slips = GlobalVariables.split(",");
            for (int i = 0; i < GlobalVariables_Slips.length; i++) {
                System.out.println("分割后的值：" + GlobalVariables_Slips[i]);
                Object GlobalVariables_value = JSONPath.read(result, GlobalVariables_Slips[i]);
//                如果得到的返回值是整数类型或者是其他类型，就用toString,如果得到的是null就用if结构里面的(需要转成String类型的)
                if (GlobalVariables_value == null) {
                    String GlobalVariables_value_String = (String) GlobalVariables_value;
                    GlobalVariables_value_String = "null";
                    logger.info("上一个接口返回的设为全局变量：" + GlobalVariables_value_String);
//                添加用例编号区分用的哪条响应取的的值
//                    GlobalVariables_value_String = GlobalVariables_value_String.substring(1, GlobalVariables_value_String.length() - 1);
                    GlobalVariables_Slips[i] = CaseId + GlobalVariables_Slips[i];
                    GlobalVariables_Map.put(GlobalVariables_Slips[i], GlobalVariables_value_String);
                } else if (GlobalVariables_value != null) {
                    //捕获空指针异常如果获取的值没有就给GlobalVariables_value_String（请求参数赋值”“）
                    try {
                        String GlobalVariables_value_String = GlobalVariables_value.toString();
                        logger.info("上一个接口返回的设为全局变量：" + GlobalVariables_value_String);
//                添加用例编号区分用的哪条响应取的的值
                        GlobalVariables_Slips[i] = CaseId + GlobalVariables_Slips[i];
                        GlobalVariables_Map.put(GlobalVariables_Slips[i], GlobalVariables_value_String);
                    } catch (NullPointerException e) {
                        String GlobalVariables_value_String = "null";
                        logger.info("上一个接口返回的设为全局变量：" + GlobalVariables_value_String);
//                添加用例编号区分用的哪条响应取的的值
                        GlobalVariables_Slips[i] = CaseId + GlobalVariables_Slips[i];
                        GlobalVariables_Map.put(GlobalVariables_Slips[i], GlobalVariables_value_String);
                    }
                }
            }

        }
        return GlobalVariables_Map;
    }
}
