package com.hand.xy99.weixin.service.impl;

import com.hand.xy99.weixin.util.common.SendMessageUtil;
import com.hand.xy99.weixin.pojo.message.send.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
/**@desc  : 发送消息
 * @author: shirayner
 * @date  : 2017-8-18 上午10:06:23
 */
public class SendMessageService {
	private static Logger log = LoggerFactory.getLogger(SendMessageService.class);

	 /**
     * 微信公共账号发送给账号
     * @param content 文本内容
     * @param toUser 微信用户
     * @return
     */
    public  void sendTextMessageToUser(String content,String toUser){
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		TextMessage textMessage=new TextMessage();
		textMessage.setTouser(toUser);
		textMessage.setMsgtype("text");
		Text text =new Text();
		text.setContent(content);
		textMessage.setText(text);
		sendMessageUtil.sendMessage(textMessage);
    }
    /**
     * 微信公共账号发送给账号(本方法限制使用的消息类型是语音或者图片)
     * @param mediaId 图片或者语音内容
     * @param toUser 微信用户
     * @return
     */
    public  void sendPicOrVoiceMessageToUser(String mediaId,String toUser,String msgType){
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		Media media=new Media();
		media.setMedia_id(mediaId);
		if("voice".equals(msgType)){
			VoiceMessage voiceMessage=new VoiceMessage();
			voiceMessage.setTouser(toUser);
			voiceMessage.setMsgtype(msgType);
			voiceMessage.setVoice(media);
			sendMessageUtil.sendMessage(voiceMessage);
		}else {
			ImgMessage imgMessage=new ImgMessage();
			imgMessage.setTouser(toUser);
			imgMessage.setMsgtype(msgType);
			imgMessage.setImage(media);
			sendMessageUtil.sendMessage(imgMessage);
		}
    }
    /**
     *  发送图文给所有的用户
     * @param openId 用户的id
     */
    public  void sendNewsToUser(String openId){
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		NewsMessage newsMessage=new NewsMessage();
		newsMessage.setTouser(openId);
		newsMessage.setMsgtype("news");
		News news =new News();
		List<Article> list=new ArrayList<Article>();
		//设置图文消息
		String title1 = "HAP审计的实现和使用";
		String description1 = "由于HAP框架用的是Spring+SpringMVC+Mybatis，其中Mybatis中的拦截器可以选择在被拦截的方法前后执行自己的逻辑。所以我们通过拦截器实现了审计功能，当用户对某个实体类进行增删改操作时，拦截器可以拦截，然后将操作的数据记录在审计表中，便于用户以后审计。";
		String picUrl1 ="http://upload-images.jianshu.io/upload_images/7855203-b9e9c9ded8a732a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
		String textUrl1 = "http://blog.csdn.net/a1786223749/article/details/78330890";
		String title2 = "KendoUI之Grid的问题详解";
		String description2 = "kendoLov带出的值出现 null和undefined";
		String picUrl2 ="https://demos.telerik.com/kendo-ui/content/shared/images/theme-builder.png";
		String textUrl2 = "http://blog.csdn.net/a1786223749/article/details/78330908";
		Article article1=new  Article();
		article1.setTitle(title1);
		article1.setDescription(description1);
		article1.setPicurl(picUrl1);
		article1.setUrl(textUrl1);
		Article article2=new  Article();
		article2.setTitle(title2);
		article2.setDescription(description2);
		article2.setPicurl(picUrl2);
		article2.setUrl(textUrl2);
		list.add(article1);
		list.add(article2);
		news.setArticles(list);
		newsMessage.setNews(news);
		sendMessageUtil.sendMessage(newsMessage);
    }

	//2.发送文本卡片消息
	public void sendTextcardMessage(String toUser){
		//0.设置消息内容
		String title="代办事宜";
		String description="<div class=\"gray\">2017年8月18日</div> <div class=\"normal\">" +
				"恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">" +
				"请于2017年10月10日前联系行政同事领取</div>";
		String url="http://www.cnblogs.com/shirui/p/7297872.html";
		//1.创建文本卡片消息对象
		TextcardMessage message=new TextcardMessage();
		//1.1非必需
		message.setTouser(toUser);  //不区分大小写
		//1.2必需
		message.setMsgtype("textcard");
		Textcard textcard=new Textcard();
		textcard.setTitle(title);
		textcard.setDescription(description);
		textcard.setUrl(url);
		message.setTextcard(textcard);
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		sendMessageUtil.sendMessage(message);
	}
	//5.发送视频消息
	public void sendVideoMessage(String media_id,String toUser){
		String title="视频示例";
		String description="好看的视频";
		//1.创建视频消息对象
		VideoMessage message=new VideoMessage();
		//1.1非必需
		message.setTouser(toUser);  //不区分大小写
		message.setMsgtype("video");
		Video video=new Video();
		video.setMedia_id(media_id);
		video.setTitle(title);
		video.setDescription(description);
		message.setVideo(video);
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		sendMessageUtil.sendMessage(message);
	}
	//6.发送文件消息
	public void testSendFileMessage(String media_id,String toUser){
		//1.创建文件对象
		FileMessage message=new FileMessage();
		//1.1非必需
		message.setTouser(toUser);  //不区分大小写
		//1.2必需
		message.setMsgtype("file");
		Media file=new Media();
		file.setMedia_id(media_id);
		message.setFile(file);
		SendMessageUtil sendMessageUtil =new SendMessageUtil();
		sendMessageUtil.sendMessage(message);
	}
	
}
