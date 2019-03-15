package cn.itcast.goods.order.service;

import java.sql.SQLException;

import cn.itcast.goods.order.dao.OrderDao;
import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> myOrders(String uid, int pc){
		try {
			JdbcUtils.beginTransaction();//事务开始
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();//事务提交
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();//事务回滚
			} catch (Exception e1) {}
				throw new RuntimeException(e);
		}
	}

	/**
	 * 生成订单
	 * @param order
	 */
	public void creatOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {	}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid,int status) {
		try {
			orderDao.updateStatus(oid,status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
