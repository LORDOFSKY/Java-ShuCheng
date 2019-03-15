package cn.itcast.goods.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.domain.User;
import cn.itcast.goods.user.service.UserException;
import cn.itcast.goods.user.service.UserService;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户控制层 Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService(); // 依赖UserService

	public UserServlet() {
	}

	/**
	 * 注册功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User对象
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2.校验,如果校验失败，保存错误信息，返回到regist.jsp中显示
		 */
		Map<String, String> errors = validateRegist(formUser, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}
		/*
		 * 3.使用Service完成业务
		 */
		userService.regist(formUser);
		/*
		 * 4.保存成功信息，转发到msg.jsp显示
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请到邮箱去激活！");
		return "f:/jsps/msg.jsp";

	}

	/**
	 * ajax用户名是否注册校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 2.通过Service得到校验结果
		 */
		boolean b = userService.ajaxValidateLoginname(loginname);
		/*
		 * 3.发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}

	/**
	 * ajax Email是否注册校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.获取邮箱
		 */
		String email = req.getParameter("email");
		/*
		 * 2.通过Service得到校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/*
		 * 3.发给客户端
		 */
		resp.getWriter().print(b);

		return null;
	}

	/**
	 * ajax 验证码是否正确校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		/*
		 * 1.获取输入框中的验证码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2.获取图片上真是的验证码
		 */
		String vcode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3.忽略大小写进行比较验证码
		 */
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		/*
		 * 4.发送给客户端
		 */
		resp.getWriter().print(b);

		return null;
	}

	/*
	 * 注册校验服务端 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中，返回map
	 * 
	 */
	private Map<String, String> validateRegist(User formUser, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * 1.校验登录名
		 */
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if (!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册");
		}
		/*
		 * 2.校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginname", "密码不能为空！");
		} else if (loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginname", "密码长度必须在3~20之间！");
		}
		/*
		 * 3.确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次密码不相同！");
		}
		/*
		 * 4.校验Email
		 */
		String email = formUser.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式不正确！");
		} else if (!userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册");
		}
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误");
		}
		return errors;

	}

	/**
	 * 激活功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取参数激活码 2.用激活码调用service完成激活
		 * >service方法有可能抛出异常，把异常信息拿来，保存到request中，转发到msg.jsp显示
		 * 3.（无异常）保存成功信息到request中，转发到msg.jsp中显示
		 */
		String code = req.getParameter("activationCode");
		try {
			userService.activation(code);
			req.setAttribute("msg", "success");// 通知msg.jsp显示错号//通知msg.jsp显示对号
			req.setAttribute("msg", "激活成功，请马上登录！");
		} catch (UserException e) {
			// 说明抛出了异常
			req.setAttribute("msg", e.getMessage());// 将异常信息传给msg.jsp
			req.setAttribute("code", "error");// 通知msg.jsp显示错号
		}

		return "f:/jsps/msg.jsp";
	}

	/**
	 * 登录功能
	 * 
	 * 
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User对象 
		 * 2.校验表单数据
		 * 3.使用UserService查询，得到User 
		 * 4.查看用户是否存在,如果不存在
		 * >保存错误信息：用户名或密码错误 >保存用户数据，为了回显 >转发到login.jsp 
		 * 5.如果存在，查看状态，如果状态为false：
		 * >保存错误信息，您没有激活； >保存用户数据：为了回显 >转发到login.jsp 
		 * 6.登陆成功： >保存当前查询出的user到session中；
		 * >保存当前用户的名称到cookie中，注意：中文需要编码处理
		 */

		/*
		 * 1.封装表单数据到User对象
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2.校验
		 */
		Map<String, String> errors = validateLogin(formUser, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}
		/*
		 * 3.调用UserService#login（）方法
		 */
		User user = userService.login(formUser);
		/*
		 * 4.判断
		 */
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		} else {
			if (!user.isStatus()) {
				req.setAttribute("msg", "还未激活！");
				req.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";
			} else {
				// 保存用户到session
				req.getSession().setAttribute("sessionUser", user);
				// 获取用户名保存到cookie中
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "UTF-8");
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);// 保存十天
				resp.addCookie(cookie);// 发送cookie
				return "r:/index.jsp";// 重定向到主页
			}
		}

	}
	

	/*
	 * 登录校验的方法体，内容可拓展
	 */
	private Map<String, String> validateLogin(User formUser, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		
		/*
		 * 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String)session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}

	/**
	 * 修改密码功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到user中
		 * 2.从sessioin中获取uid
		 * 3.使用uid和表单中的新旧密码来调用service
		 * 	>如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4.保存成功信息到request中
		 * 5.转发到msg.jsp
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		if(user == null) {
			req.setAttribute("msg","您还没有登录！");
			return "f:/jsps/user/login.jsp";
		}
		try {
		userService.updatePassword(user.getUid(),formUser.getNewpass(),formUser.getLoginpass());
		req.setAttribute("msg", "密码修改成功！");
		req.setAttribute("code", "success");
		return "f:/jsp/user/pwd.jsp";
		}catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request
			req.setAttribute("formUser", formUser);//为了回显
			return "f:/jsps/user/pwd.jsp";
		}
	}
	
	
	/**
	 * 退出功能
	 * 
	 */
	public String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		return "f:/jsps/user/login.jsp";
	}
	
	
	
	
	
}
