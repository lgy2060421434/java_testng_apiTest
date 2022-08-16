package aout_li_requsetGetAndPost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONPath;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

//无请求头的get请求方法
public class getDoGet {
    public static Logger logger = Logger.getLogger(getDoGet.class);
    public static String result_Code = null;
    public static Map<String, String> tokens = new HashMap<String, String>();
    public static int code1;
    public static Map<String, String> result_code = new HashMap<String, String>();
    //    存储token的键值对
    public static Map<String, String> doGet(String url, Map<String, Object> params, String isNeedToken, String CaseId) {
        int make = 1;
        Set<String> keys = params.keySet();
        for (Object name : keys) {
            Object value = params.get(name);
            if (make == 1) {
                url += "?" + name + "=" + value;

            } else {
                url += "&" + name + "=" + value;
            }
            make++;

        }
        try {

            logger.info("接口的请求地址:" + url);
            HttpGet get = new HttpGet(url);
            //            发送亲求前判断IsNeedToken是取Token还是赋值token
            if (isNeedToken.trim().length() > 0 && isNeedToken != null) {
                if (!isNeedToken.substring(0, 1).equals("$")) {
                    int isNeedToken_int = Integer.parseInt(isNeedToken.substring(0, 1));
                    if (isNeedToken_int > 0) {
                        addTokenBeforeResponse(get, isNeedToken);
                    }
                }
            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(get);
//			拿到响应的code状态码
            code1 = response.getStatusLine().getStatusCode();
            String code=String.valueOf(code1);
            String result = EntityUtils.toString(response.getEntity());
            logger.info("得到响应的状态码：" + code1);
//		用阿里的json进行http请求数据返回json中string字段包含unicode的转码
            JSONObject jsonObject = JSONObject.parseObject(result);
            result = jsonObject.toJSONString();
//            如果需要取token就取token
            if (isNeedToken != null && isNeedToken.trim().length() > 0 && isNeedToken.substring(0, 1).equals("$")) {
                addToken(result, isNeedToken, CaseId);
            }
            result_Code = result;
            result_code.put("result",result);
            result_code.put("code",code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result_code;

    }

    //      重写doget方法,有请求头时的get方法
    public static Map<String, String> doGet(String url, Map<String, String> params, Map<String, String> heder, String isNeedToken, String CaseId) {
//    	  定义是否是只有一个参数的地址拼接
        int make = 1;
        Set<String> keys = params.keySet();
        for (String name : keys) {
            String value = params.get(name);
            if (make == 1) {
                url += "?" + name + "=" + value;

            } else {
                url += "&" + name + "=" + value;
            }
            make++;

        }
        try {
            System.out.println(url);
            HttpGet get = new HttpGet(url);
            //            发送亲求前判断IsNeedToken是取Token还是赋值token
            if (isNeedToken.trim().length() > 0 && isNeedToken != null) {
                if (!isNeedToken.substring(0, 1).equals("$")) {
                    int isNeedToken_int = Integer.parseInt(isNeedToken.substring(0, 1));
                    if (isNeedToken_int > 0) {
                        addTokenBeforeResponse(get, isNeedToken);
                    }
                }
            }
//           循环给请求头加值
            Set<String> kyes = heder.keySet();
            for (String name : kyes) {
                String value = heder.get(name);
                get.addHeader(name, value);

            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(get);
            code1 = response.getStatusLine().getStatusCode();
            String code=String.valueOf(code1);
            logger.info("得到响应的状态码：" + code1);
            String result = EntityUtils.toString(response.getEntity());
//		用阿里的json进行http请求数据返回json中string字段包含unicode的转码
            JSONObject jsonObject = JSONObject.parseObject(result);
            result = jsonObject.toJSONString();
            //            如果需要取token就取token
            if (isNeedToken != null && isNeedToken.trim().length() > 0 && isNeedToken.substring(0, 1).equals("$")) {
                addToken(result, isNeedToken, CaseId);
            }
            result_Code = result;
            result_code.put("result",result);
            result_code.put("code",code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result_code;
    }

    //请求之前判断是否有token
    public static void addTokenBeforeResponse(HttpRequest request, String isNeedToken) {
        String token = tokens.get(isNeedToken);
        //        如果token是get接口请求的，就拿get方法里面的tokens.token
        if (token == null) {
            token = getDoPost.tokens.get(isNeedToken);
        }
        if (token != null) {
            token = "Bearer" + " " + token;
            logger.info("接口鉴权：" + token);
            request.setHeader("Authorization", token);
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

}