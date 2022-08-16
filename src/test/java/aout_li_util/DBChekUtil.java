package aout_li_util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import pojo.DBCheker;
import pojo.DBQueryResult;

public class DBChekUtil {

	/**
	 * 根据脚本查询并返回查询结果
	 * 
	 * @param ValidateSql 传入的sql脚本
	 * @return json格式类型
	 */
	public static String doQuery(String ValidateSql) {
//		将json数据转成list集合
		List<DBCheker> dbchekers = JSONObject.parseArray(ValidateSql, DBCheker.class);
		List<DBQueryResult> dbQueryResults = new ArrayList<DBQueryResult>();
		for (DBCheker dbcheker : dbchekers) {
//			拿到sql的编号
			String no = dbcheker.getNo();
			String sql = dbcheker.getSql();
//        	执行查询,获得结果,JDBCYtil,数据库链接工具类
			Map<String, Object> columnLableAndValues = JDBCUtil.query(sql);
			DBQueryResult dbQueryResult = new DBQueryResult();
			dbQueryResult.setNo(no);
			dbQueryResult.setColumenLableAndValue(columnLableAndValues);
			dbQueryResults.add(dbQueryResult);
		}
//        将list数据再装换成json格式类型
		return JSONObject.toJSONString(dbQueryResults);
	}

}
