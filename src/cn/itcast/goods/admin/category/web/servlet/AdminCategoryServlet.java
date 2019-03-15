package cn.itcast.goods.admin.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;


public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	
	
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	
	
	/**
	 * 添加一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Category中
		 * 2.调用service方法完成添加
		 * 3.调用findAll（），返回list.jsp显示所有分类
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());//设置cid
		categoryService.add(parent);
		return findAll(req,resp);
	}

	/**
	 * 添加二级分类 ：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");//当前点击的父分类的id
		List<Category> parents = categoryService.findParents();//获取所有父分类
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		
		return "f:/adminjsps/admin/category/add2.jsp";
	}

	
	/**
	 * 添加二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Category中
		 * 1.5 需要手动地把表单中的pid映射到child对象中
		 * 2.调用service方法完成添加
		 * 3.调用findAll（），返回list.jsp显示所有分类
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());//设置cid
		
		//手动映射pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		return findAll(req,resp);
	}	
	
	/**
	 * 修改一级分类：第一步（加载）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取cid
		 * 2.使用cid调用service加载Category
		 * 3.保存Category
		 * 4.转发到edit.jsp页面显示Category
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}

	
	/**
	 * 修改一级分类：第二步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Category中
		 * 2.调用Service方法进行修改
		 * 3.转发到list.jsp显示所有分类（return findAll（））
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(req, resp);
	}

	/**
	 * 修改二级分类：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取连接参数cid 通过cid加载Category child中  保存之
		 * 2.查询出所有一级分类 保存之
		 * 3.转发到list.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());
		return "f:/adminjsps/admin/category/edit2.jsp";
	}

	/**
	 * 修改二级分类：第二步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到Category child
		 * 2.把表单中的pid封装到child，...
		 * 3.调用service方法完成修改
		 * 4.返回到list.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.edit(child);
		
		return findAll(req, resp);
	}

	
	/**
	 * 删除一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取链接参数cid（1级分类的ID）
		 * 2.通过cid，查看该父分类下子分类的个数
		 * 3.判断，如果大于零，则说明有子分类，不能删除。保存错误信息，转发到msg.jsp
		 * 		  如果等于0，则删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryService.findChildrenCountByParent(cid);
		if(cnt > 0) {
			//req.setAttribute("code", "error"); 后台没必要勾/叉号显示
			req.setAttribute("msg", "该分类下还有子类，无法删除！");
			return "f:/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
		
	}

	/**
	 * 删除二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取链接参数cid（2级分类的ID）
		 * 2.通过cid，查看该父分类下图书的个数
		 * 3.判断，如果大于零，保存错误信息，转发到msg.jsp
		 * 		  如果等于0，则删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt > 0) {
			//req.setAttribute("code", "error");后台没必要勾/叉号显示
			req.setAttribute("msg", "该分类下还存在图书，无法删除！");
			return "f:/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
	}

	
	
	
}
