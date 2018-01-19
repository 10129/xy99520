package com.hand.weiapi.weChatServlet;
/**
 *Created by xieshuai on 2018/1/15.
 */
import com.hand.weiapi.messagedto.image.Image;
import com.hand.weiapi.messagedto.image.ImageMessage;
import com.hand.weiapi.messagedto.music.Music;
import com.hand.weiapi.messagedto.music.MusicMessage;
import com.hand.weiapi.messagedto.news.NewsMessage;
import com.hand.weiapi.messagedto.news.item;
import com.hand.weiapi.messagedto.text.TextMessage;
import com.hand.weiapi.messagedto.video.Video;
import com.hand.weiapi.messagedto.video.VideoMessage;
import com.hand.weiapi.messagedto.voice.Voice;
import com.hand.weiapi.messagedto.voice.VoiceMessage;
import com.hand.weiapi.service.IWeixinService;
import com.hand.weiapi.weixinUtil.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Controller
public class weChatAccounts extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(weChatAccounts.class);
    @Autowired
    private IWeixinService weixinService;

    /*
    * 自定义token, 用作生成签名,从而验证安全性
    * */
    private final String TOKEN = "cherry";
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
//            ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//// TODO 接收、处理、响应由微信服务器转发的用户发送给公众帐号的消息
//// 将请求、响应的编码均设置为UTF‐8（防止中文乱码）
//        System.out.println("请求进入");
//        String result = "";
//        try {
//            Map<String,String> map = MessageUtil1.parseXml(req);
//            System.out.println("开始构造消息");
//            result = MessageUtil1.buildXml(map);
//            System.out.println(result);
//            if(result.equals("")){
//                result = "未正确响应";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("发生异常："+ e.getMessage());
//        }
//        resp.getWriter().println(result);
//    }
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
//        String xml=weixinService.processRequest(request);
    Map<String,String> xmlMap = MessageUtil.parseXml(request);
    String ToUserName = xmlMap.get("ToUserName");
    String FromUserName = xmlMap.get("FromUserName");
    String MsgType = xmlMap.get("MsgType");
    String xml="";
    if (MsgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
        // 用户发送的文本消息
        String content = xmlMap.get("Content");

        // 链接
        if (content.contains("csdn")) {
            TextMessage tm = new TextMessage();
            tm.setToUserName(FromUserName);
            tm.setFromUserName(ToUserName);
            tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            tm.setCreateTime(System.currentTimeMillis());
            tm.setContent("我的CSDN博客：<a href=\"http://my.csdn.net/qincidong\">我的CSDN博客</a>\n");
            xml = MessageUtil.textMessageToXml(tm);
        } else if (content.contains("图片")) {
            ImageMessage im = new ImageMessage();
            im.setToUserName(FromUserName);
            im.setFromUserName(ToUserName);
            im.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
            im.setCreateTime(System.currentTimeMillis());
            Image image = new Image();
            image.setMediaId("cuy8FdvowpQyKE5Q3XJYj4pxeagBb1L8Qs1GEz12qyar0iNHulufdqZ0CALZVzYj");
            im.setImage(image);
            xml = MessageUtil.imageMessageToXml(im);
        } else if (content.contains("音乐")) {
            MusicMessage im = new MusicMessage();
            im.setToUserName(FromUserName);
            im.setFromUserName(ToUserName);
            im.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
            im.setCreateTime(System.currentTimeMillis());
            Music music = new Music();
            String title = "亲爱的路人";
            String description = "\uE03E多听音乐 心情棒棒";
            String hqMusicUrl = "http://www.kugou.com/song/20qzz4f.html?frombaidu#hash=20C16B9CCCCF851D1D23AF52DD963986&album_id=0";
            music.setTitle(title);
            music.setDescription(description);
            music.setHQMusicUrl(hqMusicUrl);
            music.setMusicUrl(hqMusicUrl);
            im.setMusic(music);
            xml = MessageUtil.musicMessageToXml(im);
        } else if(content.contains("图文")){
            NewsMessage im = new NewsMessage();
            im.setToUserName(FromUserName);
            im.setFromUserName(ToUserName);
            im.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            im.setCreateTime(System.currentTimeMillis());
            item item1 =new item();
            String title1 = "HAP审计的实现和使用";
            String description1 = "由于HAP框架用的是Spring+SpringMVC+Mybatis，其中Mybatis中的拦截器可以选择在被拦截的方法前后执行自己的逻辑。所以我们通过拦截器实现了审计功能，当用户对某个实体类进行增删改操作时，拦截器可以拦截，然后将操作的数据记录在审计表中，便于用户以后审计。";
            String picUrl1 ="http://upload-images.jianshu.io/upload_images/7855203-b9e9c9ded8a732a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
            String textUrl1 = "http://blog.csdn.net/a1786223749/article/details/78330890";

            String title2 = "KendoUI之Grid的问题详解";
            String description2 = "kendoLov带出的值出现 null和undefined";
            String picUrl2 ="https://demos.telerik.com/kendo-ui/content/shared/images/theme-builder.png";
            String textUrl2 = "http://blog.csdn.net/a1786223749/article/details/78330908";
            item1.setTitle(title1);
            item1.setDescription(description1);
            item1.setPicUrl(picUrl1);
            item1.setUrl(textUrl1);
            item item2 =new item();
            item2.setTitle(title2);
            item2.setDescription(description2);
            item2.setPicUrl(picUrl2);
            item2.setUrl(textUrl2);
            List<item> itemList =new ArrayList<item>();
            itemList.add(item1);
            itemList.add(item2);
            im.setArticles(itemList);
            im.setArticleCount(2);
            xml = MessageUtil.newsMessageToXml(im);
        } else {
            // 响应
            TextMessage tm = new TextMessage();
            tm.setToUserName(FromUserName);
            tm.setFromUserName(ToUserName);
            tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            tm.setCreateTime(System.currentTimeMillis());
            tm.setContent("你好，你发送的内容是：\n" + content);

            xml = MessageUtil.textMessageToXml(tm);
        }
    }else if(MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
            VoiceMessage im = new VoiceMessage();
            im.setToUserName(FromUserName);
            im.setFromUserName(ToUserName);
            im.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
            im.setCreateTime(System.currentTimeMillis());
            Voice voice = new Voice();
            voice.setMediaId(xmlMap.get("MediaId"));
            im.setVoice(voice);
            xml = MessageUtil.voiceMessageToXml(im);
        }else if(MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
            VideoMessage im = new VideoMessage();
            im.setToUserName(FromUserName);
            im.setFromUserName(ToUserName);
            im.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
            im.setCreateTime(System.currentTimeMillis());
            Video video = new Video();
            video.setMediaId(xmlMap.get("MediaId"));
            video.setTitle("hahah");
            video.setDescription("还给你一个视频");
            im.setVideo(video);
            xml = MessageUtil.videoMessageToXml(im);
    }else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
        String event = xmlMap.get("Event");
        if (event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            // 订阅
            TextMessage tm = new TextMessage();
            tm.setToUserName(FromUserName);
            tm.setFromUserName(ToUserName);
            tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            tm.setCreateTime(System.currentTimeMillis());
            tm.setContent("你好，欢迎关注[程序员的生活]公众号！[愉快]/呲牙/玫瑰\n目前可以回复文本消息");
            xml = MessageUtil.textMessageToXml(tm);

        } else if (event.equals(MessageUtil.EVENT_TYPE_CLICK)) {
            String eventKey = xmlMap.get("EventKey");
            if (eventKey.equals("reply_words")) { // 点击了回复文字菜单
                TextMessage tm = new TextMessage();
                tm.setToUserName(FromUserName);
                tm.setFromUserName(ToUserName);
                tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                tm.setCreateTime(System.currentTimeMillis());
                tm.setContent("你好，你点击了回复文本菜单：\n");

                xml = MessageUtil.textMessageToXml(tm);

            }
        }
    }
    System.out.print(xml);
    PrintWriter out = response.getWriter();
    out.print(xml);
    out.close();

}
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        System.out.println("‐‐‐‐‐开始校验签名‐‐‐‐‐");
/**
 * 接收微信服务器发送请求时传递过来的参数
 */
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce"); //随机数
        String echostr = req.getParameter("echostr");//随机字符串
/**
 * 将token、timestamp、nonce三个参数进行字典序排序
 * 并拼接为一个字符串
 */
        String sortStr = sort(TOKEN,timestamp,nonce);
/**
 * 字符串进行shal加密
 */
        String mySignature = shal(sortStr);
/**
 * 校验微信服务器传递过来的签名 和 加密后的字符串是否一致, 若一致则签名通过
 */
        if(!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)){
            System.out.println("‐‐‐‐‐签名校验通过‐‐‐‐‐");
            resp.getWriter().write(echostr);
        }else {
            System.out.println("‐‐‐‐‐校验签名失败‐‐‐‐‐");
        }
    }
    /**在web.xml中配置 servlet:
     * * 参数排序
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     * */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
       return sb.toString();
    }
    /**
    * 字符串进行shal加密
    * @param decript
    * @return
    */
    public  String shal(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}