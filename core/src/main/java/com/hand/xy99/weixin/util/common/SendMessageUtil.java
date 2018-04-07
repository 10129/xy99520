package com.hand.xy99.weixin.util.common;

import com.google.gson.Gson;
import com.hand.xy99.weixin.pojo.message.send.BaseMessage;
import com.hand.xy99.weixin.util.common.CommonUtil;
import com.hand.xy99.weixin.weChatServlet.AccessTokenServlet;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hand on 2018/1/20.
 */
public class SendMessageUtil {

    /**
     * @desc ：0.公共方法：发送消息
     * @param message void
     */
    public void sendMessage(BaseMessage message){
        //1.获取json字符串：将message对象转换为json字符串
        Gson gson = new Gson();
        String json =gson.toJson(message);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonTextMessage:"+json);
        //获取accessToken
        String accessToken =AccessTokenServlet.getAccessToken(CommonUtil.APPID, CommonUtil.APP_SECRET).getAccessToken();
        //获取请求路径
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
        System.out.println("json:"+json);
        try {
            connectWeiXinInterface(action,json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 连接请求微信后台接口
     * @param action 接口url
     * @param json  请求接口传送的json字符串
     */
    public  void connectWeiXinInterface(String action,String json){
        URL url;
        try {
            url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(json.getBytes("UTF-8"));// 传入参数
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String result = new String(jsonBytes, "UTF-8");
            System.out.println("请求返回结果:"+result);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
