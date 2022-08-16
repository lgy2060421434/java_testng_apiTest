package aout_li_variable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateSerialNumber {
    public String dataCreat(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        date=date+"XB";
        return  date;
    }
}
