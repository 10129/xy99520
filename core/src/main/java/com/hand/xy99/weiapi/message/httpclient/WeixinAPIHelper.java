package com.hand.xy99.weiapi.message.httpclient;

import com.google.gson.Gson;
import com.hand.xy99.weiapi.test.WeiXinUtil;
import com.hand.xy99.weiapi.util.CommonUtil;
import com.hand.xy99.weixin.message.resp.TextMessage;
import com.hand.xy99.weixin.message.send.BaseMessage;
import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weiapi.util.MessageUtil;
import com.hand.xy99.weiapi.weChatServlet.AccessTokenServlet;
import net.sf.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.hand.xy99.weiapi.util.MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
import static com.hand.xy99.weiapi.util.MessageUtil.REQ_MESSAGE_TYPE_TEXT;
import static com.hand.xy99.weiapi.util.MessageUtil.REQ_MESSAGE_TYPE_VOICE;

/**
 * Created by hand on 2018/1/20.
 */
public class WeixinAPIHelper {

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
     * 微信公共账号发送给账号
     * @param content 文本内容
     * @param toUser 微信用户
     * @return
     */

    public  void sendTextMessageToUser(String content,String toUser){

        String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";

        //获取access_token
        //获取accessToken
        AccessTokenInfo.accessToken= AccessTokenServlet.getAccessToken(CommonUtil.APPID, CommonUtil.APP_SECRET);

        String accessToken = AccessTokenInfo.accessToken.getAccessToken();

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
     * 微信公共账号发送给账号(本方法限制使用的消息类型是语音或者图片)
     * @param mediaId 图片或者语音内容
     * @param toUser 微信用户
     * @return
     */
    public  void sendPicOrVoiceMessageToUser(String mediaId,String toUser,String msgType){
        String json=null;
        if(msgType.equals(REQ_MESSAGE_TYPE_IMAGE)){
            json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"image\", \"image\": {\"media_id\": \""+mediaId+"\"}}";
        }else if(msgType.equals(REQ_MESSAGE_TYPE_VOICE)){
            json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"voice\", \"voice\": {\"media_id\": \""+mediaId+"\"}}";
        }
        //获取access_token
        String accessToken = AccessTokenInfo.accessToken.getAccessToken();
        //获取请求路径
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
        try {
            connectWeiXinInterface(action,json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  发送图文给所有的用户
     * @param openId 用户的id
     */
    public  void sendNewsToUser(String openId){

////        MediaUtil mediaUtil = MediaUtil.getInstance();
//
//        ArrayList<Object> articles = new ArrayList<Object>();
//
//        Article a = new Article();
//
//        articles.add(a);
//
//        String str = JsonUtil.getJsonStrFromList(articles);
//
//        String json = "{\"touser\":\""+openId+"\",\"msgtype\":\"news\",\"news\":" +
//
//                "{\"articles\":" +str +"}"+"}";
//
//        json = json.replace("picUrl", "picurl");
//
//        System.out.println(json);
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(openId);
        textMessage.setContent("早上好！");
        textMessage.setMsgType(REQ_MESSAGE_TYPE_TEXT);
        textMessage.setCreateTime(System.currentTimeMillis());
//        textMessage.setFromUserName();
//        textMessage.setMsgId();
       String json= MessageUtil.messageToXml(textMessage);
        //获取access_token

        String access_token = AccessTokenInfo.accessToken.getAccessToken();

        //获取请求路径

        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;

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
