package aout_li_variable;

import aout_li_util.JDBCUtil;

import java.util.Map;

/*
 * 生成机器编号*/
public class CreteSerial_number {
    public String Serial_number() {
        String sql = "SELECT MAX(serial_number)+1 as serial_number FROM `retail`.`retail_equipment_serial_number`";
        Map<String, Object> columnLableAndValues = JDBCUtil.query(sql);
        return columnLableAndValues.get("serial_number").toString();
    }
}
