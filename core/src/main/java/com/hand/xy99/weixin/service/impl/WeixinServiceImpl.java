package com.hand.xy99.weixin.service.impl;

import com.hand.xy99.weixin.pojo.message.menu.CommandButton;
import com.hand.xy99.weixin.pojo.message.menu.ComplexButton;
import com.hand.xy99.weixin.pojo.message.menu.Menu;
import com.hand.xy99.weixin.pojo.message.menu.ViewButton;
import com.hand.xy99.weixin.service.IWeixinService;
import com.hand.xy99.weixin.util.common.RespMessageUtil;
import com.hand.xy99.weixin.pojo.message.resp.*;
import com.hand.xy99.weixin.pojo.Token;
import net.sf.json.JSONObject;
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
    public static Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);
@Override
public String processRequest(HttpServletRequest req) {
        // 解析微信传递的参数
        String str = null;
        try {
            Map<String,String> xmlMap = RespMessageUtil.parseXml(req);
            str = "请求处理异常，请稍后再试！";

            String ToUserName = xmlMap.get("ToUserName");
            String FromUserName = xmlMap.get("FromUserName");
            String MsgType = xmlMap.get("MsgType");

            if (MsgType.equals(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
                // 用户发送的文本消息
                String content = xmlMap.get("Content");
                log.info("用户：[" + FromUserName + "]发送的文本消息：" + content);

                // 链接
                if (content.contains("csdn")) {
                    TextMessage tm = new TextMessage();
                    tm.setToUserName(FromUserName);
                    tm.setFromUserName(ToUserName);
                    tm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    tm.setCreateTime(System.currentTimeMillis());
                    tm.setContent("我的CSDN博客：<a href=\"http://my.csdn.net/qincidong\">我的CSDN博客</a>\n");
                    return RespMessageUtil.messageToXml(tm);
                }
                else if (content.contains("1")) { // 点击了回复音乐
                    MusicMessage mm =  new MusicMessage();
                    mm.setFromUserName(ToUserName);
                    mm.setToUserName(FromUserName);
                    mm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                    mm.setCreateTime(System.currentTimeMillis());
                    Music music = new Music();
                    music.setTitle("Maid with the Flaxen Hair");
                    music.setDescription("测试音乐");
                    music.setMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                    music.setHQMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                    mm.setMusic(music);

                    String musicXml = RespMessageUtil.messageToXml(mm);
                    log.info("musicXml:\n" + musicXml);
                    return musicXml;
                }
                else if (content.contains("2")) { // 点击了回复图文
                    NewsMessage im = new NewsMessage();
                    im.setToUserName(FromUserName);
                    im.setFromUserName(ToUserName);
                    im.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_NEWS);
                    im.setCreateTime(System.currentTimeMillis());
                    Article item1 =new Article();
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
                    Article item2 =new Article();
                    item2.setTitle(title2);
                    item2.setDescription(description2);
                    item2.setPicUrl(picUrl2);
                    item2.setUrl(textUrl2);
                    List<Article> itemList =new ArrayList<Article>();
                    itemList.add(item1);
                    itemList.add(item2);
                    im.setArticles(itemList);
                    im.setArticleCount(2);
                    return RespMessageUtil.messageToXml(im);
                }

                // 响应
                TextMessage tm = new TextMessage();
                tm.setToUserName(FromUserName);
                tm.setFromUserName(ToUserName);
                tm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT);
                tm.setCreateTime(System.currentTimeMillis());
                tm.setContent("你好，你发送的内容是：\n" + content);

                String xml = RespMessageUtil.messageToXml(tm);
                log.info("xml:" + xml);
                return xml;
            }
            else if (MsgType.equals(RespMessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
//                System.out.print(xmlMap.get("MediaId"));
//                TextMessage tm = new TextMessage();
//                tm.setToUserName(FromUserName);
//                tm.setFromUserName(ToUserName);
//                tm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT);
//                tm.setCreateTime(System.currentTimeMillis());
//                tm.setContent("你好，你发送的内容是：\n" + "视频"+xmlMap.get("MediaId"));
//
//                String xml = RespMessageUtil.textMessageToXml(tm);
//                log.info("xml:" + xml);
//                return xml;
                VideoMessage im = new VideoMessage();
                im.setToUserName(FromUserName);
                im.setFromUserName(ToUserName);
                im.setMsgType(RespMessageUtil.REQ_MESSAGE_TYPE_VIDEO);
                im.setCreateTime(System.currentTimeMillis());
                Video video = new Video();
                video.setMediaId("XZGjf-nyUEOZ2e59bo1GEcS21GrU6u0MJtrDIYyuwugsUPoHWsTSpYZmnR5Fbusj");
//                video.setTitle("hahah");
//                video.setDescription("还给你一个视频");
                im.setVideo(video);
                String xml = RespMessageUtil.messageToXml(im);
                return xml;
            }
            else if (MsgType.equals(RespMessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String event = xmlMap.get("Event");
                if (event.equals(RespMessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    // 订阅
                    TextMessage tm = new TextMessage();
                    tm.setToUserName(FromUserName);
                    tm.setFromUserName(ToUserName);
                    tm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    tm.setCreateTime(System.currentTimeMillis());
                    tm.setContent("你好，欢迎关注[程序员的生活]公众号！[愉快]/呲牙/玫瑰\n目前可以回复文本消息");
                    return RespMessageUtil.messageToXml(tm);
                }
                else if (event.equals(RespMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消订阅
                    log.info("用户【" + FromUserName + "]取消关注了。");
                }
                else if (event.equals(RespMessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = xmlMap.get("EventKey");
                    if (eventKey.equals("reply_words")) { // 点击了回复文字菜单
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(FromUserName);
                        tm.setFromUserName(ToUserName);
                        tm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        tm.setCreateTime(System.currentTimeMillis());
                        tm.setContent("你好，你点击了回复文本菜单：测试测试\n");

                        String xml = RespMessageUtil.messageToXml(tm);
                        log.info("xml:" + xml);
                        return xml;

                    }else if (eventKey.equals("reply_music")) {
                        MusicMessage mm =  new MusicMessage();
                        mm.setFromUserName(ToUserName);
                        mm.setToUserName(FromUserName);
                        mm.setMsgType(RespMessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                        mm.setCreateTime(System.currentTimeMillis());
                        Music music = new Music();
                        music.setTitle("Maid with the Flaxen Hair");
                        music.setDescription("测试音乐");
                        music.setMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                        music.setHQMusicUrl("http://yinyueshiting.baidu.com/data2/music/123297915/1201250291415073661128.mp3?xcode=e2edf18bbe9e452655284217cdb920a7a6a03c85c06f4409");
                        mm.setMusic(music);

                        String musicXml = RespMessageUtil.messageToXml(mm);
                        log.info("musicXml:\n" + musicXml);
                        return musicXml;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理微信请求时发生异常：");
        }

        return str;
    }

    @Override
    public Menu getMenu(Token accessToken) {
        // 2).创建菜单
        Menu menu = new Menu();
        // 菜单1
        ComplexButton cb0 = new ComplexButton();
        cb0.setName("超值预定");

        ViewButton cb01 = new ViewButton();
        cb01.setName("测试授权1");
        cb01.setType("view");
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd135f4cb0d7346bf&redirect_uri=http://8ir64m.natappfree.cc/oauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        cb01.setUrl(url);

        ViewButton cb02 = new ViewButton();
        cb02.setName("测试授权2");
        cb02.setType("view");
        String url2="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd135f4cb0d7346bf&redirect_uri=http://8ir64m.natappfree.cc/oauthServlet&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        cb02.setUrl(url2);

        cb0.setSub_button(new ViewButton[]{cb01,cb02});

        // 菜单2
        ComplexButton cb1 = new ComplexButton();
        cb1.setName("我的服务");

        ViewButton cb11 = new ViewButton();
        cb11.setName("办登机牌");
        cb11.setType("view");
        cb11.setUrl("http://www.meituan.com");

        ViewButton cb12 = new ViewButton();
        cb12.setName("航班动态");
        cb12.setType("view");
        cb12.setUrl("http://www.meituan.com");

        ViewButton cb13 = new ViewButton();
        cb13.setName("里程查询");
        cb13.setType("view");
        cb13.setUrl("http://www.meituan.com");

        cb1.setSub_button(new ViewButton[]{cb11,cb12,cb13});

        // 菜单3
        ComplexButton cb2 = new ComplexButton();
        cb2.setName("我的测试");

        CommandButton cb21 = new CommandButton();
        cb21.setName("回复文字");
        cb21.setType("click");
        cb21.setKey("reply_words");

        CommandButton cb22 = new CommandButton();
        cb22.setName("回复音乐");
        cb22.setType("click");
        cb22.setKey("reply_music");

        CommandButton cb23 = new CommandButton();
        cb23.setName("回复图文");
        cb23.setType("click");
        cb23.setKey("reply_news");

        CommandButton cb24 = new CommandButton();
        cb24.setName("测试授权");
        cb24.setType("click");
        cb24.setKey("reply_link");

        cb2.setSub_button(new CommandButton[]{cb21,cb22,cb23,cb24});

        menu.setButton(new ComplexButton[]{cb0,cb1,cb2});
        String menuJsonString = JSONObject.fromObject(menu).toString();
        System.out.println(menuJsonString);
        return menu;
    }

}