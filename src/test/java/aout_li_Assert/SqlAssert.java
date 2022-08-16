package aout_li_Assert;

import aout_li_util.VariableUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class SqlAssert {
    public static Logger logger = Logger.getLogger(SqlAssert.class);

    public static void assertEqualsSql(String CaseId, String afterValidateResult, String apiId) {
        logger.info("sql查询之后的数据结果" + afterValidateResult);
        if (CaseId.equals("3")) {
            String afterValidateResult1 = afterValidateResult.substring(1, afterValidateResult.length() - 1);
            //            因为拿到的是数组类型的，需要用obj接收，然后转string
            Object status = JSONPath.read(afterValidateResult, "$..status");
            String statusStr = status.toString();
            Assert.assertEquals(statusStr, "[1]", "红包提交了没有结算");
        }
        if (CaseId.equals("4")) {
            Object remark = JSONPath.read(afterValidateResult, "$..remark");
            String remarkStr = remark.toString();
            if (remarkStr != null) {
                remarkStr = "有数据";
            }
            Assert.assertEquals(remarkStr, "有数据", "没有积分");
        }

        if (CaseId.equals("5")) {
            Object investor = JSONPath.read(afterValidateResult, "$..investor");
            String investorStr = investor.toString();
            investorStr = investorStr.substring(1, investorStr.length() - 1);
            double investorDble = Double.valueOf(investorStr);
            if (investorDble > 0) {
                investorStr = "有分润";
            }
            Assert.assertEquals(investorStr, "有分润", "没有投资分润");
        }
    }
}
