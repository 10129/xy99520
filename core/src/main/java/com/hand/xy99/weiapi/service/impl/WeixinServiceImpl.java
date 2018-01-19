package com.hand.xy99.weiapi.service.impl;

import com.hand.xy99.weiapi.messagedto.music.Music;
import com.hand.xy99.weiapi.messagedto.music.MusicMessage;
import com.hand.xy99.weiapi.messagedto.news.Articles;
import com.hand.xy99.weiapi.messagedto.news.NewsMessage;
import com.hand.xy99.weiapi.messagedto.text.TextMessage;
import com.hand.xy99.weiapi.service.IWeixinService;
import com.hand.xy99.weiapi.weixinUtil.MessageUtil;
import com.hand.xy99.weiapi.weChatServlet.AccessTokenServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xieshuai on 2018/1/18.
 */
@Service
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
                        tm.setContent("你好，你点击了回复文本菜单：\n" );

                        String xml = MessageUtil.textMessageToXml(tm);
                        log.info("xml:" + xml);
                        return xml;
                    }
                    else if (eventKey.equals("reply_music")) { // 点击了回复音乐
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
                    else if (eventKey.equals("reply_news")) { // 点击了回复图文
                        NewsMessage nm = new NewsMessage();
                        nm.setFromUserName(ToUserName);
                        nm.setToUserName(FromUserName);
                        nm.setCreateTime(System.currentTimeMillis());
                        nm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        List<Articles> articles = new ArrayList<Articles>();
                        Articles e1 = new Articles();
                        e1.setTitle("马云接受外媒专访：中国的五大银行想杀了“我”");
                        e1.setDescription("阿里巴巴集团上市大获成功，《华尔街日报》日前就阿里巴巴集团、支付宝等话题采访了马云，马云也谈到了与苹果Apple Pay建立电子支付联盟的可能性。本文摘编自《华尔街日报》，原文标题：马云谈阿里巴巴将如何帮助美国出口商，虎嗅略有删节。");
                        e1.setPicUrl("http://img1.gtimg.com/finance/pics/hv1/29/53/1739/113092019.jpg");
                        e1.setUrl("http://finance.qq.com/a/20141105/010616.htm?pgv_ref=aio2012&ptlang=2052");

                        Articles e2 = new Articles();
                        e2.setTitle("史上最牛登机牌：姓名竟是微博名 涉事航空公司公开致歉");
                        e2.setDescription("世上最遥远的距离是飞机在等你登机，你却过不了安检。");
                        e2.setPicUrl("http://p9.qhimg.com/dmfd/328_164_100/t011946ff676981792d.png");
                        e2.setUrl("http://www.techweb.com.cn/column/2014-11-05/2093128.shtml");
                        articles.add(e1);
                        articles.add(e2);

                        nm.setArticles(articles);
                        nm.setArticleCount(articles.size());

                        String newsXml = MessageUtil.newsMessageToXml(nm);
                        log.info("\n"+newsXml);
                        return newsXml;
                    }
                    else if (eventKey.equals("reply_link")) {
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(FromUserName);
                        tm.setFromUserName(ToUserName);
                        tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        tm.setCreateTime(System.currentTimeMillis());
                        tm.setContent("我的CSDN博客：<a href=\"http://my.csdn.net/qincidong\">我的CSDN博客</a>\n");
                        return MessageUtil.textMessageToXml(tm);
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