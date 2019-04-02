package com.hand.xy99.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author shuai.xie
 * @date 2017年09月14日 下午12:36:12
 * @comment 
 */

public class MailUtil {
	
	private String host;
	private String user;
	private String password;

	static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	public MailUtil(String host, String user, String password){
		this.host = host;
		this.user = user;
		this.password = password;
	}

	/**
	 *
	 * 方法名: sendMail
	 * 描述: 发送邮件
	 * 创建人: shuai.xie
	 * 创建时间: 2017年11月27日 下午7:19:06
	 * 版本号: v1.0
	 * 抛出异常:
	 * 参数: @param subject
	 * 参数: @param toMail
	 * 参数: @param content
	 * 参数: @param fileName excel名称
	 * 参数: @return
	 * 返回类型: boolean
	 */
	public   boolean sendMail(String subject, String toMail, String content,String fileName, InputStream is,String ccList) {
		boolean isFlag = false;
		try {
//			String smtpFromMail = "shuai.xie@hand-china.com";  //账号
//			String pwd = "Xie190924"; //密码
//			// int port = 25; //端口
//			String host = "mail.hand-china.com"; //端口

			Properties props = new Properties();
			props.put("mail.smtp.host", host); // 指定SMTP服务器
			props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
//                MessageTemplate messageTemplate = messageTemplateMapper.getMsgTemByCode(null, emailTempCode);
//                // 主题
//                String content = messageTemplate.getContent();
//                // 内容
//                subject = messageTemplate.getSubject();
				//message.setFrom(new InternetAddress(smtpFromMail, "物料询价单"));
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
				if(ccList!=null && !"".equals(ccList)){
					message.addRecipients(Message.RecipientType.CC,ccList);
				}
				message.setSubject(subject);

				message.addHeader("charset", "UTF-8");

                /*添加正文内容*/
				Multipart multipart = new MimeMultipart();   //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
				MimeBodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content,"UTF-8");

				//contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				multipart.addBodyPart(contentPart);

                /*添加附件*/
                if(is != null) {
					MimeBodyPart fileBody = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(is, "application/msexcel");
					fileBody.setDataHandler(new DataHandler(source));
					// 中文乱码问题
					fileBody.setFileName(MimeUtility.encodeText(fileName));
					multipart.addBodyPart(fileBody);
				}

				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				//transport.connect(host, port, smtpFromMail, pwd);
				transport.connect(host, user, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
				logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+ toMail+" success #");
			} catch (Exception e) {
				  logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+ toMail+" error #");
				  logger.info(e.toString());
				e.printStackTrace();
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}

	/**
	 *
	 * 方法名: sendMailWithNoFile
	 * 描述: 发送邮件不带附件
	 * 创建人: yujiao.liu@hand-china.com
	 * 创建时间: 2017年11月27日 下午7:19:06
	 * 版本号: v1.0
	 * 抛出异常:
	 * 参数: @param subject
	 * 参数: @param toMail
	 * 参数: @param content
	 * 参数: @return
	 * 返回类型: boolean
	 */
	public   boolean sendMailWithNoFile(String subject, String toMail, String content) {
		boolean isFlag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host); // 指定SMTP服务器
			props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
				message.setSubject(subject);
				message.addHeader("charset", "UTF-8");
                /*添加正文内容*/
				Multipart multipart = new MimeMultipart();   //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
				MimeBodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content,"UTF-8");

				//contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				multipart.addBodyPart(contentPart);
				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, user, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
				logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+ toMail+" success #");
			} catch (Exception e) {
				logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+ toMail+" error #");
				logger.info(e.toString());
				e.printStackTrace();
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}

	/**
	 * 群发邮件带附件
	 * @param subject
	 * @param toEmailList  发件人list
	 * @param content
	 * @param fileName
	 * @param is
	 * @param ccList
	 * @return
	 */
	public   boolean sendMail(String subject,List<String> toEmailList, String content, String fileName, InputStream is, String ccList) {
		boolean isFlag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host); // 指定SMTP服务器
			props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(user));
				for(String email : toEmailList){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
				if(ccList!=null && !"".equals(ccList)){
					message.addRecipients(Message.RecipientType.CC,ccList);
				}
				message.setSubject(subject);
				message.addHeader("charset", "UTF-8");

                /*添加正文内容*/
				Multipart multipart = new MimeMultipart();   //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
				MimeBodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content,"UTF-8");

				//contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				multipart.addBodyPart(contentPart);

                /*添加附件*/
				if(is != null) {
					MimeBodyPart fileBody = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(is, "application/msexcel");
					fileBody.setDataHandler(new DataHandler(source));
					// 中文乱码问题
					fileBody.setFileName(MimeUtility.encodeText(fileName));
					multipart.addBodyPart(fileBody);
				}

				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				//transport.connect(host, port, smtpFromMail, pwd);
				transport.connect(host, user, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
			} catch (Exception e) {
				logger.info(e.toString());
				e.printStackTrace();
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}

	/**
	 * 群发邮件 不带附件
	 * @param subject
	 * @param toEmailList
	 * @param content
	 * @return
	 */
	public   boolean sendMailWithNoFile(String subject, List<String> toEmailList, String content) {
		boolean isFlag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host); // 指定SMTP服务器
			props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(user));
				for(String email : toEmailList){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
				message.setSubject(subject);
				message.addHeader("charset", "UTF-8");
                /*添加正文内容*/
				Multipart multipart = new MimeMultipart();   //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
				MimeBodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content,"UTF-8");

				//contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				multipart.addBodyPart(contentPart);
				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, user, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
			} catch (Exception e) {
				logger.info(e.toString());
				e.printStackTrace();
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}


}
