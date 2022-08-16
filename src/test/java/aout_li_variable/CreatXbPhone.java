package aout_li_variable;

import aout_li_util.JDBCUtil;

import java.util.Map;
import java.util.Random;

public class CreatXbPhone {
    public static String pp;

    public static Map<String, Object> phone2() {
        Random random = new Random();
        int phone = random.nextInt(2000);
        String sql = "SELECT username as phone,is_auth as is_auth FROM `member`.`life_member` WHERE `id` = '" + phone + "'";
        Map<String, Object> columnLableAndValues = JDBCUtil.query(sql);
//		 返回结果
        return columnLableAndValues;
    }

    public static String phone1() {
        try {
            for (; ; ) {
                Map<String, Object> columnLableAndValues = phone2();
                int is_auth = Integer.parseInt(columnLableAndValues.get("is_auth").toString());
                if (is_auth == 1 && columnLableAndValues.get("phone").toString().length() > 0) {
                    pp = columnLableAndValues.get("phone").toString();
                    break;
                }
            }
        } catch (NullPointerException e) {
            phone1();
        }
        return pp;
    }
}