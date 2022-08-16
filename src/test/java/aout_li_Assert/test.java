package aout_li_Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class test {
    public static int phone;

    public static void main(String[] args) throws IOException {
        p();
    }

    public static int p() {


        Random random = new Random();
        phone = random.nextInt(154);
        String S = String.valueOf(phone);
        int sum=0;
        List<Double> po=new ArrayList<Double>();
        System.out.println("S:"+S);
        for (int i = 0; i < S.length(); i++) {
            double[] dou=new double[S.length()];
            double d = Double.valueOf(S.substring(i, i + 1));
            dou[i] = d;
            int l = S.length();
            double len = Double.valueOf(l);
            double[] dm=new double[S.length()];
            dm[i] = Math.pow(dou[i], len);
            po.add(dm[i]);
        }
        for (int j=0; j<po.size();j++){
            double in=po.get(j);
             int in1=(int) in;
            sum+=in1;
        }
        if (sum==phone){
            System.out.println("他们是自恋数字:"+sum);
        }else {
            System.out.println("他们不是自恋数字:"+sum);
        }
        return phone;
    }

}
