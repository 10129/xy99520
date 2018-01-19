package com.hand.weiapi.weixinUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import com.hand.weiapi.messagedto.image.ImageMessage;
import com.hand.weiapi.messagedto.music.MusicMessage;
import com.hand.weiapi.messagedto.news.NewsMessage;
import com.hand.weiapi.messagedto.text.TextMessage;
import com.hand.weiapi.messagedto.video.VideoMessage;
import com.hand.weiapi.messagedto.voice.VoiceMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 对消息的处理
 * @author Created by xieshuai on 2018/1/18.
 *
 */
public class MessageUtil {
    /**
     * text
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * music
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * news
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * text
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * image
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * link
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * location
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * voice
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * video
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * shortvideo
     */
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

    /**
     * event
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * subscribe
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * unsubscribe
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * CLICK
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    public static Pattern patternInt = Pattern.compile("[0-9]*(\\.?)[0-9]*");
    public static Pattern patternFloat = Pattern.compile("[0-9]+");


    public static Map<String,String> parseXml(HttpServletRequest request){

        Map<String,String> messageMap=new HashMap<String, String>();

        InputStream inputStream=null;
        try {
            //读取request Stream信息
            inputStream=request.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SAXReader reader = new SAXReader();
        Document document=null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Element root=document.getRootElement();
        List<Element> elementsList=root.elements();

        for(Element e:elementsList){
            messageMap.put(e.getName(),e.getText());
        }
        try {
            inputStream.close();
            inputStream=null;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return messageMap;
    }


    /**
     * 转换文本消息
     *
     * @param textMessage
     *
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    /**
     * 转换图片消息
     *
     * @param imageMessage
     *
     * @return xml
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }
    /**
     * 转换声音消息
     *
     * @param voiceMessage
     *
     * @return xml
     */
    public static String voiceMessageToXml(VoiceMessage voiceMessage) {
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }
    /**
     * 转换音乐消息
     *
     * @param musicMessage
     *
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    /**
     * 转换视频消息
     *
     * @param videoMessage
     *
     * @return xml
     */
    public static String videoMessageToXml(VideoMessage videoMessage) {
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }
    /**
     * 转换图文消息
     *
     * @param newsMessage
     *
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        return xstream.toXML(newsMessage);
    }
    /**
     * @author Created by xieshuai on 2018/1/18.
     * 定义xstream让value自动加上CDATA标签
     */
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = false;
                @SuppressWarnings("unchecked")
                @Override
                public void startNode(String name, Class clazz) {
                    if (!name.equals("xml")) {
                        char[] arr = name.toCharArray();
                        if (arr[0] >= 'a' && arr[0] <= 'z') {
                            // arr[0] -= 'a' - 'A';
                            arr[0] = (char) ((int) arr[0] - 32);
                        }
                        name = new String(arr);
                        //如果是类名则取最后.的位置
                        if(name!=null &&name.contains(".")){
//                            int i=name.lastIndexOf(".");
                           name= name.substring(name.lastIndexOf(".")+1,name.length());
                        }

                    }
                    super.startNode(name, clazz);

                }
@Override
public void setValue(String text) {
    if (text != null && !"".equals(text)) {
        if (patternInt.matcher(text).matches()
                || patternFloat.matcher(text).matches()) {
            cdata = false;
        } else {
            cdata = true;
        }
    }
    super.setValue(text);
}
@Override
protected void writeText(QuickWriter writer, String text) {
            if (cdata) {
                writer.write("<![CDATA[");
                writer.write(text);
                writer.write("]]>");
            } else {
                writer.write(text);
            }
        }
    };
}
    });
}