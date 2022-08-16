package aout_li_case;

import aout_li_requsetGetAndPost.BaseProcessor;
import aout_li_util.CaseUtil;
import org.testng.annotations.DataProvider;

public class HT_Order_Selecte extends BaseProcessor {
    @DataProvider
    public Object[][] datas() {
        Object[][] datas = CaseUtil.getCaseDdatasByApiId("2", cellNames);
        return datas;
        // TODO Auto-generated method stub

    }
}
