package aout_li_requsetGetAndPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONPath;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;


public class getDoPost {
    public static Logger logger = Logger.getLogger(getDoPost.class);
    //    存储cookies的键值对
    public static Map<String, String> cookies = new HashMap<String, String>();
    //    存储token的键值对
    public static Map<String, String> tokens = new HashMap<String, String>();
    public static Map<String, String> result_code = new HashMap<String, String>();
    public static String result = null;
    public static int code1;

    public static Map<String, String> doPost(String url, Map<String, Object> params, String isNeedToken, String CaseId) {
        // TODO Auto-generated constructor stub
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        Set<String> keys = params.keySet();
        for (String name : keys) {
            Object value = params.get(name);
            if (value == null) {
                String value1 = (String) value;
                parameters.add(new BasicNameValuePair(name, value1));
            } else if (value != null) {
                String value1 = value.toString();
                parameters.add(new BasicNameValuePair(name, value1));
            }
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
            HttpClient client = HttpClients.createDefault();
//            发送亲求前判断IsNeedToken是取Token还是赋值token
            if (isNeedToken.trim().length() > 0 && isNeedToken != null) {
                if (!isNeedToken.substring(0, 1).equals("$")) {
                    int isNeedToken_int = Integer.parseInt(isNeedToken.substring(0, 1));
                    if (isNeedToken_int > 0) {
                        addTokenBeforeResponse(post, isNeedToken);
                    }
                }
            }

//			在发送请求前,判断是否有cookie
            addCookieBeforRespons(post);

            HttpResponse response = client.execute(post);
//			拿到响应结果找到是否有sessionid,有就保存cookie
            addCookie(response);
            code1 = response.getStatusLine().getStatusCode();
            String code = String.valueOf(code1);
            logger.info("这是状态码呀" + code1);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
//			用阿里的json进行http请求数据返回json中string字段包含unicode的转码
            JSONObject jsonObject = JSONObject.parseObject(result);
            result = jsonObject.toJSONString();
            result_code.put("result", result);
            result_code.put("code", code);
//            如果需要取token就取token
            if (isNeedToken != null && isNeedToken.trim().length() > 0 && isNeedToken.substring(0, 1).equals("$")) {
                addToken(result, isNeedToken, CaseId);
            }
//			result_Code = code1 + "\n" + result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result_code;

    }


    //请求之前判断是否有cookie
    public static void addCookieBeforRespons(HttpRequest requst) {
        // TODO Auto-generated method stub
//        拿到map集合里面得cookie得值
        String sessionIdCookie = cookies.get("JSESSIONID");
//		如果有cookies就在请求头里面添加Cookie
        if (sessionIdCookie != null) {
            requst.setHeader("Cookie", sessionIdCookie);
        }
    }


    //接口鉴权方法添加session会话到map集合容器里
    public static void addCookie(HttpResponse response) {
//         从响应头里取出名字
        Header setCookieHeader = response.getFirstHeader("Set-Cookie");
        if (setCookieHeader != null) {
            String cookieString = setCookieHeader.getValue();
//			 用来分割;号之前的cookie的值
            if (cookieString != null && cookieString.trim().length() > 0) {
                String[] cookieParis = cookieString.split(";");
                if (cookieParis != null) {
                    for (String cookiePari : cookieParis) {
//						如果有JSESSIONID,则说明响应头里面有会话id这个数据
                        if (cookiePari.contains("JSESSIONID")) {
//                    	   保存到map集合里面
                            cookies.put("JSESSIONID", cookiePari);
                        }
                    }
                }
            }
        }
    }

    public static void addToken(String result, String IsNeedToken, String CaseId) {
//         从响应头里取出名字
        String authorization = CaseId + IsNeedToken;
        String token = (String) JSONPath.read(result, IsNeedToken);
        if (token != null && token.trim().length() > 0) {
            tokens.put(authorization, token);
            logger.info("拿到参数token:" + token);
        }
    }

    //请求之前判断是否有token
    public static void addTokenBeforeResponse(HttpRequest request, String isNeedToken) {
        String token = tokens.get(isNeedToken);
        //        如果token是get接口请求的，就拿get方法里面的tokens.token
        if (token == null) {
            token = getDoGet.tokens.get(isNeedToken);
        }
        if (token != null) {
            token = "Bearer" + " " + token;
            logger.info("接口鉴权：" + token);
            request.setHeader("Authorization", token);
        }
    }

}
