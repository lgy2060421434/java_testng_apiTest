package aout_li_requsetGetAndPost;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class form_dataDoPost {
    public static Logger logger = Logger.getLogger(form_dataDoPost.class);
    //    存储cookies的键值对
    public static Map<String, String> cookies = new HashMap<String, String>();
    public static Map<String, String> result_code = new HashMap<String, String>();
    //    存储token的键值对
    public static Map<String, String> tokens = new HashMap<String, String>();
    public static String result = null;
    public static int code1;

    public static Map<String, String> doPost(String url, Map<String, Object> params, String isNeedToken, String CaseId, String fileUp) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        Set<String> keys = params.keySet();
        for (String name : keys) {
            Object value = params.get(name);
            String value1 = value.toString();
            builder.addTextBody(name, value1, ContentType.TEXT_PLAIN);
        }
        //builder.setCharset(Charset.forName("uft-8"));//设置请求的编码格式
//        builder.addTextBody("msg_id", "1029", ContentType.TEXT_PLAIN);
//        builder.addTextBody("lib_id", "1", ContentType.TEXT_PLAIN);
//        builder.addTextBody("person_name", "xiaoming", ContentType.TEXT_PLAIN);


        try {
            if (fileUp != null && fileUp.trim().length() > 0) {
                // 把文件加到HTTP的post请求中
                String[] fileUp_Split = fileUp.split(",");
                for (int i = 0; i < fileUp_Split.length; i++) {
//                    文件类型
                    String fileUpS = fileUp_Split[i].substring(0, fileUp_Split[i].indexOf(":"));
//                    文件路径
                    String fileUpUrl = fileUp_Split[i].substring(fileUp_Split[i].indexOf(":") + 1, fileUp_Split[i].length());
                    File f = new File(fileUpUrl);
                    builder.addBinaryBody(
                            fileUpS,
                            new FileInputStream(f),
                            ContentType.APPLICATION_OCTET_STREAM,
                            f.getName()
                    );
                }
            }
//            发送亲求前判断IsNeedToken是取Token还是赋值token
            if (isNeedToken.trim().length() > 0 && isNeedToken != null) {
                if (!isNeedToken.substring(0, 1).equals("$")) {
                    int isNeedToken_int = Integer.parseInt(isNeedToken.substring(0, 1));
                    if (isNeedToken_int > 0) {
                        addTokenBeforeResponse(post, isNeedToken);
                    }
                }
            }

            HttpClient client = HttpClients.createDefault();
            HttpEntity multipart = builder.build();
            post.setEntity(multipart);
            HttpResponse response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            code1 = response.getStatusLine().getStatusCode();
            String code=String.valueOf(code1);
            logger.info("这是状态码呀" + code1);
            result = jsonObject.toJSONString();
            result_code.put("result",result);
            result_code.put("code",code);

            //            如果需要取token就取token
            if (isNeedToken != null && isNeedToken.trim().length() > 0 && isNeedToken.substring(0, 1).equals("$")) {
                addToken(result, isNeedToken, CaseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回最终的请求结果
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
