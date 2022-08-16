package aout_li_variable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatNumber {
    public static String creatID() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        int nian = Integer.valueOf(date.substring(0, 4));
        int nian1 = Integer.valueOf(date.substring(4, 8));
        int yue = Integer.valueOf(date.substring(8, 12));
        int ri = Integer.valueOf(date.substring(12, 14));
        int id = nian + nian1 + yue + ri;
        String Id = String.valueOf(id);
        System.out.println(id);
        return Id;
    }
}
