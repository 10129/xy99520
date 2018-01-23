package com.hand.xy99.weiapi.weChatServlet;
/**
 * Created by xieshuai on 2018/1/16.
 */
import com.hand.xy99.weiapi.util.CommonUtil;
import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weiapi.message.httpclient.WeixinAPIHelper;
import com.hand.xy99.weiapi.util.UploadMediaApiUtil;
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
        WeixinAPIHelper weixinAPIHelper =new  WeixinAPIHelper();
        weixinAPIHelper.sendTextMessageToUser("早上好！","");
    }
}
