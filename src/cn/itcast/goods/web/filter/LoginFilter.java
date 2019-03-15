package cn.itcast.goods.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.goods.user.domain.User;

public class LoginFilter implements Filter {

   	public void destroy() {

   	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * 1.获取session中的user
		 * 2.判断是否为null:
		 * 		>若为null，则保存错误信息，转发到msg.jsp
		 * 		>若不为null，则放行
		 */
		HttpServletRequest req = (HttpServletRequest)request;//request类型为ServletRequest，需要转换成HttpServletRequest才能获取session
		Object user = req.getSession().getAttribute("sessionUser");
		if(user == null) {
			req.setAttribute("code", "error");//msg上显示X图片
			req.setAttribute("msg","您还未登录，不能访问本资源！" );
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);//不是BaseServlet不能直接return转发
		}else {
			chain.doFilter(request, response);//放行
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
