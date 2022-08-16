package aout_li_Assert;

import aout_li_requsetGetAndPost.getDoPost;
import aout_li_util.VariableUtil;
import com.alibaba.fastjson.JSONPath;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

public class asserCase1 {
    //    引入log4j
    public static Logger logger = Logger.getLogger(VariableUtil.class);

    //断言
    public static void assertEquals2(String CaseId, String result, String apiId) {
//app端获取token断言
        if (CaseId.equals("1")) {
            Reporter.log("判断是否成功登录拿到token");
            String message = (String) JSONPath.read(result, "$.message");
            Assert.assertEquals(message, "success", "返回的不是success");
            logger.info("判断响应的token是否等于" + message);
//           拿到响应的code值断言
            Reporter.log("判断code是否等于0，等于0代表成功返回token");
            Object code = JSONPath.read(result, "$.code");
            String codeString = code.toString();
            Assert.assertEquals(codeString, "0");
            logger.info("判断响应的code是否是" + codeString);
        }
//补货列表查询断言
        if (apiId.equals("3")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }


        //机器列表断言
        if (apiId.equals("10")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-订单详情列表查询
        if (apiId.equals("15")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-商品列表展示
        if (apiId.equals("17")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-商品列表展示
        if (apiId.equals("18")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-问题反馈
        if (apiId.equals("20")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-缺货登记
        if (apiId.equals("21")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }


        if (apiId.equals("22")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("23")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        //小程序-缺货登记
        if (apiId.equals("24")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("25")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("26")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("27")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("28")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("29")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("30")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("31")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("32")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("33")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("35")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("36")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("37")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("38")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("39")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("40")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }

        if (apiId.equals("41")) {
            Reporter.log("判断是否成功查询");
            String code = (String) JSONPath.read(result, "$.code");
            Assert.assertEquals(code, "SUCCESS", "返回的不是success");
            logger.info("判断响应的token是否等于" + code);
        }
//   示例     if (CaseId.equals("6")) {
//            Reporter.log("判断响应的token是否有");
//            String message = (String) JSONPath.read(result, "$.message");
//            Assert.assertEquals(message, "success", "返回的不是success");  //Assert.assertEquals(actual,expected，“message1”)actual实际值和expected期望值比较,比较失败返回message1
//            logger.info("判断响应的token是否等于" + message);
//
//            Reporter.log("判断响应的数据状态是否是200");
//            int code = getDoPost.code1;
//            String code2 = String.valueOf(code);
//            Assert.assertEquals(code2, "200", "返回的不是200状态");  //Assert.assertEquals(actual,expected，“message1”)actual实际值和expected期望值比较,比较失败返回message1
//            logger.info("断言判断响应的数据状态是否是200");
//        }
    }
}
