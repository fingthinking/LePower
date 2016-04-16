package com.lesport.util;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static final String HOST = "smtp.163.com";	//主机
	public static final String PROTOCOL = "smtp";	//协议
	public static final int PORT = 25;		//端口
	public static final String EMAILFROM = "rushuaicao@163.com";		//发件人的email
	public static final String EMAILPWD = "caorush123uai";		//发件人密码
	
	/**
	 * @描述：获取session
	 * @return
	 */
	public static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);		//设置服务器地址
		props.put("mail.store.protocol", PROTOCOL);		//设置协议
		props.put("mail.smtp.port", PORT);		//设置端口
		props.put("mail.smtp.auth", true);
		
		//验证发件人的邮箱密码正确，才可以进入发件人的邮箱，对用户的邮箱发送邮件
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAILFROM, EMAILPWD);
			}
		};
		Session session = Session.getDefaultInstance(props, authenticator);
		return session;
	}
	
	/**
	 * @描述：发送邮件
	 * @param emailTo  发送邮件的地址（邮件接收人，即用户邮箱）
	 * @param content  发送邮件内容
	 */
	public static void send(String emailTo,String content) {
		Session session = getSession();
		System.out.println("--send--" + content);
		Message msg = new MimeMessage(session);		//实例化一个消息对象
		
		try {
			msg.setFrom(new InternetAddress(EMAILFROM));
			InternetAddress[] address = {new InternetAddress(emailTo)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("账号激活邮件");
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");
			
			//发送消息
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @author 刘衍庆
	 * @date 2016年3月14日15:42:22
	 * @描述：发送邮件
	 * @param emailTo  发送邮件的地址（邮件接收人，即用户邮箱）
	 * @param content  发送邮件内容
	 */
	public static void sendCode(String emailTo,String content) {
		Session session = getSession();
		System.out.println("--send--" + content);
		Message msg = new MimeMessage(session);		//实例化一个消息对象
		
		try {
			msg.setFrom(new InternetAddress(EMAILFROM));
			InternetAddress[] address = {new InternetAddress(emailTo)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("找回密码");
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");
			
			//发送消息
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @描述:对邮箱格式进行检查
	 * @param email
	 * @return 邮箱格式正确返回真，否则返回假
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        System.out.println("邮箱格式检查结果：" + flag);
        return flag;
	}
}
