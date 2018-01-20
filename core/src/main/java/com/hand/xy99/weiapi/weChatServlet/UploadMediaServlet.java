package com.hand.xy99.weiapi.weChatServlet;
/**
 * Created by xieshuai on 2018/1/16.
 */
import com.alibaba.fastjson.JSONObject;
import com.hand.xy99.weiapi.dto.AccessTokenInfo;
import com.hand.xy99.weiapi.util.UploadMediaApiUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
        String appId = AccessTokenServlet.APPID;
        String appSecret = AccessTokenServlet.APP_SECRET;
        Map<String,Object> accessTokenMap1=AccessTokenServlet.accessTokenMap;
        String accessToken = AccessTokenInfo.accessToken.getToken();
//        {"media_id":"ic9BXM7j4a07SPHzRdgeCh4LE0RvXghkvF-wDr1lNSb-nG83ScU2wlOOtYxPqhM1","created_at":1516445859,"type":"image"}
//        String filePath = "E:\\垃圾\\素材\\1237.png";
        String filePath = "E:\\垃圾\\素材\\1237.mp4";
        File file = new File(filePath);
//        String type = "IMAGE";
        String type = "VIDEO";
        JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type);
        System.out.println(jsonObject.toString());
    }
}
