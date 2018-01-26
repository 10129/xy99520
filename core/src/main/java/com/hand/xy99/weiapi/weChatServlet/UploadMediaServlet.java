package com.hand.xy99.weiapi.weChatServlet;
/**
 * Created by xieshuai on 2018/1/16.
 */
import com.hand.xy99.weiapi.util.CommonUtil;
import com.hand.xy99.weiapi.util.MessageUtil;
import com.hand.xy99.weiapi.util.ReqMessageUtil;
import com.hand.xy99.weixin.message.req.TextMessage;
import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weiapi.message.httpclient.WeixinAPIHelper;
import com.hand.xy99.weiapi.util.UploadMediaApiUtil;
import com.hand.xy99.weixin.pojo.UserInfo;
import com.hand.xy99.weixin.util.AdvancedUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.hand.xy99.weiapi.util.MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
import static com.hand.xy99.weiapi.util.MessageUtil.REQ_MESSAGE_TYPE_VOICE;

/**
 * 上传素材servlet
 */
@WebServlet(name = "uploadMediaServlet")
public class UploadMediaServlet extends HttpServlet {
    @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
        String appId = CommonUtil.APPID;
        String appSecret = CommonUtil.APP_SECRET;
        Map<String,Object> accessTokenMap1=CommonUtil.accessTokenMap;
        String accessToken = AccessTokenInfo.accessToken.getAccessToken();
//        String filePath = "E:\\垃圾\\素材\\1237.mp4";
//        File file = new File(filePath);
//        String type = "VIDEO";
//        JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type);
//        System.out.println(jsonObject.toString());
          WeixinAPIHelper weixinAPIHelper =new  WeixinAPIHelper();
//        weixinAPIHelper.sendTextMessageToUser("早上好！","o5KdN1hbbeN2fhkWRroji0LOtqOM");
          TextMessage textMessage=new TextMessage();
          textMessage.setContent("ssss");
          textMessage.setToUserName("o5KdN1hbbeN2fhkWRroji0LOtqOM");
          textMessage.setMsgType("text");
          String xml=ReqMessageUtil.messageToXml(textMessage);
        String access_token = AccessTokenInfo.accessToken.getAccessToken();

        //获取请求路径

        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;

//        weixinAPIHelper.connectWeiXinInterface(action,xml);
//        weixinAPIHelper.sendNewsToUser("o5KdN1hbbeN2fhkWRroji0LOtqOM");
//        weixinAPIHelper.sendPicOrVoiceMessageToUser("cuy8FdvowpQyKE5Q3XJYj4pxeagBb1L8Qs1GEz12qyar0iNHulufdqZ0CALZVzYj","o5KdN1hbbeN2fhkWRroji0LOtqOM",REQ_MESSAGE_TYPE_IMAGE);
//获取所有关注的用户信息并发送文字消息
//        AdvancedUtil tChatUserService = new AdvancedUtil();
//        List<UserInfo> users= tChatUserService.getUserInfo(tChatUserService.getUserOpenId(access_token, ""));
//
//        for(int i=0;i<users.size();i++){
//            UserInfo user=users.get(i);
//            weixinAPIHelper.sendTextMessageToUser("下午好！"+user.getNickname(),user.getOpenId());
//        }
//END
    }
}
