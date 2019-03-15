package cn.itcast.goods.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

/**
 * 分类模块web层
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryService();   
    /**
     * 查询所有分类
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	/*
    	 * 1.service得到所有分类
    	 * 2.保存到request中，转发到left.jsp
    	 */
    	List<Category> parents = categoryService.findAll();
    	req.setAttribute("parents", parents);
    	return "f:/jsps/left.jsp";
    }
    	
}
