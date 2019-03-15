package cn.itcast.goods.admin.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.admin.admin.domain.Admin;
import cn.itcast.goods.admin.admin.service.AdminService;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private AdminService adminService = new AdminService();
   
	
	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据
		 */
		Admin form = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.login(form);
		if(admin == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/adminjsps/login.jsp";
		}else {
			req.getSession().setAttribute("admin", admin);
			return "/adminjsps/admin/index.jsp";
		}
	}
	
	
	
}
