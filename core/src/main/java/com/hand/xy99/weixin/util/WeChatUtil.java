package com.hand.xy99.weixin.util;

import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weixin.pojo.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by xieshuai on 2018/1/25.
 */
public class WeChatUtil {
    static Logger log = LoggerFactory.getLogger(WeChatUtil.class);
    Token mToken = AccessTokenInfo.accessToken;

    /**
     * HTTPS请求Get方法调用
     * @param path
     * @param requestData
     * @return
     */
    public static String doHttpsGet(String path, String requestData) throws HttpException, IOException
    {
        // 创建https请求，未默认证书，可自行添加
        // 设置编码
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(path);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        httpClient.executeMethod(getMethod);

        // 读取内容
        byte[] responseBody = getMethod.getResponseBody();
        String strResp = new String(responseBody, "UTF-8");

        System.out.println(strResp);

        getMethod.releaseConnection();

        return strResp;
    }

    /**
     * HTTPS请求Post方法调用
     * @param path
     * @param requestData
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String doHttpsPost(String path, String requestData) throws HttpException, IOException
    {
        // 创建https请求，未默认证书，可自行添加
        String strResp ="";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(path);
        // 设置编码
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        System.out.println("path:" + path);
        System.out.println("requestData:" + requestData);

        postMethod.setRequestBody(requestData);

        long start = System.currentTimeMillis();
        // 执行getMethod
        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        // 失败
        if (statusCode != HttpStatus.SC_OK)
        {
            System.out.println("Method failed: " + postMethod.getStatusLine());
            // 读取内容
            byte[] responseBody = postMethod.getResponseBody();
            // 处理内容
            strResp = new String(responseBody, "UTF-8");
            System.out.println(strResp);
        }
        else
        {
            // 读取内容
            byte[] responseBody = postMethod.getResponseBody();
            strResp = new String(responseBody, "UTF-8");
            System.out.println("服务器返回:" + strResp);
        }

        postMethod.releaseConnection();

        return strResp;
    }
    public static Map<String, Object> jsonToMap(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<JSONObject> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = it.next();
                    list.add(jsonToMap(json2.toString()));
                }
                map.put(k.toString(), list);
                log.info("k.toString()="+k.toString()+"list="+list);
            } else {
                map.put(k.toString(), v);
                log.info("k.toString()="+k.toString()+"v="+v);
            }
        }
        return map;
    }

    public static String mapToJson(Map<String, String> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = (String) it.next();
            value = map.get(key);
            jsonBuffer.append(key + ":" +"\""+ value+"\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }
}
