package cn.itcast.goods.cart.web.Servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.cart.service.CartItemService;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CartItemsServlet
 */
public class CartItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       private CartItemService cartItemService = new CartItemService();
       
       
       
      /**
       * 我的购物车
       * @param req
       * @param resp
       * @return
       * @throws ServletException
       * @throws IOException
       */
       public String myCart(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    		/*
    		 * 1.得到uid
    		 */
    	    User user = (User)req.getSession().getAttribute("sessionUser");
    		String uid = user.getUid();
    		/*
    		 * 2.通过Service得到当前用户的所有购物车条目
    		 */
    		List<CartItem> cartItemLIst = cartItemService.myCart(uid);
    		/*
    		 * 3.保存起来，转发到/cart/list.jsp
    		 */
    		req.setAttribute("CartItemList", cartItemLIst);
    		return "f:/jsps/cart/list.jsp";
    		
       }
    		
       	
       /**
      	 * 添加条目
        * @param req
        * @param resp
        * @return
        * @throws ServletException
        * @throws IOException
        */
       public String add(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    	   	/*
    	   	 * 1.封装表单数据到CartItem（bid，quantity）
    	   	 */
    	   Map map = req.getParameterMap();
    	   CartItem cartItem = CommonUtils.toBean(map, CartItem.class);//封装quantity
    	   Book book =CommonUtils.toBean(map, Book.class);
    	   User user = (User)req.getSession().getAttribute("sessionUser");
    	   cartItem.setBook(book);
    	   cartItem.setUser(user);
    	   /*
    	    * 2.调用service完成添加
    	    */
    	   cartItemService.add(cartItem);
    	   
    	   /*
    	    * 3.查询当前用户的所有条目，转发到list.jsp
    	    */
    	   return myCart(req,resp);//调用购物车的方法
       }

       /**
        * 批量删除
        * @param req
        * @param resp
        * @return
        * @throws ServletException
        * @throws IOException
        */
       public String batchDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    	   /*
    	    * 1.获取cartItems参数
    	    * 2.调用service方法
    	    * 3.返回到list.jsp
    	    */
    	   String cartItemIds = req.getParameter("cartItemIds");
    	   cartItemService.batchDelete(cartItemIds);
    	   return myCart(req,resp);
       }
    		
       /**
            * 修改数量
        * @param req
        * @param resp
        * @return
        * @throws ServletException
        * @throws IOException
        */
       public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    	   String cartItemId = req.getParameter("cartItemId");
    	   int quantity = Integer.parseInt(req.getParameter("quantity"));
    	   CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
    	   //给客户端返回一个json对象
    	    StringBuilder sb = new StringBuilder("{");
    	   	sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
    	   	sb.append(",");	
    	   	sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
    	   	sb.append("}");
    	   	
    	   	resp.getWriter().print(sb);		
    	   	return null;
       }
       
       
       /**
        * 加载多个CartItem
        * @param req
        * @param resp
        * @return
        * @throws ServletException
        * @throws IOException
        */
       public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    	   /*
    	    * 1.获取cartIteIds参数
    	    */
    	   String cartItemIds = req.getParameter("cartItemIds");
    	   
    	   double total = Double.parseDouble(req.getParameter("total"));
    	   /*
    	    * 2.通过service得到List<CartItem>
    	    */
    	   List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
    	   /*
    	    * 3.保存，然后转发到/cart/showitem.jsp
    	    */
    	   req.setAttribute("cartItemList", cartItemList);
    	   req.setAttribute("total", total);
    	   req.setAttribute("cartItemIds", cartItemIds);
    	   return "f:/jsps/cart/showitem.jsp";
       }
       
       
}
