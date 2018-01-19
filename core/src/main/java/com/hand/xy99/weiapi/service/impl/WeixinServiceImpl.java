package com.hand.xy99.weiapi.service.impl;

import com.hand.xy99.weiapi.messagedto.music.Music;
import com.hand.xy99.weiapi.messagedto.music.MusicMessage;
import com.hand.xy99.weiapi.messagedto.news.item;
import com.hand.xy99.weiapi.messagedto.news.NewsMessage;
import com.hand.xy99.weiapi.messagedto.text.TextMessage;
import com.hand.xy99.weiapi.messagedto.video.Video;
import com.hand.xy99.weiapi.messagedto.video.VideoMessage;
import com.hand.xy99.weiapi.service.IWeixinService;
import com.hand.xy99.weiapi.weixinUtil.MessageUtil;
import com.hand.xy99.weiapi.weChatServlet.AccessTokenServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xieshuai on 2018/1/18.
 */
@Scope("singleton")
@Repository
public class WeixinServiceImpl implements IWeixinService {
    public static Logger log = LoggerFactory.getLogger(AccessTokenServlet.class);
@Override
public String processRequest(HttpServletRequest req) {
        // 解析微信传递的参数
        String str = null;
        try {
            Map<String,String> xmlMap = MessageUtil.parseXml(req);
            str = "请求处理异常，请稍后再试！";

            String ToUserName = xmlMap.get("ToUserName");
            String FromUserName = xmlMap.get("FromUserName");
            String MsgType = xmlMap.get("MsgType");

            if (MsgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
                // 用户发送的文本消息
                String content = xmlMap.get("Content");
                log.info("用户：[" + FromUserName + "]发送的文本消息：" + content);

                // 链接
                if (content.contains("csdn")) {
                    TextMessage tm = new TextMessage();
                    tm.setToUserName(FromUserName);
                    tm.setFromUserName(ToUserName);
                    tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    tm.setCreateTime(System.currentTimeMillis());
                    tm.setContent("我的CSDN博客：<a href=\"http://my.csdn.net/qincidong\">我的CSDN博客</a>\n");
                    return MessageUtil.textMessageToXml(tm);
                }
                else if (content.contains("1")) { // 点击了回复音乐
                    MusicMessage mm =  new MusicMessage();
                    mm.setFromUserName(ToUserName);
                    mm.setToUserName(FromUserName);
                    mm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                    mm.setCreateTime(System.currentTimeMillis());
                    Music music = new Music();
                    music.setTitle("Maid with the Flaxen Hair");
                    music.setDescription("测试音乐");
                    music.setMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                    music.setHQMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                    mm.setMusic(music);

                    String musicXml = MessageUtil.musicMessageToXml(mm);
                    log.info("musicXml:\n" + musicXml);
                    return musicXml;
                }
                else if (content.contains("2")) { // 点击了回复图文
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
                    return MessageUtil.newsMessageToXml(im);
                }
                else if (content.contains("3")) {
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
                    String xml = MessageUtil.videoMessageToXml(im);
                    return xml;
                }
                // 响应
                TextMessage tm = new TextMessage();
                tm.setToUserName(FromUserName);
                tm.setFromUserName(ToUserName);
                tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                tm.setCreateTime(System.currentTimeMillis());
                tm.setContent("你好，你发送的内容是：\n" + content);

                String xml = MessageUtil.textMessageToXml(tm);
                log.info("xml:" + xml);
                return xml;
            }
            else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String event = xmlMap.get("Event");
                if (event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    // 订阅
                    TextMessage tm = new TextMessage();
                    tm.setToUserName(FromUserName);
                    tm.setFromUserName(ToUserName);
                    tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    tm.setCreateTime(System.currentTimeMillis());
                    tm.setContent("你好，欢迎关注[程序员的生活]公众号！[愉快]/呲牙/玫瑰\n目前可以回复文本消息");
                    return MessageUtil.textMessageToXml(tm);
                }
                else if (event.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消订阅
                    log.info("用户【" + FromUserName + "]取消关注了。");
                }
                else if (event.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = xmlMap.get("EventKey");
                    if (eventKey.equals("reply_words")) { // 点击了回复文字菜单
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(FromUserName);
                        tm.setFromUserName(ToUserName);
                        tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        tm.setCreateTime(System.currentTimeMillis());
                        tm.setContent("你好，你点击了回复文本菜单：\n");

                        String xml = MessageUtil.textMessageToXml(tm);
                        log.info("xml:" + xml);
                        return xml;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理微信请求时发生异常：");
        }

        return str;
    }

}