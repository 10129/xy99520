package com.hand.xy99.weixin.weChatServlet;
/**
 * Created by xieshuai on 2018/1/16.
 */
import com.hand.xy99.weixin.pojo.message.send.Text;
import com.hand.xy99.weixin.pojo.message.send.TextMessage;
import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weixin.util.common.CommonUtil;
import com.hand.xy99.weixin.util.UploadMediaApiUtil;
import com.hand.xy99.weixin.util.common.SendMessageUtil;
import com.hand.xy99.weixin.service.impl.SendMessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
          SendMessageUtil sendMessageUtil =new SendMessageUtil();
//        sendMessageUtil.sendTextMessageToUser("早上好！","o5KdN1hbbeN2fhkWRroji0LOtqOM");
          TextMessage textMessage=new TextMessage();
        textMessage.setTouser("o5KdN1hbbeN2fhkWRroji0LOtqOM");
//        textMessage.setSafe("");
//        textMessage.setTotag("");
        textMessage.setMsgtype("text");
        Text text =new Text();
        text.setContent("哈哈哈！");
        textMessage.setText(text);
//        textMessage.setAgentid();
//        textMessage.setToparty("");
//        sendMessageUtil.sendMessage(textMessage);
        //获取请求路径
//        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
//        sendMessageUtil.sendTextMessageToUser("下午好！"+user.getNickname(),user.getOpenId());
        //        sendMessageService.sendMessage(access_token,textMessage);
//        SendMessageUtil sendMessageUtil =new SendMessageUtil();
//        sendMessageUtil.sendTextMessage();
//        sendMessageUtil.connectWeiXinInterface(action,xml);
//        sendMessageUtil.sendNewsToUser("o5KdN1hbbeN2fhkWRroji0LOtqOM");
//        sendMessageUtil.sendPicOrVoiceMessageToUser("cuy8FdvowpQyKE5Q3XJYj4pxeagBb1L8Qs1GEz12qyar0iNHulufdqZ0CALZVzYj","o5KdN1hbbeN2fhkWRroji0LOtqOM",REQ_MESSAGE_TYPE_IMAGE);
//获取所有关注的用户信息并发送文字消息
//        AdvancedUtil tChatUserService = new AdvancedUtil();
//        List<UserInfo> users= tChatUserService.getUserInfo(tChatUserService.getUserOpenId(access_token, ""));
//
//        for(int i=0;i<users.size();i++){
//            UserInfo user=users.get(i);
//            sendMessageUtil.sendTextMessageToUser("下午好！"+user.getNickname(),user.getOpenId());
//        }
//END
        String toUser="o5KdN1hbbeN2fhkWRroji0LOtqOM";
        SendMessageService sendMessageService=new SendMessageService();
//        sendMessageService.sendTextMessageToUser("下午好",toUser);
//        sendMessageService.sendPicOrVoiceMessageToUser("cuy8FdvowpQyKE5Q3XJYj4pxeagBb1L8Qs1GEz12qyar0iNHulufdqZ0CALZVzYj",toUser,"image");
//        sendMessageService.sendPicOrVoiceMessageToUser("",toUser,"");
//        sendMessageService.sendNewsToUser(toUser);
        //0.设置消息内容
        String media_id="XZGjf-nyUEOZ2e59bo1GEcS21GrU6u0MJtrDIYyuwugsUPoHWsTSpYZmnR5Fbusj";
        sendMessageService.sendVideoMessage(media_id,toUser);
//        sendMessageService.sendTextcardMessage(toUser);
    }
}
