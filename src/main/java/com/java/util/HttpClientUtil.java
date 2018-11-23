/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HttpClientTest
 * Author:   adm
 * Date:     2018/11/20 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.java.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author adm
 * @create 2018/11/20
 * @since 1.0.0
 */
@Slf4j
public class HttpClientUtil {
    public static void doGet(HashMap<String,String> params,String url)  {//使用URIbuilder构建url
        GetMethod getMethod=null;
        try {
            HttpClient httpClient=new HttpClient();
            URIBuilder builder=new URIBuilder(url);
            Set<String> keys = params.keySet();
            for(String key:keys){
                builder.setParameter(key,params.get(key));
            }
            getMethod = new GetMethod(builder.build().toString());
            int status = httpClient.executeMethod(getMethod);
            if(status!=HttpStatus.SC_OK){
                System.out.println("错误信息："+getMethod.getStatusLine());
            }
//            byte[] body = getMethod.getResponseBody();
//            System.out.println(new String(body));

//            String body = getMethod.getResponseBodyAsString();
//            System.out.println(body);

            InputStream bodyAsStream = getMethod.getResponseBodyAsStream();//针对处理大量数据时使用
            String resBody = IOUtils.toString(bodyAsStream, "utf-8");
            System.out.println(resBody);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            getMethod.releaseConnection();
        }
    }

    public static void doGet02(HashMap<String,String> params,String url){//拼接url
        try {
            HttpClient httpClient=new HttpClient();
            List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
            Set<String> keys = params.keySet();
            for(String key:keys){
                paramsList.add(new BasicNameValuePair(key,params.get(key)));
            }
            String getURL=EntityUtils.toString(new UrlEncodedFormEntity(paramsList,"UTF-8"));
            System.out.println(getURL);
            HttpGet request=new HttpGet(url+"?"+getURL);
            System.out.println(request.getURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param params  url参数
     * @param url 请求url
     */
    public static void doGet03(HashMap<String,String> params,String url) {//httpclient4.x
            org.apache.http.client.HttpClient httpClient=null;
        try {
            httpClient = new DefaultHttpClient();
            URIBuilder builder=new URIBuilder(url);
            Set<String> keys = params.keySet();
            for(String key:keys){
                builder.setParameter(key,params.get(key));
            }
            HttpGet httpGet=new HttpGet(builder.build().toString());
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode!=HttpStatus.SC_OK){
                System.out.println(httpResponse.getStatusLine());
            }
            HttpEntity entity = httpResponse.getEntity();
            //处理返回数据
            String resBody = EntityUtils.toString(entity, "utf-8");
            System.out.println(resBody);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
    }


    public static void doPostWithJson(JSONObject params,String url){
            org.apache.http.client.HttpClient httpClient=null;
        try {
            httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setHeader("Authorization","Bearer fdab6d9c-d19e-471c-917f-2321ab4933f2");
            StringEntity entity = new StringEntity(params.toJSONString(), "utf-8");
//            entity.setContentType("application/json");//头和实体里的contentType选其一即可
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!=HttpStatus.SC_OK){
                System.out.println("请求出错，错误原因："+response.getStatusLine());
            }
            HttpEntity ResEntity = response.getEntity();
            //处理返回数据
            String resBody = EntityUtils.toString(ResEntity, "utf-8");
            System.out.println(resBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }


    /**
     * @param url token请求路径
     * @param userName
     * @param passWord
     */
    public static String doPostToken(String url,String userName,String passWord){
        org.apache.http.client.HttpClient httpClient=null;
        String resBody="";
        try {
            httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String value ="Basic "+ new BASE64Encoder().encode((userName + ":" + passWord).getBytes());
            httpPost.setHeader("Authorization",value);
            List<NameValuePair> list=new ArrayList<>();
            list.add(new BasicNameValuePair("grant_type","client_credentials"));
            list.add(new BasicNameValuePair("scope","write"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"utf-8");
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode!=HttpStatus.SC_OK){
                log.error("token请求失败，失败原因{}",httpResponse.getStatusLine());
            }
            HttpEntity ResEntity = httpResponse.getEntity();
            //处理返回数据
            resBody = EntityUtils.toString(ResEntity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return resBody;
    }


    public static void main(String[] args) {
        /*HashMap params=new HashMap();
        params.put("key","d5297b5b745eba2ded1abb3fe0b86fbb");
        String url="http://v.juhe.cn/historyWeather/province";
        doGet03(params,url);*/

       /*String url="http://stage.huilianyi.com/api/open/flight/transactions";
       JSONObject params=new JSONObject();
       params.put("CompanyOID","b59da2bd-0b2c-4e65-8d24-6abd3fa1dac2");
       params.put("BatchNo","20180701");
       params.put("DateFrom","2018-01-01 00:00:00");
       params.put("DateTo","2018-01-01 00:00:00");
       params.put("Page","0");
       params.put("Size","1");
       doPostWithJson(params,url);*/

//       String url="http://stage.huilianyi.com/oauth/token";

        HashMap params=new HashMap();
        params.put("key1","value1");
        params.put("key2","value2");
        params.put("key3","value3");
        String url="http://v.juhe.cn/historyWeather/province";
        doGet02(params,url);
    }
}

