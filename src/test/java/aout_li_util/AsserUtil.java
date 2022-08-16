package aout_li_util;

import aout_li_requsetGetAndPost.BodyPost;
import aout_li_requsetGetAndPost.form_dataDoPost;
import aout_li_requsetGetAndPost.getDoGet;
import aout_li_requsetGetAndPost.getDoPost;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

/*如果响应结果和期望值一样就返回通过,不一样就返回实际结果
 * @author Administrator
 *expecteResponseData 期望结果
 *AssertResult 实际结果
 */
public class AsserUtil {
    public static Logger logger = Logger.getLogger(VariableUtil.class);

    public static String asssertEquals(String expecteResponseData, String AssertResult, String code) {
        Reporter.log("判断响应的数据状态是否是200");
        Assert.assertEquals(code, "200", "返回的不是200状态");  //Assert.assertEquals(actual,expected，“message1”)actual实际值和expected期望值比较,比较失败返回message1
        logger.info("断言判断响应的数据状态是否是200");

        String result = "通过";

        try {
            Assert.assertEquals(expecteResponseData, AssertResult);

        } catch (
                Throwable e) {
            // TODO: handle exception
            result = AssertResult;
        }
        return result;
    }

    public void asssertCode(int code) {
        String code2 = String.valueOf(code);
        Assert.assertEquals(code2, "200", "返回的不是200状态");  //Assert.assertEquals(actual,expected，“message1”)actual实际值和expected期望值比较,比较失败返回message1
        logger.info("断言判断响应的数据状态是否是200");
    }
}
