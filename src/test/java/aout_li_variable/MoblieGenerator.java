package aout_li_variable;

import java.util.Map;

import aout_li_util.JDBCUtil;

public class MoblieGenerator {

	/**
	 * 生成待注册的手机号码
	 * 
	 * @return
	 */
	public String generateToBeRegisterMobile() {
		String sql = "SELECT CONCAT( max(username)+1,'') as toBeLoginUsername FROM `member`.`life_member`WHERE username";
		Map<String, Object> columnLableAndValues = JDBCUtil.query(sql);

		return columnLableAndValues.get("toBeLoginUsername").toString();

	}

	/**
	 * 生成注册过的手机号码
	 * 
	 * @return
	 */
	public String generateNoteToBeRegisterMobile() {
		String sql = "SELECT CONCAT( max(username)+2,'') as toBeLoginUsername FROM `member`.`life_member`WHERE username";
		Map<String, Object> columnLableAndValues = JDBCUtil.query(sql);
//		 返回结果
		return columnLableAndValues.get("toBeLoginUsername").toString();
	}

}
