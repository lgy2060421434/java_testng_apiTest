package aout_li_requsetGetAndPost;

import java.util.HashMap;
import java.util.Map;

import aout_li_Assert.SqlAssert;
import aout_li_Assert.asserCase1;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONPath;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import aout_li_util.AsserUtil;
import aout_li_util.DBChekUtil;
import aout_li_util.ExcelUtil;
import aout_li_util.PropertiesUtil;
import aout_li_util.RestUtil;
import aout_li_util.VariableUtil;
import pojo.WriteBackData;

import static aout_li_util.VariableUtil.variableNameAndValuesMap;

/**
 * 接口测试统一处理类
 *
 * @author Administrator
 */
public class BaseProcessor {

    public static Logger logger = Logger.getLogger(BaseProcessor.class);
    public static Map<String, String> GlobalVariables_Map = new HashMap<String, String>();
    public String[] cellNames = {"CaseId", "ApiId", "Params", "ExpecteResponseData", "PreValidateSql",
            "AfterValidateSql", "IsNeedToken", "GlobalVariables", "CaseDetil", "FileUp"};

    /**
     * @param CaseId              用例id
     * @param apiId               接口编号
     * @param paramsmeter         参数
     * @param expecteResponseData 期望响应数据
     * @param preValidateSql      测试执行前的sql脚本
     * @param afterValidateSql    测试执行后的sql脚本
     */
    @Test(dataProvider = "datas")
    public static void run(String CaseId, String apiId, String paramsmeter, String expecteResponseData, String preValidateSql,
                           String afterValidateSql, String isNeedToken, String GlobalVariables, String CaseDetil, String fileUp) {
        System.out.println("\n");
        logger.info("开始调用前的数据验证");
        logger.info("这是第" + CaseId + "条测试数据," + CaseDetil);
//		执行接口调用前验证数据
        if (preValidateSql != null && preValidateSql.trim().length() > 0) {
//			替换变量为参数
            preValidateSql = VariableUtil.replaceVariable(preValidateSql);
//			在腳本执行前查询我们想要验证的字段
            String preValidateResult = DBChekUtil.doQuery(preValidateSql);
            logger.info("用例测试前得到的查询结果:" + preValidateResult);
            ExcelUtil.writeBackData(PropertiesUtil.getExcelPath(), "用例", CaseId, "PreValidateResult",
                    preValidateResult);

        }
        logger.info("根据接口编号[" + apiId + "]获得请求地址");
        String url = RestUtil.getUrlByApiId(apiId);
//        拿到环境拼接到地址上面
        String Environment = variableNameAndValuesMap.get("${Environment}");
        if (Environment != null && Environment.trim().length() > 0) {
            url = variableNameAndValuesMap.get("${Environment}") + url;
        }
        logger.info("请求地址" + url);
        String type = RestUtil.getTypeByApiId(apiId);
        logger.info("根据接口编号获得请求类型" + type);

//        如果没有参数就让参数为{}
        if (paramsmeter == null || paramsmeter.trim().length() == 0) {
            paramsmeter = "{}";
        }
//		将变量替换成参数接收
        paramsmeter = VariableUtil.replaceVariable(paramsmeter);
        logger.info("替换变量" + paramsmeter);
//      如果入参包含[]就使用这个方法
        Map<String, String> result_code = ExcelUtil.upMath(paramsmeter, type, url, isNeedToken, CaseId, fileUp);
        String result = result_code.get("result");
        String code = result_code.get("code");
        logger.info("响应的数据和状态" + result);

//        如果GlobalVariables全局变量需要赋值，就拿到这个接口的结果的变量存到map集合里面方便调用
        GlobalVariables_Map = ExcelUtil.getGlobalVariables(GlobalVariables, result, CaseId);

//        对应用例编号进行断言
        asserCase1.assertEquals2(CaseId, result, apiId);
//		判断是否是通过的数据断言
        String results = AsserUtil.asssertEquals(expecteResponseData, result, code);
        // 实例化实体类回写数据对象
        WriteBackData backData = new WriteBackData("用例", CaseId, "ActualResponseData", results);
//        保存回写数据对象
        ExcelUtil.backDatas.add(backData);
        ExcelUtil.writeBackData(PropertiesUtil.getExcelPath(), "用例", CaseId, "ActualResponseData", results);
        logger.info("接口调用后的数据验证");
        if (afterValidateSql != null && afterValidateSql.trim().length() > 0) {
//			替换变量为参数
            afterValidateSql = VariableUtil.replaceVariable(afterValidateSql);
//			接口调用后我们想要查询的字段
            String afterValidateResult = DBChekUtil.doQuery(afterValidateSql);
//          sql数据校验
            SqlAssert.assertEqualsSql(CaseId, afterValidateResult, apiId);
            ExcelUtil.writeBackData(PropertiesUtil.getExcelPath(), "用例", CaseId, "AfterValidateResult",
                    afterValidateResult);

        }

    }

//	在所有的执行完后执行

    /**
     * 批量回写的数据
     */
    @AfterSuite
    public void batchWriteBackDates() {
        logger.info("批量保存回写数据");
        ExcelUtil.batchWriteBackDates(PropertiesUtil.getExcelPath());
    }

}
