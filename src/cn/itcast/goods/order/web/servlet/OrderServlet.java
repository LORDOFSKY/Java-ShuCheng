package cn.itcast.goods.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.cart.service.CartItemService;
import cn.itcast.goods.order.dao.OrderItem;
import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.order.service.OrderService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	private CartItemService cartItemService = new CartItemService();// 跨模块依赖

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
	 * 获取URL
	 * 
	 * @param req
	 * @return
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
	 * 我的订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.得到pc，如果页面传，使用页面的 如果页面没传，默认pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2.得到url
		 */
		String url = getUrl(req);
		/*
		 * 3.从session中获取User
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		/*
		 * 4.使用pc和cid 调用service的findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/order/list.jsp";
	}

	
	
	public String createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1.获取所有购物车条目，然后查询之
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 2.创建订单
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(String.format("%tF %<tT",new Date()));
		order.setStatus(1);
		order.setAddress(req.getParameter("address"));
		User owner = (User)req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);
		
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : cartItemList) {
		total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
		}
		order.setTotal(total.doubleValue());
		/*
		 * 3.创建List<OrderItem>
		 * 一个CartItem对应一个OrderItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(CartItem cartItem : cartItemList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());//设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());;
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);//实现双向关联
		/*
		 * 4.调用Service完成添加
		 */
		orderService.creatOrder(order);
		
		//删除购物车条目
		cartItemService.batchDelete(cartItemIds);
		
		/*
		 * 5.保存，转发
		 */
		req.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}
	
			
	/**
	 * 加载订单	
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		
		String btn = req.getParameter("btn");
		req.setAttribute("btn", btn);
		
		return "f:/jsps/order/desc.jsp";
	}

	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "订单状态错误，不能取消！");
			return "f:/jsps/msg.jsp";
		}else {
		orderService.updateStatus(oid, 5);//设置状态为取消
		req.setAttribute("code", "success");
		req.setAttribute("msg", "订单已取消！");
		return "f:/jsps/msg.jsp";
		}
	}
	
	
	/**
	 * 确认收货
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "订单状态错误，无法确认收货！");
			return "f:/jsps/msg.jsp";
		}else {
		orderService.updateStatus(oid, 4);//设置状态为交易成功
		req.setAttribute("code", "success");
		req.setAttribute("msg", "交易成功！");
		return "f:/jsps/msg.jsp";
		}
	}
	
	/**
	 * 支付准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String paymentPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("order", orderService.load(req.getParameter("oid")));
		return "f:/jsps/order/pay.jsp";
	}

	/**
	 * 支付方法
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("order", orderService.load(req.getParameter("oid")));
		//加载配置文件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		/*
		 * 1.准备13个参数,详见接口参数文档
		 */
		String p0_Cmd = "Buy";//业务类型，固定值为“Buy”
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = req.getParameter("oid");//订单编码
		String p3_Amt = "0.01";//支付金额
		String p4_Cur = "CNY";//交易币种，固定值
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述
		String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址
		String p9_SAF = "";//送货地址
		String pa_MP = "";//扩展信息
		String pd_FrpId = req.getParameter("yh");//支付通道
		String pr_NeedResponse = "1";//应答机制。固定值为“1”
		
		/*
		 * 2.计算hmac
		 * 需要上面的13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		/*
		 * 3.重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");//易宝订单访问地址
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		
		resp.sendRedirect(sb.toString());//请求重定向
		return null;
	}
	
	/**
	 * 回馈方法
	 * 当支付成功时，易宝会访问这里
	 * 用两种方法访问：
	 * 1.引导用户浏览器重定向（如果用户关闭了浏览器，则不访问这里）
	 * 2.易宝的服务器会进行点对点通讯的方式访问这个方法(必须回馈success，否则易宝服务器会一直调用这个方法)
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String back(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("order", orderService.load(req.getParameter("oid")));
		/*
		 * 1.获取12个参数
		 */
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String hmac = req.getParameter("hmac");
		/*
		 * 2.获取keyValue
		 */
		//加载配置文件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		String keyValue = props.getProperty("keyValue");
		/*
		 * 3.调用PaymentUtil方法来校验调用者的身份
		 *>如果校验失败：保存错误信息，转发到msg.jsp
		 *>如果校验通过：
		 *	*判断访问的方法是重定向还是点对点，
		 *		如果是重定向，则
		 *			修改订单状态，保存成功信息到msg.jsp
		 *		如果是点对点，则
		 *			修改订单状态，返回success
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(!bool) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "无效的签名，支付失败！(你不是好人！)");
			return "f:/jsps/msg.jsp";
		}
		if(r1_Code.equals("1")) {
			orderService.updateStatus(r6_Order, 2);
			if(r9_BType.equals("1")) {
				req.setAttribute("code", "success");
				req.setAttribute("msg", "恭喜，支付成功！");
				return "f:/jsps/msg.jsp";
			}else if(r9_BType.equals("2")){
				resp.getWriter().print("success");
			}
		}
		return null;
	}
	
	
	
	
			
}
