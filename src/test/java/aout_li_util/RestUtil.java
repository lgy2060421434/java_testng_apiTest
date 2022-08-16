package aout_li_util;

import java.util.ArrayList;
import java.util.List;

import pojo.Rest;

public class RestUtil {
	public static List<Rest> relist = new ArrayList<Rest>();
	static {
		List<Rest> list = ExcelUtil.lode(PropertiesUtil.getExcelPath(), "接口信息", Rest.class);
		relist.addAll(list);
	}

	/**
	 * @param apiId,根据接口编号拿到接口地址
	 * @return
	 */
	public static String getUrlByApiId(String apiId) {
		for (Rest rest : relist) {
			if (rest.getApiId().equals(apiId)) {
				return rest.getApiAdrivd();
			}
		}

		return "";

	}

	/**根据接口编号拿到接口提交方式
	 * @param apiId
	 * @return
	 */
	public static String getTypeByApiId(String apiId) {
		for (Rest rest : relist) {
			if (rest.getApiId().equals(apiId)) {
				return rest.getUpMath();
			}
		}
		return "";
	}
}
