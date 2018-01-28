package com.hand.xy99.weixin.test;
/**
 * Created by xieshuai on 2018/1/16.
 */
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MessageXMLTest {
    /**
     * 解析微信发来的请求（XML）
     * @param request
     * @return map
     * @throws Exception
     */
    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
// 将解析结果存储在HashMap中
        Map<String,String> map = new HashMap();
// 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        System.out.println("获取输入流");
// 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
// 得到xml根元素
        Element root = document.getRootElement();
// 得到根元素的所有子节点
        List<Element> elementList = root.elements();
// 遍历所有子节点
        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }// 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }
    /**
      * 根据消息类型 构造返回消息
      */
    public static String buildXml(Map<String,String> map) {
        String result;
        // CreateTime ToUserName FromUserName  MsgId MsgType 5个属性通用
        String createTime = map.get("CreateTime").toString();
        String toUserName = map.get("ToUserName").toString();// 开发者微信号
        String fromUserName = map.get("FromUserName").toString();
        String msgId = map.get("MsgId").toString();
        String msgType = map.get("MsgType").toString();
        System.out.println("MsgType:" + msgType);
        if(msgType.toUpperCase().equals("EVENT")){
            result = buildTextMessage(map, "关注！");
        }else if(msgType.toUpperCase().equals("TEXT")){
            String content = map.get("Content").toString();
            //Music
            if("音乐".equals(content)){
                result = buildMusicMessage(map);
            }else if("图文".equals(content)){
                result = buildNewsMessage(map);
            }else {
                result = buildTextMessage(map, "文字！");
            }
        }else if(msgType.toUpperCase().equals("IMAGE")){
        //MediaId PicUrl  图片2个属性
            String mediaId = map.get("MediaId").toString();
            String picUrl = map.get("PicUrl").toString();
            result = buildImageMessage(map, picUrl);
        }else if(msgType.toUpperCase().equals("VOICE")){
            //MediaId Format Recognition 语音3个
            String mediaId = map.get("MediaId").toString();
            String format = map.get("Format").toString();
            String recognition = map.get("Recognition").toString();
            result = buildVoiceMessage(map);
        }else if(msgType.toUpperCase().equals("VIDEO")){
            String mediaId = map.get("MediaId").toString();
            result = buildVideoMessage(map);
        }else if(msgType.toUpperCase().equals("NEWS")){
            result = buildNewsMessage(map);
        }else if(msgType.toUpperCase().equals("SUBSCRIBE")){
            result = buildTextMessage(map, "订阅！");
        }else if(msgType.toUpperCase().equals("UNSUBSCRIBE")){
            result = buildTextMessage(map, "取消订阅！");
        }else if(msgType.toUpperCase().equals("LOCATION")){
            result = buildTextMessage(map, "地理位置消息！");
        }else if(msgType.toUpperCase().equals("LINK")){
            result = buildTextMessage(map, "链接消息！");
        }else{
            result = String
                    .format(
                            "<xml>"+
                                    "<ToUserName><![CDATA[%s]]></ToUserName>"+
                                    "<FromUserName><![CDATA[%s]]></FromUserName>"+
                                    "<CreateTime>%s</CreateTime>"+
                                    "<MsgType><![CDATA[text]]></MsgType>"+
                                    "<Content><![CDATA[%s]]></Content>"+
                                    "</xml>",
                            fromUserName, toUserName, getUtcTime(),
                            "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
        }
        return result;
    }
    /**
     * 构造文本消息
     * @param map
     * @param content
     * @return
     */
    private static String buildTextMessage(Map<String,String> map, String content) {
//发送方帐号
        String fromUserName = map.get("FromUserName");
// 开发者微信号
        String toUserName = map.get("ToUserName");
/**
 * 文本消息XML数据格式
 */
        return String.format(
                "<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>"+
                 "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), content);
    }
    /**
     * 构建图片消息
     * @param map
     * @param picUrl
     * @return
     */
    private static String buildImageMessage(Map<String, String> map, String picUrl) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
/*返回指定的图片(该图片是上传为素材的,获得其media_id)*/
String media_id = "cuy8FdvowpQyKE5Q3XJYj4pxeagBb1L8Qs1GEz12qyar0iNHulufdqZ0CALZVzYj";
/*返回用户发过来的图片*/
//        String media_id = map.get("MediaId");
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image>" +
                        " <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Image>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }
    /**
     * 构造语音消息
     * @param map
     * @return
     */
    private static String buildVoiceMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
/*返回用户发过来的语音*/
        String media_id = map.get("MediaId");
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[voice]]></MsgType>" +
                        "<Voice>" +
                        " <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Voice>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }
    /**
     * 回复视频消息
     * @param map
     * @return
     */
    private static String buildVideoMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String title = "客官发过来的视频哟~~";
        String description = "客官您呐,现在肯定很开心,对不啦 嘻嘻😄";
        /*返回用户发过来的视频*/
        String media_id = map.get("MediaId");
//        String media_id = "hTl1of-w78xO-0cPnF_Wax1QrTwhnFpG1WBkAWEYRr9Hfwxw8DYKPYFX-22hAwSs";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[video]]></MsgType>" +
                        "<Video>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "   <Title><![CDATA[%s]]></Title>" +
                        "   <Description><![CDATA[%s]]></Description>" +
                        "</Video>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id,title,description
        );
    }
    /**
     * 回复音乐消息
     * @param map
     * @return
     */
    private static String buildMusicMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String title = "亲爱的路人";
        String description = "\uE03E多听音乐 心情棒棒";
        String hqMusicUrl ="http://www.kugou.com/song/20qzz4f.html?frombaidu#hash=20C16B9CCCCF851D1D23AF52DD963986&album_id=0";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[music]]></MsgType>" +
                        "<Music>" +
                        "<Title><![CDATA[%s]]></Title>" +
                        "<Description><![CDATA[%s]]></Description>" +
                        "<MusicUrl>< ![CDATA[%s] ]></MusicUrl>" +  //非必须项 音乐链接
                        "<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>"+ //非必须项 高质量音乐链接，WIFI环境优先使用该链接播放音乐
                        "</Music>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),title,description,hqMusicUrl,hqMusicUrl
        );
    }

    /**
     * 返回图文消息
     * @param map
     * @return
     */
    private static String buildNewsMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String title1 = "HAP审计的实现和使用";
        String description1 = "由于HAP框架用的是Spring+SpringMVC+Mybatis，其中Mybatis中的拦截器可以选择在被拦截的方法前后执行自己的逻辑。所以我们通过拦截器实现了审计功能，当用户对某个实体类进行增删改操作时，拦截器可以拦截，然后将操作的数据记录在审计表中，便于用户以后审计。";
        String picUrl1 ="http://upload-images.jianshu.io/upload_images/7855203-b9e9c9ded8a732a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        String textUrl1 = "http://blog.csdn.net/a1786223749/article/details/78330890";

        String title2 = "KendoUI之Grid的问题详解";
        String description2 = "kendoLov带出的值出现 null和undefined";
        String picUrl2 ="https://demos.telerik.com/kendo-ui/content/shared/images/theme-builder.png";
        String textUrl2 = "http://blog.csdn.net/a1786223749/article/details/78330908";

        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[news]]></MsgType>" +
                        "<ArticleCount>2</ArticleCount>" + //图文消息个数，限制为8条以内
                        "<Articles>" + //多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
                        "<item>" +
                        "<Title><![CDATA[%s]]></Title> " +
                        "<Description><![CDATA[%s]]></Description>" +
                        "<PicUrl><![CDATA[%s]]></PicUrl>" + //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
                        "<Url><![CDATA[%s]]></Url>" + //点击图文消息跳转链接
                        "</item>" +
                        "<item>" +
                        "<Title><![CDATA[%s]]></Title>" +
                        "<Description><![CDATA[%s]]></Description>" +
                        "<PicUrl><![CDATA[%s]]]></PicUrl>" +
                        "<Url><![CDATA[%s]]]></Url>" +
                        "</item>" +
                        "</Articles>" +
                        "</xml>"
                ,
                fromUserName,toUserName, getUtcTime(),
                title1,description1,picUrl1,textUrl1,
                title2,description2,picUrl2,textUrl2
        );
    }

    /**
     * 得到当前时间
     * @return String
     */
    private static String getUtcTime() {
        Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
        String nowTime = df.format(dt);
        long dd = (long) 0;
        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {
        }
        return String.valueOf(dd);
    }
}