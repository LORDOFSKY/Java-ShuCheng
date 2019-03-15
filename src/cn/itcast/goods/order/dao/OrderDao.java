package cn.itcast.goods.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.pager.Expression;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.pager.PageConstants;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	
	private PageBean<Order> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
	/*
	 * 1.得到ps 2.得到tr 3.得到beanList 4.创建PageBean ，返回
	 */
	/*
	 * 1.得到ps
	 */
	int ps = PageConstants.ORDER_PAGE_SIZE;// 每页记录数
	/*
	 * 2.通过exprList来生成where子句
	 */
	StringBuilder whereSql = new StringBuilder(" where 1=1");// where 1=1 没有具体意思 只是为了引出后面的and
	List<Object> params = new ArrayList<Object>();// SQL中的有问号， 对应问号的值
	for (Expression expr : exprList) {
		/*
		 * 添加一个条件上； 1）以and开头 2）条件的名称 3 ）条件的运算符，可以是= ，！=，<,>,...is null,is null没有值
		 * 4）如果条件不是is null，再追加问号，然后再向params中添加一个和问号对应的值
		 */
		whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
		// where 1=1 and bid = is null
		if (!expr.getOperator().equals("is null")) {
			whereSql.append("?");
			params.add(expr.getValue());
		}
	}

	/*
	 * 3.总记录数
	 */
	String sql = "select count(*) from t_order" + whereSql;
	Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
	int tr = number.intValue();// 得到总记录数

	/*
	 * 4.得到beanList，即当前页记录
	 */
	sql = "select * from t_order" + whereSql + "order by ordertime desc limit ?,?";
	params.add((pc - 1) * ps);// 第一个问号，当前页首行记录的下标
	params.add(ps);// 一共查询几行
	List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
	//虽然已获取所有订单，但此时的订单没有订单条目
	//遍历所有订单，为其加载它的所有订单条目
	for(Order order : beanList) {
		loadOrderItem(order);
	}
	
	
	/*
	 * 5.创建PageBean，设置参数
	 */
	PageBean<Order> pb = new PageBean<Order>();
	/*
	 * 其中PageBean没有url，这个任务交给Servlet
	 */
	pb.setBeanList(beanList);
	pb.setPc(pc);
	pb.setPs(ps);
	pb.setTr(tr);

	return pb;
}
	
	/**
	 * 为指定的订单加载所有订单条目
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItem(Order order) throws SQLException {
		/*
		 * 1.给sql语句select* from t_order where oid=?
		 * 2.执行之，得到List<OderItem>
		 * 3.设置给Order对象
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
	}

	/**
	 * 将多个Map转换成多个OrderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	
	
	/**
	 * 把一个Map转换成一个OrderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * 按作者查询
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid));
		return findByCriteria(exprList, pc);
	}
	
	
	public void addOrder(Order order) throws SQLException {
		/*
		 * 1.插入订单
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		qr.update(sql,params);
		
		/*
		 * 2.循环遍历订单的所有条目，让每个条目生成一个Object[]
		 * 多个条目就对应Object[][]
		 * 执行批处理，完成插入订单条目
		 */
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();//有多少个orderItem
		Object[][] objc = new Object[len][];
		for(int i = 0 ; i<len; i++) {
			OrderItem item = order.getOrderItemList().get(i);
			objc[i] = new Object[] {item.getOrderItemId(),item.getQuantity(),item.getSubtotal(),item.getBook().getBid(),item.getBook().getBname(),item.getBook().getCurrPrice(),item.getBook().getImage_b(),order.getOid()};
		}
		qr.batch(sql, objc);
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		loadOrderItem(order);
		return order;
	}
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(),oid);
		return number.intValue();
	}
	
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String oid ,int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql,status,oid);
	}
	
}