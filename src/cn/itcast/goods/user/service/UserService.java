package cn.itcast.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.dao.UserDao;
import cn.itcast.goods.user.domain.User;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 用户业务层
 * 
 * @author MyPC
 *
 */
public class UserService {
	private UserDao userDao = new UserDao(); // 依赖UserDao

	/**
	 * 用户名校验
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Email校验
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 注册功能
	 */
	public void regist(User user) {
		/*
		 * 1.数据补齐
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		/*
		 * 2.向数据库插入
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		/*
		 * 3.发邮件
		 */
		
		//把配置文件加载到prop中
		Properties prop= new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));//利用类加载器加载配置文件
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		
		
		//登录邮件服务器,得到Session 
		String host = prop.getProperty("host");//登录主机名
		String name = prop.getProperty("username");//登录名
		String pass = prop.getProperty("password");//密码
		Session session = MailUtils.createSession(host, name, pass);
		
		//创建Mail对象
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		//MessageFormat.format（参数a，参数b）能把参数b赋值给参数a中的第一个占位符
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());//email_template.properties中的content中有｛0｝占位符
		Mail mail = new Mail(from,to,subject,content);
		
		//发送邮件
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * 激活功能
	 * @throws UserException 
	 * 
	 */
	public void activation(String code) throws UserException {
		/*
		 * 1.通过激活码查询用户
		 * 2.如果User为null，说明是无效激活码，抛出异常 （无效激活码）
		 * 3.查看用户状态是否为true，如果为true，说明用户已存在，则抛出异常 （请不要二次激活）
		 * 4.修改用户状态为true
		 */
		try {
		User user = userDao.findByCode(code);
		if(user==null) {
			throw new UserException("无效的激活码！");
		}
		if(user.isStatus()) {
			throw new UserException("请不要二次激活！");
		}
		userDao.updateStatus(user.getUid(), true);//修改状态
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 登录功能
	 * 
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 修改密码
	 * @throws UserException 
	 * 
	 */
	public void updatePassword(String uid,String newPass,String oldPass) throws UserException {

		try {
			/*
			 * 1.校验老密码
			 */
			boolean b = userDao.findByUidAndPassword(uid, oldPass);
			if(!b) {
				throw new UserException("原密码错误！");
			}
			/*
			 * 2.修改密码
			 */
			userDao.updatePassword(uid, newPass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	
	
	
	
	
}
