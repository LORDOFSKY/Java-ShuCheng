package cn.itcast.goods.book.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();

	/**
	 * 获取pc
	 * 
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if (param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}

	/**
	 * 截取URL的页码（pc）部分
	 * 
	 * @param req
	 * @return
	 */
	/*
	 * 原地址：http://localhost:8080/goods/BookServlet?method=findByCategory&cid=xxx&pc=
	 * 3 分为两部分：/goods/BookServlet + method=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，则截取掉，如果不存在则不用截取
		 */
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			url = url.substring(0, index);// 截取
		}
		return url;
	}

	/**
	 * 按分类查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.获取查询条件，本方法为分类，所以是cid，即分类的id
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按作者查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.获取查询条件
		 */
		String author = req.getParameter("author");
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按出版社查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.获取查询条件
		 */
		String press = req.getParameter("press");
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按书名查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.获取查询条件
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 多条件组合查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.获取查询条件
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按bid查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
	
	
	
}
